package cellular_automata.filemanagement;

import java.io.File;
import java.util.*;

import cellular_automata.filemanagement.app.ApplicationFileStrategy;
import cellular_automata.filemanagement.rle.RunLengthEncodedFileStrategy;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileSystemFacade {
  private final Window ownerWindow;
  private final ApplicationFileStrategy appFileStrategy;
  private final RunLengthEncodedFileStrategy rleFileStrategy;
  private final Map<String, FileStrategy> fileExtensionToStrategyMap;
  private final FileChooser.ExtensionFilter applicationFileFilter;
  private final FileChooser.ExtensionFilter runLengthEncodedFileFilter;
  private final FileChooser openFileChooser;
  private final FileChooser saveFileChooser;

  public FileSystemFacade(final Window ownerWindow) {
    this.ownerWindow = ownerWindow;

    appFileStrategy = new ApplicationFileStrategy();
    rleFileStrategy = new RunLengthEncodedFileStrategy();

    applicationFileFilter = new FileChooser.ExtensionFilter(
        "App specific save files (*" + appFileStrategy.getValidFileExtension() + ")",
        "*" + appFileStrategy.getValidFileExtension());

    runLengthEncodedFileFilter = new FileChooser.ExtensionFilter(
        "RLE files (*" + rleFileStrategy.getValidFileExtension() + ")", "*" + rleFileStrategy.getValidFileExtension());

    fileExtensionToStrategyMap = new HashMap<>();
    fileExtensionToStrategyMap.put(appFileStrategy.getValidFileExtension(), appFileStrategy);
    fileExtensionToStrategyMap.put(rleFileStrategy.getValidFileExtension(), rleFileStrategy);

    openFileChooser = new FileChooser();
    openFileChooser.setTitle("Select file to open");
    openFileChooser.getExtensionFilters().addAll(applicationFileFilter, runLengthEncodedFileFilter);

    saveFileChooser = new FileChooser();
    saveFileChooser.setTitle("Select file to save to");
    saveFileChooser.getExtensionFilters().addAll(applicationFileFilter);
  }

  public SimulationData openFromFile() {
    final File fileToOpen = openFileChooser.showOpenDialog(ownerWindow);
    final String filePath = fileToOpen.getPath();
    final String extension = filePath.substring(filePath.indexOf("."));

    return fileExtensionToStrategyMap.get(extension).openFile(fileToOpen);
  }

  public void saveToFile(final SimulationData data) {
    final File fileToSaveTo = openFileChooser.showSaveDialog(ownerWindow);

    appFileStrategy.saveFile(fileToSaveTo, data);
  }
}
