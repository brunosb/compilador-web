package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public class ClassDeclExtends extends ClassDecl {
    public Identifier i;
    public Identifier j;
    public VarDeclList vl;
    public MethodDeclList ml;

    public ClassDeclExtends(Identifier ai, Identifier aj, VarDeclList avl, MethodDeclList aml, int ln) {
        super(ln);
        i = ai;
        j = aj;
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
