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

<%@ taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<%@ page import="com.adobe.aemds.guide.service.GuideModelTransformer,
                 com.adobe.aemds.guide.service.GuideIntegrationService,
                 java.util.ArrayList,
                 java.util.List,
                 java.util.UUID,
                 com.day.cq.wcm.api.AuthoringUIMode" %>
<%@ page import="com.adobe.aemds.guide.utils.GuideUtils" %>
<%@ page import="org.apache.sling.commons.json.JSONObject" %>
<%@ page import="org.apache.sling.commons.json.io.JSONWriter" %>
<%@ page import="java.io.StringWriter" %>
<%@include file="/libs/fd/af/components/guidesglobal.jsp" %>


<%
    //TODO: Use tag lib to correctly import library
    String refName = properties.get("xdpRef", "");
    boolean hasXDP = false;
    if (refName.length() > 0) {
        hasXDP = true;
    }
    refName = properties.get("xsdRef", "");
    boolean hasXSD = false;
    if (refName.length() > 0) {
        hasXSD = true;
    }
    pageContext.setAttribute("guideContainerPath",resource.getPath(),PageContext.REQUEST_SCOPE);
%>
<c:if test="${!isEditMode}">
    <cq:includeClientLib categories="xfaforms.3rdparty"/>
</c:if>
<cq:includeClientLib categories="guides.I18N.${guide:getLocale(slingRequest,resource)}"/>
<c:choose>
    <c:when test="${isEditMode}">
        <c:if test="<%= AuthoringUIMode.fromRequest(slingRequest) == AuthoringUIMode.CLASSIC%>">
            <cq:includeClientLib categories="guides.guidesAuthoring"/>
        </c:if>
    </c:when>
    <c:otherwise>
        <cq:includeClientLib categories="xfaforms.xfalibwidgets"/>
        <c:if test="<%= hasXDP%>">
            <cq:includeClientLib categories="xfaforms.xfalibModel"/>
        </c:if>
        <cq:includeClientLib categories="guides.guidelib"/>
    </c:otherwise>
</c:choose>
<cq:includeClientLib categories="af.customwidgets"/>
<guide:initializeBean name="guideContainer" className="com.adobe.aemds.guide.common.GuideContainer" />
<c:set var="hasXDP" value="false" />
<c:set var="hasXSD" value="false" />
<c:if test="${not empty guideContainer.xdpRef}">
    <c:set var="hasXDP" value="true" />
</c:if>
<c:if test="${not empty guideContainer.xsdRef}">
    <c:set var="hasXSD" value="true" />
</c:if>

<c:if test="${not empty guideContainer.cssFileRef}">
    <script type="text/javascript">
        //just call a function to load a new CSS:
        guidelib.util.GuideUtil.loadCSS("${slingRequest.contextPath}${guideContainer.cssFileRef}");
    </script>
</c:if>
<form>
    <%
        GuideContainer guideContainer = (GuideContainer)pageContext.getAttribute("guideContainer", PageContext.REQUEST_SCOPE);
        I18n i18n =GuideELUtils.getI18n(slingRequest,resource);
        if(WCMMode.fromRequest(slingRequest) == WCMMode.EDIT || WCMMode.fromRequest(slingRequest)== WCMMode.DESIGN)
        {
            i18n=null;
        }
        GuideModelTransformer guideModelTransformer = sling.getService(GuideModelTransformer.class);
        String guideJson = guideModelTransformer.exportGuideJson(guideContainer.getResource(),i18n);
        String guideContext = guideModelTransformer.exportInitialGuideContext(guideContainer.getResource());
        GuideIntegrationService guideIntegrationService = sling.getService(GuideIntegrationService.class);
        List <String> guideIntegrationServiceScriptPaths = guideIntegrationService.getScriptPaths();
        String guideCurrentStateJson = null;
        if (request.getParameter("guideStatePath") != null && request.getParameter("guideStatePath").length()>0) {
            guideCurrentStateJson = guideModelTransformer.exportGuideState(request.getParameter("guideStatePath"));
        } else if (request.getParameter("guideStatePathRef") != null && request.getParameter("guideStatePathRef").length()>0) {
            guideCurrentStateJson = guideModelTransformer.exportGuideStateFromStore(request.getParameter("guideStatePathRef"));
        }
        String xfaJson = "";
        String renderContext = "";
        String xdpRenderError = "";
        boolean addGuideSyncMessage = guideContainer.isGuideSyncRequired();
        boolean validXDP = hasXDP && guideContainer.isXDPValid();
        if(validXDP) {
            try {
                Map<String, String> xfaJsonMap = guideModelTransformer.exportXfaJson(guideContainer.getResource());
                if (xfaJsonMap != null && xfaJsonMap.containsKey(GuideConstants.XFA_FORMDOM)) {
                    xfaJson = xfaJsonMap.get(GuideConstants.XFA_FORMDOM);
                }
                if (xfaJsonMap != null && xfaJsonMap.containsKey(GuideConstants.XFA_RENDER_CONTEXT)) {
                    renderContext = xfaJsonMap.get(GuideConstants.XFA_RENDER_CONTEXT);
                }
            } catch (Exception e) {
                I18n localI18n = new I18n(slingRequest);
                xdpRenderError = localI18n.getVar("Unable to generate JSON from Template provided in Adaptive Form. Please see the server logs for more Information");
            }
        } else if(hasXDP) {
            I18n localI18n = new I18n(slingRequest);
            xdpRenderError = localI18n.getVar("Template provided in the Adaptive Form doesn't exists [")  +
                            guideContainer.getXDPName() + localI18n.getVar("]");
            addGuideSyncMessage = false;
        }
        Map<String, Object> layoutproperties = NodeStructureUtils.getLayoutProperties(slingRequest, guideContainer.getResource(), NodeStructureUtils.GUIDECONTAINER_NODENAME);
        String layoutpath = (String) layoutproperties.get(NodeStructureUtils.LAYOUT_PATH_PROPERTY);
        String jspname = layoutpath.substring(layoutpath.lastIndexOf("/") + 1) + ".jsp";
        String layoutjsp = layoutpath + "/" + jspname;
        if(guideCurrentStateJson==null) {
            if(slingRequest.getAttribute("data")!=null) {
                guideCurrentStateJson = guideModelTransformer.exportGuideDataJson(guideContainer.getResource(), (String)slingRequest.getAttribute("data"),i18n);
            } else if(slingRequest.getParameter("dataRef")!=null) {
                guideCurrentStateJson = guideModelTransformer.exportGuideDataJsonFromDataRef(guideContainer.getResource(), slingRequest.getParameter("dataRef"),i18n);
            }
        }
        StringWriter jsonStringWriter = new StringWriter();
        JSONWriter jsonWriter = new JSONWriter(jsonStringWriter);
        jsonWriter.object();
        jsonWriter.key("guidejson").value(guideJson);
        jsonWriter.key("guidecontext").value(guideContext);
        jsonWriter.key("guidemergedjson").value(guideCurrentStateJson);
        jsonWriter.key("xfajson").value(xfaJson);
        jsonWriter.key("xfarendercontext").value(renderContext);
        jsonWriter.endObject();
    %>
    <div id="actionField">
        <input type="hidden" id="redirect" name=":redirect"
               value="${guide:encodeForHtmlAttr(guideContainer.redirect,xssAPI)}"/>
        <guide:guideStart/>
        <c:forEach items="<%= guideIntegrationServiceScriptPaths%>" var="currentPath">
            <c:if test="${not empty currentPath}">
                <cq:include script="${currentPath}"/>
            </c:if>
        </c:forEach>
    </div>
    <div
         class="guideContainerWrapperNode ${guideContainer.nodeClass} container"
         data-path="${guideContainer.path}"
         data-guide-test-data="<%= (slingRequest.getParameter("addTestData")!= null) ? GuideUtils.escapeXml(jsonStringWriter.toString()) : null %>"
         data-tmproot="<%= UUID.randomUUID().toString()%>">
        <div class="${guideContainer.nodeClass}">
            <cq:include script="<%= layoutjsp %>"/>
        </div>
    </div>
    <c:if test="${isEditMode}">
        <c:if test="<%= AuthoringUIMode.fromRequest(slingRequest) == AuthoringUIMode.CLASSIC%>">
            <div id="guide_statusbar">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">${guideContainer.statusBarTitle}</h3>
                        <span class="badge counter"></span>
                        <span class="glyphicon glyphicon-open pull-right"></span>
                        <button type="button" class="close" aria-hidden="true">&times;</button>
                    </div>
                    <div class="panel-body">
                        <ul>

                        </ul>
                    </div>
                </div>
            </div>
            <script>
                window.guidelib.author.statusBar.init("#guide_statusbar", <%=addGuideSyncMessage%>);
                window.guidelib.author.statusBar.addMessage("<%=xdpRenderError%>");
                window.guidelib.author.GuideExtJSDialogUtils.bindRefField.setBindRefFlag(${hasXDP || hasXSD});
            </script>
        </c:if>
    </c:if>
    <c:if test="${!isEditMode}">
        <script>
            window.guideBridge.registerConfig("contextPath", "${slingRequest.contextPath}");
            window.guidelib.model.fireOnContainerDomElementReady(<%= jsonStringWriter.toString() %>);
        </script>
    </c:if>
</form>
