package cellular_automata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import cellular_automata.alerts.Alertable;
import cellular_automata.cells.Generations;
import cellular_automata.cells.rules.CellRulesEditor;
import cellular_automata.filemanagement.FileSystemCoordinator;
import cellular_automata.filemanagement.data.SimulationData;
import cellular_automata.graphics.GenerationsLabel;
import cellular_automata.graphics.board.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;

public class CellularAutomataController implements Alertable {
  @FXML private MenuItem openFile;
  @FXML private MenuItem saveFile;
  @FXML private MenuItem toggleGridLines;
  @FXML private MenuItem editRules;
  @FXML private Board board;
  @FXML private Button start;
  @FXML private Button stop;
  @FXML private Button lastStep;
  @FXML private Button nextStep;
  @FXML private Button reset;
  @FXML private Button clear;
  @FXML private Slider speed;
  @FXML private Label rules;
  @FXML private GenerationsLabel generationsLabel;
  private final Properties defaultProperties;
  private final FileSystemCoordinator fileSystem;
  private Generations generations;

  public CellularAutomataController() {
    defaultProperties = new Properties();

    final ClassLoader classLoader = getClass().getClassLoader();
    final File defaultPropertiesFile = new File(classLoader.getResource("defaults.properties").getFile());

    try (final FileInputStream in = new FileInputStream(defaultPropertiesFile)) {
      defaultProperties.load(in);
    } catch (IOException e) {
      notifyNonRecoverableError("Unable to load the default application settings.", e);
    }

    fileSystem = new FileSystemCoordinator(CellularAutomataApp.getPrimaryStage());

    generations = new Generations();
  }

  public void initialize() {
    setUpBoard();
    rules.setText(board.getCells().getRules().toString());
    final SimulationLoop loop = setUpGame();
    setUpFileControls(loop);
    generations.addGenerationsChangeListener(generationsLabel);
    generationsLabel.setText(Integer.toString(generations.getValue()));
    editRules.setOnAction(event -> {
      final CellRulesEditor editor = new CellRulesEditor(board.getCells().getRules());
      editor.showAndWait();
      rules.setText(board.getCells().getRules().toString());
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

  private SimulationLoop setUpGame() {
    final SimulationLoop game = new SimulationLoop(board, Long.valueOf((String) defaultProperties.get("timeStep")),
        generations);

    setUpGameControlButtons(game);
    setupGameSpeedSlider(game);

    return game;
  }

  private void setUpFileControls(final SimulationLoop loop) {
    openFile.setOnAction(event -> {
      loop.stop();

      final SimulationData saveData = fileSystem.openFromFile();

      if (saveData != null) {
        loop.reset();
        board.getCells().copyCellStates(saveData.getCells());
        board.getCells().copyRules(saveData.getCellRules());
        board.setShowGridLines(saveData.getShowGridLines());
        board.getCells().lockCurrentStateAsSeed();
        rules.setText(board.getCells().getRules().toString());
        board.draw();
      }
    });

    saveFile.setOnAction(event -> {
      loop.reset();

      final SimulationData saveData = new SimulationData(board.getCells().getWorkingCells(), board.getShowGridLines(),
          board.getCells().getRules());

      fileSystem.saveToFile(saveData);
    });
  }

  private void setUpBoardDimensions() {
    final int boardWidth = Integer.valueOf((String) defaultProperties.get("boardWidth"));
    final int boardHeight = Integer.valueOf((String) defaultProperties.get("boardHeight"));
    final int cellWidth = Integer.valueOf((String) defaultProperties.get("cellWidth"));
    final int cellHeight = Integer.valueOf((String) defaultProperties.get("cellHeight"));

    board.setUp(boardWidth, boardHeight, cellWidth, cellHeight);
  }

  private void setUpBoardControls() {
    toggleGridLines.setOnAction(event -> {
      board.toggleGridLines();
      board.draw();
    });
  }

  private void setUpGameControlButtons(final SimulationLoop loop) {
    start.setOnAction(event -> loop.start());
    stop.setOnAction(event -> loop.stop());
    lastStep.setOnAction(event -> loop.last());
    nextStep.setOnAction(event -> loop.next());
    reset.setOnAction(event -> loop.reset());
    clear.setOnAction(event -> loop.clear());
  }

  private void setupGameSpeedSlider(final SimulationLoop loop) {
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
    final double minimumSpeed = Double.valueOf((String) defaultProperties.get("minimumSpeed"));
    final double initialSpeed = Double.valueOf((String) defaultProperties.get("initialSpeed"));
    final double maximumSpeed = Double.valueOf((String) defaultProperties.get("maximumSpeed"));

    speed.setMin(minimumSpeed);
    speed.setValue(initialSpeed);
    speed.setMax(maximumSpeed);
  }
}
