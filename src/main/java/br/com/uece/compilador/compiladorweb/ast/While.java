package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public class While extends Statement {
    public Exp e;
    public Statement s;

    public While(Exp ae, Statement as, int ln) {
        super(ln);
        e = ae;
        s = as;
    }


    @Override
    public void acceptFirst(Visitor visitor, Map map, int level, int nivel) {
        visitor.visit(this, map, level, nivel);
    }

    @Override
    public void acceptSecond(SecondVisitor secondVisitor, Map map, int nivel, Classe classe, Metodos metodos) {
        secondVisitor.visit(this, map, nivel, classe, metodos);
    }
}

