<%------------------------------------------------------------------------
 ~
 ~ ADOBE CONFIDENTIAL
 ~ __________________
 ~
 ~  Copyright 2014 Adobe Systems Incorporated
 ~  All Rights Reserved.
 ~
 ~ NOTICE:  All information contained herein is, and remains
 ~ the property of Adobe Systems Incorporated and its suppliers,
 ~ if any.  The intellectual and technical concepts contained
 ~ herein are proprietary to Adobe Systems Incorporated and its
 ~ suppliers and may be covered by U.S. and Foreign Patents,
 ~ patents in process, and are protected by trade secret or copyright law.
 ~ Dissemination of this information or reproduction of this material
 ~ is strictly forbidden unless prior written permission is obtained
 ~ from Adobe Systems Incorporated.
 --------------------------------------------------------------------------%>
 
<%--
  TextBox Component
--%>
<%@include file="/libs/fd/af/components/guidesglobal.jsp"%>
    <%-- todo: In case of repeatable panels, please change this logic at view layer --%>
    <div class="<%= GuideConstants.GUIDE_FIELD_WIDGET%> ${guideField.multiLine ? " multiline" : ""}" style="${guide:encodeForHtmlAttr(guideField.styles,xssAPI)}">

        <c:choose>
            <c:when test="${guideField.multiLine}">
                <textarea id="${guideid}${'_widget'}" name="${guide:encodeForHtmlAttr(guideField.name,xssAPI)}">${guide:encodeForHtml(guideField.value,xssAPI)}</textarea>
            </c:when>

            <c:otherwise>
                 <input type="hidden" id="${guideid}${'_widget'}" name="${guide:encodeForHtmlAttr(guideField.name,xssAPI)}" value="${guide:encodeForHtmlAttr(guideField.value,xssAPI)}"/>
            </c:otherwise>
        </c:choose>
        <%-- End of Widget Div --%>
    </div>
