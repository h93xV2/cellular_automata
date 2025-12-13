package cellular_automata.cells;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Generations {
  private static final String GENERATIONS_PROPERTY_NAME = "generations";
  private static final int MINIMUM_GENERATIONS = 0;
  private int generations;
  private final PropertyChangeSupport changeSupport;

  public Generations() {
    changeSupport = new PropertyChangeSupport(this);
  }

  public void addGenerationsChangeListener(final PropertyChangeListener listener) {
    changeSupport.addPropertyChangeListener(listener);
  }

  public void removeGenerationsChangeListener(final PropertyChangeListener listener) {
    changeSupport.addPropertyChangeListener(listener);
  }

  public void increment() {
    final int oldGenerations = generations;
    generations++;
    changeSupport.firePropertyChange(GENERATIONS_PROPERTY_NAME, oldGenerations, generations);
  }

  public void decrement() {
    if (MINIMUM_GENERATIONS < generations) {
      final int oldGenerations = generations;
      generations--;
      changeSupport.firePropertyChange(GENERATIONS_PROPERTY_NAME, oldGenerations, generations);
    }
  }

  public void reset() {
    final int oldGenerations = generations;
    generations = 0;
    changeSupport.firePropertyChange(GENERATIONS_PROPERTY_NAME, oldGenerations, generations);
  }

  public int getValue() {
    return generations;
  }
}
