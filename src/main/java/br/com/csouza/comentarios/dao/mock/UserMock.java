package br.com.csouza.comentarios.dao.mock;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.interfaces.dao.IUserDAO;

public class UserMock extends GenericMock<User, Long> implements IUserDAO {

    @Override
    public User findByLogin(String login) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }
}
