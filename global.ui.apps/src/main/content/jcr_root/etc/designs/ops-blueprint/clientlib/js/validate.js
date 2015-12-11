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

function isTaDValid(subject, isMandatory) {

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

function creditLimitvalidate(subject, aCardCategory, isMandatory) {
	if (isMandatory == true) {
		if (aCardCategory == 'ANZ Rewards Black') {
			(subject > 6000) ? true : false
		} else {
			(subject > 15000) ? true : false
		};
	} else {
		if (subject == null) {
			return true;
		} else {
			if (aCardCategory == 'ANZ Rewards Black') {
				(subject > 6000) ? true : false
			} else {
				(subject > 15000) ? true : false
			};
		}
	}
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

		if (subject.match(/^[A-Za-z0-9.-]{10,20}$/)) {
			return true;
		} else {
			return false;
		}


	} else {
		if (subject == null || subject.match(/^[A-Za-z0-9.-]{10,20}$/)) {
			return true;
		} else {
			return false;
		}
	}
}

// Function to get the dropdown value

function getDDValue(subject){
	var ddElement = document.getElementById(document.getElementById(subject.id).children[1].lastElementChild.id);
	var ddValue =  ddElement.options[ddElement.selectedIndex].text
	alert(ddValue);
	//return ddValue;
}

// Function to get the dropdown value

function getStateFromValue(securityState){
	var ddElement = document.getElementById(document.getElementById(securityState.id).children[1].lastElementChild.id);
	var ddValue =  ddElement.options[ddElement.selectedIndex].text

	return ddValue;
}

// Function to get the Panel Name for Security Tab
function getSecurityPannelName(Address1,Address2,city,state,postCode)
{
    state = getStateFromValue(state);
    var ConcatinateAddress;
	if(Address1 == null && Address2 == null && city == null && postCode == null){
		return "Security";
    }
    else{

 		ConcatinateAddress =(Address1 + " " + Address2 + " " +city + " " + state + " " + postCode).replace("null", ",").replace(",,,"," ");
		return ConcatinateAddress;
    }

}


// Function to get the Formatted Names for Applicant Tab
function getFormattedNames(Title, firstName, middleName, lastName, shortName, FormalSaluation, mailTitle)
{
var ApplicantShortName;
var ApplicantFormalName;
var ApplicantMailingTitle;

    if(firstName && middleName && lastName == null)
{
shortName.value=" ";
FormalSaluation=" ";
mailTitle=" ";
}
    var fName=firstName.charAt(0);
     if (middleName != null && middleName !== undefined) {
        var mName=middleName.charAt(0);
   }
else{
    var mName="";
    }
 ApplicantShortName =(lastName + " " + fName  + mName).replace("null", " ");
 shortName.value=ApplicantShortName;

  ApplicantFormalName =(Title + " " + lastName ).replace("null", " ");
FormalSaluation.value=ApplicantFormalName;

if (firstName != null && firstName !== undefined ) {
        var fName=firstName.charAt(0);
}else
    {
    var fName="";
    }
     if (middleName != null && middleName !== undefined ) {
        var mName=middleName.charAt(0);
   }
else{
    var mName="";
    }
 ApplicantMailingTitle =(Title + " " + fName + " " + mName + " "  + lastName).replace("null", " ");

mailTitle.value = ApplicantMailingTitle;
}


// Function to select 'Select' as default for dropdowns
function makeSelectasDefault(subject)
{
    alert('VAL: ' + '#' + subject.id + ' select');
    $('#' + subject.id + ' select').val(-1);

}






