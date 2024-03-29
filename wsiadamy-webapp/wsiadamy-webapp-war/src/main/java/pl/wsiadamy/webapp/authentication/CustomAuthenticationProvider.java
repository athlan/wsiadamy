package pl.wsiadamy.webapp.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.wsiadamy.common.model.bo.UserBO;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.entity.UserLogin;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	UserBO userBO;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;

		String username = String.valueOf(auth.getPrincipal());
		String password = String.valueOf(auth.getCredentials());
		
		UserLogin userLogin = userBO.getByUsername(username);
		
		if(null == userLogin)
			throw new BadCredentialsException("Wrong username or password");
		
		User user = userLogin.getUser();
		
		if(!userBO.authenticateUser(userLogin, password))
			throw new BadCredentialsException("Wrong username or password");
		
		return createToken(user);
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	public static UsernamePasswordAuthenticationToken createToken(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		
		if(null != user.getUserData())
			authorities.add(new GrantedAuthorityImpl("ROLE_USER_WITH_DATA"));
		
		return new UsernamePasswordAuthenticationToken(user, null, authorities);
	}
	
	public static void authenticateUser(User user) {
        Authentication authentication = CustomAuthenticationProvider.createToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
