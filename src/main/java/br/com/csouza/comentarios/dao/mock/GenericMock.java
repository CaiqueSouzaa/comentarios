package br.com.csouza.comentarios.dao.mock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import br.com.csouza.comentarios.domain.DatabaseEntity;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IGenericDAO;

public class GenericMock<T extends DatabaseEntity, E extends Serializable> implements IGenericDAO<T, E> {

    @Override
    public T create(T entity) {
        entity.setId(1L);
        return entity;
    }

    @Override
    public Collection<T> read() {
        return new ArrayList<>();
    }

    @Override
    public T update(T entity) throws IDNotFoundException {
        entity.setId(1L);
        return entity;
    }

    @Override
    public Boolean delete(E id) throws IDNotFoundException {
        return true;
    }

    @Override
    public T findById(E id) throws IDNotFoundException {
        return null;
    }
    
}
