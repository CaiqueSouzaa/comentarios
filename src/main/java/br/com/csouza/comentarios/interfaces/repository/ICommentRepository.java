package br.com.csouza.comentarios.interfaces.repository;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Comment;
import br.com.csouza.comentarios.exceptions.IDNotFoundException;
import br.com.csouza.comentarios.exceptions.PostNotFoundException;
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
	 * @throws PostNotFoundException Exceção lançada caso o ID de post não seja localizado.
	 */
	public Collection<Comment> getAllByPostId(Long id) throws PostNotFoundException;
	
	/**
	 * Método responsável por buscar pelos comentários de um usuário com base em seu ID de registro.
	 * @param postId - ID de registro do post a ser buscado.
	 * @param userId - ID de registro do usuário a ser buscado.
	 * @return Coleção contendo todos os comentários do post filtrados pelo ID de usuário.
	 * @throws UserNotFoundException Exceção lançada caso o ID de usuário não seja localizado.
	 */
	public Collection<Comment> getCreatedById(Long postId, Long userId) throws UserNotFoundException, PostNotFoundException;
	
	/**
	 * Método responsável por buscar pelos comentários de um usuário com base em seu nome de login.
	 * @param postId - ID de registro do post a ser buscado.
	 * @param login - Nome de login a ser buscado.
	 * @return Coleção contendo todos os comentários filtrados pelo nome de login.
	 * @throws UserNotFoundException Exceção lançada caso o nome de login não seja localizado.
	 */
	public Collection<Comment> getCreatedByLogin(Long postId, String login) throws UserNotFoundException, PostNotFoundException;
	
	/**
	 * Método responsável por buscar pelos comentários de um usuário com base em seu endereço de e-mail.
	 * @param postId - ID de registro do post a ser buscado.
	 * @param email - Endereço de e-mail de usuário a ser buscado.
	 * @return Coleção contendo todos os comentários filtrados pelo endereço de e-mail.
	 * @throws UserNotFoundException Exceção lançada caso o nome de login não seja localizado.
	 */
	public Collection<Comment> getCreatedByEmail(Long postId, String email) throws UserNotFoundException, PostNotFoundException;
}
