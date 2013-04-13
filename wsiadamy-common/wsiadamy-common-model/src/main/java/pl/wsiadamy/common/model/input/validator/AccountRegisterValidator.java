package pl.wsiadamy.common.model.input.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.input.AccountRegisterInput;

@Component
public class AccountRegisterValidator implements Validator {

	@Autowired
	UserBO userBO;

	@Override
	public boolean supports(Class<?> clazz) {
		return AccountRegisterInput.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AccountRegisterInput input = (AccountRegisterInput) target;
		
		if(userBO.getByUsername(input.getEmail()) != null) {
			errors.rejectValue("email", "error.email.taken", "This email address is alerady taken");
		}
		
		if(!input.getPassword().equals(input.getPasswordConfirmation())) {
			errors.rejectValue("passwordConfirmation", "error.notmatch.password", "Passwords does not match");
		}
	}
}
