package com.craftinginterpreters.lox;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ScannerTest {
    private static final String EMPTY_SOURCE = "";
    private static final String SOURCE_WITH_INVALID_TOKEN = "@";
    private static final String SOURCE_WITH_COMMENT = "// Comment";
    private static final String SOURCE_WITH_BLOCK_COMMENT = "/* Block\nComment \n*/";
    private static final String SOURCE_WITH_BLOCK_COMMENT_AND_IDENTIFIER = "/* Block\nComment\n*/\nsomeObject";
    private static final String SOURCE_WITH_STRING = "\"testString\"";
    private static final String SOURCE_WITH_STRING_LITERAL = "testString";
    private static final String SOURCE_WITH_UNTERMINATED_STRING = "\"testString";
    private static final String SOURCE_WITH_NUMBER = "1";
    private static final String SOURCE_WITH_FRACTIONAL_NUMBER = "1.5";
    private static final String SOURCE_WITH_KEYWORD = "and";
    private static final String SOURCE_WITH_IDENTIFIER = "someObject";
    private static final double SOURCE_WITH_NUMBER_LITERAL = 1.0;
    private static final double SOURCE_WITH__FRACTIONAL_NUMBER_LITERAL = 1.5;

    @Test
    public void testScanTokens_withEmptySource_thenReturnEOF() {
        final Scanner scanner = new Scanner(EMPTY_SOURCE);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 1);
        final List<Token> expectedTokens = Collections.singletonList(endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withInvalidToken_thenReturnEOF() {
        final Scanner scanner = new Scanner(SOURCE_WITH_INVALID_TOKEN);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 1);
        final List<Token> expectedTokens = Collections.singletonList(endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withComment_thenReturnEOF() {
        final Scanner scanner = new Scanner(SOURCE_WITH_COMMENT);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 1);
        final List<Token> expectedTokens = Collections.singletonList(endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withBlockComment_thenReturnEOF() {
        final Scanner scanner = new Scanner(SOURCE_WITH_BLOCK_COMMENT);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 3);
        final List<Token> expectedTokens = Collections.singletonList(endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withBlockCommentAndIdentifier_thenReturnIdentifierToken() {
        final Scanner scanner = new Scanner(SOURCE_WITH_BLOCK_COMMENT_AND_IDENTIFIER);
        final Token identifierToken = new Token(TokenType.IDENTIFIER, SOURCE_WITH_IDENTIFIER, null, 4);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 4);
        final List<Token> expectedTokens = List.of(identifierToken, endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();
        
        System.out.println("TEST" + tokens);
        System.out.println("TEST" + expectedTokens);
        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withString_thenReturnStringToken() {
        final Scanner scanner = new Scanner(SOURCE_WITH_STRING);
        final Token stringToken = new Token(TokenType.STRING, SOURCE_WITH_STRING, SOURCE_WITH_STRING_LITERAL, 1);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 1);
        final List<Token> expectedTokens = List.of(stringToken, endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withUnterminatedString_thenReturnEndOfFile() {
        final Scanner scanner = new Scanner(SOURCE_WITH_UNTERMINATED_STRING);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 1);
        final List<Token> expectedTokens = Collections.singletonList(endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withNumber_thenReturnNumberToken() {
        final Scanner scanner = new Scanner(SOURCE_WITH_NUMBER);
        final Token numberToken = new Token(TokenType.NUMBER, SOURCE_WITH_NUMBER, SOURCE_WITH_NUMBER_LITERAL, 1);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 1);
        final List<Token> expectedTokens = List.of(numberToken, endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withFractionalNumber_thenReturnNumberToken() {
        final Scanner scanner = new Scanner(SOURCE_WITH_FRACTIONAL_NUMBER);
        final Token numberToken =
            new Token(TokenType.NUMBER, SOURCE_WITH_FRACTIONAL_NUMBER, SOURCE_WITH__FRACTIONAL_NUMBER_LITERAL, 1);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 1);
        final List<Token> expectedTokens = List.of(numberToken, endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withKeyword_thenReturnKeywordToken() {
        final Scanner scanner = new Scanner(SOURCE_WITH_KEYWORD);
        final Token keywordToken = new Token(TokenType.AND, SOURCE_WITH_KEYWORD, null, 1);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 1);
        final List<Token> expectedTokens = List.of(keywordToken, endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }

    @Test
    public void testScanTokens_withIdentifier_thenReturnIdentifierToken() {
        final Scanner scanner = new Scanner(SOURCE_WITH_IDENTIFIER);
        final Token identifierToken = new Token(TokenType.IDENTIFIER, SOURCE_WITH_IDENTIFIER, null, 1);
        final Token endOfFileToken = new Token(TokenType.EOF, "", null, 1);
        final List<Token> expectedTokens = List.of(identifierToken, endOfFileToken);

        final List<Token> tokens = scanner.scanTokens();

        assertThat(tokens).isEqualTo(expectedTokens);
    }
}
