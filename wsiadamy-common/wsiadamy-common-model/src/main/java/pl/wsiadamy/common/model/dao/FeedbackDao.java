package pl.wsiadamy.common.model.dao;

import java.util.List;
import java.util.Map;

import pl.wsiadamy.common.model.common.AbstractDao;
import pl.wsiadamy.common.model.entity.Feedback;
import pl.wsiadamy.common.model.entity.Route;

public interface FeedbackDao extends AbstractDao<Feedback, Integer> {

	List<Route> listRoutesToFeedback(Map<String, Object> params, int limit, int offset);
	
	Long listRoutesToFeedbackCount(Map<String, Object> params);
	
}
