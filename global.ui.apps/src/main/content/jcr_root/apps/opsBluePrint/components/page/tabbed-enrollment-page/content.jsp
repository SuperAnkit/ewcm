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
<div class="" style="">
<%--<cq:include path="applicationformheader" resourceType="ops-blueprint/components/applicationformheader" />--%>
    <div id="sectionbar" class="navbar-default-section">
<cq:include script="externalNavigator.jsp" />
    </div>
</div>
    <script type="text/javascript">
    $(window).scroll(function() {
    if ($(window).scrollTop() > 115) {
      $('#sectionbar').addClass('navbar-fixed');
      $('.anz-head').hide();
    }
    if ($(window).scrollTop() < 115) {
      $('#sectionbar').removeClass('navbar-fixed');
         $('.anz-head').show();
    }

    });


  </script

<div class="guideBody">
    <guide:includeGuideContainer/>
</div>

