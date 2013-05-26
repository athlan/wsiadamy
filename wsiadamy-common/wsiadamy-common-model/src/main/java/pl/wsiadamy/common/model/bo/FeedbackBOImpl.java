package pl.wsiadamy.common.model.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.wsiadamy.common.model.dao.FeedbackDao;
import pl.wsiadamy.common.model.dao.ParticipanseDao;
import pl.wsiadamy.common.model.entity.Feedback;
import pl.wsiadamy.common.model.entity.Participanse;
import pl.wsiadamy.common.model.entity.ParticipanseRSPV;
import pl.wsiadamy.common.model.entity.Route;
import pl.wsiadamy.common.model.entity.User;
import pl.wsiadamy.common.model.input.FeedbackInput;

@Service("feedbackBO")
public class FeedbackBOImpl implements FeedbackBO {

	@Autowired
	FeedbackDao feedbackDao;

	@Autowired
	ParticipanseDao participanseDao;
	
	@Override
	public Feedback createFeedback(Participanse participanse, User user,
			User feedbackAuthor, FeedbackInput input) {
		Feedback feedback = new Feedback(user, feedbackAuthor, participanse.getRoute(), input.getValue());
		
		feedback.setDateCreated(new Date());
		
		fillRoute(feedback, input);
		
		feedbackDao.create(feedback);
		
		// sync participanse
		if(participanse.getRoute().getOwner().equals(feedbackAuthor)) {
			participanse.setFeedbackDriver(feedback);
		}
		else if(participanse.getUser().equals(user)) {
			participanse.setFeedbackParticipant(feedback);
		}
		else
			throw new RuntimeException("Trying to add feedback neither owner and participant.");
		
		participanseDao.update(participanse);
		
		return feedback;
	}

	@Override
	public Feedback editFeedback(Integer id, FeedbackInput input) {
		Feedback feedback = feedbackDao.get(id);
		
		fillRoute(feedback, input);
		
		feedbackDao.update(feedback);
		
		return feedback;
	}
	
	private void fillRoute(Feedback feedback, FeedbackInput input) {
		feedback.setVote(input.getValue());
		
		if(null == input.getComment() || input.getComment().equals("")) {
			feedback.setComment(null);
		}
		else {
			feedback.setComment(input.getComment());
		}
	}

	@Override
	public void save(Feedback feedback) {
		feedbackDao.create(feedback);
	}

	@Override
	public void update(Feedback feedback) {
		feedbackDao.update(feedback);
	}

	@Override
	public void delete(Feedback feedback) {
		feedbackDao.remove(feedback);
	}

	@Override
	public Feedback getById(Integer id) {
		return feedbackDao.get(id);
	}
	
	@Override
	public List<Route> listRoutesToFeedback(Map<String, Object> params, int limit, int offset) {
		return feedbackDao.listRoutesToFeedback(params, limit, offset);
	}

	@Override
	public Long listRoutesToFeedbackCount(Map<String, Object> params) {
		return feedbackDao.listRoutesToFeedbackCount(params);
	}

	@Override
	public List<Participanse> getParticipansesToFeedback(Route route) {
		List<Participanse> result = new ArrayList<Participanse>();
		
		for (Participanse participanse : route.getParticipanses()) {
			if(participanse.getRspvStatus() == ParticipanseRSPV.APPROVED && participanse.getFeedbackDriver() == null)
				result.add(participanse);
		}
		
		return result;
	}

}
