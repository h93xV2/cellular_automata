package cellular_automata.persistence.rle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cellular_automata.AlertMediator;
import cellular_automata.cells.Cell;
import javafx.util.Pair;

public class RunLengthEncodedFileAdapter {
	private static final String commentLineTypeOne = "#C";
	private static final String commentLineTypeTwo = "#c";
	private static final String nameLine = "#N";
	private static final String authorInformation = "#O";
	private static final String topLeftCornerTypeOne = "#P";
	private static final String topLeftCornerTypeTwo = "#R";
	private static final String rulesLine = "#r";
	private static final String deadCell = "b";
	private static final String liveCell = "o";
	private static final String endOfLine = "$";
	private static final String endOfPattern = "!";
	
	public Cell[][] parseFile(final File fileToParse) {
		try (final BufferedReader reader = new BufferedReader(new FileReader(fileToParse))) {
			var line = "";
			
			while ((line = reader.readLine()) != null) {
				parseLine(line);
			}
		} catch (IOException e) {
			AlertMediator.notifyRecoverableError("Unable to open the requested RLE file.");
		}
		
		return null;
	}
	
	private void parseLine(final String line) {
		final String lineStart = line.substring(0, 2);
		final RunLengthEncodedData data = new RunLengthEncodedData();
		
		switch(lineStart) {
			case commentLineTypeOne -> data.addComment(parseInformationLine(line));
			case commentLineTypeTwo -> data.addComment(parseInformationLine(line));
			case nameLine -> data.setPatternName(parseInformationLine(line));
			case authorInformation -> data.setAuthorInformation(parseInformationLine(line));
			case topLeftCornerTypeOne -> data.setTopLeftCorner(parseTopLeftCorner(line));
			case topLeftCornerTypeTwo -> data.setTopLeftCorner(parseTopLeftCorner(line));
			default -> throw new RuntimeException();
		};
	}
	
	private String parseInformationLine(final String line) {
		return line.substring(2).trim();
	}
	
	private Pair<Integer, Integer> parseTopLeftCorner(final String line) {
		final String parsedLine = parseInformationLine(line);
		final String[] coordinates = parsedLine.split(" ");
		final int x = Integer.valueOf(coordinates[0]);
		final int y = Integer.valueOf(coordinates[1]);
		
		return new Pair<Integer, Integer>(x, y);
	}
}
