package br.com.uece.compilador.compiladorweb.ast.visitor;

import br.com.uece.compilador.compiladorweb.ast.*;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;
import br.com.uece.compilador.compiladorweb.tabelas.Variaveis;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Bruno Barbosa and Yan Gurgel on 09/11/18.
 */
public class SecondVisitorImpl implements SecondVisitor {
    // Return added for the toy example language---they are subsumed in the MiniJava AST by the MethodDecl nodes.

    // Exp e;

    public boolean eCompativel(Map mapa, String classe, String extension) {
        if (classe.equals("int") || extension.equals("int")) {
            if (classe.equals(extension))
                return true;
        } else if (classe.equals("int []") || extension.equals("int []")) {
            if (classe.equals(extension))
                return true;
        } else if (classe.equals("boolean") || extension.equals("boolean")) {
            if (classe.equals(extension))
                return true;
        } else if (classe.equals("null") || extension.equals("null")) {
            return false;
        } else {
            if (extension.equals(classe)) {
                return true;
            } else {
                String extenType = "null";

                if (mapa.containsKey(classe)) {
                    Classe myClass = (Classe) mapa.get(classe);

                    if (myClass.extendida != null) {
                        String classPai = myClass.extendida;
                        Classe exten;
                        do {
                            if (mapa.containsKey(classPai)) {
                                exten = (Classe) mapa.get(classPai);

                                if (exten.id.equals(extension)) {
                                    extenType = extension;
                                    break;
                                }

                                classPai = exten.extendida;
                            } else
                                break;

                        } while (classPai != null);
                    }
                }
                if (extension.equals(extenType))
                    return true;
            }
        }
        return false;
    }


    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.m.accept2(this, mapa, nivel + 1, null, null);
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.elementAt(i).accept2(this, mapa, nivel + 1, null, null);
        }
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n, Map mapa, int nivel, Classe cl, Metodos mt) {
        Classe c = (Classe) mapa.get(n.i1.s);
        n.i1.accept2(this, mapa, nivel + 1, c, null);

        Metodos m = (Metodos) c.metodo.get("main");

        n.i2.accept2(this, mapa, nivel + 1, c, m);

        n.s.acceptSecond(this, mapa, nivel + 1, c, m);
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n, Map mapa, int nivel, Classe cl, Metodos mt) {
        Classe c = (Classe) mapa.get(n.i.s);

        n.i.accept2(this, mapa, nivel + 1, c, null);

        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.elementAt(i).accept2(this, mapa, nivel + 1, c, null);
        }
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.elementAt(i).accept2(this, mapa, nivel + 1, c, null);
        }
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n, Map mapa, int nivel, Classe cl, Metodos mt) {
        Classe c = (Classe) mapa.get(n.i.s);

        n.i.accept2(this, mapa, nivel + 1, c, null);

        n.j.accept2(this, mapa, nivel + 1, c, null);

        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.elementAt(i).accept2(this, mapa, nivel + 1, c, null);

        }
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.elementAt(i).accept2(this, mapa, nivel + 1, c, null);
        }
    }

    // Type t;
    // Identifier i;
    public void visit(VarDecl n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.t.acceptSecond(this, mapa, nivel + 1, cl, mt);
        n.i.accept2(this, mapa, nivel + 1, cl, mt);
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n, Map mapa, int nivel, Classe cl, Metodos mt) {
        Metodos m = (Metodos) cl.metodo.get(n.i.s);

        n.t.acceptSecond(this, mapa, nivel + 1, cl, m);
        n.i.accept2(this, mapa, nivel + 1, cl, m);

        for (int i = 0; i < n.fl.size(); i++) {
            n.fl.elementAt(i).accept2(this, mapa, nivel + 1, cl, m);
        }
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.elementAt(i).accept2(this, mapa, nivel + 1, cl, m);
        }
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.elementAt(i).acceptSecond(this, mapa, nivel + 1, cl, m);
        }

        String returnType = n.e.acceptSecond(this, mapa, nivel + 1, cl, m);
        if (!n.t.toString().equals(returnType))
            System.out.println("Errado=> ( " + returnType + " ) correto=> ( " + n.t.toString() + " ) em " + (n.line_number + 1));
    }

    // Type t;
    // Identifier i;
    public void visit(Formal n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.t.acceptSecond(this, mapa, nivel, cl, mt);
        n.i.accept2(this, mapa, nivel, cl, mt);
    }

    // StatementList sl;
    public void visit(Block n, Map mapa, int nivel, Classe cl, Metodos mt) {
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.elementAt(i).acceptSecond(this, mapa, nivel, cl, mt);
        }
    }

    public void visit(Return n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
    }

    // Exp e;
    // Statement s1,s2;
    public void visit(If n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String expType = n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        if (!expType.equals("boolean")) {
            System.out.println("Errado=> if(" + expType + ") correto=>  if( boolean ) em " + (n.line_number + 1));
        }
        n.s1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        n.s2.acceptSecond(this, mapa, nivel + 1, cl, mt);
    }

    // Exp e;
    // Statement s;
    public void visit(While n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String expType = n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        if (!expType.equals("boolean"))
            System.out.println("Errado=> while(" + expType + ") correto=> while( boolean ) em " + (n.line_number + 1));
        n.s.acceptSecond(this, mapa, nivel + 1, cl, mt);
    }

    // Exp e;
    public void visit(Print n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String expType = n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        if (!expType.equals("int"))
            System.out.println("Errado=> println( " + expType + " ) correto=> println( int ) em " + (n.line_number + 1));
    }

    // Identifier i;
    // Exp e;
    public void visit(Assign n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.i.accept2(this, mapa, nivel + 1, cl, mt);

        String identif = n.i.s;
        String identifType = null;
        if (cl.variavel != null && cl.variavel.containsKey(n.i.s)) {
            Variaveis a = (Variaveis) cl.variavel.get(n.i.s);
            identifType = a.type;
        } else if (mt.arg.containsKey(n.i.s)) {
            Variaveis a = (Variaveis) mt.arg.get(n.i.s);
            identifType = a.type;

        } else if (mt.variavel.containsKey(n.i.s)) {
            Variaveis a = (Variaveis) mt.variavel.get(n.i.s);
            identifType = a.type;
        } else if (cl.extendida != null) {
            String classPai = cl.extendida;
            Classe exten;
            do {
                if (mapa.containsKey(classPai)) {
                    exten = (Classe) mapa.get(classPai);

                    if (exten.variavel.containsKey(identif)) {
                        Variaveis a = (Variaveis) exten.variavel.get(identif);
                        identifType = a.type;
                        break;
                    }

                    classPai = exten.extendida;
                } else break;
            } while (classPai != null);
        }

        String expType = n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);

        if (identifType == null) {
            identifType = "null";
            System.out.println("Variavel ( " + identif + " ) não declarada em " + (n.line_number + 1));
        } else {
            if (!identifType.equals(expType) && !expType.equals("null")) {
                if (!expType.equals("int") && !expType.equals("int []") && !expType.equals("boolean")) {
                    if (!eCompativel(mapa, expType, identifType)) {
                        System.out.println("Errado=> ( " + identifType + " ) correto=> ( " + expType + " ) em " + (n.line_number + 1));
                    }
                }

            }
        }

    }

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.i.accept2(this, mapa, nivel + 2, cl, mt);
        String identif = n.i.s;
        String identifType = null;
        if (cl.variavel.containsKey(identif)) {
            Variaveis a = (Variaveis) cl.variavel.get(identif);
            identifType = a.type;
        } else if (mt.arg.containsKey(identif)) {
            Variaveis a = (Variaveis) mt.arg.get(identif);
            identifType = a.type;
        } else if (mt.variavel.containsKey(identif)) {
            Variaveis a = (Variaveis) mt.variavel.get(identif);
            identifType = a.type;
        } else if (cl.extendida != null)////
        {
            String classPai = cl.extendida;
            Classe exten = null;
            do {
                if (mapa.containsKey(classPai)) {
                    exten = (Classe) mapa.get(classPai);

                    if (exten.variavel.containsKey(identif)) {
                        Variaveis a = (Variaveis) exten.variavel.get(identif);
                        identifType = a.type;
                        break;
                    }

                    classPai = exten.extendida;
                } else
                    break;
            } while (classPai != null);
        }

        String expType1 = n.e1.acceptSecond(this, mapa, nivel + 2, cl, mt);
        String expType2 = n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);

        if (identifType == null) {
            identifType = "null";
            System.out.println("Variavel ( " + identif + " ) não declarada em " + (n.line_number + 1));
        } else {
            if (!identifType.equals("int []"))
                System.out.println("Variavel ( " + identif + " ) não é do tipo ( int [] ) em " + (n.line_number + 1));
        }

        if (!expType1.equals("int") && !expType1.equals("null"))
            System.out.println("Valor incompativel: ( " + expType1 + " ) em " + (n.line_number + 1));

        if (!expType2.equals("int") && !expType2.equals("null"))
            System.out.println("Valor Incompativel: ( " + expType1 + " ) em " + (n.line_number + 1));


    }

    // Exp e1,e2;
    public String visit(And n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String typeExp1 = n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        String typeExp2 = n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        if (!typeExp1.equals("null") && !typeExp2.equals("null")) {
            if ((!typeExp2.equals("boolean")) || (!typeExp1.equals("boolean"))) {
                System.out.println("Tipos incompativeis ((" + typeExp1 + ") && (" + typeExp2 + ")) em " + (n.line_number + 1));
            }
        }
        return "boolean";
    }


    // Exp e1,e2;
    public String visit(Plus n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String typeExp1 = n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        String typeExp2 = n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);

        if (!typeExp1.equals("null") && !typeExp2.equals("null")) {
            if (!typeExp1.equals(typeExp2)) {
                System.out.println("Tipos incompativeis (( " + typeExp1 + " ) + ( " + typeExp2 + " )) em " + (n.line_number + 1));
            } else if (!typeExp1.equals("int")) {
                System.out.println("Tipos incompativeis (( int ) + ( " + typeExp1 + " )) em " + (n.line_number + 1));
            } else if (!typeExp2.equals("int")) {
                System.out.println("Tipos incompativeis (( " + typeExp2 + " ) + ( int )) em " + (n.line_number + 1));
            }
        }
        return "int";
    }

    // Exp e1,e2;
    public String visit(Minus n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String typeExp1 = n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        String typeExp2 = n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);

        if (!typeExp1.equals("null") && !typeExp2.equals("null")) {
            if (!typeExp1.equals(typeExp2)) {
                System.out.println("Tipos incompativeis (( " + typeExp1 + " ) - ( " + typeExp2 + " )) em " + (n.line_number + 1));
            } else if (!typeExp1.equals("int")) {
                System.out.println("Tipos incompativeis (( int ) - ( " + typeExp1 + " )) em " + (n.line_number + 1));
            } else if (!typeExp2.equals("int")) {
                System.out.println("Tipos incompativeis (( " + typeExp2 + " ) - ( int )) em " + (n.line_number + 1));
            }
        }
        return "int";
    }

    // Exp e1,e2;
    public String visit(Times n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String typeExp1 = n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        String typeExp2 = n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);

        if (!typeExp1.equals("null") && !typeExp2.equals("null")) {
            if (!typeExp1.equals(typeExp2)) {
                System.out.println("Tipos incompativeis (( " + typeExp1 + " ) * ( " + typeExp2 + " )) em " + (n.line_number + 1));
            } else if (!typeExp1.equals("int")) {
                System.out.println("Tipos incompativeis (( int ) * ( " + typeExp1 + " )) em " + (n.line_number + 1));
            } else if (!typeExp2.equals("int")) {
                System.out.println("Tipos incompativeis (( " + typeExp2 + " ) * ( int )) em " + (n.line_number + 1));
            }
        }
        return "int";
    }

    // Exp e1,e2;
    public String visit(LessThan n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String typeExp1 = n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        String typeExp2 = n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);

        if (!typeExp1.equals("null") && !typeExp2.equals("null")) {
            if (!typeExp1.equals(typeExp2)) {
                System.out.println("Tipos incompativeis (( " + typeExp1 + " ) < ( " + typeExp2 + " )) em " + (n.line_number + 1));
            } else if (!typeExp1.equals("int")) {
                System.out.println("Tipos incompativeis (( int ) < ( " + typeExp1 + " )) em " + (n.line_number + 1));
            } else if (!typeExp2.equals("int")) {
                System.out.println("Tipos incompativeis (( " + typeExp2 + " ) < ( int )) em: " + (n.line_number + 1));
            }
        }
        return "boolean";
    }

    // Exp e1,e2;
    public String visit(ArrayLookup n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String expType1 = n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        String expType2 = n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        if (!expType1.equals("null")) {
            if (!expType1.equals("int []") && !expType1.equals("null"))
                System.out.println("Errado=> ( " + expType1 + " ) correto=> (( int [] )) em " + (n.line_number + 1));
        }

        if (!expType2.equals("int") && !expType2.equals("null"))
            System.out.println("Tipo incompativel ( [" + expType2 + "] ) em " + (n.line_number + 1));

        return "int";
    }

    // Exp e;
    public String visit(ArrayLength n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String expType = n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        if (!expType.equals("int []") && !expType.equals("null"))
            System.out.println("Errado=> ( " + expType + " ) correto=> ( int [] ) em " + (n.line_number + 1));

        return "int";
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    public String visit(Call n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String expType = n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        Classe a = null;

        if (!expType.equals(cl.id)) {
            if (mapa.containsKey(expType)) {
                a = (Classe) mapa.get(expType);
            }
        } else
            a = cl;

        n.i.accept2(this, mapa, nivel + 1, cl, mt);

        Metodos b = null;
        if (a != null)
            if (a.metodo.containsKey(n.i.s)) {
                b = (Metodos) a.metodo.get(n.i.s);
            } else {
                if (a.extendida != null)////
                {
                    String classPai = a.extendida;
                    Classe exten;
                    do {
                        exten = (Classe) mapa.get(classPai);

                        if (exten.metodo.containsKey(n.i.s)) {
                            b = (Metodos) exten.metodo.get(n.i.s);
                            break;
                        }

                        classPai = exten.extendida;

                    } while (classPai != null);
                }
            }
        if (n.el.size() > 0) {
            Map argumentos = new LinkedHashMap();
            for (int i = 0; i < n.el.size(); i++) {
                String argExpType = n.el.elementAt(i).acceptSecond(this, mapa, nivel + 1, cl, mt);
                String key = "@";
                while (argumentos.containsKey(key)) key += "@";
                argumentos.put(key, argExpType);
            }
            if (b != null) {
                Map argMet = b.arg;

                boolean erroDeArgumento = false;

                Set s1 = argMet.entrySet();
                Set s2 = argumentos.entrySet();
                Iterator i1 = s1.iterator();
                Iterator i2 = s2.iterator();

                if (argumentos.size() > argMet.size()) {
                    erroDeArgumento = true;
                } else if (argumentos.size() < argMet.size()) {
                    erroDeArgumento = true;
                } else {
                    while (i2.hasNext()) {
                        Map.Entry entrada1 = (Map.Entry) i1.next();
                        Variaveis vari1 = (Variaveis) entrada1.getValue();

                        Map.Entry entrada2 = (Map.Entry) i2.next();
                        String vari2 = (String) entrada2.getValue();

                        if (!eCompativel(mapa, vari2, vari1.type))
                            erroDeArgumento = true;
                    }
                }

                if (erroDeArgumento) {
                    System.out.print("Argumentos incorretos em: (( " + n.i.s + "(");

                    Set ss = argumentos.entrySet();
                    Iterator ii = ss.iterator();

                    while (ii.hasNext()) {
                        Map.Entry entrada = (Map.Entry) ii.next();
                        String vari = (String) entrada.getValue();
                        System.out.print(vari);
                        if (ii.hasNext())
                            System.out.print(", ");
                    }
                    System.out.print(") )) ");

                    System.out.print("deveria ser: (( " + n.i.s + "(");

                    ss = argMet.entrySet();
                    ii = ss.iterator();

                    while (ii.hasNext()) {
                        Map.Entry entrada = (Map.Entry) ii.next();
                        Variaveis vari = (Variaveis) entrada.getValue();
                        System.out.print(vari.type);
                        if (ii.hasNext())
                            System.out.print(", ");
                    }
                    System.out.println(") )) em " + (n.line_number + 1));
                }

                return b.type;
            }
        } else {
            if (b != null)
                return b.type;
        }

        System.out.println("Metodo ( " + n.i.s + "()" + " ) não encontrado na linha " + (n.line_number + 1));
        return "null";

    }

    // String s;
    public String visit(IdentifierExp n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String identifType = null;
        if (cl.variavel.containsKey(n.s)) {
            Variaveis a = (Variaveis) cl.variavel.get(n.s);
            identifType = new String(a.type);
        } else if (mt.arg.containsKey(n.s)) {
            Variaveis a = (Variaveis) mt.arg.get(n.s);
            identifType = new String(a.type);
        } else if (mt.variavel.containsKey(n.s)) {
            Variaveis a = (Variaveis) mt.variavel.get(n.s);
            identifType = new String(a.type);
        } else if (cl.extendida != null)////
        {
            String classPai = cl.extendida;
            Classe exten = null;
            do {
                exten = (Classe) mapa.get(classPai);
                if (exten != null) {
                    if (exten.variavel.containsKey(n.s)) {
                        Variaveis a = (Variaveis) exten.variavel.get(n.s);
                        identifType = a.type;
                        break;
                    }

                    classPai = exten.extendida;
                } else {
                    classPai = null;

                }
            } while (classPai != null);
        }

        if (identifType == null) {
            identifType = "null";
            System.out.println("Variavel não foi declarada: (" + n.s + ") em " + (n.line_number + 1));
        }

        return identifType;

    }

    public String visit(This n, Map mapa, int nivel, Classe cl, Metodos mt) {
        return cl.id;
    }

    // Exp e;
    public String visit(NewArray n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String typeExp = n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        if (!typeExp.equals("int")) {
            System.out.println("Tipo incompativel ( [" + typeExp + "] ) em " + (n.line_number + 1));
        }

        return "int []";
    }

    // Identifier i;
    public String visit(NewObject n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.i.accept2(this, mapa, nivel + 1, cl, mt);
        if (!mapa.containsKey(n.i.s)) {
            System.out.println("Tipo ainda não definido: (" + n.i.s + ") em " + (n.line_number + 1));
        }

        return n.i.s;
    }

    // Exp e;
    public String visit(Not n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String typeExp = n.e.acceptSecond(this, mapa, nivel, cl, mt);
        if (!typeExp.equals("boolean")) {
            System.out.println("Tipo incompativel  Not( " + typeExp + " ) em " + (n.line_number + 1));
        }

        return "boolean";
    }


    // int i;
    public String visit(IntegerLiteral n, Map mapa, int nivel, Classe cl, Metodos mt) {
        return "int";
    }

    public String visit(True n, Map mapa, int nivel, Classe cl, Metodos mt) {
        return "boolean";
    }

    public String visit(False n, Map mapa, int nivel, Classe cl, Metodos mt) {
        return "boolean";
    }


    // String s;
    public void visit(Identifier n, Map mapa, int nivel, Classe cl, Metodos mt) {

    }

    public void visit(IntArrayType n, Map mapa, int nivel, Classe cl, Metodos mt) {
    }

    public void visit(BooleanType n, Map mapa, int nivel, Classe cl, Metodos mt) {

    }

    public void visit(IntegerType n, Map mapa, int nivel, Classe cl, Metodos mt) {

    }

    // String s;
    public void visit(IdentifierType n, Map mapa, int nivel, Classe cl, Metodos mt) {

    }
}
