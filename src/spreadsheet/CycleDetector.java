package spreadsheet;

import common.api.BasicSpreadsheet;
import common.api.CellLocation;
import java.util.*;

/**
 * Detects dependency cycles.
 */
public class CycleDetector {
  /**
   * Constructs a new cycle detector.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param spreadsheet The parent spreadsheet, used for resolving cell locations.
   */
  private BasicSpreadsheet spreadsheet;
  private Set<CellLocation> seen = new HashSet<>();

  CycleDetector(BasicSpreadsheet spreadsheet) {
    this.spreadsheet = spreadsheet;
  }

  /**
   * Checks for a cycle in the spreadsheet, starting at a particular cell.
   *
   * <p>DO NOT CHANGE THE SIGNATURE. The test suite depends on this.
   *
   * @param start The cell location where cycle detection should start.
   * @return Whether a cycle was detected in the dependency graph starting at the given cell.
   */
  public boolean hasCycleFrom(CellLocation start) {
    if (seen.contains(start)) {
      return true;
    }
    Set<CellLocation> dependencies = new HashSet<>();
    spreadsheet.findCellReferences(start, dependencies);
    seen.add(start);
    for (CellLocation dep : dependencies) {
      if (hasCycleFrom(dep)) {
        seen.remove(start);
        return true;
      }
      ;
    }
    seen.remove(start);
    return false;
  }

}
