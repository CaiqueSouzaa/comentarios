package br.com.csouza.comentarios.exceptions;

public class UserEmailNotValidException extends RuntimeException{
    public UserEmailNotValidException(String msg) {
        super(msg);
    }
}
