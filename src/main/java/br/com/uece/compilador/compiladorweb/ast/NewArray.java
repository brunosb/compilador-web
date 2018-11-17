package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public class NewArray extends Exp {
    public Exp e;

    public NewArray(Exp ae, int ln) {
        super(ln);
        e = ae;
    }

    @Override
    public void acceptFirst(Visitor visitor, Map map, int level, int nivel) {
        visitor.visit(this, map, level, nivel);
    }


    @Override
    public String acceptSecond(SecondVisitor secondVisitor, Map map, int nivel, Classe classe, Metodos metodos) {
        return secondVisitor.visit(this, map, nivel, classe, metodos);
    }
}
