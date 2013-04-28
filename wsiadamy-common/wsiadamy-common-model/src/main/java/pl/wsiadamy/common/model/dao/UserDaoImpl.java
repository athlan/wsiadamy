package pl.wsiadamy.common.model.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.common.AbstractDaoImpl;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.entity.UserAccountScope;
import pl.wsiadamy.common.model.entity.UserLogin;

@Component
public class UserDaoImpl extends AbstractDaoImpl<User, Integer> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}
	
	@Override
	public UserLogin getByUsername(String username) {
		return this.getByUsername(username, UserAccountScope.NATIVE);
	}

	@Override
	public UserLogin getByUsername(String username, UserAccountScope scope) {
		Query query = getEntityManager()
			.createQuery("select u from UserLogin u join u.userOwner where u.username = :username and u.accountScope = :scope")
			.setParameter("username", username)
			.setParameter("scope", scope);
		
		List<UserLogin> result = query.getResultList();
		
		if(result.size() > 0)
			return result.get(0);
		
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		return this.getUserByUsername(username, UserAccountScope.NATIVE);
	}

	@Override
	public User getUserByUsername(String username, UserAccountScope scope) {
		UserLogin userLogin = getByUsername(username, scope);
		
		if(null == userLogin)
			return null;
		
		return userLogin.getUser();
	}

}
