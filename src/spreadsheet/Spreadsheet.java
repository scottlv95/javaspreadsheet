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

  private Map<CellLocation, Cell> state;

  public Spreadsheet() {
    this.state = new HashMap<>();
  }

  /**
   * Construct an empty spreadsheet.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */


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
  public void setCellExpression(CellLocation location, String input) throws InvalidSyntaxException {
    if (!state.containsKey(location)) {
      state.put(location, new Cell(this, location));
    }
    Cell c = state.get(location);
    c.setExpression(input);
    c.recalculate();
  }

  @Override
  public double getCellValue(CellLocation location) {
    if (!state.containsKey(location)) {
      state.put(location,new Cell(this,location));
    }
    return state.get(location).getValue();
  }
  //
  // end replacing
  //

  @Override
  public String getCellExpression(CellLocation location) {
    if (!state.containsKey(location)) {
      state.put(location,new Cell(this,location));
    }
    return state.get(location).getExpression();
  }

  @Override
  public String getCellDisplay(CellLocation location) {
    if (!state.containsKey(location)) {
      state.put(location,new Cell(this,location));
    }
    if (state.get(location).getEmptyState()) {
      return "";
    }
    else {
      return Double.toString(getCellValue(location));
    }
  }

  @Override
  public void addDependency(CellLocation dependent, CellLocation dependency) {
    state.get(dependency).addDependent(dependent);
  }

  @Override
  public void removeDependency(CellLocation dependent, CellLocation dependency) {
    state.get(dependency).removeDependent(dependent);
  }

  @Override
  public void recalculate(CellLocation location) {
    state.get(location).recalculate();
  }

  @Override
  public void findCellReferences(CellLocation subject, Set<CellLocation> target) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
