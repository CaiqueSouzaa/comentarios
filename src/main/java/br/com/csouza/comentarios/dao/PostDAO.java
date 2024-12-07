package br.com.csouza.comentarios.dao;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IPostDAO;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;
import br.com.csouza.comentarios.jdbc.PostgreSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PostDAO extends GenericDAO<Post, Long> implements IPostDAO{
	private final IUserDAO userDAO;
	
	public PostDAO() {
		super(Post.class);
		this.userDAO = new UserDAO();
	}
	
	@Override
	public Post create(Post post) {
		final EntityManager entityManager = PostgreSQL.getConnection();
		
		try {
			this.begin(entityManager);
			post.setUser(entityManager.merge(post.getUser()));
			entityManager.persist(post);
			this.commit(entityManager);
			
			return post;
		} catch (Exception e) {
			this.rollback(entityManager);
			throw e;
		} finally {
			PostgreSQL.closeConnection(entityManager);
		}
	}

	@Override
	public Collection<Post> findCreatedById(Long id) throws UserNotFoundException {
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			
			// Validando se o ID de usuário existe.
			try {
				this.userDAO.findById(id);				
			} catch (Exception e) {
				throw new UserNotFoundException("ID [" + id + "] de usuário não localizado.");
			}
			
			final String stQuery = "SELECT post FROM Post AS post "
					+ "INNER JOIN User AS user "
					+ "ON post.user.id = :id";
			
			final TypedQuery<Post> query = entityManager.createQuery(stQuery, Post.class);
			query.setParameter("id", id);
			
			return query.getResultList();
		}
	}

	@Override
	public Collection<Post> findCreatedByLogin(String login) throws UserNotFoundException {
		try(final EntityManager entityManager = PostgreSQL.getConnection()) {
			
			// Validando se o nome de login de usuŕio existe.
			try {
				this.userDAO.findByLogin(login);
			} catch (Exception e) {
				throw new UserNotFoundException("Nome de login [" + login + "] não localizado.");
			}
			
			final String stQuery = "SELECT post FROM Post AS post "
					+ "INNER JOIN User AS user "
					+ "ON post.user.login = :login";
			
			final TypedQuery<Post> query = entityManager.createQuery(stQuery, Post.class);
			query.setParameter("login", login);
			
			return query.getResultList();
		}
	}

	@Override
	public Collection<Post> findCreatedByEmail(String email) throws UserNotFoundException {
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			
			// Validando se o e-mail de usuário existe
			try {
				this.userDAO.findByEmail(email);
			} catch (Exception e) {
				throw new UserNotFoundException("Endereço de e-mail [" + email + "] não localizado.");
			}
			
			final String stQuery = "SELECT post FROM Post AS post "
					+ "INNER JOIN User AS user "
					+ "ON post.user.email = :email";
			
			final TypedQuery<Post> query = entityManager.createQuery(stQuery, Post.class);
			query.setParameter("email", email);
			
			return query.getResultList();
		}
	}
}
