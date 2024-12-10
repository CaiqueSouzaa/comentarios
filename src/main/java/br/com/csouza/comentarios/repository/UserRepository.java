package br.com.csouza.comentarios.repository;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.UserEmailInvalidException;
import br.com.csouza.comentarios.exceptions.UserEmailNotAvaliableException;
import br.com.csouza.comentarios.exceptions.UserLoginLength;
import br.com.csouza.comentarios.exceptions.UserLoginNotAvaliableException;
import br.com.csouza.comentarios.exceptions.UserNameLength;
import br.com.csouza.comentarios.exceptions.UserSurnameLength;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;
import br.com.csouza.comentarios.utils.Data;

public class UserRepository extends Repository<User, Long> implements IUserRepository {
	private final IUserDAO userDAO;
	
	public UserRepository(IUserDAO userDAO) {
		super(userDAO);
		this.userDAO = userDAO;
	}

	private void checkName(String name) {
		if (Data.isEmpty(name) || !Data.isValidSize(name, 3)) {
			throw new UserNameLength("O nome de usuário deve possuir 3 ou mais caracteres.");
		}
	}

	private void checkSurname(String surname) {
		if (Data.isEmpty(surname) || !Data.isValidSize(surname, 3)) {
			throw new UserSurnameLength("O sobrenome de usuário deve possuir 3 ou mais caracteres.");
		}
	}

	private void checkLogin(String login) {
		if (Data.isEmpty(login) || !Data.isValidSize(login, 5)) {
			throw new UserLoginLength("O nome de login deve possuir 5 ou mais caracteres.");
		}
	}

	private void checkEmail(String email) {
		final String emailRegex = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z0-9.]+$";
		
		if (Data.isEmpty(email) || !Data.isValidRegex(emailRegex, email)) {
			throw new UserEmailInvalidException("Certifique-se de que o endereço de e-mail é válido.");
		}
	}

	@Override
	public User register(User user) {
		this.checkName(user.getName());
		this.checkSurname(user.getSurname());
		this.checkEmail(user.getEmail());
		this.checkLogin(user.getLogin());

		// Checando se o endereço de e-mail e nome de login estão disponíveis para uso.
		final User ul = this.userDAO.findByLogin(user.getLogin());
		
		if (ul != null) {
			throw new UserLoginNotAvaliableException("Nome de login [" + user.getLogin() + "] não disponível para uso.");
		}

		final User ue = this.userDAO.findByEmail(user.getEmail());
		
		if (ue != null) {
			throw new UserEmailNotAvaliableException("Endereço de email [" + user.getEmail() + "] não disponível para uso.");
		}



		return this.userDAO.create(user);
	}

	@Override
	public User getByLogin(String login) {

		this.checkLogin(login);

		try {
			return this.userDAO.findByLogin(login);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public User getByEmail(String email) {
		this.checkEmail(email);

		return this.userDAO.findByEmail(email);
	}
}
