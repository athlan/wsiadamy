package pl.wsiadamy.common.model.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import pl.wsiadamy.common.model.common.AbstractEntity;

@Entity
@Table(name = "participanse")
public class Participanse extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@OneToOne
	private User user;

	@OneToOne
	private User userSender;
	
	@OneToOne
	private Route route;
	
	@Column
	private Date rspvDateSent;

	@Column
	private Date rspvDateAccepted;
	
	@Column
	private ParticipanseRSPV rspvStatus;

	public Participanse() {
	}

	public Participanse(User user, Route route) {
		setUser(user);
		setUserSender(user);
		setRoute(route);
	}

	public Participanse(User user, User userSender, Route route) {
		setUser(user);
		setUserSender(userSender);
		setRoute(route);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	private void setUser(User user) {
		this.user = user;
	}

	public User getUserSender() {
		return userSender;
	}

	public void setUserSender(User userSender) {
		this.userSender = userSender;
	}

	public Route getRoute() {
		return route;
	}

	private void setRoute(Route route) {
		this.route = route;
	}

	public Date getRspvDateSent() {
		return rspvDateSent;
	}

	public void setRspvDateSent(Date rspvDateSent) {
		this.rspvDateSent = rspvDateSent;
	}

	public Date getRspvDateAccepted() {
		return rspvDateAccepted;
	}

	public void setRspvDateAccepted(Date rspvDateAccepted) {
		this.rspvDateAccepted = rspvDateAccepted;
	}

	public ParticipanseRSPV getRspvStatus() {
		return rspvStatus;
	}
	
	public void setRspvStatus(ParticipanseRSPV rspvStatus) {
		this.rspvStatus = rspvStatus;
	}
	
	@PrePersist
	@PreUpdate
	public void recalculateRouteSeatsAvailable() {
		getRoute().prePersistRecalculateSeatsAvailable();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		
		if(!(obj instanceof Participanse))
			return false;
		
		Participanse objCast = (Participanse) obj;
		
		return (
			null != objCast.getUser() && null != this.getUser()
			&& objCast.getUser().getId().equals(this.getUser().getId())
			&& null != objCast.getRoute() && null != this.getRoute()
			&& objCast.getRoute().getId().equals(this.getRoute().getId())
		);
	}
	
	@Override
	public String toString() {
		return "Participanse [id=" + id + "]";
	}
}