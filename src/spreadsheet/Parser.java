package spreadsheet;

import common.api.Expression;
import common.lexer.InvalidTokenException;
import common.lexer.Lexer;
import common.lexer.Token;
import java.util.Stack;
import spreadsheet.BinOp.Associativity;

public class Parser {

  /**
   * Parse a string into an Expression.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */


  static Expression parse(String input) throws InvalidSyntaxException {
    Stack<Expression> operands = new Stack<>();
    Stack<BinOp> operators = new Stack<>();
    Lexer lexer = new Lexer(input);
    while (true) {
      Token token;
      try {
        token = lexer.nextToken();
      } catch (InvalidTokenException e) {
        throw new InvalidSyntaxException("Invalid Syntax");
      }
      if (token == null) {
        while (!operators.empty()) {
          Expression e2 = operands.pop();
          Expression e1 = operands.pop();
          operands.push(new BinApp(operators.pop(), e1, e2));
        }
        return operands.pop();
      }
      switch (token.kind) {
        case NUMBER -> {
          Expression e = new Numbers(token.numberValue);
          operands.push(e);
        }
        case CELL_LOCATION -> {
          Expression e = new CellReferences(token.cellLocationValue);
          operands.push(e);
        }
        case CARET, STAR, PLUS, MINUS, SLASH -> {
          BinOp op = new BinOp(token.kind);
          if (operators.empty()) {
            operators.push(op);
          } else if (operators.peek().getPrecedence() > op.getPrecedence() || (
              operators.peek().getPrecedence() == op.getPrecedence()
                  && operators.peek().getAssociativity() == Associativity.LEFT)) {
            Expression e2 = operands.pop();
            Expression e1 = operands.pop();
            Expression e = new BinApp(operators.pop(), e1, e2);
            operators.push(op);
            operands.push(e);
          } else {
            operators.push(op);
          }
        }
        case LPARENTHESIS -> {
          operators.push(new BinOp(Token.Kind.LPARENTHESIS));
        }
        case RPARENTHESIS -> {
          while (operators.peek().getKind() != Token.Kind.LPARENTHESIS) {
            Expression e2 = operands.pop();
            Expression e1 = operands.pop();
            operands.push(new BinApp(operators.pop(), e1, e2));
          }
          Expression e3 = operands.pop();
          operators.pop();
          operands.push(new Parentheses(e3));
        }

        default -> throw new InvalidSyntaxException("Invalid Syntax");
      }

    }
  }
}
