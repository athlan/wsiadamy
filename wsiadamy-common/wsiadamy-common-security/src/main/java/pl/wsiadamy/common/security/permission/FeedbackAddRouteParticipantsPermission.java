package pl.wsiadamy.common.security.permission;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.security.Permission;
import pl.wsiadamy.common.security.util.AthenticationUtil;

@Component
public class FeedbackAddRouteParticipantsPermission implements Permission {

	@Autowired
	private RouteBO routeBO;

	@Override
	public boolean isAllowed(Authentication authentication, Object targetDomainObject) {

		Route route = getTargetDomain(targetDomainObject);
		User user = AthenticationUtil.getUser();
		
		if(route == null || user == null)
			return false;
		
		// route departure have to be in past
		if(!route.getDateDeparture().before(new Date()))
			return false;
		
		if(!route.getOwner().equals(user))
			return false;
		
		if(route.getRouteDetails().getFeedbackCountDriver() >= route.getSeatsParticipants())
			return false; // all feedbacks has been given
		
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
