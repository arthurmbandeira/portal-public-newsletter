package br.com.carrefoursolucoes.publico;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSetConstants;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

public class PublicNewsletterPortlet extends MVCPortlet {

	private static Log _log = LogFactoryUtil.getLog(PublicNewsletterPortlet.class);

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long groupId = themeDisplay.getLayout().getGroupId();
		long companyId = CompanyThreadLocal.getCompanyId();
		long userId = 0;
		try {
			User defaultUser = UserLocalServiceUtil.getDefaultUser(companyId);
			if (defaultUser != null)
				userId = defaultUser.getUserId();
		} catch (PortalException | SystemException e1) {
			e1.printStackTrace();
		}

		_log.debug("Subscribe to Newsletter");

		String email = ParamUtil.getString(actionRequest, "email");

		PortletPreferences prefs = actionRequest.getPreferences();
		String ddlname = prefs.getValue("DDLNAME", "");

		super.processAction(actionRequest, actionResponse);

		_log.debug("Email: " + email);

		try {
			this._saveDDLRecord(email, ddlname, companyId, groupId, userId);
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		}

		SessionMessages.add(actionRequest, "success");
	}

	private void _saveDDLRecord(String email, String recordSetName, long companyId, long groupId, long userId)
			throws PortalException, SystemException {

		List<DDLRecordSet> sets = DDLRecordSetLocalServiceUtil.search(companyId, groupId, recordSetName, null,
				DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		long recordSetId = 0;
		if (!sets.isEmpty()) {

			for (DDLRecordSet set : sets) {

				Locale locale = LocaleThreadLocal.getDefaultLocale();

				if (set.getName(locale).equals(recordSetName)) {

					_log.debug("recordSetName: " + recordSetName);
					recordSetId = set.getRecordSetId();

					break;
				}
			}
		}

		Map<String, Serializable> fields = new HashMap<String, Serializable>();
		fields.put("email", email);

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		DDLRecordLocalServiceUtil.addRecord(userId, groupId, recordSetId, 0, fields, serviceContext);

	}

}
