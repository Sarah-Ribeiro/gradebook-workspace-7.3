package com.liferay.training.gradebook.util.validator;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.training.gradebook.exception.AssignmentValidationException;
import com.liferay.training.gradebook.validator.AssignmentValidator;
import org.osgi.service.component.annotations.Component;

import java.util.*;

@Component(

        immediate = true,
        service = AssignmentValidator.class

)
public class AssignmentValidatorImpl implements AssignmentValidator {

//    Validates assignment values and throws
//    {AssignmentValidationException} if assignment values are not valid.

    public void validate(Map<Locale, String> titleMap, Map<Locale, String> descriptionMap, Date dueDate) throws AssignmentValidationException {
        List<String> errors = new ArrayList<>();

        if (!isAssignmentValid(titleMap, descriptionMap, dueDate, errors)) {
            throw new AssignmentValidationException(errors);
        }
    }

    private boolean isAssignmentValid(final Map<Locale, String> titleMap, final Map<Locale, String> descriptionMap
            , final Date dueDate, final List<String> errors) {
        boolean result = true;

        result &= isTitleValid(titleMap, errors);
        result &= isDueDateValid(dueDate, errors);
        result &= isDescriptionValid(descriptionMap, errors);

        return result;
    }

    private boolean isDescriptionValid(final Map<Locale, String> descriptionMap, final List<String> errors) {

        boolean result = true;

//        Verify the Title has something

        Locale defaultLocale = LocaleUtil.getSiteDefault();

        if (Validator.isBlank(descriptionMap.get(defaultLocale))) {
            errors.add("assignmentTitleEmpty");
            result = false;
        }

        return result;

    }

    private boolean isDueDateValid(final Date dueDate, final List<String> errors) {

        boolean result = true;

        if (Validator.isNull(dueDate)) {
            errors.add("assignmentDateEmpty");
            result = false;
        }

        return result;

    }

    private boolean isTitleValid(final Map<Locale, String> titleMap, final List<String> errors) {
        boolean result = true;

//        Verify the Title has something

        Locale defaultLocale = LocaleUtil.getSiteDefault();

        if (Validator.isBlank(titleMap.get(defaultLocale))) {
            errors.add("assignmentTitleEmpty");
            result = false;
        }

        return result;
    }

}