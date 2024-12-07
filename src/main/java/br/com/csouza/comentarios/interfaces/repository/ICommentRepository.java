package br.com.csouza.comentarios.interfaces.repository;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Comment;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;

/**
 * Interface responsável por fonecer os métodos do repositório CommentRepository.
 * 
 * @author Caique Souza
 * @version 1.0
 */
public interface ICommentRepository extends IRepository<Comment, Long> {
	/**
	 * Método responsável por buscar por todos os comentários de um post.
	 * @param id - ID do post a ter os comentários buscados.
	 * @return Coleção contendo todos os comentário do post.
	 * @throws IDNotFoundException Exceção lançada caso o ID de post não seja localizado.
	 */
	public Collection<Comment> getAllByPostId(Long id) throws IDNotFoundException;
	
	/**
	 * Método responsável por buscar pelos comentários de um usuário com base em seu ID de registro.
	 * @param id - ID de registro do usuário a ser buscado.
	 * @return Coleção contendo todos os comentários filtrados pelo ID de usuário.
	 * @throws IDNotFoundException Exceção lançada caso o ID de usuário não seja localizado.
	 */
	public Collection<Comment> getCreatedById(Long id) throws IDNotFoundException;
	
	/**
	 * Método responsável por buscar pelos comentários de um usuário com base em seu nome de login.
	 * @param login - Nome de login a ser buscado.
	 * @return Coleção contendo todos os comentários filtrados pelo nome de login.
	 * @throws UserNotFoundException Exceção lançada caso o nome de login não seja localizado.
	 */
	public Collection<Comment> getCreatedByLogin(String login) throws UserNotFoundException;
	
	/**
	 * Método responsável por buscar pelos comentários de um usuário com base em seu endereço de e-mail.
	 * @param email - Endereço de e-mail de usuário a ser buscado.
	 * @return Coleção contendo todos os comentários filtrados pelo endereço de e-mail.
	 * @throws UserNotFoundException Exceção lançada caso o nome de login não seja localizado.
	 */
	public Collection<Comment> getCreatedByEmail(String email) throws UserNotFoundException;
}
