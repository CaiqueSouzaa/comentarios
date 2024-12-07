package br.com.csouza.comentarios.utils;

import java.util.Random;

import br.com.csouza.comentarios.exceptions.FakeSizeException;

/**
 * Essa classe fornece alguns métodos estaticos para geração de valores aleatórios.
 * 
 * @author Caique Souza
 * @version 1.0
 */
public class Fake {
	private static final String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	/**
	 * Método privado para validação.
	 * @param size Tamanho de algo.
	 * @param msg Mensagem a ser inclusa.
	 * @throws FakeSizeException Exceção lançada caso a validação seja false.
	 */
	private static void validation(Integer size, String msg) throws FakeSizeException {
		if (size == null || size <= 0) {
			throw new FakeSizeException("O tamanho " + msg + " não pode ser nulo, menor ou igual a zero.");
		}
	}
	
	/**
	 * Método para gerar strings aleatórias.
	 * @param size Tamanho da string a ser gerada.
	 * @return String gerada.
	 * @throws FakeSizeException Exceção lançada caso o tamanho da String não esteja de acordo.
	 */
	public static String fake(Integer size) throws FakeSizeException {
		validation(size, "da String");
		
		final Random random = new Random();
		final StringBuilder st = new StringBuilder();
		
		
		for (int i = 0; i < size; i++) {
			st.append(abc.charAt(random.nextInt(abc.length())));
		}
		
		return st.toString();
	}
	
	/**
	 * Método para gerar números inteiros aleatórios.
	 * @param size Quantia de casas que o número gerado deve possuir.
	 * @return Número aleatório gerado.
	 * @throws FakeSizeException
	 */
	public static String integer(Integer size) throws FakeSizeException {
		validation(size, "do inteiro");
		
		final StringBuilder st = new StringBuilder();
		final Random random = new Random();
		
		for (int i = 0; i < size; i++) {
			st.append(random.nextInt(10));
		}
		
		return st.toString();
	}
	
	/**
	 * Método para gerar logins aleátorios.
	 * @param nameSize Tamanho do nome a ser gerado.
	 * @param surnameSize Tamanho do sobrenome a ser gerado.
	 * @return Login aleatório.
	 * @throws FakeSizeException Exceção lançada caso o tamanho da String não esteja de acordo.
	 */
	public static String login(int nameSize, int surnameSize) throws FakeSizeException {
		return fake(nameSize) + "." + fake(surnameSize);
	}
	
	/**
	 * Método para gerar emails aleatórios.
	 * @param nameSize Tamanho do nome a ser gerado.
	 * @param surnameSize Tamanho do sobrenome a ser gerado.
	 * @param domain Dominio a ser incluso no email.
	 * @return Email aleatório.
	 * @throws FakeSizeException Exceção lançada caso o tamanho da String não esteja de acordo.
	 */
	public static String email(int nameSize, int surnameSize, String domain) throws FakeSizeException {
		return login(nameSize, surnameSize).toLowerCase() + "@" + domain;
	}
}
