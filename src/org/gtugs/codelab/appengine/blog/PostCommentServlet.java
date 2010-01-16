package org.gtugs.codelab.appengine.blog;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.jdo.PersistenceManager;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.codelab.appengine.blog.datastore.Comment;

public class PostCommentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		String strId = req.getParameter("id");
		String strName = req.getParameter("name");
		String strComment = req.getParameter("comment");

		strComment = strComment.replaceAll("\\n", "<br />\n");

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Comment comment = new Comment();
		comment.setPostId(strId);
		comment.setName(strName);
		comment.setComment(strComment);
		comment.setDate(new Date());

		try {
			pm.makePersistent(comment);

			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);

			// TODO
			InternetAddress address = new InternetAddress("koichiro.nishijima@gmail.com",
					"Administrator", "iso-2022-jp");

			MimeMessage message = new MimeMessage(session);
			message.setFrom(address);
			message.addRecipient(javax.mail.Message.RecipientType.TO, address);

			// TODO
			message.setSubject("Message from GAE blog", "ISO-2022-JP");
			message.setText(strName + "\n\n" + strComment);
			Transport.send(message);

			resp.sendRedirect("/");
		} catch (MessagingException e) {
			// TODO
			e.printStackTrace();
		} finally {
			pm.close();
		}

	}

}
