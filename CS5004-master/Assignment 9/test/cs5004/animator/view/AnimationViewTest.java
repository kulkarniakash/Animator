package cs5004.animator.view;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import cs5004.animator.AnimationReader;
import cs5004.animator.controller.AnimationController;
import cs5004.animator.controller.TextController;
import cs5004.animator.controller.VisualController;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.model.ShapeType;

public class AnimationViewTest {

  private List<IViewShape> shapes;

  @Before
  public void setup() {
    IViewShape rect1 = new Rectangle(new int[]{50, 50}, new int[]{20, 20}, Color.RED);
    shapes = new ArrayList<>();
    shapes.add(rect1);
  }

  @Test
  public void testRectangleOnScreen() throws OperationNotSupportedException {
    AnimationView view = new VisualViewImpl();
    view.screenInit(0, 0, 600, 400);
    view.renderAllShapes(shapes);

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new IllegalArgumentException();
    }

    view.clearScreen();

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testAnimation() throws OperationNotSupportedException {
    AnimationView view = new VisualViewImpl();
    view.screenInit(0, 0, 600, 400);
    AnimationModel model = new AnimationModelImpl();

    model.addShape(ShapeType.RECTANGLE, "rect1", new int[]{100, 100}, Color.RED,
            60, 60);
    model.move("rect1", new int[]{100, 100}, new int[]{200, 200}, 0, 400);
    model.changeColor("rect1", Color.RED, Color.WHITE, 0, 400);
    AnimationController controller = new VisualController(model, view, 24);
    controller.playAnimation();

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testBigBang() throws OperationNotSupportedException {
    AnimationModel model = new AnimationModelImpl();
    AnimationModelImpl.Builder builder = new AnimationModelImpl.Builder(model);
    Readable file;
    try {
      file = new FileReader("big-bang-big-crunch.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found");
    }
    AnimationReader.parseFile(file, builder);
    AnimationView view = new VisualViewImpl();
    AnimationController controller = new VisualController(model, view, 24);
    controller.playAnimation();

    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testBuildings() throws OperationNotSupportedException {
    AnimationModel model = new AnimationModelImpl();

    AnimationModelImpl.Builder builder = new AnimationModelImpl.Builder(model);
    Readable file;
    try {
      file = new FileReader("buildings.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found");
    }
    AnimationReader.parseFile(file, builder);
    AnimationView view = new VisualViewImpl();
    AnimationController controller = new VisualController(model, view, 24);
    controller.playAnimation();

    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testHanoi() throws OperationNotSupportedException {
    AnimationModel model = new AnimationModelImpl();

    AnimationModelImpl.Builder builder = new AnimationModelImpl.Builder(model);
    Readable file;
    try {
      file = new FileReader("hanoi.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found");
    }
    AnimationReader.parseFile(file, builder);
    AnimationView view = new VisualViewImpl();
    AnimationController controller = new VisualController(model, view, 24);
    controller.playAnimation();

    try {
      Thread.sleep(40000);
    } catch (InterruptedException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testToh3() throws OperationNotSupportedException {
    AnimationModel model = new AnimationModelImpl();

    AnimationModelImpl.Builder builder = new AnimationModelImpl.Builder(model);
    Readable file;
    try {
      file = new FileReader("toh-3.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found");
    }
    AnimationReader.parseFile(file, builder);
    AnimationView view = new VisualViewImpl();
    AnimationController controller = new VisualController(model, view, 24);
    controller.playAnimation();

    try {
      Thread.sleep(40000);
    } catch (InterruptedException e) {
      throw new IllegalArgumentException();
    }
  }

  @Test
  public void testTextView() throws OperationNotSupportedException {
    AnimationModel model = new AnimationModelImpl();
    AnimationModelImpl.Builder builder = new AnimationModelImpl.Builder(model);
    Readable file;
    try {
      file = new FileReader("toh-3.txt");
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found");
    }
    AnimationReader.parseFile(file, builder);
    AnimationView view = new TextViewImpl(null);
    AnimationController controller = new TextController(model, view, 24);
    controller.playAnimation();
  }
}