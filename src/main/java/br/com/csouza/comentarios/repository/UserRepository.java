package br.com.csouza.comentarios.repository;

import java.util.Collection;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;
import br.com.csouza.comentarios.interfaces.repository.IUserRepository;

public class UserRepository implements IUserRepository {
	private final IUserDAO userDAO;
	
	public UserRepository(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Override
	public User register(User user) {
		return this.userDAO.create(user);
	}

	@Override
	public Collection<User> findAll() {
		return this.userDAO.read();
	}

	@Override
	public User getById(Long id) throws IDNotFoundException {
		return this.userDAO.findById(id);
	}

	@Override
	public User getByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User user) throws IDNotFoundException {
		return this.userDAO.update(user);
	}

	@Override
	public Boolean destroy(Long id) throws IDNotFoundException {
		return this.userDAO.delete(id);
	}
}
