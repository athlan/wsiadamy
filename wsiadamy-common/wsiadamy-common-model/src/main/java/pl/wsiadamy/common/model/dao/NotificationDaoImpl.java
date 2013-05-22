package pl.wsiadamy.common.model.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import pl.wsiadamy.common.model.common.AbstractDaoMongodbImpl;
import pl.wsiadamy.common.model.entity.Notification;
import pl.wsiadamy.common.model.entity.User;

@Component
public class NotificationDaoImpl extends AbstractDaoMongodbImpl<Notification> implements NotificationDao {
	
	private static final String collectionName = "notifications";
	
	public NotificationDaoImpl() {
		super(Notification.class, collectionName);
	}
	
	@Override
	public List<Notification> getNotificationsByUser(User user) {
		Query query = new Query(Criteria.where("userReceiver").is(user.getId()));
		return getDocumentManager().find(query, getEntityClass(), getCollectionName());
	}
}
