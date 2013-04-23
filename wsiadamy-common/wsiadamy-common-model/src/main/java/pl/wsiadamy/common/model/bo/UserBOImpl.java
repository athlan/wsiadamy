package pl.wsiadamy.common.model.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.common.PasswordCryptography;
import pl.wsiadamy.common.model.dao.UserDao;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.entity.UserAccountScope;
import pl.wsiadamy.common.model.entity.UserData;

@Service("userBO")
public class UserBOImpl implements UserBO {
	@Autowired
	UserDao userDao;

	public User createUser(String username, String password) {
		User user = new User();
		UserData userData = new UserData(user);
		
		user.setUsername(username);
		userData.setContactEmail(username);
		
		userDao.create(user);
		
		return user;
	}

	@Override
	public User createUserFacebook(Long facebookId, String email, String firstname, String lastname) {
		User user = new User();
		UserData userData = new UserData(user);
		
		user.setAccountScope(UserAccountScope.FACEBOOK);
		user.setUsername(facebookId.toString());
		
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
	public User getByUsername(String username) {
		return this.getByUsername(username, UserAccountScope.NATIVE);
	}
	
	@Override
	public User getByUsername(String username, UserAccountScope scope) {
		return userDao.getByUsername(username, scope);
	}
	
	@Override
	public boolean authenticateUser(User user, String password) {
		if(null == user)
			return false;
		
		return PasswordCryptography.compare(user.getPassword(), password, user.getPasswordSalt());
	}
}
