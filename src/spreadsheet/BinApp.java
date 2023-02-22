package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;
import java.util.Set;

public class BinApp implements Expression {

  private BinOp operator;
  private Expression e1;
  private Expression e2;

  public BinApp(BinOp operator, Expression e1, Expression e2) {
    this.operator = operator;
    this.e1 = e1;
    this.e2 = e2;
  }

  @Override
  public String toString() {
    return "(" + e1.toString() + operator.toString() + e2.toString() + ")";
  }

  @Override
  public double evaluate(EvaluationContext context) {
    double v1 = e1.evaluate(context);
    double v2 = e2.evaluate(context);
    double ret;
    switch (operator.getKind()) {
      case PLUS -> ret = v1 + v2;
      case MINUS -> ret = v1 - v2;
      case CARET -> ret = Math.pow(v1, v2);
      case SLASH -> ret = v1 / v2;
      case STAR -> ret = v1 * v2;
      default -> ret = 0;
    }
    return ret;
  }

  @Override
  public void findCellReferences(Set<CellLocation> dependencies) {
    e1.findCellReferences(dependencies);
    e2.findCellReferences(dependencies);
  }
}
