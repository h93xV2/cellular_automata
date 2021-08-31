package cellular_automata.filemanagement;

import java.util.ArrayList;
import java.util.List;

import cellular_automata.cells.Cell;
import cellular_automata.cells.rules.CellRules;

public class SimulationData {
  private List<String> comments;
  private String patternName;
  private String authorInformation;
  private PatternPoint topLeftCorner;
  private CellRules cellRules;
  private int width;
  private int height;
  private Cell[][] cells;
  private boolean showGridLines;

  public SimulationData(final Cell[][] cells, final boolean showGridLines, final CellRules cellRules) {
    this();
    setCells(cells);
    setRules(cellRules);
    this.showGridLines = showGridLines;
  }

  public SimulationData() {
    comments = new ArrayList<>();
    topLeftCorner = new PatternPoint(0, 0);
    cellRules = new CellRules();
    showGridLines = true;
  }

  public List<String> getComments() {
    return comments;
  }

  public void addComment(final String comment) {
    comments.add(comment);
  }

  public String getPatternName() {
    return patternName;
  }

  public void setPatternName(final String patternName) {
    this.patternName = patternName;
  }

  public String getAuthorInformation() {
    return authorInformation;
  }

  public void setAuthorInformation(final String authorInformation) {
    this.authorInformation = authorInformation;
  }

  public PatternPoint getTopLeftCorner() {
    return topLeftCorner;
  }

  public void setTopLeftCorner(final PatternPoint topLeftCorner) {
    this.topLeftCorner = topLeftCorner;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(final int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(final int height) {
    this.height = height;
  }

  public CellRules getCellRules() {
    return cellRules;
  }

  public void setRules(final CellRules cellRules) {
    this.cellRules = (CellRules) cellRules.clone();
  }

  public Cell[][] getCells() {
    return cells == null ? cells : createCellCopy(cells);
  }

  public void setCells(final Cell[][] cells) {
    this.cells = createCellCopy(cells);
  }
  
  private Cell[][] createCellCopy(final Cell[][] cellsToCopy) {
    final Cell[][] copyCells = new Cell[cellsToCopy.length][cellsToCopy[0].length];

    for (var x = 0; x < cellsToCopy.length; x++) {
      for (var y = 0; y < cellsToCopy[0].length; y++) {
        if (cellsToCopy[x][y] != null) {
          copyCells[x][y] = (Cell) cellsToCopy[x][y].clone();
        }
      }
    }

    return copyCells;
  }

  public boolean getShowGridLines() {
    return showGridLines;
  }

  public void setShowGridLines(final boolean showGridLines) {
    this.showGridLines = showGridLines;
  }
}
