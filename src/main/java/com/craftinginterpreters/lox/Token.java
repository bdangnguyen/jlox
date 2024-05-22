package com.craftinginterpreters.lox;

/**
 * POJO that represents a Token in the Lox language.
 */
record Token(TokenType tokenType, String lexeme, Object literal, int line) {

    /**
     * Prints out information related to the Token.
     */
    @Override
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
