package cellular_automata.cells.rules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NeighborCounts implements Iterable<Integer> {
  private List<Integer> neighborCounts;

  public NeighborCounts() {
    this(0);
  }

  public NeighborCounts(final int initialSize) {
    setNeighborCounts(new ArrayList<>(initialSize));
  }

  public void add(final int neighborCount) {
    if (!getNeighborCounts().contains(neighborCount)) {
      getNeighborCounts().add(neighborCount);
    }
  }

  public void clear() {
    getNeighborCounts().clear();
  }

  public int size() {
    return getNeighborCounts().size();
  }

  public boolean contains(final int neighborCount) {
    return getNeighborCounts().contains(neighborCount);
  }

  @Override
  public String toString() {
    final StringBuilder stringRepresentation = new StringBuilder();

    for (var neighborCount : getNeighborCounts()) {
      stringRepresentation.append(neighborCount);
    }

    return stringRepresentation.toString();
  }

  @Override
  public Iterator<Integer> iterator() {
    return getNeighborCounts().iterator();
  }

  public List<Integer> getNeighborCounts() {
    return neighborCounts;
  }

  public void setNeighborCounts(List<Integer> neighborCounts) {
    this.neighborCounts = neighborCounts;
  }
}
