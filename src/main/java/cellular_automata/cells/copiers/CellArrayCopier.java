package cellular_automata.cells.copiers;

import cellular_automata.cells.Cell;

public class CellArrayCopier {
  private CellArrayCopier() {
  }

  public static Cell[][] createCopy(final Cell[][] cells) {
    final Cell[][] copyCells = new Cell[cells.length][cells[0].length];

    for (var x = 0; x < cells.length; x++) {
      for (var y = 0; y < cells[0].length; y++) {
        if (cells[x][y] != null) {
          copyCells[x][y] = (Cell) cells[x][y].clone();
        }
      }
    }

    return copyCells;
  }
}
