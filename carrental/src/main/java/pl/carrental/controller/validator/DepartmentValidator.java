package pl.carrental.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.carrental.domain.Car;
import pl.carrental.domain.Department;


@Component
public class DepartmentValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Department.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Department department = (Department) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "no", "NotEmpty");

    }
}
