package cellular_automata.graphics.board;

import cellular_automata.cells.CellMatrix;
import cellular_automata.cells.CellState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class Board extends Canvas {
  private CellMatrix cells;
  private double cellWidth;
  private double cellHeight;
  private double workingCellWidth;
  private double workingCellHeight;
  private boolean showGridLines;
  private boolean boardSetUp;
  private static final double lineWidth = 1.0;

  public void setUp(final int boardWidth, final int boardHeight, final int cellWidth, final int cellHeight) {
    setWidth(boardWidth);
    setHeight(boardHeight);

    this.cellWidth = cellWidth;
    this.cellHeight = cellHeight;
    this.workingCellWidth = this.cellWidth;
    this.workingCellHeight = this.cellHeight;

    final int cellMatrixWidth = computeCellMatrixWidth();
    final int cellMatrixHeight = computeCellMatrixHeight();

    cells = new CellMatrix(cellMatrixWidth, cellMatrixHeight);

    setOnMouseClicked(event -> {
      final int convertedX = (int) (event.getX() / workingCellWidth);
      final int convertedY = (int) (event.getY() / workingCellHeight);
      cells.getCell(convertedX, convertedY).toggleState();
      cells.lockCurrentStateAsSeed();
      drawCells();
    });

    showGridLines = true;
    boardSetUp = true;
    
    drawCells();
  }

  private int computeCellMatrixWidth() {
    return (int) (getHeight() / workingCellWidth);
  }

  private int computeCellMatrixHeight() {
    return (int) (getWidth() / workingCellHeight);
  }

  public void drawCells() {
    if (!boardSetUp) throw new BoardNotSetUpRuntimeException();
    
    final GraphicsContext gc = getGraphicsContext2D();

    gc.setFill(Color.WHITE);
    gc.fillRect(0.0, 0.0, getWidth(), getHeight());

    final Pair<Double, Double> computedCellDimensions = drawConditionalGridAndGetCellDimensions();

    final double cellDrawWidth = computedCellDimensions.getKey();
    final double cellDrawHeight = computedCellDimensions.getValue();

    cells.forEach((x, y, cell) -> {
      final double boardX = computeBoardX(x);
      final double boardY = computeBoardY(y);

      if (CellState.DEAD.equals(cell.getState())) {
        gc.setFill(Color.WHITE);
      } else {
        gc.setFill(Color.BLACK);
      }

      gc.fillRect(boardX, boardY, cellDrawWidth, cellDrawHeight);
    });
  }

  private Pair<Double, Double> drawConditionalGridAndGetCellDimensions() {
    final GraphicsContext gc = getGraphicsContext2D();

    var cellWidth = workingCellWidth;
    var cellHeight = workingCellHeight;

    if (showGridLines) {
      cellWidth -= 2.0 * lineWidth;
      cellHeight -= 2.0 * lineWidth;
      drawGridLines(gc);
    }

    return new Pair<>(cellWidth, cellHeight);
  }

  private void drawGridLines(final GraphicsContext gc) {
    gc.setStroke(Color.LIGHTBLUE);
    gc.setLineWidth(lineWidth);
    gc.beginPath();

    for (var x = 0.0; x <= getWidth(); x += workingCellWidth) {
      gc.moveTo(x, 0.0);
      gc.lineTo(x, getHeight());
    }

    for (var y = 0.0; y <= getHeight(); y += workingCellHeight) {
      gc.moveTo(0.0, y);
      gc.lineTo(getWidth(), y);
    }

    gc.stroke();
  }

  private double computeBoardX(final int cellX) {
    var boardX = (double) (cellX * workingCellWidth);

    if (showGridLines) {
      boardX += lineWidth;
    }

    return boardX;
  }

  private double computeBoardY(final int cellY) {
    var boardY = (double) (cellY * workingCellHeight);

    if (showGridLines) {
      boardY += lineWidth;
    }

    return boardY;
  }

  public void toggleGridLines() {
    showGridLines = !showGridLines;
  }

  public boolean getShowGridLines() {
    return showGridLines;
  }

  public void setShowGridLines(final boolean showGridLines) {
    this.showGridLines = showGridLines;
  }

  public CellMatrix getCells() {
    return cells;
  }
}
