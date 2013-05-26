package pl.wsiadamy.common.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pl.wsiadamy.common.model.common.AbstractEntity;

@Entity
@Table(name = "feedback")
public class Feedback extends AbstractEntity<Integer> {
	
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
	private Integer vote;

	@Column
	private String comment;
	
	@Column
	private Date dateCreated;
	
	public Feedback() {
	}

	public Feedback(User user, Route route) {
		setUser(user);
		setUserSender(user);
		setRoute(route);
	}

	public Feedback(User user, User userSender, Route route, Integer vote) {
		setUser(user);
		setUserSender(userSender);
		setRoute(route);
		setVote(vote);
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

	public Integer getVote() {
		return vote;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		
		if(!(obj instanceof Feedback))
			return false;
		
		Feedback objCast = (Feedback) obj;
		
		return (
			null != objCast.getUser() && null != this.getUser()
			&& objCast.getUser().getId().equals(this.getUser().getId())
			&& null != objCast.getRoute() && null != this.getRoute()
			&& objCast.getRoute().getId().equals(this.getRoute().getId())
		);
	}
	
	@Override
	public String toString() {
		return "Feedback [id=" + id + "]";
	}
}