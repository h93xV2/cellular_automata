package cellular_automata.persistence.rle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum LineType {
	COMMENT_TYPE_ONE("#C"),
	COMMENT_TYPE_TWO("#c"),
	PATTERN_NAME("#N"),
	AUTHOR_INFORMATION("#O"),
	TOP_LEFT_CORNER_TYPE_ONE("#P"),
	TOP_LEFT_CORNER_TYPE_TWO("#R"),
	CELL_RULES("#r");

	private String pattern;
	private static Map<String, LineType> patternMap;

	private LineType(final String pattern) {
		this.pattern = pattern;
	}

	String getPattern() {
		return pattern;
	}

	static Map<String, LineType> getPatternMap() {
		if (patternMap == null) {
			populatePatternMap();
		}

		return patternMap;
	}

	private static void populatePatternMap() {
		final Map<String, LineType> newPatternMap = new HashMap<>();

		for (LineType lineType : LineType.values()) {
			newPatternMap.put(lineType.getPattern(), lineType);
		}

		patternMap = Collections.unmodifiableMap(newPatternMap);
	}
}
