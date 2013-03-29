package pl.wsiadamy.common.model.dao;

import pl.wsiadamy.common.model.common.AbstractDao;
import pl.wsiadamy.common.model.entity.User;

public interface UserDao extends AbstractDao<User, Integer> {
	
	User getByUsername(String username);
}
