package cellular_automata.cells;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BirthAndSurvivalConstraintsTest {
  @Test
  void birthNeighborSizeIsOne() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();

    assertEquals(1, constraints.getTotalBirthNeighborCounts());
  }

  @Test
  void neighborsRequiredForBirthIsThree() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();

    assertTrue(constraints.isCountWithinBirthSet(3));
  }

  @Test
  void survivalNeighborSizeIsTwo() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();

    assertEquals(2, constraints.getTotalSurvivalNeighborCounts());
  }

  @Test
  void liveNeighborsRequiredForSurvivalIsTwoOrThree() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();

    assertTrue(constraints.isCountWithinSurvivalSet(2) && constraints.isCountWithinSurvivalSet(3));
  }

  @Test
  void addBirthNeighborCount() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
    constraints.addBirthNeighborCount(10);

    assertTrue(constraints.isCountWithinBirthSet(10));
  }

  @Test
  void addSurvivalNeighborCount() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
    constraints.addSurvivalNeighborCount(13);

    assertTrue(constraints.isCountWithinSurvivalSet(13));
  }

  @Test
  void birthCountsCanBeCleared() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
    constraints.clearBirthNeighborCounts();

    assertEquals(0, constraints.getTotalBirthNeighborCounts());
  }

  @Test
  void survivalCountsCanBeCleared() {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
    constraints.clearSurvivalNeighborCounts();

    assertEquals(0, constraints.getTotalSurvivalNeighborCounts());
  }
}
