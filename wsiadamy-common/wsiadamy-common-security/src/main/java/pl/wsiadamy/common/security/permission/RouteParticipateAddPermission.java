package pl.wsiadamy.common.security.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.bo.ParticipanseBO;
import pl.wsiadamy.common.model.bo.RouteBO;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.ParticipanseRSPV;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.security.Permission;
import pl.wsiadamy.common.security.util.AthenticationUtil;

@Component
public class RouteParticipateAddPermission implements Permission {

	@Autowired
	private RouteBO routeBO;

	@Autowired
	private ParticipanseBO participanseBO;
	
	@Override
	public boolean isAllowed(Authentication authentication, Object targetDomainObject) {

		Route route = getTargetDomain(targetDomainObject);
		User user = AthenticationUtil.getUser();
		
		if(user == null)
			return true;
		
//		if(route == null || user == null)
		if(route == null)
			return false;
		
		Participanse participanse = participanseBO.getByUserRoute(user, route);
		
		if(null != participanse) {
			if(participanse.getRspvStatus() != ParticipanseRSPV.REJECTED)
				return false;
		}
		
		if(route.getSeatsAvailable() == 0)
			return false;
		
		if(route.getOwner().equals(user))
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
