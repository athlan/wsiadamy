package pl.wsiadamy.webapp.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.User;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	UserBO userBO;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());
		
		User user = userBO.getByUsername(username);

		if(null == user)
			throw new BadCredentialsException("Wrong username or password");

		if(!userBO.authenticateUser(user, password))
			throw new BadCredentialsException("Wrong username or password");
		
		throw new BadCredentialsException("OK");
//		return new UsernamePasswordAuthenticationToken(null, null) ;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
