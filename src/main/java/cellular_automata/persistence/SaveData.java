package cellular_automata.persistence;

import cellular_automata.cells.Cell;

public class SaveData {
	private Cell[][] cellMatrix;
	private boolean showGridLines;
	
	public SaveData() {
		 // This is empty to appease the object mapper.
	}
	
	public SaveData(final Cell[][] cellMatrix, final boolean showGridLines) {
		this.cellMatrix = cellMatrix;
		this.showGridLines = showGridLines;
	}
	
	public Cell[][] getCells() {
		return cellMatrix;
	}
	
	public void setCells(final Cell[][] cellMatrix) {
		this.cellMatrix = cellMatrix;
	}
	
	public boolean getShowGridLines() {
		return showGridLines;
	}
	
	public void setShowGridLines(final boolean showGridLines) {
		this.showGridLines = showGridLines;
	}
}
