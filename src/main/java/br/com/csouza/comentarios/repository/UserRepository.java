package br.com.csouza.comentarios.repository;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.UserInvalidException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;

public class UserRepository extends Repository<User, Long> implements IUserRepository {
	private final IUserDAO userDAO;
	
	public UserRepository(IUserDAO userDAO) {
		super(userDAO);
		this.userDAO = userDAO;
	}
	
	private void checkStringSize(String text, int size) {
		if (text == null || text.length() < size) {
			throw new RuntimeException(("O nome de usuário deve ser maior ou igual a três caracteres."));
		}
	}
	
	@Override
	public User register(User user) {
		this.checkStringSize(user.getName(), 3);
		this.checkStringSize(user.getSurname(), 3);
		this.checkStringSize(user.getLogin(), 5);
		this.checkStringSize(user.getEmail(), 5);
		
		return this.userDAO.create(user);
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
