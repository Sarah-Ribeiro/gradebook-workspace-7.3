package com.liferay.training.gradebook.web.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.training.gradebook.exception.AssignmentValidationException;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.AssignmentService;
import com.liferay.training.gradebook.web.constants.GradebookPortletKeys;
import com.liferay.training.gradebook.web.constants.MVCCommandNames;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * MVC Action Command for adding assignments.
 *
 * @author liferay
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + GradebookPortletKeys.GRADEBOOK,
                "mvc.command.name=" + MVCCommandNames.ADD_ASSIGNMENT
        },
        service = MVCActionCommand.class
)
public class AddAssignmentMVCActionCommand extends BaseMVCActionCommand {

    @Reference
    protected AssignmentService _assignmentService;

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
        ServiceContext serviceContext = ServiceContextFactory.getInstance(Assignment.class.getName(), actionRequest);

//        Get parameters from the request

        Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(actionRequest, "title");

        Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(actionRequest, "description");

        Date dueDate = ParamUtil.getDate(actionRequest, "dueDate", null);

//        Get parameters from the request.
//        String title = ParamUtil.getString(actionRequest, "title", StringPool.BLANK);
//        String description = ParamUtil.getString(actionRequest, "description", StringPool.BLANK);
//        Date dueDate = ParamUtil.getDate(actionRequest, "dueDate",
//                DateFormatFactoryUtil.getDate(themeDisplay.getLocale()));

        try {
            // Call the service to add a new assignment.
            _assignmentService.addAssignment(themeDisplay.getScopeGroupId(), titleMap, descriptionMap, dueDate,
                    serviceContext);

//            Set the success message

            SessionMessages.add(actionRequest, "assignmentAdded");

            sendRedirect(actionRequest, actionResponse);
        } catch (AssignmentValidationException ave) {
//            Get error messages from the service layer

            ave.printStackTrace();

            ave.getErrors().forEach(key -> SessionMessages.add(actionRequest, key));
            actionResponse.setRenderParameter("mvcRenderCommandName", MVCCommandNames.EDIT_ASSIGNMENT);
        } catch (PortalException pe) {

            pe.printStackTrace();

//            Set error messages from the service layer

            SessionErrors.add(actionRequest, "serviceErrorDetails", pe);

            actionResponse.setRenderParameter("mvcRenderCommandName", MVCCommandNames.EDIT_ASSIGNMENT);
        }
    }
}