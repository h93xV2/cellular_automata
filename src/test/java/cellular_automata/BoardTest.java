package cellular_automata;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BoardTest {
  @Test
  void boardCellsExist() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);

    assertNotNull(board.getCells());
  }
}
