package pl.wsiadamy.common.model.input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AccountDataInput {
	
	@NotNull
	@Length(min=3)
	private String firstname;
	
	@NotNull
	@Length(min=3)
	private String lastname;
	
	private Calendar birthday;
	
	private SimpleDateFormat birthdayFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	@NotNull
	@Length(min=3)
	private String contactPhone;
	
    public AccountDataInput() {
    }

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBirthday() {
		if(null == this.birthday)
			return "";
		
		return birthdayFormat.format(this.birthday.getTime());
	}
	
	public Calendar getBirthdayObject() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}

	public void setBirthday(String birthday) throws ParseException {
		setBirthday(Calendar.getInstance());
		this.birthday.setTime(birthdayFormat.parse(birthday));
	}
	
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

}
