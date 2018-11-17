package br.com.uece.compilador.compiladorweb.ast.visitor;

import br.com.uece.compilador.compiladorweb.ast.*;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;
import br.com.uece.compilador.compiladorweb.tabelas.Variaveis;

import java.util.Map;

/**
 * Created by Bruno Barbosa and Yan Gurgel on 09/11/18.
 */

public class FirstVisitorImpl implements Visitor {

    @Override
    public void visit(Program program, Map map, int variavel1, int level) {
        program.m.accept(this, map, level, variavel1 + 1);
        int i = 0;
        while (i < program.cl.size()) {
            program.cl.elementAt(i).accept(this, map, level, variavel1 + 1);
            ++i;
        }
    }


    @Override
    public void visit(ClassDeclSimple classDeclSimple, Map map, int nivel, int level) {
        Classe a = new Classe(false);
        a.linha = classDeclSimple.line_number;
        if (map.containsKey(classDeclSimple.i.toString())) {
            System.out.println("Classe  já definida : ( class " + classDeclSimple.i.toString() + " ) em " + (classDeclSimple.line_number + 1));
            while (map.containsKey(a.id)) {
                a.id = String.valueOf(a.id) + "@";
            }
        }
        a.id = classDeclSimple.i.toString();
        classDeclSimple.i.accept(this, map, level, nivel + 1);
        int i = 0;
        while (i < classDeclSimple.vl.size()) {
            classDeclSimple.vl.elementAt(i).accept(this, a.variavel, a.nivel + 1, nivel + 1);
            classDeclSimple.vl.size();
            ++i;
        }
        i = 0;
        while (i < classDeclSimple.ml.size()) {
            classDeclSimple.ml.elementAt(i).accept(this, a.metodo, a.nivel + 1, nivel + 1);
            ++i;
        }
        map.put(a.id, a);
    }

    @Override
    public void visit(MainClass mainClass, Map map, int nivel, int level) {
        Classe a = new Classe(true);
        a.linha = mainClass.i1.line_number;
        if (map.containsKey(mainClass.i1.toString())) {
            System.out.println("Classe já definida : ( class " + mainClass.i1.toString() + " ) em " + (mainClass.line_number + 1));
            while (map.containsKey(a.id)) {
                a.id = String.valueOf(a.id) + "@";
            }
        }
        a.id = mainClass.i1.toString();
        mainClass.i1.accept(this, map, level, nivel + 1);
        Variaveis v = new Variaveis();
        v.type = "String []";
        v.id = mainClass.i2.toString();
        v.linha = mainClass.i2.line_number;
        mainClass.i2.accept(this, map, level, nivel + 1);
        Metodos m = new Metodos();
        m.type = "void";
        m.id = "main";
        m.arg.put(v.id, v);
        m.linha = mainClass.i2.line_number;
        mainClass.s.acceptFirst(this, m.variavel, m.nivel + 1, nivel + 1);
        map.put(a.id, a);
    }


    @Override
    public void visit(ClassDeclExtends classDeclExtends, Map map, int nivel, int level) {
        Classe a = new Classe(false);
        a.linha = classDeclExtends.line_number;
        if (map.containsKey(classDeclExtends.i.toString())) {
            System.out.println("Classe  já definida : ( class " + classDeclExtends.i.toString() + " ) em " + (classDeclExtends.line_number + 1));
            while (map.containsKey(a.id)) {
                a.id = String.valueOf(a.id) + "@";
            }
        }
        a.id = classDeclExtends.i.toString();
        classDeclExtends.i.accept(this, map, level, nivel + 1);
        a.extendida = new String();
        a.extendida = classDeclExtends.j.s.toString();
        classDeclExtends.j.accept(this, map, level, nivel + 1);
        int i = 0;
        while (i < classDeclExtends.vl.size()) {
            classDeclExtends.vl.elementAt(i).accept(this, a.variavel, a.nivel + 1, nivel + 1);
            classDeclExtends.vl.size();
            ++i;
        }
        i = 0;
        while (i < classDeclExtends.ml.size()) {
            classDeclExtends.ml.elementAt(i).accept(this, a.metodo, a.nivel + 1, nivel + 1);
            ++i;
        }
        map.put(a.id, a);
    }


    @Override
    public void visit(MethodDecl methodDecl, Map map, int nivel, int level) {
        Metodos a = new Metodos();
        a.type = methodDecl.t.toString();
        a.id = methodDecl.i.toString();
        a.linha = methodDecl.line_number;
        if (map.containsKey(a.id)) {
            System.out.println("Método  já definida : ( " + a.type + " " + a.id + "() ) em " + (methodDecl.line_number + 1));
            while (map.containsKey(a.id)) {
                a.id = String.valueOf(a.id) + "@";
            }
        }
        methodDecl.t.acceptFirst(this, map, level, nivel + 1);
        methodDecl.i.accept(this, map, level, nivel + 1);
        int i = 0;
        while (i < methodDecl.fl.size()) {
            methodDecl.fl.elementAt(i).accept(this, a.arg, level, nivel);
            methodDecl.fl.size();
            ++i;
        }
        i = 0;
        while (i < methodDecl.vl.size()) {
            methodDecl.vl.elementAt(i).accept(this, a.variavel, a.nivel + 1, nivel);
            ++i;
        }
        i = 0;
        while (i < methodDecl.sl.size()) {
            methodDecl.sl.elementAt(i).acceptFirst(this, a.variavel, a.nivel + 1, nivel);
            methodDecl.sl.size();
            ++i;
        }
        methodDecl.e.acceptFirst(this, map, level, nivel + 1);
        map.put(a.id, a);
    }

    @Override
    public void visit(VarDecl varDecl, Map map, int nivel, int level) {
        Variaveis a = new Variaveis();
        a.type = varDecl.t.toString();
        a.id = varDecl.i.toString();
        a.linha = varDecl.line_number;
        if (map.containsKey(a.id)) {
            System.out.println("Variável  já definida : ( " + a.type + " " + a.id + " ) em " + (varDecl.line_number + 1));
            while (map.containsKey(a.id)) {
                a.id = String.valueOf(a.id) + "@";
            }
        }
        map.put(a.id, a);
        varDecl.t.acceptFirst(this, map, level, nivel + 1);
        varDecl.i.accept(this, map, level, nivel + 1);
    }


    @Override
    public void visit(If anIf, Map map, int nivel, int level) {
        anIf.e.acceptFirst(this, map, level, nivel + 1);
        anIf.s1.acceptFirst(this, map, level, nivel + 1);
        anIf.s2.acceptFirst(this, map, level, nivel + 1);
    }

    @Override
    public void visit(While aWhile, Map map, int nivel, int level) {
        aWhile.e.acceptFirst(this, map, level, nivel + 1);
        aWhile.s.acceptFirst(this, map, level, nivel + 1);
    }

    @Override
    public void visit(Print print, Map map, int nivel, int level) {
        print.e.acceptFirst(this, map, level, nivel + 1);
    }

    @Override
    public void visit(Return aReturn, Map map, int nivel, int level) {
        aReturn.e.acceptFirst(this, map, level, nivel);
    }

    @Override
    public void visit(ArrayAssign arrayAssign, Map map, int nivel, int level) {
        arrayAssign.i.accept(this, map, level, nivel + 2);
        arrayAssign.e1.acceptFirst(this, map, level, nivel + 2);
        arrayAssign.e2.acceptFirst(this, map, level, nivel + 1);
    }

    @Override
    public void visit(LessThan lessThan, Map map, int nivel, int level) {
        lessThan.e1.acceptFirst(this, map, level, nivel + 1);
        lessThan.e2.acceptFirst(this, map, level, nivel + 1);
    }

    @Override
    public void visit(And and, Map map, int nivel, int level) {
        and.e1.acceptFirst(this, map, level, nivel);
        and.e2.acceptFirst(this, map, level, nivel);
    }

    @Override
    public void visit(Assign assign, Map map, int nivel, int level) {
        assign.i.accept(this, map, level, nivel + 1);
        assign.e.acceptFirst(this, map, level, nivel + 1);
    }


    @Override
    public void visit(Plus plus, Map map, int nivel, int level) {
        plus.e1.acceptFirst(this, map, level, nivel + 1);
        plus.e2.acceptFirst(this, map, level, nivel + 1);
    }

    @Override
    public void visit(Minus minus, Map map, int nivel, int level) {
        minus.e1.acceptFirst(this, map, level, nivel + 1);
        minus.e2.acceptFirst(this, map, level, nivel + 1);
    }

    @Override
    public void visit(Times times, Map map, int nivel, int level) {
        times.e1.acceptFirst(this, map, level, nivel + 1);
        times.e2.acceptFirst(this, map, level, nivel + 1);
    }

    @Override
    public void visit(ArrayLength arrayLength, Map map, int nivel, int level) {
        arrayLength.e.acceptFirst(this, map, level, nivel + 1);
    }


    @Override
    public void visit(ArrayLookup arrayLookup, Map map, int nivel, int level) {
        arrayLookup.e1.acceptFirst(this, map, level, nivel + 1);
        arrayLookup.e2.acceptFirst(this, map, level, nivel + 1);
    }


    @Override
    public void visit(Call call, Map map, int nivel, int level) {
        call.e.acceptFirst(this, map, level, nivel + 1);
        call.i.accept(this, map, level, nivel + 1);
        int i = 0;
        while (i < call.el.size()) {
            call.el.elementAt(i).acceptFirst(this, map, level, nivel + 1);
            call.el.size();
            ++i;
        }
    }


    @Override
    public void visit(Block block, Map map, int nivel, int level) {
        int i = 0;
        while (i < block.sl.size()) {
            block.sl.elementAt(i).acceptFirst(this, map, level, nivel);
            ++i;
        }
    }

    @Override
    public void visit(Formal formal, Map map, int nivel, int level) {
        Variaveis a = new Variaveis();
        a.type = formal.t.toString();
        a.id = formal.i.toString();
        a.linha = formal.line_number;
        if (map.containsKey(a.id)) {
            System.out.println("Argumento  já definida : ( " + a.type + a.id + " ) em " + (formal.line_number + 1));
            while (map.containsKey(a.id)) {
                a.id = String.valueOf(a.id) + "@";
            }
        }
        map.put(a.id, a);
        formal.t.acceptFirst(this, map, level, nivel + 1);
        formal.i.accept(this, map, level, nivel + 1);
    }

    @Override
    public void visit(IntegerLiteral integerLiteral, Map map, int nivel, int level) {
    }

    @Override
    public void visit(True aTrue, Map map, int nivel, int level) {
    }

    @Override
    public void visit(False aFalse, Map map, int nivel, int level) {
    }

    @Override
    public void visit(IdentifierExp identifierExp, Map map, int nivel, int level) {
    }

    @Override
    public void visit(This aThis, Map map, int nivel, int level) {
    }

    @Override
    public void visit(NewArray newArray, Map map, int nivel, int level) {
        newArray.e.acceptFirst(this, map, level, nivel + 1);
    }

    @Override
    public void visit(NewObject newObject, Map map, int nivel, int level) {
        newObject.i.accept(this, map, level, nivel + 1);
    }

    @Override
    public void visit(Not not, Map map, int nivel, int level) {
        not.e.acceptFirst(this, map, level, nivel);
    }

    @Override
    public void visit(Identifier identifier, Map map, int nivel, int level) {
    }


    @Override
    public void visit(IntArrayType intArrayType, Map map, int nivel, int level) {
    }

    @Override
    public void visit(BooleanType booleanType, Map map, int nivel, int level) {
    }

    @Override
    public void visit(IntegerType integerType, Map map, int nivel, int level) {
    }

    @Override
    public void visit(IdentifierType identifierType, Map map, int nivel, int level) {
    }
}
