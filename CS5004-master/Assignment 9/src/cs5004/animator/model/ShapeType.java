package cs5004.animator.model;

/**
 * An enum that defines enum values corresponding to different shapes.
 */
public enum ShapeType {

  RECTANGLE(0, "Rectangle"),
  TRIANGLE(1, "Triangle"),
  OVAL(2, "Oval");

  private final int value;
  private final String type;

  ShapeType(int value, String type) {
    this.value = value;
    this.type = type;
  }

  int getValue() {
    return this.value;
  }

  String getType() {
    return this.type;
  }
}
