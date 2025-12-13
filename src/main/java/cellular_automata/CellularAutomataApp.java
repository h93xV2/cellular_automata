package cellular_automata;

import cellular_automata.fxml.FxmlLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class CellularAutomataApp extends Application {
  private FxmlLoader loader;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(final Stage stage) {
    stage.setOnHiding(event -> Platform.exit());
    stage.setResizable(false);

    loader = new FxmlLoader(stage);
    loader.loadFxml("/fxml/main.fxml");

    stage.show();
  }
}
