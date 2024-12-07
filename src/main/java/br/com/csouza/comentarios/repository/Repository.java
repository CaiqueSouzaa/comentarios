package br.com.csouza.comentarios.repository;

import java.io.Serializable;
import java.util.Collection;

import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IGenericDAO;
import br.com.csouza.comentarios.interfaces.repository.IRepository;

public abstract class Repository<T, E extends Serializable> implements IRepository<T, E> {
	protected final IGenericDAO< T, E> dao;
	
	protected Repository(IGenericDAO<T, E> dao) {
		this.dao = dao;
	}
	
	@Override
	public T register(T entity) {
		return this.dao.create(entity);
	}

	@Override
	public Collection<T> getAll() {
		return this.dao.read();
	}

	@Override
	public T getById(E id) throws IDNotFoundException {
		return this.dao.findById(id);
	}

	@Override
	public T update(T entity) throws IDNotFoundException {
		return this.dao.update(entity);
	}

	@Override
	public Boolean destroy(E id) throws IDNotFoundException {
		return this.dao.delete(id);
	}

}
