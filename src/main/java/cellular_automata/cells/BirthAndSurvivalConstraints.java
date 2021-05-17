package cellular_automata.cells;

import java.util.ArrayList;
import java.util.List;

public class BirthAndSurvivalConstraints {
  private List<Integer> neighborsRequiredForBirth;
  private List<Integer> neighborsRequiredForSurvival;

  public BirthAndSurvivalConstraints() {
    neighborsRequiredForBirth = new ArrayList<>();
    neighborsRequiredForSurvival = new ArrayList<>();
  }

  public void addBirthNeighborCount(final int neighbors) {
    neighborsRequiredForBirth.add(neighbors);
  }

  public void addSurvivalNeighborCount(final int neighbors) {
    neighborsRequiredForSurvival.add(neighbors);
  }

  public List<Integer> getLiveNeighborsRequiredForBirth() {
    return neighborsRequiredForBirth;
  }

  public List<Integer> getLiveNeighborsRequiredForSurvival() {
    return neighborsRequiredForSurvival;
  }
}
