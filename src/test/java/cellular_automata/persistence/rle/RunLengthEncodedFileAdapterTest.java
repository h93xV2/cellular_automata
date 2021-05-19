package cellular_automata.persistence.rle;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import cellular_automata.cells.CellState;
import javafx.util.Pair;

public class RunLengthEncodedFileAdapterTest {
  private static final String simpleTestFilePath = "src/test/resources/testpattern.rle";
  private static final String footballTestFilePath = "src/test/resources/football.rle";

  @Test
  void parseCommentLineTypeOne() {
    final String commentLine = "#C hello world";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(commentLine, data);

    assertEquals("hello world", data.getComments().get(0));
  }

  @Test
  void parseCommentLineTypeTwo() {
    final String commentLine = "#c this is a comment";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(commentLine, data);

    assertEquals("this is a comment", data.getComments().get(0));
  }

  @Test
  void parseNameLine() {
    final String nameLine = "#N Gosper glider gun";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(nameLine, data);

    assertEquals("Gosper glider gun", data.getPatternName());
  }

  @Test
  void parseAuthorInformation() {
    final String authorInformation = "#O John Smith";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(authorInformation, data);

    assertEquals("John Smith", data.getAuthorInformation());
  }

  @Test
  void parseTopLeftCornerTypeOne() {
    final String topLeftCorner = "#P 5 7";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(topLeftCorner, data);

    assertEquals(new Pair<Integer, Integer>(5, 7), data.getTopLeftCorner());
  }

  @Test
  void parseTopLeftCornerTypeTwo() {
    final String topLeftCorner = "#R -13 -20";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(topLeftCorner, data);

    assertEquals(new Pair<Integer, Integer>(-13, -20), data.getTopLeftCorner());
  }

  @Test
  void parseBirthConstraints() {
    final String cellRules = "#r B3/S23";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(cellRules, data);

    assertEquals(3, data.getBirthAndSurvivalConstraints().getLiveNeighborsRequiredForBirth().get(0));
  }

  @Test
  void parseSurvivalConstraints() {
    final String cellRules = "#r B3/S9";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(cellRules, data);

    assertEquals(9, data.getBirthAndSurvivalConstraints().getLiveNeighborsRequiredForSurvival().get(0));
  }

  @Test
  void parseEmptySurvivalConstraints() {
    final String cellRules = "#r B2/S";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(cellRules, data);

    assertTrue(data.getBirthAndSurvivalConstraints().getLiveNeighborsRequiredForSurvival().isEmpty());
  }

  @Test
  void parseDifferentFormatBirthConstraints() {
    final String cellRules = "#r 23/3";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(cellRules, data);

    assertEquals(3, data.getBirthAndSurvivalConstraints().getLiveNeighborsRequiredForBirth().get(0));
  }

  @Test
  void widthIsParsedFromTheHeaderLine() {
    final String header = "x = 2, y = 5";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(header, data);

    assertEquals(2, data.getWidth());
  }

  @Test
  void heightIsParsedFromTheHeaderLine() {
    final String header = "x = 30, y = 20";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(header, data);

    assertEquals(20, data.getHeight());
  }

  @Test
  void cellRulesAreParsedFromTheHeaderLine() {
    final String header = "x = 30, y = 20, rule = B3/S23";
    final RunLengthEncodedData data = new RunLengthEncodedData();

    RunLengthEncodedFileAdapter.parseLine(header, data);

    assertEquals(3, data.getBirthAndSurvivalConstraints().getLiveNeighborsRequiredForBirth().get(0));
  }

  @Test
  void parseCellStateLine() {
    final String cellInformation = "bo$2bo$3o!";

    assertThrows(CellStateLineDetectedException.class, () -> {
      RunLengthEncodedFileAdapter.parseLine(cellInformation, new RunLengthEncodedData());
    });
  }

  @Test
  void testFileProducesSomeOutput() {
    final File testFile = new File(simpleTestFilePath);

    assertNotNull(RunLengthEncodedFileAdapter.parseFile(testFile));
  }

  @Test
  void testFileIsCorrectlyParsedForName() {
    final File testFile = new File(simpleTestFilePath);

    final RunLengthEncodedData data = RunLengthEncodedFileAdapter.parseFile(testFile);

    assertEquals("hello world", data.getPatternName());
  }

  @Test
  void testRleCellsCanBeDecoded() {
    final String cellString = "o$!";
    final RunLengthEncodedData data = new RunLengthEncodedData();
    data.setWidth(1);
    data.setHeight(1);

    RunLengthEncodedFileAdapter.parseRunLengthEncodedLine(cellString, data);

    assertNotNull(data.getCells());
  }

  @Test
  void testBasicRleCellsAreCorrectlyDecoded() {
    final String cellString = "o$!";
    final RunLengthEncodedData data = new RunLengthEncodedData();
    data.setWidth(1);
    data.setHeight(1);

    RunLengthEncodedFileAdapter.parseRunLengthEncodedLine(cellString, data);

    assertEquals(CellState.LIVE, data.getCells()[0][0].getState());
  }

  @Test
  void testRleFileHasCellsParsed() {
    final File testFile = new File(simpleTestFilePath);

    final RunLengthEncodedData data = RunLengthEncodedFileAdapter.parseFile(testFile);

    assertEquals(CellState.LIVE, data.getCells()[0][0].getState());
  }
  
  @Test
  void testFootballFile() {
    final File testFile = new File(footballTestFilePath);
    
    final RunLengthEncodedData data = RunLengthEncodedFileAdapter.parseFile(testFile);
    
    assertEquals(CellState.LIVE, data.getCells()[1][3].getState());
  }
}
