package cellular_automata;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CellularAutomataApp extends Application {
  private static Stage primaryStage;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(final Stage stage) {
    setPrimaryStage(stage);

    final ClassLoader classLoader = getClass().getClassLoader();
    final File fxmlFile = new File(classLoader.getResource("fxml/main.xml").getFile());

    try {
      final URL fxmlUrl = fxmlFile.toURI().toURL();
      final FXMLLoader loader = new FXMLLoader(fxmlUrl);
      final VBox vbox = loader.<VBox>load();
      final Scene scene = new Scene(vbox);

      stage.setScene(scene);
      stage.setResizable(false);
      stage.show();
    } catch (IllegalArgumentException | IOException exception) {
      AlertMediator.notifyNonRecoverableError("An error occurred while building the graphical user interface.");
    }
  }

  static Stage getPrimaryStage() {
    return primaryStage;
  }

  private void setPrimaryStage(final Stage primaryStage) {
    CellularAutomataApp.primaryStage = primaryStage;
  }
}
