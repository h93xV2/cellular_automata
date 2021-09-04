package cellular_automata;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class CellularAutomataApp extends Application implements FxmlLoader {
  private static Stage primaryStage;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(final Stage stage) {
    stage.setOnHiding(event -> Platform.exit());
    stage.setResizable(false);

    setPrimaryStage(stage);

    loadFxml(stage, "fxml/main.xml");
    
    stage.show();
  }

  static Stage getPrimaryStage() {
    return primaryStage;
  }

  private void setPrimaryStage(final Stage primaryStage) {
    CellularAutomataApp.primaryStage = primaryStage;
  }
}
