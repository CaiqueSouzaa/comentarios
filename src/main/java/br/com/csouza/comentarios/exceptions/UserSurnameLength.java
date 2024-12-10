package br.com.csouza.comentarios.exceptions;

public class UserSurnameLength extends RuntimeException {
    public UserSurnameLength(String msg) {
        super(msg);
    }
}
