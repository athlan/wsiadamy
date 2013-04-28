package pl.wsiadamy.common.model.bo;

import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;

public interface ParticipanseBO {
	void save(Participanse participanse);
	void update(Participanse participanse);
	void delete(Participanse participanse);
	
	Participanse getById(Integer id);

	Participanse getByUserRoute(User participant, Route route);
	
	boolean participateRoute(User participant, Route route);
	
	boolean participateRouteCancel(Participanse participanse);
}
