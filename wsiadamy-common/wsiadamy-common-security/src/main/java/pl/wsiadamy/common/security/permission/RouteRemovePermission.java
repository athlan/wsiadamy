package pl.wsiadamy.common.security.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.security.Permission;
import pl.wsiadamy.common.security.util.AthenticationUtil;

@Component
public class RouteRemovePermission implements Permission {

	@Autowired
	private RouteBO routeBO;
	
	@Override
	public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
		if(!(targetDomainObject instanceof Integer))
			return false;
		
		Route route = routeBO.getById((Integer) targetDomainObject);
		
		if(route == null)
			return false;
		
		User user = AthenticationUtil.getUser();
		
		return null != user && user.equals(route.getOwner());
	}

}
