package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public class If extends Statement {
    public Exp e;
    public Statement s1, s2;

    public If(Exp ae, Statement as1, Statement as2, int ln) {
        super(ln);
        e = ae;
        s1 = as1;
        s2 = as2;
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

