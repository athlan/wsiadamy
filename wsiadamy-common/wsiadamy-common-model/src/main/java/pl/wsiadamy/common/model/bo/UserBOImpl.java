package pl.wsiadamy.common.model.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.common.PasswordCryptography;
import pl.wsiadamy.common.model.dao.UserDao;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.entity.UserAccountScope;
import pl.wsiadamy.common.model.entity.UserData;
import pl.wsiadamy.common.model.entity.UserLogin;

@Service("userBO")
public class UserBOImpl implements UserBO {
	@Autowired
	UserDao userDao;

	public User createUser(String username, String password) {
		User user = new User();
		UserData userData = new UserData(user);
		UserLogin userLogin = new UserLogin(user);
		
		userLogin.setAccountScope(UserAccountScope.NATIVE);
		userLogin.setUsername(username);
		userLogin.setPassword(password);
		user.addLogin(userLogin);
		
		userData.setContactEmail(username);
		
		userDao.create(user);
		
		return user;
	}

	@Override
	public User createUserFacebook(Long facebookId, String email, String firstname, String lastname) {
		User user = new User();
		UserData userData = new UserData(user);
		UserLogin userLogin = new UserLogin(user);
		
		userLogin.setAccountScope(UserAccountScope.FACEBOOK);
		userLogin.setUsername(facebookId.toString());
		
		user.addLogin(userLogin);
		
		userData.setFacebookId(facebookId);
		userData.setContactEmail(email);
		userData.setFirstname(firstname);
		userData.setLastname(lastname);
		
		userDao.create(user);
		
		return user;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public void save(User user) {
		userDao.create(user);
	}
	
	@Override
	public void update(User user) {
		userDao.update(user);
	}
	
	@Override
	public void delete(User user) {
		userDao.remove(user);
	}

	@Override
	public User getById(Integer id) {
		return userDao.get(id);
	}

	@Override
	public UserLogin getByUsername(String username) {
		return this.getByUsername(username, UserAccountScope.NATIVE);
	}
	
	@Override
	public UserLogin getByUsername(String username, UserAccountScope scope) {
		return userDao.getByUsername(username, scope);
	}

	@Override
	public User getUserByUsername(String username) {
		return this.getUserByUsername(username, UserAccountScope.NATIVE);
	}
	
	@Override
	public User getUserByUsername(String username, UserAccountScope scope) {
		return userDao.getUserByUsername(username, scope);
	}
	
	@Override
	public boolean authenticateUser(UserLogin userLogin, String password) {
		if(null == userLogin)
			return false;
		
		return PasswordCryptography.compare(userLogin.getPassword(), password, userLogin.getPasswordSalt());
	}
}
