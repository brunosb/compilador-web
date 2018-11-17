package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public class Program extends ASTNode {
    public MainClass m;
    public ClassDeclList cl;

    public Program(MainClass am, ClassDeclList acl, int ln) {
        super(ln);
        m = am;
        cl = acl;
    }

    public void acceptFirst(Visitor visitor, Map mapa, int level, int nivel) {
        visitor.visit(this, mapa, level, nivel);
    }

    public void acceptSecond(SecondVisitor v, Map mapa, int nivel, Classe cl, Metodos mt) {
        v.visit(this, mapa, nivel, cl, mt);
    }
}
