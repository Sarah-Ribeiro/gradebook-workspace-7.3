package com.liferay.training.gradebook.validator;

import com.liferay.training.gradebook.exception.AssignmentValidationException;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

public interface AssignmentValidator {

//    Validates an Assignment

    public void validate(Map<Locale, String> titleMap, Map<Locale, String> descriptionMap, Date dueDate) throws AssignmentValidationException;

}