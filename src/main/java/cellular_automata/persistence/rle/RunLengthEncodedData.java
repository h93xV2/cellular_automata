package cellular_automata.persistence.rle;

import java.util.ArrayList;
import java.util.List;

import cellular_automata.cells.BirthAndSurvivalConstraints;
import cellular_automata.cells.Cell;
import javafx.util.Pair;

public class RunLengthEncodedData {
	private List<String> comments;
	private String patternName;
	private String authorInformation;
	private Pair<Integer, Integer> topLeftCorner;
	private BirthAndSurvivalConstraints constraints;
	private int width;
	private int height;
	private Cell[][] cells;

	public RunLengthEncodedData() {
		comments = new ArrayList<>();
		topLeftCorner = new Pair<Integer, Integer>(0, 0);
		constraints = new BirthAndSurvivalConstraints();
	}

	public List<String> getComments() {
		return comments;
	}

	public void addComment(final String comment) {
		comments.add(comment);
	}

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(final String patternName) {
		this.patternName = patternName;
	}

	public String getAuthorInformation() {
		return authorInformation;
	}

	public void setAuthorInformation(final String authorInformation) {
		this.authorInformation = authorInformation;
	}

	public Pair<Integer, Integer> getTopLeftCorner() {
		return topLeftCorner;
	}

	public void setTopLeftCorner(final Pair<Integer, Integer> topLeftCorner) {
		this.topLeftCorner = topLeftCorner;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public BirthAndSurvivalConstraints getBirthAndSurvivalConstraints() {
		return constraints;
	}

	public void setBirthAndSurvivalConstraints(final BirthAndSurvivalConstraints constraints) {
		this.constraints.getLiveNeighborsRequiredForBirth().clear();
		this.constraints.getLiveNeighborsRequiredForSurvival().clear();

		for (Integer birthNeighbors : constraints.getLiveNeighborsRequiredForBirth()) {
			this.constraints.getLiveNeighborsRequiredForBirth().add(birthNeighbors);
		}

		for (Integer survivalNeighbors : constraints.getLiveNeighborsRequiredForSurvival()) {
			this.constraints.getLiveNeighborsRequiredForSurvival().add(survivalNeighbors);
		}
	}
}
