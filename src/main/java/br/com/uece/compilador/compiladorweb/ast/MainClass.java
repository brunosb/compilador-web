package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public class MainClass extends ASTNode {
    public Identifier i1, i2;
    public Statement s;

    public MainClass(Identifier ai1, Identifier ai2, Statement as, int ln) {
        super(ln);
        i1 = ai1;
        i2 = ai2;
        s = as;
    }

    public void accept(Visitor v, Map mapa, int level, int nivel) {
        v.visit(this, mapa, level, nivel);
    }

    public void accept2(SecondVisitor v, Map mapa, int nivel, Classe cl, Metodos mt) {
        v.visit(this, mapa, nivel, cl, mt);
    }
}

