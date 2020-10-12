package cs5004.animator.view;

import java.awt.*;

import cs5004.animator.model.AnimationType;
import cs5004.animator.model.ShapeType;

public interface TextView {

  void createShape(String name, ShapeType shapeType, Color color, int[] dimensions, int[] pos,
                   int[] existence);

  void renderShape(AnimationType animationType, String name,
                          ShapeType shapeType, int[] fromAttribute, int[] toAttribute,
                   Integer[] timeInterval);

  void renderShape(AnimationType animationType, String name,
                   ShapeType shapeType, Color fromAttribute, Color toAttribute,
                   Integer[] timeInterval);

  void output();
}
