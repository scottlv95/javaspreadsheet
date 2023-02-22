package spreadsheet;

import common.lexer.Token.Kind;

public class BinOp {


  private Kind kind;


  public BinOp(Kind kind) {
    this.kind = kind;
  }

  public Kind getKind() {
    return kind;
  }

  public int getPrecedence() throws InvalidSyntaxException {
    int precedence;
    switch (kind) {
      case PLUS, MINUS -> precedence = 1;
      case STAR, SLASH -> precedence = 2;
      case CARET -> precedence = 3;
      default -> throw new InvalidSyntaxException("Invalid Syntax");
    }
    return precedence;
  }

  public Associativity getAssociativity() throws InvalidSyntaxException {
    Associativity a;
    switch (kind) {
      case PLUS, MINUS, STAR, SLASH -> a = Associativity.LEFT;
      case CARET -> a = Associativity.RIGHT;
      default -> throw new InvalidSyntaxException("Invalid Syntax");
    }
    return a;
  }

  @Override
  public String toString() {
    String out;
    switch (this.kind) {
      case MINUS -> out = "-";
      case PLUS -> out = "+";
      case STAR -> out = "*";
      case SLASH -> out = "/";
      case CARET -> out = "^";
      default -> out = this.toString();
    }
    return out;
  }

  enum Associativity {
    LEFT, RIGHT
  }


}
