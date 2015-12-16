//global variables used for Conditional Mandatory

var defaultCssName = "mandatory-fld";
var defaultMandatoryAemParam = "aria-required";

var _zeroDependentsForApplicant = '0';
var _statDecTrue = 'true';
var _firstLoanConditionValue = 'Refi Non ANZ Inv Ln';
var _secondLoanConditionValue = 'Refi Non ANZ Home Ln';
var _propStatusConditionValue = 'To be Built';
var _firstLoanCategoryConditionValue = 'Construction';
var _secondLoanCategoryConditionValue = 'Pur Estab Dwellings';
var _thirdLoanCategoryConditionValue = 'Pur of New Dwellings';
var _fourthLoanCategoryConditionValue = 'Purchase of Land';
var _unemployed = '6';

// NEW FUNCTIONS for Conditional Manditory Fields
// function for conditional mand -- Panel Applicant -- Number of dependents..Age of Dependent


function dependentCondMand(mandatoryElement, conditionalElement){

     if(conditionalElement.value != _zeroDependentsForApplicant){
		setMandatoryEle(mandatoryElement);

    }else{
        removeMandatoryEle(mandatoryElement);
    }
}

// function for conditional mand -- Panel Applicant -- Is Stat Dec Held


function statDecCondMand(mandatoryElement_1, mandatoryElement_2, conditionalElement){

     if(conditionalElement.value == _statDecTrue){

		setMandatoryEle(mandatoryElement_1);
        setMandatoryEle(mandatoryElement_2);

    }else{

        removeMandatoryEle(mandatoryElement_1);
        removeMandatoryEle(mandatoryElement_2);

    }
}

// function for conditional mand -- Panel Loan and OFI -- For all Refi Non-ANZ Inv Loan & Non-ANZ Home Loan

function refiCondMand(mandatoryElement_1, mandatoryElement_2, conditionalElement){

	var ddConditionalTextValue = getConditionalDropDownText(conditionalElement);

    if( ddConditionalTextValue == _firstLoanConditionValue || 
       						ddConditionalTextValue == _secondLoanConditionValue){

		setMandatoryEle(mandatoryElement_1);
        setMandatoryEle(mandatoryElement_2);

    }else{

		removeMandatoryEle(mandatoryElement_1);
        removeMandatoryEle(mandatoryElement_2);

    }
}


// function for conditional mand -- Panel Security -- If Property Status is To Be built


function propStatusCondMand(mandatoryElement_1, mandatoryElement_2, conditionalElement){

	var ddConditionalTextValue = getConditionalDropDownText(conditionalElement);

    if(ddConditionalTextValue == _propStatusConditionValue){

		setMandatoryEle(mandatoryElement_1);
        setMandatoryEle(mandatoryElement_2);

    }else{

        removeMandatoryEle(mandatoryElement_1);
        removeMandatoryEle(mandatoryElement_2);

    }
}

// funtion for conditional mand - Solicitator Name on Loan Details - Purpose Cat/ purpose change


function soliNameCondMand(mandatoryElement_1, conditionalElement){

    var ddConditionalTextValue = getConditionalDropDownText(conditionalElement);

    if( ddConditionalTextValue == _firstLoanCategoryConditionValue || 
       						ddConditionalTextValue == _secondLoanCategoryConditionValue || 
       						ddConditionalTextValue == _thirdLoanCategoryConditionValue || 
       						ddConditionalTextValue == _fourthLoanCategoryConditionValue){
		setMandatoryEle(mandatoryElement_1);

    }else{
        removeMandatoryEle(mandatoryElement_1);
    }
}

// function for Employemnt Conditional mandatory changes

function unemployedCondMand(eOccupationalGroup,eOccupational,eGrossMonthly,eNetMonthly,timeEmploymentYears,timeEmploymentMonths,eEmploymentType,eProbationaryPeriod){

     if(eEmploymentType.value != _unemployed){
        setMandatoryEle(eOccupationalGroup);
        setMandatoryEle(eOccupational);
        setMandatoryEle(eGrossMonthly);
        setMandatoryEle(eNetMonthly);
        setMandatoryEle(timeEmploymentYears);
        setMandatoryEle(timeEmploymentMonths);
        setMandatoryEle(eProbationaryPeriod); 
    }else{
        removeMandatoryEle(eOccupationalGroup);
        removeMandatoryEle(eOccupational);
        removeMandatoryEle(eGrossMonthly);
        removeMandatoryEle(eNetMonthly);
        removeMandatoryEle(timeEmploymentYears);
        removeMandatoryEle(timeEmploymentMonths);
        removeMandatoryEle(eProbationaryPeriod); 
    }
}

// functions for adding/removing mandatory validation to the elements

function setMandatoryEle(ele){

		$("#"+ele.id).parent().parent().addClass(defaultCssName);
        ele.mandatory = true;
		$("#"+ele.id).attr(defaultMandatoryAemParam,true);


}

function removeMandatoryEle(ele){

  		ele.mandatory = false;
        $("#"+ele.id).parent().parent().removeClass(defaultCssName);
        $("#"+ele.id).removeAttr(defaultMandatoryAemParam,true);

}

// function for getting selected Dropdown text value

function getConditionalDropDownText(ele){
		return $("#"+ele.id+" :selected").text();
}