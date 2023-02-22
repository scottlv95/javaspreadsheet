package spreadsheet;

import common.api.BasicSpreadsheet;
import common.api.CellLocation;
import common.api.Expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Spreadsheet implements BasicSpreadsheet {
  //
  // start replacing
  //

  public Spreadsheet() {
    this.state = new HashMap<CellLocation, Double>();
  }

  /**
   * Construct an empty spreadsheet.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */

  private Map<CellLocation, Double> state;

  /**
   * Parse and evaluate an expression, using the spreadsheet as a context.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  public double evaluateExpression(String expression)
      throws InvalidSyntaxException {
    Expression e = Parser.parse(expression);
    return e.evaluate(this);
  }

  /**
   * Assign an expression to a cell.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  public void setCellExpression(CellLocation location, String input)
      throws InvalidSyntaxException {
    state.put(location, evaluateExpression(input));
  }

  @Override
  public double getCellValue(CellLocation location) {
    return state.getOrDefault(location, 0.0);
  }
  //
  // end replacing
  //

  @Override
  public String getCellExpression(CellLocation location) {
    return "";
  }

  @Override
  public String getCellDisplay(CellLocation location) {
    return Double.toString(this.getCellValue(location));
  }

  @Override
  public void addDependency(CellLocation dependent, CellLocation dependency) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void removeDependency(CellLocation dependent, CellLocation dependency) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void recalculate(CellLocation location) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  @Override
  public void findCellReferences(CellLocation subject, Set<CellLocation> target) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
