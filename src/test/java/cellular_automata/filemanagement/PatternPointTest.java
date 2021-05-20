package cellular_automata.filemanagement;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PatternPointTest {
  @Test
  void pointIsZeroedOutByDefault() {
    final PatternPoint point = new PatternPoint();

    assertTrue(point.getX() == 0 && point.getY() == 0);
  }

  @Test
  void pointEqualsIsReflexive() {
    final PatternPoint point = new PatternPoint();

    assertEquals(point, point);
  }

  @Test
  void pointEqualsIsSymmetric() {
    final PatternPoint pointA = new PatternPoint();
    final PatternPoint pointB = new PatternPoint();

    assertTrue(pointA.equals(pointB) && pointB.equals(pointA));
  }

  @Test
  void pointEqualsIsTransitive() {
    final PatternPoint pointA = new PatternPoint(1, 3);
    final PatternPoint pointB = new PatternPoint(1, 3);
    final PatternPoint pointC = new PatternPoint(1, 3);

    assertTrue(pointA.equals(pointB) && pointB.equals(pointC) && pointC.equals(pointA));
  }

  @Test
  void pointEqualsIsFalseForNull() {
    final PatternPoint point = new PatternPoint();

    assertNotEquals(null, point);
  }

  @SuppressWarnings("unlikely-arg-type")
  @Test
  void pointIsNotEqualToSomeOtherObject() {
    final PatternPoint point = new PatternPoint();

    assertFalse(point.equals("hello"));
  }

  @Test
  void separatePointsAreNotEqual() {
    final PatternPoint pointA = new PatternPoint();
    final PatternPoint pointB = new PatternPoint(1, 3);

    assertFalse(pointA.equals(pointB));
  }
  
  @Test
  void similarPointsAreNotEqual() {
    final PatternPoint pointA = new PatternPoint();
    final PatternPoint pointB = new PatternPoint(0, 3);
    
    assertFalse(pointA.equals(pointB));
  }
  
  @Test
  void theValueCanBeSetForX() {
    final PatternPoint point = new PatternPoint();
    point.setX(3);
    
    assertEquals(3, point.getX());
  }
  
  @Test
  void theValueCanBeSetForY() {
    final PatternPoint point = new PatternPoint();
    point.setY(5);
    
    assertEquals(5, point.getY());
  }
}
