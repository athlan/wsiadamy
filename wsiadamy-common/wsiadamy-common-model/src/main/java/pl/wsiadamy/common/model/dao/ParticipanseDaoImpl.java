package pl.wsiadamy.common.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.common.AbstractDaoImpl;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;

@Component
public class ParticipanseDaoImpl extends AbstractDaoImpl<Participanse, Integer> implements ParticipanseDao {

	public ParticipanseDaoImpl() {
		super(Participanse.class);
	}
	
	@Override
	public Participanse getParticipationByUser(User participant, Route route) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Participanse> q = cb.createQuery(Participanse.class);
		Root<Participanse> root = q.from(Participanse.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		
		predicates.add(cb.equal(root.get("user"), participant.getId()));
		predicates.add(cb.equal(root.get("route"), route.getId()));
		
		if(predicates.size() > 0)
			q.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
		TypedQuery<Participanse> tq = getEntityManager().createQuery(q);
		
		try {
			return tq.getSingleResult();
		}
		catch(Throwable e) {
			return null;
		}
	}
}
