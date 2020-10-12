package cs5004.animator.view;

import java.awt.*;

import cs5004.animator.model.IShape;

public class Rectangle extends ViewAbstractShape {

  public Rectangle(int[] pos, int[] dimensions, Color color) {
    super(pos, dimensions, color);
  }

  public Rectangle(IShape shape) {
    super(shape);
  }
  @Override
  public void draw(Graphics g) {
    g.setColor(color);
    g.fillRect(pos[0], pos[1], dimensions[0], dimensions[1]);
  }
}
