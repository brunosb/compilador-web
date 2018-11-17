package br.com.uece.compilador.compiladorweb.ast.visitor;

import br.com.uece.compilador.compiladorweb.ast.*;
import br.com.uece.compilador.compiladorweb.tabelas.*;

import java.util.Map;

/**
 * Created by Bruno Barbosa and Yan Gurgel on 09/11/18.
 */

public interface SecondVisitor {

    public void visit(MainClass variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(ClassDeclSimple variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(ClassDeclExtends variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(VarDecl variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(MethodDecl variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(Formal variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(Program variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(IntArrayType variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(BooleanType variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(IntegerType variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public boolean eCompativel(Map variavel1, String variavel2, String variavel3);

    public void visit(IdentifierType variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(Block variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(Return variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(While variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(If variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(Print variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(Assign variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public void visit(ArrayAssign variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(And variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(LessThan variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(Plus variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(Minus variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(Times variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);


    public String visit(ArrayLength variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(Call variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(ArrayLookup variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(IntegerLiteral variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(True variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(False variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(IdentifierExp variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos
            variavel5);

    public String visit(This variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(Not variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(NewArray variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);

    public String visit(NewObject variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);


    public void visit(Identifier variavel1, Map variavel2, int variavel3, Classe variavel4, Metodos variavel5);
}
