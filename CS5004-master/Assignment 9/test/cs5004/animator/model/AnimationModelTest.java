package cs5004.animator.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.IShape;
import cs5004.animator.model.Rectangle;
import cs5004.animator.model.ShapeType;

/**
 * A JUnit test class for the  class.
 */
public class AnimationModelTest {
  private AnimationModel model1;

  @Before
  public void setup() {
    // legal constructor input
    try {
      // regular model
//      model1 = new AnimationModelImpl(0, 0, 600, 400, Color.WHITE);

      //model with nul color object defaults to white background
//      AnimationModel model2 = new AnimationModelImpl(0, 0, 600, 400,
//              null);
      AnimationModel model1 = new AnimationModelImpl();
      AnimationModel model2 = new AnimationModelImpl();
    } catch (IllegalArgumentException e) {
      fail("An exception should not have been thrown");
    }

//    // illegal row input in the constructor (screen width less than 1)
//    try {
//      AnimationModel model3 = new AnimationModelImpl(0, 0, 0, 400,
//              Color.WHITE);
//      fail("An exception should have been thrown");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//    // illegal row input in the constructor (screen height less than 1)
//    try {
//      AnimationModel model4 = new AnimationModelImpl(0, 0, 600, 0,
//              Color.WHITE);
//      fail("An exception should have been thrown");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }

    model1.addShape(ShapeType.RECTANGLE, "rect1", new int[]{50, 100}, Color.WHITE,
            20, 10);

  }

 /* @Test
  public void testInitialPosition1() {
    Rectangle rect = (Rectangle) model1.getObjectAtTime("rect1", 10);
    assertEquals(50, rect.getPosition()[0]);
    assertEquals(100, rect.getPosition()[1]);

    rect = (Rectangle) model1.getObjectAtTime("rect1", 50);
    assertEquals(50, rect.getPosition()[0]);
    assertEquals(100, rect.getPosition()[1]);
  }*/

  @Test
  public void testMove1() {
    model1.move("rect1", new int[]{50, 100}, new int[]{20, 20}, 20, 50);

    Rectangle rect = (Rectangle) model1.getObjectAtTime("rect1", 50);

    assertEquals(20, rect.getPosition()[0]);
    assertEquals(20, rect.getPosition()[1]);

    rect = (Rectangle) model1.getObjectAtTime("rect1", 35);

    assertEquals(35, rect.getPosition()[0]);
    assertEquals(60, rect.getPosition()[1]);
  }

  // Two moves (consecutive)
  @Test
  public void testSequenceOfAnimations() {
    model1.move("rect1", new int[]{50, 100}, new int[]{20, 20}, 20, 50);
    model1.move("rect1", new int[]{20, 20}, new int[]{100, 100}, 50, 60);

    Rectangle rect = (Rectangle) model1.getObjectAtTime("rect1", 50);

    assertEquals(20, rect.getPosition()[0]);
    assertEquals(20, rect.getPosition()[1]);

    rect = (Rectangle) model1.getObjectAtTime("rect1", 60);

    assertEquals(100, rect.getPosition()[0]);
    assertEquals(100, rect.getPosition()[1]);

    assertEquals("Shapes:\n" +
                    "Name: rect1\n" +
                    "Type: RECTANGLE\n" +
                    "BL Corner: (50, 100), Length: 20, Width: 10, Color: (255, 255, 255)\n" +
                    "Appears: at t = 20\n" +
                    "Disappears at t = 60\n" +
                    "\n" +
                    "Shape rect1 moves from (50, 100) to (20, 20) from t = 20 to t = 50\n" +
                    "Shape rect1 moves from (20, 20) to (100, 100) from t = 50 to t = 60\n",
            model1.showAnimationText());
  }

  // two moves (overlapping)
  @Test (expected = IllegalArgumentException.class)
  public void testOverlappingMoves() {
    model1.move("rect1", new int[]{50, 100}, new int[]{20, 20}, 20, 50);
    model1.move("rect1", new int[]{20, 20}, new int[]{100, 100}, 30, 60);
  }

  // two consecutive but inconsistent moves
  @Test (expected = IllegalArgumentException.class)
  public void testInconsistentMoves() {
    model1.move("rect1", new int[]{50, 100}, new int[]{20, 20}, 20, 50);
    model1.move("rect1", new int[]{30, 20}, new int[]{100, 100}, 30, 60);
  }


  // tests sequence of color changes
  @Test
  public void testSequenceOfColor() {
    model1.changeColor("rect1", Color.WHITE, Color.RED, 10, 20);
    model1.changeColor("rect1", Color.GRAY, Color.PINK, 30, 40);

    IShape shape10  = model1.getObjectAtTime("rect1", 10);
    IShape shape15 = model1.getObjectAtTime("rect1", 15);
    IShape shape20 = model1.getObjectAtTime("rect1", 20);
    IShape shape25 = model1.getObjectAtTime("rect1", 25);
    IShape shape30 = model1.getObjectAtTime("rect1", 30);

    assertEquals(Color.WHITE, shape10.getColor());
    assertEquals(new Color(255, 128, 128), shape15.getColor());
    assertEquals(Color.RED, shape20.getColor());
    assertEquals(Color.RED, shape25.getColor());
    assertEquals(Color.GRAY, shape30.getColor());
  }

  // test overlap of changeColor and move
  @Test
  public void testOverlapColorMove() {
    model1.move("rect1", new int[]{20, 20}, new int[]{30, 30}, 20, 30);
    model1.changeColor("rect1", Color.GREEN, Color.PINK, 25, 35);

    IShape shape20 = model1.getObjectAtTime("rect1", 20);
    IShape shape25 = model1.getObjectAtTime("rect1", 25);
    IShape shape30 = model1.getObjectAtTime("rect1", 30);
    IShape shape35 = model1.getObjectAtTime("rect1", 35);

    assertArrayEquals(new int[]{20, 20}, shape20.getPosition());
    assertEquals(Color.WHITE, shape20.getColor());
    assertArrayEquals(new int[]{25, 25}, shape25.getPosition());
    assertEquals(Color.GREEN, shape25.getColor());
    assertArrayEquals(new int[]{30, 30}, shape30.getPosition());
    assertEquals(new Color(127, 215, 87), shape30.getColor());
    assertArrayEquals(new int[]{30, 30}, shape35.getPosition());
    assertEquals(Color.PINK, shape35.getColor());
  }

  // test overlap of rescale and move
  @Test
  public void testOverlapReScaleMove() {
    model1.move("rect1", new int[]{20, 20}, new int[]{30, 30}, 20, 30);
    model1.changeDimension("rect1", new int[]{10, 10}, new int[]{20, 20}, 25,
            35);

    IShape shape20 = model1.getObjectAtTime("rect1", 20);
    IShape shape25 = model1.getObjectAtTime("rect1", 25);
    IShape shape30 = model1.getObjectAtTime("rect1", 30);
    IShape shape35 = model1.getObjectAtTime("rect1", 35);

    assertArrayEquals(new int[]{20, 20}, shape20.getPosition());
    assertArrayEquals(new int[]{20, 10}, shape20.getDimensions());
    assertArrayEquals(new int[]{25, 25}, shape25.getPosition());
    assertArrayEquals(new int[]{10, 10}, shape25.getDimensions());
    assertArrayEquals(new int[]{30, 30}, shape30.getPosition());
    assertArrayEquals(new int[]{15, 15}, shape30.getDimensions());
    assertArrayEquals(new int[]{30, 30}, shape35.getPosition());
    assertArrayEquals(new int[]{20, 20}, shape35.getDimensions());
  }

  // test Overlap of color and rescale
  @Test
  public void testOverlapRescaleColor() {
    model1.changeColor("rect1", Color.GREEN, Color.PINK, 25, 35);
    model1.changeDimension("rect1", new int[]{10, 10}, new int[]{20, 20}, 25,
            35);

    IShape shape25 = model1.getObjectAtTime("rect1", 25);
    IShape shape35 = model1.getObjectAtTime("rect1", 35);

    assertArrayEquals(new int[]{10, 10}, shape25.getDimensions());
    assertEquals(Color.GREEN, shape25.getColor());
    assertArrayEquals(new int[]{20, 20}, shape35.getDimensions());
    assertEquals(Color.PINK, shape35.getColor());
  }

  // random usage of model, testing getAllObjectNames and get getObjectAtTime
  @Test
  public void testRandom1() {
    model1.move("rect1", new int[]{50, 100}, new int[]{30, 30}, 20, 30);
    model1.addShape(ShapeType.OVAL, "oval1", new int[]{60, 60}, Color.BLUE, 10,
            20);
    model1.addShape(ShapeType.TRIANGLE, "tri1", new int[]{80, 80}, Color.BLACK, 15,
            10);

    assertEquals(new ArrayList<String>(Arrays.asList("rect1")),
            model1.getAllObjectNamesAtTime(20));
    assertEquals(new ArrayList<String>(Arrays.asList()),
            model1.getAllObjectNamesAtTime(15));

    model1.move("rect1", new int[]{50, 50}, new int[]{30, 45}, 40, 50);
    model1.move("oval1", new int[]{70, 70}, new int[]{80, 80}, 40, 50);

    assertEquals(new ArrayList<String>(Arrays.asList("rect1", "oval1")),
            model1.getAllObjectNamesAtTime(45));

    model1.changeColor("oval1", Color.GREEN, Color.RED, 40, 50);
    model1.changeDimension("tri1", new int[]{15, 10}, new int[]{20, 5}, 5, 10);

    IShape ovalt50 = model1.getObjectAtTime("oval1", 50);
    assertEquals(Color.RED, ovalt50.getColor());
    assertArrayEquals(new int[]{80, 80}, ovalt50.getPosition());

    IShape rectt50 = model1.getObjectAtTime("rect1", 50);
    assertArrayEquals(new int[]{30, 45}, rectt50.getPosition());

    model1.move("rect1", new int[]{20, 20}, new int[]{30, 30}, 35, 38);

    String expected = "Shapes:\n" +
            "Name: rect1\n" +
            "Type: RECTANGLE\n" +
            "BL Corner: (50, 100), Length: 20, Width: 10, Color: (255, 255, 255)\n" +
            "Appears: at t = 20\n" +
            "Disappears at t = 50\n" +
            "\n" +
            "Name: oval1\n" +
            "Type: OVAL\n" +
            "Center: (60, 60), X Radius: 10, Y Radius: 20, Color: (0, 0, 255)\n" +
            "Appears: at t = 40\n" +
            "Disappears at t = 50\n" +
            "\n" +
            "Name: tri1\n" +
            "Type: TRIANGLE\n" +
            "Min corner: (80, 80), Base: 15, Height: 10, Color: (0, 0, 0)\n" +
            "Appears: at t = 5\n" +
            "Disappears at t = 10\n" +
            "\n" +
            "Shape tri1 scales from Base: 15, Height: 10 to Base: 20, Height: 5from t = 5 to t = 10"
            + "\nShape rect1 moves from (50, 100) to (30, 30) from t = 20 to t = 30\n" +
            "Shape rect1 moves from (20, 20) to (30, 30) from t = 35 to t = 38\n" +
            "Shape rect1 moves from (50, 50) to (30, 45) from t = 40 to t = 50\n" +
            "Shape oval1 moves from (70, 70) to (80, 80) from t = 40 to t = 50\n" +
            "Shape oval1 changes color from (0, 255, 0) to (255, 0, 0) from t = 40 to t = 50\n";
    assertEquals(expected, model1.showAnimationText());
  }
}