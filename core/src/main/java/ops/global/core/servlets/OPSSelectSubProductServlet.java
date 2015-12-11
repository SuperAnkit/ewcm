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



@SlingServlet(paths = "/bin/DDsubproduct",
        methods = {"POST"},
        metatype = true)
public class OPSSelectSubProductServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
	HttpClient client = new HttpClient();
   
        private Logger logger = LoggerFactory.getLogger(OPSSelectSubProductServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	
        	// fetch all the parameters
        	String ldPurposeCategory = request.getParameter("ldPurposeCategory");
        	String ldProductCode = request.getParameter("ldProductCode");
        	
        	GetMethod method = null;
        	
        	logger.info("VALUE of ldPurposeCategory" + ldPurposeCategory);
        	logger.info("VALUE of ldProductCode" + ldProductCode);
        	
        	
        	String getServiceURL = OPSConstants.WS_GET_SUBPRODUCT_CODE_URL.replace("ldProductCode", ldProductCode).replace("ldPurposeCategory", ldPurposeCategory);
        	// create WS connection
        	method = new GetMethod(getServiceURL);
        	
        	logger.info("REDAER LOG++++++++++++++++++++++++" + getServiceURL);
        	
        	// Execute the method.
			int statusCode = client.executeMethod(method);
			
			InputStream inputStream = method.getResponseBodyAsStream();
		/*	DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder;
			Document doc = null;
			
				try {
					docBuilder = docBuilderFactory.newDocumentBuilder();
					doc = docBuilder.parse(inputStream);
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
        	
        	
	    	URL URLobj = new URL(getServiceURL);
			URLConnection connection = URLobj.openConnection();
			
	    	String charset = "UTF-8"; 
	    	  connection.setDoOutput(true); // Triggers POST.
	    	  connection.setRequestProperty("Accept-Charset", charset);
	    	  connection.setRequestProperty("Content-Type", "application/json;charset=" + charset);
	    	 */
	    	 

	  		
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(inputStream));
			String inputLine;
			StringBuffer newresponse = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				newresponse.append(inputLine);
			}
			in.close();
			
		

	String toReturnResults = null;
	toReturnResults = newresponse.toString();
	logger.info("REDAER LOG++++++++++++++++++++++++" + toReturnResults);

	response.setContentType("application/json");
    response.getWriter().write(toReturnResults.toString());
	//response.getWriter().write(toReturnResults);
        	
        	
        	
        	
        
        	////////////////////////////////////
       /* response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
        	String ILS[] = {"2=Home Loan Variable",
                    "3=Home Loan Fixed",
                    "4=Simplicity Plus HL",
                    "5=Inv Res Variable",
                    "6=Res Inv Loan Fixed",
                    "7=Simplicity Plus RIL",
                    "8=Res Inv Land Loan",
                    "9=Res Land Loan"
					};
            String DDA[] = {"1=Equity Manager"
                    };
			

			
			String ldProductCodeGroup = request.getParameter("ldProductCode");
			int ldProductCodeGroupValue = Integer.parseInt(ldProductCodeGroup);
			
			JSONArray ldSubProductCodeJsonArray = new JSONArray();
			
            if (ldProductCodeGroupValue == 1) {
            	ldSubProductCodeJsonArray = new JSONArray();
                for (String ldSubProductCode : ILS) {
                	ldSubProductCodeJsonArray.put(ldSubProductCode);
                }
			} else if (ldProductCodeGroupValue == 2) {
				ldSubProductCodeJsonArray = new JSONArray();
                for (String ldSubProductCode : DDA) {
                	ldSubProductCodeJsonArray.put(ldSubProductCode);
                }
			}
                response.setContentType("application/json");
                response.getWriter().write(ldSubProductCodeJsonArray.toString());
             
        } catch ( Exception e) {
            logger.error(e.getMessage(), e);
        }*/

    }

    }

