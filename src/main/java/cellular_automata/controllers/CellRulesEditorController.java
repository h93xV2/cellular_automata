package cellular_automata.controllers;

import cellular_automata.cells.rules.CellRules;
import cellular_automata.cells.rules.NeighborCounts;
import cellular_automata.cells.rules.NumberField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CellRulesEditorController extends Controller {
  @FXML
  private NumberField birthNeighbors;
  @FXML
  private NumberField survivalNeighbors;
  @FXML
  private Button apply;
  @FXML
  private Button cancel;
  
  private ControllerData shareableData;

  public void initialize() {
    shareableData = new ControllerData(new CellRules());

    apply.setOnAction(event -> {
      copyFieldEntriesToShareableData();

      closeStage(event);
    });

    cancel.setOnAction(this::closeStage);
  }

  @Override
  public ControllerData getShareableData() {
    return shareableData;
  }

  @Override
  public void setShareableData(final ControllerData data) {
    final NeighborCounts neighborsRequiredForBirth = data.cellRules().getNeighborsRequiredForBirth();
    final NeighborCounts neighborsRequiredForSurvival = data.cellRules().getNeighborsRequiredForSurvival();

    birthNeighbors.setText(neighborsRequiredForBirth.toString());
    survivalNeighbors.setText(neighborsRequiredForSurvival.toString());

    copyFieldEntriesToShareableData();
  }

  private void copyFieldEntriesToShareableData() {
    shareableData.cellRules().clearBirthNeighborCounts();
    shareableData.cellRules().clearSurvivalNeighborCounts();

    final String birthNeighborString = birthNeighbors.getText();

    for (var i = 0; i < birthNeighborString.length(); i++) {
      shareableData.cellRules().addBirthNeighborCount(Integer.parseInt(birthNeighborString.substring(i, i + 1)));
    }

    final String survivalNeighborString = survivalNeighbors.getText();

    for (var j = 0; j < survivalNeighborString.length(); j++) {
      shareableData.cellRules()
          .addSurvivalNeighborCount(Integer.parseInt(survivalNeighborString.substring(j, j + 1)));
    }
  }

  private void closeStage(final ActionEvent event) {
    // From https://stackoverflow.com/a/11476162
    Node source = (Node) event.getSource();
    Stage stage = (Stage) source.getScene().getWindow();
    stage.close();
  }
}
