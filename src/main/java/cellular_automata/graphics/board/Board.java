package cellular_automata.graphics.board;

import cellular_automata.cells.CellMatrix;
import cellular_automata.cells.CellState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class Board extends Canvas {
  private static final double LINE_WIDTH = 1.0;

  private CellMatrix cells;
  private double cellWidth;
  private double cellHeight;
  private double workingCellWidth;
  private double workingCellHeight;
  private boolean showGridLines;
  private boolean boardSetUp;

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
      draw();
    });

    showGridLines = true;
    boardSetUp = true;

    draw();
  }

  private int computeCellMatrixWidth() {
    return (int) (getHeight() / workingCellWidth);
  }

  private int computeCellMatrixHeight() {
    return (int) (getWidth() / workingCellHeight);
  }

  public void draw() {
    if (!boardSetUp)
      throw new BoardNotSetUpRuntimeException();

    clearBoard();

    drawCells();
  }

  private void clearBoard() {
    final GraphicsContext gc = getGraphicsContext2D();

    gc.setFill(Color.WHITE);
    gc.fillRect(0.0, 0.0, getWidth(), getHeight());
  }

  private void drawCells() {
    final GraphicsContext gc = getGraphicsContext2D();

    final Pair<Double, Double> computedCellDimensions = drawConditionalGridAndGetCellDimensions();

    cells.forEach((x, y, cell) -> {
      final double boardX = computeBoardX(x);
      final double boardY = computeBoardY(y);

      if (CellState.DEAD.equals(cell.getState())) {
        gc.setFill(Color.WHITE);
      } else {
        gc.setFill(Color.BLACK);
      }

      gc.fillRect(boardX, boardY, computedCellDimensions.getKey(), computedCellDimensions.getValue());
    });
  }

  private Pair<Double, Double> drawConditionalGridAndGetCellDimensions() {
    final GraphicsContext gc = getGraphicsContext2D();

    var drawnCellWidth = workingCellWidth;
    var drawnCellHeight = workingCellHeight;

    if (showGridLines) {
      drawnCellWidth -= 2.0 * LINE_WIDTH;
      drawnCellHeight -= 2.0 * LINE_WIDTH;
      drawGridLines(gc);
    }

    return new Pair<>(drawnCellWidth, drawnCellHeight);
  }

  private void drawGridLines(final GraphicsContext gc) {
    gc.setStroke(Color.LIGHTBLUE);
    gc.setLineWidth(LINE_WIDTH);
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
      boardX += LINE_WIDTH;
    }

    return boardX;
  }

  private double computeBoardY(final int cellY) {
    var boardY = (double) (cellY * workingCellHeight);

    if (showGridLines) {
      boardY += LINE_WIDTH;
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
