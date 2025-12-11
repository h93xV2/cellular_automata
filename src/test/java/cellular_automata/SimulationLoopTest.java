package cellular_automata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cellular_automata.cells.CellState;
import cellular_automata.cells.Generations;
import cellular_automata.graphics.board.Board;

public class SimulationLoopTest {
  @Test
  void cellsAreNotMutatedWhenTheLoopIsCreated() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);

    final SimulationLoop loop = new SimulationLoop(board, 1, new Generations());
    loop.next();

    assertEquals(CellState.DEAD, board.getCells().getCell(0, 0).getState());
  }

  @Disabled
  @Test
  void theLoopCanBeReset() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);
    board.getCells().getCell(0, 0).toggleState();
    board.getCells().lockCurrentStateAsSeed();

    final boolean cellIsAliveBeforeLoop = CellState.LIVE.equals(board.getCells().getCell(0, 0).getState());

    final SimulationLoop loop = new SimulationLoop(board, 1, new Generations());
    loop.next();

    final boolean cellIsDeadAfterLoop = CellState.DEAD.equals(board.getCells().getCell(0, 0).getState());

    loop.reset();

    final boolean cellIsAliveAfterReset = CellState.LIVE.equals(board.getCells().getCell(0, 0).getState());

    assertTrue(cellIsAliveBeforeLoop && cellIsDeadAfterLoop && cellIsAliveAfterReset);
  }

  @Disabled
  @Test()
  void theLoopCanBeCleared() {
    final Board board = new Board();
    board.setUp(1, 1, 1, 1);
    board.getCells().getCell(0, 0).toggleState();

    final boolean cellIsAliveBefore = CellState.LIVE.equals(board.getCells().getCell(0, 0).getState());

    final SimulationLoop loop = new SimulationLoop(board, 1, new Generations());
    loop.clear();

    final boolean cellIsDeadAfterClear = CellState.DEAD.equals(board.getCells().getCell(0, 0).getState());

    assertTrue(cellIsAliveBefore && cellIsDeadAfterClear);
  }
}
