package cs5004.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.swing.*;

import cs5004.animator.model.AnimationModel;
import cs5004.animator.model.IShape;
import cs5004.animator.view.AnimationView;
import cs5004.animator.view.VisualView;
import cs5004.animator.view.IViewShape;
import cs5004.animator.view.Oval;
import cs5004.animator.view.Rectangle;

public class VisualController implements AnimationController {

  private AnimationModel model;
  private AnimationView view;
  private int fps;

  public VisualController(AnimationModel model, AnimationView view, int fps) {
    this.model = model;
    this.view = view;
    this.fps = fps;
  }

  @Override
  public void playAnimation()  throws OperationNotSupportedException {
    int[] screenAttr = model.getScreenAttributes();
    view.screenInit(screenAttr[0], screenAttr[1], screenAttr[2], screenAttr[3]);
    final int[] currFrame = {0};
    Timer timer = new Timer(1000 / fps, null);
    timer.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        List<IViewShape> shapes = new ArrayList<>();
        List<String> names = model.getAllObjectNamesAtTime(currFrame[0]);
        for (String name : names) {
          IShape obj = model.getObjectAtTime(name, currFrame[0]);
          switch (obj.getType()) {
            case RECTANGLE:
              shapes.add(new Rectangle(obj));
              break;
            case OVAL:
              shapes.add(new Oval(obj));
              break;
            default:
              throw new IllegalStateException("Cannot resolve shape type");
          }
        }
        try {
          view.renderAllShapes(shapes);
        } catch (OperationNotSupportedException operationNotSupportedException) {
          operationNotSupportedException.printStackTrace();
        }
        if (model.getLastFrameNumber() > currFrame[0]) {
          currFrame[0]++;
        } else {
          ((Timer) e.getSource()).stop();
        }
      }
    });

    timer.start();
  }
}
