package cellular_automata;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cellular_automata.board.Board;
import cellular_automata.cells.CellState;

public class SimulationLoopTest {
  @Test
  void cellsAreNotMutatedWhenTheLoopIsCreated() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);

    final SimulationLoop loop = new SimulationLoop(board, 1);
    loop.next();

    assertEquals(CellState.DEAD, board.getCells().getCell(0, 0).getState());
  }

  @Test
  void theLoopCanBeReset() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);
    board.getCells().getCell(0, 0).toggleState();
    board.getCells().lockCurrentStateAsSeed();

    final boolean cellIsAliveBeforeLoop = CellState.LIVE.equals(board.getCells().getCell(0, 0).getState());

    final SimulationLoop loop = new SimulationLoop(board, 1);
    loop.next();

    final boolean cellIsDeadAfterLoop = CellState.DEAD.equals(board.getCells().getCell(0, 0).getState());

    loop.reset();

    final boolean cellIsAliveAfterReset = CellState.LIVE.equals(board.getCells().getCell(0, 0).getState());

    assertTrue(cellIsAliveBeforeLoop && cellIsDeadAfterLoop && cellIsAliveAfterReset);
  }
}
