package pl.wsiadamy.common.model.bo;

import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.entity.UserAccountScope;
import pl.wsiadamy.common.model.entity.UserLogin;

public interface UserBO {
	void save(User user);
	void update(User user);
	void delete(User user);
	
	User getById(Integer id);
	
	UserLogin getByUsername(String username);
	UserLogin getByUsername(String username, UserAccountScope scope);

	User getUserByUsername(String username);
	User getUserByUsername(String username, UserAccountScope scope);
	
	User createUser(String username, String password);
	User createUserFacebook(Long facebookId, String email, String firstname, String lastname);
	
	boolean authenticateUser(UserLogin userLogin, String password);
}
