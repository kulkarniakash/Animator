package cs5004.animator.view;

import java.awt.*;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import cs5004.animator.model.AnimationType;
import cs5004.animator.model.ShapeType;

public interface AnimationView {

  // for text view
  void createShape(String name, ShapeType shapeType, Color color, int[] dimensions, int[] pos,
                   int[] existence) throws OperationNotSupportedException;

  void renderShape(AnimationType animationType, String name,
                   ShapeType shapeType, int[] fromAttribute, int[] toAttribute,
                   Integer[] timeInterval) throws OperationNotSupportedException;

  void renderShape(AnimationType animationType, String name,
                   ShapeType shapeType, Color fromAttribute, Color toAttribute,
                   Integer[] timeInterval) throws OperationNotSupportedException;

  void output() throws OperationNotSupportedException;


  // for visual view
  void screenInit(int topLeftX, int topLeftY, int width, int height)
          throws OperationNotSupportedException;

  void renderAllShapes(List<IViewShape> shapes) throws OperationNotSupportedException;

  void clearScreen() throws OperationNotSupportedException;
}
