package cs5004.animator.model;

import java.awt.Color;

/**
 * This interface contains all operations that all shapes should support.
 */
public interface IShape {

  /**
   * Gets the name of the shape.
   *
   * @return a unique string representing the name of the shape
   */
  String getName();

  /**
   * Gets the type of the shape.
   *
   * @return a ShapeType representing the shape's type (e.g. RECTANGLE)
   */
  ShapeType getType();

  /**
   * Gets the position of the shape.
   *
   * @return an int array representing the position of the shape on a coordinate graph
   */
  int[] getPosition();

  /**
   * Gets the color of the shape.
   *
   * @return a Color from the built-in Java class representing the color of the shape
   */
  Color getColor();


  /**
   * Gets the dimensions of the shape.
   *
   * @return an int array representing the shape's horizontal and vertical dimensions
   */
  int[] getDimensions();

  /**
   * Changes the color of the shape.
   *
   * @param color a Color object representing the color to change the shape to
   * @return a new shape with all the same properties except the changed color
   * @throws IllegalArgumentException if the given Color object is null
   */
  IShape changeColor(Color color) throws IllegalArgumentException;

  /**
   * Changes the position of the shape.
   *
   * @param row the row of the position it wants to move to
   * @param col the col of the position it wants to move to
   * @return a new shape with all the same properties except the changed position
   */
  IShape changePosition(int row, int col);

  /**
   * Changes a dimension of the shape.
   *
   * @param newDimensions an array representing the new dimension(s) of the shape
   * @return a new shape with all the same properties except the changed dimension(s)
   * @throws IllegalArgumentException if the given dimensions array is null or the incorrect length
   */
  IShape changeDimension(int[] newDimensions) throws IllegalArgumentException;

  /**
   * Gets the attributes of the shape as a string.
   *
   * @return a string representing the shape's attributes
   */
  String toString();
}
