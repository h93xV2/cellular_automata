package cellular_automata.alerts;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

public interface Alertable {
  public default void notifyNonRecoverableError(final String message, final Exception e) {
    final Alert errorAlert = buildErrorAlertWithTextArea(message + "\n" + e.toString(), ButtonType.CLOSE);
    errorAlert.showAndWait();
    System.exit(1);
  }

  public default void notifyRecoverableError(final String message) {
    final Alert errorAlert = new Alert(AlertType.ERROR, message, ButtonType.CLOSE);
    errorAlert.showAndWait();
  }

  public default void notifyRecoverableError(final String message, final Exception e) {
    final Alert errorAlert = buildErrorAlertWithTextArea(message + "\n" + e.toString(), ButtonType.OK);
    errorAlert.showAndWait();
  }

  private Alert buildErrorAlertWithTextArea(final String message, final ButtonType buttonType) {
    final TextArea errorText = new TextArea(message);
    errorText.setEditable(false);
    errorText.setWrapText(false);

    final Alert errorAlert = new Alert(AlertType.ERROR);
    errorAlert.getDialogPane().setContent(errorText);

    return errorAlert;
  }
}
