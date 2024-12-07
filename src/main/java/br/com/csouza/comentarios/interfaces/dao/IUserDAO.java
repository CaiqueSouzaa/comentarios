package br.com.csouza.comentarios.interfaces.dao;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;

/**
 * Interface responsável por fornecer o CRUD ao UserDAO.
 * 
 * @author Caique Souza
 * @version 1.0
 */
public interface IUserDAO extends IGenericDAO<User, Long> {
	/**
	 * Método responsável pela busca de um usuário com base em seu login.
	 * @param login - Login de usuário a ser buscado.
	 * @return Usuário referente ao login
	 * @throws UserNotFoundException Exceção lançada caso o login de usuário não seja localizado.
	 */
	public User findByLogin(String login) throws UserNotFoundException;
	
	/**
	 * Método responsável pela busca de um usuário com base em seu endereço de e-mail.
	 * @param email - Endereço de e-mail de usuário a ser buscado.
	 * @return Usuário referente ao endereço de e-mail.
	 * @throws UserNotFoundException Exceção lançada caso o endereço de email de usuário não seja localizado.
	 */
	public User findByEmail(String email) throws UserNotFoundException;
}
