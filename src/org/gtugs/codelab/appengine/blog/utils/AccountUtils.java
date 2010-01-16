package org.gtugs.codelab.appengine.blog.utils;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AccountUtils {

	public static boolean isAdmin(HttpServletRequest request) {
		UserService userService = UserServiceFactory.getUserService();

		Principal principal = request.getUserPrincipal();

		if (principal == null || !userService.isUserAdmin()) {
			return false;
		}
		return true;
	}
}
