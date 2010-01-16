package org.gtugs.codelab.appengine.blog.datastore;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.codelab.appengine.blog.AbstractTestCase;
import org.gtugs.codelab.appengine.blog.PMF;
import org.junit.Test;


public class CommentTest extends AbstractTestCase {
	PersistenceManager pm = PMF.get().getPersistenceManager();

	@Test
	public void 書き込み() {
		Comment comment = new Comment();
		comment.setPostId("1");
		comment.setName("JUnit test");
		comment.setComment("コメントです");

		try {
			pm.makePersistent(comment);
		} finally {
			pm.close();
		}
	}

	@Test
	public void 一覧() {
		Query commentQuery = pm.newQuery(Comment.class);
		List<Comment> comments = (List<Comment>) commentQuery.execute();
		for (Comment comment : comments) {
			System.out.println(comment.getPostId() +"@"+ comment.getName() +" : "+ comment.getComment() + "<br>");
		}

	}

	@Test
	public void フィルタして一覧() {
		long postId = 1;

		Query commentQuery = pm.newQuery(Comment.class);
		commentQuery.setFilter("postId == '"+ postId +"'");
		List<Comment> comments = (List<Comment>) commentQuery.execute();
		for (Comment comment : comments) {
			System.out.println(comment.getPostId() +"@"+ comment.getName() +" : "+ comment.getComment() + "<br>");
		}

	}


}
