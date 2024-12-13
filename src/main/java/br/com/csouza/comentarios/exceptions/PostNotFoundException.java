package br.com.csouza.comentarios.exceptions;

public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(String msg) {
		super(msg);
	}
}
