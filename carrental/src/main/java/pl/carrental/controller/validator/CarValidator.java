package pl.carrental.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.carrental.domain.Car;
import pl.carrental.domain.User;


@Component
public class CarValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Car.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Car car = (Car) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mark", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "model", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "perDay", "NotEmpty");

    }
}
