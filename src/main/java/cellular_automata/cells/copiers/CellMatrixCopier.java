package cellular_automata.cells.copiers;

import cellular_automata.cells.Cell;
import cellular_automata.cells.CellMatrix;

public interface CellMatrixCopier {
  public void copyCellStates(final Cell[][] source, final CellMatrix destination);
}
