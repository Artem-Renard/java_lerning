package ru.stqa.pft.sandbox.point;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandbox.Square;

public class PointTest {
  @Test
  public void testDistance () {
    Point t1 = new Point (0,0);
    Point t2 = new Point (0,3);
    t1.distance(t2);
    Assert.assertEquals(t1.distance(t2),3 );
  }

}
