package cs5004.animator.model;

import java.awt.Color;
import java.util.List;
import java.util.Map;

/**
 * Interface for the animation model.
 */
public interface AnimationModel {

  int[] getScreenAttributes();
  /**
   * Moves the shape object from one position to another.
   *
   * @param name name of shape to move
   * @param fromPos from position of shape
   * @param toPos position to move to
   * @param fromTime start time of motion
   * @param toTime end time of motion
   * @throws IllegalArgumentException If the time interval overlaps with another motion interval
   *                                  or if it is out of bounds
   */
  void move(String name, int[] fromPos, int[] toPos,  int fromTime, int toTime)
          throws IllegalArgumentException;

  /**
   * Changes the color of the shape object.
   *
   * @param name name of shape to move
   * @param fromColor previous color of shape
   * @param toColor new color of shape
   * @param fromTime start time of motion
   * @param toTime end time of motion
   * @throws IllegalArgumentException If the time interval overlaps with another change color
   *                                  interval or if it is out of bounds
   */
  void changeColor(String name, Color fromColor, Color toColor, int fromTime, int toTime)
          throws IllegalArgumentException;

  /**
   * Changes the dimension(s) of the shape object.
   *
   * @param name name of shape to move
   * @param fromDim previous dimensions(s) of shape
   * @param toDim new dimensions(s) of shape
   * @param fromTime start time of motion
   * @param toTime end time of motion
   * @throws IllegalArgumentException If the time interval overlaps with another change dimensions
   *                                  interval or if it is out of bounds
   */
  void changeDimension(String name, int[] fromDim, int[] toDim, int fromTime, int toTime)
          throws IllegalArgumentException;

  /**
   * Adds a shape object to the stage. This object is stored in a map.
   *
   * @param shapeType a ShapeType object representing the type of shape it is (e.g. RECTANGLE)
   * @param name  a unique string representing the name of the object
   * @param pos   an array of integer representing the initial position of the shape one the stage
   * @param color  A Color object representing the color of this object
   * @param dimension1 an int representing the horizontal dimension of this object
   * @param dimension2 an int representing the vertical dimension of this object
   * @param appearance an int representing the time when the given shape should appear
   * @param disappearance an int representing the time when the given shape should disappear
   * @throws IllegalArgumentException if any of the arguments ar out of bounds
   */
  void addShape(ShapeType shapeType, String name, int[] pos, Color color, int dimension1,
                int dimension2) throws IllegalArgumentException;

  /**
   * Gets the shape object at a given point in time.
   *
   * @param name  a unique string representing the name of the shape object
   * @param time  an int representing the time to get the given shape object
   * @return an IShape from the given point in time
   */
  IShape getObjectAtTime(String name, int time);

  /**
   * Gets a string representation of all the shape objects at a given point in time.
   *
   * @param time  an int representing the time to get the given shape objects
   * @return a List of strings, each representing an IShape from the given point in time
   */
  List<String> getAllObjectNamesAtTime(int time);

  /**
   * Undoes the given animation of the given shape object at the given time interval.
   *
   * @param name  a unique string representing the name of the shape object
   * @param type an AnimationType representing thhe type of animation to undo
   * @param fromTime an int representing the start time of the animation to undo
   * @param toTime an int representing the end time of the animation to undo
   * @throws IllegalArgumentException if time interval for the given animation type doesn't exist
   */
  void undoAnimation(String name, AnimationType type, int fromTime, int toTime)
          throws IllegalArgumentException;

  /**
   * Gets a string representation of all animation after all the animations and shapes have been
   * stored.
   *
   * @return a strings representing all animation after all the animations and shapes have been
   *         stored.
   */
  String showAnimationText();

  int getLastFrameNumber();

  void setBounds(int topLeftX, int topLeftY, int width, int height);

  Map<String, ObjectTimelineProp> getObjectNameMap();

  // void removeShape();
}
