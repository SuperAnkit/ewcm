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

public class ErrorMessage extends WCMUse {

	private final Logger log = LoggerFactory.getLogger(ErrorMessage.class);
	private static String message = null;
   
	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	@Override
	public void activate() throws Exception {
		
		message = getRequest().getParameter("errorMsg");

	}


	
	
	public String getMessage() {
		log.info("ERRO+++++++++++++++++++++++++++++++" + message);
		return message;
	}
	
	
	
}