package cellular_automata.persistence;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import cellular_automata.AlertMediator;

public class FileSystemAdapter {
	public static SaveData openGameFile(final File fileToOpenFrom) {
		final ObjectMapper objectMapper = new ObjectMapper();

		try {
			return objectMapper.readValue(fileToOpenFrom, SaveData.class);
		} catch (IOException e) {
			AlertMediator.notifyRecoverableError("Unable to open the requested game file.");
		}

		return null;
	}

	public static void saveGameFile(final SaveData saveData, final File fileToSaveTo) {
		final ObjectMapper objectMapper = new ObjectMapper();

		try {
			objectMapper.writeValue(fileToSaveTo, saveData);
		} catch (IOException e) {
			AlertMediator.notifyRecoverableError("Unable to save the game data to the requested file.");
		}
	}
}
