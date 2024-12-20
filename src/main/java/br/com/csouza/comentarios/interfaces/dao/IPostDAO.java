package br.com.csouza.comentarios.interfaces.dao;

import java.util.Collection;

import br.com.csouza.comentarios.domain.Post;
import br.com.csouza.comentarios.exceptions.UserNotFoundException;

public interface IPostDAO extends IGenericDAO<Post, Long> {
	/**
	 * Método responsável por buscar pelos posts de um usuário com base em seu ID de registro.
	 * @param id - ID de registro do usuário a ser buscado.
	 * @return Coleção contendo todos os posts filtrados pelo ID de usuário.
	 * @throws UserNotFoundException Exceção lançada caso o ID de usuário não seja localizado.
	 */
	public Collection<Post> findCreatedById(Long id) throws UserNotFoundException;
	
	/**
	 * Método responsável por buscar pelos posts de um usuário com base em seu nome de login.
	 * @param login - Nome de login a ser buscado.
	 * @return Coleção contendo todos os posts filtrados pelo nome de login.
	 * @throws UserNotFoundException Exceção lançada caso o nome de login não seja localizado.
	 */
	public Collection<Post> findCreatedByLogin(String login) throws UserNotFoundException;
	
	/**
	 * Método responsável por buscar pelos posts de um usuário com base em seu endereço de e-mail.
	 * @param email - Endereço de e-mail de usuário a ser buscado.
	 * @return Coleção contendo todos os posts filtrados pelo endereço de e-mail.
	 * @throws UserNotFoundException Exceção lançada caso o nome de login não seja localizado.
	 */
	public Collection<Post> findCreatedByEmail(String email) throws UserNotFoundException;
}
