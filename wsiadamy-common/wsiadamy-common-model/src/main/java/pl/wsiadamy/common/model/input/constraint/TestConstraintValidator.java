package pl.wsiadamy.common.model.input.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import pl.wsiadamy.common.model.bo.UserBO;

public class TestConstraintValidator implements ConstraintValidator<TestConstraint, String> {
	@Override
	public void initialize(TestConstraint firstUpper) {
		// See JSR 303 Section 2.4.1 for sample implementation.
	}
	
	@Autowired
	UserBO userBO;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}
		
//		userBO.createUser("valid", "aa");
		
		return value.substring(0, 1).equals(value.substring(0, 1).toUpperCase());
	}
}
