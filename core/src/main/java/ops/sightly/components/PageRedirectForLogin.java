package ops.sightly.components;

import java.util.Iterator;

import ops.global.core.servlets.OPSConstants;

import org.apache.felix.scr.annotations.Reference;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.adobe.cq.sightly.WCMUse;
import com.day.cq.wcm.api.WCMMode;

public class PageRedirectForLogin extends WCMUse {

	private final Logger log = LoggerFactory
			.getLogger(PageRedirectForLogin.class);
	private static String redirectPath = null;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	public void activate() throws Exception {

		boolean loginCookieExists = false;
		boolean isOPSUser = false;
		boolean isModeDisabled = false;
		Cookie cookie = null;
		String currentPagePath = getRequest().getRequestURI();

		// check if wcmMode is disabled
		isModeDisabled = WCMMode.fromRequest(getRequest()).equals(WCMMode.DISABLED);
		
		// Get an array of Cookies associated with this domain
		Cookie[] cookies = getRequest().getCookies();

		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().equals("login-token")
					&& (!cookie.getValue().equals(null))) {

				loginCookieExists = true;
				break;

			}
		}

		// check the users group membership
		UserManager userManager = getResourceResolver().adaptTo(
				UserManager.class);
		Authorizable opsBluPrintUser = userManager.getAuthorizable(getRequest()
				.getUserPrincipal());
		Iterator<Group> groups = opsBluPrintUser.memberOf();
		for (; groups.hasNext();) {
			String groupName = groups.next().getPrincipal().getName();

			if ((groupName.equals(OPSConstants.MAKER_GROUP))
					|| (groupName.equals(OPSConstants.CHECKER_GROUP))
					|| (groupName.equals(OPSConstants.READER_GROUP))) {
				isOPSUser = true;
			}

		}

		if (loginCookieExists) {
			// when login cookie exists
			// do nothing for ops users and redirect the other users to login
			// page again

			redirectPath = OPSConstants.REDIRECT_LOGIN_URL + "?resource="
					+ currentPagePath;
			if (!isOPSUser) {
				cookie.setPath("/");
				cookie.setMaxAge(0);
				cookie.setValue(null);
				getResponse().addCookie(cookie);
				getResponse().setStatus(
						HttpServletResponse.SC_MOVED_PERMANENTLY);
				getResponse().setHeader("Location", redirectPath);
				getResponse().sendRedirect(redirectPath);
			}

		} else {
			// when no login cookie exists
			// forward to the login page for ops users
			if (currentPagePath.contains("&guideStatePathRef=")
					|| currentPagePath.contains("&dataRef=")) {
				redirectPath = OPSConstants.REDIRECT_LOGIN_URL + "?resource="
						+ OPSConstants.FP_FORMS_LANDING_PATH;
			} else {
				redirectPath = OPSConstants.REDIRECT_LOGIN_URL + "?resource="
						+ currentPagePath;
			}


			if (isModeDisabled) {
				redirectPath = redirectPath + OPSConstants.LOGIN_STRING;
			} else {
				redirectPath = redirectPath + OPSConstants.WCM_MODE_DISABLED + OPSConstants.LOGIN_STRING;
			}
			
			getResponse().setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			getResponse().setHeader("Location", redirectPath);
			getResponse().sendRedirect(redirectPath);

		}

	}

}