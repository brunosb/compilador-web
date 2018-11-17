package br.com.uece.compilador.compiladorweb.ast.visitor;


import br.com.uece.compilador.compiladorweb.ast.*;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.tabelas.Metodos;
import br.com.uece.compilador.compiladorweb.tabelas.Variaveis;

import java.util.Map;

/**
 * Created by Bruno Barbosa and Yan Gurgel on 09/11/18.
 */

public class ThirdVisitor implements SecondVisitor {
    private StringBuilder code;
    ClassDecl curClass = null;
    MethodDecl curMethod = null;
    MainClass mC = null;
    private int labelCounter;

    @Override
    public boolean eCompativel(Map mapa, String classe, String extension) {
        return false;
    }

    public String getCode() {
        return this.code.toString();
    }

    public int getVarOffsetInMethod(Identifier id) {
        int i = 0;
        while (i < this.curMethod.fl.size()) {
            if (this.curMethod.fl.elementAt(i).i.s.equals(id.s)) {
                return (i + 2) * 4;
            }
            ++i;
        }
        i = 0;
        while (i < this.curMethod.vl.size()) {
            if (this.curMethod.vl.elementAt(i).i.s.equals(id.s)) {
                return (i + 1) * -4;
            }
            ++i;
        }
        return 0;
    }

    public int getVarOffsetInClass(Identifier id) {
        int i = 0;
        while (i < this.curClass.vl.size()) {
            if (this.curClass.vl.elementAt(i).i.s.equals(id.s)) {
                return i * 4;
            }
            ++i;
        }
        return 0;
    }

    @Override
    public void visit(Return n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
    }

    @Override
    public void visit(Program n, Map mapa, int nivel, Classe cl, Metodos mt) {
        this.labelCounter = 0;
        this.code = new StringBuilder();
        n.m.accept2(this, mapa, nivel + 1, null, null);
        int i = 0;
        while (i < n.cl.size()) {
            n.cl.elementAt(i).accept2(this, mapa, nivel + 1, null, null);
            ++i;
        }
    }

    @Override
    public void visit(MainClass n, Map mapa, int nivel, Classe cl, Metodos mt) {
        Classe c = (Classe) mapa.get(n.i1.s);
        this.mC = n;
        Metodos m = (Metodos) c.metodo.get("main");
        this.code.append("\t.text\n");
        this.code.append("\t.globl _asm_main\n\n");
        this.code.append("_asm_main:\n");
        this.code.append("\tpushl\t%ebp\n");
        this.code.append("\tmovl\t%esp,%ebp\n\n");
        n.s.acceptSecond(this, mapa, nivel + 1, c, m);
        this.code.append("\tmovl\t%ebp,%esp\n");
        this.code.append("\tpopl\t%ebp\n");
        this.code.append("\tret\n\n");
    }

    @Override
    public void visit(ClassDeclSimple n, Map mapa, int nivel, Classe cl, Metodos mt) {
        Classe c = (Classe) mapa.get(n.i.s);
        this.curClass = n;
        this.curMethod = null;
        int i = 0;
        while (i < n.ml.size()) {
            n.ml.elementAt(i).accept2(this, mapa, nivel + 1, c, null);
            ++i;
        }
    }

    @Override
    public void visit(ClassDeclExtends n, Map mapa, int nivel, Classe cl, Metodos mt) {
        Classe c = (Classe) mapa.get(n.i.s);
        this.curClass = n;
        this.curMethod = null;
        int i = 0;
        while (i < n.ml.size()) {
            n.ml.elementAt(i).accept2(this, mapa, nivel + 1, c, null);
            ++i;
        }
    }


    @Override
    public void visit(MethodDecl n, Map mapa, int nivel, Classe cl, Metodos mt) {
        Metodos m = (Metodos) cl.metodo.get(n.i.s);
        this.curMethod = n;
        this.code.append(String.valueOf(this.curClass.i.s) + "_" + n.i.s + ":\n");
        this.code.append("\tpushl\t%ebp\n");
        this.code.append("\tmovl\t%esp,%ebp\n");
        this.code.append("\tsub\t$" + n.vl.size() * 4 + ",%esp\n\n");
        int i = 0;
        while (i < n.sl.size()) {
            n.sl.elementAt(i).acceptSecond(this, mapa, nivel + 1, cl, m);
            n.sl.size();
            ++i;
        }
        n.e.acceptSecond(this, mapa, nivel + 1, cl, m);
        this.code.append("\tmovl\t%ebp,%esp\n");
        this.code.append("\tpopl\t%ebp\n");
        this.code.append("\tret\n\n");
    }


    @Override
    public void visit(Block n, Map mapa, int nivel, Classe cl, Metodos mt) {
        int i = 0;
        while (i < n.sl.size()) {
            n.sl.elementAt(i).acceptSecond(this, mapa, nivel, cl, mt);
            ++i;
        }
    }

    @Override
    public void visit(If n, Map mapa, int nivel, Classe cl, Metodos mt) {
        int els = this.labelCounter++;
        int done = this.labelCounter++;
        n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tjz\tL" + els + "\n");
        n.s1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tjmp\tL" + done + "\nL" + els + ":\n");
        n.s2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("L" + done + ":\n");
    }

    @Override
    public void visit(While n, Map mapa, int nivel, Classe cl, Metodos mt) {
        int test = this.labelCounter++;
        int done = this.labelCounter++;
        this.code.append("L" + test + ":\n");
        n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tjz\tL" + done + "\n");
        n.s.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tjmp\tL" + test + "\n");
        this.code.append("L" + done + ":\n");
    }

    @Override
    public void visit(Print n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpush\t%eax\n");
        this.code.append("\tcall\t_put\n");
        this.code.append("\tadd\t$4,%esp\n\n");
    }

    @Override
    public void visit(Assign n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String identif = n.i.s;
        n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        Classe c = (Classe) mapa.get(this.curClass.i.s);
        if (c.variavel != null && c.variavel.containsKey(n.i.s)) {
            this.code.append("\tmov\t" + (this.curMethod.fl.size() + 2) * 4 + "(%ebp),%edx\n");
            this.code.append("\tmov\t%eax," + this.getVarOffsetInClass(n.i) + "(%edx)\n");
        } else {
            this.code.append("\tmov\t%eax," + this.getVarOffsetInMethod(n.i) + "(%ebp)\n");
        }
    }

    @Override
    public void visit(ArrayAssign n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.i.accept2(this, mapa, nivel + 2, cl, mt);
        this.code.append("\tpush\t%eax\n");
        n.e1.acceptSecond(this, mapa, nivel + 2, cl, mt);
        this.code.append("\tpush\t%eax\n");
        n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tinc\t%eax\n");
        this.code.append("\timul\t$4,%eax\n");
        this.code.append("\tpop\t%edx\n");
        this.code.append("\tpop\t%ecx\n");
        this.code.append("\tmov\t%edx,%eax(%ecx)\n");
        this.code.append("\tpush\t%eax\n");
    }

    @Override
    public String visit(And n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpush\t%eax\n");
        n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpop\t%edx\n");
        this.code.append("\tand\t%edx,%eax\n");
        return "boolean";
    }

    @Override
    public String visit(LessThan n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpush\t%eax\n");
        n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpop\t%edx\n");
        this.code.append("\tcmp\t%edx,%eax\n");
        int less = this.labelCounter++;
        int done = this.labelCounter++;
        this.code.append("\tjl\tL" + less + "\n\tmov\t$0,%eax\n\tjmp\tL" + done + "\n");
        this.code.append("L" + less + ":\n\tmov\t$1,%eax\n");
        this.code.append("L" + done + ":\n");
        return "boolean";
    }

    @Override
    public String visit(Plus n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpush\t%eax\n");
        n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpop\t%edx\n");
        this.code.append("\tadd\t%edx,%eax\n");
        return "int";
    }

    @Override
    public String visit(Minus n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpush\t%eax\n");
        n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpop\t%edx\n");
        this.code.append("\tsub\t%edx,%eax\n");
        return "int";
    }

    @Override
    public String visit(Times n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpush\t%eax\n");
        n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpop\t%edx\n");
        this.code.append("\timul\t%edx,%eax\n");
        return "int";
    }

    @Override
    public String visit(ArrayLookup n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e1.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpush\t%eax\n");
        n.e2.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpop\t%edx\n");
        this.code.append("\tinc\t%eax\n");
        this.code.append("\timul\t$4,%eax\n");
        this.code.append("\tmov\t%eax(%edx),%eax\n");
        return "int";
    }

    @Override
    public String visit(ArrayLength n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tmov\t$0(%edx),%eax\n");
        return "int";
    }

    @Override
    public String visit(Call n, Map mapa, int nivel, Classe cl, Metodos mt) {
        String expType = n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tpush\t%eax\n");
        int i = n.el.size() - 1;
        while (i >= 0) {
            n.el.elementAt(i).acceptSecond(this, mapa, nivel + 1, cl, mt);
            this.code.append("\tpush\t%eax\n");
            --i;
        }
        Classe a = null;
        if (!expType.equals(cl.id)) {
            if (mapa.containsKey(expType)) {
                a = (Classe) mapa.get(expType);
            }
        } else {
            a = cl;
        }
        n.i.accept2(this, mapa, nivel + 1, cl, mt);
        Metodos b = null;
        if (a != null) {
            if (a.metodo.containsKey(n.i.s)) {
                b = (Metodos) a.metodo.get(n.i.s);
            } else if (a.extendida != null) {
                String classPai = a.extendida;
                Classe exten = null;
                do {
                    exten = (Classe) mapa.get(classPai);
                    if (!exten.metodo.containsKey(n.i.s)) continue;
                    b = (Metodos) exten.metodo.get(n.i.s);
                    break;
                } while ((classPai = exten.extendida) != null);
            }
        }
        this.code.append("\tcall\t" + a.id + "_" + b.id + "\n");
        if (n.el.size() > 0) {
            this.code.append("\tadd\t$" + (n.el.size() + 1) * 4 + ",%esp\n");
            if (b != null) {
                return b.type;
            }
        } else if (b != null) {
            return b.type;
        }
        return "null";
    }

    @Override
    public String visit(IntegerLiteral n, Map mapa, int nivel, Classe cl, Metodos mt) {
        this.code.append("\tmov\t$" + n.i + ",%eax\n");
        return "int";
    }

    @Override
    public String visit(True n, Map mapa, int nivel, Classe cl, Metodos mt) {
        this.code.append("\tmov\t$1,%eax\n");
        return "boolean";
    }

    @Override
    public String visit(False n, Map mapa, int nivel, Classe cl, Metodos mt) {
        this.code.append("\tmov\t$0,%eax\n");
        return "boolean";
    }

    @Override
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
        } else if (cl.extendida != null) {
            String classPai = cl.extendida;
            Classe exten = null;
            do {
                if ((exten = (Classe) mapa.get(classPai)) != null) {
                    if (exten.variavel.containsKey(n.s)) {
                        Variaveis a = (Variaveis) exten.variavel.get(n.s);
                        identifType = a.type;
                        break;
                    }
                    classPai = exten.extendida;
                    continue;
                }
                classPai = null;
            } while (classPai != null);
        }
        if (identifType == null) {
            identifType = "null";
        }
        return identifType;
    }

    @Override
    public String visit(This n, Map mapa, int nivel, Classe cl, Metodos mt) {
        this.code.append("\tmov\t" + (this.curMethod.fl.size() + 2) * 4 + "(%ebp),%eax\n");
        return cl.id;
    }

    @Override
    public String visit(NewArray n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e.acceptSecond(this, mapa, nivel + 1, cl, mt);
        this.code.append("\tinc\t%eax\n");
        this.code.append("\tpush\t%eax\n");
        this.code.append("\tcall\t_mjmalloc\n");
        this.code.append("\tadd\t$4,%esp\n\n");
        return "int []";
    }

    @Override
    public String visit(NewObject n, Map mapa, int nivel, Classe cl, Metodos mt) {
        Classe c = (Classe) mapa.get(n.i.s);
        int s = c.tamanhoInstancia();
        this.code.append("\tpush\t$" + s + "\n");
        this.code.append("\tcall\t_mjmalloc\n");
        this.code.append("\tadd\t$4,%esp\n\n");
        return n.i.s;
    }

    @Override
    public String visit(Not n, Map mapa, int nivel, Classe cl, Metodos mt) {
        n.e.acceptSecond(this, mapa, nivel, cl, mt);
        this.code.append("\txor\t$1,%eax\n");
        return "boolean";
    }

    @Override
    public void visit(Identifier n, Map mapa, int nivel, Classe cl, Metodos mt) {
        if (this.curClass != null) {
            Classe c = (Classe) mapa.get(this.curClass.i.s);
            if (c.variavel.containsKey(n.s)) {
                this.code.append("\tmov\t" + (this.curMethod.fl.size() + 2) * 4 + "(%ebp),%edx\n");
                this.code.append("\tmov\t%eax," + this.getVarOffsetInClass(n) + "(%edx)\n");
            }
            this.code.append("\tmov\t" + this.getVarOffsetInMethod(n) + "(%ebp),%eax\n");
        } else {
            Classe c = (Classe) mapa.get(this.mC.i1.s);
        }
    }

    @Override
    public void visit(Formal n, Map mapa, int nivel, Classe cl, Metodos mt) {
    }

    @Override
    public void visit(IntArrayType n, Map mapa, int nivel, Classe cl, Metodos mt) {
    }

    @Override
    public void visit(BooleanType n, Map mapa, int nivel, Classe cl, Metodos mt) {
    }

    @Override
    public void visit(IntegerType n, Map mapa, int nivel, Classe cl, Metodos mt) {
    }

    @Override
    public void visit(IdentifierType n, Map mapa, int nivel, Classe cl, Metodos mt) {
    }

    @Override
    public void visit(VarDecl n, Map mapa, int nivel, Classe cl, Metodos mt) {
    }
}
