package br.com.uece.compilador.compiladorweb.service;

import br.com.uece.compilador.compiladorweb.ast.Program;
import br.com.uece.compilador.compiladorweb.ast.visitor.FirstVisitorImpl;
import br.com.uece.compilador.compiladorweb.ast.visitor.SecondVisitorImpl;
import br.com.uece.compilador.compiladorweb.ast.visitor.ThirdVisitor;
import br.com.uece.compilador.compiladorweb.parser.parser;
import br.com.uece.compilador.compiladorweb.parser.sym;
import br.com.uece.compilador.compiladorweb.scanner.scanner;
import br.com.uece.compilador.compiladorweb.tabelas.Classe;
import br.com.uece.compilador.compiladorweb.throwables.CompiladorException;
import java_cup.runtime.Symbol;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Create by Bruno Barbosa - 09/11/2018
 */
@RestController
@RequestMapping("/compilar")
public class CompilarService {

    @RequestMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> generate(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        Map<String, String> retorno = new LinkedHashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));

        /*Tokens*/
        scanner scannerToken = new scanner(reader);
        StringBuilder tokens = new StringBuilder();
        Symbol symbol = scannerToken.next_token();
        try {
            while (symbol.sym != sym.EOF) {
                tokens.append(scannerToken.symbolToString(symbol)).append(" ");
                symbol = scannerToken.next_token();
            }
            retorno.put("token", tokens.toString());
        } catch (CompiladorException e) {
            e.printStackTrace();
            retorno.put("errorToken", "Erro na geração do Token");
        }

        /*Scanner*/
        reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
        scanner scannerClasseFirstVisitor = new scanner(reader);
        Map mapParaEstruturaLinkedMap = new HashMap();
        Map mapClasses = new LinkedHashMap(mapParaEstruturaLinkedMap);
        StringBuilder verificacao = new StringBuilder("Contrução da Tabela de simbolos...\n");

        Program program = null;
        try {
            parser parserForFristVisitor = new parser(scannerClasseFirstVisitor);
            Symbol symbolForFristVisitor = parserForFristVisitor.parse();
            verificacao.append(symbolForFristVisitor); // nó inicial
            program = (Program) symbolForFristVisitor.value;
            program.acceptFirst(new FirstVisitorImpl(), mapClasses, 0, 0); // isso faz o log do código e constroi a estrutura de classes no linkedMap
            Set listaDeClasses = mapClasses.entrySet();
            for (Object listaDeClass : listaDeClasses) {
                Map.Entry entrada = (Map.Entry) listaDeClass;
                Classe classe = (Classe) entrada.getValue();
                classe.verificar(mapClasses);
                classe.verificarTipoExistente(mapClasses);
            }

            reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
            scanner scannerClasseSecondVisitor = new scanner(reader);
            parser parserForSecondVisitor = new parser(scannerClasseSecondVisitor);
            Symbol symbolForSecondVisitor = parserForSecondVisitor.parse();
            program = (Program) symbolForSecondVisitor.value;
            program.acceptSecond(new SecondVisitorImpl(), mapClasses, 0, null, null);
            //verificacao.append("Tabela de símbolos construída com sucesso!\n");
            //verificacao.append("Tabelas geradas:\n");

            listaDeClasses = mapClasses.entrySet();
            for (Object listaDeClass : listaDeClasses) {
                Map.Entry entrada = (Map.Entry) listaDeClass;
                Classe classe = (Classe) entrada.getValue();
                verificacao.append(classe.log());
            }
            retorno.put("verificacao", verificacao.toString());
        } catch (Exception e) {
            e.printStackTrace();
            retorno.put("errorVerificacao", "Erro ao gerar a Tabela de Símbolos");
        }

        /*Assembly*/
        try {
            StringBuilder assembly = new StringBuilder();
            ThirdVisitor thirdVisitor = new ThirdVisitor();
            program.acceptSecond(thirdVisitor, mapClasses, 0, null, null);
            assembly.append(thirdVisitor.getCode());

            retorno.put("assembly", assembly.toString());
        } catch (Exception e) {
            e.printStackTrace();
            retorno.put("errorAssembly", "Erro na geração do Assembly");
        }

        reader.close();
        return retorno;
    }
}
