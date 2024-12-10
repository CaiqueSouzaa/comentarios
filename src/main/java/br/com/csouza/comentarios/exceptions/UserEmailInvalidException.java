package br.com.csouza.comentarios.exceptions;

public class UserEmailInvalidException extends RuntimeException {
    public UserEmailInvalidException(String msg) {
        super(msg);
    }
}
