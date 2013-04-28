package pl.wsiadamy.common.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pl.wsiadamy.common.model.common.AbstractEntity;
import pl.wsiadamy.common.model.common.PasswordCryptography;

@Entity
@Table(name = "users_logins")
public class UserLogin extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private User userOwner;
	
	@Column(length = 255)
	private String username;

	@Column
	private UserAccountScope accountScope;

	@Column(length = 32)
	private String password;
	
	@Column(length = 32)
	private String password_salt;

	public UserLogin() {
	}

	public UserLogin(User user) {
		setUser(user);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return userOwner;
	}

	public void setUser(User user) {
		this.userOwner = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public UserAccountScope getAccountScope() {
		return accountScope;
	}

	public void setAccountScope(UserAccountScope accountScope) {
		this.accountScope = accountScope;
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

	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		
		if(!(obj instanceof UserLogin))
			return false;
		
		UserLogin objCast = (UserLogin) obj;
		
		return objCast.getId().equals(this.getId());
	}
	
	@Override
	public String toString() {
		return "UserLogin [id=" + id + "]";
	}
}
