package pl.wsiadamy.common.model.input.constraint;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
 
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
 
import javax.validation.Constraint;
import javax.validation.Payload;
 
@Documented
@Constraint(validatedBy = EmailExistsConstraintValidator.class)
@Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailExistsConstraint {
 String message() default "{pl.wsiadamy.common.model.input.constraint.EmailExistsConstraint.message}";
 
 Class<?>[] groups() default {};
 
 Class<? extends Payload>[] payload() default {};
}