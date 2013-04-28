package pl.wsiadamy.common.security.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.bo.ParticipanseBO;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.ParticipanseRSPV;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.security.Permission;
import pl.wsiadamy.common.security.util.AthenticationUtil;

@Component
public class RouteParticipateCancelPermission implements Permission {

	@Autowired
	private ParticipanseBO participanseBO;
	
	@Override
	public boolean isAllowed(Authentication authentication, Object targetDomainObject) {

		Participanse participanse = getTargetDomain(targetDomainObject);
		User user = AthenticationUtil.getUser();
		
		if(participanse == null || user == null)
			return false;
		
		if(!participanse.getUser().equals(user))
			return false;
		
		// he owner cannot cancel participanse
		User routeOwner = participanse.getRoute().getOwner();
		if(routeOwner.equals(user))
			return false;
		
		// already on list?
		boolean result = false;
		
		if(participanse.getUser().equals(user)) {
			if(participanse.getRspvStatus() == ParticipanseRSPV.APPROVED)
				result = true;
		}
		
		return result;
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