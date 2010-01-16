package org.gtugs.codelab.appengine.blog;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gtugs.codelab.appengine.blog.utils.AccountUtils;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AccountFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = ((HttpServletRequest) req);

		UserService userService = UserServiceFactory.getUserService();
		String requestUri = request.getRequestURI();

		if (!AccountUtils.isAdmin(request)) {
			// create login url
			((HttpServletResponse) resp).sendRedirect(userService
					.createLoginURL(requestUri));
			return;
		}

		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
	}

}
