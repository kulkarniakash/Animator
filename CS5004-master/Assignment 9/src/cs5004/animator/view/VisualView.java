package cs5004.animator.view;

import java.util.List;

import cs5004.animator.model.IShape;

public interface VisualView {

  void screenInit(int topLeftX, int topLeftY, int width, int height);

  void renderAllShapes(List<IViewShape> shapes);

  void clearScreen();

}
