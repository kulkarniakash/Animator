package cs5004.animator.view;

import java.awt.*;

public interface IViewShape {
  int[] getPosition();
  Color getColor();
  int[] getDimensions();
  void draw(Graphics g);
}
