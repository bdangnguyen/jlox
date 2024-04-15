package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.List;

import static com.craftinginterpreters.lox.TokenType.BANG;
import static com.craftinginterpreters.lox.TokenType.BANG_EQUAL;
import static com.craftinginterpreters.lox.TokenType.COMMA;
import static com.craftinginterpreters.lox.TokenType.DOT;
import static com.craftinginterpreters.lox.TokenType.EOF;
import static com.craftinginterpreters.lox.TokenType.EQUAL;
import static com.craftinginterpreters.lox.TokenType.EQUAL_EQUAL;
import static com.craftinginterpreters.lox.TokenType.GREATER;
import static com.craftinginterpreters.lox.TokenType.GREATER_EQUAL;
import static com.craftinginterpreters.lox.TokenType.LEFT_BRACE;
import static com.craftinginterpreters.lox.TokenType.LEFT_PAREN;
import static com.craftinginterpreters.lox.TokenType.LESS;
import static com.craftinginterpreters.lox.TokenType.LESS_EQUAL;
import static com.craftinginterpreters.lox.TokenType.MINUS;
import static com.craftinginterpreters.lox.TokenType.PLUS;
import static com.craftinginterpreters.lox.TokenType.RIGHT_BRACE;
import static com.craftinginterpreters.lox.TokenType.RIGHT_PAREN;
import static com.craftinginterpreters.lox.TokenType.SEMICOLON;
import static com.craftinginterpreters.lox.TokenType.SLASH;
import static com.craftinginterpreters.lox.TokenType.STAR;

class Scanner {
    private final String source;
    private final List<Token> tokens;
    private int start = 0;
    private int current = 0;
    private int line = 1;

    Scanner(final String source) {
        this.source = source;
        this.tokens = new ArrayList<Token>();
    }

    /**
     * Scan source file and returns a list of tokens. Advances character by character until a token is matched.
     * @return
     */
    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        final char c = advance();
        switch (c) {
            case '(' -> addToken(LEFT_PAREN);
            case ')' -> addToken(RIGHT_PAREN);
            case '{' -> addToken(LEFT_BRACE);
            case '}' -> addToken(RIGHT_BRACE);
            case ',' -> addToken(COMMA);
            case '.' -> addToken(DOT);
            case '-' -> addToken(MINUS);
            case '+' -> addToken(PLUS);
            case ';' -> addToken(SEMICOLON);
            case '*' -> addToken(STAR);
            case '!' -> addToken(match('=') ? BANG_EQUAL : BANG);
            case '=' -> addToken(match('=') ? EQUAL_EQUAL : EQUAL);
            case '<' -> addToken(match('=') ? LESS_EQUAL : LESS);
            case '>' -> addToken(match('=') ? GREATER_EQUAL : GREATER);
            case '/' -> {
                if (match('/')) {
                    while(peek() != '\n' && !isAtEnd()) {
                        advance();
                    }
                } else {
                    addToken(SLASH);
                }
            }
            default -> Lox.error(line, "unexpected character");
        }
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(final TokenType tokenType) {
        addToken(tokenType, null);
    }

    private void addToken(final TokenType tokenType, final Object literal) {
        final String text = source.substring(start, current);
        tokens.add(new Token(tokenType, text, literal, line));
    }

    private boolean match(final char expected) {
        if (isAtEnd()) {
            return false;
        }

        if (source.charAt(current) != expected) {
            return false;
        }

        current++;
        return true;
    } 

    private char peek() {
        if (isAtEnd()) {
            return '\0';
        }

        return source.charAt(current);
    }


}
