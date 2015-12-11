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
	private static int ldPurposeCategoryGroupValue;
   
        private Logger logger = LoggerFactory.getLogger(OPSSelectPurposeServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
        	String Bridging[] = {"6=Const Not 1st Home",
                    "7=Const 1st home",
                    "9=Const Other 1st home",
                    "8=Const Other not 1st",
                    "17=Pur Estab 1st Home",
                    "16=Pur Estab Not 1st",
                    "11=Pur New 1st Home",
                    "10=Pur New Home Not 1st",
                    "15=PurEstab Oth 1st",
                    "14=PurEstab Oth Not 1st",
                    "13=PurNewOth 1st home",
                    "12=PurNewOth Not 1st"
					};
            String Construction[] = {"34=Const Not 1st Home",
                    "35=Const 1st home",
                    "37=Const Other 1st home",
					"36=Const Other not 1st",
					"38=DwellingsRent Resale"
                    };
			String Home_Improvement[] = {"39=DwellingsRent Resale",
                    "18=Home Imprv Alter Add"
					};
			String Pur_Estab_Dwellings[] = {"40=DwellingsRent Resale",
                    "42=Pur Estab 1st Home",
                    "41=Pur Estab Not 1st",
					"44=PurEstab Oth 1st",
					"43=PurEstab Oth Not 1st"
					};
			String Pur_of_New_Dwellings[] = {"49=DwellingsRent Resale",
                    "46=Pur New 1st Home",
                    "45=Pur New Home Not 1st",
                    "48=PurNewOth 1st home",
                    "47=PurNewOth Not 1st"}; 
			String Purchase_of_Land[] = {"50=DwellingsRent Resale",
                    "1=Residential Land"}; 
			String Re_finanace[] = {"2=DwellingsRent Resale",
                    "20=Refi ANZ Home Loan",
                    "5=Refi Exist ANZ Loans",
                    "19=Refi Non ANZ Home Ln",
                    "3=Refi Non ANZ Inv Ln"
                    }; 
			String Supplementary[] = {"4=Boats Caravans",
                    "21=Debt Cons Non ANZ Ln",
                    "22=DwellingsRent Resale",
                    "23=Home Imprv Alter Add",
                    "24=Household/Personal",
                    "25=Motor Cycles",
                    "26=New Motor Car",
					"27=Other Investments",
					"28=Other Motor Vehicle",
					"29=Refi ANZ Home Loan",
					"30=Refi Exist ANZ Loans",
					"31=Refi Non ANZ Home Ln",
					"32=Travel Holidays",
					"33=Used Motor Car"
					};

			
			String ldPurposeCategoryGroup = request.getParameter("ldPurposeCategory");
			
			if (ldPurposeCategoryGroup.length() > 0) {
				ldPurposeCategoryGroupValue = Integer.parseInt(ldPurposeCategoryGroup);
			} else {
				ldPurposeCategoryGroupValue = 1001;
			}
			 
			
			JSONArray ldPurposeCategoryJsonArray = new JSONArray();
			
            if (ldPurposeCategoryGroupValue == 1) {
            	ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Bridging) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 2) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Construction) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 3) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Home_Improvement) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 4) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Pur_Estab_Dwellings) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 5) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Pur_of_New_Dwellings) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 6) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Purchase_of_Land) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 7) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Re_finanace) {
                	ldPurposeCategoryJsonArray.put(ldPurposeCategory);
                }
			} else if (ldPurposeCategoryGroupValue == 8) {
				ldPurposeCategoryJsonArray = new JSONArray();
                for (String ldPurposeCategory : Supplementary) {
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

