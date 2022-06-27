
%{
#include "heading.h"
int yyerror(char *s);
int yylex(void);
%}

%define lr.type canonical-lr

%start typexpr

%token TICK
%token IDENT
%token STAR
%token ARROW
%token LPAREN
%token RPAREN


%%

typexpr: TICK IDENT
  | LPAREN typexpr RPAREN
  | typexpr ARROW typexpr
  | typexpr STAR typexpr
  | typeconstr
  ;


typeconstr: global-name
global-name: IDENT

%%

int yyerror(string s)
{
  exit(1);
}

int yyerror(char *s)
{
  return yyerror(string(s));
}
