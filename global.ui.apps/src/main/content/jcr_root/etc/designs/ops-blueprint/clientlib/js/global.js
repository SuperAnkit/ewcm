window.onload = function() {

	//  functionality when window loads

	//to enable create New tab only for Maker Group
	var user_group = $("#user_group").val();
	if (user_group == 'maker_group') {
		$('#createNewFormTab').removeClass('displayNone');
	}


	//For sizing the save draft button on the form
	$("#guideContainer-toolbar-save___widget").addClass('widthAdjustment')

	//For making search field empty on page load
	$("#frmSearch_txt").val('');

	//if(($('#user_group').val()=='maker_group') || ($('#user_group').val()=='checker_group')){alert('TRUE')} else {alert('FALSE')};


	//for making validation valid by default
	//alert($('#guideContainer-rootPanel-guidepanel_879805661044659-panel1393766190488-guidetextbox_0___widget').val());
	$('#isValidation').attr('value', 'true');

	//alert($('#guideContainer-rootPanel-guidepanel_879805661044659-panel1393766190488-guidetextbox_0___widget').val());
	hideCalculateOnlyFields();

	displayUserRole();

}

function populateEmployerName(employers,applicantCalcOnly) {

	var aarr = [];

	for (var i = 1; i < employers._children.length; i++) {
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


function populateApplicantName(rootPanel,applicantCalcOnly) {
	rooPanel = rootPanel;

    console.log("====App rootPanel console======   " + rootPanel._children.length);
    console.log("====App rootPanel console======   " + rootPanel._children[0].name);
    console.log("====App rootPanel console======   " + rootPanel._children[1].name);
    console.log("====App rootPanel console======   " + rootPanel._children[2].name);
    console.log("====App rootPanel console======   " + rootPanel._children[3].name);

	var aarr = [];

	for (var i = 1; i < rootPanel._children.length; i++) {
		var cpanel = rootPanel._children[i];

        if(cpanel.name == 'pApplicant'){

		var primary_applicant = cpanel._children[1];
		var customer_type = primary_applicant._children[7];
		var firstName_text_field = customer_type._children[1];

		var lastName = primary_applicant._children[7];
		var lastName_text_field = lastName._children[3];

        var middleName = primary_applicant._children[7];
		var middleName_text_field = middleName._children[2];

		var firstNameValue = firstName_text_field.value;
		var lastNameValue = lastName_text_field.value;
		var middleNameValue = middleName_text_field.value;
		var sequence_number = primary_applicant._children[0].value;
        //var applicantCalcOnly = applicantCalcOnly.value;    

        		//alert(firstNameValue +"~~~" +lastNameValue +"~~~" +sequence_number+"~~~" +middleNameValue);

			if (firstNameValue==null)
            firstNameValue="";
        	if (middleNameValue==null)
            middleNameValue="";
        	if (lastNameValue==null)
            lastNameValue="";

        var Value = sequence_number + "=" + firstNameValue + " "+ middleNameValue + " "+ lastNameValue;
        //+ "::"+ tempNameValue + "::";
		Value = Value.replace("null", "");
        //alert(Value);
		aarr.push(Value);

	}

}
	console.log("arrr pushhhhed---"+aarr+"---");
	return aarr;
}

var coappDelval = [];

$(document).ready(function() {

//fix for radio buttons
	setTimeout(function() {
		$('body input:radio').each(function(i) {
			var chk = $(this).attr('aria-selected');
			if (chk === "true") {
				$(this).trigger('click').trigger('click');
				$("body input:text").eq(0).focus(); // To focus on first text field
			}
		});
	}, 10);



	$("body").on("click", "li#guideContainer-rootPanel-Broker___guide-item-nav > a", function() {
		console.info("broker doone");
		setTimeout(function() {
			$('#guideContainer-rootPanel-Broker-broker__ input:radio').each(function(i) {
				var chk = $(this).attr('aria-selected');
				if (chk === "true") {
					$(this).trigger('click').trigger('click');
					$("#guideContainer-rootPanel-Broker-broker__ input:text").eq(0).focus();
				}
			});
		}, 50);


	});

	$("body").on("click", "li#guideContainer-rootPanel-Employment___guide-item-nav > a", function() {
		console.info("employee doone");
		setTimeout(function() {
			$('#guideContainer-rootPanel-Employment-employment___guide-item-container input:radio').each(function(i) {
				var chk = $(this).attr('aria-selected');
				if (chk === "true") {
					$(this).trigger('click').trigger('click');
					$("#guideContainer-rootPanel-Employment-employment___guide-item-container input:text").eq(0).focus();

				}
			});
		}, 50);


	});

	$("body").on("click", "li#guideContainer-rootPanel-Document___guide-item-nav > a", function() {
		console.info("document doone");
		setTimeout(function() {
			$('#guideContainer-rootPanel-Document-documentpanel__ input:radio').each(function(i) {
				var chk = $(this).attr('aria-selected');
				if (chk === "true") {
					$(this).trigger('click').trigger('click');
					$("#guideContainer-rootPanel-Document-documentpanel__ input:text").eq(0).focus();

				}
			});
		}, 50);


	});


	$("body").on("click", "li#guideContainer-rootPanel-Account___guide-item-nav > a", function() {
		console.info("Account doone");
		setTimeout(function() {
			$('#guideContainer-rootPanel-Account___guide-item-container input:radio').each(function(i) {
				var chk = $(this).attr('aria-selected');
				if (chk === "true") {
					$(this).trigger('click').trigger('click');

				}
			});
		}, 50);

	});



// search ops

    var searchValue = $("#frmSearch_txt").val();
    if(searchValue == ''){
        $("#btnSearch").attr("disabled","disabled");
    } else {
		$("#btnSearch").removeAttr("disabled");
    }

    $("#frmSearch_txt").keyup(function(){
        if($(this).val() != ''){
           $("#btnSearch").removeAttr("disabled");
        } else {
            $("#btnSearch").attr("disabled","disabled");
        }
    });

    $("#frmSearch_txt").bind("paste", function(e){
        $("#btnSearch").removeAttr("disabled");
    } );

    $('#frmSearch_txt').bind("keypress", function (e) {
		if (e.keyCode == 13) {
			searchResults(); 
			return false; 
		}
	});

});


function searchResults() {
	var txtval = $("#frmSearch_txt").val();
	var searchResultData;
	var user_group = $("#user_group").val();
	var user_name = $("#user_name").val();
	var radiovalue = $("input[name='optradio']:checked").val();

	var submitData = "frmSearch_txt=" + txtval + "&user_group=" + user_group + "&user_name=" + user_name + "&docType=" + radiovalue;
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

function poulateUI(data) {


	if (data == '' || data == null) {
		$(".getBackResult").html('');
		$(".getBackResult").append('No Results Found');
	} else {
		$(".getBackResult").html('');
		$(".getBackResult").append('<div>' + data + '</div>');

	}
}



function showhideTabs(val) {

	if (val === "review-tab") {
		$("#review-tab").show();
		$("#draft-tab").hide();
		$("#workingItems-tab").hide();
	} else if (val === "draft-tab") {
		$("#draft-tab").show();
		$("#review-tab").hide();
		$("#workingItems-tab").hide();
	} else {
		$("#draft-tab").hide();
		$("#review-tab").hide();
		$("#workingItems-tab").show();
	}

}

// Methods for sequence generation - Start
function SequenceGenerator() { 
    var map = new Object(); // or var map = {};
    this.get = function(k) {
        var cnt = map[k] || 0;
        map[k] = ++cnt;
        return cnt;
    };

    this._setCountValue = function(k, v) {
        if( map[k] == undefined || v > map[k]){
           map[k] = v;
        }
    };
}


var sg_new = new SequenceGenerator();
var ele;

function getInitCounter(element,isAppFin) {

    ele = element;
    $("#"+element.id).attr("hidden",true);
    var value = element.value;
    var panelId = '';
    if(isAppFin){
                                panelId = element.parent.parent.parent.id;
    }else{
                                panelId = element.parent.parent.id;
    }


    console.log("value :---->  " + value);
    if(value != "" && value != null && value != undefined)
    {
        //console.log("Panel_ : " + panelId + "  value_ : " + value);
        try{
        sg_new._setCountValue(panelId,value);
        }catch(e){console.log(e);}
       // console.log("Panel_1 : " + panelId + "  value_1 : " + value);

        return value;
    }

    console.log(panelId);
    return sg_new.get(panelId);

}
// Methods for sequence numbers - Stop

// To disable all text fields on the form. call the below function.
function disableTextFields() {
	var f = document.getElementsByTagName('input');
	for(var i=0;i<f.length;i++) {
    	if(f[i].getAttribute('type')=='text'){
			f[i].setAttribute('disabled',true);
  		}
	}
}

// To disable all select dropdowns on the form. call the below function.
function disableSelectDropdowns() {
	var s = document.getElementsByTagName('select');
    for(var i=0;i<s.length;i++) {
       s[i].setAttribute('disabled',true);
    }
}

// To hide calc only fields from form
function hideCalculateOnlyFields() {
	var s = document.getElementsByClassName('calc-only');
    for(var i=0;i<s.length;i++) {
       s[i].setAttribute('hidden',true);
    }
}

function displayUserRole(){
var role = $('#user_group').val();
if(role == 'maker_group'){
	document.getElementById('role_label').innerHTML = 'You are logged as MAKER';
}
else if(role == 'checker_group'){
document.getElementById('role_label').innerHTML = 'You are logged as CHECKER';
}
else if(role == 'reader_group'){
document.getElementById('role_label').innerHTML = 'You are logged as READER';
}
else {
// do nothing
}
}
