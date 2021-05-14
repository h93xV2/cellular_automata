package cellular_automata;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertMediator {
	public static void notifyNonRecoverableError(final String message) {
		// TODO: Log the detailed exception information somewhere.
		final Alert errorAlert = new Alert(AlertType.ERROR, message, ButtonType.CLOSE);
		errorAlert.setAlertType(AlertType.ERROR);
		errorAlert.showAndWait();
		System.exit(1);
	}

	public static void notifyRecoverableError(final String message) {
		final Alert errorAlert = new Alert(AlertType.ERROR, message, ButtonType.OK);
		errorAlert.setAlertType(AlertType.ERROR);
		errorAlert.showAndWait();
	}
}
