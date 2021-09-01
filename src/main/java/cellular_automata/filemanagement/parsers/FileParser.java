package cellular_automata.filemanagement.parsers;

import java.io.File;

import cellular_automata.filemanagement.data.SimulationData;

public interface FileParser {
  public String getValidFileExtension();
  public SimulationData openFile(final File fileToOpen);
  public void saveFile(final File fileToSaveTo, final SimulationData data);
}
