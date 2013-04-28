package pl.wsiadamy.common.model.dao;

import pl.wsiadamy.common.model.common.AbstractDao;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;

public interface ParticipanseDao extends AbstractDao<Participanse, Integer> {

	Participanse getParticipationByUser(User participant, Route route);
}
