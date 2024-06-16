/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.training.gradebook.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.service.base.AssignmentLocalServiceBaseImpl;
import com.liferay.training.gradebook.validator.AssignmentValidator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component(
        property = "model.class.name=com.liferay.training.gradebook.model.Assignment",
        service = AopService.class
)
public class AssignmentLocalServiceImpl extends AssignmentLocalServiceBaseImpl {

    @Reference
    AssignmentValidator _assignmentValidator;

    public Assignment addAssignment(long groupId, Map<Locale, String> titleMap, Map<Locale, String> descriptionMap, Date dueDate, ServiceContext serviceContext) throws PortalException {
        _assignmentValidator.validate(titleMap, descriptionMap, dueDate);

        Group group = groupLocalService.getGroup(groupId);

        long userId = serviceContext.getUserId();

        User user = userLocalService.getUser(userId);

        long assignmentId = counterLocalService.increment(Assignment.class.getName());

        Assignment assignment = createAssignment(assignmentId);

        assignment.setCompanyId(group.getCompanyId());
        assignment.setCreateDate(serviceContext.getCreateDate(new Date()));
        assignment.setDueDate(dueDate);
        assignment.setDescriptionMap(descriptionMap);
        assignment.setGroupId(groupId);
        assignment.setModifiedDate(serviceContext.getModifiedDate(new Date()));
        assignment.setTitleMap(titleMap);
        assignment.setUserId(userId);
        assignment.setUserName(user.getScreenName());

        assignment = super.addAssignment(assignment);

        // Add permission resources.

        boolean portletActions = false;
        boolean addGroupPermissions = true;
        boolean addGuestPermissions = true;

        resourceLocalService.addResources(
                group.getCompanyId(), groupId, userId, Assignment.class.getName(),
                assignment.getAssignmentId(), portletActions, addGroupPermissions,
                addGuestPermissions
        );

        // Update asset resources
        updateAsset(assignment, serviceContext);

        return assignment;
    }

    public Assignment updateAssignment(long assignmentId, Map<Locale, String> titleMap, Map<Locale, String> descriptionMap, Date dueDate, ServiceContext serviceContext) throws PortalException {
        _assignmentValidator.validate(titleMap, descriptionMap, dueDate);

        Assignment assignment = getAssignment(assignmentId);

        assignment.setModifiedDate(new Date());
        assignment.setTitleMap(titleMap);
        assignment.setDueDate(dueDate);
        assignment.setDescriptionMap(descriptionMap);
        assignment = super.updateAssignment(assignment);

        // Update asset resources
        updateAsset(assignment, serviceContext);

        return assignment;
    }

    public List<Assignment> getAssignmentsByGroupId(long groupId) {
        return assignmentPersistence.findByGroupId(groupId);
    }

    public List<Assignment> getAssignmentsByGroupId(long groupId, int start, int end) {
        return assignmentPersistence.findByGroupId(groupId, start, end);
    }

    public List<Assignment> getAssignmentsByGroupId(long groupId, String keywords, int start, int end, OrderByComparator<Assignment> orderByComparator) {
        return assignmentPersistence.findByGroupId(groupId, start, end, orderByComparator);
    }

    public List<Assignment> getAssigmentsByKeywords(long groupId, String keywords, int start, int end, OrderByComparator<Assignment> orderByComparator) {
        return assignmentLocalService.dynamicQuery(getKeywordSearchDynamicQuery(groupId, keywords), start, end, orderByComparator);
    }

    public long getAssignmentsCountByKeywords(long groupId, String keywords) {
        return assignmentLocalService.dynamicQueryCount(getKeywordSearchDynamicQuery(groupId, keywords));
    }

    private DynamicQuery getKeywordSearchDynamicQuery(
            long groupId, String keywords
    ) {
        DynamicQuery dynamicQuery = dynamicQuery().add(
                RestrictionsFactoryUtil.eq("groupId", groupId)
        );
        if (Validator.isNotNull(keywords)) {
            Disjunction disjunctionQuery = RestrictionsFactoryUtil.disjunction();
            disjunctionQuery.add(RestrictionsFactoryUtil.like("title", "%" + keywords + "%"));
            disjunctionQuery.add(RestrictionsFactoryUtil.like("description", "%" + keywords + "%"));
            dynamicQuery.add(disjunctionQuery);
        }
        return dynamicQuery;
    }

    public Assignment deleteAssignment(Assignment assignment) throws PortalException {
        // Delete permission resources.

        resourceLocalService.deleteResource(
                assignment, ResourceConstants.SCOPE_INDIVIDUAL
        );

        // Delete the assignment
        assetEntryLocalService.deleteEntry(
                Assignment.class.getName(), assignment.getAssignmentId()
        );

        return super.deleteAssignment(assignment);
    }

    private void updateAsset(
            Assignment assignment, ServiceContext serviceContext
    ) throws PortalException {
        assetEntryLocalService.updateEntry(
                serviceContext.getUserId(), serviceContext.getScopeGroupId(),
                assignment.getCreateDate(), assignment.getModifiedDate(),
                Assignment.class.getName(), assignment.getAssignmentId(),
                assignment.getUserUuid(), 0, serviceContext.getAssetCategoryIds(),
                serviceContext.getAssetTagNames(), true, true,
                assignment.getCreateDate(), null, null, null,
                ContentTypes.TEXT_HTML, assignment.getTitle(serviceContext.getLocale()),
                assignment.getDescription(), null, null, null, 0, 0,
                serviceContext.getAssetPriority()
        );
    }

    @Override
    public Assignment addAssignment(Assignment assignment) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Assignment updateAssignment(Assignment assignment) {
        throw new UnsupportedOperationException("Not supported.");
    }

}