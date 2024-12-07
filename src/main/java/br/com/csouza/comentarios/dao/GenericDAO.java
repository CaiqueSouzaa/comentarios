package br.com.csouza.comentarios.dao;

import java.io.Serializable;
import java.util.Collection;

import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IGenericDAO;
import br.com.csouza.comentarios.jdbc.PostgreSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public abstract class GenericDAO<T, E extends Serializable> implements IGenericDAO<T, E> {
	private final Class<T> entityClass;
	
	protected GenericDAO(Class<T> entity) {
		this.entityClass = entity;
	}
	
	@Override
	public T create(T entity) {
		final EntityManager entityManager = PostgreSQL.getConnection();
		
		try {
			this.begin(entityManager);
			entityManager.persist(entity);
			this.commit(entityManager);
			
			return entity;
		} catch (Exception e) {
			this.rollback(entityManager);
			throw e;
		} finally {
			PostgreSQL.closeConnection(entityManager);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> read() {		
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			final Query query = entityManager.createQuery("FROM " + entityClass.getName());
			return query.getResultList();
		}
	}

	@Override
	public T update(T entity) throws IDNotFoundException {
		final EntityManager entityManager = PostgreSQL.getConnection();
		
		try {
			this.begin(entityManager);
			entityManager.merge(entity);
			this.commit(entityManager);
			
			return entity;
		} catch (Exception e) {
			this.rollback(entityManager);
			throw e;
		} finally {
			PostgreSQL.closeConnection(entityManager);
		}
	}

	@Override
	public Boolean delete(Serializable id) throws IDNotFoundException {
		final EntityManager entityManager = PostgreSQL.getConnection();
		T entity = null;

		// Checando se o ID de registro existe.
		try {
			entity = entityManager.find(entityClass, id);			
		} catch (Exception e) {
			throw new IDNotFoundException(id);
		}
		
		if (entity == null) {
			throw new IDNotFoundException(id);
		}
		
		try {
			this.begin(entityManager);
			entityManager.remove(entity);
			this.commit(entityManager);
			
			return true;
		} catch (Exception e) {
			this.rollback(entityManager);
			throw e;
		} finally {
			PostgreSQL.closeConnection(entityManager);
		}
	}

	@Override
	public T findById(Serializable id) throws IDNotFoundException {
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			final T entity = entityManager.find(entityClass, id);
			
			if (entity == null) {
				throw new IDNotFoundException(id);
			}
			
			return entity;
		}
	}
	
	protected void begin(EntityManager entityManager) {
		entityManager.getTransaction().begin();
	}
	
	protected void commit(EntityManager entityManager) {
		entityManager.getTransaction().commit();
	}
	
	protected void rollback(EntityManager entityManager) {
		entityManager.getTransaction().rollback();
	}
}
