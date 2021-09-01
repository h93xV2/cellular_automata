package cellular_automata.filemanagement.parsers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

enum LineType {
  COMMENT_TYPE_ONE("#C"),
  COMMENT_TYPE_TWO("#c"),
  PATTERN_NAME("#N"),
  AUTHOR_INFORMATION("#O"),
  TOP_LEFT_CORNER_TYPE_ONE("#P"),
  TOP_LEFT_CORNER_TYPE_TWO("#R"),
  CELL_RULES("#r");

  private String rleLineMarker;
  private static Map<String, LineType> patternMap;

  private LineType(final String pattern) {
    this.rleLineMarker = pattern;
  }

  String getRleLineMarker() {
    return rleLineMarker;
  }

  static Map<String, LineType> getRleLineMarkerToLineTypeMap() {
    if (patternMap == null) {
      populateRleLineMarkerToLineTypeMap();
    }

    return patternMap;
  }

  private static void populateRleLineMarkerToLineTypeMap() {
    final Map<String, LineType> newPatternMap = new HashMap<>();

    for (LineType lineType : LineType.values()) {
      newPatternMap.put(lineType.getRleLineMarker(), lineType);
    }

    patternMap = Collections.unmodifiableMap(newPatternMap);
  }
}
