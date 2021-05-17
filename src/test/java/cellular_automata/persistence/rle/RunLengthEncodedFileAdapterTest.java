package cellular_automata.persistence.rle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

public class RunLengthEncodedFileAdapterTest {
	@Test void parseCommentLineTypeOne() {
		final String commentLine = "#C hello world";
		final RunLengthEncodedData data = new RunLengthEncodedData();
		
		RunLengthEncodedFileAdapter.parseLine(commentLine, data);
		
		assertEquals("hello world", data.getComments().get(0));
	}
	
	@Test void parseCommentLineTypeTwo() {
		final String commentLine = "#c this is a comment";
		final RunLengthEncodedData data = new RunLengthEncodedData();
		
		RunLengthEncodedFileAdapter.parseLine(commentLine, data);
		
		assertEquals("this is a comment", data.getComments().get(0));
	}
	
	@Test void parseNameLine() {
		final String nameLine = "#N Gosper glider gun";
		final RunLengthEncodedData data = new RunLengthEncodedData();
		
		RunLengthEncodedFileAdapter.parseLine(nameLine, data);
		
		assertEquals("Gosper glider gun", data.getPatternName());
	}
	
	@Test void parseAuthorInformation() {
		final String authorInformation = "#O John Smith";
		final RunLengthEncodedData data = new RunLengthEncodedData();
		
		RunLengthEncodedFileAdapter.parseLine(authorInformation, data);
		
		assertEquals("John Smith", data.getAuthorInformation());
	}
	
	@Test void parseTopLeftCornerTypeOne() {
		final String topLeftCorner = "#P 5 7";
		final RunLengthEncodedData data = new RunLengthEncodedData();
		
		RunLengthEncodedFileAdapter.parseLine(topLeftCorner, data);
		
		assertEquals(new Pair<Integer, Integer>(5, 7), data.getTopLeftCorner());
	}
	
	@Test void parseTopLeftCornerTypeTwo() {
		final String topLeftCorner = "#R -13 -20";
		final RunLengthEncodedData data = new RunLengthEncodedData();
		
		RunLengthEncodedFileAdapter.parseLine(topLeftCorner, data);
		
		assertEquals(new Pair<Integer, Integer>(-13, -20), data.getTopLeftCorner());
	}
}