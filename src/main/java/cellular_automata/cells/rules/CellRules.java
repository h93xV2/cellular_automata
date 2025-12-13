package cellular_automata.cells.rules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "totalBirthNeighborCounts", "totalSurvivalNeighborCounts" })
public class CellRules {
  private NeighborCounts neighborsRequiredForBirth;
  private NeighborCounts neighborsRequiredForSurvival;

  public CellRules() {
    neighborsRequiredForBirth = new NeighborCounts(1);
    neighborsRequiredForBirth.add(3);

    this.neighborsRequiredForSurvival = new NeighborCounts(2);
    neighborsRequiredForSurvival.add(2);
    neighborsRequiredForSurvival.add(3);

  }

  public void addBirthNeighborCount(final Integer neighbors) {
    neighborsRequiredForBirth.add(neighbors);
  }

  public void addSurvivalNeighborCount(final Integer neighbors) {
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

  @Override
  public Object clone() throws CloneNotSupportedException {
    final CellRules newCopy = new CellRules();
    newCopy.clearBirthNeighborCounts();
    newCopy.clearSurvivalNeighborCounts();

    for (var birthCount : neighborsRequiredForBirth) {
      newCopy.addBirthNeighborCount(birthCount);
    }

    for (var survivalCount : neighborsRequiredForSurvival) {
      newCopy.addSurvivalNeighborCount(survivalCount);
    }

    return newCopy;
  }

  @Override
  public String toString() {
    final StringBuilder stringVersion = new StringBuilder();
    stringVersion.append("B");
    stringVersion.append(neighborsRequiredForBirth.toString());
    stringVersion.append("/S");
    stringVersion.append(neighborsRequiredForSurvival.toString());

    return stringVersion.toString();
  }

  public NeighborCounts getNeighborsRequiredForBirth() {
    return neighborsRequiredForBirth;
  }

  public void setNeighborsRequiredForBirth(final NeighborCounts neighborsRequiredForBirth) {
    this.neighborsRequiredForBirth = neighborsRequiredForBirth;
  }

  public NeighborCounts getNeighborsRequiredForSurvival() {
    return neighborsRequiredForSurvival;
  }

  public void setNeighborsRequiredForSurvival(NeighborCounts neighborsRequiredForSurvival) {
    this.neighborsRequiredForSurvival = neighborsRequiredForSurvival;
  }
}
