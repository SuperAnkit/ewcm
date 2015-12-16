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
%><%@include file="/libs/foundation/global.jsp" %>
<body>
<cq:includeClientLib css ="apps.ops-blueprint"/>
<div id="wrap">
<cq:include script="header.jsp"/>
<cq:include script="content.jsp"/>
</div>
<cq:include script="footer.jsp"/>
<cq:include script="progress.jsp"/>


<cq:includeClientLib js ="apps.ops-blueprint"/>
<%--<div id="sign-in" class="signin-popup">
    <div class="cqlogin">
        <cq:include script="/apps/geometrixx-gov/components/userinfo/cqlogin.jsp"/>
    </div>
</div>--%>
</body>