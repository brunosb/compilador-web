package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public class ClassDeclSimple extends ClassDecl {


    public ClassDeclSimple(Identifier ai, VarDeclList avl, MethodDeclList aml, int ln) {
        super(ln);
        i = ai;
        vl = avl;
        ml = aml;
    }


    @Override
    public void accept(Visitor v, Map mapa, int level, int nivel) {
        v.visit(this, mapa, level, nivel);
    }

    @Override
    public void accept2(SecondVisitor v, Map mapa, int nivel, Classe cl, Metodos mt) {
        v.visit(this, mapa, nivel, cl, mt);
    }
}
