package pl.wsiadamy.common.model.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.dao.UserDao;
import pl.wsiadamy.common.model.entity.User;

@Service("userBO")
public class UserBOImpl implements UserBO {
	@Autowired
	UserDao userDao;
	
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
		return null;
		//return userDao.save(user);
	}
}
