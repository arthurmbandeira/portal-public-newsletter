package br.com.carrefoursolucoes.publico;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

public class PublicNewsletterConfiguration extends DefaultConfigurationAction {
	
	private static Log _log = LogFactoryUtil.getLog(PublicNewsletterConfiguration.class);

	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
		_log.debug("Updating configuration");

		super.processAction(portletConfig, actionRequest, actionResponse);

		PortletPreferences prefs = actionRequest.getPreferences();

		String ddlname = ParamUtil.getString(actionRequest, "ddlname");
		
		_log.debug("ddlname: " + ddlname);
		
		prefs.setValue("DDLNAME", ddlname);
		prefs.store();
		
		SessionMessages.add(actionRequest, "success");
	}

}
