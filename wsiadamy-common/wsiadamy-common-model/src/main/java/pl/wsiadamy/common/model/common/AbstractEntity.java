package pl.wsiadamy.common.model.common;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AbstractEntity<IdType> implements Serializable {
	
	public abstract IdType getId();
	
	public abstract void setId(IdType id);
}
