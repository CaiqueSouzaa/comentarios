package br.com.csouza.comentarios.interfaces.repository;

import br.com.csouza.comentarios.domain.User;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;

/**
 * Interface responsável por fonecer os métodos do repositório UserRepository.
 * 
 * @author Caique Souza
 * @version 1.0
 */
public interface IUserRepository extends IRepository<User, Long> {
	/**
	 * Método responsável por buscar um usuário com base em seu nome de login.
	 * @param login - Nome de login a ser buscado.
	 * @return Objeto do tipo Usuário contendo as informações solicitadas.
	 * @throws UserNotFoundException Exceção lançada caso o nome de login não seja localizado.
	 */
	public User getByLogin(String login) throws UserNotFoundException ;
	
	/**
	 * Método responsável por buscar um usuário com base em seu endereço de e-mail.
	 * @param email - Endereço de e-mail de usuário a ser buscado.
	 * @return Objeto do tipo Usuário contendo as informações solicitadas.
	 * @throws UserNotFoundException Exceção lançada caso o endereço de e-mail não seja localizado.
	 */
	public User getByEmail(String email) throws UserNotFoundException ;
}
