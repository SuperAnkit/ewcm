function isValidDate(subject, isMandatory) {

	if (isMandatory == true) {

		if (subject.match(/(^(((0?[1-9]|1[0-9]|2[0-8])[-](0?[1-9]|1[012]))|((29|30|31)[-](0?[13578]|1[02]))|((29|30)[-](0?[4,6,9]|11)))[-](19|0?[2-9][0-9])\d\d$)|(^29[-]0?2[-](19|0?[2-9]0?[0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/)) {
			return true;
		} else {
			return false;
		}


	} else {
		if (subject == null || subject.match(/(^(((0?[1-9]|1[0-9]|2[0-8])[-](0?[1-9]|1[012]))|((29|30|31)[-](0?[13578]|1[02]))|((29|30)[-](0?[4,6,9]|11)))[-](19|0?[2-9][0-9])\d\d$)|(^29[-]0?2[-](19|0?[2-9]0?[0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/)) {
			return true;
		} else {
			return false;
		}
	}
}

function isValidDateTime(subject, isMandatory) {
	if (isMandatory == true) {

		if (subject.match(/^([0-2]\d|3[0-1])\-(0\d|1[0-2])\-(19|20)\d{2} (([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$/)) {
			return true;
		} else {
			return false;
		}


	} else {
		if (subject == null || subject.match(/^([0-2]\d|3[0-1])\-(0\d|1[0-2])\-(19|20)\d{2} (([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])(:([0-5]?[0-9]))?$/)) {
			return true;
		} else {
			return false;
		}
	}

}




function isMOSValid(subject, isMandatory) {

	if (isMandatory == true) {

		if (subject.match(/^(0?[0-9]|[1-9][0-9])$/)) {
			return true;
		} else {
			return false;
		}


	} else {
		if (subject == null || subject.match(/^(0?[0-9]|[1-9][0-9])$/)) {
			return true;
		} else {
			return false;
		}
	}

}

function isTaDValid(subject, isMandatory, yearOrMonthVal) {

    //alert("year and month values"+subject +"---" +yearOrMonthVal);

    if(subject != 00 || subject != 0 && yearOrMonthVal != 00 || yearOrMonthVal != 0){

		if (isMandatory == true) {

		if (subject.match(/^(0?[0-9]|[1-9][0-9])$/)) {
			return true;
		} else {
			return false;
		}


	} else {
		if (subject == null || subject.match(/^(0?[0-9]|[1-9][0-9])$/)) {
			return true;
		} else {
			return false;
		}
	}

    }
    else{
		return false;
    }
}



function isBSBValid(subject, isMandatory) {
	if (isMandatory == true) {

		if (subject.match(/^(?=.*[0-9])[- 0-9]+$/)) {
			return true;
		} else {
			return false;
		}


	} else {
		if (subject == null || subject.match(/^(?=.*[0-9])[- 0-9]+$/)) {
			return true;
		} else {
			return false;
		}
	}

}

/* Function for validatiing email */

function isValidateEmail(email, isMandatory) {

	if (isMandatory == true) {

		if (email.match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)) {
			return true;
		} else {
			return false;
		}


	} else {
		if (email == null || email.match(/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/)) {
			return true;
		} else {
			return false;
		}
	}
}


/*Function for checking empty text boxex */

function isEmptyText(text, isMandatory) {

	if (isMandatory == true) {

		if (text.match !== 0) {
			return true;
		} else {
			return false;
		}


	} else {
		if (text == null || text.length !== 0) {
			return true;
		} else {
			return false;
		}
	}
}

// function for validating mandatory radio buttons

function isRadioValid(radioVal) {
	if (radioVal != null) {
		return true;
	} else {
		return false;
	}
}

// function for validating mandatory dropdown fields

function isDropDownValid(dropdownVal) {
	if (dropdownVal != null) {
		return true;
	} else {
		return false;
	}
}

// function to validate credit limit for account section
function creditLimitvalidate(subject, aCardCategory, aCardType, isMandatory) {
	if ((aCardType == 'New Credit Card Account Required') || (aCardType == 'Upgrade of Existing Card')) {
		if (isMandatory == 'true') {
			if (aCardCategory != 'ANZ Rewards Black') {
				if (subject >= 6000) {
					return true;
				} else {
					return false;
				}
			} else {
				if (subject >= 15000) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			if (subject == null) {
				return true;
			} else {
				if (aCardCategory != 'ANZ Rewards Black') {
					if (subject >= 6000) {
						return true;
					} else {
						return false;
					}
				} else {
					if (subject >= 15000) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
	} else return true;
}

function makeMandatory(currentElement, valueFromElement) {
	console.log("makeMandootry============>>>>>>>>>>>>" + valueFromElement.value);
	if (valueFromElement.value != null) {
		$("#" + currentElement.id).parent().parent().addClass("mandatory-fld");
		currentElement.mandatory = true;
		console.log("TRUE============>>>>>>>>>>>>");
		$("#" + currentElement.id).attr("aria-required", true);

		//return false;

	} else {
		console.log("FALSE============>>>>>>>>>>>>");
		currentElement.mandatory = true;
		$("#" + currentElement.id).parent().parent().removeClass("mandatory-fld");
		$("#" + currentElement.id).removeAttr("aria-required", true);
		// return true;
	}
}

// check for conditional mandatory




function checkMand(subject, conditionValue) {
	alert(conditionValue);
	if (conditionValue != '') {
		var yy = window.guideBridge.resolveNode("guide[0].guide1[0].guideRootPanel[0].broker[0].subpanel-broker[0].bCPID[0]");
		alert(yy);
		yy.mandatory = true;
		/*
        alert("Inside IF");
        $("#"+subject.id).addClass('mandatory-fld');

          $("#"+subject.id).attr('required','true');

         $("#"+subject.id).attr('aria-required','true');
*/

	} else {
		$("#" + subject.id).removeClass('mandatory-fld')
	}
	alert(subject.id);
	alert(subject.name);

	//subject.addClass(mandatory-fld);

}


// Validation for Account numbers
function isValidAccountNumber(subject, isMandatory) {

	if (isMandatory == true) {

		if (subject.match(/^[A-Za-z0-9.-]{4,20}$/)) {
			return true;
		} else {
			return false;
		}


	} else {
		if (subject == null || subject.match(/^[A-Za-z0-9.-]{4,20}$/)) {
			return true;
		} else {
			return false;
		}
	}
}

// Validation for OFI Account numbers
function isValidOFIAccountNumber(subject, isMandatory) {

	if (isMandatory == true) {

		if (subject.match(/^[A-Za-z0-9.-]{4,20}$/)) {
			return true;
		} else {
			return false;
		}


	} else {
		if (subject == null || subject.match(/^[A-Za-z0-9.-]{4,20}$/)) {
			return true;
		} else {
			return false;
		}
	}
}

// Function to get the dropdown value

/*function getDDValue(subject){
	var ddElement = document.getElementById(document.getElementById(subject.id).children[1].lastElementChild.id);
	var ddValue =  ddElement.options[ddElement.selectedIndex].text
	//alert(ddValue);
	return ddValue;
}*/

function getSecurityPanelName(Address1,Address2,city,state,postCode)
{
    var ConcatinateAddress;
	if(Address1 == null && Address2 == null && city == null && postCode == null && state.value == null){
	return "Security";
    }
    else{
        var stateValue = $("#"+state.id+" :selected").text();
        if (Address1==null)
            Address1="";
        if (Address2==null)
            Address2="";
        if (city==null)
            city="";
        if (postCode==null)
            postCode="";

		ConcatinateAddress =Address1 + " " + Address2 + " " +city + " " + stateValue + " " + postCode;

        return ConcatinateAddress;
    }

}



function getFormattedNames(Title, firstName, middleName, lastName, shortName, FormalSaluation, mailTitle) {

    var fname="";
    var mname="";
    var lname="";
    var salutation;

    if (firstName==null && middleName==null && lastName==null) {
		shortName.value="";
		mailTitle.value="";
		FormalSaluation.value="";
        return true;
    }

    if (firstName!=null && firstName!="") 
        fname = firstName.charAt(0);
    else
        fname = "";

    if (middleName!=null) 
        mname = middleName.charAt(0);
    else
        mname = "";

    if (lastName!=null) {
        lname = lastName.charAt(0);
    } else {
        lname = "";
        lastName = "";
    }

	shortName.value=lastName + " " + fname + " " + mname;
	mailTitle.value=Title + " " + fname + " " + mname + " " + lastName;
	FormalSaluation.value = Title + " " + lastName;
}

// function to display names on Applicant Panel

function displayApplicantPanelName(applFirstName, applMiddleName, applLastName, companyName, isIndividual){

    if(isIndividual == '1'){

        if(applFirstName == null && applMiddleName == null && applLastName == null){
			return "Applicant";
			}
    	else{

			if (applFirstName==null)
            applFirstName="";
        	if (applMiddleName==null)
            applMiddleName="";
        	if (applLastName==null)
            applLastName="";

   			var panelDisplayName = applFirstName + " " + applMiddleName + " "+ applLastName;
			panelDisplayName = panelDisplayName.replace("null", "");
			return panelDisplayName;

		}
    }

    else{
		 if(companyName == null){
			return "Applicant";
			}
		else{
			if (companyName==null)
            companyName="";

			var panelDisplayName = companyName;
			panelDisplayName = panelDisplayName.replace("null", "");
			return panelDisplayName;
        }
    }
}


// Function to select 'Select' as default for dropdowns
function makeSelectasDefault(subject)
{
    console.log("SELECT TEST: " + subject.id)
    console.log("SELECT TEST:-" + subject.value + "----")
    if(subject.value == null ){
        console.log("INSIDE IF");
        return -1;
    }
    else{
        console.log("INSIDE ELSE");
		return subject.value;
    }

}


// Function to flush mandatory fields for checker for first time only
function flushMandatoryForChecker(mandatoryFlushCounter, subject, type, isMandatory) {
    console.log('M V ID:' + subject.value);
    var formUserGroup = $("#user_group").val();
    if(mandatoryFlushCounter == 0 && formUserGroup == 'checker_group'){
        if(type == 'textbox' || type == 'radio' || type == 'checkbox'){
        return '';
        }
        if(type == 'dropdown'){
         if(isMandatory == 'true'){
                return '';
            }
            else{
                return -1;
            }
        }

    }
    else{
		return subject.value;
        }

}

function getEmploymentPanelSummaryName(applicantName,employmentCategory)
{
    //alert("In Function");
    var employmentDetails;
	
	if(applicantName.value == null && employmentCategory == null){
	return "Employment";
    }
    else{

        var applicantNameValue = $("#"+applicantName.id+" :selected").text();
        if (applicantName==null)
            applicantName="";


        if (employmentCategory==null)
        {
            employmentCategory="";
        }

       else if(employmentCategory=="primary")
            {
            employmentCategory="Primary";
            }
       else if(employmentCategory=="additional")
            {
            employmentCategory="Additional";
            }
       else if(employmentCategory=="previous")
            {
            employmentCategory="Previous";
            }

        employmentDetails = (employmentCategory + " Employment for  " + applicantNameValue).replace("null","").replace("null","");
        return employmentDetails;
    }

}

// function to check only one spouse is selected
var spusex = 0;
function validateApplicantSpouse(spouseEle){

	var s = document.getElementsByTagName('select');
    for(var i=0;i<s.length;i++) {
		console.log("Spouse dd length"+s.length);
        if(s[i].getAttribute('aria-label') == 'Relationship Type'){

			console.log("Printing inside relationship Type");
			console.log("SPOUSE VALIDATION IF LOOP"+s[i].options[s[i].selectedIndex].text +"===="+getConditionalDropDownText(spouseEle));
            if(spusex == 0){
				spusex = spusex + 1;
				return true;

            }else{
 			if(s[i].options[s[i].selectedIndex].text == getConditionalDropDownText(spouseEle) 
                && getConditionalDropDownText(spouseEle) == 'Spouse'){
				return false;
            }else{return true;}

            }


		//alert('Inside spouse element');
        }
      // s[i].setAttribute('disabled',true);
    }
}

// function for validate date time and future date


function validateBrokerDateTime(subject) {
	subject = subject.trim();
	var dateTimeFormat = /^([1-9]|([012][0-9])|(3[01]))[/]([0]{0,1}[1-9]|1[012])[/]\d\d\d\d ([012]{0,1}[0-9]:[0-5][0-9]{0,1}) (([AP]M)|([ap]m))$/;    
    var enteredDate = subject.split(" ")[0];
    var enteredTime = subject.split(" ")[1];

    if (subject!=null) {

        if (isFutureDate(enteredDate)) {
            //alert("please do not enter future date");
            return false;
        }
        if(subject.match(dateTimeFormat)){
			return true;
        }else{
			return false;
        }
    }
}

function isFutureDate(idate){
    var today = new Date().getTime(),
        idate = idate.split("/");

    idate = new Date(idate[2], idate[1] - 1, idate[0]).getTime();
    return (today - idate) < 0 ? true : false;
}


//Function to copy data in MakerOnly attribute and alert checker if value is different
function checkerValidation(subject, makerElement) {
    var formUserGroup = $("#user_group").val();
    if(formUserGroup == 'maker_group'){
       return subject.value; 

    }
    else if(formUserGroup == 'checker_group'){
        if (subject.value!="" && subject.value !=null && subject.value !='null' && subject.value !=undefined) {
            if (subject.value != makerElement) {
                var response = confirm('Caution: Value entered by you '+subject.value+' does not match '+makerElement+'. Pls confirm if you want to overwrite?');
                if (response == false) {
                    subject.value = makerElement;
                //txt = "You pressed OK!";
                    } 
                return makerElement;
            } 
            else {
                return makerElement;
            }
        } else {
			return makerElement;
        }
    }
    else{
    	return makerElement;
    }

}


//Function for validating postcode
function validatePostCode(city, state, postcode, applicantCust, pOverseasAddress, isMandatory){
    console.log("state: " + state.value);
    console.log("postcode: " + postcode.value);
    console.log("city: " + city.value);
	if(applicantCust=='1'){
		

		
		if (isMandatory == 'true') {
			if (pOverseasAddress=='true') {
				if(postcode.value.match(/^[0-9]{4,10}$/)){	

					return true;
				}else { // else for if(postcode.value.match(/^[0-9]{4,10}$/)

					return false;
				}
			}
			else{

				if((state.value == null) || (city.value == null) || (state.value == 'null') || (city.value == 'null')){ // check if city or state is null
					return false;
					}
					else{ // else for city and state is null
					if(postcode.value.match(/^[0-9]{4,10}$/)){	
						// make ajax call to validate postcode
                        var status = false;
			            var submitData = "postcode=" + postcode.value + "&state=" + state.value + "&city=" + city.value ;
			            $.ajax({
			                  url: '/bin/validatePostcode',
			                  async:false,
			                  data: submitData,
			                  error: function() {
			                     $('#info').html('<p>An error has occurred</p>');
			                  },
			                  dataType: 'text',
			                  success: function(data) {

			                    console.log("code++" + data + "++");
                                  status = data;


			                  },
			                  type: 'POST'
			             });
                        if(status == "true"){
                            console.log("am in true");
                            return true;
                        }
                        else{
                            console.log('FALSE');
                            return false;
                        }
					}else { // else for if(postcode.value.match(/^[0-9]{4,10}$/)
						return false;
					}
					}		
			
			}
		} 
		else { // else for if (isMandatory == 'true')
			if (pOverseasAddress=='true') {
				if(postcode.value.match(/^[0-9]{4,10}$/)){	
					return true;
				}else { // else for if(postcode.value.match(/^[0-9]{4,10}$/)
					return false;
				}
			}
			else{
				if (postcode.value == '' || postcode.value== 'null' || postcode.value== null) {
					return true;
				} else { // else for checking if postcode is valid
					if((state.value == null) || (city.value == null) || (state.value == 'null') || (city.value == 'null')){ // check if city or state is null
						return false;
					}
						else{ // else for city and state is null
						if(postcode.value.match(/^[0-9]{4,10}$/)){	
							// make ajax call to validate postcode
                        var status = false;
			            var submitData = "postcode=" + postcode.value + "&state=" + state.value + "&city=" + city.value ;
			            $.ajax({
			                  url: '/bin/validatePostcode',
			                  async:false,
			                  data: submitData,
			                  error: function() {
			                     $('#info').html('<p>An error has occurred</p>');
			                  },
			                  dataType: 'text',
			                  success: function(data) {

			                    console.log("code++" + data + "++");
                                  status = data;


			                  },
			                  type: 'POST'
			             });
                        if(status == "true"){
                            console.log("am in true");
                            return true;
                        }
                        else{
                            console.log('FALSE');
                            return false;
                        }
						}else { // else for if(postcode.value.match(/^[0-9]{4,10}$/)
							return false;
						}
						}
					}

			}
		}
    }
		else{ // else for if(applicantCust.value=='1')
            console.log("outttt");
			return true;
			}
}