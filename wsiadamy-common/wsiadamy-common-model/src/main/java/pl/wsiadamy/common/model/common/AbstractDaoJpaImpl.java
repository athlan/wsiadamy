package pl.wsiadamy.common.model.common;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AbstractDaoJpaImpl<T extends AbstractEntity<IdType>, IdType extends Serializable>
	implements AbstractDao<T, IdType> {

	@PersistenceContext(unitName = "wsiadamy")
	private EntityManager em;
	
	private Class<T> entityClass;
	
	public AbstractDaoJpaImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	protected EntityManager getEntityManager() {
		return em;
	}

	protected void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void create(T entity) {
		getEntityManager().persist(entity);
		flush();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void update(T entity) {
		getEntityManager().merge(entity);
		flush();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
		flush();
	}

	@Override
	public void refresh(T entity) {
		getEntityManager().refresh(entity);
		flush();
	}

	@Override
	public void detach(T entity) {
		getEntityManager().detach(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public void flush() {
		getEntityManager().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(IdType id) {
		Query query = getEntityManager().createQuery("select object(obj) from " + entityClass.getName() + " as obj where obj.id = :id").setParameter("id", id);
		
		List<T> result = query.getResultList();
		
		if(result.size() > 0)
			return result.get(0);
		
		return null;
	}
}
