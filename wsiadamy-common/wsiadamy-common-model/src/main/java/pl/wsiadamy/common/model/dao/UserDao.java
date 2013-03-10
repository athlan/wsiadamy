package pl.wsiadamy.common.model.dao;

import pl.wsiadamy.common.model.User;

public interface UserDao {
	void save(User user);
	void update(User user);
	void delete(User user);
	
	User getById(Integer id);
}
