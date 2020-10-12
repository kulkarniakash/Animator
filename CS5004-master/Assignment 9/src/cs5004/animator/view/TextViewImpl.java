package cs5004.animator.view;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import cs5004.animator.model.AnimationType;
import cs5004.animator.model.ShapeType;

public class TextViewImpl implements AnimationView {

  private final OutputStreamWriter destination;
  private StringBuilder animationText;

  public TextViewImpl(OutputStreamWriter destination) {
    this.destination = destination;
    this.animationText = new StringBuilder();
  }

  @Override
  public void createShape(String name, ShapeType shapeType, Color color, int[] dimensions,
                          int[] pos, int[] existence) {
    animationText.append(String.format("Creates %s %s colored (%d, %d, %d) at (%d, %d) with " +
                    "dimensions (%d, %d)\nAppears at t = %d and disappears at t = %d\n",
            shapeType, name, color.getRed(), color.getGreen(), color.getBlue(), pos[0], pos[1],
            dimensions[0], dimensions[1], existence[0], existence[1]));
  }

  @Override
  public void renderShape(AnimationType animationType, String name, ShapeType shapeType,
                          int[] fromAttribute, int[] toAttribute, Integer[] timeInterval)
          throws OperationNotSupportedException {
    switch(animationType) {
      case MOTION:
        animationText.append(String.format("%s %s moves from position (%d, %d) to position (%d, %d)"
                        + "from t = %d to t = %d\n", shapeType.toString(), name,  fromAttribute[0],
                fromAttribute[1], toAttribute[0], toAttribute[1], timeInterval[0],
                timeInterval[1]));
        break;
      case SCALE:
        animationText.append(String.format("%s %s scales from dimensions (%d, %d) to dimensions "
                        + "(%d, %d) from t = %d to t = %d\n", shapeType.toString(), name,
                fromAttribute[0], fromAttribute[1], toAttribute[0], toAttribute[1], timeInterval[0],
                timeInterval[1]));
        break;
      default:
        throw new IllegalArgumentException("Animation type not found.");
    }
  }

  @Override
  public void renderShape(AnimationType animationType, String name, ShapeType shapeType,
                          Color fromAttribute, Color toAttribute, Integer[] timeInterval)
          throws OperationNotSupportedException {
    if (animationType != AnimationType.COLOR) {
      throw new IllegalArgumentException("Incompatible arguments and animation type");
    }

    animationText.append(String.format("%s %s changes color from (%d, %d, %d) to (%d, %d, %d)" +
                    " from t = %d to t = %d\n", shapeType.toString(), name, fromAttribute.getRed(),
            fromAttribute.getGreen(), fromAttribute.getBlue(), toAttribute.getRed(),
            toAttribute.getGreen(), toAttribute.getBlue(), timeInterval[0], timeInterval[1]));
  }

  @Override
  public void output() throws OperationNotSupportedException {
    if (destination == null) {
      System.out.println(animationText.toString());
      return;
    }
    try {
      //destination.append(animationText.toString());
      BufferedWriter out = new BufferedWriter(destination);
      out.write(animationText.toString());
      out.close();
    } catch (IOException e) {
      throw new IllegalStateException("Could not print.");
    }


  }

  @Override
  public void screenInit(int topLeftX, int topLeftY, int width, int height)
          throws OperationNotSupportedException {
    throw new OperationNotSupportedException("You cannot use this method for this class.");
  }

  @Override
  public void renderAllShapes(List<IViewShape> shapes) throws OperationNotSupportedException {
    throw new OperationNotSupportedException("You cannot use this method for this class.");
  }

  @Override
  public void clearScreen() throws OperationNotSupportedException {
    throw new OperationNotSupportedException("You cannot use this method for this class.");
  }

}
