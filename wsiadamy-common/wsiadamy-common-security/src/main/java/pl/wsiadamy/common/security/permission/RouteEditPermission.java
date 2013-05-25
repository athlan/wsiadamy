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
public class RouteEditPermission implements Permission {

	@Autowired
	private RouteBO routeBO;
	
	@Override
	public boolean isAllowed(Authentication authentication, Object targetDomainObject) {
		Route route = getTargetDomain(targetDomainObject);
		User user = AthenticationUtil.getUser();
		
		if(route == null || user == null)
			return false;
		
		if(!route.getOwner().equals(user))
			return false;
		
		return true;
	}

	protected Route getTargetDomain(Object targetDomainObject) {
		if(targetDomainObject instanceof Route) {
			return (Route) targetDomainObject;
		}
		
		if(targetDomainObject instanceof Integer) {
			return routeBO.getById((Integer) targetDomainObject);
		}
		
		return null;
	}
}
