package pl.wsiadamy.common.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import pl.wsiadamy.common.model.common.AbstractEntity;
import pl.wsiadamy.common.model.common.PasswordCryptography;

@Entity
@Table(name = "users")
public class User extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "userOwner", cascade = CascadeType.ALL)
	private List<UserLogin> logins;
	
	@Column(length = 32)
	private String password;
	
	@Column(length = 32)
	private String password_salt;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private UserData userData;
	
	public User() {
		logins = new ArrayList<UserLogin>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		if(null == userData)
			return "";
		
		if(null != userData.getFirstname() && null != userData.getLastname())
			return userData.getFirstname() + " " + userData.getLastname();
		
		if(null != userData.getContactEmail())
			return userData.getContactEmail();
		
		return "";
	}

	public void setUsername(String username) {
		throw new RuntimeException("Cannot ser username. Try to user.getLogins().");
	}
	
	public List<UserLogin> getLogins() {
		return logins;
	}

	public void addLogin(UserLogin login) {
		this.logins.add(login);
	}

	public void removeLogin(UserLogin login) {
		this.logins.remove(login);
	}

	public void clearLogin() {
		this.logins.clear();
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		String passwordSalt = PasswordCryptography.getSalt(password);
		
		this.setPasswordSalt(passwordSalt);
		this.password = PasswordCryptography.encode(password, passwordSalt);
	}

	public String getPasswordSalt() {
		return password_salt;
	}
	
	private void setPasswordSalt(String passwordSalt) {
		this.password_salt = passwordSalt;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		
		if(!(obj instanceof User))
			return false;
		
		User objCast = (User) obj;
		
		return objCast.getId().equals(this.getId());
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}
}
