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
	private static int eOccupationalGroupValue;
   
        private Logger logger = LoggerFactory.getLogger(OPSSelectOccupationalServlet.class);
     
        protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException,
            IOException {
    	
        	
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
        	String Unknown[] = {"1=Unknown"
                    };
            String Professional[] = {"44=Accountant(other)",
                    "43=Accountant-chartered",
                    "45=Architect",
                    "46=Dentist",
                    "47=Doctor,specialist",
                    "48=Engineer",
                    "49=Financial controller",
                    "51=Journalist",
                    "50=Lawyer",
                    "52=Nurse",
                    "57=Other professional",
                    "53=Pharmacist",
                    "54=solicitor",
                    "56=special exec consult",
                    "55=stockbroker"};
            String Agricultural[] = {"120=Farm Labourer",
                    "119=Farm Manager",
                    "118=Farm Owner"
                    };
			String General[] = {"123=Homemaker/domestic",
                    "122=Military/armed forces",
                    "126=Other",
                    "121=Police officer",
                    "125=Retired",
                    "128=Small bus. Proprietr",
                    "124=Student",
                    "127=Unemployed"};
			String Management[] = {"130=Lower levl mgr/admin",
                    "66=middle level mgr/adm",
                    "65=senior level mgr/adm"};
			String Office_Workers[] = {"72=Acct/payroll clerk",
                    "73=Bank teller",
                    "74=Bookkeeper",
                    "75=Cashier",
                    "76=Key punch operator",
                    "129=Coral Sea Islands",
                    "77=Office clerk",
                    "80=Other clerical",
                    "79=Postal service workr",
                    "78=Receptn/phone opr",
                    "71=secretary"}; 
			String Prof_Skill_Trades[] = {"97=Baker",
                    "88=Bricklayer",
                    "98=Butcher",
                    "89=carpenter",
                    "90=Electrician",
                    "87=Foreman",
                    "91=Jeweller",
                    "92=Mechanic",
                    "99=Other trades person",
                    "93=painter",
                    "94=plasterer",
                    "95=plumber",
                    "96=tailor"}; 
			String Sales[] = {"85=Checkout operator",
                    "81=Insurance brokr/agnt",
                    "86=other sales",
                    "82=real estate agnt/mgr",
                    "84=sales assistant",
                    "83=sales representative"}; 
			String Semi_Professional[] = {"64=Other teacher",
                    "58=preschool teacher",
                    "59=primary school teacher",
                    "63=remedial/other teacher",
                    "60=secondary teacher",
                    "61=TAFE teacher",
                    "62=tertiary teacher"};
			String Service[] = {"110=bar attendant",
			"106=beautician",
			"115=bus driver",
			"116=delivery or courier",
			"109=dental assistant",
			"107=hairdresser",
			"108=nursing aide",
			"117=other service worker",
			"113=porter",
			"114=taxi driver",
			"112=usher",
			"111=waiter"
			};
			String Technical[] = {"68=computer programmer",
			"69=data analyst/research",
			"70=other technical"
			};
			String Unskilled_Trades[] = {"103=Builder's labourer",
			"101=factory worker",
			"105=other worker/laborer",
			"100=trades person's asst",
			"104=truck driver",
			"102=warehouseman"
			};
			
			String eOccupationalGroup = request.getParameter("eOccupationalGroup");
			if (eOccupationalGroup.length() > 0) {
				eOccupationalGroupValue = Integer.parseInt(eOccupationalGroup);
			} else {
				eOccupationalGroupValue = 1001;
			}
			
			
            JSONArray eoccupationalJsonArray = new JSONArray();
                if (eOccupationalGroupValue == 1) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Agricultural) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 2) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : General) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 3) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Management) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 4) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Office_Workers) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 5) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Prof_Skill_Trades) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 6) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Professional) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 7) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Sales) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 8) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Semi_Professional) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 9) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Service) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
				} else if (eOccupationalGroupValue == 10) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Technical) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
                } else if (eOccupationalGroupValue == 11) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Unskilled_Trades) {
                    	eoccupationalJsonArray.put(eoccupational);
                    }
                } else if (eOccupationalGroupValue == 12) {
                    eoccupationalJsonArray = new JSONArray();
                    for (String eoccupational : Unknown) {
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

