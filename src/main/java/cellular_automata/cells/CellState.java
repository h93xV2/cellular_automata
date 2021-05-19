package cellular_automata.cells;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum CellState {
  LIVE("o"),
  DEAD("b");

  private String rleCellStateSymbol;
  private static Map<String, CellState> symbolMap;

  private CellState(final String symbol) {
    this.rleCellStateSymbol = symbol;
  }

  String getRleCellStateSymbol() {
    return rleCellStateSymbol;
  }

  public static Map<String, CellState> getRleCellStateSymbolToCellStateMap() {
    if (symbolMap == null) {
      populateRleCellStateSymbolToCellStateMap();
    }

    return symbolMap;
  }

  private static void populateRleCellStateSymbolToCellStateMap() {
    final Map<String, CellState> newSymbolMap = new HashMap<>();

    for (CellState cellState : CellState.values()) {
      newSymbolMap.put(cellState.getRleCellStateSymbol(), cellState);
    }

    symbolMap = Collections.unmodifiableMap(newSymbolMap);
  }
}
