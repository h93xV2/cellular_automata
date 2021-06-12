package cellular_automata.cells.rules;

import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class NumberField extends TextField {
  private static final char minimumDigit = '0';
  private static final char maximumDigit = '8';

  public NumberField() {
    super();

    addEventFilter(KeyEvent.KEY_TYPED, this::filterText);
  }

  private void filterText(final KeyEvent event) {
    final KeyCode eventCode = event.getCode();
    final boolean validKeyCode = eventCode.equals(KeyCode.BACK_SPACE) || eventCode.equals(KeyCode.LEFT)
        || eventCode.equals(KeyCode.RIGHT);

    if (!validKeyCode) {
      final char[] chars = event.getCharacter().toCharArray();

      for (var c : chars) {
        if (c < minimumDigit || c > maximumDigit) {
          event.consume();
        }
      }
    }
  }

  @Override
  public void paste() {
    final String textToPaste = Clipboard.getSystemClipboard().getString();
    final char[] chars = textToPaste.toCharArray();

    for (var c : chars) {
      if (c < '0' || c > '9') {
        return;
      }
    }

    replaceSelection(textToPaste);
  }
}
