package com.dragonappear.inha.domain.user.validation;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.FIELD;


@Constraint(validatedBy = Email.EmailValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface Email {

    String message() default "이메일이 양식에 맞지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

    @Slf4j
    class EmailValidator implements ConstraintValidator<Email, String> {
        private final String REGEX_EMAIL = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}";
        public Pattern email = Pattern.compile(REGEX_EMAIL);

        @Override
        public boolean isValid(String s,
                               ConstraintValidatorContext constraintValidatorContext) {
            log.info("email validation");
            if( ObjectUtils.isEmpty(s)) {
                return true;
            } else {
                return email.matcher(s).matches();
            }
        }
    }

}