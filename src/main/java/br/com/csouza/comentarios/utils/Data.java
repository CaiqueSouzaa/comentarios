package br.com.csouza.comentarios.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {
	/**
	 * Método para verificar se um objeto é nulo.
	 * @param value - Objeto a ser verificado.
	 * @return {@code true} Nulo; {@code false} Não nulo.
	 */
	public static boolean isNull(Object value) {
		return value == null ? true : false;
	}
	
	/**
	 * Método para verificar se uma String é vázia.
	 * @param value - String a ser verificada.
	 * @return {@code true} Vázia; {@code false} Não vázia.
	 */
	public static boolean isEmpty(String value) {
		return !(!isNull(value) && value.trim().length() > 0);
	}

	/**
	 * Método para verificar se um regex é válido.
	 * @param regex - Regra regex.
	 * @param value - Text a ser submetido ao regex.
	 * @return {@code true} Válido; {@code false} Não válido.
	 */
	public static boolean isValidRegex(String regex, String value) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value.trim());

		return matcher.matches();
	}

	/**
	 * Método para validar o tamanho de String.
	 * @param value - String a ser verificada.
	 * @param size - Tamanho que a String deve possuir.
	 * @return {@code true} A String é igual ou maior ao número esperado; {@code false} A String é menor ao número esperado.
	 */
	public static boolean isValidSize(String value, int size) {
		return !isEmpty(value) && value.length() >= size;
	}
}
