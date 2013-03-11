package pl.wsiadamy.common.model.bo;

import pl.wsiadamy.common.model.entity.User;

public interface UserBO {
	void save(User user);
	void update(User user);
	void delete(User user);
	
	User getById(Integer id);
}
