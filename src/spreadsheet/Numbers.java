package spreadsheet;

import common.api.CellLocation;
import common.api.EvaluationContext;
import common.api.Expression;

import java.util.Collections;
import java.util.Set;

public class Numbers implements Expression {

  private double value;

  public Numbers(double value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return Double.toString(value);
  }

  @Override
  public double evaluate(EvaluationContext context) {
    return value;
  }

  @Override
  public void findCellReferences(Set<CellLocation> dependencies) {
  }

  @Override
  public Set<CellLocation> getCellReferences() {
    return Collections.emptySet();
  }
}
