<%@ include file="../META-INF/resources/init.jsp" %>

<%

    AssetRenderer<?> assetRenderer = (AssetRenderer<?>) request.getAttribute(
            WebKeys.ASSET_RENDERER
    );

    String viewEntryURL = assetRenderer.getURLView(liferayPortletResponse, WindowState.MAXIMIZED);

    Assignment assignment = (Assignment) request.getAttribute("assignemnt");

%>

<aui:a cssClass="title-link" href="<%=viewEntryURL%>">
    <h3 class="title"><%=HtmlUtil.escape(assignment.getTitle(locale))%>
    </h3>
</aui:a>

<div class="autofit-col autofit-col-expand">
    <%= HtmlUtil.escape(assignment.getDescription()) %>
</div>