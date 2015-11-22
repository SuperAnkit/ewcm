<%--
  ADOBE CONFIDENTIAL
  __________________

   Copyright 2014 Adobe Systems Incorporated
   All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Adobe Systems Incorporated and its suppliers,
  if any.  The intellectual and technical concepts contained
  herein are proprietary to Adobe Systems Incorporated and its
  suppliers and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden unless prior written permission is obtained
  from Adobe Systems Incorporated.
--%><%
%><%@include file="/libs/fd/af/components/guidesglobal.jsp"%>
<guide:initializeBean name="guidePanel" className="com.adobe.aemds.guide.common.GuidePanel" resourcePath="<%= resource.getPath() + "/guideContainer/rootPanel" %>" restoreOnExit="true">
    <div class="container">
        <ul class="upperNavigation clearfix">
            <c:forEach items="${guidePanel.items}" var="panelItem">
                <li data-guide-id="${panelItem.id}_guide-item-nav" title="${panelItem.navTitle}" data-path="${panelItem.path}"  >
					<div class="progress">
                        <a id="${panelItem.id}_guide-item-nav-progress" class=" progress-bar progress-bar-warning" role="progressbar" aria-valuenow="00" aria-valuemin="0" aria-valuemax="100" style="width:0%;min-width:0.1%;padding:0px" data-guide-toggle="tab">

                    </a>
                        </div>
                        <div style="position:relative;top:-37px;">
                        <a class="${panelItem.cssClassName}_external_nav"><span style="">${panelItem.navTitle}</span></a>
                    	</div>


                </li>
                <!--span style="width:100%">${panelItem.navTitle}</span-->
            </c:forEach>
        </ul>
    </div>
</guide:initializeBean>

<c:if test="${!isEditMode}">
    <script type="text/javascript">
var myprogress;
$( document ).ready(function() {
window.guideBridge.on("elementNavigationChanged", function(event, data)
	  {
		  
		  var prevpanel = (window.guideBridge.resolveNode(data.prevText)),
		   subpanel = true,
		   varpanel = false;
           if (prevpanel.parent.parent !== undefined && prevpanel.parent.parent !== null) {
                if(prevpanel.parent.parent.parent == null ){subpanel = false;}
          }    
		  if(!subpanel){
			  var size = 0;

			  var j=0;
			  for(i=0; i < prevpanel.children.length; i++){
				  if(prevpanel.children[i].name != "textdraw"){
					  size++;
					  if(prevpanel.children[i].value != null){
						  j++;
					  }
				  }
			  }
			 
			  var percent = (j/size*100);
			  var panelid = (prevpanel.id),
              navBarProgress = document.getElementById(panelid + "_guide-item-nav-progress");
			  if(percent >= 0 &&  percent <= 25 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                    document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#FF0000";
                  }

         	 }
              if(percent > 25 &&  percent <= 50 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                      document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#FFFF00";
                  }

         	 }
              if(percent > 50 &&  percent <= 75 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                      document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#FFC200";

                  }

         	 }
               if(percent == 100 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                    document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#00FF00";
                  }

         	 }

			  for(i=1; i < prevpanel.children.length; i+=2){
				  
				  if( prevpanel.children[i].children.length){varpanel = true;}
			  }
			  if(varpanel){
				  
				  for(i=1; i < prevpanel.children.length; i+=2){
					  var size = prevpanel.children[i].children.length;
				  }
				  for(i=1; i < prevpanel.children.length; i+=2){
					  j = 0;
					  base = 0;
					  
					  
					  for(k = 0; k < prevpanel.children[i].children.length; k++ ){
						  if(prevpanel.children[i].children[k].className != "guideTextDraw" ){
							  base++;
							  if(prevpanel.children[i].children[k].value != null){j++;}
						  }
					  }
				  }
				  percent = (j/base*100);				  
				  panelid = (prevpanel.id);
				  navBarProgress = document.getElementById(panelid + "_guide-item-nav-progress");
                 if(percent >=0 &&  percent <= 25 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                    document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#FF0000";
                  }

         	 }
              if(percent > 25 &&  percent <= 50 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                      document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#FFFF00";
                  }

         	 }
              if(percent > 50 &&  percent <= 75 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                      document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#FFC200";

                  }

         	 }
               if(percent == 100 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                    document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#00FF00";
                  }

         	 }
			  }
		  }
		  else{

			  var currentpanel = prevpanel.parent;

			  var size = 0;
			  if(currentpanel && currentpanel.children) {
				for(i = 0; i < currentpanel.children.length; i++){
                      if(currentpanel.children[i].children){
                          size += currentpanel.children[i].children.length;
                      }
                  }
			  }
              console.info("size" + size);
			  var filled = 0;
               if( currentpanel && currentpanel.children ) {
                   console.info("currentpanel.children.length"+ currentpanel.children.length);
			  for(i = 0; i < currentpanel.children.length; i++){
                      if(currentpanel.children[i].children){
                      var subsize = currentpanel.children[i].children.length;
                          console.info("Panel idd::" + currentpanel.children[i].children.id);
                          console.info("subsize::" + subsize);
                      var base = 0;
					  j=0;
					   for(k=0; k< subsize; k++){
							   if(currentpanel.children[i].children[k].className != "guideTextDraw" ){

                                  base++;

                                    if(currentpanel.children[i].children[k].value != null){
                                        console.info("enterred value "+ currentpanel.children[i].children[k].value);
                                        console.info("currentpanel.children[i].children[k].value" + currentpanel.children[i].children[k].value);
                                      filled++;
                                      }

                              }

                          }

                      }
                  }
			  }
			  percent = (filled/size*100);
               panelid = (currentpanel.id);
			   navBarProgress = document.getElementById(panelid + "_guide-item-nav-progress");


			if(percent >= 0 &&  percent <= 25 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                      document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#FF0000";

                  }

         	 }
              if(percent > 25 &&  percent <= 50 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                      document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#FFFF00";

                  }

         	 }
              if(percent > 50 &&  percent <= 75 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                      document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#FFC200";

                  }

         	 }
               if(percent == 100 ) {
                  if(navBarProgress) {
                    document.getElementById(panelid + "_guide-item-nav-progress").style.width=percent + "%";
                    document.getElementById(panelid + "_guide-item-nav-progress").style.backgroundColor = "#00FF00";
                  }

         	 }
		  }

		  	  
		  
	  })


});  




</script>
</c:if>
