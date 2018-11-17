package br.com.uece.compilador.compiladorweb.tabelas;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lucas Vieira && Bruno Barbosa on 01/06/17.
 */

public class Metodos {
    public String type;
    public String id;
    public Map arg;
    public Map variavel;
    public int nivel;
    public int linha;

    public Metodos() {
        type = new String();
        id = new String();
        arg = new LinkedHashMap();
        variavel = new LinkedHashMap();
        nivel = 0;
    }

    public void identacao(StringBuilder log) {
        for (int i = 0; i < this.nivel; i++) {
            log.append("\t");
        }
    }

    String log() {
        StringBuilder log = new StringBuilder();
        identacao(log);
        log.append("\nMETODO");
        identacao(log);
        log.append(type).append(" ").append(id).append(" ");

        if (!arg.isEmpty()) {
            log.append("ARGURMENTO ");
            Set ss = arg.entrySet();
            for (Object s : ss) {
                Map.Entry entrada = (Map.Entry) s;
                Variaveis a = (Variaveis) entrada.getValue();
                log.append(a.log(false)).append(" ");
            }
        }

        if (!variavel.isEmpty()) {
            log.append("\n");
            Set ss = variavel.entrySet();
            for (Object s : ss) {
                Map.Entry entrada = (Map.Entry) s;
                Variaveis a = (Variaveis) entrada.getValue();
                a.nivel = nivel + 1;
                log.append(a.log(true));
            }
        }
        log.append("\n");
        return log.toString();
    }

    public void repetirVerificacaoVariavel(Map classMap, String nomeClass) {
        Classe myClass = (Classe) classMap.get(nomeClass);

        if (!variavel.isEmpty()) {
            Set ss = variavel.entrySet();
            Iterator ii = ss.iterator();
            while (ii.hasNext()) {
                Map.Entry v = (Map.Entry) ii.next();
                Variaveis a = (Variaveis) v.getValue();
                if (arg.containsKey(a.id)) {
                    System.out.println("A variavel ( " + a.type + " " + a.id + " ) foi redefinida, em " + (a.linha + 1));
                }
            }

            if (myClass.variavel != null) {
                ss = variavel.entrySet();
                ii = ss.iterator();
                while (ii.hasNext()) {
                    Map.Entry v = (Map.Entry) ii.next();
                    Variaveis a = (Variaveis) v.getValue();
                    if (myClass.variavel.containsKey(a.id))
                        System.out.println("A variavel ( " + a.type + " " + a.id + " ) foi redefinida, em " + (a.linha + 1));
                }
            }

            if (myClass.extendida != null) {
                String classPai = myClass.extendida;
                Classe exten = null;
                do {
                    if (classMap.containsKey(classPai)) {
                        exten = (Classe) classMap.get(classPai);

                        ss = variavel.entrySet();
                        ii = ss.iterator();

                        while (ii.hasNext()) {
                            Map.Entry entrada = (Map.Entry) ii.next();
                            Variaveis a = (Variaveis) entrada.getValue();

                            if (exten.variavel.containsKey(a.id)) {
                                System.out.println("A variavel ( " + a.type + " " + a.id + " ) da Super-Classe: " + exten.id + " foi redefinida, em " + (a.linha + 1));
                            }
                        }

                        classPai = exten.extendida;
                    } else
                        break;

                } while (classPai != null);
            }
        }

        if (!arg.isEmpty()) {
            if (myClass.variavel != null) {
                Set ss = arg.entrySet();
                Iterator ii = ss.iterator();
                while (ii.hasNext()) {
                    Map.Entry v = (Map.Entry) ii.next();
                    Variaveis a = (Variaveis) v.getValue();
                    if (myClass.variavel.containsKey(a.id))
                        System.out.println("O argumento ( " + a.type + " " + a.id + " ) foi redefinido, em " + (a.linha + 1));
                }
            }

            if (myClass.extendida != null) {
                String classPai = myClass.extendida;
                Classe exten = null;
                do {
                    if (classMap.containsKey(classPai)) {
                        exten = (Classe) classMap.get(classPai);

                        Set ss = arg.entrySet();
                        Iterator ii = ss.iterator();

                        while (ii.hasNext()) {
                            Map.Entry entrada = (Map.Entry) ii.next();
                            Variaveis a = (Variaveis) entrada.getValue();

                            if (exten.variavel.containsKey(a.id)) {
                                System.out.println("O argumento ( " + a.type + " " + a.id + " )) da Super-Classe foi redefinido: " + exten.id + ", em " + (a.linha + 1));
                            }
                        }

                        classPai = exten.extendida;
                    } else
                        break;

                } while (classPai != null);
            }
        }
    }

    public void verificarTipoExistente(Map cl) {
        if (variavel != null) {
            Set ss = variavel.entrySet();
            Iterator ii = ss.iterator();
            while (ii.hasNext()) {
                Map.Entry entrada = (Map.Entry) ii.next();
                Variaveis a = (Variaveis) entrada.getValue();
                a.verificarTipoExistente(cl);
            }
        }
        if (!arg.isEmpty()) {
            Set ss = arg.entrySet();
            Iterator ii = ss.iterator();
            while (ii.hasNext()) {
                Map.Entry v = (Map.Entry) ii.next();
                Variaveis a = (Variaveis) v.getValue();
                a.verificarTipoExistente(cl);
            }
        }
    }
}