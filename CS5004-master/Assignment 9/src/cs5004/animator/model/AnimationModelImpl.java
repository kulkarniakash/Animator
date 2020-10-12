package cs5004.animator.model;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Concrete class of AnimationModel. Attributes are objectNameMap, which maps the name of an object
 * with an ObjectTimeLineProp object, which is a class that stores the object reference and the time
 * intervals when an object moves, changes color and scales; keyFrame, which maps time frames
 * (integers) to sub-maps (which map object names to DataObj objects). The keyFrame stores the
 * "state" of one more more objects at a particular time. So for example, at t=5 we could have a
 * green rectangle and position (5,5) and a blue circle at position (10,10). This data is stored
 * by keyFrame. It also contains a screen width, screen height, and a string builder to store
 * animation messages.
 */
public class AnimationModelImpl implements AnimationModel {
  Map<String, ObjectTimelineProp> objectNameMap;
  Map<Integer, Map<String, IShape>> keyFrames;
  int topLeftX;
  int topLeftY;
  int screenWidth;
  int screenHeight;
  Color bgColor;
  StringBuilder description;


  public static final class Builder implements AnimationBuilder<AnimationModel> {
    private AnimationModel model;

    public Builder(AnimationModel model) {
      this.model = model;
    }

    @Override
    public AnimationModel build() {
      return model;
    }

    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      model.setBounds(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type)
            throws IllegalArgumentException{
      ShapeType shapeType;
      switch(type) {
        case "rectangle":
          shapeType = ShapeType.RECTANGLE;
          break;
        case "ellipse":
          shapeType = ShapeType.OVAL;
          break;
        default:
          throw new IllegalArgumentException("Invalid type");
      }
      model.addShape(shapeType, name, new int[]{0, 0}, Color.WHITE, 10,
              10);
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
                                                      int h1, int r1, int g1, int b1, int t2,
                                                      int x2, int y2, int w2, int h2, int r2,
                                                      int g2, int b2) {
      model.move(name, new int[]{x1, y1}, new int[]{x2, y2}, t1, t2);
      model.changeColor(name, new Color(r1, g1, b1), new Color(r2, g2, b2), t1, t2);
      model.changeDimension(name, new int[]{w1, h1}, new int[]{w2, h2}, t1, t2);

      return this;
    }
  }

  /**
   * Constructs an AnimationModelImpl object.
   *
   * @param width an int representing the width of the screen
   * @param height an int representing the height of the screen
   * @param bgColor a Color object representing the color of this screen object
   * @param frames an int representing the number of frames this animation will have
   * @throws IllegalArgumentException if the width height, or frames are non-positive
   */
  //public AnimationModelImpl(int topLeftX, int topLeftY, int width, int height, Color bgColor)
  public AnimationModelImpl()
          throws IllegalArgumentException {
    // validate and initialize screen dimensions

//    setBounds(topLeftX, topLeftY, width, height);

    // initialize background color
//    if (bgColor == null) {
      bgColor = Color.WHITE;
//    }
//    this.bgColor = bgColor;

    // initialize other attributes
    objectNameMap = new LinkedHashMap<>();
    keyFrames = new LinkedHashMap<>();
    description = new StringBuilder();
  }

  public void setBounds(int topLeftX, int topLeftY, int width, int height) {
    if (width <= 0 || height <= 0 ) {
      throw new IllegalArgumentException("Width and height need to be positive.");
    }
    screenWidth = width;
    screenHeight = height;
    this.topLeftX = topLeftX;
    this.topLeftY = topLeftY;
  }

  @Override
  public Map<String, ObjectTimelineProp> getObjectNameMap() {
    Map<String, ObjectTimelineProp> copy = new LinkedHashMap<>();
    for (String name : objectNameMap.keySet()) {
      copy.put(name, objectNameMap.get(name).copy());
    }

    return copy;
  }

  private void validateShapeAttributes(ShapeType shapeType, String name, int[] pos, Color color,
                             int dimension1, int dimension2)
          throws IllegalArgumentException {
    // validate shape type
    if (shapeType == null) {
      throw new IllegalArgumentException("Cannot accept null shape type");
    }

    // validate name
    if (objectNameMap.containsKey(name)) {
      throw new IllegalArgumentException("Name already taken.");
    }
    /*if (name == null) {
      throw new IllegalArgumentException("Cannot accept null string as name.");
    }
    Scanner scanner = new Scanner(name);
    if (!scanner.hasNext()) {
      throw new IllegalArgumentException("Cannot accept empty string as name");
    }*/

    // validate position
    /*if (pos[0] < 0 || pos[0] > screenWidth || pos[1] < 0 || pos[1] > screenHeight) {
      throw new IllegalArgumentException("Position specified does not fit within screen.");
    }*/
    /*if (pos.length != 2) {
      throw new IllegalArgumentException("Position must be ordered pair.");
    }*/

    // validate color
    if (color == null) {
      throw new IllegalArgumentException("You must put in a valid color object");
    }

    // validate dimensions
    /*if (dimension1 + pos[0] > screenWidth ||  pos[1] - dimension2 < 0) {
      throw new IllegalArgumentException("These dimensions exceed the bounds of the screen");
    }*/
    /*if (dimension1 < 1 || dimension2 < 1) {
      throw new IllegalArgumentException("Dimensions must be positive integers");
    }*/


  }

  private void insertObjectIntoKeyFrame(IShape obj, int frame) {

    if (keyFrames.containsKey(frame)) {
      keyFrames.get(frame).put(obj.getName(), obj);
    } else {
      Map<String, IShape> state = new LinkedHashMap<>();
      state.put(obj.getName(), obj);
      keyFrames.put(frame, state);
    }
  }

  @Override
  public void addShape(ShapeType shapeType, String name, int[] pos, Color color, int dimension1,
                       int dimension2)
          throws IllegalArgumentException {

    try {
      validateShapeAttributes(shapeType, name, pos, color, dimension1, dimension2);
    } catch (Exception e) {
      throw e;
    }

    IShape newShape;
    switch (shapeType) {
      case RECTANGLE:
        newShape = new Rectangle(name, pos[0], pos[1], dimension1, dimension2, color);
        break;
      case TRIANGLE:
        newShape = new Triangle(name, pos[0], pos[1], dimension1, dimension2, color);
        break;
      case OVAL:
        newShape = new Oval(name, pos[0], pos[1], dimension1, dimension2, color);
        break;
      default:
        throw new IllegalArgumentException("Shape type is invalid");
    }

    objectNameMap.put(name, new ObjectTimelineProp(newShape));
  }

  private boolean intervalOverlaps(List<Integer[]> intervals, int fromTime, int toTime) {

    for (Integer[] interval : intervals) {
      int combinedLength = Math.max(interval[1], toTime) - Math.min(interval[0], fromTime);
      int individualLength = toTime - fromTime + interval[1] - interval[0];
      if (combinedLength < individualLength) {
        return true;
      }
    }

    return false;
  }

  private int floorOfTime(List<Integer[]> intervals, int fromTime) {
    int time = 0;
    for (Integer[] itr : intervals) {
      if (itr[1] <= fromTime) {
        time = itr[1];
      } else {
        break;
      }
    }

    return time;
  }

  private int ceilOfTime(List<Integer[]> intervals, int toTime) {
    int time = 0;
    for (Integer[] itr : intervals) {
      if (itr[0] >= toTime) {
        time = itr[0];
        break;
      }
    }

    return time;
  }

  private boolean hasConsistentFromDimension(String name, int fromTime, int[] fromDim) {
    List<Integer[]> intervals = objectNameMap.get(name).getScaleIntervals();
    if (intervals.size() == 0) {
      return true;
    }
    int time = floorOfTime(intervals, fromTime);
    if (time == fromTime) {
      if (keyFrames.get(fromTime).containsKey(name)) {
        int[] dim = keyFrames.get(fromTime).get(name).getDimensions();
        return Arrays.equals(dim, fromDim);
      }
    }

    return true;
  }

  private boolean hasConsistentToDimension(String name, int toTime, int[] toDim) {
    List<Integer[]> intervals = objectNameMap.get(name).getScaleIntervals();
    if (intervals.size() == 0) {
      return true;
    }
    int time = ceilOfTime(intervals, toTime);
    if (time == toTime && keyFrames.get(toTime).containsKey(name)) {
      int[] dim = keyFrames.get(toTime).get(name).getDimensions();
      return Arrays.equals(dim, toDim);
    }

    return true;
  }

  private boolean hasConsistentFromColor(String name, int fromTime, Color fromColor) {
    List<Integer[]> intervals = objectNameMap.get(name).getColorIntervals();
    if (intervals.size() == 0) {
      return true;
    }
    int time = floorOfTime(intervals, fromTime);
    if (time == fromTime) {
      if (keyFrames.get(fromTime).containsKey(name)) {
        Color color = keyFrames.get(fromTime).get(name).getColor();
        return color.equals(fromColor);
      }
    }

    return true;
  }

  private boolean hasConsistentToColor(String name, int toTime,
                                       Color toColor) {
    List<Integer[]> intervals = objectNameMap.get(name).getColorIntervals();
    if (intervals.size() == 0) {
      return true;
    }
    int time = ceilOfTime(intervals, toTime);
    if (time == toTime && keyFrames.get(toTime).containsKey(name)) {
      Color color = keyFrames.get(toTime).get(name).getColor();
      return color.equals(toColor);
    }

    return true;
  }

  private boolean hasConsistentFromPosition(String name, int fromTime,
                                            int[] fromPos) {
    List<Integer[]> intervals = objectNameMap.get(name).getMotionIntervals();
    if (intervals.size() == 0) {
      return true;
    }
    int time = floorOfTime(intervals, fromTime);
    if (time == fromTime) {
      if (keyFrames.get(fromTime).containsKey(name)) {
        int[] pos = keyFrames.get(fromTime).get(name).getPosition();
        return Arrays.equals(pos, fromPos);
      }
    }

    return true;
  }

  private boolean hasConsistentToPosition(String name, int toTime,
                                          int[] toPos) {
    List<Integer[]> intervals = objectNameMap.get(name).getMotionIntervals();
    if (intervals.size() == 0) {
      return true;
    }
    int time = ceilOfTime(intervals, toTime);
    if (time == toTime && keyFrames.get(toTime).containsKey(name)) {
      int[] pos = keyFrames.get(toTime).get(name).getPosition();
      return Arrays.equals(pos, toPos);
    }

    return true;
  }

  @Override
  public int[] getScreenAttributes() {
    return new int[]{topLeftX, topLeftY, screenWidth, screenHeight};
  }

  @Override
  public void move(String name, int[] fromPos, int[] toPos, int fromTime, int toTime)
          throws IllegalArgumentException {
    // validate the time interval i.e. check if it overlaps with an existing motion time interval
    // by getting a copy of the motionInterval array list from the objectNameMap.
    // If fromPos and toPos are out of the screen throw an error. Check whether fromTime and toTime
    // are within the appearance and disappearance range of the object. Save the time interval
    // closest to to fromTime such that the interval lies entirely behind fromTime. This is accompl
    // -ished by motionInterval array again. Save this value as int prevTime.

    // We now have to find the position of the object just before the fromTime. Find the value for
    // prevTime in keyFrame and search for the object with our name. Copy the DataObj present
    // over there. Make another copy of the DataObj. We now have two copies- one for t = fromTime
    // and the other for t = toTime.

    // Ensure that fromPos matches with the position in the DataObj copy,
    // else throw an exception. Modify the data in the second DataObj copy and insert both of the
    // copies in keyFrame at their respective from and to times.

    // Update description.
    if (!objectNameMap.containsKey(name)) {
      throw new IllegalArgumentException("Object does not exist.");
    }

    if (fromPos.length != 2 || toPos.length != 2) {
      throw new IllegalArgumentException("Position must be ordered pair.");
    }
    /*if (fromPos[0] < 0 || fromPos[0] > screenWidth || fromPos[1] < 0 || fromPos[1] > screenHeight
            || toPos[0] < 0 || toPos[0] > screenWidth || toPos[1] < 0 || toPos[1] > screenHeight) {
      throw new IllegalArgumentException("Position specified does not fit within screen.");
    }*/


    List<Integer[]> motionInterval = objectNameMap.get(name).getMotionIntervals();

    if (intervalOverlaps(motionInterval, fromTime, toTime)) {
      throw new IllegalArgumentException("This time interval already has its motion defined.");
    }
    if (!hasConsistentFromPosition(name, fromTime, fromPos)) {
      throw new IllegalArgumentException("From position not consistent.");
    }
    if (!hasConsistentToPosition(name, toTime, toPos)) {
      throw new IllegalArgumentException("To position not consistent");
    }

    insertMotionAnimation(name, fromPos, toPos, fromTime, toTime);
    objectNameMap.get(name).addToMotionInterval(fromTime, toTime);
  }

  @Override
  public void changeDimension(String name, int[] fromDim, int[] toDim, int fromTime, int toTime)
          throws IllegalArgumentException {
    // see logic of move. Here however we would also have to create cases depending on what type
    // of object it is.

    if (!objectNameMap.containsKey(name)) {
      throw new IllegalArgumentException("Object does not exist.");
    }
    for (int i = 0; i < fromDim.length; i++) {
      if (fromDim[i] <= 0 || toDim[i] <= 0) {
        throw new IllegalArgumentException("Dimension of object must be positive");
      }
    }

    List<Integer[]> scaleInterval = objectNameMap.get(name).getScaleIntervals();

    if (intervalOverlaps(scaleInterval, fromTime, toTime)) {
      throw new IllegalArgumentException("This time interval already has its motion defined.");
    }

    if (!hasConsistentFromDimension(name, fromTime, fromDim)) {
      throw new IllegalArgumentException("From dimension not consistent.");
    }

    if (!hasConsistentToDimension(name, toTime, toDim)) {
      throw new IllegalArgumentException("To dimension not consistent");
    }

    insertScaleAnimation(name, fromDim, toDim, fromTime, toTime);
    objectNameMap.get(name).addToScaleInterval(fromTime, toTime);
  }

  @Override
  public void changeColor(String name, Color fromColor, Color toColor, int fromTime, int toTime)
          throws IllegalArgumentException {

    if (!objectNameMap.containsKey(name)) {
      throw new IllegalArgumentException("Object does not exist.");
    }

    if (fromColor == null) {
      throw new IllegalArgumentException("Cannot accept null color");
    }

    if (toColor == null) {
      throw new IllegalArgumentException("Cannot accept null color");
    }


    List<Integer[]> colorIntervals = objectNameMap.get(name).getColorIntervals();

    if (intervalOverlaps(colorIntervals, fromTime, toTime)) {
      throw new IllegalArgumentException("This object already has its color transition defined "
              + "for this interval");
    }

    if (!hasConsistentFromColor(name, fromTime, fromColor)) {
      throw new IllegalArgumentException("From color not consistent.");
    }

    if (!hasConsistentToColor(name, toTime, toColor)) {
      throw new IllegalArgumentException("To Color not consistent.");
    }

    insertColorAnimation(name, fromColor, toColor, fromTime, toTime);
    objectNameMap.get(name).addToColorInterval(fromTime, toTime);
  }

  @Override
  public List<String> getAllObjectNamesAtTime(int time) {
    Set<String> allObjNames = objectNameMap.keySet();
    ArrayList<String> objNames = new ArrayList<>();

    for (String name : allObjNames) {
      if (time <= objectNameMap.get(name).getDisappearance() && time >= objectNameMap.get(name)
              .getAppearance()) {
        objNames.add(name);
      }
    }

    return objNames;
  }

  // ADD STUFF FOR SCALE AND SHAPE TOO
  @Override
  public IShape getObjectAtTime(String name, int time) {

    if (objectNameMap.get(name).getAppearance() > time || objectNameMap.get(name).getDisappearance()
            < time) {
      return null;
    }

    // calculate position here
    Integer[] timeInterval = objectNameMap.get(name).getMotionIntervalAtTime(time);
    int[] currPos = objectNameMap.get(name).getObject().getPosition();
    if (timeInterval[0] != -1) {
      currPos = new int[2];
      int[] fromPos = keyFrames.get(timeInterval[0]).get(name).getPosition();
      int[] toPos = keyFrames.get(timeInterval[1]).get(name).getPosition();
      if (timeInterval[1] - timeInterval[0] == 0) {
        currPos[0] = fromPos[0];
        currPos[1] = fromPos[1];
      } else {
        currPos[0] = fromPos[0] + (time - timeInterval[0])
                * (toPos[0] - fromPos[0]) / (timeInterval[1] - timeInterval[0]);
        currPos[1] = fromPos[1] + (time - timeInterval[0])
                * (toPos[1] - fromPos[1]) / (timeInterval[1] - timeInterval[0]);
      }
    }


    // calculate color here
    timeInterval = objectNameMap.get(name).getColorIntervalAtTime(time);
    Color currColor = objectNameMap.get(name).getObject().getColor();
    if (timeInterval[0] != -1) {
      Color fromColor = keyFrames.get(timeInterval[0]).get(name).getColor();
      Color toColor = keyFrames.get(timeInterval[1]).get(name).getColor();
      if (timeInterval[1] - timeInterval[0] == 0) {
        currColor = new Color(fromColor.getRed(), fromColor.getGreen(), fromColor.getBlue());
      } else {
        int red = fromColor.getRed() + (time - timeInterval[0])
                * (toColor.getRed() - fromColor.getRed()) / (timeInterval[1] - timeInterval[0]);
        int green = fromColor.getGreen() + (time - timeInterval[0])
                * (toColor.getGreen() - fromColor.getGreen()) / (timeInterval[1] - timeInterval[0]);
        int blue = fromColor.getBlue() + (time - timeInterval[0])
                * (toColor.getBlue() - fromColor.getBlue()) / (timeInterval[1] - timeInterval[0]);
        currColor = new Color(red, green, blue);
      }
    }

    // calculate dimensions here
    timeInterval = objectNameMap.get(name).getScaleIntervalAtTime(time);
    int[] currScale = objectNameMap.get(name).getObject().getDimensions();
    if (timeInterval[0] != -1) {
      int[] fromScale = keyFrames.get(timeInterval[0]).get(name).getDimensions();
      int[] toScale = keyFrames.get(timeInterval[1]).get(name).getDimensions();
      if (timeInterval[1] - timeInterval[0] == 0) {
        currScale = fromScale.clone();
      } else {
        currScale = new int[fromScale.length];
        for (int i = 0; i < currScale.length; i++) {
          currScale[i] = fromScale[i] + (time - timeInterval[0])
                  * (toScale[i] - fromScale[i]) / (timeInterval[1] - timeInterval[0]);
        }
      }
    }

    return objectNameMap.get(name).getObject().changePosition(currPos[0], currPos[1])
            .changeColor(currColor).changeDimension(currScale);
  }


  // finds the greatest time lesser than specified time when the object had a keyframe
  private int findJustBeforeTime(String name, int time) {
    Set<Integer> set = keyFrames.keySet();
    List<Integer> timePoints = new ArrayList<>(set);
    Collections.sort(timePoints);

    if (!objectNameMap.containsKey(name)) {
      return -1;
    }

    int lastTime = time;
    for (int itr : timePoints) {
      if (itr <= time) {
        if (keyFrames.get(itr).containsKey(name)) {
          lastTime = itr;
        }
      } else {
        break;
      }
    }

    return lastTime;
  }

  private void insertMotionAnimation(String name, int[] fromPos, int[] toPos, int fromTime,
                                     int toTime) throws IllegalStateException {
    int lastTime = findJustBeforeTime(name, fromTime);
    if (lastTime == -1) {
      throw new IllegalStateException("Could not find object on stage.");
    }

    IShape fromObj;
    IShape toObj;
    try {
      fromObj = getObjectAtTime(name, fromTime).changePosition(fromPos[0], fromPos[1]);
      // modified from toObj = fromObj.getObjectAtTime(name, fromTime).change...
      if (getObjectAtTime(name, toTime) == null) {
        toObj = fromObj.changePosition(toPos[0], toPos[1]);
      } else {
        toObj = getObjectAtTime(name, toTime).changePosition(toPos[0], toPos[1]);
      }
    } catch (NullPointerException e) {
      fromObj = objectNameMap.get(name).getObject().changePosition(fromPos[0], fromPos[1]);
      toObj = objectNameMap.get(name).getObject().changePosition(toPos[0], toPos[1]);
    }

    insertObjectIntoKeyFrame(fromObj, fromTime);
    insertObjectIntoKeyFrame(toObj, toTime);
  }

  private void insertColorAnimation(String name, Color fromColor, Color toColor, int fromTime,
                                    int toTime) throws IllegalStateException {
    int lastTime = findJustBeforeTime(name, fromTime);
    if (lastTime == -1) {
      throw new IllegalStateException("Could not find object on stage.");
    }

    IShape fromObj;
    IShape toObj;
    try {
      fromObj = getObjectAtTime(name, fromTime).changeColor(fromColor);
      if (getObjectAtTime(name, toTime) == null) {
        toObj = fromObj.changeColor(toColor);
      } else {
        toObj = getObjectAtTime(name, toTime).changeColor(toColor);
      }
    } catch (NullPointerException e) {
      fromObj = objectNameMap.get(name).getObject().changeColor(fromColor);
      toObj = objectNameMap.get(name).getObject().changeColor(toColor);
    }

    insertObjectIntoKeyFrame(fromObj, fromTime);
    insertObjectIntoKeyFrame(toObj, toTime);
  }

  private void insertScaleAnimation(String name, int[] fromScale, int[] toScale, int fromTime,
                                    int toTime) throws IllegalStateException {
    int lastTime = findJustBeforeTime(name, fromTime);
    if (lastTime == -1) {
      throw new IllegalStateException("Could not find object on stage.");
    }

    IShape fromObj;
    IShape toObj;
    try {
      fromObj = getObjectAtTime(name, fromTime).changeDimension(fromScale);
      if (getObjectAtTime(name, toTime) == null) {
        toObj = fromObj.changeDimension(toScale);
      } else {
        toObj = getObjectAtTime(name, toTime).changeDimension(toScale);
      }
    } catch (NullPointerException e) {
      fromObj = objectNameMap.get(name).getObject().changeDimension(fromScale);
      toObj = objectNameMap.get(name).getObject().changeDimension(toScale);
    }

    insertObjectIntoKeyFrame(fromObj, fromTime);
    insertObjectIntoKeyFrame(toObj, toTime);
  }

  private int getIndexOfInterval(List<Integer[]> intervals, int fromTime, int toTime)
          throws NoSuchElementException {
    for (int i = 0; i < intervals.size(); i++) {
      if (intervals.get(i)[0] == fromTime && intervals.get(i)[1] == toTime) {
        return i;
      }
    }

    throw new NoSuchElementException("Could not find interval");
  }

  @Override
  public void undoAnimation(String name, AnimationType type, int fromTime, int toTime)
          throws IllegalArgumentException {
    /*switch (type) {
      case MOTION:
        List<Integer[]> intervals = objectNameMap.get(name).getMotionIntervals();
        try {
          int index = getIndexOfInterval(intervals, fromTime, toTime);
          objectNameMap.get(name).deleteMotionInterval(index);
        } catch (NoSuchElementException e) {
          throw new IllegalArgumentException("Interval does not exist");
        }
        break;
      case COLOR:
        intervals = objectNameMap.get(name).getColorIntervals();
        try {
          int index = getIndexOfInterval(intervals, fromTime, toTime);
          objectNameMap.get(name).deleteMotionInterval(index);
        } catch (NoSuchElementException e) {
          throw new IllegalArgumentException("Interval does not exist");
        }
        break;
    }*/
  }

  @Override
  public String showAnimationText() {
    description.append("Shapes:\n");

    Set<String> shapes = objectNameMap.keySet();
    for (String name : shapes) {
      IShape shape = objectNameMap.get(name).getObject();
      description.append("Name: ").append(name).append("\n");
      description.append("Type: ").append(shape.getType()).append("\n");
      description.append(shape).append("\n");
      description.append("Appears: at t = ").append(objectNameMap.get(name).getAppearance())
              .append("\n");
      description.append("Disappears at t = ").append(objectNameMap.get(name).getDisappearance())
              .append("\n");
      description.append("\n");
    }

    List<Integer> frames = new ArrayList<>(keyFrames.keySet());
    Collections.sort(frames);

    for (int frame : frames) {
      Map<String, IShape> objects = keyFrames.get(frame);
      for (String name : objects.keySet()) {
        List<Integer[]> moveIntervals = objectNameMap.get(name).getMotionIntervals();
        List<Integer[]> colorIntervals = objectNameMap.get(name).getColorIntervals();
        List<Integer[]> scaleIntervals = objectNameMap.get(name).getScaleIntervals();

        for (Integer[] time : moveIntervals) {
          if (frame == time[0]) {
            int[] fromPos = keyFrames.get(time[0]).get(name).getPosition();
            int[] toPos = keyFrames.get(time[1]).get(name).getPosition();
            description.append("Shape ").append(name).append(String.format(
                    " moves from (%d, %d) " + "to (%d, %d) from t = %d to t = %d", fromPos[0],
                    fromPos[1], toPos[0], toPos[1], time[0], time[1])).append("\n");
          }
        }

        for (Integer[] time : colorIntervals) {
          if (frame == time[0]) {
            Color fromColor = keyFrames.get(time[0]).get(name).getColor();
            Color toColor = keyFrames.get(time[1]).get(name).getColor();
            description.append("Shape ").append(name).append(String.format(" changes color "
                            + "from (%d, %d, %d) to (%d, %d, %d) from t = %d to t = %d\n",
                    fromColor.getRed(), fromColor.getGreen(), fromColor.getBlue(), toColor.getRed(),
                    toColor.getGreen(), toColor.getBlue(), time[0], time[1]));
          }
        }

        for (Integer[] time : scaleIntervals) {
          if (frame == time[0]) {
            int[] fromScale = keyFrames.get(time[0]).get(name).getDimensions();
            int[] toScale = keyFrames.get(time[1]).get(name).getDimensions();

            switch (objectNameMap.get(name).getObject().getType()) {
              case RECTANGLE:
                description.append("Shape ").append(name).append(
                        String.format(" scales from Width: %d, Height: %d to Width: %d, Height: %d "
                                        + "from t = %d to t = %d\n", fromScale[0], fromScale[1],
                              toScale[0], toScale[1], time[0], time[1]));
                break;
              case OVAL:
                description.append("Shape ").append(name).append(
                        String.format(" scales from X Radius: %d, Y Radius: %d to X Radius: %d, Y "
                                + "Radius: %d from t = %d to t = %d\n", fromScale[0], fromScale[1],
                              toScale[0], toScale[1], time[0], time[1]));
                break;
              case TRIANGLE:
                description.append("Shape ").append(name).append(
                        String.format(" scales from Base: %d, Height: %d to Base: %d, Height: %d"
                                      +  "from t = %d to t = %d\n", fromScale[0], fromScale[1],
                              toScale[0], toScale[1], time[0], time[1]));
                break;
              default:
                break;
            }
          }
        }
      }
    }

    return description.toString();
  }

  @Override
  public int getLastFrameNumber() {
    Set<Integer> set = keyFrames.keySet();
    List<Integer> keys = new ArrayList<>(set);
    Collections.sort(keys);
    return keys.get(keys.size()-1);
  }


}