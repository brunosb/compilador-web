package br.com.uece.compilador.compiladorweb.tabelas;


import java.util.Map;

/**
 * Created by Lucas Vieira && Bruno Barbosa on 10/05/17.
 */

public class Variaveis {
    public String type;
    public String id;
    public int nivel;
    public int linha;

    public Variaveis() {
        type = new String();
        id = new String();
        nivel = 0;
        linha = 0;
    }

    private void identacao(StringBuilder log) {
        for (int i = 0; i < this.nivel; i++) {
            log.append("\t");
        }
    }

    String log(boolean var) {
        StringBuilder log = new StringBuilder();
        if (var) {
            identacao(log);
            log.append("\nVARIAVEL");
            identacao(log);
            log.append(type).append(" ").append(id).append(" ").append("\n");
        } else {
            log.append(type).append(" ").append(id).append(" ");
        }
        return log.toString();
    }

    public void verificarTipoExistente(Map cl) {
        if (!type.equals("int") && !type.equals("int []") && !type.equals("boolean") && !cl.containsKey(type) && !type.equals("String []"))
            System.out.println("O Tipo: ( " + type + " ) n�o foi definido, em " + (linha + 1));
    }
}
