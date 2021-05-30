package cellular_automata.filemanagement;

import java.util.ArrayList;
import java.util.List;

import cellular_automata.cells.BirthAndSurvivalConstraints;
import cellular_automata.cells.BirthAndSurvivalConstraintsCopier;
import cellular_automata.cells.Cell;
import cellular_automata.cells.CellArrayCopier;

public class SimulationData {
  private List<String> comments;
  private String patternName;
  private String authorInformation;
  private PatternPoint topLeftCorner;
  private BirthAndSurvivalConstraints constraints;
  private int width;
  private int height;
  private Cell[][] cells;
  private boolean showGridLines;

  public SimulationData(final Cell[][] cells, final boolean showGridLines) {
    this();
    setCells(cells);
    this.showGridLines = showGridLines;
  }

  public SimulationData() {
    comments = new ArrayList<>();
    topLeftCorner = new PatternPoint(0, 0);
    constraints = new BirthAndSurvivalConstraints();
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

  public BirthAndSurvivalConstraints getBirthAndSurvivalConstraints() {
    return constraints;
  }

  public void setBirthAndSurvivalConstraints(final BirthAndSurvivalConstraints constraints) {
    BirthAndSurvivalConstraintsCopier.copy(constraints, this.constraints);
  }

  public Cell[][] getCells() {
    return cells == null ? cells : CellArrayCopier.createCopy(cells);
  }

  public void setCells(final Cell[][] cells) {
    this.cells = CellArrayCopier.createCopy(cells);
  }

  public boolean getShowGridLines() {
    return showGridLines;
  }

  public void setShowGridLines(final boolean showGridLines) {
    this.showGridLines = showGridLines;
  }
}
