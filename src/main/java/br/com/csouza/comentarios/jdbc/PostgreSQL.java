package br.com.csouza.comentarios.jdbc;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe estatica que fornece a conexão e a desconexão com o servidor.
 * 
 * @author Caique Souza
 * @version 1.0
 */
public class PostgreSQL {
	private static final String persistenceUnitName = "Comentarios";
	private static EntityManagerFactory entityManagerFactory;
	
	private PostgreSQL() {}
	
	/**
	 * Método para inicializar a conexão.
	 * @return EntityManager conectado ao banco de dados.
	 */
	public static EntityManager getConnection() {
		if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
			entityManagerFactory = initConnection();
		}
		
		return entityManagerFactory.createEntityManager();
	}

	/**
	 * Método para finalizar a conexão do EntityManagerFactory.
	 */
	public static void closeConnection() {
		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Método para finalizar a conexão do EntityManager conectado.
	 * @param entityManager EntityManager a ser desconectado.
	 */
	public static void closeConnection(EntityManager entityManager) {
		if (entityManager != null && entityManager.isOpen()) {
			entityManager.close();
		}
	}
	
	/**
	 * Método privado para obter a EntityManagerFactory.
	 * @return EntityManagerFactory conectada ao banco de dados.
	 */
	private static EntityManagerFactory initConnection() {
		return Persistence.createEntityManagerFactory(persistenceUnitName);
	}
}
