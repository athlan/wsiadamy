package pl.wsiadamy.common.security.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.security.Permission;
import pl.wsiadamy.common.security.util.AthenticationUtil;

@Component
public class RouteParticipateCancelPermission implements Permission {

	@Autowired
	private RouteBO routeBO;
	
	@Override
	public boolean isAllowed(Authentication authentication, Object targetDomainObject) {

		Route route = getTargetDomain(targetDomainObject);
		User user = AthenticationUtil.getUser();
		
		if(route == null || user == null)
			return false;
		
		// he owner cannot cancel participanse
		if(route.getOwner().equals(user))
			return false;

		// already on list?
		boolean result = false;
		
		for (Participanse participanse : route.getParticipanses()) {
			if(participanse.getUser().equals(user)) {
				result = true;
				break;
			}
		}
		
		return result;
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
