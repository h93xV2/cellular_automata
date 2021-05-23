package cellular_automata.filemanagement.rle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

import cellular_automata.AlertMediator;
import cellular_automata.cells.BirthAndSurvivalConstraints;
import cellular_automata.cells.Cell;
import cellular_automata.cells.CellState;
import cellular_automata.filemanagement.FileStrategy;
import cellular_automata.filemanagement.PatternPoint;
import cellular_automata.filemanagement.SimulationData;

public class RunLengthEncodedFileStrategy implements FileStrategy {
  private static final String fileNameExtension = ".rle";

  private static final int characterOffset = 2;
  private static final int topLeftCornerX = 0;
  private static final int topLeftCornerY = 1;
  private static final String endOfLineSplitter = "\\$";
  private static final String endOfPatternMarker = "!";

  @Override
  public String getValidFileExtension() {
    return fileNameExtension;
  }

  @Override
  public SimulationData openFile(final File fileToParse) {
    try (final BufferedReader reader = new BufferedReader(new FileReader(fileToParse))) {
      final SimulationData data = new SimulationData();

      var line = "";
      var encodedCellLines = new StringBuilder();

      while ((line = reader.readLine()) != null) {
        try {
          parseLine(line, data);
        } catch (CellStateLineDetectedException e) {
          encodedCellLines.append(line);
        }
      }

      parseRunLengthEncodedLine(encodedCellLines.toString(), data);

      return data;
    } catch (IOException e) {
      AlertMediator.notifyRecoverableError("Unable to open the requested RLE file.", e);
    }

    return null;
  }

  void parseLine(final String line, final SimulationData data) {
    final String lineStart = line.trim().substring(0, 2);
    final LineType typeOfLineUnderInspection = LineType.getRleLineMarkerToLineTypeMap().get(lineStart);

    if (typeOfLineUnderInspection != null) {
      populateDataFromMarkedLine(line, typeOfLineUnderInspection, data);
    } else {
      if (lineStart.startsWith("x")) {
        parseHeaderLine(line, data);
      } else {
        throw new CellStateLineDetectedException();
      }
    }
  }

  private void populateDataFromMarkedLine(final String line, final LineType typeOfLineUnderInspection,
      final SimulationData data) {
    switch (typeOfLineUnderInspection) {
    case COMMENT_TYPE_ONE -> data.addComment(parseInformationLine(line));
    case COMMENT_TYPE_TWO -> data.addComment(parseInformationLine(line));
    case PATTERN_NAME -> data.setPatternName(parseInformationLine(line));
    case AUTHOR_INFORMATION -> data.setAuthorInformation(parseInformationLine(line));
    case TOP_LEFT_CORNER_TYPE_ONE -> data.setTopLeftCorner(parseTopLeftCorner(line));
    case TOP_LEFT_CORNER_TYPE_TWO -> data.setTopLeftCorner(parseTopLeftCorner(line));
    case CELL_RULES -> data.setBirthAndSurvivalConstraints(parseMarkedRuleLine(line));
    default -> throw new RuntimeException();
    }
  }

  private String parseInformationLine(final String line) {
    return line.substring(characterOffset).trim();
  }

  private PatternPoint parseTopLeftCorner(final String line) {
    final String strippedLine = parseInformationLine(line);
    final String[] coordinates = strippedLine.split(" ");
    final int x = Integer.valueOf(coordinates[topLeftCornerX]);
    final int y = Integer.valueOf(coordinates[topLeftCornerY]);

    return new PatternPoint(x, y);
  }

  private BirthAndSurvivalConstraints parseMarkedRuleLine(final String line) {
    final String strippedLine = parseInformationLine(line);

    return parseRuleData(strippedLine);
  }

  private BirthAndSurvivalConstraints parseRuleData(final String line) {
    final String[] neighborLists = line.split("/");
    final String birthList = neighborLists[0].startsWith("B") ? neighborLists[0].substring(1) : neighborLists[1];
    final String survivalList = neighborLists[1].startsWith("S") ? neighborLists[1].substring(1) : neighborLists[0];

    return buildConstraintsFromBirthAndSurvivalLists(birthList, survivalList);
  }

  private BirthAndSurvivalConstraints buildConstraintsFromBirthAndSurvivalLists(final String birthList,
      final String survivalList) {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();

    parseNeighborList(birthList, constraints.getLiveNeighborsRequiredForBirth()::add);
    parseNeighborList(survivalList, constraints.getLiveNeighborsRequiredForSurvival()::add);

    return constraints;
  }

  private void parseNeighborList(final String neighborList, final Consumer<Integer> neighborConsumer) {
    final String[] neighbors = neighborList.split("");

    for (var neighborCount : neighbors) {
      if (!"".equals(neighborCount)) {
        neighborConsumer.accept(Integer.parseInt(neighborCount));
      }
    }
  }

  private void parseHeaderLine(final String line, final SimulationData data) {
    final String[] headerAttributes = line.split(",");

    for (var attribute : headerAttributes) {
      attribute = attribute.trim();

      final String[] attributeParts = attribute.split("=");

      if (attribute.startsWith("x")) {
        data.setWidth(Integer.parseInt(attributeParts[1].trim()));
      } else if (attribute.startsWith("y")) {
        data.setHeight(Integer.parseInt(attributeParts[1].trim()));
      } else if (attribute.startsWith("rule")) {
        data.setBirthAndSurvivalConstraints(parseRuleData(attributeParts[1].trim()));
      } else {
        throw new RuntimeException();
      }
    }
  }

  void parseRunLengthEncodedLine(final String line, final SimulationData data) {
    final Cell[][] cells = new Cell[data.getWidth()][data.getHeight()];
    final String[] cellRows = getEncodedCellRowsFromEncodedCellLine(line);

    for (var y = 0; y < cellRows.length; y++) {
      decodeEncodedCellRow(cellRows[y], y, cells, data);
    }

    data.setCells(cells);
  }

  private String[] getEncodedCellRowsFromEncodedCellLine(final String line) {
    final String processedLine = prepareCellLineForProcessing(line);

    return processedLine.split(endOfLineSplitter);
  }

  private String prepareCellLineForProcessing(final String line) {
    var processedLine = line.trim();
    processedLine = processedLine.replace(" ", "");
    processedLine = processedLine.replace(endOfPatternMarker, "");

    return processedLine;
  }

  private void decodeEncodedCellRow(final String cellRow, final int y, final Cell[][] cells,
      final SimulationData data) {
    var x = 0;
    var count = 0;

    for (var character : cellRow.toCharArray()) {
      if (Character.isDigit(character)) {
        count = calculateDecodedCharacterCount(count, character);
      } else {
        if (count == 0) {
          count++;
        }

        while (count > 0) {
          cells[x][y] = buildCellFromCharacterRepresentation(character);
          count--;
          x++;
        }
      }
    }

    if (x < data.getWidth()) {
      addImpliedDeadCells(data, cells, x, y);
    }
  }

  private int calculateDecodedCharacterCount(final int count, final char character) {
    return (count * 10) + Integer.valueOf(String.valueOf(character));
  }

  private Cell buildCellFromCharacterRepresentation(final char character) {
    final Cell cell = new Cell();
    cell.setState(CellState.getRleSymbolToCellStateMap().get(String.valueOf(character)));

    return cell;
  }

  private void addImpliedDeadCells(final SimulationData data, final Cell[][] cells, int x, final int y) {
    while (x < data.getWidth()) {
      cells[x][y] = new Cell();
      x++;
    }
  }

  @Override
  public void saveFile(File fileToSaveTo, SimulationData data) {
    AlertMediator.notifyRecoverableError("This operation is not supported at this time.");
  }
}
