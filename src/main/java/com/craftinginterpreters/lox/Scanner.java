package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.craftinginterpreters.lox.TokenType.AND;
import static com.craftinginterpreters.lox.TokenType.BANG;
import static com.craftinginterpreters.lox.TokenType.BANG_EQUAL;
import static com.craftinginterpreters.lox.TokenType.CLASS;
import static com.craftinginterpreters.lox.TokenType.COMMA;
import static com.craftinginterpreters.lox.TokenType.DOT;
import static com.craftinginterpreters.lox.TokenType.ELSE;
import static com.craftinginterpreters.lox.TokenType.EOF;
import static com.craftinginterpreters.lox.TokenType.EQUAL;
import static com.craftinginterpreters.lox.TokenType.EQUAL_EQUAL;
import static com.craftinginterpreters.lox.TokenType.FALSE;
import static com.craftinginterpreters.lox.TokenType.FOR;
import static com.craftinginterpreters.lox.TokenType.FUN;
import static com.craftinginterpreters.lox.TokenType.GREATER;
import static com.craftinginterpreters.lox.TokenType.GREATER_EQUAL;
import static com.craftinginterpreters.lox.TokenType.IDENTIFIER;
import static com.craftinginterpreters.lox.TokenType.IF;
import static com.craftinginterpreters.lox.TokenType.LEFT_BRACE;
import static com.craftinginterpreters.lox.TokenType.LEFT_PAREN;
import static com.craftinginterpreters.lox.TokenType.LESS;
import static com.craftinginterpreters.lox.TokenType.LESS_EQUAL;
import static com.craftinginterpreters.lox.TokenType.MINUS;
import static com.craftinginterpreters.lox.TokenType.NIL;
import static com.craftinginterpreters.lox.TokenType.NUMBER;
import static com.craftinginterpreters.lox.TokenType.OR;
import static com.craftinginterpreters.lox.TokenType.PLUS;
import static com.craftinginterpreters.lox.TokenType.PRINT;
import static com.craftinginterpreters.lox.TokenType.RETURN;
import static com.craftinginterpreters.lox.TokenType.RIGHT_BRACE;
import static com.craftinginterpreters.lox.TokenType.RIGHT_PAREN;
import static com.craftinginterpreters.lox.TokenType.SEMICOLON;
import static com.craftinginterpreters.lox.TokenType.SLASH;
import static com.craftinginterpreters.lox.TokenType.STAR;
import static com.craftinginterpreters.lox.TokenType.STRING;
import static com.craftinginterpreters.lox.TokenType.SUPER;
import static com.craftinginterpreters.lox.TokenType.THIS;
import static com.craftinginterpreters.lox.TokenType.TRUE;
import static com.craftinginterpreters.lox.TokenType.VAR;
import static com.craftinginterpreters.lox.TokenType.WHILE;
import static java.util.Map.entry;

class Scanner {
    private static final Map<String, TokenType> keywords = Map.ofEntries(
        entry("and",    AND),
        entry("class",  CLASS),
        entry("else",   ELSE),
        entry("false",  FALSE),
        entry("for",    FOR),
        entry("fun",    FUN),
        entry("if",     IF),
        entry("nil",    NIL),
        entry("or",     OR),
        entry("print",  PRINT),
        entry("return", RETURN),
        entry("super",  SUPER),
        entry("this",   THIS),
        entry("true",   TRUE),
        entry("var",    VAR),
        entry("while",  WHILE)
    );

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
                break;
            }
            case ' ', '\r', '\t' -> {
                // Ignore whitespace
                break;
            }
            case '\n' -> {
                line++;
                break;
            }
            case '"' -> {
                string();
                break;
            }
            default -> {
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    identifier();
                } else {
                    Lox.error(line, "Unexpected character");
                    break;
                }
            }
        }
    }

    // Consume characters until hit the end of the string.
    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n' ) {
                line++;
            }
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string");
            return;
        }

        // String is closed with '"'.
        advance();

        // Trim the quotes.
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);;
    }

    // Scan a number literal. If a decimal point is detected keep scanning
    // until the number ends.
    private void number() {
        while (isDigit(peek())) {
            advance();
        }

        // Consume the decimal point and any additional numbers.
        if (peek() == '.' && isDigit(peekNext())) {
            advance();

            while (isDigit(peek())) {
                advance();
            }
        }

        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) {
            advance();
        }

        final String text = source.substring(start, current);
        final TokenType type = findIdentifierType(text);
        addToken(type);
    }

    private TokenType findIdentifierType(final String text) {
        final TokenType type = keywords.get(text);
        if (type == null) {
            return IDENTIFIER;
        }

        return type;
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

    private char peekNext() {
        if (current + 1 >= source.length()) {
            return '\0';
        }

        return source.charAt(current + 1);
    }

    private boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(final char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
               c == '_';
    }

    private boolean isAlphaNumeric(final char c) {
        return isAlpha(c) || isDigit(c);
    }
}
