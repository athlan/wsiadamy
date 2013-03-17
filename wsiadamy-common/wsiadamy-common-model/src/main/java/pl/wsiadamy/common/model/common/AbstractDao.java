package pl.wsiadamy.common.model.common;

import java.io.Serializable;

public interface AbstractDao<T, IdType extends Serializable> {
	
	void create(T entity);
	
	void update(T entity);
	
	void remove(T entity);
	
	void refresh(T entity);
	
	void detach(T entity);
	
	void flush();
	
	T get(IdType id);
}
