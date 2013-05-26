package pl.wsiadamy.common.model.bo;

import java.util.List;
import java.util.Map;

import pl.wsiadamy.common.model.entity.Feedback;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.FeedbackInput;

public interface FeedbackBO {
	void save(Feedback feedback);
	void update(Feedback feedback);
	void delete(Feedback feedback);
	
	Feedback getById(Integer id);
	
	Feedback createFeedback(Participanse participanse, User user, User feedbackAuthor, FeedbackInput input);
	
	Feedback editFeedback(Integer id, FeedbackInput input);

	List<Route> listRoutesToFeedback(Map<String, Object> params, int limit, int offset);

	Long listRoutesToFeedbackCount(Map<String, Object> params);
	
}
