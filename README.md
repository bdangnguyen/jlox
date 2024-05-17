# README
This is a Java 21 implementation of the Lox language in Java as described in Robert Nystrom's [Crafting Interpreters](https://craftinginterpreters.com/). The purpose of this was to learn more about interpreters and programming languages in general.

## Usage
1. Install depedendencies and compile directly using Maven using ```mvn clean install```.
2. Run ```java -cp .\target\lox-1.0.jar com.craftinginterpreters.lox.Lox``` to use the REPL shell.
3. Run ```java -cp .\target\lox-1.0.jar com.craftinginterpreters.tool.GenerateAst .\src\main\java\com\craftinginterpreters\lox``` to generate the AST java class files. 

Alternatively run the pom.xml using a IDE of choice.

## Testing
Tests can be ran using ``` mvn clean test```

## Dependencies
* [Maven](https://maven.apache.org/) is the build tool.
* [JUnit5](https://junit.org/junit5/) is the testing framework.
* [AssertJ](https://assertj.github.io/doc/) to provide more powerful assertions.

## Grammar
This current implementation of jlox has the following grammar.
```
expression     → literal
               | unary
               | binary
               | grouping ;

literal        → NUMBER | STRING | "true" | "false" | "nil" ;
grouping       → "(" expression ")" ;
unary          → ( "-" | "!" ) expression ;
binary         → expression operator expression ;
operator       → "==" | "!=" | "<" | "<=" | ">" | ">="
               | "+"  | "-"  | "*" | "/" ;
```