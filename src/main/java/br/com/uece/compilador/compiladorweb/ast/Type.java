package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public abstract class Type extends ASTNode {
    public Type(int ln) {
        super(ln);
    }

    public abstract void acceptFirst(Visitor visitor, Map map, int var3, int var4);

    public abstract void acceptSecond(SecondVisitor secondVisitor, Map map, int var3, Classe classe, Metodos metodos);
}
