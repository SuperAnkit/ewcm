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
handleOPSSubmit = function (state, successRedirect, failureRedirect, formAppNo){
	var responsedata = "";

 	var formUserName = $("#user_name").val();
    //var formAppNo = $("#guideContainer-rootPanel-Broker-broker-guidetextbox_2___widget").val();
    //var reDirect = $("#redirect").val();

    alert("APP NO TO SUBMIT " + formAppNo);

	$('#isValidation').attr('value','true');
    //$('#redirect').val(redirect);

    var user_name = "user_name=" + formUserName;
    var application_no = "application_no=" + formAppNo;
    var status = "state=" + state;

    var submitData = user_name + "&" + application_no + "&" + status ;

    if(state == "ops:review")
    {    
    var url = '/bin/submit';
    }

    console.log("URL "+ url);

    // grey out the page while processing
    $('#progress').removeClass('DisplayNone');
    $('#progress').addClass('DisplayBlock');


    console.log("4564564 call");

    window.guideBridge.submit({

        error: function(guideResultObject) { 
            console.log("error on submit");
                $('#progress').removeClass('DisplayBlock');
   				$('#progress').addClass('DisplayNone');
            alert("The form could not be processed, please try again in sometime.");
        } ,
  		success: function(guideResultObject) {
            console.log("called here");
            //window.location = redirect;
            $.ajax({
                  url: url,
                  async:false,
                  data: submitData,
                  error: function() {
                     $('#info').html('<p>An error has occurred</p>');
                  },
                  dataType: 'text',
                  success: function(data) {
                    var resCode = data;
                    //alert("code++" + resCode + "++");
					if(resCode == 'SUCCESS'){
                        window.location = successRedirect;
                    }
                      else{
						window.location = failureRedirect + "?errorMsg=" + resCode;
                      }

                  },
                  type: 'POST'
             }); 
        }

    });
    console.log("finish me:" );
    console.log("after call");
    $('#progress').removeClass('DisplayBlock');
    $('#progress').addClass('DisplayNone');

}