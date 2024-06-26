package com.liferay.training.gradebook.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.training.gradebook.web.constants.GradebookPortletKeys;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;

/**
 * @author sarahribeiro
 */
@Component(
        property = {
                "com.liferay.portlet.display-category=category.training",
                "com.liferay.portlet.css-class-wrapper=gradebook-portlet",
                "com.liferay.portlet.header-portlet-css=/css/main.css",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.display-name=Gradebook",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.name=" + GradebookPortletKeys.GRADEBOOK,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user",
                "javax.portlet.init-param.add-process-action-success-action=false"
        },
        service = Portlet.class
)
public class GradebookPortlet extends MVCPortlet {
}