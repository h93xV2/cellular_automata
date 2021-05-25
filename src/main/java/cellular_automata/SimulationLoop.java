package cellular_automata;

import cellular_automata.board.Board;
import cellular_automata.cells.CellMatrix;
import javafx.animation.AnimationTimer;

public class SimulationLoop extends AnimationTimer {
  private final CellMatrix cells;
  private long lastTime;
  private boolean firstIteration;
  private final long referenceTimeStep;
  private long currentTimeStep;
  private Board board;

  public SimulationLoop(final Board board, final long timeStep) {
    this.board = board;
    this.cells = this.board.getCells();
    firstIteration = true;
    referenceTimeStep = timeStep;
    currentTimeStep = timeStep;
  }

  @Override
  public void handle(final long now) {
    if (firstIteration) {
      firstIteration = false;
    }

    if (now - lastTime > currentTimeStep) {
      lastTime = now;
      next();
    }
  }

  public void next() {
    cells.next();
    board.drawCells();
  }

  public void reset() {
    stop();
    cells.reset();
    board.drawCells();
  }

  public void clear() {
    stop();
    cells.clear();
    cells.lockCurrentStateAsSeed();
    board.drawCells();
  }

  @Override
  public void stop() {
    super.stop();
    firstIteration = true;
  }

  public void setTimeStepMultiplier(final double multiplier) {
    currentTimeStep = (long) (referenceTimeStep / multiplier);
  }
}
