package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public abstract class Statement extends ASTNode {
    public Statement(int ln) {
        super(ln);
    }

    public abstract void acceptFirst(Visitor visitor, Map map, int var3, int var4);

    public abstract void acceptSecond(SecondVisitor secondVisitor, Map map, int var3, Classe classe, Metodos metodos);
}
