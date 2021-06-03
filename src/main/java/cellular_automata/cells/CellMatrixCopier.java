package cellular_automata.cells;

public interface CellMatrixCopier {
  public void copyCellStates(final Cell[][] source, final CellMatrix destination);
}
