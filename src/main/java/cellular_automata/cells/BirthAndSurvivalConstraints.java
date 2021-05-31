package cellular_automata.cells;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "totalBirthNeighborCounts", "totalSurvivalNeighborCounts" })
public class BirthAndSurvivalConstraints {
  private Set<Integer> neighborsRequiredForBirth;
  private Set<Integer> neighborsRequiredForSurvival;

  public BirthAndSurvivalConstraints() {
    neighborsRequiredForBirth = new HashSet<>(1);
    neighborsRequiredForBirth.add(3);

    neighborsRequiredForSurvival = new HashSet<>(2);
    neighborsRequiredForSurvival.add(2);
    neighborsRequiredForSurvival.add(3);

  }

  public void addBirthNeighborCount(final int neighbors) {
    neighborsRequiredForBirth.add(neighbors);
  }

  public void addSurvivalNeighborCount(final int neighbors) {
    neighborsRequiredForSurvival.add(neighbors);
  }

  public void clearBirthNeighborCounts() {
    neighborsRequiredForBirth.clear();
  }

  public void clearSurvivalNeighborCounts() {
    neighborsRequiredForSurvival.clear();
  }

  public int getTotalBirthNeighborCounts() {
    return neighborsRequiredForBirth.size();
  }

  public int getTotalSurvivalNeighborCounts() {
    return neighborsRequiredForSurvival.size();
  }

  public boolean isCountWithinBirthSet(final int neighborCount) {
    return neighborsRequiredForBirth.contains(neighborCount);
  }

  public boolean isCountWithinSurvivalSet(final int neighborCount) {
    return neighborsRequiredForSurvival.contains(neighborCount);
  }
  
  Set<Integer> getBirthNeighborsCounts() {
    return Collections.unmodifiableSet(neighborsRequiredForBirth);
  }
  
  Set<Integer> getSurvivalNeighborCounts() {
    return Collections.unmodifiableSet(neighborsRequiredForSurvival);
  }
}
