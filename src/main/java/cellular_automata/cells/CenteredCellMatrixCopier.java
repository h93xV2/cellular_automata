package cellular_automata.cells;

public class CenteredCellMatrixCopier implements CellMatrixCopier {

  @Override
  public void copyCellStates(Cell[][] source, CellMatrix destination) {
    final int xOffset = (destination.getWidth() - source.length) / 2;
    final int yOffset = (destination.getHeight() - source[0].length) / 2;

    for (var x = 0; x < source.length; x++) {
      for (var y = 0; y < source[0].length; y++) {
        final int xDestination = x + xOffset;
        final int yDestination = y + yOffset;

        if (0 <= xDestination && xDestination < destination.getWidth() && 0 <= yDestination
            && yDestination < destination.getHeight()) {
          if (source[x][y] == null) {
            destination.getCell(xDestination, yDestination).setState(CellState.DEAD);
          } else {
            destination.getCell(xDestination, yDestination).setState(source[x][y].getState());
          }
        }
      }
    }
  }

}
