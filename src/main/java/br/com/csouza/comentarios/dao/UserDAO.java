package br.com.csouza.comentarios.dao;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;
import br.com.csouza.comentarios.jdbc.PostgreSQL;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UserDAO extends GenericDAO<User, Long> implements IUserDAO {
	public UserDAO() {
		super(User.class);
	}

	@Override
	public User findByLogin(String login) throws UserNotFoundException {
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			final String stQuery = "SELECT user FROM User AS user "
					+ "WHERE user.login = :login";
			
			final TypedQuery<User> query = entityManager.createQuery(stQuery, User.class);
			query.setParameter("login", login);
			
			try {				
				return query.getSingleResult();
			} catch (Exception e) {
				throw new UserNotFoundException("Login de usuário [" + login + "] não localizado ou inexistente.");
			}
		}
	}

	@Override
	public User findByEmail(String email) throws UserNotFoundException {
		try (final EntityManager entityManager = PostgreSQL.getConnection()) {
			final String stQuery = "SELECT user FROM User AS user "
					+ "WHERE user.email = :email";
			
			final TypedQuery<User> query = entityManager.createQuery(stQuery, User.class);
			query.setParameter("email", email);
			
			try {
				return query.getSingleResult();				
			} catch (Exception e) {
				throw new UserNotFoundException("Endereço de e-mail [" + email + "] não localizado ou inexistente.");
			}
		}
	}
}
