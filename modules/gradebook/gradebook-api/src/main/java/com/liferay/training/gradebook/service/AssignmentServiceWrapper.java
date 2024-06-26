/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.training.gradebook.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssignmentService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssignmentService
 * @generated
 */
public class AssignmentServiceWrapper
	implements AssignmentService, ServiceWrapper<AssignmentService> {

	public AssignmentServiceWrapper(AssignmentService assignmentService) {
		_assignmentService = assignmentService;
	}

	@Override
	public com.liferay.training.gradebook.model.Assignment addAssignment(
			long groupId, java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Date dueDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assignmentService.addAssignment(
			groupId, titleMap, descriptionMap, dueDate, serviceContext);
	}

	@Override
	public com.liferay.training.gradebook.model.Assignment deleteAssignment(
			long assignmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assignmentService.deleteAssignment(assignmentId);
	}

	@Override
	public long getAssigmentsCountByKeywords(long groupId, String keywords) {
		return _assignmentService.getAssigmentsCountByKeywords(
			groupId, keywords);
	}

	@Override
	public com.liferay.training.gradebook.model.Assignment getAssignment(
			long assignmentId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assignmentService.getAssignment(assignmentId);
	}

	@Override
	public java.util.List<com.liferay.training.gradebook.model.Assignment>
		getAssignmentsByGroupId(long groupId) {

		return _assignmentService.getAssignmentsByGroupId(groupId);
	}

	@Override
	public java.util.List<com.liferay.training.gradebook.model.Assignment>
		getAssignmentsByKeywords(
			long groupId, String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.training.gradebook.model.Assignment>
					orderByComparator) {

		return _assignmentService.getAssignmentsByKeywords(
			groupId, keywords, start, end, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assignmentService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.training.gradebook.model.Assignment updateAssignment(
			long assignmentId, java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			java.util.Date dueDate,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assignmentService.updateAssignment(
			assignmentId, titleMap, descriptionMap, dueDate, serviceContext);
	}

	@Override
	public AssignmentService getWrappedService() {
		return _assignmentService;
	}

	@Override
	public void setWrappedService(AssignmentService assignmentService) {
		_assignmentService = assignmentService;
	}

	private AssignmentService _assignmentService;

}