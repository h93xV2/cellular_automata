package cellular_automata.cells;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum CellState {
	LIVE("o"),
	DEAD("b");
	
	private String symbol;
	private static Map<String, CellState> symbolMap;
	
	private CellState(final String symbol) {
		this.symbol = symbol;
	}
	
	String getSymbol() {
		return symbol;
	}
	
	static Map<String, CellState> getSymbolMap() {
		if (symbolMap == null) {
			populateSymbolMap();
		}
		
		return symbolMap;
	}
	
	private static void populateSymbolMap() {
		final Map<String, CellState> newSymbolMap = new HashMap<>();
		
		for (CellState cellState : CellState.values()) {
			newSymbolMap.put(cellState.getSymbol(), cellState);
		}
		
		symbolMap = Collections.unmodifiableMap(newSymbolMap);
	}
}
