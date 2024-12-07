package br.com.csouza.comentarios.dao;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;

public class UserDAO extends GenericDAO<User, Long> implements IUserDAO {
	public UserDAO() {
		super(User.class);
	}
}
