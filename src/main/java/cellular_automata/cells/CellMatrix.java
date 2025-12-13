package cellular_automata.cells;

import cellular_automata.cells.rules.CellRules;

public interface CellMatrix {
    CellState getState(int x, int y);

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