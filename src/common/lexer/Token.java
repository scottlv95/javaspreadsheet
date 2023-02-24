package common.lexer;

import static common.lexer.Token.Kind.CELL_LOCATION;
import static common.lexer.Token.Kind.NUMBER;

import common.api.CellLocation;
import java.util.Objects;

/**
 * Representation of a token from the input string.
 *
 * <p>If `kind` if NUMBER or CELL_LOCATION, then the represented value may be found respectively in
 * `numberValue` and `cellLocationValue`.
 */
public class Token {
  public final Kind kind;
  public final CellLocation cellLocationValue;
  public final double numberValue;

  Token(Kind kind) {
    this(kind, null, 0);
    assert (kind != NUMBER && kind != CELL_LOCATION);
  }

  Token(double value) {
    this(NUMBER, null, value);
  }

  Token(CellLocation cellLocation) {
    this(CELL_LOCATION, cellLocation, 0);
  }

  private Token(Kind kind, CellLocation cellLocationValue, double numberValue) {
    this.kind = kind;
    this.cellLocationValue = cellLocationValue;
    this.numberValue = numberValue;
  }

  @Override
  public String toString() {
    switch (kind) {
      case CELL_LOCATION:
        return "CELL(" + cellLocationValue.toString() + ")";
      case NUMBER:
        return "NUMBER(" + numberValue + ")";
      default:
        return kind.name();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Token other = (Token) obj;
    if (this.kind != other.kind) {
      return false;
    }
    switch (this.kind) {
      case NUMBER:
        return this.numberValue == other.numberValue;
      case CELL_LOCATION:
        return this.cellLocationValue.equals(other.cellLocationValue);
      default:
        return true;
    }
  }

  @Override
  public int hashCode() {
    switch (kind) {
      case NUMBER:
        return Objects.hash(kind, numberValue);
      case CELL_LOCATION:
        return Objects.hash(kind, cellLocationValue);
      default:
        return Objects.hash(kind);
    }
  }

  public enum Kind {
    PLUS("+",1),
    MINUS("-",1),
    STAR("*",2),
    SLASH("/",2),
    CARET("^",3),
    LPARENTHESIS("(",0),
    RPARENTHESIS(")",4),
    LANGLE("<"),
    RANGLE(">"),
    EQUALS("="),
    NUMBER(),
    CELL_LOCATION(),
    ;

    private String symbol;
    private int precedence;

    Kind(String symbol,int precedence) {
      this.symbol = symbol;
      this.precedence = precedence;
    }
    Kind(String symbol) {
      this(symbol,1);
    }
    Kind(){
      this("",1);
    }

    public String getSymbol() {
      return symbol;
    }

    public int getPrecedence() {
      return precedence;
    }
  }
}
