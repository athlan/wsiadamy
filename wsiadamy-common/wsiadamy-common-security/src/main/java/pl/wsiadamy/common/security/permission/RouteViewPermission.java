package pl.wsiadamy.common.security.permission;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.wsiadamy.common.security.Permission;

@Component
public class RouteViewPermission implements Permission {

	@Override
	public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
		return true;
	}

}
