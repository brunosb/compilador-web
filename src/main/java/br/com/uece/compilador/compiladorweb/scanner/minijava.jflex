package Scanner;

import java_cup.runtime.*;
import parser.sym;
import Throwables.*;

// importações que deveram existir no scanner /\


%% //indica o inicio das configurações que seram passa para o jflex para construir a classe analizadora. (Configurações globais)

%public //Faz com que a classe criada tenha uma função "main"
%final //Faz com que a classe criada seja final
%class scanner //Indica o nome da classe
// Exceção declarada
%yylexthrow CompiladorException , LexicalCompiladorException
%unicode  //Define o conjunto de caracteres em que o scanner funcionará
%cup //Muda para o modo de compatibilidade CUP para interface com um analisador gerado por CUP.
%line // Troca a contagem de linha (o número da linha atual pode ser acessado através da variável yyline)
%column //Alterna a contagem de colunas (a coluna atual é acessada via yycolumn)

//O código entre% {e%} é copiado textualmente na fonte da classe lexer
//gerada. Aqui você pode declarar variáveis e funções dos membros que são
//usadas dentro das ações do scanner.

%{
  // cria token
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }


    // Método que imprime as Tokens

  public String symbolToString(Symbol s) {
    switch (s.sym) {
      //+
      case sym.PLUS: return "PLUS";
      //-
      case sym.MINUS: return "MINUS";
      //*
      case sym.TIMES: return "TIMES";

      case sym.BECOMES: return "BECOMES";

      //;
      case sym.SEMICOLON: return "SEMICOLON";
      //&&
      case sym.AND: return "AND";

      //return
      case sym.RETURN: return "RETURN";
      //id
      case sym.IDENTIFIER: return "ID(" + (String)s.value + ")";
      // string que representa um inteiro
      case sym.INTEGER_LITERAL: return "INTEGER_LITERAL(" + s.value + ")";
      //boolean
      case sym.BOOLEAN_TYPE: return "BOOLEAN";
      //int
      case sym.INTEGER_TYPE: return "INTEGER";
      //if
      case sym.IF: return "IF";
      // !
      case sym.NOT: return "NOT";
      //. do ponto flutuante
      case sym.DOT: return "DOT";
      //,
      case sym.COMMA: return "COMMA";
      //string
      case sym.STRING: return "STRING";
      //sout
      case sym.SYSTEM_OUT_PRINTLN: return "SYSTEM_OUT_PRINTLN";
      //else se nao
      case sym.ELSE: return "ELSE";
      //while iterador
      case sym.WHILE: return "WHILE";
      //class cabeçalho de uma classe
      case sym.CLASS: return "CLASS";
      // classes extends de outras classes
      case sym.EXTENDS: return "EXTENDS";
      //public  assinatura classe, metodo e variavel
      case sym.PUBLIC: return "PUBLIC";
      //static assinatura classe, metodo e variavel
      case sym.STATIC: return "STATIC";
      //return void
      case sym.VOID: return "VOID";
      //metodo main
      case sym.MAIN: return "MAIN";
      //true
      case sym.TRUE: return "TRUE";
      //false
      case sym.FALSE: return "FALSE";
      //length de um vetor/arraylist
      case sym.LENGTH: return "LENGTH";
      // contexto daquele objeto
      case sym.THIS: return "THIS";
      //new de um objeto
      case sym.NEW: return "NEW";
      //(
      case sym.LPAREN: return "LPAREN";
      //)
      case sym.RPAREN: return "RPAREN";
      //[
      case sym.LBRACKET: return "LBRACKET";
      //]
      case sym.RBRACKET: return "RBRACKET";
      //{
      case sym.LBRACE: return "LBRACE";
      //}
      case sym.RBRACE: return "RBRACE";
       //<
      case sym.LT: return "LT";
      //final
      case sym.EOF: return "<EOF>";
      //erro
      case sym.error: return "<ERROR>";
      //se nao entrar em nenhum dessas casos, é pq nao é uma token definida
      default: return "<UNEXPECTED TOKEN " + s.toString() + ">";
    }
  }
%}


/* Definiçoes para expressoes regulares(R.E) e acoes
 que são executados quando o scanner corresponde à expressão regular associada. */
letter = [a-zA-Z]
digit = [0-9]
eol = [\r\n]
not_eol = [^\r\n]
white = {eol}|[ \t]

/* R.E para comentário */
start_comment = "/*"
comment_contents = ([^*]|\*[^/])
end_comment = "*/"

%% // alguams definições das regras da linguagem

/* As definições das tokens */

/* comentarios */
"//"{not_eol}*{eol} { /* do nothing */ }
{start_comment}{comment_contents}*{end_comment} { /* do nothing */ }

/* reserved words */
/* (put here so that reserved words take precedence over identifiers) */

/*  Tokens de tipos */
"int" { return symbol(sym.INTEGER_TYPE); }
"boolean" { return symbol(sym.BOOLEAN_TYPE); }
"true" { return symbol(sym.TRUE); }
"false" { return symbol(sym.FALSE); }
"String" { return symbol(sym.STRING); }


/* Definicao de Tokens de tipos de declaraçoes */
"public" { return symbol(sym.PUBLIC); }
"static" { return symbol(sym.STATIC); }
"void" { return symbol(sym.VOID); }
"main" { return symbol(sym.MAIN); }
"class" { return symbol(sym.CLASS); }
"extends" { return symbol(sym.EXTENDS); }

"new" { return symbol(sym.NEW); }
"this" { return symbol(sym.THIS); }
"length" { return symbol(sym.LENGTH); }

/* token para controlar fluxo */
"if" { return symbol(sym.IF); }
"else" { return symbol(sym.ELSE); }
"while" { return symbol(sym.WHILE); }
"return" { return symbol(sym.RETURN); }

/*  definicao Token do tipo  sout do java */
"System"{white}*"."{white}*"out"{white}*"."{white}*"println" {
    return symbol(sym.SYSTEM_OUT_PRINTLN);
}

/* definicao de Tokens que sao literais */
{digit}+ { return symbol(sym.INTEGER_LITERAL, new Integer(yytext())); }

/*  definicao de tokens operadores */
"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"&&" { return symbol(sym.AND); }
"!" { return symbol(sym.NOT); }
"<" { return symbol(sym.LT); }
"." { return symbol(sym.DOT); }
"," { return symbol(sym.COMMA); }
"=" { return symbol(sym.BECOMES); }

/* definicao de tokens delimitadores */
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
"[" { return symbol(sym.LBRACKET); }
"]" { return symbol(sym.RBRACKET); }
"{" { return symbol(sym.LBRACE); }
"}" { return symbol(sym.RBRACE); }
";" { return symbol(sym.SEMICOLON); }

/* R.E para identificadores */
{letter} ({letter}|{digit}|_)* { return symbol(sym.IDENTIFIER, yytext()); }


/* whitespace */
{white}+ { /* ignore whitespace */ }

/* lexical errors (put last so other matches take precedence) */
. {
    System.err.println(
        "Warning: ignoring invalid token at line " +
        (yyline + 1) +
        ", column " +
        (yycolumn + 1) +
        "."
    );

    // Do not return any tokens
}