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

  public int getPrecedence() {
    return this.kind.getPrecedence();
  }

  public Associativity getAssociativity() throws InvalidSyntaxException {
    Associativity a;
    switch (kind) {
      case PLUS, MINUS, STAR, SLASH, RPARENTHESIS -> a = Associativity.LEFT;
      case CARET, LPARENTHESIS -> a = Associativity.RIGHT;
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
