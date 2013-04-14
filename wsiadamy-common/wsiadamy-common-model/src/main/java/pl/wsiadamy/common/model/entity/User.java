package pl.wsiadamy.common.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pl.wsiadamy.common.model.common.AbstractEntity;
import pl.wsiadamy.common.model.common.PasswordCryptography;

@Entity
@Table(name = "users")
public class User extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 255)
	private String username;

	@Column(length = 32)
	private String password;
	
	@Column(length = 32)
	private String password_salt;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private UserData userData;
	
	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	public String toString() {
		return "User [id=" + id + "]";
	}
}
