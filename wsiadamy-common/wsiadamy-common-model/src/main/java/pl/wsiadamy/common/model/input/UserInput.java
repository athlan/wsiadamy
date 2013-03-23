package pl.wsiadamy.common.model.input;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import pl.wsiadamy.common.model.input.constraint.TestConstraint;

public class UserInput {
	
	@NotNull
	@Size(min=1,max=20)
	@Email
	@TestConstraint
	private String name;
	
	@Min(0)
    @Max(120)
    private int age;
	
	public UserInput(String name, int age) {
        this.name = name;
        this.age = age;
    }
 
    public UserInput() {
        name = "";
        age = 1;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
    
}
