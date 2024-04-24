package com.craftinginterpreters.lox;

/**
 * POJO that represents a Token in the Lox language.
 */
class Token {
    private final TokenType tokenType;
    private final String lexeme;
    private final Object literal;
    private final int line;

    Token(final TokenType tokenType,final String lexeme, final Object literal, final int line) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    /**
     * Prints out information related to the Token.
     */
    public String toString() {
        return tokenType + " " + lexeme + " " + literal + " " + line;
    }

    @Override
    public boolean equals(final Object object) {
        return this.toString().equals(object.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
