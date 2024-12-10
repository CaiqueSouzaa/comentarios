package br.com.csouza.comentarios.utils;

public class Terminal {
    /**
     * Método para limpar o terminal.
     * 
     * @author Caique Souza
     * @version 1.0
     */
    public static void clear() {
        System.out.print("\033c");
    }
}