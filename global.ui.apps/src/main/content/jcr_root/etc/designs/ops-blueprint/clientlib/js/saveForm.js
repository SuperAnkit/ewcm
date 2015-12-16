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
handleOPSSave = function (formAppNo, mandatoryFlushCounter){
    var guideName;
    var guidePath = window.guideBridge.getGuideContext().data[window.guideBridge.GUIDE_PATH];
    guidePath = guidePath.substr(0,guidePath.lastIndexOf("jcr:content/guideContainer")-1);
    guidePath = "/content/dam/formsanddocuments" + guidePath.substring(17);
    guideName = guidePath.substring(guidePath.lastIndexOf("/")+1);
    var resultObj,errorObj, guideState;
    var draftID;

    var formUserGroup = $("#user_group").val();
	if(formUserGroup == 'checker_group'){
		mandatoryFlushCounter.value = parseInt(mandatoryFlushCounter.value) + 1;
    }
    if(formUserGroup == 'maker_group'){
		mandatoryFlushCounter.value = 0;
    }

    //add bApplicationNumber to the formInstance
    //var formAppNo = $("#guideContainer-rootPanel-Broker-broker-guidetextbox_2___widget").val();

    if(typeof window.guideBridge.customContextProperty("draftID") === "undefined"){
        var time = new Date();
        time = time.getTime();
        draftID = guideName + "_" + formAppNo + "_af";
        window.guideBridge.customContextProperty("draftID",draftID);
    }else{
        draftID =  window.guideBridge.customContextProperty("draftID");
    }
    $("#draftID").attr("value",draftID);
    var currentNodePath = $("#saveButtonPath").data("savebuttonpath");
    var contextPath = window.guideBridge._getUrl(currentNodePath);
    var fileUploadPath = contextPath+".fp.attach.jsp/"+draftID;
    var paramObj = {
        "success":function(result){
            window.guideBridge.trigger(
                "saveStarted",
                window.guidelib.event.GuideModelEvent.createEvent("saveStarted")
            );
            guideState = result.data;
            var urlForDraft = contextPath + ".fp.draft.json?func=saveDraft";
            $.ajax({
                type:"POST",
                url: urlForDraft,
                async: true,
                cache: false,
                mimeType:"multipart/form-data",
                data: {
                    'formName'  : guideName,
                    'formPath'  : guidePath,
                    'formData'  : JSON.stringify(guideState),
                    'draftID'   : draftID,
                    'formType'  : "af",
                    '_charset_' : "UTF-8"
                },
                success: function (result) {
                    $(".__FP_Saved_Message").show();
                    $(".__FP_Saved_Message" ).fadeOut( 1600, "linear");
                    window.guideBridge.trigger(
                        "saveCompleted",
                        window.guidelib.event.GuideModelEvent.createEvent("saveCompleted")
                    );
                    window.guideBridge.customContextProperty("draftID",result.draftID);
                },
                error : function (result) {
                    if(result.status == "503"){
                        alert(guidelib.util.GuideUtil.getLocalizedMessage("", guidelib.i18n.LogMessages["AEM-AF-901-008"]));
                    }else{
                        alert(guidelib.util.GuideUtil.getLocalizedMessage("", guidelib.i18n.LogMessages["AEM-AF-901-009"]));
                    }
                }
            });
        },
        "error": function(result){
            errorObj = result;
            if(errorObj != null){
                console.log(guidelib.util.GuideUtil.getLocalizedMessage("", guidelib.i18n.LogMessages["AEM-AF-901-009"]));
                return 0;
            }
        },
        "fileUploadPath": fileUploadPath
    }

    window.guideBridge.getGuideState(paramObj);

}