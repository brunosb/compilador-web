package br.com.uece.compilador.compiladorweb.ast;

import java.util.Vector;

public class StatementList extends ASTNode {
    private Vector list;

    public StatementList(int ln) {
        super(ln);
        list = new Vector();
    }

    public void addElement(Statement n) {
        list.addElement(n);
    }

    public Statement elementAt(int i) {
        return (Statement) list.elementAt(i);
    }

    public int size() {
        return list.size();
    }
}
