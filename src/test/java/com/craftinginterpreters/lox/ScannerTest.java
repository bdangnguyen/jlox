package com.craftinginterpreters.lox;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ScannerTest {

    @Test
    public void testScanTokens_WithEmptySource() {
        final Scanner scanner = new Scanner("");
        final List<Token> expectedTokens = Collections.singletonList(new Token(TokenType.EOF, "", null, 0));

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    // TODO
    public void testScanTokens_WithInvalidToken() {
    }

    @Test
    // TODO
    public void testScanTokens_WithNonEmptySource() {
    }
    
}
