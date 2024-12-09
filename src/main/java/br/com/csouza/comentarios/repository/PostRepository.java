package br.com.csouza.comentarios.repository;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IPostDAO;
import br.com.csouza.comentarios.interfaces.repository.IPostRepository;
import br.com.csouza.comentarios.utils.Data;

public class PostRepository extends Repository<Post, Long> implements IPostRepository {
	private final IPostDAO postDAO;
	
	public PostRepository(IPostDAO dao) {
		super(dao);
		this.postDAO = dao;
	}

	private void checkTitle(Post post) {
		final String fieldName = "título";
		Data.canEmpty(fieldName, post.getTitle(), false);
		Data.canNull(fieldName, post.getTitle(), false);
		post.setTitle(post.getTitle().trim());

	}

	@Override 
	public Post register(Post post) {
		this.checkTitle(post);

		return this.postDAO.create(post);
	}

	@Override
	public Post update(Post post) throws IDNotFoundException {
		this.checkTitle(post);

		return this.postDAO.update(post);
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
