package cellular_automata.persistence.rle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cellular_automata.cells.BirthAndSurvivalConstraints;
import cellular_automata.cells.Cell;
import cellular_automata.cells.CellState;
import javafx.util.Pair;

public class RunLengthEncodedDataTest {
  @Test
  void commentsAreEmpty() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    assertTrue(data.getComments().isEmpty());
  }

  @Test
  void patternNameIsNull() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    assertNull(data.getPatternName());
  }

  @Test
  void authorInformationIsNull() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    assertNull(data.getAuthorInformation());
  }

  @Test
  void topLeftCornerIsZeroedOut() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    assertEquals(new Pair<Integer, Integer>(0, 0), data.getTopLeftCorner());
  }

  @Test
  void widthIsZero() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    assertEquals(0, data.getWidth());
  }

  @Test
  void heightIsZero() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    assertEquals(0, data.getHeight());
  }

  @Test
  void commentCanBeAdded() {
    final RunLengthEncodedData data = new RunLengthEncodedData();
    final String comment = "hello world";

    data.addComment(comment);

    assertEquals(comment, data.getComments().get(0));
  }

  @Test
  void patternNameCanBeSet() {
    final RunLengthEncodedData data = new RunLengthEncodedData();
    final String name = "12345";

    data.setPatternName(name);

    assertEquals(name, data.getPatternName());
  }

  @Test
  void authorInformationCanBeSet() {
    final RunLengthEncodedData data = new RunLengthEncodedData();
    final String info = "hello";

    data.setAuthorInformation(info);

    assertEquals(info, data.getAuthorInformation());
  }

  @Test
  void topLeftCornerCanBeSet() {
    final RunLengthEncodedData data = new RunLengthEncodedData();
    final Pair<Integer, Integer> toSet = new Pair<Integer, Integer>(3, 5);

    data.setTopLeftCorner(toSet);

    assertEquals(new Pair<Integer, Integer>(3, 5), data.getTopLeftCorner());
  }

  @Test
  void widthCanBeSet() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    data.setWidth(10);

    assertEquals(10, data.getWidth());
  }

  @Test
  void heightCanBeSet() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    data.setHeight(15);

    assertEquals(15, data.getHeight());
  }

  @Test
  void gitBirthAndSurvivalConstraints() {
    final RunLengthEncodedData data = new RunLengthEncodedData();
    final BirthAndSurvivalConstraints constraints = data.getBirthAndSurvivalConstraints();

    assertTrue(constraints.getLiveNeighborsRequiredForBirth().isEmpty()
        && constraints.getLiveNeighborsRequiredForSurvival().isEmpty());
  }

  @Test
  void birthConstraintsCanBeSet() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
    constraints.addBirthNeighborCount(20);

    final RunLengthEncodedData data = new RunLengthEncodedData();
    data.setBirthAndSurvivalConstraints(constraints);

    assertEquals(20, data.getBirthAndSurvivalConstraints().getLiveNeighborsRequiredForBirth().get(0));
  }

  @Test
  void survivalConstraintsCanBeSet() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
    constraints.addSurvivalNeighborCount(7);

    final RunLengthEncodedData data = new RunLengthEncodedData();
    data.setBirthAndSurvivalConstraints(constraints);

    assertEquals(7, data.getBirthAndSurvivalConstraints().getLiveNeighborsRequiredForSurvival().get(0));
  }

  @Test
  void cellsAreNullByDefault() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    assertNull(data.getCells());
  }

  @Test
  void cellsCanBeSet() {
    final RunLengthEncodedData data = new RunLengthEncodedData();
    data.setCells(new Cell[1][1]);

    assertNotNull(data.getCells());
  }

  @Test
  void setCellsAreNotReferencedElsewhere() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    final Cell[][] cells = new Cell[1][1];
    cells[0][0] = new Cell();

    data.setCells(cells);

    cells[0][0].toggleState();

    assertEquals(CellState.DEAD, data.getCells()[0][0].getState());
  }

  @Test
  void encapsulatedCellsCannotBeMutated() {
    final RunLengthEncodedData data = new RunLengthEncodedData();

    final Cell[][] cells = new Cell[1][1];
    cells[0][0] = new Cell();

    data.setCells(cells);

    data.getCells()[0][0].toggleState();

    assertEquals(CellState.DEAD, data.getCells()[0][0].getState());
  }
}
