package br.com.csouza.comentarios.exceptions;

public class UserLoginLength extends RuntimeException {
    public UserLoginLength(String msg) {
        super(msg);
    }
}
