package cellular_automata.cells;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
  @Test
  void cellIsDeadByDefault() {
    final Cell cell = new Cell();

    assertEquals(CellState.DEAD, cell.getState());
  }

  @Test
  void deadCellIsToggledAlive() {
    final Cell cell = new Cell();
    cell.toggleState();

    assertEquals(CellState.LIVE, cell.getState());
  }

  @Test
  void cellCanHaveStateSet() {
    final Cell cell = new Cell();
    cell.toggleState();
    cell.setState(CellState.DEAD);

    assertEquals(CellState.DEAD, cell.getState());
  }

  @Test
  void cellsAreEqual() {
    final Cell cellA = new Cell();
    final Cell cellB = new Cell();

    assertEquals(cellA, cellB);
  }

  @Test
  void cellEqualityIsReflexive() {
    final Cell cell = new Cell();

    assertEquals(cell, cell);
  }

  @Test
  void cellEqualityIsSymmetric() {
    final Cell x = new Cell();
    final Cell y = new Cell();

    assertTrue(x.equals(y) && y.equals(x));
  }

  @Test
  void cellEqualityIsTransitive() {
    final Cell x = new Cell();
    final Cell y = new Cell();
    final Cell z = new Cell();

    assertTrue(x.equals(y) && y.equals(z) && x.equals(z));
  }

  @Test
  void cellsAreNotNull() {
    final Cell cell = new Cell();

    assertFalse(cell.equals(null));
  }

  @Test
  void cellsWithDifferentStatesAreNotEqual() {
    final Cell cellA = new Cell();
    final Cell cellB = new Cell();

    cellA.toggleState();

    assertNotEquals(cellA, cellB);
  }

  @Test
  void clonedCellIsNewObject() {
    final Cell cell = new Cell();

    final Cell clone = (Cell) cell.clone();

    assertTrue(clone != cell);
  }

  @Test
  void clonedCellEqualsOriginalCell() {
    final Cell cell = new Cell();

    final Cell clone = (Cell) cell.clone();

    assertEquals(cell, clone);
  }

  @Test
  void modifiedCloneIsNotEqualToOriginal() {
    final Cell cell = new Cell();

    final Cell clone = (Cell) cell.clone();
    clone.toggleState();

    assertNotEquals(cell, clone);
  }

  @Test
  void originalIsNotAffectedByCloneStateChange() {
    final Cell cell = new Cell();

    final Cell clone = (Cell) cell.clone();
    clone.toggleState();

    assertEquals(cell, cell);
  }
}
