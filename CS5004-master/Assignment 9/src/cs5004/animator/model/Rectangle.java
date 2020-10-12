package cs5004.animator.model;

import java.awt.Color;

/**
 * This concrete class extends AbstractShape, and represents a Rectangle shape. Each Rectangle has a
 * name, color, length, width, a type, and starting position at the bottom left corner of the shape.
 */
public class Rectangle extends AbstractShape {
  private final int length;
  private final int width;
  private final ShapeType shapeType = ShapeType.RECTANGLE;

  /**
   * Inherited by IShape objects, which initializes the Rectangle shape object to the given name,
   * position, dimensions, and color.
   * @param name   a unique string representing the name of this object
   * @param xPos   an integer representing the x coordinate of the initial position on a coordinate
   *               graph
   * @param yPos   an integer representing the y coordinate of the initial position on a coordinate
   *               graph
   * @param length an int representing the horizontal dimension (length) of this object
   * @param width  an int representing the vertical dimension (width) of this object
   * @param color  A Color object representing the color of this object
   * @throws IllegalArgumentException if the given Color object is null or the name string value has
   *                                  no characters or one of the dimensions is non-positive
   */
  public Rectangle(String name, int xPos, int yPos, int length, int width, Color color)
          throws IllegalArgumentException {
    super(name, xPos, yPos, length, width, color);
    this.length = length;
    this.width = width;
  }

  @Override
  public ShapeType getType() {
    return this.shapeType;
  }

  @Override
  public int[] getDimensions() {
    return new int[]{this.length, this.width};
  }

  @Override
  public IShape changePosition(int newXPos, int newYPos) {
    return new Rectangle(this.name, newXPos, newYPos, this.length, this.width, this.getColor());
  }

  @Override
  public IShape changeDimension(int[] newDimensions) throws IllegalArgumentException {
    if (newDimensions == null || newDimensions.length != 2) {
      throw new IllegalArgumentException("Please put in valid input");
    }
    return new Rectangle(this.name, this.xPos, this.yPos, newDimensions[0], newDimensions[1],
            this.getColor());
  }

  @Override
  public IShape changeColor(Color color) {
    try {
      Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
      return new Rectangle(this.name, this.xPos, this.yPos, this.length, this.width, newColor);
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public String toString() {
    return String.format("BL Corner: (%d, %d), Length: %d, Width: %d, Color: (%d, %d, %d)",
            this.xPos, this.yPos, this.length, this.width, this.color.getRed(),
            this.color.getGreen(), this.color.getBlue());
  }

  /**
   * Gets the positions of the four corners of the rectangle, starting at the bottom left and moving
   * clockwise.
   *
   * @return a 2D array, each row representing an (x, y) coordinate
   */
  public int[][] getCorners() {
    int[][] corners = new int[4][2];

    corners[0][0] = this.xPos;
    corners[0][1] = this.yPos;

    corners[1][0] = this.xPos;
    corners[1][1] = this.yPos + this.width;

    corners[2][0] = this.xPos + this.length;
    corners[2][1] = this.yPos + this.width;

    corners[3][0] = this.xPos + this.length;
    corners[3][1] = this.yPos;

    return corners;
  }

}
