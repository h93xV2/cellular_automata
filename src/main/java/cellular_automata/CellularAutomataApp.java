package cellular_automata;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import cellular_automata.alerts.Alertable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CellularAutomataApp extends Application implements Alertable {
  private static Stage primaryStage;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(final Stage stage) {
    setPrimaryStage(stage);

    final ClassLoader classLoader = getClass().getClassLoader();
    final File fxmlFile = new File(classLoader.getResource("fxml/main.xml").getFile());

    setStageAndShow(stage, fxmlFile);
  }
  
  private void setStageAndShow(final Stage stage, final File fxmlFile) {
    try {
      stage.setScene(buildScene(fxmlFile));
      stage.setResizable(false);
      stage.show();
    } catch (IOException exception) {
      notifyNonRecoverableError("An error occurred while building the graphical user interface.",
          exception);
    }
  }
  
  private Scene buildScene(final File fxmlFile) throws IOException {
    final URL fxmlUrl = fxmlFile.toURI().toURL();
    final FXMLLoader loader = new FXMLLoader(fxmlUrl);
    final BorderPane container = loader.<BorderPane>load();
    final Scene scene = new Scene(container);
    
    return scene;
  }

  static Stage getPrimaryStage() {
    return primaryStage;
  }

  private void setPrimaryStage(final Stage primaryStage) {
    CellularAutomataApp.primaryStage = primaryStage;
  }
}
