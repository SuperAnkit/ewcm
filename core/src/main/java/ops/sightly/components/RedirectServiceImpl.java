package ops.sightly.components;

import java.io.IOException;
import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.WCMMode;

import ops.global.core.servlets.OPSConstants;
import ops.sightly.components.RedirectService;

import javax.servlet.http.Cookie;

//This is a component so it can provide or consume services
@Component
@Service
public class RedirectServiceImpl implements RedirectService {

	private final Logger log = LoggerFactory
			.getLogger(RedirectServiceImpl.class);
	private static String redirectPath = null;

	@Override
	// A basic setter method that sets key
	public void redirectPath(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws RepositoryException,
			IOException {

		boolean loginCookieExists = false;
		boolean isOPSUser = false;
		boolean isModeDisabled = false;
		Cookie cookie = null;
		String currentPagePath = request.getRequestURI();
		
		// check if wcmMode is disabled
		isModeDisabled = WCMMode.fromRequest(request).equals(WCMMode.DISABLED);
		
		// Get an array of Cookies associated with this domain
		Cookie[] cookies = request.getCookies();

		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (cookie.getName().equals("login-token")
					&& (!cookie.getValue().equals(null))) {

				loginCookieExists = true;
				break;

			}
		}

		// check the users group membership
		UserManager userManager = request.getResourceResolver().adaptTo(
				UserManager.class);
		Authorizable opsBluPrintUser = userManager.getAuthorizable(request
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
				response.addCookie(cookie);
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				response.setHeader("Location", redirectPath);
				response.sendRedirect(redirectPath);
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
			

			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", redirectPath);
			response.sendRedirect(redirectPath);

		}

	}

}