package br.com.uece.compilador.compiladorweb.ast;

import java.util.Vector;

public class FormalList extends ASTNode {
   private Vector list;

   public FormalList(int ln) {
      super(ln);
      list = new Vector();
   }

   public void addElement(Formal n) {
      list.addElement(n);
   }

   public Formal elementAt(int i)  { 
      return (Formal)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}
