package cellular_automata.graphics.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cellular_automata.cells.CellState;

public class BoardTest {
  @Test
  void drawingIsNotAllowedUntilTheBoardIsSetUp() {
    final Board board = new Board();

    assertThrows(BoardNotSetUpRuntimeException.class, board::drawCells);
  }

  @Test
  void theBoardCanBeSetUpWithoutModifyingCells() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);

    assertEquals(CellState.DEAD, board.getCells().getCell(0, 0).getState());
  }

  @Test
  void liveBoardCellCanBeSetAndRetrieved() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);
    board.getCells().getCell(0, 0).toggleState();
    board.drawCells();

    assertEquals(CellState.LIVE, board.getCells().getCell(0, 0).getState());
  }

  @Test
  void gridLinesCanBeToggled() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);

    final boolean initialGridLines = board.getShowGridLines();

    board.toggleGridLines();

    final boolean postGridLines = board.getShowGridLines();

    assertTrue(initialGridLines && !postGridLines);
  }

  @Test
  void gridLinesCanBeExternallySet() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);

    final boolean preGridLinesSet = board.getShowGridLines();

    board.setShowGridLines(false);

    final boolean postGridLinesSet = board.getShowGridLines();

    assertTrue(preGridLinesSet && !postGridLinesSet);
  }
}
