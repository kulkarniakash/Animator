package cs5004.animator;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.naming.OperationNotSupportedException;

import cs5004.animator.controller.AnimationController;
import cs5004.animator.controller.TextController;
import cs5004.animator.controller.VisualController;
import cs5004.animator.model.AnimationBuilder;
import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationModelImpl;
import cs5004.animator.view.AnimationView;
import cs5004.animator.view.IViewShape;
import cs5004.animator.view.TextView;
import cs5004.animator.view.TextViewImpl;
import cs5004.animator.view.VisualView;
import cs5004.animator.view.VisualViewImpl;

/**
 * This class represents the an animation, and is the driver of the entire Java EasyAnimator
 * application.
 */
public final class EasyAnimator {
  private static AnimationModel model;
  private static AnimationController controller;
  private static AnimationView view;

  /**
   * Initializes an animation by constructing a model of the game.
   * @param args an array of strings representing the user input in the command line
   */
  public static void main(String[] args) throws IOException, OperationNotSupportedException {
    // create the model
    model = new AnimationModelImpl();

    boolean viewTracker = false;
    boolean inFileTracker = false;
    String animationType = "";
    String outputFile = "";
    int speedTicks = 1;
    for (int i = 0; i < args.length; i += 2) {
      String argType = args[i];
      String argElement = args[i + 1];
      switch (argType) {
        case ("-in"):
          AnimationReader.parseFile(new FileReader(argElement),
                  new AnimationModelImpl.Builder(model));
          inFileTracker = true;
          continue;
        case("-out"):
          outputFile = argElement;
          continue;
        case("-view"):
          if (argElement.equalsIgnoreCase("text")) {
            animationType = "text";
            viewTracker = true;
          } else if (argElement.equalsIgnoreCase("visual")) {
            animationType = "visual";
            viewTracker = true;
          } else {
            throw new IllegalArgumentException("Please put in valid input for the view: text or "
                    + "visual");
          }
          continue;
        case("-speed"):
          speedTicks = Integer.parseInt(argElement);
          continue;
        default:
          throw new IllegalArgumentException("Please put in valid commands");
      }
    }

    // ensures that the required inputs, -view and -in, are there, and throws an exception if
    // they're not
    if (!(inFileTracker && viewTracker)) {
      throw new IllegalArgumentException("You must put in valid arguments for the input file and "
              + "the view");
    }

    // create the specific view and controller
    if (animationType.equals("text")) {
      if (outputFile.equals("")) {
        view = new TextViewImpl(null);
      } else {
        view = new TextViewImpl(new FileWriter(outputFile, true));
      }
      controller = new TextController(model, view, speedTicks);
      controller.playAnimation();
    } else if (animationType.equals("visual")) {
      view = new VisualViewImpl();
      controller = new VisualController(model, view, speedTicks);
      controller.playAnimation();
    }
  }

}
