package org.gtugs.codelab.appengine.blog.datastore;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gtugs.codelab.appengine.blog.PMF;
import org.gtugs.codelab.appengine.blog.utils.Environment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;

public class PostTest {

	@Before
	public void setUp() throws Exception {
		// ApiProxyの設定
		ApiProxy.setEnvironmentForCurrentThread(new Environment());
		ApiProxy.setDelegate(new ApiProxyLocalImpl(new File(".")) {
		});
	}

	@After
	public void tearDown() throws Exception {
		// ApiProxyの設定を破棄
		ApiProxy.setDelegate(null);
		ApiProxy.setEnvironmentForCurrentThread(null);
	}

	@Test
	public void 保存テスト() {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Post post = new Post();
		post.setTitle("JUnit test");
		post.setDate(new Date());
		post.setContent("本文です");
//		List<String> tags = new ArrayList<String>();
//		tags.add("test");
//		post.setTags(tags);

		try {
			pm.makePersistent(post);
		} finally {
			pm.close();
		}
	}

	@Test
	public void 一覧テスト() {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query query = pm.newQuery(Post.class);
		List<Post> list = (List<Post>) query.execute();
		for (Post post : list) {
			System.out.println(post.getTitle() +" / "+ post.getDate());
		}
	}
}
