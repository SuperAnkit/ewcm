package ops.global.core.servlets;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ops.global.core.servlets.OPSConstants;

import com.day.cq.commons.Externalizer;


@SlingServlet(paths = "/bin/getReaderPage",
        methods = {"GET"},
        metatype = true)
public class OPSReaderServlet extends SlingAllMethodsServlet {
    
	// initializing all the constants
    private static final long serialVersionUID = 1L;
    private String search_keyword;
    private String docType;
	HttpClient client = new HttpClient();

	GetMethod method = null;
    
    @Reference
	private ResourceResolverFactory resourceResolverFactory;
   
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	
    	// fetch all the parameters
    	search_keyword = request.getParameter(OPSConstants.SEARCHED_APP_NO);
    	docType = request.getParameter(OPSConstants.DOC_TYPE);
    		
		// populate json structure for querying DB		
    	String param = OPSConstants.READER_PARAM.replace(OPSConstants.SEARCHED_APP_NO, search_keyword).replace(OPSConstants.DOC_TYPE, docType);
    	ResourceResolver resResolver = null;
        
        try {
			resResolver = this.resourceResolverFactory.getAdministrativeResourceResolver(null);

			// fetch WS URL from externalizer based upon the run-mode
	    	Externalizer externalizer = resResolver.adaptTo(Externalizer.class);
	    	String webServiceURL = externalizer.externalLink(resResolver,"WSRequest","");
	    	
			String getServiceURL = OPSConstants.WS_GET_READER_URL.replace(OPSConstants.WS_URL, webServiceURL);
			
			// create WS connection
	    	URL URLobj = new URL(getServiceURL);
			URLConnection connection = URLobj.openConnection();
			
	    	String charset = "UTF-8"; 
	    	  connection.setDoOutput(true); // Triggers POST.
	    	  connection.setRequestProperty("Accept-Charset", charset);
	    	  connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);

	    	  DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	    	  	wr.writeBytes(param);
	  			wr.flush();
	  			wr.close();

	  		
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer newresponse = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				newresponse.append(inputLine);
			}
			in.close();
			
		

	String toReturnResults = null;
	toReturnResults = newresponse.toString();

	if (toReturnResults != null) {
		response.getWriter().write(toReturnResults);
	} else {
		response.getWriter().write("");
	}


	    
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
    	
    	
    }
    
	
}

