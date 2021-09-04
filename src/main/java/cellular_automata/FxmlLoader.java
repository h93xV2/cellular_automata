package cellular_automata;

import static cellular_automata.alerts.Alerts.notifyNonRecoverableError;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import cellular_automata.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public interface FxmlLoader {
  public default Controller loadFxml(final Stage stage, final String filePath) {
    final ClassLoader classLoader = getClass().getClassLoader();
    final File fxmlFile = new File(classLoader.getResource(filePath).getFile());
    final Controller controller = setStage(stage, fxmlFile);

    return controller;
  }

  private Controller setStage(final Stage stage, final File fxmlFile) {
    Pair<Scene, Controller> sceneAndController = null;

    try {
      sceneAndController = buildScene(fxmlFile);

      stage.setScene(sceneAndController.getKey());
    } catch (IOException exception) {
      notifyNonRecoverableError("An error occurred while building the graphical user interface.", exception);
    }

    return sceneAndController.getValue();
  }

  private Pair<Scene, Controller> buildScene(final File fxmlFile) throws IOException {
    final URL fxmlUrl = fxmlFile.toURI().toURL();
    final FXMLLoader loader = new FXMLLoader(fxmlUrl);
    final BorderPane container = loader.<BorderPane>load();
    final Scene scene = new Scene(container);

    return new Pair<>(scene, loader.getController());
  }
}
