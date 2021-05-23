package cellular_automata.filemanagement.app;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import cellular_automata.AlertMediator;
import cellular_automata.filemanagement.FileStrategy;
import cellular_automata.filemanagement.SimulationData;

public class ApplicationFileStrategy implements FileStrategy {
  private static final String fileNameExtension = ".json";
  
  @Override
  public String getValidFileExtension() {
    return fileNameExtension;
  }

  @Override
  public SimulationData openFile(final File fileToOpen) {
    final ObjectMapper objectMapper = new ObjectMapper();
    
    try {
      return objectMapper.readValue(fileToOpen, SimulationData.class);
    } catch (IOException e) {
      AlertMediator.notifyRecoverableError("Unable to open the requested game file.", e);
    }

    return null;
  }

  @Override
  public void saveFile(final File fileToSaveTo, final SimulationData data) {
    final ObjectMapper objectMapper = new ObjectMapper();

    try {
      objectMapper.writeValue(fileToSaveTo, data);
    } catch (IOException e) {
      AlertMediator.notifyRecoverableError("Unable to save the game data to the requested file.", e);
    }
  }
}
