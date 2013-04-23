package pl.wsiadamy.common.model.bo;

import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.entity.UserAccountScope;

public interface UserBO {
	void save(User user);
	void update(User user);
	void delete(User user);
	
	User getById(Integer id);
	User getByUsername(String username);
	User getByUsername(String username, UserAccountScope scope);
	
	User createUser(String username, String password);
	User createUserFacebook(Long facebookId, String email, String firstname, String lastname);
	
	boolean authenticateUser(User user, String password);
}
