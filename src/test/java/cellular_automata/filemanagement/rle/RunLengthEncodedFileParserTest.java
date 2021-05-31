package cellular_automata.filemanagement.rle;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import cellular_automata.cells.BirthAndSurvivalConstraints;
import cellular_automata.cells.Cell;
import cellular_automata.cells.CellState;
import cellular_automata.filemanagement.PatternPoint;
import cellular_automata.filemanagement.SimulationData;

public class RunLengthEncodedFileParserTest {
  private static final String simpleTestFilePath = "src/test/resources/testpattern.rle";
  private static final String footballTestFilePath = "src/test/resources/football.rle";
  private static final String commentsAfterPatternFilePath = "src/test/resources/commentsafterpattern.rle";
  private final RunLengthEncodedFileParser rleParser = new RunLengthEncodedFileParser();

  @Test
  void parseCommentLineTypeOne() {
    final String commentLine = "#C hello world";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(commentLine, data);

    assertEquals("hello world", data.getComments().get(0));
  }

  @Test
  void parseCommentLineTypeTwo() {
    final String commentLine = "#c this is a comment";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(commentLine, data);

    assertEquals("this is a comment", data.getComments().get(0));
  }

  @Test
  void parseNameLine() {
    final String nameLine = "#N Gosper glider gun";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(nameLine, data);

    assertEquals("Gosper glider gun", data.getPatternName());
  }

  @Test
  void parseAuthorInformation() {
    final String authorInformation = "#O John Smith";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(authorInformation, data);

    assertEquals("John Smith", data.getAuthorInformation());
  }

  @Test
  void parseTopLeftCornerTypeOne() {
    final String topLeftCorner = "#P 5 7";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(topLeftCorner, data);

    assertEquals(new PatternPoint(5, 7), data.getTopLeftCorner());
  }

  @Test
  void parseTopLeftCornerTypeTwo() {
    final String topLeftCorner = "#R -13 -20";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(topLeftCorner, data);

    assertEquals(new PatternPoint(-13, -20), data.getTopLeftCorner());
  }

  @Test
  void parseBirthConstraints() {
    final String cellRules = "#r B7/S23";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(cellRules, data);

    assertTrue(data.getBirthAndSurvivalConstraints().isCountWithinBirthSet(7));
  }

  @Test
  void parseSurvivalConstraints() {
    final String cellRules = "#r B3/S9";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(cellRules, data);

    assertTrue(data.getBirthAndSurvivalConstraints().isCountWithinSurvivalSet(9));
  }

  @Test
  void parseEmptySurvivalConstraints() {
    final String cellRules = "#r B2/S";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(cellRules, data);

    assertTrue(data.getBirthAndSurvivalConstraints().getTotalSurvivalNeighborCounts() == 0);
  }

  @Test
  void parseDifferentFormatBirthConstraints() {
    final String cellRules = "#r 35/7";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(cellRules, data);

    assertTrue(data.getBirthAndSurvivalConstraints().isCountWithinBirthSet(7));
  }

  @Test
  void widthIsParsedFromTheHeaderLine() {
    final String header = "x = 2, y = 5";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(header, data);

    assertEquals(2, data.getWidth());
  }

  @Test
  void heightIsParsedFromTheHeaderLine() {
    final String header = "x = 30, y = 20";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(header, data);

    assertEquals(20, data.getHeight());
  }

  @Test
  void cellRulesAreParsedFromTheHeaderLine() {
    final String header = "x = 30, y = 20, rule = B2/S45";
    final SimulationData data = new SimulationData();

    rleParser.parseLine(header, data);

    assertTrue(data.getBirthAndSurvivalConstraints().isCountWithinBirthSet(2));
  }

  @Test
  void parseCellStateLine() {
    final String cellInformation = "bo$2bo$3o!";

    assertThrows(CellStateLineDetectedException.class, () -> {
      rleParser.parseLine(cellInformation, new SimulationData());
    });
  }

  @Test
  void testFileProducesSomeOutput() {
    final File testFile = new File(simpleTestFilePath);

    assertNotNull(rleParser.openFile(testFile));
  }

  @Test
  void testFileIsCorrectlyParsedForName() {
    final File testFile = new File(simpleTestFilePath);

    final SimulationData data = rleParser.openFile(testFile);

    assertEquals("hello world", data.getPatternName());
  }

  @Test
  void testRleCellsCanBeDecoded() {
    final String cellString = "o$!";
    final SimulationData data = new SimulationData();
    data.setWidth(1);
    data.setHeight(1);

    rleParser.parseRunLengthEncodedLine(cellString, data);

    assertNotNull(data.getCells());
  }

  @Test
  void testBasicRleCellsAreCorrectlyDecoded() {
    final String cellString = "o$!";
    final SimulationData data = new SimulationData();
    data.setWidth(1);
    data.setHeight(1);

    rleParser.parseRunLengthEncodedLine(cellString, data);

    assertEquals(CellState.LIVE, data.getCells()[0][0].getState());
  }

  @Test
  void testRleFileHasCellsParsed() {
    final File testFile = new File(simpleTestFilePath);

    final SimulationData data = rleParser.openFile(testFile);

    assertEquals(CellState.LIVE, data.getCells()[0][0].getState());
  }

  @Test
  void testFootballFile() {
    final File testFile = new File(footballTestFilePath);

    final SimulationData data = rleParser.openFile(testFile);

    assertEquals(CellState.LIVE, data.getCells()[1][3].getState());
  }

  @Test
  void parseHeaderLineWithUnrecognizedAttribute() {
    final String headerString = "x = 3, y=4, rule=B3/S23, wasd=wasd";

    assertThrows(UnknownHeaderAttributeException.class,
        () -> rleParser.parseLine(headerString, new SimulationData()));
  }

  @Test
  void parseEncodedCellLine() {
    final String cellLine = "3o!";
    final SimulationData data = new SimulationData();
    data.setWidth(3);
    data.setHeight(1);

    rleParser.parseRunLengthEncodedLine(cellLine, data);

    final Cell[][] cells = data.getCells();
    var cellsAreAlive = true;

    for (var i = 0; i < cells.length; i++) {
      cellsAreAlive &= CellState.LIVE.equals(cells[i][0].getState());
    }

    assertTrue(cellsAreAlive);
  }

  @Test
  void impliedDeadCellsAtEndOfPatternLine() {
    final String cellLine = "3o!";
    final SimulationData data = new SimulationData();
    data.setWidth(4);
    data.setHeight(1);

    rleParser.parseRunLengthEncodedLine(cellLine, data);

    assertEquals(CellState.DEAD, data.getCells()[3][0].getState());
  }

  @Test
  void unknownCellStatesAreDefaultedToDead() {
    final String cellLine = "abo!";
    final SimulationData data = new SimulationData();
    data.setWidth(3);
    data.setHeight(1);

    rleParser.parseRunLengthEncodedLine(cellLine, data);

    assertEquals(CellState.DEAD, data.getCells()[0][0].getState());
  }

  @Test
  void fileWithoutRulesDefaultsToConwaysGameOfLife() {
    final File testFile = new File(simpleTestFilePath);
    final SimulationData data = rleParser.openFile(testFile);
    final BirthAndSurvivalConstraints constraints = data.getBirthAndSurvivalConstraints();

    assertTrue(constraints.getTotalBirthNeighborCounts() == 1 && constraints.getTotalSurvivalNeighborCounts() == 2
        && constraints.isCountWithinBirthSet(3) && constraints.isCountWithinSurvivalSet(2)
        && constraints.isCountWithinSurvivalSet(3));
  }

  @Test
  void linesAfterThePatternEndAreTreatedAsComments() {
    final File testFile = new File(commentsAfterPatternFilePath);
    final SimulationData data = rleParser.openFile(testFile);
    final List<String> comments = data.getComments();

    assertEquals("This is a comment", comments.get(0));
  }
}
