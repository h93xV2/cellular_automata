package cellular_automata.cells;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import cellular_automata.cells.rules.CellRules;

public class CellMatrix implements Cloneable {
  private static final int cellRowStartIndex = 0;
  private static final int cellColumnStartIndex = 0;
  private Cell[][] seedCells;
  private Cell[][] tempCells;
  private Cell[][] workingCells;
  private int width;
  private int height;
  private CellRules cellRules;
  private Deque<Cell[][]> history;

  public CellMatrix(final int width, final int height) {
    this(new Cell[width][height]);
  }

  public CellMatrix(final Cell[][] sourceCells) {
    width = sourceCells.length;
    height = sourceCells[0].length;
    seedCells = new Cell[width][height];
    tempCells = new Cell[width][height];
    workingCells = new Cell[width][height];
    cellRules = new CellRules();
    history = new ArrayDeque<>();

    forEach((x, y) -> {
      final Cell sourceCell = sourceCells[x][y] == null ? new Cell() : sourceCells[x][y];
      seedCells[x][y] = (Cell) sourceCell.clone();
      tempCells[x][y] = (Cell) sourceCell.clone();
      workingCells[x][y] = (Cell) sourceCell.clone();
    });
  }

  public void forEach(final CellTriConsumer consumer) {
    forEach((x, y) -> consumer.accept(x, y, workingCells[x][y]));
  }

  public void forEach(final Consumer<Cell> consumer) {
    forEach((x, y) -> consumer.accept(workingCells[x][y]));
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
    forEach((x, y, cell) -> seedCells[x][y].setState(cell.getState()));
  }

  public void next() {
    final Cell[][] currentFrame = new Cell[width][height];

    forEach((x, y, cell) -> {
      tempCells[x][y].setState(cell.getState());
      currentFrame[x][y] = (Cell) cell.clone();
    });

    history.push(currentFrame);

    forEach((x, y, cell) -> {
      final int liveNeighbors = countLiveNeighbors(x, y);

      if (resurrect(cell, liveNeighbors) || kill(cell, liveNeighbors)) {
        cell.toggleState();
      }
    });
  }

  private boolean resurrect(final Cell currentCell, final int liveNeighbors) {
    return CellState.DEAD.equals(currentCell.getState()) && cellRules.isCountWithinBirthSet(liveNeighbors);
  }

  private boolean kill(final Cell currentCell, final int liveNeighbors) {
    return CellState.LIVE.equals(currentCell.getState()) && !cellRules.isCountWithinSurvivalSet(liveNeighbors);
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

  public void last() {
    if (!history.isEmpty()) {
      final Cell[][] lastFrame = history.pop();

      forEach((x, y, cell) -> cell.setState(lastFrame[x][y].getState()));
    }
  }

  public void reset() {
    forEach((x, y, cell) -> cell.setState(seedCells[x][y].getState()));
    history.clear();
  }

  public void clear() {
    forEach(cell -> cell.setState(CellState.DEAD));
    history.clear();
  }

  public Cell[][] getWorkingCells() {
    return workingCells;
  }

  public void copyRules(final CellRules cellRules) {
    this.cellRules = (CellRules) cellRules.clone();
  }

  public CellRules getRules() {
    return cellRules;
  }
}
