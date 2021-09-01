package cellular_automata.filemanagement.parsers;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import cellular_automata.alerts.Alertable;
import cellular_automata.filemanagement.data.SimulationData;

public class ApplicationFileParser implements Alertable, FileParser {
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
      notifyRecoverableError("Unable to open the requested file.", e);
    }

    return null;
  }

  @Override
  public void saveFile(final File fileToSaveTo, final SimulationData data) {
    final ObjectMapper objectMapper = new ObjectMapper();

    try {
      objectMapper.writeValue(fileToSaveTo, data);
    } catch (IOException e) {
      notifyRecoverableError("Unable to save data to the requested file.", e);
    }
  }
}
