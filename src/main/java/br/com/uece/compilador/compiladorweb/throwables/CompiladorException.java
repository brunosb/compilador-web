package br.com.uece.compilador.compiladorweb.throwables;

/**
 * This class is used to throw errors during compilation.
 */

public class CompiladorException extends Exception {
    /** A standard interface for reporting an error */
    public CompiladorException(String message) {
        super(message);
    }

    /** An interface to use if the line of the input where the error
     * occurs is known */
    public CompiladorException(String message, int line) {
        super("line " + line + ": " + message);
    }

    /** An interface to use if the line and column of the input where
     * the error occurs is known */
    public CompiladorException(String message, int line, int column) {
        super("line " + line + ", column " + column + ": " + message);
    }
}
