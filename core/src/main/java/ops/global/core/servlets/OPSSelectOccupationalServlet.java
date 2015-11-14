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


@SlingServlet(paths = "/bin/DDoccupational",
        methods = {"POST"},
        metatype = true)
public class OPSSelectOccupationalServlet extends SlingAllMethodsServlet {
    
	private static final long serialVersionUID = 1L;  
   
        private Logger logger = LoggerFactory.getLogger(OPSSelectOccupationalServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String Professional[] = {"0=Accountant(other)",
                    "1=Accountant-chartered",
                    "2=architect",
                    "3=Dentist",
                    "4=Doctor,specialist",
                    "5=Engineer",
                    "6=Financial controller",
                    "7=Journalist",
                    "8=Lawyer",
                    "9=Nurse",
                    "10=Other professional",
                    "11=pharmacist",
                    "12=solicitor",
                    "13=special exec consult",
                    "14=stockbroker"};
            String Agricultural[] = {"0=Farm Labourer",
                    "1=Farm Labourer",
                    "2=Farm Owner"
                    };
			String General[] = {"0=Homemaker/domestic",
                    "1=Military/armed forces",
                    "2=Other",
                    "3=Police officer",
                    "4=Retired",
                    "5=Small bus. Proprietr",
                    "6=Student",
                    "7=Unemployed"};
			String Management[] = {"0=ALower levl mgr/admin",
                    "1=middle level mgr/adm",
                    "2=senior level mgr/adm"};
			String Office_Workers[] = {"0=Acct/payroll clerk",
                    "1=Bank teller",
                    "2=Bookkeeper",
                    "3=Cashier",
                    "4=Key punch operator",
                    "5=Coral Sea Islands",
                    "6=Office clerk",
                    "7=Other clerical",
                    "8=Postal service workr",
                    "9=Receptn/phone opr",
                    "10=secretary"}; 
			String Prof_Skill_Trades[] = {"0=Baker",
                    "1=Bricklayer",
                    "2=Butcher",
                    "3=carpenter",
                    "4=Electrician",
                    "5=Foreman",
                    "6=Jeweller",
                    "7=Mechanic",
                    "8=Other trades person",
                    "9=painter",
                    "10=plasterer",
                    "11=plumber",
                    "12=tailor"}; 
			String Sales[] = {"0=Checkout operator",
                    "1=Insurance brokr/agnt",
                    "2=other sales",
                    "3=real estate agnt/mgr",
                    "4=sales assistant",
                    "5=sales representative"}; 
			String Semi_Professional[] = {"0=Other teacher",
                    "1=preschool teacher",
                    "2=primary school teacher",
                    "3=remedial/other teacher",
                    "4=secondary teacher",
                    "5=TAFE teacher",
                    "6=tertiary teacher"};
			String Service[] = {"0=bar attendant",
			"1=beautician",
			"2=bus  driver",
			"3=delivery or courier",
			"4=dental assistant",
			"5=hairdresser",
			"6=nursing aide",
			"7=other service worker",
			"8=porter",
			"9=taxi driver",
			"10=usher",
			"11=waiter"
			};
			String Technical[] = {"0=computer programmer",
			"1=data analyst/research",
			"2=other technical"
			};
			String Unskilled_Trades[] = {"0=Builder's labourer",
			"1=factory worker",
			"2=other worker/laborer",
			"3=trades person's asst",
			"4=truck driver",
			"5=warehouseman"
			};
			String eOccupationalGroup = request.getParameter("eOccupationalGroup");
			int eOccupationalGroupValue = Integer.parseInt(eOccupationalGroup);
			
            JSONArray eoccupationalJsonArray = new JSONArray();
                if (eOccupationalGroupValue == 0) {
                   eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Professional) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
                } else if (eOccupationalGroupValue == 1) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Semi_Professional) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 2) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Management) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 3) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Technical) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 4) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Office_Workers) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 5) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Sales) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 6) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Prof_Skill_Trades) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 7) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Unskilled_Trades) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 8) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Service) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 9) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : General) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 10) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Agricultural) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
                }
                response.setContentType("application/json");
                response.getWriter().write(eoccupationalJsonArray.toString());
         
 
        } catch ( Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    }

