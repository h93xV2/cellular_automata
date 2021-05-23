package cellular_automata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import cellular_automata.filemanagement.FileSystemFacade;
import cellular_automata.filemanagement.SimulationData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;

public class CellularAutomataController {
  @FXML private MenuItem openFile;
  @FXML private MenuItem saveFile;
  @FXML private MenuItem showGridLines;
  @FXML private Board board;
  @FXML private Button startGame;
  @FXML private Button stopGame;
  @FXML private Button nextIteration;
  @FXML private Button resetGame;
  @FXML private Button clearGame;
  @FXML private Slider gameSpeed;
  private final Properties defaultProperties;
  private final FileSystemFacade fileSystem;

  public CellularAutomataController() {
    defaultProperties = new Properties();

    final ClassLoader classLoader = getClass().getClassLoader();
    final File defaultPropertiesFile = new File(classLoader.getResource("defaults.properties").getFile());

    try (final FileInputStream in = new FileInputStream(defaultPropertiesFile)) {
      defaultProperties.load(in);
    } catch (IOException e) {
      AlertMediator.notifyNonRecoverableError("Unable to load the default application settings.", e);
    }

    fileSystem = new FileSystemFacade(CellularAutomataApp.getPrimaryStage());
  }

  public void initialize() {
    setUpBoard();
    final SimulationLoop loop = setUpGame();
    setUpFileControls(loop);
  }

  private void setUpBoard() {
    try {
      setUpBoardDimensions();
    } catch (NumberFormatException nfe) {
      AlertMediator.notifyNonRecoverableError("Unable to establish the size of the window.", nfe);
    }

    setUpBoardControls();
  }

  private SimulationLoop setUpGame() {
    final SimulationLoop game = new SimulationLoop(board, Long.valueOf((String) defaultProperties.get("timeStep")));

    setUpGameControlButtons(game);
    setupGameSpeedSlider(game);

    return game;
  }

  private void setUpFileControls(final SimulationLoop loop) {
    openFile.setOnAction(event -> {
      loop.stop();

      final SimulationData saveData = fileSystem.openFromFile();

      if (saveData != null) {
        board.getCells().copyCellStates(saveData.getCells());
        board.setShowGridLines(saveData.getShowGridLines());
        board.getCells().lockCurrentStateAsSeed();
        board.drawCells();
      }
    });

    saveFile.setOnAction(event -> {
      loop.reset();

      final SimulationData saveData = new SimulationData(board.getCells().getWorkingCells(), board.getShowGridLines());

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
    showGridLines.setOnAction(event -> {
      board.toggleGridLines();
      board.drawCells();
    });
  }

  private void setUpGameControlButtons(final SimulationLoop loop) {
    startGame.setOnAction(event -> loop.start());
    stopGame.setOnAction(event -> loop.stop());
    nextIteration.setOnAction(event -> loop.next());
    resetGame.setOnAction(event -> loop.reset());
    clearGame.setOnAction(event -> loop.clear());
  }

  private void setupGameSpeedSlider(final SimulationLoop loop) {
    try {
      initializeGameSpeedSlider();
    } catch (NumberFormatException nfe) {
      AlertMediator.notifyNonRecoverableError("Unable to create the slider used to control speed.", nfe);
    }

    gameSpeed.valueProperty().addListener((observableValue, oldValue, newValue) -> {
      loop.setTimeStepMultiplier(newValue.doubleValue());
    });
  }

  private void initializeGameSpeedSlider() {
    final double minimumSpeed = Double.valueOf((String) defaultProperties.get("minimumSpeed"));
    final double initialSpeed = Double.valueOf((String) defaultProperties.get("initialSpeed"));
    final double maximumSpeed = Double.valueOf((String) defaultProperties.get("maximumSpeed"));

    gameSpeed.setMin(minimumSpeed);
    gameSpeed.setValue(initialSpeed);
    gameSpeed.setMax(maximumSpeed);
  }
}
