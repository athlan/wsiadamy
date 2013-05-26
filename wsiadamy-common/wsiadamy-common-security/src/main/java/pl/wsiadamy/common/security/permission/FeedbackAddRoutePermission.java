package pl.wsiadamy.common.security.permission;

import java.util.Date;

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
public class FeedbackAddRoutePermission implements Permission {

	@Autowired
	private RouteBO routeBO;

	@Autowired
	private ParticipanseBO participanseBO;
	
	@Override
	public boolean isAllowed(Authentication authentication, Object targetDomainObject) {

		Route route = getTargetDomain(targetDomainObject);
		User user = AthenticationUtil.getUser();
		
		if(route == null || user == null)
			return false;
		
		// route departure have to be in past
		if(!route.getDateDeparture().before(new Date()))
			return false;
		
		Participanse participanse = participanseBO.getByUserRoute(user, route);
		
		if(null == participanse)
			return false;
		
		if(participanse.getRspvStatus() != ParticipanseRSPV.APPROVED)
			return false;
		
		if(route.getOwner().equals(user)) {
			if(route.getRouteDetails().getFeedbackCountDriver() >= route.getSeatsParticipants())
				return false; // all feedbacks has been given
		}
		else {
			if(null != participanse.getFeedbackParticipant())
				return false; // feedback has been already given
		}
		
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
