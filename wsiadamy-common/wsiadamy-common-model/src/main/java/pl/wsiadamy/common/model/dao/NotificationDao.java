package pl.wsiadamy.common.model.dao;

import java.util.List;

import pl.wsiadamy.common.model.common.AbstractDao;
import pl.wsiadamy.common.model.entity.Notification;
import pl.wsiadamy.common.model.entity.User;

public interface NotificationDao extends AbstractDao<Notification, String> {

	List<Notification> getNotificationsByUser(User user);
}
