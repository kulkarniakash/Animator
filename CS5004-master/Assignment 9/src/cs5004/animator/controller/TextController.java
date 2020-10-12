package cs5004.animator.controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.OperationNotSupportedException;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.AnimationType;
import cs5004.animator.model.IShape;
import cs5004.animator.model.ObjectTimelineProp;
import cs5004.animator.view.AnimationView;
import cs5004.animator.view.TextView;

public class TextController implements AnimationController {

  private final AnimationModel model;
  private final AnimationView view;
  private final int fps;

  public TextController(AnimationModel model, AnimationView view, int fps) {
    this.model = model;
    this.view = view;
    this.fps = fps;
  }

  @Override
  public void playAnimation() throws OperationNotSupportedException {
    Map<String, ObjectTimelineProp> shapes = model.getObjectNameMap();
    for (String name : shapes.keySet()) {
      ObjectTimelineProp objT = shapes.get(name);
      IShape shape = model.getObjectAtTime(name, objT.getAppearance() );
      view.createShape(name, shape.getType(), shape.getColor(), shape.getDimensions(),
              shape.getPosition(), new int[]{shapes.get(name).getAppearance(),
                      shapes.get(name).getDisappearance()});
    }

    List<String> motionNames = new ArrayList<>();
    List<String> colorNames = new ArrayList<>();
    List<String> scaleNames = new ArrayList<>();
    List<Integer[]> motionIntervals = new ArrayList<>();
    List<Integer[]> colorIntervals = new ArrayList<>();
    List<Integer[]> scaleIntervals = new ArrayList<>();

    for (String name : shapes.keySet()) {
      sortIntervals(shapes.get(name).getMotionIntervals(), name, motionIntervals, motionNames);
    }

    List<Integer[]> motionCopy = copyInterval(motionIntervals);
    List<String> motionNamesCopy = copyInterval(motionNames, "copy");
    int j = 0;
    for (int i = 0; i < motionIntervals.size(); i++) {
      IShape shape1 = model.getObjectAtTime(motionNames.get(i), motionIntervals.get(i)[0]);
      IShape shape2 = model.getObjectAtTime(motionNames.get(i), motionIntervals.get(i)[1]);
      if (Arrays.equals(shape1.getPosition(), shape2.getPosition())) {
        motionCopy.remove(j);
        motionNamesCopy.remove(j);
      } else {
        j++;
      }
    }
    motionIntervals = motionCopy;
    motionNames = motionNamesCopy;

    for (String name : shapes.keySet()) {
      sortIntervals(shapes.get(name).getColorIntervals(), name, colorIntervals, colorNames);
    }

    List<Integer[]> colorCopy = copyInterval(colorIntervals);
    List<String> colorNamesCopy = copyInterval(colorNames, "copy");
    j = 0;
    for (int i = 0; i < colorIntervals.size(); i++) {
      IShape shape1 = model.getObjectAtTime(colorNames.get(i), colorIntervals.get(i)[0]);
      IShape shape2 = model.getObjectAtTime(colorNames.get(i), colorIntervals.get(i)[1]);
      if (shape1.getColor().equals(shape2.getColor())) {
        colorCopy.remove(j);
        colorNamesCopy.remove(j);
      } else {
        j++;
      }
    }
    colorIntervals = colorCopy;
    colorNames = colorNamesCopy;

    for (String name : shapes.keySet()) {
      sortIntervals(shapes.get(name).getScaleIntervals(), name, scaleIntervals, scaleNames);
    }

    List<Integer[]> scaleCopy = copyInterval(scaleIntervals);
    List<String> scaleNamesCopy = copyInterval(scaleNames, "copy");
    j = 0;
    for (int i = 0; i < scaleIntervals.size(); i++) {
      IShape shape1 = model.getObjectAtTime(scaleNames.get(i), scaleIntervals.get(i)[0]);
      IShape shape2 = model.getObjectAtTime(scaleNames.get(i), scaleIntervals.get(i)[1]);
      if (Arrays.equals(shape1.getDimensions(), shape2.getDimensions())) {
        scaleCopy.remove(j);
        scaleNamesCopy.remove(j);
      } else {
        j++;
      }
    }
    scaleIntervals = scaleCopy;
    scaleNames = scaleNamesCopy;

    boolean intervalsLeft = false;

    do {
      List<Integer[]> intervals = motionIntervals;
      List<String> names = motionNames;
      AnimationType animationType = AnimationType.MOTION;

      if (intervals.size() == 0) {
        intervals = colorIntervals;
        names = colorNames;
        animationType = AnimationType.COLOR;
      }

      if (intervals.size() == 0) {
        intervals = scaleIntervals;
        names = scaleNames;
        animationType = AnimationType.SCALE;
      }

      if (intervals.size() == 0) {
        break;
      }

      if (colorIntervals.size() != 0 && intervals.get(0)[0] >
      colorIntervals.get(0)[0]) {
        intervals = colorIntervals;
        names = colorNames;
        animationType = AnimationType.COLOR;
      }

      if (scaleIntervals.size() != 0 && intervals.get(0)[0] >
      scaleIntervals.get(0)[0]) {
        intervals = scaleIntervals;
        names = scaleNames;
        animationType = AnimationType.SCALE;
      }

      switch(animationType) {
        case MOTION:
          view.renderShape(
                  animationType, names.get(0), shapes.get(names.get(0)).getObject().getType(),
                  model.getObjectAtTime(names.get(0), intervals.get(0)[0]).getPosition(),
                  model.getObjectAtTime(names.get(0), intervals.get(0)[1]).getPosition(),
                  intervals.get(0));
          break;
        case SCALE:
          view.renderShape(
                  animationType, names.get(0), shapes.get(names.get(0)).getObject().getType(),
                  model.getObjectAtTime(names.get(0), intervals.get(0)[0]).getDimensions(),
                  model.getObjectAtTime(names.get(0), intervals.get(0)[1]).getDimensions(),
                  intervals.get(0));
          break;
        case COLOR:
          view.renderShape(
                  animationType, names.get(0), shapes.get(names.get(0)).getObject().getType(),
                  model.getObjectAtTime(names.get(0), intervals.get(0)[0]).getColor(),
                  model.getObjectAtTime(names.get(0), intervals.get(0)[1]).getColor(),
                  intervals.get(0));
          break;
      }
      intervals.remove(0);
      names.remove(0);

    } while (true);

    view.output();
  }

  private List<Integer[]> copyInterval(List<Integer[]> intervals) {
    List<Integer[]> newList = new ArrayList<>();
    for (Integer[] arr : intervals) {
      newList.add(arr.clone());
    }
    return newList;
  }

  private List<String> copyInterval(List<String> intervals, String copy) {
    List<String> newList = new ArrayList<>(intervals);
    return newList;
  }

  private void sortIntervals(List<Integer[]> source, String name, List<Integer[]> dest,
                             List<String> names) {
    for (Integer[] interval : source) {
      int i;
      boolean duplicate = false;
      for (i = 0; i < dest.size(); i++) {
        if (Arrays.equals(interval, dest.get(i)) && names.get(i).equals(name)) {
          duplicate = true;
          break;
        }

        if (interval[0] < dest.get(i)[0]) {
          break;
        }
      }

      if (duplicate) {
        continue;
      }

      /*if (dest.size() == 0 || i == dest.size()) {
        dest.add(interval.clone());
        names.add(name);
      } else {*/
        dest.add(i, interval.clone());
        names.add(i, name);
      //}
    }
  }
}
