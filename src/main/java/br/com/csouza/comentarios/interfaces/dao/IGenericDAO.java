package br.com.csouza.comentarios.interfaces.dao;

import java.io.Serializable;
import java.util.Collection;

import br.com.csouza.comentarios.exceptions.IDNotFoundException;

/**
 * Interface responsável por fornecer o CRUD aos DAOs.
 * @param <T> Entidade a ser manipulada.
 * @param <E> Tipo do atributo ID da entidade.
 * 
 * @author Caique Souza
 * @version 1.0
 */
public interface IGenericDAO<T, E extends Serializable> {
	/**
	 * Método responsável pela criação de um novo registro.
	 * @param entity - Objeto a ser registrado.
	 * @return Objeto contendo seu ID de registro.
	 */
	public T create(T entity);

	/**
	 * Método responsável pela busca de todos os registros.
	 * @return Coleção contendo todos os registros.
	 */
	public Collection<T> read();
	
	/**
	 * Método responsável pela atualização de um registro.
	 * @param entity - Objeto, contendo seu ID de registro, com as informações a serem atualizadas.
	 * @return Objeto com suas informações atualizadas.
	 * @throws IDNotFoundException - Exceção lançada caso o ID de registro não exista.
	 */
	public T update(T entity) throws IDNotFoundException;
	
	/**
	 * Método responsável por excluir um registro.
	 * @param id - ID de registro a ser excluído.
	 * @return {@code true} caso excluído e {@code false} caso não excluído
	 * @throws IDNotFoundException - Exceção lançada caso o ID de registro não exista.
	 */
	public Boolean delete(E id) throws IDNotFoundException;

	/**
	 * Método para buscar por um único registro com base em seu ID.
	 * @param id - ID do registro a ser buscado.
	 * @return Objeto correspondente ao ID.
	 * @throws IDNotFoundException - Exceção lançada caso o ID de registro não exista.
	 */
	public T findById(E id) throws IDNotFoundException;
}
