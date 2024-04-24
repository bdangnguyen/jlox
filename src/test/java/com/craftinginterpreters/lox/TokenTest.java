package com.craftinginterpreters.lox;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TokenTest {
    private static final String expectedString = "BANG ! null 1";

    @Test
    public void testToString() {
        final TokenType tokenType = TokenType.BANG;
        final String lexeme = "!";
        final int line = 1;

        final Token token = new Token(tokenType, lexeme, null, line);

        assertThat(token.toString()).isEqualTo(expectedString);
    }
}
