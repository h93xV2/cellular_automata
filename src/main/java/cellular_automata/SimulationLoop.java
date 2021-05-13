package cellular_automata;

import cellular_automata.cells.CellMatrix;
import javafx.animation.AnimationTimer;

public class SimulationLoop extends AnimationTimer {
    private static final long DEFAULT_TIME_STEP = 125000000;
    private final CellMatrix cells;
    private long lastTime;
    private boolean firstIteration;
    private long timeStep;
    private Board board;
    
    public SimulationLoop(final Board board) {
        this.board = board;
        this.cells = this.board.getCells();
        firstIteration = true;
        timeStep = DEFAULT_TIME_STEP;
    }
    
    @Override
    public void handle(final long now) {
        if (firstIteration) {
            firstIteration = false;
        }

        if (now - lastTime > timeStep) {
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
        timeStep = (long) (DEFAULT_TIME_STEP / multiplier);
    }
}
