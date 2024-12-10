package br.com.csouza.comentarios.exceptions;

public class UserEmailNotAvaliableException extends RuntimeException {
    public UserEmailNotAvaliableException(String msg) {
        super(msg);
    }
}
