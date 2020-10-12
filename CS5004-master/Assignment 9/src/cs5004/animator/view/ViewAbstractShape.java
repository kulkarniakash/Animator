package cs5004.animator.view;

import java.awt.Color;

import cs5004.animator.model.IShape;

public abstract class ViewAbstractShape implements IViewShape {
  protected final int[] pos;
  protected final int[] dimensions;
  protected final Color color;

  public ViewAbstractShape(int[] pos, int[] dimensions, Color color) {
    this.pos = pos.clone();
    this.dimensions = dimensions.clone();
    this.color = new Color(color.getRed(), color.getGreen(), color.getBlue());
  }

  public ViewAbstractShape(IShape shape) {
    pos = shape.getPosition().clone();
    dimensions = shape.getDimensions().clone();
    color = shape.getColor();
  }

  @Override
  public int[] getPosition() {
    return pos.clone();
  }

  @Override
  public Color getColor() {
    return new Color(color.getRed(), color.getGreen(), color.getBlue());
  }

  @Override
  public int[] getDimensions() {
    return dimensions.clone();
  }
}
