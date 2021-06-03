package cellular_automata.cells.copiers;

import cellular_automata.cells.Cell;
import cellular_automata.cells.CellMatrix;
import cellular_automata.cells.CellState;

public class CenteredCellMatrixCopier implements CellMatrixCopier {

  @Override
  public void copyCellStates(Cell[][] source, CellMatrix destination) {
    destination.forEach(cell -> cell.setState(CellState.DEAD));

    final int sourceWidth = source.length;
    final int sourceHeight = source[0].length;
    final int xOffset = (destination.getWidth() - sourceWidth) / 2;
    final int yOffset = (destination.getHeight() - sourceHeight) / 2;

    for (var x = 0; x < sourceWidth; x++) {
      for (var y = 0; y < sourceHeight; y++) {
        final int xDestination = x + xOffset;
        final int yDestination = y + yOffset;

        if (0 <= xDestination && xDestination < destination.getWidth() && 0 <= yDestination
            && yDestination < destination.getHeight()) {
          if (source[x][y] != null) {
            destination.getCell(xDestination, yDestination).setState(source[x][y].getState());
          }
        }
      }
    }
  }

}
