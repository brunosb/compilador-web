package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public class MethodDecl extends ASTNode {
    public Type t;
    public Identifier i;
    public FormalList fl;
    public VarDeclList vl;
    public StatementList sl;
    public Exp e;

    public MethodDecl(Type at, Identifier ai, FormalList afl, VarDeclList avl,
                      StatementList asl, Exp ae, int ln) {
        super(ln);
        t = at;
        i = ai;
        fl = afl;
        vl = avl;
        sl = asl;
        e = ae;
    }

    public void accept(Visitor v, Map mapa, int level, int nivel) {
        v.visit(this, mapa, level, nivel);
    }

    public void accept2(SecondVisitor v, Map mapa, int nivel, Classe cl, Metodos mt) {
        v.visit(this, mapa, nivel, cl, mt);
    }
}
