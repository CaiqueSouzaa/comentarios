package br.com.csouza.comentarios.exceptions;

public class UserLoginNotAvaliableException extends RuntimeException {
    public UserLoginNotAvaliableException(String msg) {
        super(msg);
    }
}
