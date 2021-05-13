package cellular_automata.cells;

public class Cell implements Cloneable {
	private CellState state;
    
    public Cell() {
        setState(CellState.DEAD);
    }

    public CellState getState() {
        return state;
    }

    public void setState(final CellState state) {
        this.state = state;
    }

    public void toggleState() {
        if (CellState.DEAD.equals(state)) {
            setState(CellState.LIVE);
            return;
        }

        setState(CellState.DEAD);
    }

    @Override
    public boolean equals(final Object cell) {
        if (this == cell) {
            return true;
        }

        if (!(cell instanceof Cell)) {
            return false;
        }

        final Cell castedCell = (Cell) cell;

        return state.equals(castedCell.getState());
    }

    @Override
    public Object clone() {
        try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO: Figure out a way to present this error.
			throw new RuntimeException(e);
		}
    }
}
