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
  DropDown List Component
--%>
<%@include file="/libs/fd/af/components/guidesglobal.jsp"%>
    <%-- todo: In case of repeatable panels, please change this logic at view layer --%>
<div class="<%= GuideConstants.GUIDE_FIELD_WIDGET%>" style="${guide:encodeForHtmlAttr(guideField.styles,xssAPI)}">

    <select id="${guideid}${"_widget"}" name="${guide:encodeForHtmlAttr(guideField.name,xssAPI)}" ${guideField.isMultiSelect ? "multiple=\"multiple\"" : ""}>

        <c:forEach items="${guideField.options}" var="option" varStatus="loopCounter">
            <option value="${guide:encodeForHtmlAttr(option.key,xssAPI)}"> ${guide:encodeForHtml(option.value,xssAPI)} </option>
        </c:forEach>

    </select>
    <%-- End of Widget Div --%>
</div>
