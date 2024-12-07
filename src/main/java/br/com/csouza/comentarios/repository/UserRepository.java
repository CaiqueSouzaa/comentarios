package br.com.csouza.comentarios.repository;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;

public class UserRepository extends Repository<User, Long> implements IUserRepository {
	private final IUserDAO userDAO;
	
	public UserRepository(IUserDAO userDAO) {
		super(userDAO);
		this.userDAO = userDAO;
	}

	@Override
	public User getByLogin(String login) throws UserNotFoundException {
		return this.userDAO.findByLogin(login);
	}

	@Override
	public User getByEmail(String email) throws UserNotFoundException  {
		return this.userDAO.findByEmail(email);
	}
}
