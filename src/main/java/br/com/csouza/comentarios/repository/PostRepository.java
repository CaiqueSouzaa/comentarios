package br.com.csouza.comentarios.repository;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.PostTitleLengthException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IPostDAO;
import br.com.csouza.comentarios.interfaces.repository.IPostRepository;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;
import br.com.csouza.comentarios.utils.Data;

public class PostRepository extends Repository<Post, Long> implements IPostRepository {
	private final IPostDAO postDAO;
	private final IUserRepository userRepository;
	
	public PostRepository(IPostDAO dao, IUserRepository userRepository) {
		super(dao);
		this.postDAO = dao;
		this.userRepository = userRepository;
	}

	private void checkTitle(String title) {
		if (Data.isEmpty(title) || !Data.isValidSize(title, 1)) {
			throw new PostTitleLengthException("O título de uma publicação não deve estar nulo ou vázio.");
		}
	}

	private void checkTitle(Post post) {
		this.checkTitle(post.getTitle());
		post.setTitle(post.getTitle().trim());
	}

	private void checkUserId(Long id) {
		if (Data.isNull(id)) {
			throw new IDNotFoundException("ID de usuário não pode ser nulo.");
		}
	}

	private void checkUser(User user) {
		try {
			this.userRepository.getById(user.getId());
		} catch (Exception e) {
			throw new IDNotFoundException("ID de usuário [" + user.getId() + "] não localizado.");
		}
	}

	@Override 
	public Post register(Post post) {
		this.checkTitle(post);
		this.checkUserId(post.getUser().getId());
		this.checkUser(post.getUser());

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
