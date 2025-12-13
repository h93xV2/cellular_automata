package cellular_automata.controllers;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import cellular_automata.SimulationLoop;
import static cellular_automata.alerts.Alerts.notifyNonRecoverableError;
import cellular_automata.cells.Generations;
import cellular_automata.filemanagement.FileSystemCoordinator;
import cellular_automata.filemanagement.data.SimulationData;
import cellular_automata.fxml.FxmlLoader;
import cellular_automata.graphics.GenerationsLabel;
import cellular_automata.graphics.board.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CellularAutomataController extends Controller {
  @FXML
  private MenuItem openFile;
  @FXML
  private MenuItem saveFile;
  @FXML
  private MenuItem toggleGridLines;
  @FXML
  private MenuItem editRules;
  @FXML
  private Board board;
  @FXML
  private Button start;
  @FXML
  private Button stop;
  @FXML
  private Button lastStep;
  @FXML
  private Button nextStep;
  @FXML
  private Button reset;
  @FXML
  private Button clear;
  @FXML
  private Slider speed;
  @FXML
  private Label rules;
  @FXML
  private GenerationsLabel generationsLabel;

  private final Properties defaultProperties;
  private final Generations generations;

  private FileSystemCoordinator fileSystem;
  private FxmlLoader editorLoader;

  public CellularAutomataController() {
    defaultProperties = new Properties();

    try (InputStream in = getClass().getResourceAsStream("/defaults.properties")) {

        if (in == null) {
            throw new IllegalStateException("defaults.properties not found");
        }

        defaultProperties.load(in);
    } catch (IOException e) {
        notifyNonRecoverableError(
            "Unable to load the default application settings.", e
        );
    }

    generations = new Generations();
  }

  public void initialize() {
    setUpBoard();
    setRuleLabelText();
    connectSimulationLoopToFileSystem(setUpAndRetrieveSimulationLoop());
    setUpGenerationsLabel();

    editRules.setOnAction(event -> {
      if (editorLoader == null)
        editorLoader = new FxmlLoader(setUpAndRetrieveEditorStage());

      final Controller editorController = getEditorController();

      editorLoader.getStage().showAndWait();

      updateBoardUsingDataFromEditor(editorController.getShareableData());
      setRuleLabelText();
    });
  }

  private void setUpBoard() {
    try {
      setUpBoardDimensions();
    } catch (NumberFormatException nfe) {
      notifyNonRecoverableError("Unable to establish the size of the window.", nfe);
    }

    setUpBoardControls();
  }

  private void setRuleLabelText() {
    rules.setText(board.getCells().getRules().toString());
  }

  private SimulationLoop setUpAndRetrieveSimulationLoop() {
    final SimulationLoop simulation = new SimulationLoop(board,
        Long.parseLong((String) defaultProperties.get("timeStep")), generations);

    setUpControlButtons(simulation);
    setupSpeedSlider(simulation);

    return simulation;
  }

  private void connectSimulationLoopToFileSystem(final SimulationLoop loop) {
    setUpOpenFile(loop);
    setUpSaveFile(loop);
  }

  private void setUpOpenFile(final SimulationLoop loop) {
    openFile.setOnAction(event -> {
      loop.stop();

      fileSystem.openFromFile().ifPresent(saveData -> {
        loop.reset();
        board.getCells().copyCellStates(saveData.getCells());
        board.getCells().copyRules(saveData.getCellRules());
        board.setShowGridLines(saveData.getShowGridLines());
        board.getCells().lockCurrentStateAsSeed();
        rules.setText(board.getCells().getRules().toString());
        board.draw();
      });
    });
  }

  private void setUpSaveFile(final SimulationLoop loop) {
    saveFile.setOnAction(event -> {
      loop.reset();

      final SimulationData saveData = new SimulationData(board.getCells().getWorkingCells(), board.getShowGridLines(),
          board.getCells().getRules());

      fileSystem.saveToFile(saveData);
    });
  }

  private void setUpGenerationsLabel() {
    generations.addGenerationsChangeListener((PropertyChangeListener) generationsLabel);
    generationsLabel.setText(Integer.toString(generations.getValue()));
  }

  private Stage setUpAndRetrieveEditorStage() {
    final Stage editorStage = new Stage();

    editorStage.setTitle("Edit cell rules");
    editorStage.initModality(Modality.APPLICATION_MODAL);

    return editorStage;
  }

  private Controller getEditorController() {
    final Controller editorController = editorLoader.loadFxml("/fxml/cellruleseditor.fxml");

    editorController.setShareableData(getShareableData());

    return editorController;
  }

  private void updateBoardUsingDataFromEditor(final ControllerData editorData) {
    board.getCells().getRules().clearBirthNeighborCounts();
    board.getCells().getRules().clearSurvivalNeighborCounts();
    board.getCells().getRules().setNeighborsRequiredForBirth(editorData.cellRules().getNeighborsRequiredForBirth());
    board.getCells().getRules()
        .setNeighborsRequiredForSurvival(editorData.cellRules().getNeighborsRequiredForSurvival());
  }

  private void setUpBoardDimensions() {
    final int boardWidth = Integer.parseInt((String) defaultProperties.get("boardWidth"));
    final int boardHeight = Integer.parseInt((String) defaultProperties.get("boardHeight"));
    final int cellWidth = Integer.parseInt((String) defaultProperties.get("cellWidth"));
    final int cellHeight = Integer.parseInt((String) defaultProperties.get("cellHeight"));

    board.setUp(boardWidth, boardHeight, cellWidth, cellHeight);
  }

  private void setUpBoardControls() {
    toggleGridLines.setOnAction(event -> {
      board.toggleGridLines();
      board.draw();
    });
  }

  private void setUpControlButtons(final SimulationLoop loop) {
    start.setOnAction(event -> loop.start());
    stop.setOnAction(event -> loop.stop());
    lastStep.setOnAction(event -> loop.last());
    nextStep.setOnAction(event -> loop.next());
    reset.setOnAction(event -> loop.reset());
    clear.setOnAction(event -> loop.clear());
  }

  private void setupSpeedSlider(final SimulationLoop loop) {
    try {
      initializeGameSpeedSlider();
    } catch (NumberFormatException nfe) {
      notifyNonRecoverableError("Unable to create the slider used to control speed.", nfe);
    }

    speed.valueProperty().addListener((observableValue, oldValue, newValue) -> {
      loop.setTimeStepMultiplier(newValue.doubleValue());
    });
  }

  private void initializeGameSpeedSlider() {
    final double minimumSpeed = Double.parseDouble((String) defaultProperties.get("minimumSpeed"));
    final double initialSpeed = Double.parseDouble((String) defaultProperties.get("initialSpeed"));
    final double maximumSpeed = Double.parseDouble((String) defaultProperties.get("maximumSpeed"));

    speed.setMin(minimumSpeed);
    speed.setValue(initialSpeed);
    speed.setMax(maximumSpeed);
  }

  @Override
  public ControllerData getShareableData() {
    return new ControllerData(board.getCells().getRules());
  }

  @Override
  public void setStage(final Stage stage) {
    fileSystem = new FileSystemCoordinator(getStage());
  }
}
