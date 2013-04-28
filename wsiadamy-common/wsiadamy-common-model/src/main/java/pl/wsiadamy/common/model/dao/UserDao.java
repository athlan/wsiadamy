package pl.wsiadamy.common.model.dao;

import pl.wsiadamy.common.model.common.AbstractDao;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.entity.UserAccountScope;
import pl.wsiadamy.common.model.entity.UserLogin;

public interface UserDao extends AbstractDao<User, Integer> {

	UserLogin getByUsername(String username);
	UserLogin getByUsername(String username, UserAccountScope scope);
	
	User getUserByUsername(String username);
	User getUserByUsername(String username, UserAccountScope scope);
}
