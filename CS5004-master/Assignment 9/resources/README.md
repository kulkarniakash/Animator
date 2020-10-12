CS5004- Easy Animator
(Collaborated with Victorya Rockingster)

The Easy Animator is an MVC based Java program that takes in a text file that contains instructions on what an animation should look like in a suitable format and then outputs the animation.

How to see the animation: Run the JAR file following the given example: "-in hanoi.txt -view visual -speed 24" (this would be the command line argument). Substitute "hanoi.txt" with any suitable txt file. If run correctly, the animation should appear in a separate window.

Overview of Design
There are two interfaces- AnimationModel and IShape. Since the model stores all the attributes of the object at each point in time, it has methods to manipulate and add objects to the animation. IShape is a common interface for all kinds of shapes such as rectangles, triangles and ovals. Adding a new shape is as easy as extending the abstract class that implements IShape.

The abstract class stores the common sttributes that all shapes must have as well as getters for these attributes.

The AnimationModelImpl (the class that implements animation model) has two important fields- objectNameMap and keyFrames. Every shape on the screen has associated with it a unique name. objectNameMap associates this name with the object and its timeline properties such as the time intervals it is in motion, changing color, scaling etc. The object and its timeline properties are encapsulated in one class called ObjectTimelineProp.

The program is designed to store copies of the attributes of all objects at the beginning and end of every time interval some change takes place. For example, if the object moves from t = 2 to t = 10, two keyframes 2 and 10 (if not already present) are created and copies of the object with the relevant attributes modified occupy position at those key frames. The field "keyFrames" stores all such frames.

Rectangle, Triangle and Oval are concrete classes implementing AbstractShape. These classes represent their respective shapes.

ObjectTimelineProp, as discussed earlier, is a record of all the intervals the shape has moved, changed color or rescaled for one particular shape. This exists to check for conflicts when the user tries to enter incompatible from position and to position. It also helps in determining the state of an object between two time intervals.

There are also enum classes such as AnimationType and ShapeType which stores what kind of animation (motion, color, scale) is being referred to or what kind of shape an object is.

How to use AnimationModel
Adding a shape to the animation
To create a new model, simply follow the following pattern AnimationModel model = new AnimationModelImpl(width, height, color, frames) where width and height are integer values representing the dimensions of the screen, color is a Java Color object and frames is the number of frames.

To add a shape:

model.addShape(ShapeType.RECTANGLE, "shape name", ...)

Inserting an animation
model.move("shape name", ...) model.changeColor("shape name", ...) model.changeDimension("shape name", ...)

Getting an (immutable) shape at a particular time
IShape shape = model.getObjectAtTime("shape name", time)

To find its position, color and dimensions, we can do the following shape.getPosition() shape.getColor() shape.getDimensions

Getting the names of all objects on the screen at a given time
model.getAllObjectNamesAtTime(time)

Getting the description of what the animation does so far
model.showAnimationText()
