package cellular_automata.filemanagement;

import java.io.File;
import java.util.*;

import cellular_automata.filemanagement.app.ApplicationFileStrategy;
import cellular_automata.filemanagement.rle.RunLengthEncodedFileStrategy;
import javafx.stage.FileChooser;

public class FileStrategyMediator {
  private final ApplicationFileStrategy appFileStrategy;
  private final RunLengthEncodedFileStrategy rleFileStrategy;
  private final Map<String, FileStrategy> fileExtensionToStrategyMap;
  private final FileChooser.ExtensionFilter applicationFileFilter;
  private final FileChooser.ExtensionFilter runLengthEncodedFileFilter;

  public FileStrategyMediator() {
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
  }

  public SimulationData openFile(final File fileToOpen) {
    final String filePath = fileToOpen.getPath();
    final String extension = filePath.substring(filePath.indexOf("."));

    return fileExtensionToStrategyMap.get(extension).openFile(fileToOpen);
  }

  public void saveFile(final File fileToSaveTo, final SimulationData data) {
    appFileStrategy.saveFile(fileToSaveTo, data);
  }

  public List<FileChooser.ExtensionFilter> getOpenFileFilters() {
    final List<FileChooser.ExtensionFilter> openFilters = new ArrayList<>();

    openFilters.add(applicationFileFilter);
    openFilters.add(runLengthEncodedFileFilter);

    return openFilters;
  }

  public List<FileChooser.ExtensionFilter> getSaveFileFilters() {
    final List<FileChooser.ExtensionFilter> saveFilters = new ArrayList<>();

    saveFilters.add(applicationFileFilter);

    return saveFilters;
  }
}
