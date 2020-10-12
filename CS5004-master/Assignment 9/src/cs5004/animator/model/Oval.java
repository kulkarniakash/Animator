package cs5004.animator.model;

import java.awt.Color;

/**
 * This concrete class extends AbstractShape, and represents an Oval shape. Each Oval has a name,
 * color, x radius, y radius, a type, and starting position at the left
 */
public class Oval extends AbstractShape {
  private final int xRadius;
  private final int yRadius;
  private final ShapeType shapeType = ShapeType.OVAL;

  /**
   * Inherited by IShape objects, which initializes the Oval shape object to the given name,
   * position, dimensions, and color.
   * @param name   a unique string representing the name of this object
   * @param xPos   an integer representing the x coordinate of the initial position on a coordinate
   *               graph
   * @param yPos   an integer representing the y coordinate of the initial position on a coordinate
   *               graph
   * @param xRadius   an int representing the horizontal dimension (xRadius) of this object
   * @param yRadius an int representing the vertical dimension (yRadius) of this object
   * @param color  A Color object representing the color of this object
   * @throws IllegalArgumentException if the given Color object is null or the name string value has
   *                                  no characters or one of the dimensions is non-positive
   */
  public Oval(String name, int xPos, int yPos, int xRadius, int yRadius, Color color) {
    super(name, xPos, yPos, xRadius, yRadius, color);
    this.xRadius = xRadius;
    this.yRadius = yRadius;
  }

  @Override
  public ShapeType getType() {
    return this.shapeType;
  }

  @Override
  public int[] getDimensions() {
    return new int[]{this.xRadius, this.yRadius};
  }

  @Override
  public IShape changePosition(int newXPos, int newYPos) {
    return new Oval(this.name, newXPos, newYPos, this.xRadius, this.yRadius, this.getColor());
  }

  @Override
  public IShape changeDimension(int[] newDimensions) throws IllegalArgumentException {
    if (newDimensions == null || newDimensions.length != 2) {
      throw new IllegalArgumentException("Please put in valid input");
    }
    return new Oval(this.name, this.xPos, this.yPos, newDimensions[0], newDimensions[1],
            this.getColor());
  }

  @Override
  public IShape changeColor(Color color) {
    try {
      Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
      return new Oval(this.name, this.xPos, this.yPos, this.xRadius, this.yRadius, newColor);
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public String toString() {
    return String.format("Center: (%d, %d), X Radius: %d, Y Radius: %d, Color: (%d, %d, %d)",
            this.xPos, this.yPos, this.xRadius, this.yRadius, this.color.getRed(),
            this.color.getGreen(), this.color.getBlue());
  }

}
