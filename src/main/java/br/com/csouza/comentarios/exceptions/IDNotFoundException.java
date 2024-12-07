package br.com.csouza.comentarios.exceptions;

import java.io.Serializable;

public class IDNotFoundException extends Exception {
	public IDNotFoundException(Serializable id) {
		super("ID de registro [" + id + "] não localizado.");
	}
}
