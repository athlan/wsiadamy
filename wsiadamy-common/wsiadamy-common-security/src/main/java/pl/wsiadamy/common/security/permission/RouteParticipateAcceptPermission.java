package pl.wsiadamy.common.security.permission;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.bo.ParticipanseBO;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.ParticipanseRSPV;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.security.Permission;
import pl.wsiadamy.common.security.util.AthenticationUtil;

@Component
public class RouteParticipateAcceptPermission implements Permission {

	@Autowired
	private ParticipanseBO participanseBO;
	
	@Override
	public boolean isAllowed(Authentication authentication, Object targetDomainObject) {

		Participanse participanse = getTargetDomain(targetDomainObject);
		User user = AthenticationUtil.getUser();
		
		if(participanse == null || user == null || !participanse.getUser().equals(user))
			return false;
		
		Route route = participanse.getRoute();
		
		if(route.getDateDeparture().before(new Date()))
			return false;
		
		if(participanse.getRspvStatus() != ParticipanseRSPV.PENDING)
			return false;

		if(participanse.getUserSender() != null && participanse.getUserSender().equals(user))
			return false;
		
		return true;
	}
	
	protected Participanse getTargetDomain(Object targetDomainObject) {
		if(targetDomainObject instanceof Participanse) {
			return (Participanse) targetDomainObject;
		}
		
		if(targetDomainObject instanceof Integer) {
			return participanseBO.getById((Integer) targetDomainObject);
		}
		
		return null;
	}
}
