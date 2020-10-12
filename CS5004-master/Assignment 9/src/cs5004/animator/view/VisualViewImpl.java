package cs5004.animator.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;

import cs5004.animator.model.AnimationType;
import cs5004.animator.model.ShapeType;

// Sought help from: https://www.javatpoint.com/Graphics-in-swing
public class VisualViewImpl implements AnimationView {
  private int topLeftX;
  private int getTopLeftY;
  private int screenWidth;
  private int screenHeight;
  private JFrame frame;
  private ViewPanel panel;
  private List<IViewShape> shapes;
  private JScrollPane mainScrollPane;

  public VisualViewImpl() {
    frame = new JFrame();
    shapes = new ArrayList<>();
  }

  @Override
  public void screenInit(int topLeftX, int topLeftY, int width, int height) {
    this.topLeftX = topLeftX;
    this.getTopLeftY = topLeftY;
    this.screenWidth = width;
    this.screenHeight = height;

    panel = new ViewPanel(width, height);
    mainScrollPane = new JScrollPane(panel);
    frame.setPreferredSize(new Dimension(width, height));
    panel.setPreferredSize(new Dimension(width, height));
    frame.add(mainScrollPane, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(topLeftX, topLeftY, width, height);
    frame.setTitle("Easy Animator");
  }

  @Override
  public void renderAllShapes(List<IViewShape> shapes) {
    frame.add(panel);
    panel.updatePanel(shapes);
    frame.setVisible(true);
  }

  @Override
  public void clearScreen() {
    panel.updatePanel(new ArrayList<>());
  }


  @Override
  public void createShape(String name, ShapeType shapeType, Color color, int[] dimensions,
                          int[] pos, int[] existence) throws OperationNotSupportedException {
    throw new OperationNotSupportedException("You cannot use this method for this class.");
  }

  @Override
  public void renderShape(AnimationType animationType, String name, ShapeType shapeType,
                          int[] fromAttribute, int[] toAttribute, Integer[] timeInterval)
          throws OperationNotSupportedException {
    throw new OperationNotSupportedException("You cannot use this method for this class.");
  }

  @Override
  public void renderShape(AnimationType animationType, String name, ShapeType shapeType,
                          Color fromAttribute, Color toAttribute, Integer[] timeInterval)
          throws OperationNotSupportedException {
    throw new OperationNotSupportedException("You cannot use this method for this class.");
  }

  @Override
  public void output() throws OperationNotSupportedException {
    throw new OperationNotSupportedException("You cannot use this method for this class.");
  }
}
