package ops.sightly.components;

import java.util.Iterator;
import org.apache.felix.scr.annotations.Reference;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.sightly.WCMUse;

public class GuideHiddenTextbox extends WCMUse {

	private final Logger log = LoggerFactory
			.getLogger(GuideHiddenTextbox.class);
	private static final String PROP_LIST = "listFrom";
	private static final String STATIC_VALUE = "staticValue";
	private static final String MAKER_GROUP = "maker_group";
	private static final String CHECKER_GROUP = "checker_group";
	private static final String READER_GROUP = "reader_group";

	private static final String PROP_LIST_OPTION1 = "userGroup";
	private static final String PROP_LIST_OPTION2 = "userID";
	private static final String PROP_LIST_OPTION3 = "sessionToken";
	private static final String PROP_LIST_OPTION4 = "staticValue";

	private static String hiddenFieldValue = null;

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	public void activate() throws Exception {

		String listGroup = getProperties().get(PROP_LIST, String.class);

		if (listGroup.equals(PROP_LIST_OPTION1)) {
			UserManager userManager = getResourceResolver().adaptTo(
					UserManager.class);
			Authorizable opsBluPrintUser = userManager
					.getAuthorizable(getRequest().getUserPrincipal());
			Iterator<Group> groups = opsBluPrintUser.memberOf();
			for (; groups.hasNext();) {
				String groupName = groups.next().getPrincipal().getName();

				if ((groupName.equals(MAKER_GROUP))
						|| (groupName.equals(CHECKER_GROUP))
						|| (groupName.equals(READER_GROUP))) {
					hiddenFieldValue = groupName;
				}

			}

		} else if (listGroup.equals(PROP_LIST_OPTION2)) {

			hiddenFieldValue = getRequest().getUserPrincipal().getName();
		} else if (listGroup.equals(PROP_LIST_OPTION3)) {
			// to be added

		} else if (listGroup.equals(PROP_LIST_OPTION4)) {
			hiddenFieldValue = getProperties().get(STATIC_VALUE, String.class);

		}

	}

	public String getHiddenFieldValue() {
		return hiddenFieldValue;
	}

}