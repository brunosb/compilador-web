package br.com.uece.compilador.compiladorweb.ast.visitor;

import br.com.uece.compilador.compiladorweb.ast.*;

import java.util.Map;

/**
 * Created by Bruno Barbosa and Yan Gurgel on 09/11/18.
 */

public interface Visitor {
    // Return added for the toy example language---they are subsumed in the MiniJava AST by the MethodDecl nodes.

    public void visit(Program program, Map mapa, int variavel1, int variavel2);

    public void visit(MainClass mainClass, Map mapa, int variavel1, int variavel2);

    public void visit(ClassDeclSimple classDeclSimple, Map mapa, int variavel1, int variavel2);

    public void visit(VarDecl varDecl, Map mapa, int variavel1, int variavel2);

    public void visit(Return aReturn, Map mapa, int variavel1, int variavel2);

    public void visit(ClassDeclExtends classDeclExtends, Map mapa, int variavel1, int variavel2);

    public void visit(MethodDecl methodDecl, Map mapa, int variavel1, int variavel2);

    public void visit(IntegerType integerType, Map mapa, int variavel1, int variavel2);

    public void visit(BooleanType booleanType, Map mapa, int variavel1, int variavel2);

    public void visit(IdentifierType identifierType, Map mapa, int variavel1, int variavel2);

    public void visit(Block block, Map mapa, int variavel1, int variavel2);

    public void visit(If anIf, Map mapa, int variavel1, int variavel2);

    public void visit(While aWhile, Map mapa, int variavel1, int variavel2);

    public void visit(Assign assign, Map mapa, int variavel1, int variavel2);

    public void visit(ArrayAssign arrayAssign, Map mapa, int variavel1, int variavel2);

    public void visit(Print print, Map mapaa, int variavel1, int variavel2);

    public void visit(ArrayLookup arrayLookup, Map mapa, int variavel1, int variavel2);

    public void visit(IntArrayType intArrayType, Map mapa, int variavel1, int variavel2);

    public void visit(ArrayLength arrayLength, Map mapa, int variavel1, int variavel2);

    public void visit(Call call, Map mapa, int variavel1, int variavel2);

    public void visit(IntegerLiteral integerLiteral, Map mapa, int variavel1, int variavel2);

    public void visit(IdentifierExp identifierExp, Map mapa, int variavel1, int variavel2);

    public void visit(This aThis, Map mapa, int variavel1, int variavel2);

    public void visit(NewObject newObject, Map mapa, int variavel1, int variavel2);

    public void visit(NewArray newArray, Map mapa, int variavel1, int variavel2);

    public void visit(True aTrue, Map mapa, int variavel1, int variavel2);

    public void visit(False aFalse, Map mapa, int variavel1, int variavel2);

    public void visit(Not not, Map mapa, int variavel1, int var4);

    public void visit(Identifier identifier, Map mapa, int variavel1, int variavel2);

    public void visit(And and, Map mapa, int variavel1, int variavel2);

    public void visit(Plus plus, Map mapa, int variavel1, int variavel2);

    public void visit(Minus minus, Map mapa, int variavel1, int variavel2);

    public void visit(Times times, Map mapa, int variavel1, int variavel2);

    public void visit(LessThan lessThan, Map mapa, int variavel1, int variavel2);

    public void visit(Formal formal, Map mapa, int variavel1, int variavel2);
}
