package pl.wsiadamy.common.model.input;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class AccountRegisterInput {
	
	@NotNull
	@Email
	private String email;

	@NotNull
	@Length(min=1)
	private String password;

	@NotNull
	@Length(min=1)
	
	private String passwordConfirmation;
	
	@AssertTrue(message="passwords must match")
	public boolean testValid() {
		return true;
	}
	
    public AccountRegisterInput() {
    	setEmail("");
    	setPassword("");
    	setPasswordConfirmation("");
    }

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
}
