package cellular_automata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import cellular_automata.persistence.FileSystemAdapter;
import cellular_automata.persistence.SaveData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;

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
  private final FileChooser fileChooser;

  public CellularAutomataController() {
    defaultProperties = new Properties();

    final ClassLoader classLoader = getClass().getClassLoader();
    final File defaultPropertiesFile = new File(classLoader.getResource("defaults.properties").getFile());

    try (final FileInputStream in = new FileInputStream(defaultPropertiesFile)) {
      defaultProperties.load(in);
    } catch (IOException e) {
      AlertMediator.notifyNonRecoverableError("Unable to load the default application settings.");
    }

    fileChooser = new FileChooser();

    fileChooser.setTitle("Select file");
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Game of Life files (*" + FileSystemAdapter.fileNameExtension + ")",
            "*" + FileSystemAdapter.fileNameExtension));
  }

  public void initialize() {
    setUpBoard();
    final SimulationLoop game = setUpGame();
    setUpFileControls(game);
  }

  private void setUpFileControls(final SimulationLoop game) {
    openFile.setOnAction(event -> {
      game.stop();

      final File fileToOpen = fileChooser.showOpenDialog(CellularAutomataApp.getPrimaryStage());

      final SaveData saveData = FileSystemAdapter.openFile(fileToOpen);

      board.getCells().forEach((x, y) -> board.getCells().getCell(x, y).setState(saveData.getCells()[x][y].getState()));
      board.setShowGridLines(saveData.getShowGridLines());
      board.getCells().lockCurrentStateAsSeed();
      board.drawCells();
    });

    saveFile.setOnAction(event -> {
      game.reset();

      var fileToSaveTo = fileChooser.showSaveDialog(CellularAutomataApp.getPrimaryStage());

      if (!fileToSaveTo.getName().contains(FileSystemAdapter.fileNameExtension)) {
        fileToSaveTo = new File(fileToSaveTo.getAbsolutePath() + FileSystemAdapter.fileNameExtension);
      }

      final SaveData saveData = new SaveData(board.getCells().getWorkingCells(), board.getShowGridLines());

      FileSystemAdapter.saveFile(saveData, fileToSaveTo);
    });
  }

  private void setUpBoard() {
    try {
      final int boardWidth = Integer.valueOf((String) defaultProperties.get("boardWidth"));
      final int boardHeight = Integer.valueOf((String) defaultProperties.get("boardHeight"));
      final int cellWidth = Integer.valueOf((String) defaultProperties.get("cellWidth"));
      final int cellHeight = Integer.valueOf((String) defaultProperties.get("cellHeight"));

      board.setUp(boardWidth, boardHeight, cellWidth, cellHeight);
    } catch (NumberFormatException nfe) {
      AlertMediator.notifyNonRecoverableError("Unable to establish the size of the window.");
    }

    setUpBoardControls();
  }

  private void setUpBoardControls() {
    showGridLines.setOnAction(event -> {
      board.toggleGridLines();
      board.drawCells();
    });
  }

  private SimulationLoop setUpGame() {
    final SimulationLoop game = new SimulationLoop(board);

    setUpGameControlButtons(game);
    setupGameSpeedSlider(game);

    return game;
  }

  private void setUpGameControlButtons(final SimulationLoop game) {
    startGame.setOnAction(event -> game.start());
    stopGame.setOnAction(event -> game.stop());
    nextIteration.setOnAction(event -> game.next());
    resetGame.setOnAction(event -> game.reset());
    clearGame.setOnAction(event -> game.clear());
  }

  private void setupGameSpeedSlider(final SimulationLoop game) {
    try {
      final double minimumSpeed = Double.valueOf((String) defaultProperties.get("minimumSpeed"));
      final double initialSpeed = Double.valueOf((String) defaultProperties.get("initialSpeed"));
      final double maximumSpeed = Double.valueOf((String) defaultProperties.get("maximumSpeed"));

      gameSpeed.setMin(minimumSpeed);
      gameSpeed.setValue(initialSpeed);
      gameSpeed.setMax(maximumSpeed);
    } catch (NumberFormatException nfe) {
      AlertMediator.notifyNonRecoverableError("Unable to create the slider used to control speed.");
    }

    gameSpeed.valueProperty().addListener((observableValue, oldValue, newValue) -> {
      game.setTimeStepMultiplier(newValue.doubleValue());
    });
  }
}
