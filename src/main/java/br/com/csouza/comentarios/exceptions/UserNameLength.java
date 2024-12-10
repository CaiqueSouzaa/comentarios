package br.com.csouza.comentarios.exceptions;

public class UserNameLength extends RuntimeException {
    public UserNameLength(String msg) {
        super(msg);
    }
}
