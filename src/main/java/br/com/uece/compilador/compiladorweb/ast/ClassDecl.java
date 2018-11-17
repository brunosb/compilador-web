package br.com.uece.compilador.compiladorweb.ast;

import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitor;
import br.com.uece.compilador.compiladorweb.ast.visitor.Visitor;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;

import java.util.Map;

public abstract class ClassDecl extends ASTNode {
    public Identifier i;
    public VarDeclList vl;
    public MethodDeclList ml;

    public ClassDecl(int ln) {
        super(ln);
    }

    public abstract void accept(Visitor var1, Map var2, int var3, int var4);

    public abstract void accept2(SecondVisitor var1, Map var2, int var3, Classe var4, Metodos var5);
}
