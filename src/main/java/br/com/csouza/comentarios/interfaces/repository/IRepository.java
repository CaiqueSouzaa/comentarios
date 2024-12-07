package br.com.csouza.comentarios.interfaces.repository;

import java.io.Serializable;
import java.util.Collection;

import br.com.csouza.comentarios.exceptions.IDNotFoundException;

/**
 * Interface responsável por fornecer os métodos básicos dos repositórios.
 * 
 * @author Caique Souza
 * @version 1.0
 */
public interface IRepository<T, E extends Serializable> {
	/**
	 * Método responsável por realizar um novo registro.
	 * @param entity Objeto contendo as informações a serem registradas.
	 * @return Objeto registrado contendo seu ID de registro.
	 */
	public T register(T entity);
	
	/**
	 * Método responsável por buscar todos os registros.
	 * @return Coleção contendo todos os registros.
	 */
	public Collection<T> getAll();
	
	/**
	 * Método responsável por realizar a busca de um registro com base em seu ID.
	 * @param id ID de registro a ser buscado.
	 * @return Objeto contendo as informações do registro.
	 * @throws IDNotFoundException Exceção lançada caso o ID de registro não seja localizado.
	 */
	public T getById(E id) throws IDNotFoundException;
	
	/**
	 * Método responsável por atualizar um registro.
	 * @param entity Objeto contendo o ID de registro e as informações a serem atualizadas.
	 * @return Objeto atualizado.
	 * @throws IDNotFoundException Exceção lançada caso o ID de registro não seja localizado.
	 */
	public T update(T entity) throws IDNotFoundException;
	
	/**
	 * Método responsável por excluir um registro.
	 * @param id ID do registro a ser excluído.
	 * @return {@code true} para registro excluído e {@code false} para registro não excluído.
	 * @throws IDNotFoundException Exceção lançada caso o ID de registro não seja localizado.
	 */
	public Boolean destroy(E id) throws IDNotFoundException;
}
