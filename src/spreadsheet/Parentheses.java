package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;
import java.util.Set;

public class Parentheses implements Expression {

  Expression e;

  Parentheses(Expression e) {
    this.e = e;
  }

  @Override
  public double evaluate(EvaluationContext context) {
    return e.evaluate(context);
  }

  @Override
  public void findCellReferences(Set<CellLocation> dependencies) {
    dependencies.addAll(e.getCellReferences());
  }

  @Override
  public Set<CellLocation> getCellReferences() {
    return e.getCellReferences();
  }

  @Override
  public String toString() {
    return "(" + e.toString() + ")";
  }
}
