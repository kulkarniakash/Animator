package cs5004.animator.model;

import java.awt.Color;

/**
 * This concrete class extends AbstractShape, and represents a Triangle shape. Each Triangle is a
 * right triangle and has a name, color, base, height, a type, and starting position at the bottom
 * left corner of the shape.
 */
public class Triangle extends AbstractShape {
  private final int base;
  private final int height;
  private final ShapeType shapeType = ShapeType.TRIANGLE;

  /**
   * Inherited by IShape objects, which initializes the Triangle shape object to the given name,
   * position, dimensions, and color.
   *
   * @param name   a unique string representing the name of this object
   * @param xPos   an integer representing the x coordinate of the initial position on a coordinate
   *               graph
   * @param yPos   an integer representing the y coordinate of the initial position on a coordinate
   *               graph
   * @param base   an int representing the horizontal dimension (base) of this object
   * @param height an int representing the vertical dimension (height) of this object
   * @param color  A Color object representing the color of this object
   * @throws IllegalArgumentException if the given Color object is null or the name string value has
   *                                  no characters or one of the dimensions is non-positive
   */
  public Triangle(String name, int xPos, int yPos, int base, int height, Color color)
          throws IllegalArgumentException {
    super(name, xPos, yPos, base, height, color);
    this.base = base;
    this.height = height;
  }

  @Override
  public ShapeType getType() {
    return this.shapeType;
  }

  @Override
  public int[] getDimensions() {
    return new int[]{this.base, this.height};
  }

  @Override
  public IShape changePosition(int newXPos, int newYPos) {
    return new Triangle(this.name, newXPos, newYPos, this.base, this.height, this.getColor());
  }

  @Override
  public IShape changeDimension(int[] newDimensions) throws IllegalArgumentException {
    if (newDimensions == null || newDimensions.length != 2) {
      throw new IllegalArgumentException("Please put in valid input");
    }
    return new Triangle(this.name, this.xPos, this.yPos, newDimensions[0], newDimensions[1],
            this.getColor());
  }

  @Override
  public IShape changeColor(Color color) throws IllegalArgumentException {
    try {
      Color newColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
      return new Triangle(this.name, this.xPos, this.yPos, this.base, this.height, newColor);
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public String toString() {
    return String.format("Min corner: (%d, %d), Base: %d, Height: %d, Color: (%d, %d, %d)",
            this.xPos, this.yPos, this.base, this.height, this.color.getRed(),
            this.color.getGreen(), this.color.getBlue());
  }

  /**
   * Gets the positions of the three corners of the triangle, starting at the bottom left and moving
   * clockwise.
   *
   * @return a 2D array, each row representing an (x, y) coordinate
   */
  public int[][] getCorners() {
    int[][] corners = new int[3][2];

    corners[0][0] = this.xPos;
    corners[0][1] = this.yPos;

    corners[1][0] = this.xPos;
    corners[1][1] = this.yPos + this.height;

    corners[2][0] = this.xPos + this.base;
    corners[2][1] = this.yPos;

    return corners;
  }

}
