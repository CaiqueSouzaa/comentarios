package br.com.csouza.comentarios.exceptions;

public class PostTitleLengthException extends RuntimeException {
    public PostTitleLengthException(String msg) {
        super(msg);
    }
}
