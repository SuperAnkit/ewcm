package ops.global.core.servlets;



import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SlingServlet(paths = "/bin/DDpurpose",
        methods = {"POST"},
        metatype = true)
public class OPSSelectPurposeServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
   
        private Logger logger = LoggerFactory.getLogger(OPSSelectPurposeServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
        	String Bridging[] = {"0=Const Not 1st Home",
                    "1=Const 1st home",
                    "2=Const Other 1st home",
                    "3=Const Other not 1st",
                    "4=Pur Estab 1st Home",
                    "5=Pur Estab Not 1st",
                    "6=Pur New 1st Home",
                    "7=Pur New Home Not 1st",
                    "8=PurEstab Oth 1st",
                    "9=PurEstab Oth Not 1st",
                    "10=PurNewOth 1st home",
                    "11=PurNewOth Not 1st"
					};
            String Construction[] = {"0=Const Not 1st Home",
                    "1=Const 1st home",
                    "2=Const Other 1st home",
					"3=Const Other not 1st",
					"4=DwellingsRent Resale"
                    };
			String Home_Improvement[] = {"0=DwellingsRent Resale",
                    "1=Home Imprv Alter Add"
					};
			String Pur_Estab_Dwellings[] = {"0=DwellingsRent Resale",
                    "1=Pur Estab 1st Home",
                    "2=Pur Estab Not 1st",
					"3=PurEstab Oth 1st",
					"4=PurEstab Oth Not 1st"
					};
			String Pur_of_New_Dwellings[] = {"0=DwellingsRent Resale",
                    "1=Pur New 1st Home",
                    "2=Pur New Home Not 1st",
                    "3=PurNewOth 1st home",
                    "4=PurNewOth Not 1st"}; 
			String Purchase_of_Land[] = {"0=DwellingsRent Resale",
                    "1=Residential Land"}; 
			String Re_finanace[] = {"0=DwellingsRent Resale",
                    "1=Refi ANZ Home Loan",
                    "2=Refi Exist ANZ Loans",
                    "3=Refi Non ANZ Home Ln",
                    "4=Refi Non ANZ Inv Ln"
                    }; 
			String Supplementary[] = {"0=Boats Caravans",
                    "1=Debt Cons Non ANZ Ln",
                    "2=DwellingsRent Resale",
                    "3=Home Imprv Alter Add",
                    "4=Household/Personal",
                    "5=Motor Cycles",
                    "6=New Motor Car",
					"7=Other Investments",
					"8=Other Motor Vehicle",
					"9=Refi ANZ Home Loan",
					"10=Refi Exist ANZ Loans",
					"11=Refi Non ANZ Home Ln",
					"12=Travel Holidays",
					"13=Used Motor Car"
					};

			
			String ldPurposeCategoryGroup = request.getParameter("ldPurposeCategory");
			int ldPurposeCategoryGroupValue = Integer.parseInt(ldPurposeCategoryGroup);
			
			JSONArray ldPurposeCategoryJsonArray = new JSONArray();
			
            if (ldPurposeCategoryGroupValue == 0) {
            	ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Construction) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
            } else if (ldPurposeCategoryGroupValue == 1) {
            	ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Pur_Estab_Dwellings) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 2) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Pur_of_New_Dwellings) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 3) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Purchase_of_Land) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 4) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Home_Improvement) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 5) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Re_finanace) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 6) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Supplementary) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 10) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Bridging) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} 
            
                response.setContentType("application/json");
                response.getWriter().write(ldPurposeCategoryJsonArray.toString());
             
        } catch ( Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    }

