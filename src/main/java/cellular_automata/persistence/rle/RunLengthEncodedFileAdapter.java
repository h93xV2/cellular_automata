package cellular_automata.persistence.rle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import cellular_automata.AlertMediator;
import cellular_automata.cells.BirthAndSurvivalConstraints;
import cellular_automata.cells.Cell;
import javafx.util.Pair;

public class RunLengthEncodedFileAdapter {
	private static final int characterOffset = 2;
	private static final int topLeftCornerX = 0;
	private static final int topLeftCornerY = 1;
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

	private RunLengthEncodedFileAdapter() {}

	public static Cell[][] parseFile(final File fileToParse) {
		try (final BufferedReader reader = new BufferedReader(new FileReader(fileToParse))) {
			final RunLengthEncodedData data = new RunLengthEncodedData();

			var line = "";

			while ((line = reader.readLine()) != null) {
				parseLine(line, data);
			}
		} catch (IOException e) {
			AlertMediator.notifyRecoverableError("Unable to open the requested RLE file.");
		}

		return null;
	}

	static void parseLine(final String line, final RunLengthEncodedData data) {
		final String lineStart = line.substring(0, 2);

		switch (lineStart) {
			case commentLineTypeOne -> data.addComment(parseInformationLine(line));
			case commentLineTypeTwo -> data.addComment(parseInformationLine(line));
			case nameLine -> data.setPatternName(parseInformationLine(line));
			case authorInformation -> data.setAuthorInformation(parseInformationLine(line));
			case topLeftCornerTypeOne -> data.setTopLeftCorner(parseTopLeftCorner(line));
			case topLeftCornerTypeTwo -> data.setTopLeftCorner(parseTopLeftCorner(line));
			case rulesLine -> data.setBirthAndSurvivalConstraints(parseRuleLine(line));
			default -> throw new RuntimeException();
		}
	}

	private static String parseInformationLine(final String line) {
		return line.substring(characterOffset).trim();
	}

	private static Pair<Integer, Integer> parseTopLeftCorner(final String line) {
		final String strippedLine = parseInformationLine(line);
		final String[] coordinates = strippedLine.split(" ");
		final int x = Integer.valueOf(coordinates[topLeftCornerX]);
		final int y = Integer.valueOf(coordinates[topLeftCornerY]);

		return new Pair<Integer, Integer>(x, y);
	}
	
	private static BirthAndSurvivalConstraints parseRuleLine(final String line) {
		final String strippedLine = parseInformationLine(line);
		final String[] neighborLists = strippedLine.split("/");
		final String birthList = neighborLists[0].startsWith("B") ? neighborLists[0].substring(1) : neighborLists[1];
		final String survivalList = neighborLists[1].startsWith("S") ? neighborLists[1].substring(1) : neighborLists[0];

		final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
		final List<Integer> neighborsRequiredForBirth = constraints.getLiveNeighborsRequiredForBirth();
		final List<Integer> neighborsRequiredForSurvival = constraints.getLiveNeighborsRequiredForSurvival();

		parseNeighborList(birthList, neighborsRequiredForBirth::add);
		parseNeighborList(survivalList, neighborsRequiredForSurvival::add);

		return constraints;
	}
	
	private static void parseNeighborList(final String neighborList, final Consumer<Integer> neighborConsumer) {
		final String[] neighbors = neighborList.split("");

		for (var neighborCount : neighbors) {
			if (!"".equals(neighborCount)) {
				neighborConsumer.accept(Integer.parseInt(neighborCount));
			}
		}
	}
}
