package uz.java.backendtask.config;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PublishWindowValidator.class)
public @interface ValidPublishWindow {
    String message() default "publishAt must be before unpublishAt";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
