package br.com.uece.compilador.compiladorweb.throwables;

/** This class is used to report errors with the input program during
 * lexical analysis a.k.a. scanning. */

public class LexicalCompiladorException extends CompiladorException {
    /** A standard interface for reporting an error */
    public LexicalCompiladorException(String message) {
	super("Lexical error: " + message);
    }

    /** An interface to use if the line of the input where the error
     * occurs is known */
    public LexicalCompiladorException(String message, int line) {
	super("Lexical error: " + message, line);
    }

    /** An interface to use if the line and column of the input where
     * the error occurs is known */
    public LexicalCompiladorException(String message, int line, int column) {
	super("Lexical error: " + message, line, column);
    }
}
