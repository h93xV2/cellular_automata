package cellular_automata.cells;

public class CellPoint {
    private final int x;
    private final int y;
    private final Cell cell;

    public CellPoint(final int x, final int y) {
        this.x = x;
        this.y = y;

        this.cell = new Cell();
        this.cell.setState(CellState.LIVE);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Cell getCell() {
        return this.cell;
    }
}
