<%@ include file="/html/init.jsp"%>

<portlet:actionURL var="subscribeURL" />

<liferay-ui:success key="success" message="Obrigado por se cadastrar!"/>

<aui:form action="<%=subscribeURL%>" method="post"
	name="subscribeForm">

	<aui:input name="email" label="email" type="text">
		<aui:validator name="required" />
		<aui:validator name="email" />
	</aui:input>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>


