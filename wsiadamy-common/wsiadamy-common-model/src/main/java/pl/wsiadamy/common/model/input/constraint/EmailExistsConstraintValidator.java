package pl.wsiadamy.common.model.input.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pl.wsiadamy.common.model.bo.UserBO;

public class EmailExistsConstraintValidator implements ConstraintValidator<EmailExistsConstraint, String> {
	@Override
	public void initialize(EmailExistsConstraint firstUpper) {
		// See JSR 303 Section 2.4.1 for sample implementation.
	}
	
	@Autowired
	UserBO userBO;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}
		
		return userBO.getByUsername(value) == null;
	}
}
