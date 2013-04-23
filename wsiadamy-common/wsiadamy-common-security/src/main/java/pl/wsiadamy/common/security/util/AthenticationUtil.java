package pl.wsiadamy.common.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.entity.User;

@Component
public class AthenticationUtil {
	public static User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Object principal = auth.getPrincipal();
		
		if(principal instanceof User)
			return (User) principal;
		
		return null;
	}
}
