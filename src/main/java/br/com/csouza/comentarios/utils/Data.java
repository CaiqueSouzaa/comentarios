package br.com.csouza.comentarios.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Data {
	/**
	 * Método responsável por verificar se um atributo pode ser nulo ou não.
	 * @param field - Nome do atributo.
	 * @param value - Valor do atributo
	 * @param nullable - Se o atributo pode ser nulo ou não.
	 */
	public static void canNull(String field, Object value, boolean nullable) {
		if (nullable == false && value == null) {
			throw new RuntimeException("O campo [" + field + "] não pode estar nulo.");
		}
	}
	
    /**
     * Método responsável por verificar se um atributo pode ser vázio ou não.
	 * @param field - Nome do atributo.
	 * @param value - Valor do atributo
	 * @param empty - Se o atributo pode ser vázio ou não.
     */
    public static void canEmpty(String field, String value, boolean empty) {
        if (!empty && value.trim().length() == 0) {
			throw new RuntimeException("O campo [" + field + "] não pode estar vázio.");
        }
    }

    /**
     * Método responsável por válidar uma regra regex.
     * @param regex - Regra Regex.
     * @param field - Nome do campo.
     * @param text - Valor a ser submetido a validação da regra regex.
     */
	public static void checkRegex(String regex, String field, String text) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text.trim());

		if (!matcher.matches()) {
			throw new RuntimeException("O valor [" + text + "] não é compativel com o campo [" + field + "].");
		}
	}

    /**
     * Método responsável por válidar o tamanho de uma string.
     * @param field - Nome do campo.
     * @param text - Texto a ter seu tamanho válidado.
     * @param size - Tamanho esperado que o texto possua.
     */
	public static void checkSize(String field, String text, int size) {
		if (text == null || text.trim().length() < size) {
			throw new RuntimeException("O " + field + " deve ser maior ou igual a " + size + " caracteres.");
		}
	}

    /**
     * Método responsável por realizar válidações de nulabilidade, regra de regex e tamanho de textos.
     * @param field - Nome do campo.
     * @param text - Texto a ser verificado.
     * @param size - Tamanho esperado que o texto possua.
	 * @param canNull - Se o atributo pode ser nulo ou não.
     * @param regex - Regra Regex.
     */
	public static void checkString(String field, String text, int size, boolean canNull, String regex) {
		canNull(field, text, canNull);

		if (regex != null) {
			checkRegex(regex, field, text);
		}

		checkSize(field, text, size);
	}
	
}
