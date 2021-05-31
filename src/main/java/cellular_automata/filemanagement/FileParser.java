package cellular_automata.filemanagement;

import java.io.File;

public interface FileParser {
  public String getValidFileExtension();
  public SimulationData openFile(final File fileToOpen);
  public void saveFile(final File fileToSaveTo, final SimulationData data);
}
