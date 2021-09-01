package cellular_automata.filemanagement.data;

public class PatternPoint {
  private int x;
  private int y;

  public PatternPoint() {
    this(0, 0);
  }

  public PatternPoint(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(final int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(final int y) {
    this.y = y;
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof PatternPoint)) {
      return false;
    }
    
    if (this == obj) {
      return true;
    }

    final PatternPoint point = (PatternPoint) obj;

    return point.getX() == x && point.getY() == y;
  }
}
