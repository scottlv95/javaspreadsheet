package spreadsheet;

import common.api.BasicSpreadsheet;
import common.api.CellLocation;
import common.api.Expression;
import java.util.HashSet;
import java.util.Set;

/**
 * A single cell in a spreadsheet, tracking the expression, value, and other parts of cell state.
 */
public class Cell {
  /**
   * Constructs a new cell.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param spreadsheet The parent spreadsheet,
   * @param location The location of this cell in the spreadsheet.
   */
  private BasicSpreadsheet spreadsheet;
  private final CellLocation location;
  private boolean emptyState;
  private double value;
  private Expression expression;
  private Set<CellLocation> dependents;
  private Set<CellLocation> dependencies;


  Cell(BasicSpreadsheet spreadsheet, CellLocation location) {
    this.spreadsheet = spreadsheet;
    this.location = location;
    setEmpty();
    dependents = new HashSet<>();
  }

  /**
   * Gets the cell's last calculated value.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @return the cell's value.
   */
  public double getValue() {
    return value;
  }

  /**
   * Gets the cell's last stored expression, in string form.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @return a string that parses to an equivalent expression to that last stored in the cell; if no
   * expression is stored, we return the empty string.
   */
  public String getExpression() {
    if (emptyState) {
      return "";
    }
    return expression.toString();
  }

  /**
   * Sets the cell's expression from a string.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param input The string representing the new cell expression.
   * @throws InvalidSyntaxException if the string cannot be parsed.
   */
  public void setExpression(String input) throws InvalidSyntaxException {
    dependencies.forEach(ref -> spreadsheet.removeDependency(location, ref));

    if (input.isEmpty()) {
      emptyState = true;
      setEmpty();
    } else {
      expression = Parser.parse(input);
      emptyState = false;
      dependencies = expression.getCellReferences();
      dependencies.forEach(ref -> spreadsheet.addDependency(location, ref));
    }
  }

  /**
   * @return a string representing the value, if any, of this cell.
   */
  @Override
  public String toString() {
    if (emptyState) {
      return "";
    }
    return Double.toString(value);
  }

  /**
   * Adds the given location to this cell's dependents.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param location the location to add.
   */
  public void addDependent(CellLocation location) {
    dependents.add(location);
  }

  /**
   * Adds the given location to this cell's dependents.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param location the location to add.
   */
  public void removeDependent(CellLocation location) {
    dependents.remove(location);
  }

  /**
   * Adds this cell's expression's references to a set.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param target The set that will receive the dependencies for this
   */
  public void findCellReferences(Set<CellLocation> target) {
    if (!emptyState) {
      target.addAll(expression.getCellReferences());
    }
  }

  /**
   * Recalculates this cell's value based on its expression.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   */
  public void recalculate() {
    if (!emptyState) {
      value = expression.evaluate(spreadsheet);
      if (!dependents.isEmpty()) {
        dependents.forEach(dep -> spreadsheet.recalculate(dep));
      }
    } else {
      value = 0.0;
      dependents.forEach(dep -> spreadsheet.recalculate(dep));
    }
  }

  public boolean getEmptyState() {
    return emptyState;
  }

  public void setEmpty() {
    emptyState = true;
    expression = null;
    value = 0.0;
    dependencies = new HashSet<>();
  }

}
