package com.craftinginterpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class to generate the Expr class and its POJOs for representing the AST. Not tested
 * explicitly due to the POJOs themselves being tested.
 */
public class GenerateAst {

    public static void main(final String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }

        String outputDir = args[0];
        defineAst(outputDir, "Expr", Arrays.asList(
            "Binary   : final Expr left, final Token operator, final Expr right",
            "Grouping : final Expr expression",
            "Literal  : final Object value",
            "Unary    : final Token operator, final Expr right"
        ));
    }

    private static void defineAst(final String outputDir, final String baseName,
     final List<String> types) throws IOException {
        final String path = outputDir + "/" + baseName + ".java";
        final PrintWriter writer = new PrintWriter(path, "UTF-8");
    
        writer.println("package com.craftinginterpreters.lox;");
        writer.println();
        writer.println("abstract class " + baseName + " {");

        // The base accept() method.
        writer.println();
        writer.println("    abstract <R> R accept(Visitor<R> visitor);");

        // Visitor interface
        writer.println();
        defineVisitor(writer, baseName, types);
    
        // AST types.
        for (final String type : types) {
            final String className = type.split(":")[0].trim();
            final String fields = type.split(":")[1].trim(); 
            defineType(writer, baseName, className, fields);
        }

        writer.println("}");
        writer.close();
    }

    private static void defineVisitor(final PrintWriter writer, final String baseName,
     final List<String> types) {
        writer.println("    interface Visitor<R> {");

        for (final String type : types) {
            final String typeName = type.split(":")[0].trim();
            writer.println("        R visit" + typeName + baseName + "(" +
                typeName + " " + baseName.toLowerCase() + ");");
        }

        writer.println("    }");
    }

    private static void defineType(final PrintWriter writer, final String baseName,
     final String className, final String fieldList) {
        writer.println();
        writer.println("    static class " + className + " extends " + baseName + " {");

        // Define fields in anonymous class.
        final String[] fields = fieldList.split(", ");
        for (final String field : fields) {
            writer.println("        " + field + ";");
        }

        // Define constructor.
        writer.println();
        writer.println("        " + className + "(" + fieldList + ") {");

        // Instantiate fields in constructor.
        for (final String field : fields) {
            final String name = field.split(" ")[2];
            writer.println("            this." + name + " = " + name + ";");
        }

        // Close constructor
        writer.println("        }");

        // Visitor pattern
        writer.println();
        writer.println("        @Override");
        writer.println("        <R> R accept(final Visitor<R> visitor) {");
        writer.println("            return visitor.visit" + className + baseName + "(this);");
        writer.println("        }");

        // Close abstract class
        writer.println("    }");
    }
}
