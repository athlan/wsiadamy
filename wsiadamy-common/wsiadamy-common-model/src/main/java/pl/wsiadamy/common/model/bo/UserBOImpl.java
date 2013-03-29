package pl.wsiadamy.common.model.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.common.PasswordCryptography;
import pl.wsiadamy.common.model.dao.UserDao;
import pl.wsiadamy.common.model.entity.User;

@Service("userBO")
public class UserBOImpl implements UserBO {
	@Autowired
	UserDao userDao;
	
	public User createUser(String username, String password) {
		User user = new User();
		
		user.setUsername(username);
		
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
		return userDao.getByUsername(username);
	}
	
	@Override
	public boolean authenticateUser(User user, String password) {
		if(null == user)
			return false;
		
		return PasswordCryptography.compare(user.getPassword(), password, user.getPasswordSalt());
	}
}
