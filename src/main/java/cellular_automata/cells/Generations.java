package cellular_automata.cells;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Generations {
  private static final String generationsPropertyName = "generations";
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
    changeSupport.firePropertyChange(generationsPropertyName, oldGenerations, generations);
  }

  public void reset() {
    final int oldGenerations = generations;
    generations = 0;
    changeSupport.firePropertyChange(generationsPropertyName, oldGenerations, generations);
  }

  public int getValue() {
    return generations;
  }
}
