package cs5004.animator.model;

import java.awt.Color;

/**
 * This abstract class represents a shape, and implements the IShape interface. Each IShape has a
 * name, a color, and a starting position on a coordinate graph.
 */
public abstract class AbstractShape implements IShape {
  protected final String name;
  protected final Color color;
  protected final int xPos;
  protected final int yPos;

  /**
   * Inherited by IShape objects, which initializes the object to the given name, position, and
   * color.
   *
   * @param name       a unique string representing the name of the object
   * @param xPos       an integer representing the x coordinate of the initial position on a
   *                   coordinate graph
   * @param yPos       an integer representing the y coordinate of the initial position on a
   *                   coordinate graph
   * @param dimension1 an int representing the horizontal dimension of this object
   * @param dimension2 an int representing the vertical dimension of this object
   * @param color      A Color object representing the color of this object
   * @throws IllegalArgumentException if the given Color object is null or the name string value is
   *                                  invalid or one of the dimensions are non-positive
   */
  public AbstractShape(String name, int xPos, int yPos, int dimension1, int dimension2, Color color)
          throws IllegalArgumentException {
    if (color == null || name.trim().length() == 0 || name == null || dimension1 < 1
            || dimension2 < 1) {
      throw new IllegalArgumentException("You must put in valid arguments to create a shape "
               + "object.");
    }
    this.name = name;
    this.xPos = xPos;
    this.yPos = yPos;
    this.color = new Color(color.getRed(), color.getGreen(), color.getBlue());
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int[] getPosition() {
    return new int[]{this.xPos, this.yPos};
  }

  @Override
  public Color getColor() {
    return new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue());
  }

  @Override
  public abstract ShapeType getType();

  @Override
  public abstract int[] getDimensions();

  @Override
  public abstract IShape changeColor(Color color) throws IllegalArgumentException;

  @Override
  public abstract IShape changePosition(int row, int col);

  @Override
  public abstract IShape changeDimension(int[] newDimensions)
          throws IllegalArgumentException;

  @Override
  public abstract String toString();

}
