package pl.wsiadamy.common.model.common;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class AbstractDaoMongodbImpl<T extends AbstractEntity<String>>
	implements AbstractDao<T, String> {
	
    @Autowired
    private MongoTemplate documentManager;

	private Class<T> entityClass;
	
	private final String collectionName;

	public AbstractDaoMongodbImpl(Class<T> entityClass, String collectionName) {
		this.entityClass = entityClass;
		this.collectionName = collectionName;
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	public String getCollectionName() {
		return collectionName;
	}
	
	public MongoTemplate getDocumentManager() {
		return documentManager;
	}

	public void setDocumentManager(MongoTemplate documentManager) {
		this.documentManager = documentManager;
	}
	
	@Override
	public void create(T entity) {
		if (!getDocumentManager().collectionExists(entityClass)) {
			getDocumentManager().createCollection(entityClass);
        }
		
		entity.setId(UUID.randomUUID().toString());
		getDocumentManager().insert(entity, getCollectionName());
		flush();
	}

	@Override
	public void update(T entity) {
		getDocumentManager().insert(entity, getCollectionName());
		flush();
	}
	
	@Override
	public void remove(T entity) {
		getDocumentManager().remove(entity, getCollectionName());
		flush();
	}

	@Override
	public void refresh(T entity) {
		flush();
	}

	@Override
	public void detach(T entity) {
	}

	@Override
	public void flush() {
	}
	
	@Override
	public T get(String id) {
		return getDocumentManager().findById(id, entityClass);
	}
}
