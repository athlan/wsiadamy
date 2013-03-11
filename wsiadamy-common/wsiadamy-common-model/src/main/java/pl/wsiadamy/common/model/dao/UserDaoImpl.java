package pl.wsiadamy.common.model.dao;

import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.common.AbstractDaoImpl;
import pl.wsiadamy.common.model.entity.User;

@Component
public class UserDaoImpl extends AbstractDaoImpl<User, Integer> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

}
