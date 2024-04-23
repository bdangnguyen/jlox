package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    private static boolean hadError = false;

    public static void main(final String args[]) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    /**
     * From the command line, given a path to a file it reads and executes it.
     * @param path
     */
    private static void runFile(final String path) throws IOException {
        final byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        
        if (hadError) {
            System.exit(65);
        }
    }

    /**
     * From the command line, run lox interactively by executing code one line
     * at a time.
     */
    private static void runPrompt() throws IOException {
        final InputStreamReader input = new InputStreamReader(System.in);
        final BufferedReader reader = new BufferedReader(input);

        while (true) {
            System.out.println("> ");
            final String line = reader.readLine();
            
            if (line == null) {
                break;
            }

            run(line);
            hadError = false;
        }
    }

    private static void run(final String source) {
        final Scanner scanner = new Scanner(source);
        final List<Token> tokens = scanner.scanTokens();

        // TODO: For now, print tokens
        for (final Token token : tokens) {
            System.out.println(token);
        }
    }

    static void error(final int line, final String message) {
        report(line, "", message);
    }

    /**
     * Generic print out that some error happened at a specific line
     * @param line
     * @param where
     * @param message
     */
    private static void report(final int line, final String where, final String message) {
        System.out.println("[line " + line +"] Error" + where + ": " + message);
        hadError = true;
    }
}
