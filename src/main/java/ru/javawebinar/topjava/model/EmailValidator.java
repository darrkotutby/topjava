package ru.javawebinar.topjava.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Component
public class EmailValidator implements
        ConstraintValidator<EmailConstraint, UserTo> {

    @Autowired
    UserService service;

    @Override
    public void initialize(EmailConstraint contactNumber) {
    }

    @Override
    public boolean isValid(UserTo userTo,
                           ConstraintValidatorContext cxt) {

        if (userTo.getEmail() == null) {
            //setValidationErrorMessage(cxt, "test");
            return false;
        }
        try {
            User user = service.getByEmail(userTo.getEmail());
            if (user != null && !user.getId().equals(userTo.getId())) {
                //setValidationErrorMessage(cxt, "test1");
                //cxt.buildConstraintViolationWithTemplate("{email.validation.error}").addConstraintViolation();

                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate("{email.validation.error}")
                        .addPropertyNode("email").addConstraintViolation();

                return false;
            }
            return true;
        } catch (NotFoundException e) {
            return true;
        }

    }

    private void setValidationErrorMessage(ConstraintValidatorContext context, String template) {
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate("{" + template + "}")
                .addConstraintViolation();
    }

}