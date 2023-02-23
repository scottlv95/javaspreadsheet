package spreadsheet;

import common.lexer.Token.Kind;

public class BinOp {


  private final Kind kind;


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
    return this.kind.getSymbol();
  }

  enum Associativity {
    LEFT, RIGHT
  }


}
