package cs5004.animator.model;

/**
 * This enum contains all the types of animations an IShape can have.
 */
public enum AnimationType {

  MOTION(0, "Motion"),
  COLOR(1, "Color"),
  SCALE(2, "Scale");

  private final int value;
  private final String text;

  AnimationType(int value, String text) {
    this.value = value;
    this.text = text;
  }

  int getValue() {
    return this.value;
  }

  String getText() {
    return this.text;
  }
}
