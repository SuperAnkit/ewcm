package ops.global.core.servlets;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;



@SlingServlet(paths = "/bin/DDreverseStatementCycle",
        methods = {"POST"},
        metatype = true)
public class OPSReverseSelectStatementCycleServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
	HttpClient client = new HttpClient();
	String toReturnResults = null;
   
        private Logger logger = LoggerFactory.getLogger(OPSReverseSelectStatementCycleServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	
        	// fetch all the parameters
        	String ldDDAStatementCycle = request.getParameter("ldDDAStatementCycle");
        	
        	if (Integer.parseInt(ldDDAStatementCycle) == -1) {
        		toReturnResults = "Select";
				
			} else {
				GetMethod method = null;
	        	
	        	logger.info("VALUE of ldDDAStatementCycle" + ldDDAStatementCycle);        	
	        	
	        	String getServiceURL = OPSConstants.WS_GET_STATEMENT_CYCLE_CODE_VALUE_URL.replace("ldDDAStatementCycle", ldDDAStatementCycle);
	        	// create WS connection
	        	method = new GetMethod(getServiceURL);
	        	
	        	logger.info("REDAER LOG++++++++++++++++++++++++" + getServiceURL);
	        	
	        	// Execute the method.
				int statusCode = client.executeMethod(method);
				
				InputStream inputStream = method.getResponseBodyAsStream();
				  		
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(inputStream));
				String inputLine;
				StringBuffer newresponse = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					newresponse.append(inputLine);
				}
				in.close();
				
		
		toReturnResults = newresponse.toString();

			}
        	
        	
	logger.info("REDAER LOG++++++++++++++++++++++++" + toReturnResults);

	response.setContentType("application/json");
    response.getWriter().write(toReturnResults.toString());


    }

    }

