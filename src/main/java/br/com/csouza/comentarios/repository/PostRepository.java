package br.com.csouza.comentarios.repository;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IPostDAO;
import br.com.csouza.comentarios.interfaces.repository.IPostRepository;

public class PostRepository extends Repository<Post, Long> implements IPostRepository {
	private final IPostDAO postDAO;
	
	public PostRepository(IPostDAO dao) {
		super(dao);
		this.postDAO = dao;
	}

	@Override
	public Collection<Post> getCreatedById(Long id) throws UserNotFoundException {
		return this.postDAO.findCreatedById(id);
	}

	@Override
	public Collection<Post> getCreatedByLogin(String login) throws UserNotFoundException {
		return this.postDAO.findCreatedByLogin(login);
	}

	@Override
	public Collection<Post> getCreatedByEmail(String email) throws UserNotFoundException {
		return this.postDAO.findCreatedByEmail(email);
	}
}
