package br.com.csouza.comentarios.repository;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;
import br.com.csouza.comentarios.utils.Data;

public class UserRepository extends Repository<User, Long> implements IUserRepository {
	private final IUserDAO userDAO;
	
	public UserRepository(IUserDAO userDAO) {
		super(userDAO);
		this.userDAO = userDAO;
	}

	@Override
	public User register(User user) {
		final String emailRegex = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z0-9.]+$";

		Data.checkString("nome", user.getName(), 3, false ,null);
		Data.checkString("sobrenome", user.getSurname(), 3, false ,null);
		Data.checkString("login", user.getLogin(), 5, false ,null);
		Data.checkString("endereço de e-mail", user.getEmail(), 5, false, emailRegex);
		Data.canNull("aniversário", user.getBirthday(), false);
		
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
