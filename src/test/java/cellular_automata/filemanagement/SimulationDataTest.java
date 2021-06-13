package cellular_automata.filemanagement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cellular_automata.cells.Cell;
import cellular_automata.cells.CellState;
import cellular_automata.cells.rules.CellRules;

public class SimulationDataTest {
  @Test
  void cellsAreNullByDefault() {
    final SimulationData data = new SimulationData();

    assertNull(data.getCells());
  }

  @Test
  void showGridLinesCanBeSet() {
    final SimulationData data = new SimulationData();

    data.setShowGridLines(true);

    assertTrue(data.getShowGridLines());
  }

  @Test
  void cellsCanBeSet() {
    final SimulationData data = new SimulationData();

    data.setCells(new Cell[1][1]);

    assertNotNull(data.getCells());
  }

  @Test
  void simulationDataCanBeInitializedWithObjects() {
    final SimulationData data = new SimulationData(new Cell[1][1], true, new CellRules());

    assertTrue(data.getCells() != null && data.getShowGridLines());
  }

  @Test
  void commentsAreEmpty() {
    final SimulationData data = new SimulationData();

    assertTrue(data.getComments().isEmpty());
  }

  @Test
  void patternNameIsNull() {
    final SimulationData data = new SimulationData();

    assertNull(data.getPatternName());
  }

  @Test
  void authorInformationIsNull() {
    final SimulationData data = new SimulationData();

    assertNull(data.getAuthorInformation());
  }

  @Test
  void topLeftCornerIsZeroedOut() {
    final SimulationData data = new SimulationData();

    assertEquals(new PatternPoint(0, 0), data.getTopLeftCorner());
  }

  @Test
  void widthIsZero() {
    final SimulationData data = new SimulationData();

    assertEquals(0, data.getWidth());
  }

  @Test
  void heightIsZero() {
    final SimulationData data = new SimulationData();

    assertEquals(0, data.getHeight());
  }

  @Test
  void commentCanBeAdded() {
    final SimulationData data = new SimulationData();
    final String comment = "hello world";

    data.addComment(comment);

    assertEquals(comment, data.getComments().get(0));
  }

  @Test
  void patternNameCanBeSet() {
    final SimulationData data = new SimulationData();
    final String name = "12345";

    data.setPatternName(name);

    assertEquals(name, data.getPatternName());
  }

  @Test
  void authorInformationCanBeSet() {
    final SimulationData data = new SimulationData();
    final String info = "hello";

    data.setAuthorInformation(info);

    assertEquals(info, data.getAuthorInformation());
  }

  @Test
  void topLeftCornerCanBeSet() {
    final SimulationData data = new SimulationData();
    final PatternPoint toSet = new PatternPoint(3, 5);

    data.setTopLeftCorner(toSet);

    assertEquals(new PatternPoint(3, 5), data.getTopLeftCorner());
  }

  @Test
  void widthCanBeSet() {
    final SimulationData data = new SimulationData();

    data.setWidth(10);

    assertEquals(10, data.getWidth());
  }

  @Test
  void heightCanBeSet() {
    final SimulationData data = new SimulationData();

    data.setHeight(15);

    assertEquals(15, data.getHeight());
  }

  @Test
  void setCellsAreNotReferencedElsewhere() {
    final SimulationData data = new SimulationData();

    final Cell[][] cells = new Cell[1][1];
    cells[0][0] = new Cell();

    data.setCells(cells);

    cells[0][0].toggleState();

    assertEquals(CellState.DEAD, data.getCells()[0][0].getState());
  }

  @Test
  void encapsulatedCellsCannotBeMutated() {
    final SimulationData data = new SimulationData();

    final Cell[][] cells = new Cell[1][1];
    cells[0][0] = new Cell();

    data.setCells(cells);

    data.getCells()[0][0].toggleState();

    assertEquals(CellState.DEAD, data.getCells()[0][0].getState());
  }

  @Test
  void showGridLinesIsTrueByDefault() {
    final SimulationData data = new SimulationData();

    assertTrue(data.getShowGridLines());
  }

  @Test
  void birthAndSurvivalRulesCanBeSet() {
    final SimulationData data = new SimulationData();
    final CellRules cellRules = new CellRules();
    cellRules.clearBirthNeighborCounts();

    assertEquals(0, cellRules.getTotalBirthNeighborCounts());

    cellRules.addBirthNeighborCount(5);
    
    assertEquals(1, cellRules.getTotalBirthNeighborCounts());
    
    data.setRules(cellRules);

    assertTrue(data.getCellRules().isCountWithinBirthSet(5));
    assertEquals(1, data.getCellRules().getTotalBirthNeighborCounts());
  }
}
