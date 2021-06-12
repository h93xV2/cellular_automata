package cellular_automata.graphics;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.scene.control.Label;

public class GenerationsLabel extends Label implements PropertyChangeListener {
  @Override
  public void propertyChange(final PropertyChangeEvent event) {
    setText(((Integer) event.getNewValue()).toString());
  }
}
