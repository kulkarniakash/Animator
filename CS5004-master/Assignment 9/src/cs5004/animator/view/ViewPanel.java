package cs5004.animator.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cs5004.animator.model.IShape;

public class ViewPanel extends JPanel {

  private List<IViewShape> shapes;

  public ViewPanel(int width, int height) {
    shapes = new ArrayList<>();
  }

  public void updatePanel(List<IViewShape> shapes) {
    this.shapes = shapes;
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    for (IViewShape shape : shapes) {
      shape.draw(g2);
    }
  }
}
