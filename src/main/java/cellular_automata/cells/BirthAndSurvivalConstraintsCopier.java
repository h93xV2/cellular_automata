package cellular_automata.cells;

public class BirthAndSurvivalConstraintsCopier {
  private BirthAndSurvivalConstraintsCopier() {
  }

  public static void copy(final BirthAndSurvivalConstraints source, final BirthAndSurvivalConstraints destination) {
    destination.clearBirthNeighborCounts();
    destination.clearSurvivalNeighborCounts();

    for (Integer birthCount : source.getBirthNeighborsCounts()) {
      destination.addBirthNeighborCount(birthCount);
    }
    
    for (Integer survivalCount : source.getSurvivalNeighborCounts()) {
      destination.addSurvivalNeighborCount(survivalCount);
    }
  }
}
