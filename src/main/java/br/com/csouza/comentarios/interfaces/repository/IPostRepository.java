package br.com.csouza.comentarios.interfaces.repository;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;

/**
 * Interface responsável por fonecer os métodos do repositório PostRepository.
 * 
 * @author Caique Souza
 * @version 1.0
 */
public interface IPostRepository extends IRepository<Post, Long>{
	/**
	 * Método responsável por buscar pelos posts de um usuário com base em seu ID de registro.
	 * @param id - ID de registro do usuário a ser buscado.
	 * @return Coleção contendo todos os posts filtrados pelo ID de usuário.
	 * @throws IDNotFoundException Exceção lançada caso o ID de usuário não seja localizado.
	 */
	public Collection<Post> getCreatedById(Long id) throws UserNotFoundException;
	
	/**
	 * Método responsável por buscar pelos posts de um usuário com base em seu nome de login.
	 * @param login - Nome de login a ser buscado.
	 * @return Coleção contendo todos os posts filtrados pelo nome de login.
	 * @throws UserNotFoundException Exceção lançada caso o nome de login não seja localizado.
	 */
	public Collection<Post> getCreatedByLogin(String login) throws UserNotFoundException;
	
	/**
	 * Método responsável por buscar pelos posts de um usuário com base em seu endereço de e-mail.
	 * @param email - Endereço de e-mail de usuário a ser buscado.
	 * @return Coleção contendo todos os posts filtrados pelo endereço de e-mail.
	 * @throws UserNotFoundException Exceção lançada caso o nome de login não seja localizado.
	 */
	public Collection<Post> getCreatedByEmail(String email) throws UserNotFoundException;
}
