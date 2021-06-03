package cellular_automata.cells.copiers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import cellular_automata.cells.Cell;
import cellular_automata.cells.CellMatrix;
import cellular_automata.cells.CellState;

@TestInstance(Lifecycle.PER_CLASS)
public class CenteredCellMatrixCopierTest {
  private CellMatrixCopier copier;

  @BeforeAll
  void init() {
    copier = new CenteredCellMatrixCopier();
  }

  @Test
  void cellStatesCanBeCopiedInSameSizedObjects() {
    final Cell[][] sourceCells = new Cell[1][1];
    sourceCells[0][0] = new Cell();
    sourceCells[0][0].setState(CellState.LIVE);

    final CellMatrix matrix = new CellMatrix(1, 1);

    final boolean matrixCellIsInitiallyDead = CellState.DEAD.equals(matrix.getCell(0, 0).getState());

    copier.copyCellStates(sourceCells, matrix);

    final boolean matrixCellBecomesAlive = CellState.LIVE.equals(matrix.getCell(0, 0).getState());

    assertTrue(matrixCellIsInitiallyDead && matrixCellBecomesAlive);
  }

  @Test
  void cellStatesAreNotCopiedFromHorizontallyOversizedArrays() {
    final Cell[][] sourceCells = new Cell[2][1];
    sourceCells[0][0] = new Cell();
    sourceCells[1][0] = new Cell();

    final CellMatrix matrix = new CellMatrix(1, 1);

    copier.copyCellStates(sourceCells, matrix);

    assertEquals(1, matrix.getWidth());
  }

  @Test
  void deadStateIsSetWhenCellArrayHasNullEntry() {
    final Cell[][] sourceCells = new Cell[1][1];

    final CellMatrix matrix = new CellMatrix(1, 1);

    copier.copyCellStates(sourceCells, matrix);

    assertEquals(CellState.DEAD, matrix.getCell(0, 0).getState());
  }

  @Test
  void matrixIsHorizontallyBiggerThanArray() {
    final Cell[][] sourceCells = new Cell[1][1];

    final CellMatrix matrix = new CellMatrix(2, 1);

    copier.copyCellStates(sourceCells, matrix);

    assertEquals(CellState.DEAD, matrix.getCell(1, 0).getState());
  }

  @Test
  void matrixIsVerticallyBiggerThanArray() {
    final Cell[][] sourceCells = new Cell[1][1];

    final CellMatrix matrix = new CellMatrix(1, 2);

    copier.copyCellStates(sourceCells, matrix);

    assertEquals(CellState.DEAD, matrix.getCell(0, 1).getState());
  }

}
