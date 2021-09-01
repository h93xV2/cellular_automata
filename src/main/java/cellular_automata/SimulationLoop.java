package cellular_automata;

import cellular_automata.cells.CellMatrix;
import cellular_automata.cells.Generations;
import cellular_automata.graphics.board.Board;
import javafx.animation.AnimationTimer;

public class SimulationLoop extends AnimationTimer {
  private final CellMatrix cells;
  private long lastTime;
  private boolean firstIteration;
  private final long referenceTimeStep;
  private long currentTimeStep;
  private Board board;
  private Generations generations;

  public SimulationLoop(final Board board, final long timeStep, final Generations generations) {
    this.board = board;
    this.cells = this.board.getCells();
    this.generations = generations;
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

  public void last() {
    cells.last();
    generations.decrement();
    board.draw();
  }
  
  public void next() {
    cells.next();
    generations.increment();
    board.draw();
  }

  public void reset() {
    stop();
    cells.reset();
    generations.reset();
    board.draw();
  }

  public void clear() {
    stop();
    cells.clear();
    generations.reset();
    cells.lockCurrentStateAsSeed();
    board.draw();
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
