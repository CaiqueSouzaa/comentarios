package br.com.csouza.comentarios.dao.mock;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IPostDAO;

public class PostMock extends GenericMock<Post, Long> implements IPostDAO {
    @Override
    public Collection<Post> findCreatedById(Long id) throws UserNotFoundException {
        return null;
    }

    @Override
    public Collection<Post> findCreatedByLogin(String login) throws UserNotFoundException {
        return null;
    }

    @Override
    public Collection<Post> findCreatedByEmail(String email) throws UserNotFoundException {
        return null;
    }
    
}
