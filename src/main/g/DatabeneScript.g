/*
 * Copyright (C) 2011-2014 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

grammar DatabeneScript;

options {
    output=AST;
    backtrack=true;
    memoize=true;
}

@header {
	package com.rapiddweller.script;
}

@lexer::header{ 
	package com.rapiddweller.script;
}

@lexer::members {
	@Override
	public Token nextToken() {
		while (true) {
			state.token = null;
			state.channel = Token.DEFAULT_CHANNEL;
			state.tokenStartCharIndex = input.index();
			state.tokenStartCharPositionInLine = input.getCharPositionInLine();
			state.tokenStartLine = input.getLine();
			state.text = null;
			if ( input.LA(1)==CharStream.EOF ) {
				return Token.EOF_TOKEN;
			}
			try {
				mTokens();
				if ( state.token==null ) {
					emit();
				}
				else if ( state.token==Token.SKIP_TOKEN ) {
					continue;
				}
				return state.token;
			}
			catch (RecognitionException re) {
				reportError(re);
				throw new RuntimeException(getClass().getSimpleName() + " error", re); // or throw Error
			}
		}
	}

}

@members {
protected void mismatch(IntStream input, int ttype, BitSet follow)
  throws RecognitionException
{
  throw new MismatchedTokenException(ttype, input);
}

public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow)
  throws RecognitionException
{
  throw e;
}
}

@rulecatch {
catch (RecognitionException e) {
  throw e;
}
}


/********************************************************************************************
                          Parser section
*********************************************************************************************/

weightedLiteralList
    :   weightedLiteral (','! weightedLiteral)*;
	
weightedLiteral
    :   literal ('^'^ expression)?;

transitionList
    :   transition (','! transition)*;

transition
    :   literal '->'^ literal ('^'! expression)?;

beanSpecList
    :   beanSpec (','! beanSpec)*;

beanSpec
    :   expression -> ^(BEANSPEC expression);

expression 
    :   conditionalExpression
    |   assignment
    ;

assignment
    :   qualifiedName '='^ expression;

conditionalExpression 
    :   conditionalOrExpression ('?'^ expression ':'! conditionalExpression)?
    ;
    
conditionalOrExpression 
    :   conditionalAndExpression ('||'^ conditionalAndExpression )*
    ;

conditionalAndExpression 
    :   inclusiveOrExpression ('&&'^ inclusiveOrExpression)*
    ;

inclusiveOrExpression 
    :   exclusiveOrExpression ('|'^ exclusiveOrExpression)*
    ;

exclusiveOrExpression
    :   andExpression ('^'^ andExpression)*
    ;

andExpression
    :   equalityExpression ('&'^ equalityExpression)*
    ;

equalityExpression 
    :   relationalExpression (('==' | '!=')^ relationalExpression)*
    ;

relationalExpression 
    :   shiftExpression (('<=' | '>=' | '<' | '>')^ shiftExpression)*
    ;

shiftExpression 
    :   additiveExpression (('<<' | '>>>' | '>>')^ additiveExpression)*
    ;

additiveExpression 
    :   multiplicativeExpression (('+' | '-')^ multiplicativeExpression)*
    ;

multiplicativeExpression 
    :   unaryExpression (('*' | '/' | '%')^ unaryExpression)*
    ;

// NOTE: for '+' and '-', if the next token is int or long interal, then it's not a unary expression.
//       it's a literal with signed value. INTLITERAL AND LONG LITERAL are added here for this.
//

unaryExpression 
    :   '-' castExpression -> ^(NEGATION castExpression)
    |   '~'^ castExpression
    |   '!'^ castExpression
    |   castExpression
    ;

castExpression 
    :   '(' type ')' postfixExpression -> ^(CAST type postfixExpression)
    |   postfixExpression
    ;

type
    :   qualifiedName -> ^(TYPE qualifiedName);

postfixExpression
    :   (primary -> primary)
        (
            '[' expression ']' -> ^(INDEX $postfixExpression expression)
        |   '.' IDENTIFIER arguments-> ^(SUBINVOCATION $postfixExpression IDENTIFIER arguments)
        |   '.' IDENTIFIER -> ^(FIELD $postfixExpression IDENTIFIER)
        )*
    ;

primary 
    :   '('! expression ')'!
    |   literal
    |	creator
    |   qualifiedName arguments -> ^(INVOCATION qualifiedName arguments)
    |   qualifiedName
    ;

creator
    :   'new' qualifiedName arguments -> ^(CONSTRUCTOR qualifiedName arguments)
    |   'new' qualifiedName '{' assignment (',' assignment)* '}' -> ^(BEAN qualifiedName assignment*)
    ;

arguments
    :   '(' (expression (',' expression)*)? ')' -> ^(ARGUMENTS expression*);
    
qualifiedName
	:   IDENTIFIER ('.' IDENTIFIER)* -> ^(QUALIFIEDNAME IDENTIFIER*)
	;

literal 
    :   INTLITERAL
    |   DECIMALLITERAL
    |   STRINGLITERAL
    |   BOOLEANLITERAL
    |   NULL
    ;

/********************************************************************************************
                  Lexer section
*********************************************************************************************/

fragment TYPE:;
fragment NEGATION:;
fragment INDEX:;
fragment FIELD:;
fragment ARGUMENTS:;
fragment CAST:;
fragment CONSTRUCTOR:;
fragment INVOCATION:;
fragment SUBINVOCATION:;
fragment QUALIFIEDNAME:;
fragment BEAN:;
fragment BEANSPEC:;

BOOLEANLITERAL
    :   'true'
    |   'false'
    ;
    
INTLITERAL
    :   '0' 
    |   '1'..'9' ('0'..'9')*    
    |   '0' ('0'..'7')+         
    |   HexPrefix HexDigit+        
    ;

fragment
HexPrefix
    :   '0x';
        
fragment
HexDigit
    :   ('0'..'9'|'a'..'f'|'A'..'F')
    ;

DECIMALLITERAL
    :   ('0' .. '9')+ '.' ('0' .. '9')* Exponent?  
    |   ('0' .. '9')+ Exponent  
    ;
        
fragment 
Exponent    
    :   ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ 
    ;

STRINGLITERAL
    :   '\'' ( EscapeSequence | ~( '\'' | '\\' | '\r' | '\n' ))* '\''
    ; 

fragment
EscapeSequence 
    :   '\\' (
                 'b' 
             |   't' 
             |   'n' 
             |   'f' 
             |   'r' 
             |   '\"' 
             |   '\'' 
             |   '\\' 
             |   ('0'..'3') ('0'..'7') ('0'..'7')
             |   ('0'..'7') ('0'..'7') 
             |   ('0'..'7')
             )          
;     

WS  :   ( ' ' | '\r' | '\t' | '\u000C' | '\n' ) 
            {
                skip();
            }          
    ;
    
COMMENT:   '/*' (options {greedy=false;} : . )* '*/';
    
LINE_COMMENT
    :   '//' ~('\n'|'\r')*  ('\r\n' | '\r' | '\n') 
            {
                skip();
            }
    |   '//' ~('\n'|'\r')*     // a line comment could appear at the end of the file without CR/LF
            {
                skip();
            }
    ;

NULL:     'null';
LPAREN:   '(';
RPAREN:   ')';
LBRACE:   '{';
RBRACE:   '}';
LBRACKET: '[';
RBRACKET: ']';
SEMI:   ';';
COMMA:  ',';
DOT :   '.';
EQ  :   '=';
BANG:   '!';
TILDE:  '~';
QUES:   '?';
COLON:  ':';
EQEQ:   '==';
AMPAMP: '&&';
BARBAR: '||';
PLUS:   '+';
SUB :   '-';
STAR:   '*';
SLASH:   '/';
AMP :   '&';
BAR :   '|';
CARET:   '^';
PERCENT: '%';
MONKEYS_AT: '@';
BANGEQ: '!=';
GT  :   '>';
SHIFT_RIGHT:   '>>';
SHIFT_RIGHT2:   '>>>';
SHIFT_LEFT:   '<<';
GE  :   '>=';
LT  :   '<';
LE  :   '<=';
ARROW:   '->';
              
IDENTIFIER
    :   IdentifierStart IdentifierPart*
    ;

fragment
IdentifierStart
    :   'A'..'Z'
    |	'a'..'z'
    |   '_'
    ;                
                       
fragment 
IdentifierPart
    :   'A'..'Z'
    |	'a'..'z'
    |   '_'
    |	'0'..'9'
    ;
