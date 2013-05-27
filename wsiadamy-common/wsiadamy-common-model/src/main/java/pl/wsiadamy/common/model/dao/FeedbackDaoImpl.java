package pl.wsiadamy.common.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.common.AbstractDaoJpaImpl;
import pl.wsiadamy.common.model.entity.Feedback;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.ParticipanseRSPV;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.RouteDetails;
import pl.wsiadamy.common.model.entity.User;

@Component
public class FeedbackDaoImpl extends AbstractDaoJpaImpl<Feedback, Integer> implements FeedbackDao {

	public FeedbackDaoImpl() {
		super(Feedback.class);
	}
	
	@Override
	public List<Route> listRoutesToFeedback(Map<String, Object> params, int limit, int offset) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Route> q = cb.createQuery(Route.class);
		Root<Participanse> from = q.from(Participanse.class);

		Join<Participanse, Route> joinRoute = from.join("route");
		Join<Route, RouteDetails> joinRouteDetails = joinRoute.join("routeDetails");
		
		q.select(joinRoute);
		
		// predicates
		Predicate where = cb.conjunction();
		
		if(null != params) {
			if(params.containsKey("participantId")) {
				where = cb.and(where, cb.equal(from.get("user"), params.get("participantId")));
				
				where = cb.and(where, cb.or(
					cb.and(cb.equal(joinRoute.get("owner"), params.get("participantId")), cb.notEqual(joinRoute.get("seatsParticipants"), joinRouteDetails.get("feedbackCountDriver"))),
					cb.and(cb.notEqual(joinRoute.get("owner"), params.get("participantId")), cb.isNull(from.get("feedbackParticipant")))
				));
			}
			
			if(params.containsKey("dateDepartureBefore")) {
				where = cb.and(where, cb.lessThanOrEqualTo(joinRoute.<Date>get("dateDeparture"), (Date) params.get("dateDepartureBefore")));
			}
		}
		
		where = cb.and(where, cb.equal(from.get("rspvStatus"), ParticipanseRSPV.APPROVED));
		
		q.where(where);
		
		q.orderBy(cb.desc(joinRoute.get("dateDeparture")));
		q.groupBy(joinRoute.get("id"));
		
		TypedQuery<Route> tq = getEntityManager().createQuery(q);
		
		tq.setMaxResults(limit);
		tq.setFirstResult(offset);
		
		return tq.getResultList();
	}

	@Override
	public Long listRoutesToFeedbackCount(Map<String, Object> params) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		Root<Participanse> from = q.from(Participanse.class);
		
		Join<Participanse, Route> joinRoute = from.join("route");
		Join<Route, RouteDetails> joinRouteDetails = joinRoute.join("routeDetails");
		
		q.select(cb.count(joinRoute));
		
		// predicates
		Predicate where = cb.conjunction();
		if(null != params) {
			if(params.containsKey("participantId")) {
				where = cb.and(where, cb.equal(from.get("user"), params.get("participantId")));

				where = cb.and(where, cb.or(
					cb.and(cb.equal(joinRoute.get("owner"), params.get("participantId")), cb.notEqual(joinRoute.get("seatsParticipants"), joinRouteDetails.get("feedbackCountDriver"))),
					cb.and(cb.notEqual(joinRoute.get("owner"), params.get("participantId")), cb.isNull(from.get("feedbackParticipant")))
				));
			}
			
			if(params.containsKey("dateDepartureBefore")) {
				where = cb.and(where, cb.lessThanOrEqualTo(joinRoute.<Date>get("dateDeparture"), (Date) params.get("dateDepartureBefore")));
			}
		}

		where = cb.and(where, cb.equal(from.get("rspvStatus"), ParticipanseRSPV.APPROVED));
		
		q.where(where);
		
		return getEntityManager().createQuery(q).getSingleResult();
	}
	
	@Override
	public List<Feedback> listFeedback(Map<String, Object> params, int limit, int offset) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Feedback> q = cb.createQuery(Feedback.class);
		Root<Feedback> from = q.from(Feedback.class);

		Join<User, Feedback> joinUser = from.join("user");
		Join<User, Feedback> joinUserSender = from.join("userSender");
		
		q.select(from);
		
		// predicates
		Predicate where = cb.conjunction();
		
		if(null != params) {
			if(params.containsKey("userId")) {
				where = cb.and(where, cb.equal(joinUser.get("id"), params.get("userId")));
			}
		}
		
		q.where(where);
		
		q.orderBy(cb.desc(from.get("dateCreated")));
		
		TypedQuery<Feedback> tq = getEntityManager().createQuery(q);
		
		tq.setMaxResults(limit);
		tq.setFirstResult(offset);
		
		return tq.getResultList();
	}

	@Override
	public Long listFeedbackCount(Map<String, Object> params) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Long> q = cb.createQuery(Long.class);
		Root<Feedback> from = q.from(Feedback.class);

		Join<User, Feedback> joinUser = from.join("user");
		Join<User, Feedback> joinUserSender = from.join("userSender");
		
		q.select(cb.count(from));
		
		// predicates
		Predicate where = cb.conjunction();
		if(null != params) {
			if(params.containsKey("userId")) {
				where = cb.and(where, cb.equal(joinUser.get("id"), params.get("userId")));
			}
		}
		
		q.where(where);
		
		return getEntityManager().createQuery(q).getSingleResult();
	}
}
