package cellular_automata.controllers;

import javafx.stage.Stage;

public abstract class Controller {
  private ControllerData shareableData;
  private Stage stage;

  public ControllerData getShareableData() {
    return shareableData;
  }

  public void setShareableData(final ControllerData shareableData) {
    this.shareableData = shareableData;
  }

  public Stage getStage() {
    return stage;
  }

  public void setStage(final Stage stage) {
    this.stage = stage;
  }
}
