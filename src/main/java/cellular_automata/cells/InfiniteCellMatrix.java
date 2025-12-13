package cellular_automata.cells;

import java.util.HashSet;
import java.util.Set;

import cellular_automata.cells.rules.CellRules;

public class InfiniteCellMatrix implements CellMatrix {
    private final Set<CellPoint> cellPoints;

    public InfiniteCellMatrix() {
        this.cellPoints = new HashSet<>();
    }

    @Override
    public CellState getState(final int x, final int y) {
        // TODO: This will not perform well for large data sets.
        for (CellPoint cellPoint : cellPoints) {
            if (cellPoint.getX() == x && cellPoint.getY() == y) {
                return CellState.LIVE;
            }
        }

        return CellState.DEAD;
    }

    @Override
    public void lockCurrentStateAsSeed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lockCurrentStateAsSeed'");
    }

    @Override
    public void next() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'next'");
    }

    @Override
    public void last() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'last'");
    }

    @Override
    public void reset() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }

    @Override
    public void clear() {
        this.cellPoints.clear();
    }

    @Override
    public Cell[][] getWorkingCells() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWorkingCells'");
    }

    @Override
    public void copyRules(CellRules cellRules) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copyRules'");
    }

    @Override
    public CellRules getRules() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRules'");
    }

    @Override
    public void copyCellStates(Cell[][] source) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copyCellStates'");
    }
    
}
