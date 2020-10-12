package cs5004.animator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Used in AnimationModelImpl to store an object and all the intervals when a particular change
 * takes place for example, moving, or changing color.
 */
public class ObjectTimelineProp {
  private IShape object;
  private List<Integer[]> moveIntervals;
  private List<Integer[]> colorIntervals;
  private List<Integer[]> scaleIntervals;
  private int appearance;
  private int disappearance;

  public ObjectTimelineProp(IShape object) {
    this.object = object;
    moveIntervals = new ArrayList<>();
    colorIntervals = new ArrayList<>();
    scaleIntervals = new ArrayList<>();
    this.appearance = -1;
    this.disappearance = -1;
  }

  public ObjectTimelineProp copy() {
    ObjectTimelineProp copy = new ObjectTimelineProp(object);
    copy.moveIntervals = copyInterval(moveIntervals);
    copy.colorIntervals = copyInterval(colorIntervals);
    copy.scaleIntervals = copyInterval(scaleIntervals);
    copy.appearance = appearance;
    copy.disappearance = disappearance;
    return copy;
  }

  private void updateAppearanceAndDisappearance() {
    List<Integer[]> moveCopy = copyInterval(moveIntervals);
    List<Integer[]> colorCopy = copyInterval(colorIntervals);
    List<Integer[]> scaleCopy = copyInterval(scaleIntervals);

    moveCopy.add(new Integer[]{-1, -1});
    colorCopy.add(new Integer[]{-1, -1});
    scaleCopy.add(new Integer[]{-1, -1});

    appearance = moveCopy.get(0)[0];
    if (appearance > colorCopy.get(0)[0] && colorCopy.get(0)[0] != -1 || appearance == -1) {
      appearance = colorCopy.get(0)[0];
    }
    if (appearance > scaleCopy.get(0)[0] && scaleCopy.get(0)[0] != -1 || appearance == -1) {
      appearance = scaleCopy.get(0)[0];
    }

    int last1 = moveIntervals.size();
    int last2 = colorIntervals.size();
    int last3 = scaleIntervals.size();

    moveCopy.remove(last1);
    colorCopy.remove(last2);
    scaleCopy.remove(last3);

    moveCopy.add(0, new Integer[]{-1, -1});
    colorCopy.add(0, new Integer[]{-1, -1});
    scaleCopy.add(0, new Integer[]{-1, -1});

    disappearance = moveCopy.get(last1)[1];
    if (disappearance < colorCopy.get(last2)[1] && colorCopy.get(last2)[1] != -1) {
      disappearance = colorCopy.get(last2)[1];
    }
    if (disappearance < scaleCopy.get(last3)[1] && scaleCopy.get(last3)[1] != -1) {
      disappearance = scaleCopy.get(last3)[1];
    }
  }

  public int getAppearance() {
    return this.appearance;
  }

  public int getDisappearance() {
    return this.disappearance;
  }

  private List<Integer[]> copyInterval(List<Integer[]> intervals) {
    List<Integer[]> newList = new ArrayList<>();
    for (Integer[] arr : intervals) {
      newList.add(arr.clone());
    }
    return newList;
  }

  public List<Integer[]> getMotionIntervals() {
    return copyInterval(moveIntervals);
  }

  public List<Integer[]> getColorIntervals() {
    return copyInterval(colorIntervals);
  }

  public List<Integer[]> getScaleIntervals() {
    return copyInterval(scaleIntervals);
  }

  private void addToAnimationIntervalHelper(List<Integer[]> intervals, int fromTime, int toTime) {
    Integer[] arr = {fromTime, toTime};

    for (int i = 0; i < intervals.size(); i++) {
      //new
      if (intervals.get(i)[0].equals(arr[0]) && intervals.get(i)[1].equals(arr[1])) {
        return;
      }

      if (intervals.get(i)[0] > fromTime) {
        intervals.add(i, arr);
        return;
      }
    }

    intervals.add(arr);
  }

  public void addToMotionInterval(int fromTime, int toTime) {
    addToAnimationIntervalHelper(moveIntervals, fromTime, toTime);
    updateAppearanceAndDisappearance();
  }

  public void addToColorInterval(int fromTime, int toTime) {
    addToAnimationIntervalHelper(colorIntervals, fromTime, toTime);
    updateAppearanceAndDisappearance();
  }

  public void addToScaleInterval(int fromTime, int toTime) {
    addToAnimationIntervalHelper(scaleIntervals, fromTime, toTime);
    updateAppearanceAndDisappearance();
  }

  public void deleteMotionInterval(int index) {
    moveIntervals.remove(index);
  }

  public void deleteColorInterval(int index) {
    colorIntervals.remove(index);
  }

  public void deleteScaleInterval(int index) {
    scaleIntervals.remove(index);
  }

  private Integer[] getAnimationIntervalAtTimeHelper(List<Integer[]> intervals, int time) {
    int i;
    for (i = 0; i < intervals.size(); i++) {
      if (intervals.get(i)[0] <= time && intervals.get(i)[1] >= time) {
        return new Integer[]{intervals.get(i)[0], intervals.get(i)[1]};
      } else if (intervals.get(i)[0] > time) {
        break;
      }
    }

    if (i == 0) {
      return new Integer[]{appearance, appearance};
    }

    return new Integer[]{intervals.get(i - 1)[1], intervals.get(i - 1)[1]};
  }

  public Integer[] getMotionIntervalAtTime(int time) {
    if (time < appearance || time > disappearance) {
      return null;
    }

    return getAnimationIntervalAtTimeHelper(moveIntervals, time);
  }

  public Integer[] getColorIntervalAtTime(int time) {
    if (time < appearance || time > disappearance) {
      return null;
    }

    return getAnimationIntervalAtTimeHelper(colorIntervals, time);
  }

  public Integer[] getScaleIntervalAtTime(int time) {
    if (time < appearance || time > disappearance) {
      return null;
    }

    return getAnimationIntervalAtTimeHelper(scaleIntervals, time);
  }

  public IShape getObject() {
    return object;
  }
  // include methods to add time intervals i.e. ordered pairs to each kind of array list. Also add
  // getters to get copies of array lists.
}