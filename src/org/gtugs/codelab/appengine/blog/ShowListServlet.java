package org.gtugs.codelab.appengine.blog;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.codelab.appengine.blog.datastore.Comment;
import org.gtugs.codelab.appengine.blog.datastore.Post;
import org.gtugs.codelab.appengine.blog.utils.AccountUtils;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ShowListServlet extends HttpServlet {

	private final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		String tagParam = req.getParameter("tag");

		Writer out = resp.getWriter();

		UserService userService = UserServiceFactory.getUserService();
		boolean isAdmin = AccountUtils.isAdmin(req);
		if (isAdmin) {
			out.write("<a href=\""
					+ userService.createLogoutURL(req.getRequestURI())
					+ "\">logout</a><br /><hr>");
		} else {
			out.write("<a href=\""
					+ userService.createLoginURL(req.getRequestURI())
					+ "\">login</a><br /><hr>");
		}

		out.write("<a href=\"/admin/view.jsp\">New Entry</a><br />");
		out.write("<hr />");

		out.write("<h1><a href=\"/\">Hello App Engine!</a></h1>");
		out.write("<hr />");

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Post.class);
		List<Post> list = null;
		if (tagParam != null) {
			query.setFilter("tags == :tag");
			list = (List<Post>) query.execute(tagParam);
		} else {
			list = (List<Post>) query.execute();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		for (Post post : list) {
			String date = sdf.format(post.getDate());
			out.write(date + "<font size=5>" + post.getTitle() + "</font>");

			if (AccountUtils.isAdmin(req)) {
				String linkEdit = "[<a href=\"/admin/edit?id=" + post.getId()
						+ "\">edit</a>]";
				out.write(linkEdit);
			}

			out.write("<br />");
			List<String> tags = post.getTags();
			if (tags != null) {
				out.write("<font size=1>[TAG] ");
				for (String tag : tags) {
					out.write("<a href=\"/list?tag="
							+ URLEncoder.encode(tag, "UTF-8") + "\">" + tag
							+ "</a> ");
				}
				out.write("</font><br />");
			}
			out.write(post.getContent());
			out.write("<hr />");

			// comment
			Query commentQuery = pm.newQuery(Comment.class);
			commentQuery.setFilter("postId == '"+ post.getId() +"'");
			List<Comment> comments = (List<Comment>) commentQuery.execute();
			for (Comment comment : comments) {
				String commentDate = sdf.format(comment.getDate());
				out.write("@"+ comment.getName() +" : "+ comment.getComment() + " ("+ commentDate +")<br>");
			}

			if (AccountUtils.isAdmin(req)) {
				out.write("<form method=POST action=/admin/comment>" +
					"      <input id=id type=hidden name=id value='"+ post.getId() +"' />" +
					"      @<input id=name type=text name=name size='7'/>" +
					"      <input id=comment type=text name=comment />" +
					"      <input id=submit type=submit value='つっこみ' />" +
					"    </form>");
			} else {
				out.write("ログインしたらつっこめます<br>");
			}
			out.write("<hr />");
		}
	}
}
