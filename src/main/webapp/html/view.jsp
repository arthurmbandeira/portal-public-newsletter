<%@ include file="/html/init.jsp"%>

<portlet:actionURL var="subscribeURL" />

<liferay-ui:success key="success" message="Obrigado por se cadastrar!"/>

<%
    String title = "O Cart&#227;o Carrefour tem muito mais dicas para voc&#234; ficar sempre no comando das suas contas.";
    String label = "Deixe o seu email e fique por dentro das novidades.";
    String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam at porttitor sem. Aliquam erat volutpat. Donec placerat nisl magna, et faucibus arcu condimentum sed.";
    String image = request.getContextPath() + "/img/newsletter.png";
%>

<div class="newsletter-container">

    <div class="news-item">
        <div class="news-info">
            <div class="row">
                <div class="col news-title">
                    <h3><%= title %></h3>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <aui:form action="<%=subscribeURL%>" method="post" name="subscribeForm">
                        <aui:input name="email" label="<%= label %>" type="email">
                            <aui:validator name="required" />
                            <aui:validator name="email" />
                        </aui:input>

                        <aui:button-row>
                            <aui:button type="submit" value="&nbsp;" cssClass="btn-submit" />
                        </aui:button-row>
                    </aui:form>
                </div>
            </div>
            <div class="row">
                <div class="col">
                    <p><%= text %></p>
                </div>
            </div>
        </div>
        <div class="news-img">
            <img src="<%= image %>" title="Newsletter" />
        </div>
    </div>
</#if>
</div>


