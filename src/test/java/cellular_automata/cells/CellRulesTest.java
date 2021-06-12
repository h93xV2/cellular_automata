package cellular_automata.cells;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cellular_automata.cells.rules.CellRules;

public class CellRulesTest {
  @Test
  void birthNeighborSizeIsOne() {
    final CellRules cellRules = new CellRules();

    assertEquals(1, cellRules.getTotalBirthNeighborCounts());
  }

  @Test
  void neighborsRequiredForBirthIsThree() {
    final CellRules cellRules = new CellRules();

    assertTrue(cellRules.isCountWithinBirthSet(3));
  }

  @Test
  void survivalNeighborSizeIsTwo() {
    final CellRules cellRules = new CellRules();

    assertEquals(2, cellRules.getTotalSurvivalNeighborCounts());
  }

  @Test
  void liveNeighborsRequiredForSurvivalIsTwoOrThree() {
    final CellRules cellRules = new CellRules();

    assertTrue(cellRules.isCountWithinSurvivalSet(2) && cellRules.isCountWithinSurvivalSet(3));
  }

  @Test
  void addBirthNeighborCount() {
    final CellRules cellRules = new CellRules();
    cellRules.addBirthNeighborCount(10);

    assertTrue(cellRules.isCountWithinBirthSet(10));
  }

  @Test
  void addSurvivalNeighborCount() {
    final CellRules cellRules = new CellRules();
    cellRules.addSurvivalNeighborCount(13);

    assertTrue(cellRules.isCountWithinSurvivalSet(13));
  }

  @Test
  void birthCountsCanBeCleared() {
    final CellRules cellRules = new CellRules();
    cellRules.clearBirthNeighborCounts();

    assertEquals(0, cellRules.getTotalBirthNeighborCounts());
  }

  @Test
  void survivalCountsCanBeCleared() {
    final CellRules cellRules = new CellRules();
    cellRules.clearSurvivalNeighborCounts();

    assertEquals(0, cellRules.getTotalSurvivalNeighborCounts());
  }
}
