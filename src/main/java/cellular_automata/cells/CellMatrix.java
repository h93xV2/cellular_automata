package cellular_automata.cells;

import cellular_automata.cells.rules.CellRules;

public interface CellMatrix {
    void forEach(final CellTriConsumer consumer);

    Cell getCell(final int x, final int y);

    void lockCurrentStateAsSeed();

    void next();

    void last();

    void reset();

    void clear();

    Cell[][] getWorkingCells();

    void copyRules(CellRules cellRules);

    CellRules getRules();

    void copyCellStates(Cell[][] source);
}