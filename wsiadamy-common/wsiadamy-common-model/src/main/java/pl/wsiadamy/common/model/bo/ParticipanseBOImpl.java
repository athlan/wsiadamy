package pl.wsiadamy.common.model.bo;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.dao.ParticipanseDao;
import pl.wsiadamy.common.model.dao.RouteDao;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.ParticipanseRSPV;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;

@Service("participanseBO")
public class ParticipanseBOImpl implements ParticipanseBO {
	@Autowired
	ParticipanseDao participanseDao;
	
	@Autowired
	RouteDao routeDao;
	
	@Override
	public void save(Participanse participanse) {
		participanseDao.create(participanse);
	}
	
	@Override
	public void update(Participanse participanse) {
		participanseDao.update(participanse);
	}
	
	@Override
	public void delete(Participanse participanse) {
		participanseDao.remove(participanse);
	}

	@Override
	public Participanse getById(Integer id) {
		return participanseDao.get(id);
	}
	
	@Override
	public Participanse getByUserRoute(User participant, Route route) {
		return participanseDao.getParticipationByUser(participant, route);
	}

	@Override
	public boolean participateRoute(User participant, Route route) {
		Participanse participanse = new Participanse(participant, route);
		participanse.setUserSender(participant);
		participanse.setRspvDateSent(new Date());

		if(route.isParticipanseModeration()) {
			participanse.setRspvStatus(ParticipanseRSPV.PENDING);
		}
		else {
			participanse.setRspvStatus(ParticipanseRSPV.APPROVED);
			participanse.setRspvDateAccepted(new Date());
		}
		
		if(false == route.addParticipanse(participanse))
			return false;
		
		routeDao.update(route);
		
		return true;
	}

	@Override
	public boolean participateRouteInvite(User invitator, User participant, Route route) {
		Participanse participanse = new Participanse(participant, invitator, route);
		participanse.setRspvStatus(ParticipanseRSPV.PENDING);
		participanse.setRspvDateSent(new Date());
		
		if(false == route.addParticipanse(participanse))
			return false;
		
		routeDao.update(route);
		
		return true;
	}
	
	@Override
	public boolean participateRouteCancel(Participanse participanse) {
//		Participanse participanse = routeDao.get(id);

		if(null == participanse)
			return false;
		
		Route route = participanse.getRoute();
		
		if(false == route.removeParticipanse(participanse))
			return false;
		
		routeDao.update(route);
		
		return true;
	}
}
