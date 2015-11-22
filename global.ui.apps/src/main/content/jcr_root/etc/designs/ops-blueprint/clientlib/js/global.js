window.onload = function(){

    //  functionality when window loads

    //to enable create New tab only for Maker Group
	var user_group = $("#user_group").val();
    if(user_group == 'maker_group'){
 			$('#createNewFormTab').removeClass('displayNone');
    }


    //For sizing the save draft button on the form
    $("#guideContainer-toolbar-save___widget").addClass('widthAdjustment')

    //For making search field empty on page load
	$("#frmSearch_txt").val('');

    //if(($('#user_group').val()=='maker_group') || ($('#user_group').val()=='checker_group')){alert('TRUE')} else {alert('FALSE')};


    //for making validation valid by default
    //alert($('#guideContainer-rootPanel-guidepanel_879805661044659-panel1393766190488-guidetextbox_0___widget').val());
      $('#isValidation').attr('value','true');

 	//alert($('#guideContainer-rootPanel-guidepanel_879805661044659-panel1393766190488-guidetextbox_0___widget').val());

}

function populateEmployerName(employers){

var aarr =[];

    for(var i=1; i < employers._children.length; i++ ){
        var cpanel = employers._children[i];

        var employments = employers._children[i]._children[3];
		//alert("cpanel" + cpanel.name);
   //     alert("employments" + employments.name);
        var primary_emp = employments._children[6];
 //alert(primary_emp.name);
       //alert(employments.value);
        //var name_text_field = customer_type._children[1];
        var value = primary_emp.value;


        var sequence_number = cpanel._children[0].value;
      //  alert("sequence_number=" + sequence_number);
        var arrval = sequence_number + "=" + value;
        aarr.push(arrval);


    }
     console.log(aarr);
     return aarr;
}


function populateApplicantName(rootPanel) {
    rooPanel = rootPanel;
    var aarr =[];
    // alert(rootPanel._children.length);
          //$('.customer-d    rop-down select').empty();
   // alert("33");
    //grandpanel = panelName;


    //rootpanel -> applicant -> primary applicant -> customertype- > applicant name -> value
          //var v = rooPanel._children[1]._children[1]._children[7]._children[1].value;

//   var v = rooPanel._children[1]._children[1]._children[7]._children[1].value;
   // var arrval = v + "=" + v;
  //  aarr.push(arrval);
    //aarr.push(arrval);

    for(var i=1; i < rootPanel._children.length; i++ ){
        var cpanel = rootPanel._children[i];
        var primary_aplicant = cpanel._children[1];
        var customer_type = primary_aplicant._children[6];
        var name_text_field = customer_type._children[1];
        var value = name_text_field.value;
        var sequence_number = primary_aplicant._children[0].value;
        var arrval = sequence_number + "=" + value;
        aarr.push(arrval);


    }
     console.log(aarr);
     return aarr;
}

  var coappDelval = [];

$(document).ready(function (){


   /* alert($( "input[name*='SamTxt']").val());
    $("input[name*='SamTxt']").attr({"value" : "queue"});

alert($( "input[name*='SamTxt']").val());*/

	//fix for radio buttons
	setTimeout(function(){
        $('body input:radio').each(function(i) {
var chk = $(this).attr('aria-selected');
            if(chk === "true"){
                $(this).trigger('click').trigger('click'); 
                $("body input:text").eq(0).focus(); // To focus on first text field
            }
        });
  },10);



     $("body").on("click", "li#guideContainer-rootPanel-Broker___guide-item-nav > a" ,function(){
      console.info("broker doone");
         setTimeout(function(){
             $('#guideContainer-rootPanel-Broker-broker__ input:radio').each(function(i) {
                 var chk = $(this).attr('aria-selected');
                 if(chk === "true"){
                     $(this).trigger('click').trigger('click'); 
                     $("#guideContainer-rootPanel-Broker-broker__ input:text").eq(0).focus(); 
                 }
             });
         },50);


        });

 $("body").on("click", "li#guideContainer-rootPanel-Employment___guide-item-nav > a" ,function(){
      console.info("employee doone");
         setTimeout(function(){
             $('#guideContainer-rootPanel-Employment-employment___guide-item-container input:radio').each(function(i) {
                 var chk = $(this).attr('aria-selected');
                 if(chk === "true"){
                     $(this).trigger('click').trigger('click'); 
                      $("#guideContainer-rootPanel-Employment-employment___guide-item-container input:text").eq(0).focus(); 
                     
                 }
             });
         },50);


        });

     $("body").on("click", "li#guideContainer-rootPanel-Document___guide-item-nav > a" ,function(){
      console.info("document doone");
         setTimeout(function(){
             $('#guideContainer-rootPanel-Document-documentpanel__ input:radio').each(function(i) {
                 var chk = $(this).attr('aria-selected');
                 if(chk === "true"){
                     $(this).trigger('click').trigger('click'); 
                     $("#guideContainer-rootPanel-Document-documentpanel__ input:text").eq(0).focus(); 

                 }
             });
         },50);


        });


     $("body").on("click", "li#guideContainer-rootPanel-Account___guide-item-nav > a" ,function(){
      console.info("Account doone");
             setTimeout(function(){
             $('#guideContainer-rootPanel-Account___guide-item-container input:radio').each(function(i) {
                 var chk = $(this).attr('aria-selected');
                 if(chk === "true"){
                     $(this).trigger('click').trigger('click'); 

                 }
             });
         },50);

        });






/* Adding butotn Logic */


    $("body").on("click","button[aria-label='Add Co-applicant']", function(){
		setTimeout(function(){
        	mapping("customer-app-detail");
        });

    });

    $("body").on("click","button[aria-label='Add Employment']", function(){
		setTimeout(function(){
        	mapping("employment-emp-detail");
        });

    });
	
	$("body").on("click","button[aria-label='Add OFI details']", function(){
		setTimeout(function(){
        	mapping("ofi-app-details");
        });

    });
	
	$("body").on("click","button[aria-label='Add Solicitor Details']", function(){
		setTimeout(function(){
        	mapping("solicitor-app-details");
        });

    });

    $("body").on("click","button[aria-label='Add Multi Director']", function(){
		setTimeout(function(){
        	mapping("pmulti-director-app-details");
        });

    });

    $("body").on("click","button[aria-label='Add Trustee other']", function(){
		setTimeout(function(){
        	mapping("ptrust-app-details");
        });

    });

    $("body").on("click","button[aria-label='Add Security']", function(){
		setTimeout(function(){
        	mapping("security-app-details");
        });

    });



 /* Removing Button Logic */

 $("body").on("click","button[aria-label='Remove Co-applicant']", function(){


      var applicantDelval = $(this).parent().parent().parent().parent().find("input[type=hidden][name=hide_pApplicantID]").val();
      //applicantDelval = applicantDelval.split('_');
      applicantDelval = parseInt(applicantDelval[1]);

     coappDelval.push(applicantDelval);

     });

 $("body").on("click","button[aria-label='Remove Employment']", function(){
      var employmentDelval = $(this).parent().parent().parent().parent().find("input[type=hidden][name=hide_employmentSequenceNo]").val();
      //employmentDelval = employmentDelval.split('_');
      employmentDelval = parseInt(employmentDelval[1]);
     });


});

 $("body").on("click","button[aria-label='Remove Security']", function(){

      var securityDelval = $(this).parent().parent().parent().parent().find("input[type=hidden][name=hide_secSequenceNo]").val();
      alert("securityDelval" + securityDelval);
    //  securityDelval = securityDelval.split('_');
      securityDelval = parseInt(securityDelval[1]);
     });


function checkIdx(ids){

    if(coappDelval.indexOf(ids) != -1){
        ids++;
		return checkIdx(ids);
    } else {
        return ids;
    }
    return false;
}

function getInitCounter(panelId,fieldId) {

    /*
	panelId  - Class style name of the Parent Panel
    fieldId  - Class style name of the counter input field
    */

    var cnt=1;
    var i = 0;
    var flcnt=0;
    var incnt;
    var clsfieldId;



clsfieldId = fieldId + " input";

//check if the field is array and get the max value sequence number
  if ($(panelId).find(clsfieldId)[0].value != '')
  {
      cnt = $(panelId).length +1 ;
     incnt=cnt;
	$(panelId).each(function(){
         incnt = parseInt($(panelId).find(clsfieldId)[i].value);
        if (incnt > flcnt)
            flcnt = incnt;
   	 	i++;
    });
    cnt = parseInt(flcnt)+1;
  }
    else 
    {
       // if the field is not repeating count will be 1
        cnt = $(panelId).length;

    }

    return cnt;
}

/*function mappingV1(ids,fldCnt,fldSOM)
{
    var i = 1;
    var sids;
    var sfldCnt;

    sids = "." + ids;
    sfldCnt = "input[name=" + fldCnt + "]";

    alert("sids:" + sids + " sfldCnt:" + sfldCnt + " fldSOM:" + fldSOM);

    var ifield = guideBridge.resolveNode(fldSOM);



    ifield.value = 0; 

alert("ifield" + ifield.value);
        $(sids).find(sfldCnt).each(function(){
            alert(i);
            i = checkIdx(i);
            //$(this).val(i);
            ifield.value = i;
            i++;
       });
}*/

function mapping(ids){

    if(ids == "customer-app-detail"){

        var i = 1;
        $(".customer-app-detail").find("input[type=hidden][name=hide_pApplicantID]").each(function(){

            i = checkIdx(i);
            $(this).val(i);
            i++;
        });

    } else if(ids == "employment-emp-detail"){
        //alert("emdp detail");

        var i = 1;
        $(".employment-emp-detail").find("input[type=hidden][name=hide_employmentSequenceNo]").each(function(){
           // alert("empID"+i);
            $(this).val(i);
            i++;
        });

    } else if(ids == "ofi-app-details"){
        //alert("emdp detail");

        var i = 1;
        $(".ofi-app-details").find("input[type=hidden][name=hide_ofiSequenceNo]").each(function(){
           // alert("empID"+i);
            $(this).val(i);
            i++;
        });

    } else if(ids == "solicitor-app-details"){
        //alert("emdp detail");

        var i = 1;
        $(".solicitor-app-details").find("input[type=hidden][name=hide_solicitorSequenceNo]").each(function(){
           // alert("empID"+i);
            $(this).val(i);
            i++;
        });

    } else if(ids == "pmulti-director-app-details"){
        //alert("emdp detail");

        var i = 1;
        $(".pmulti-director-app-details").find("input[type=hidden][name=hide_pMultidirectorSequenceNo]").each(function(){
           // alert("empID"+i);
            $(this).val(i);
            i++;
        });

    } else if(ids == "security-app-details"){
        //alert("emdp detail");

        var i = 1;
        $(".security-app-details").find("input[type=hidden][name=hide_secSequenceNo]").each(function(){
           // alert("empID"+i);
            $(this).val(i);
            i++;
        });

    }

}


function searchResults(){
			var txtval = $("#frmSearch_txt").val();
			var searchResultData;
            var user_group = $("#user_group").val();
            var user_name = $("#user_name").val();
            var radiovalue =  $("input[name='optradio']:checked").val();

            var submitData = "frmSearch_txt=" + txtval + "&user_group=" + user_group + "&user_name=" + user_name + "&docType=" + radiovalue  ;
    //alert(submitData);
			
			$.ajax({
			      url: '/bin/opsSearch',
			      data: submitData,
			      error: function() {
			         $('#info').html('<p>An error has occurred</p>');
			      },
			      dataType: 'text',
			      success: function(data) {
			       //var searchResultData = data;
                      poulateUI(data)
			      },
			      type: 'POST'
			   });



		}

function poulateUI(data){


    if( data == '' || data == null)  {
        $(".getBackResult").html('');
         $(".getBackResult").append('No Results Found');
    }else{
		$(".getBackResult").html('');
		 $(".getBackResult").append('<div>'+data+'</div>');

    }
}



function showhideTabs(val) {

                if (val === "review-tab") {
                    $("#review-tab").show();
                    $("#draft-tab").hide();
                    $("#workingItems-tab").hide();
                } else if (val === "draft-tab")  {
                    $("#draft-tab").show();
                    $("#review-tab").hide();
					$("#workingItems-tab").hide();
                }
                  else {
                    $("#draft-tab").hide();
                    $("#review-tab").hide();
					$("#workingItems-tab").show();
                }

}