/*
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2014 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 *
 */


handleOPSDraftSave = function (state, redirect, formAppNo, mandatoryFlushCounter){
	var responsedata = "";

 	var formUserName = $("#user_name").val();
    var formUserGroup = $("#user_group").val();
    //var formAppNo = $("#guideContainer-rootPanel-Broker-broker-guidetextbox_2___widget").val();
    var reDirect = $("#redirect").val();


	if ($("#user_group").val() == "checker_group") {
		state = "ops:review";
	}
	alert("APP NO TO SAVE " + formAppNo);


    if(formUserGroup == 'checker_group'){
		mandatoryFlushCounter.value = parseInt(mandatoryFlushCounter.value) + 1;
    }
    if(formUserGroup == 'maker_group'){
		mandatoryFlushCounter.value = 0;
    }
    
    $('#redirect').val(redirect);

    var user_name = "user_name=" + formUserName;
    var application_no = "application_no=" + formAppNo;
    var status = "state=" + state;

    var submitData = user_name + "&" + application_no + "&" + status ;


    var url = '/bin/save';

    console.log("URL "+ url);



    console.log("Submit");


    window.guideBridge.validate = function(errorList, somExpression){
        return true;
    }

    // grey out the page while processing
    $('#progress').removeClass('DisplayNone');
    $('#progress').addClass('DisplayBlock');

    window.guideBridge.submit({

        error: function(guideResultObject) { 
            console.log("error on submit"); 
            alert("The form could not be saved, please try again in sometime.");
        } ,
  		success: function(guideResultObject) {
            console.log("called here");
            window.location = redirect;
            //$("#progress").hide();
            $.ajax({
                  url: url,
                  async:false,
                  data: submitData,
                  error: function() {
                     $('#info').html('<p>An error has occurred</p>');
                  },
                  dataType: 'text',
                  success: function(data) {
                    var newData = data;
                  },
                  type: 'POST'
             }); 
        }

    });
    console.log("finish me:" );
    console.log("after call");
   // $('#progress').removeClass('DisplayBlock');
    //$('#progress').addClass('DisplayNone');


}