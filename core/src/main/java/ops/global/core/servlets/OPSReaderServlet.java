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

import com.day.cq.commons.Externalizer;


@SlingServlet(paths = "/bin/getReaderPage",
        methods = {"GET"},
        metatype = true)
public class OPSReaderServlet extends SlingAllMethodsServlet {
    
    private static final long serialVersionUID = 1L;
    private static final String SEARCH_KEYWORD = "loanNum";
    private static final String DOC_TYPE = "docType";
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
    	logger.info("+++++++++++++++++++INTO OPS SEARCH");
    	
    	
    	search_keyword = request.getParameter(SEARCH_KEYWORD);
    	docType = request.getParameter(DOC_TYPE);
    	
    	///search_keyword = "23";
    	logger.info("SRCH TXT" + search_keyword);
	
		// get details for DB
		
    	String param = "{\"applicationNumber\":\""
    			+ search_keyword + "\", \"userName\":\"\", \"sessionToken\":\"401064664\", \"stage\":\"" + docType + "\"}";
    	ResourceResolver resResolver = null;
        
        try {
			resResolver = this.resourceResolverFactory.getAdministrativeResourceResolver(null);
			logger.info("PARAM  " + param);
	    	Externalizer externalizer = resResolver.adaptTo(
					Externalizer.class);
			String getServiceURL = externalizer.externalLink(resResolver, "readerGet","");
			logger.info("EXT ++++++" + getServiceURL);
	    	URL URLobj = new URL(getServiceURL);
	    	
			URLConnection connection = URLobj.openConnection();
			
	    	String charset = "UTF-8"; 
	    	  //URLConnection connection = new URL(url).openConnection();
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
	logger.info("REDAER LOG PAREM++++++++++++++++++++++++" + param);
	logger.info("REDAER LOG++++++++++++++++++++++++" + toReturnResults);

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

