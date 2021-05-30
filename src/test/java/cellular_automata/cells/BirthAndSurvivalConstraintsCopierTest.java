package cellular_automata.cells;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class BirthAndSurvivalConstraintsCopierTest {
  private BirthAndSurvivalConstraints destination;

  @BeforeAll
  void init() {
    final BirthAndSurvivalConstraints source = new BirthAndSurvivalConstraints();
    source.clearBirthNeighborCounts();
    source.clearSurvivalNeighborCounts();
    source.addBirthNeighborCount(13);
    source.addSurvivalNeighborCount(5);

    destination = new BirthAndSurvivalConstraints();

    BirthAndSurvivalConstraintsCopier.copy(source, destination);
  }

  @Test
  void birthCountSizeIsOne() {
    assertEquals(1, destination.getTotalBirthNeighborCounts());
  }

  @Test
  void survivalCountSizeIsOne() {
    assertEquals(1, destination.getTotalSurvivalNeighborCounts());
  }

  @Test
  void birthCountsContainCorrectValue() {
    assertTrue(destination.isCountWithinBirthSet(13));
  }

  @Test
  void survivalCountContainsCorrectValue() {
    assertTrue(destination.isCountWithinSurvivalSet(5));
  }
}
