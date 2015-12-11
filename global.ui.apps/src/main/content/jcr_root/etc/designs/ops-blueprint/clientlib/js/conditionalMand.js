// NEW FUNCTIONS for Conditional Manditory Fields
// function for conditional mand -- Panel Applicant -- Number of dependents..Age of Dependent


function dependentCondMand(mandElement, condElement){

     if(condElement.value !='0'){
		$("#"+mandElement.id).parent().parent().addClass("mandatory-fld");
        mandElement.mandatory = true;
		$("#"+mandElement.id).attr("aria-required",true);

    }else{
         mandElement.mandatory = false;
        $("#"+mandElement.id).parent().parent().removeClass("mandatory-fld");
        $("#"+mandElement.id).removeAttr("aria-required",true);
    }


}

// function for conditional mand -- Panel Applicant -- Is Stat Dec Held


function statDecCondMand(mandElement1, mandElement2, condElement){

     if(condElement.value == 'true'){
		$("#"+mandElement1.id).parent().parent().addClass("mandatory-fld");
        mandElement1.mandatory = true;
		$("#"+mandElement1.id).attr("aria-required",true);
         $("#"+mandElement2.id).parent().parent().addClass("mandatory-fld");
        mandElement2.mandatory = true;
		$("#"+mandElement2.id).attr("aria-required",true);

    }else{
         mandElement1.mandatory = false;
        $("#"+mandElement1.id).parent().parent().removeClass("mandatory-fld");
        $("#"+mandElement1.id).removeAttr("aria-required",true);
        mandElement2.mandatory = false;
        $("#"+mandElement2.id).parent().parent().removeClass("mandatory-fld");
        $("#"+mandElement2.id).removeAttr("aria-required",true);
    }


}

// function for conditional mand -- Panel Loan and OFI -- For all Refi Non-ANZ Inv Loan & Non-ANZ Home Loan

function refiCondMand(mandElement1, mandElement2, condElement){

//alert(mandElement1.id +"---" + mandElement2.id +"===="+condElement.text + "````"+ $("#"+condElement.id+" :selected").text());

    if($("#"+condElement.id+" :selected").text() == 'Refi Non ANZ Inv Ln' || 
       						$("#"+condElement.id+" :selected").text() == 'Refi Non ANZ Home Ln'){

		$("#"+mandElement1.id).parent().parent().addClass("mandatory-fld");
        mandElement1.mandatory = true;
		$("#"+mandElement1.id).attr("aria-required",true);
         $("#"+mandElement2.id).parent().parent().addClass("mandatory-fld");
        mandElement2.mandatory = true;
		$("#"+mandElement2.id).attr("aria-required",true);

    }else{

		mandElement1.mandatory = false;
        $("#"+mandElement1.id).parent().parent().removeClass("mandatory-fld");
        $("#"+mandElement1.id).removeAttr("aria-required",true);
        mandElement2.mandatory = false;
        $("#"+mandElement2.id).parent().parent().removeClass("mandatory-fld");
        $("#"+mandElement2.id).removeAttr("aria-required",true);

    }
}

// function for conditional mand -- Panel Security -- If Property Status is To Be built


function propStatusCondMand(mandElement1, mandElement2, condElement){

     if($("#"+condElement.id+" :selected").text() == 'To be Built'){
		$("#"+mandElement1.id).parent().parent().addClass("mandatory-fld");
        mandElement1.mandatory = true;
		$("#"+mandElement1.id).attr("aria-required",true);
         $("#"+mandElement2.id).parent().parent().addClass("mandatory-fld");
        mandElement2.mandatory = true;
		$("#"+mandElement2.id).attr("aria-required",true);

    }else{
         mandElement1.mandatory = false;
        $("#"+mandElement1.id).parent().parent().removeClass("mandatory-fld");
        $("#"+mandElement1.id).removeAttr("aria-required",true);
        mandElement2.mandatory = false;
        $("#"+mandElement2.id).parent().parent().removeClass("mandatory-fld");
        $("#"+mandElement2.id).removeAttr("aria-required",true);
    }
}

// OLD FUNCTIONS to check for conditional mandatory - NOT IN USE


function makeMandatory(currentElement, valueFromElement){
    console.log("makeMandootry============>>>>>>>>>>>>" + valueFromElement.value);
    if(valueFromElement.value !=null){
		$("#"+currentElement.id).parent().parent().addClass("mandatory-fld");
        currentElement.mandatory = true;
        console.log("TRUE============>>>>>>>>>>>>" );
		$("#"+currentElement.id).attr("aria-required",true);

        //return false;

    }else{
         console.log("FALSE============>>>>>>>>>>>>");
         currentElement.mandatory = true;
        $("#"+currentElement.id).parent().parent().removeClass("mandatory-fld");
        $("#"+currentElement.id).removeAttr("aria-required",true);
       // return true;
    }
}


function checkMand(subject, conditionValue){
    alert(conditionValue );
    if(conditionValue != '')
    {
var yy = window.guideBridge.resolveNode("guide[0].guide1[0].guideRootPanel[0].broker[0].subpanel-broker[0].bCPID[0]");
        alert(yy);
        yy.mandatory = true;
        /*
        alert("Inside IF");
        $("#"+subject.id).addClass('mandatory-fld');

          $("#"+subject.id).attr('required','true');

         $("#"+subject.id).attr('aria-required','true');
*/

    }
    else{
        $("#"+subject.id).removeClass('mandatory-fld')
    }
    alert( subject.id );
    alert(subject.name);

	//subject.addClass(mandatory-fld);

}
