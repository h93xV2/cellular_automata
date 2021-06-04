package cellular_automata.filemanagement.rle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import cellular_automata.AlertMediator;
import cellular_automata.cells.BirthAndSurvivalConstraints;
import cellular_automata.cells.Cell;
import cellular_automata.cells.CellState;
import cellular_automata.filemanagement.FileParser;
import cellular_automata.filemanagement.PatternPoint;
import cellular_automata.filemanagement.SimulationData;

public class RunLengthEncodedFileParser implements FileParser {
  private static final String fileNameExtension = ".rle";

  private static final int characterOffset = 2;
  private static final int topLeftCornerX = 0;
  private static final int topLeftCornerY = 1;
  private static final String endOfLineSplitter = "\\$";
  private static final String endOfPatternMarker = "!";
  private static final String startOfHeaderLineCharacter = "x";
  private static final String coordinateComponentSeparator = " ";

  @Override
  public String getValidFileExtension() {
    return fileNameExtension;
  }

  @Override
  public SimulationData openFile(final File fileToParse) {
    try (final BufferedReader reader = new BufferedReader(new FileReader(fileToParse))) {
      return parseFileData(reader);
    } catch (IOException e) {
      AlertMediator.notifyRecoverableError("Unable to open the requested RLE file.", e);
    }

    return null;
  }

  private SimulationData parseFileData(final BufferedReader reader) throws IOException {
    final SimulationData data = new SimulationData();

    var line = "";
    var encodedCellLines = new StringBuilder();
    var endOfPatternReached = false;

    while ((line = reader.readLine()) != null) {
      try {
        parseLine(line, data);
      } catch (CellStateLineDetectedException e) {
        if (!endOfPatternReached) {
          encodedCellLines.append(line);
          
          if (line.contains(endOfPatternMarker)) {
            endOfPatternReached = true;
          }
        } else {
          data.getComments().add(line);
        }
      }
    }

    parseRunLengthEncodedLine(encodedCellLines.toString(), data);

    return data;
  }
  
  void parseLine(final String line, final SimulationData data) {
    final String lineStart = line.trim().substring(0, 2);
    final LineType typeOfLineUnderInspection = LineType.getRleLineMarkerToLineTypeMap().get(lineStart);

    if (typeOfLineUnderInspection != null) {
      populateDataFromMarkedLine(line, typeOfLineUnderInspection, data);
    } else {
      if (lineStart.startsWith(startOfHeaderLineCharacter)) {
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
    final String[] coordinates = strippedLine.split(coordinateComponentSeparator);
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
    final String birthList = neighborLists[0].toLowerCase().startsWith("b") ? neighborLists[0].substring(1) : neighborLists[1];
    final String survivalList = neighborLists[1].toLowerCase().startsWith("s") ? neighborLists[1].substring(1) : neighborLists[0];

    return buildConstraintsFromBirthAndSurvivalLists(birthList, survivalList);
  }

  private BirthAndSurvivalConstraints buildConstraintsFromBirthAndSurvivalLists(final String birthList,
      final String survivalList) {
    final BirthAndSurvivalConstraints constraints = new BirthAndSurvivalConstraints();
    constraints.clearBirthNeighborCounts();
    constraints.clearSurvivalNeighborCounts();

    parseNeighborList(birthList, constraints::addBirthNeighborCount);
    parseNeighborList(survivalList, constraints::addSurvivalNeighborCount);

    return constraints;
  }

  private void parseNeighborList(final String neighborList, final Consumer<Integer> neighborConsumer) {
    Arrays.asList(neighborList.split("")).stream().filter(neighborString -> !neighborString.isBlank())
        .map(Integer::parseInt).forEach(neighborConsumer::accept);
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
        throw new UnknownHeaderAttributeException();
      }
    }
  }

  void parseRunLengthEncodedLine(final String line, final SimulationData data) {
    final Cell[][] cells = new Cell[data.getWidth()][data.getHeight()];
    final List<String> cellRows = new ArrayList<>(Arrays.asList(getEncodedCellRowsFromEncodedCellLine(line)));

    var encodedRows = cellRows.size();
    var y = 0;
    var skips = 0;
    
    while (y < encodedRows) {
      if (skips == 0) {
        final int count = decodeEncodedCellRow(cellRows.get(y), y, cells, data);
        
        if (count != 0) skips = count - 1;
      } else {
        addImpliedDeadCells(data, cells, 0, y);
        skips --;
        encodedRows ++;
        cellRows.add(y, "");
      }
      
      y ++;
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

  private int decodeEncodedCellRow(final String cellRow, final int y, final Cell[][] cells,
      final SimulationData data) {
    var x = 0;
    var count = 0;

    for (var character : cellRow.toCharArray()) {
      if (Character.isDigit(character)) {
        count = calculateDecodedCharacterCount(count, character);
      } else {
        if (count == 0)
          count++;

        while (count > 0) {
          cells[x][y] = buildCellFromCharacterRepresentation(character);
          count--;
          x++;
        }
      }
    }

    if (x < data.getWidth())
      addImpliedDeadCells(data, cells, x, y);
    
    return count;
  }

  private int calculateDecodedCharacterCount(final int count, final char character) {
    return (count * 10) + Integer.valueOf(String.valueOf(character));
  }

  private Cell buildCellFromCharacterRepresentation(final char character) {
    final String stateSymbol = String.valueOf(character);
    final Cell cell = new Cell();

    if (CellState.getRleSymbolToCellStateMap().containsKey(stateSymbol)) {
      cell.setState(CellState.getRleSymbolToCellStateMap().get(stateSymbol));
    }

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
