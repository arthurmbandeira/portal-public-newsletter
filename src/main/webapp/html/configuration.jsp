<%@ include file="/html/init.jsp" %>

<%
String ddlname_cfg = PrefsParamUtil.getString(portletPreferences, request, "DDLNAME", "Nome da DDL");
%>

<liferay-portlet:actionURL var="configurationURL" portletConfiguration="true" />

<liferay-ui:success key="success" message="Dados atualizados com sucesso."/>

<aui:form action="<%= configurationURL %>" method="post" name="fm">
    
	<aui:input name="ddlname" label="ddl-name-label" type="text" value="<%= ddlname_cfg %>">
		<aui:validator name="required" />
	</aui:input>
	
    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>


