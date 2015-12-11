package ops.global.core.servlets;



import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SlingServlet(paths = "/bin/DDreversePurpose",
        methods = {"POST"},
        metatype = true)
public class OPSReverseSelectPurposeServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
	private static String ldPurposeValue;
	private static int ldPurposeKeyValue;
   
        private Logger logger = LoggerFactory.getLogger(OPSReverseSelectPurposeServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        HashMap<Integer, String> valueMap = new HashMap<Integer, String>();
        
        valueMap.put(6,"Const Not 1st Home");
        valueMap.put(7,"Const 1st home");
        valueMap.put(9,"Const Other 1st home");
        valueMap.put(8,"Const Other not 1st");
        valueMap.put(17,"Pur Estab 1st Home");
        valueMap.put(16,"Pur Estab Not 1st");
        valueMap.put(11,"Pur New 1st Home");
        valueMap.put(10,"Pur New Home Not 1st");
        valueMap.put(15,"PurEstab Oth 1st");
        valueMap.put(14,"PurEstab Oth Not 1st");
        valueMap.put(13,"PurNewOth 1st home");
        valueMap.put(12,"PurNewOth Not 1st");
        valueMap.put(34,"Const Not 1st Home");
        valueMap.put(35,"Const 1st home");
        valueMap.put(37,"Const Other 1st home");
        valueMap.put(36,"Const Other not 1st");
        valueMap.put(38,"DwellingsRent Resale");
        valueMap.put(39,"DwellingsRent Resale");
        valueMap.put(18,"Home Imprv Alter Add");
        valueMap.put(40,"DwellingsRent Resale");
        valueMap.put(42,"Pur Estab 1st Home");
        valueMap.put(41,"Pur Estab Not 1st");
        valueMap.put(44,"PurEstab Oth 1st");
        valueMap.put(43,"PurEstab Oth Not 1st");
        valueMap.put(49,"DwellingsRent Resale");
        valueMap.put(46,"Pur New 1st Home");
        valueMap.put(45,"Pur New Home Not 1st");
        valueMap.put(48,"PurNewOth 1st home");
        valueMap.put(47,"PurNewOth Not 1st");
        valueMap.put(50,"DwellingsRent Resale");
        valueMap.put(1,"Residential Land");
        valueMap.put(2,"DwellingsRent Resale");
        valueMap.put(20,"Refi ANZ Home Loan");
        valueMap.put(5,"Refi Exist ANZ Loans");
        valueMap.put(19,"Refi Non ANZ Home Ln");
        valueMap.put(3,"Refi Non ANZ Inv Ln");
        valueMap.put(4,"Boats Caravans");
        valueMap.put(21,"Debt Cons Non ANZ Ln");
        valueMap.put(22,"DwellingsRent Resale");
        valueMap.put(23,"Home Imprv Alter Add");
        valueMap.put(24,"Household/Personal");
        valueMap.put(25,"Motor Cycles");
        valueMap.put(26,"New Motor Car");
        valueMap.put(27,"Other Investments");
        valueMap.put(28,"Other Motor Vehicle");
        valueMap.put(29,"Refi ANZ Home Loan");
        valueMap.put(30,"Refi Exist ANZ Loans");
        valueMap.put(31,"Refi Non ANZ Home Ln");
        valueMap.put(32,"Travel Holidays");
        valueMap.put(33,"Used Motor Car");

        
			
			String ldPurpose = request.getParameter("ldPurpose");
			if (ldPurpose.length() > 0) {
				ldPurposeKeyValue = Integer.parseInt(ldPurpose);
			} else {
				ldPurposeKeyValue = 1001;
			}
			
			
			Iterator<Integer> keySetIterator = valueMap.keySet().iterator();
			
			JSONArray ldPurposeGroupValueJsonArray = new JSONArray();

			while(keySetIterator.hasNext()){
			  Integer key = keySetIterator.next();
			  if (key == ldPurposeKeyValue) {
				  ldPurposeValue = valueMap.get(key);
				  ldPurposeGroupValueJsonArray.put(ldPurposeValue);
				  break;
			} 
			  
			}

				logger.info("Converting value into" + ldPurposeGroupValueJsonArray.toString());
                response.setContentType("application/json");
                response.getWriter().write(ldPurposeGroupValueJsonArray.toString());
             
        } 
}
    

    

