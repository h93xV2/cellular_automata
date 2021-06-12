package cellular_automata.cells.rules;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CellRulesEditor extends Dialog<Void> {
  private static final String cancelCloseText = "Cancel";
  private static final String applyText = "Apply";
  private static final int spacing = 5;

  private final CellRules rules;
  private final ButtonType apply;
  private final NumberField birthNeighbors;
  private final NumberField survivalNeighbors;

  public CellRulesEditor(final CellRules rules) {
    this.rules = rules;

    setTitle("Edit cell rules");

    final ButtonType close = new ButtonType(cancelCloseText, ButtonData.CANCEL_CLOSE);
    apply = new ButtonType(applyText, ButtonData.APPLY);

    getDialogPane().getButtonTypes().addAll(close, apply);

    final HBox birthControls = new HBox(spacing);
    final Label birthLabel = new Label("Birth neighbors");
    birthNeighbors = new NumberField();
    birthControls.getChildren().addAll(birthLabel, birthNeighbors);

    final HBox survivalControls = new HBox(spacing);
    final Label survivalLabel = new Label("Survival neighbors");
    survivalNeighbors = new NumberField();
    survivalControls.getChildren().addAll(survivalLabel, survivalNeighbors);

    final VBox controls = new VBox(spacing);
    controls.getChildren().addAll(birthControls, survivalControls);

    getDialogPane().setContent(controls);

    setOnShowing(this::prepBeforeShowing);
    setResultConverter(this::handleResults);
  }

  private Void handleResults(final ButtonType buttonType) {
    if (buttonType.equals(apply)) {
      rules.clearBirthNeighborCounts();
      rules.clearSurvivalNeighborCounts();

      final String birthNeighborString = birthNeighbors.getText();

      for (var i = 0; i < birthNeighborString.length(); i++) {
        rules.addBirthNeighborCount(Integer.parseInt(birthNeighborString.substring(i, i + 1)));
      }

      final String survivalNeighborString = survivalNeighbors.getText();

      for (var j = 0; j < survivalNeighborString.length(); j++) {
        rules.addSurvivalNeighborCount(Integer.parseInt(survivalNeighborString.substring(j, j + 1)));
      }
    }

    return null;
  }

  private void prepBeforeShowing(final DialogEvent event) {
    birthNeighbors.setText(rules.getNeighborsRequiredForBirth().toString());
    survivalNeighbors.setText(rules.getNeighborsRequiredForSurvival().toString());
  }
}
