package br.com.csouza.comentarios.dao;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Comment;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.PostNotFoundException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.ICommentDAO;
import br.com.csouza.comentarios.interfaces.dao.IPostDAO;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;
import br.com.csouza.comentarios.jdbc.PostgreSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class CommentDAO extends GenericDAO<Comment, Long> implements ICommentDAO {
	private final IPostDAO postDAO;
	private final IUserDAO userDAO;
	
	public CommentDAO() {
		super(Comment.class);
		this.postDAO = new PostDAO();
		this.userDAO = new UserDAO();
	}
	
	@Override
	public Comment create(Comment comment) {
		final EntityManager entityManager = PostgreSQL.getConnection();
		
		try {
			this.begin(entityManager);
			
			comment.setPost(entityManager.merge(comment.getPost()));
			comment.setUser(entityManager.merge(comment.getUser()));
			
			entityManager.persist(comment);
			
			this.commit(entityManager);
			
			return comment;
		} catch (Exception e) {
			this.rollback(entityManager);
			throw e;
		} finally {
			PostgreSQL.closeConnection(entityManager);
		}
	}

	@Override
	public Collection<Comment> findAllByPostId(Long id) throws PostNotFoundException {
		this.checkHasPost(id);
		
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			final String stQuery = "SELECT c FROM Comment AS c "
					+ "INNER JOIN Post AS p "
					+ "ON c.post.id = p.id "
					+ "WHERE p.id = :id";
			
			final TypedQuery<Comment> query = entityManager.createQuery(stQuery, Comment.class);
			query.setParameter("id", id);
			
			return query.getResultList();
		}
	}

	@Override
	public Collection<Comment> findCreatedById(Long postId, Long userId) throws UserNotFoundException, PostNotFoundException {
		this.checkHasPost(postId);
		
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			final String stQuery = "SELECT c FROM Comment AS c "
					+ "INNER JOIN Post AS p "
					+ "ON c.post.id = p.id "
					+ "INNER JOIN User AS u "
					+ "ON c.user.id = u.id "
					+ "WHERE p.id = :postId "
					+ "AND u.id = :userId";
			
			final TypedQuery<Comment> query = entityManager.createQuery(stQuery, Comment.class);
			query.setParameter("postId", postId);
			query.setParameter("userId", userId);
			
			return query.getResultList();
		}
	}

	@Override
	public Collection<Comment> findCreatedByLogin(Long postId, String login) throws UserNotFoundException, PostNotFoundException {
		this.checkHasPost(postId);
		
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			final String stQuery = "SELECT c FROM Comment AS c "
					+ "INNER JOIN Post AS p "
					+ "ON c.post.id = p.id "
					+ "INNER JOIN User AS u "
					+ "ON c.user.id = u.id "
					+ "WHERE p.id = :postId "
					+ "AND u.login = :login";
			
			final TypedQuery<Comment> query = entityManager.createQuery(stQuery, Comment.class);
			query.setParameter("postId", postId);
			query.setParameter("login", login);
			
			return query.getResultList();
		}
	}

	@Override
	public Collection<Comment> findCreatedByEmail(Long postId, String email) throws UserNotFoundException, PostNotFoundException {
		this.checkHasPost(postId);
		
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			final String stQuery = "SELECT c FROM Comment AS c "
					+ "INNER JOIN Post AS p "
					+ "ON c.post.id = p.id "
					+ "INNER JOIN User AS u "
					+ "ON c.user.id = u.id "
					+ "WHERE p.id = :postId "
					+ "AND u.email = :email";
			
			final TypedQuery<Comment> query = entityManager.createQuery(stQuery, Comment.class);
			query.setParameter("postId", postId);
			query.setParameter("email", email);
			
			return query.getResultList();
		}
	}
	
	private void checkHasPost(Long id) throws PostNotFoundException {
		try {
			this.postDAO.findById(id);
		} catch (Exception e) {
			throw new PostNotFoundException("ID [" + id + "] de post não localizado.");
		}
	}
}
