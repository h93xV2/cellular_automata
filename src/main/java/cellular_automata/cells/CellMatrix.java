package cellular_automata.cells;

import java.util.function.BiConsumer;

public class CellMatrix implements Cloneable {
  private static final int cellRowStartIndex = 0;
  private static final int cellColumnStartIndex = 0;
  private Cell[][] seedCells;
  private Cell[][] tempCells;
  private Cell[][] workingCells;
  private int width;
  private int height;
  private BirthAndSurvivalConstraints constraints;

  public CellMatrix(final int width, final int height) {
    this(new Cell[width][height]);
  }

  public CellMatrix(final Cell[][] sourceCells) {
    this.width = sourceCells.length;
    this.height = sourceCells[0].length;
    seedCells = new Cell[this.width][this.height];
    tempCells = new Cell[this.width][this.height];
    workingCells = new Cell[this.width][this.height];
    constraints = new BirthAndSurvivalConstraints();

    forEach((x, y) -> {
      final Cell sourceCell = sourceCells[x][y] == null ? new Cell() : sourceCells[x][y];
      seedCells[x][y] = (Cell) sourceCell.clone();
      tempCells[x][y] = (Cell) sourceCell.clone();
      workingCells[x][y] = (Cell) sourceCell.clone();
    });
  }

  public void forEach(final BiConsumer<Integer, Integer> consumer) {
    for (var x = cellRowStartIndex; x < width; x++) {
      for (var y = cellColumnStartIndex; y < height; y++) {
        consumer.accept(x, y);
      }
    }
  }

  public Cell getCell(final int x, final int y) {
    checkBounds(x, y);

    return workingCells[x][y];
  }

  public void set(final int x, final int y, final Cell newCell) {
    checkBounds(x, y);

    workingCells[x][y] = newCell;
    tempCells[x][y] = (Cell) newCell.clone();
    seedCells[x][y] = (Cell) newCell.clone();
  }

  private void checkBounds(final int x, final int y) {
    if (x < cellRowStartIndex || width < x || y < cellColumnStartIndex || height < y) {
      throw new IndexOutOfBoundsException();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void lockCurrentStateAsSeed() {
    forEach((x, y) -> seedCells[x][y].setState(workingCells[x][y].getState()));
  }

  public void next() {
    forEach((x, y) -> tempCells[x][y].setState(workingCells[x][y].getState()));

    forEach((x, y) -> {
      final Cell currentCell = workingCells[x][y];
      final int liveNeighbors = countLiveNeighbors(x, y);

      if (resurrect(currentCell, liveNeighbors) || kill(currentCell, liveNeighbors)) {
        currentCell.toggleState();
      }
    });
  }

  private boolean resurrect(final Cell currentCell, final int liveNeighbors) {
    return CellState.DEAD.equals(currentCell.getState()) && constraints.isCountWithinBirthSet(liveNeighbors);
  }

  private boolean kill(final Cell currentCell, final int liveNeighbors) {
    return CellState.LIVE.equals(currentCell.getState()) && !constraints.isCountWithinSurvivalSet(liveNeighbors);
  }

  private int countLiveNeighbors(final int x, final int y) {
    var neighbors = 0;

    for (var i = x - 1; i <= x + 1; i++) {
      for (var j = y - 1; j <= y + 1; j++) {
        if (0 <= i && i < width && 0 <= j && j < height
            && ((x == i && y != j) || (x != i && y == j) || (x != i && y != j))
            && CellState.LIVE.equals(tempCells[i][j].getState())) {
          neighbors++;
        }
      }
    }

    return neighbors;
  }

  public void reset() {
    forEach((i, j) -> workingCells[i][j].setState(seedCells[i][j].getState()));
  }

  public void clear() {
    forEach((i, j) -> workingCells[i][j].setState(CellState.DEAD));
  }

  public Cell[][] getWorkingCells() {
    return workingCells;
  }

  public void copyConstraints(final BirthAndSurvivalConstraints constraints) {
    this.constraints = (BirthAndSurvivalConstraints) constraints.clone();
  }
}
