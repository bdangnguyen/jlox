package com.craftinginterpreters.lox;

import com.craftinginterpreters.lox.Expr.Binary;
import com.craftinginterpreters.lox.Expr.Grouping;
import com.craftinginterpreters.lox.Expr.Literal;
import com.craftinginterpreters.lox.Expr.Unary;

public class AstPrinter implements Expr.Visitor<String> {

    // TODO: Placeholder main. Replace with easier way to evaluate expressions.
    public static void main(String[] args) {
        Expr expression = new Expr.Binary(
            new Expr.Unary(
                new Token(TokenType.MINUS, "-", null, 1),
                new Expr.Literal(123)),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(
                new Expr.Literal(45.67)));
    
        System.out.println(new AstPrinter().print(expression));
      }

    String print(final Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpr(final Binary expr) {
        return parenthesize(expr.operator.lexeme(), expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(final Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(final Literal expr) {
        if (expr == null) {
            return "nil";
        }

        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(final Unary expr) {
        return parenthesize(expr.operator.lexeme(), expr.right);
    }

    private String parenthesize(final String name, final Expr... exprs) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("(").append(name);
        for (final Expr expr : exprs) {
            stringBuilder.append(" ");
            stringBuilder.append(expr.accept(this));
        }
        stringBuilder.append(")");

        return stringBuilder.toString();
    }
}
