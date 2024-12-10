package br.com.csouza.comentarios.exceptions;

import java.io.Serializable;

public class IDNotFoundException extends RuntimeException {
	public IDNotFoundException(Serializable id) {
		super("ID de registro [" + id + "] não localizado.");
	}

	public IDNotFoundException(String msg) {
		super(msg);
	}
}
