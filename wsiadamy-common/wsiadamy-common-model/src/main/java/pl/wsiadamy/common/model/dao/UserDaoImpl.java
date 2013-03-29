package pl.wsiadamy.common.model.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.common.AbstractDaoImpl;
import pl.wsiadamy.common.model.entity.User;

@Component
public class UserDaoImpl extends AbstractDaoImpl<User, Integer> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	public User getByUsername(String username) {
		Query query = getEntityManager()
			.createQuery("select u from User u where u.username = :username")
			.setParameter("username", username);
		
		List<User> result = query.getResultList();
		
		if(result.size() > 0)
			return result.get(0);
		
		return null;
	}

}
