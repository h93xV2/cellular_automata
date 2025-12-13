package cellular_automata.cells;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FiniteCellMatrixTest {
  @Test
  void cellsCanBeSet() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 1);
    final Cell initialCell = matrix.getCell(0, 0);
    final Cell newCell = new Cell();
    newCell.setState(CellState.LIVE);

    matrix.set(0, 0, newCell);

    assertNotEquals(initialCell, matrix.getCell(0, 0));
  }

  @Test
  void anExceptionIsThrownWhenSettingOutsideTheBoundaries() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 1);

    assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(1, 2, new Cell()));
  }

  @Test
  void anExceptionIsThrownWhenGettingOutsideTheBoundaries() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 1);

    assertThrows(IndexOutOfBoundsException.class, () -> matrix.getCell(1, 2));
  }

  @Test
  void checkMatrixCanBeReset() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 2);
    matrix.getCell(0, 1).toggleState();

    assertTrue(CellState.LIVE.equals(matrix.getCell(0, 1).getState()));

    matrix.lockCurrentStateAsSeed();
    matrix.getCell(0, 1).toggleState();

    assertTrue(CellState.DEAD.equals(matrix.getCell(0, 1).getState()));

    matrix.reset();

    assertTrue(CellState.LIVE.equals(matrix.getCell(0, 1).getState()));
  }

  @Test
  void cellsDeadAfterClear() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 2);
    matrix.getCell(0, 0).toggleState();

    final boolean aliveBeforeClear = CellState.LIVE.equals(matrix.getCell(0, 0).getState());

    matrix.clear();

    final boolean deadAfterClear = CellState.DEAD.equals(matrix.getCell(0, 0).getState());

    assertTrue(aliveBeforeClear && deadAfterClear);
  }

  @Test
  void getWidth() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(2, 1);

    assertEquals(2, matrix.getWidth());
  }

  @Test
  void getHeight() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 5);

    assertEquals(5, matrix.getHeight());
  }

  @Test
  void oneCellDies() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 1);
    matrix.getCell(0, 0).toggleState();

    final boolean aliveBeforeNext = CellState.LIVE.equals(matrix.getCell(0, 0).getState());

    matrix.next();

    final boolean deadAfterNext = CellState.DEAD.equals(matrix.getCell(0, 0).getState());

    assertTrue(aliveBeforeNext && deadAfterNext);
  }

  @Test
  void setCellOutOfBoundsNegativeX() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 1);

    assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(-1, 0, new Cell()));
  }

  @Test
  void setCellOutOfBoundsOverPositiveX() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 1);

    assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(2, 0, new Cell()));
  }

  @Test
  void setCellOutOfBoundsNegativeY() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 1);

    assertThrows(IndexOutOfBoundsException.class, () -> matrix.set(0, -1, new Cell()));
  }

  @Test
  void createMatrixFromCellularArray() {
    final Cell[][] sourceCells = new Cell[1][1];
    sourceCells[0][0] = new Cell();
    sourceCells[0][0].toggleState();

    final CellState sourceState = sourceCells[0][0].getState();

    final FiniteCellMatrix matrix = new FiniteCellMatrix(sourceCells);

    assertEquals(sourceState, matrix.getCell(0, 0).getState());
  }

  @Test
  void threeCellsCauseBirth() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(2, 2);
    matrix.getCell(0, 0).toggleState();
    matrix.getCell(0, 1).toggleState();
    matrix.getCell(1, 0).toggleState();

    final boolean cellIsDeadInitially = CellState.DEAD.equals(matrix.getCell(1, 1).getState());

    matrix.next();

    final boolean cellIsAlive = CellState.LIVE.equals(matrix.getCell(1, 1).getState());

    assertTrue(cellIsDeadInitially && cellIsAlive);
  }

  @Test
  void cellDiesFromOverPopulation() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(2, 3);
    matrix.getCell(0, 0).toggleState();
    matrix.getCell(0, 1).toggleState();
    matrix.getCell(1, 0).toggleState();
    matrix.getCell(1, 1).toggleState();
    matrix.getCell(0, 2).toggleState();
    matrix.next();

    assertEquals(CellState.DEAD, matrix.getCell(0, 1).getState());
  }

  @Test
  void cellsCanBeSteppedBack() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(1, 1);
    matrix.getCell(0, 0).setState(CellState.LIVE);
    matrix.next();
    matrix.last();

    assertEquals(CellState.LIVE, matrix.getCell(0, 0).getState());
  }

  @Test
  void cellsCanBeSteppedBackMultipleTimes() {
    final FiniteCellMatrix matrix = new FiniteCellMatrix(3, 2);
    matrix.getCell(0, 1).setState(CellState.LIVE);
    matrix.getCell(1, 1).setState(CellState.LIVE);
    matrix.getCell(2, 0).setState(CellState.LIVE);
    matrix.next();
    matrix.next();
    matrix.last();
    matrix.last();

    assertEquals(CellState.LIVE, matrix.getCell(0, 1).getState());
  }

  @Test
  void cellStatesCanBeCopied() {
    final Cell[][] source = new Cell[1][2];
    source[0][0] = new Cell();
    source[0][0].toggleState();
    source[0][1] = new Cell();
    source[0][1].toggleState();

    final FiniteCellMatrix destination = new FiniteCellMatrix(1, 2);
    final boolean deadBefore = CellState.DEAD.equals(destination.getCell(0, 0).getState())
        && CellState.DEAD.equals(destination.getCell(0, 1).getState());

    destination.copyCellStates(source);

    final boolean aliveAfter = CellState.LIVE.equals(destination.getCell(0, 0).getState())
        && CellState.LIVE.equals(destination.getCell(0, 1).getState());

    assertTrue(deadBefore && aliveAfter);
  }
}
