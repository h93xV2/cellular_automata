package cellular_automata.fxml;

import java.io.IOException;
import java.net.URL;

import static cellular_automata.alerts.Alerts.notifyNonRecoverableError;
import cellular_automata.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class FxmlLoader {
  private Stage stage;

  public FxmlLoader(final Stage stage) {
    this.stage = stage;
  }

  public Controller loadFxml(final String resourcePath) {
    URL fxmlUrl = getClass().getResource(resourcePath);
    if (fxmlUrl == null) {
        throw new IllegalStateException(
            "FXML resource not found: " + resourcePath
        );
    }

    final Controller controller = connectStageAndController(stage, fxmlUrl);

    return controller;
  }

  private Controller connectStageAndController(final Stage stage, final URL fxmlUrl) {
    Pair<Scene, Controller> sceneAndController = null;

    try {
      sceneAndController = buildScene(fxmlUrl);

      stage.setScene(sceneAndController.getKey());
    } catch (IOException exception) {
      notifyNonRecoverableError("An error occurred while building the graphical user interface.", exception);
      throw new RuntimeException(exception);
    }

    final Controller controller = sceneAndController.getValue();
    controller.setStage(stage);

    return controller;
  }

  private Pair<Scene, Controller> buildScene(final URL fxmlUrl) throws IOException {
    final FXMLLoader loader = new FXMLLoader(fxmlUrl);
    final BorderPane container = loader.<BorderPane>load();
    final Scene scene = new Scene(container);

    return new Pair<>(scene, loader.getController());
  }

  public Stage getStage() {
    return stage;
  }
}
