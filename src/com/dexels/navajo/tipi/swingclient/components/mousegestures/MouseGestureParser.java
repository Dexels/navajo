package com.dexels.navajo.tipi.swingclient.components.mousegestures;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.swingclient.components.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class MouseGestureParser {
  private ArrayList points = new ArrayList();
  private Stack gestureStack = new Stack();
  private boolean shouldAddPoints = false;
  private int precision = 3;
  private String[] precisionArray = new String[precision];
  private ArrayList myGlobalListeners = new ArrayList();

  public MouseGestureParser(JComponent c) {
    initializePrecisionArray();
    c.addMouseMotionListener(new MouseMotionListener(){
      public void mouseDragged(MouseEvent e){
        if(shouldAddPoints){
          points.add(e.getPoint());
          String gesture = determineGesture();
          storeGesture(gesture);
        }
//        redirectEvent(e, e.getPoint());
      }
      public void mouseMoved(MouseEvent e){}
    });
    c.addMouseListener(new MouseAdapter(){
      public void mousePressed(MouseEvent e){
        points.add(e.getPoint());
        shouldAddPoints = (e.getButton() == e.BUTTON3);
//        redirectEvent(e, e.getPoint());
      }
      public void mouseReleased(MouseEvent e){
        shouldAddPoints = false;
        if(gestureStack.size() >0){
          fireGestureEvent();
        }
        points.clear();
        gestureStack.clear();
//        redirectEvent(e, e.getPoint());
      }
    });
  }

  private void redirectEvent(AWTEvent e, Point p){
    JDesktopPane d = SwingClient.getUserInterface().getDesktopPane();
    Component c = d.getComponentAt(p);
    String name = c.getClass().getName();
    if(!(name.indexOf("Desktop") >= 0)){
      System.err.println("Redirecting to: " + name);
      if(StandardWindow.class.isInstance(c)){
        StandardWindow w = (StandardWindow)c;

        Component b = w.getContentPane().getComponent(0);
        System.err.println("B: = " + b.getClass().getName());
        b.getComponentAt(p).dispatchEvent(e);
      }
      c.dispatchEvent(e);
    }
  }

  private void fireGestureEvent(){
    for(int i=0;i<myGlobalListeners.size();i++){
      MouseGestureListener l = (MouseGestureListener)myGlobalListeners.get(i);
      l.mouseGestureReckognized(getGesture());
    }
  }

  public void addMouseGestureListener(MouseGestureListener l){
    myGlobalListeners.add(l);
  }

  private final void initializePrecisionArray(){
    for(int i=0;i<precision;i++){
      precisionArray[i] = "d"+i;
    }
  }

  private void storeGesture(String gesture){
    for(int i = 0;i<precision-1;i++){
      precisionArray[i] = precisionArray[i+1];
    }
    precisionArray[precision-1] = gesture;

    if(allEquals()){
      if(gesture != null) {
        if(gestureStack.size() == 0) {
          gestureStack.push(gesture);
        } else {
          if(!gesture.equals((String)gestureStack.peek())) {
            gestureStack.push(gesture);
          }
        }
      }
    }
  }

  private boolean allEquals(){
    for(int i=0;i<precision-1;i++){
      if(!precisionArray[i].equals(precisionArray[i+1])){
        return false;
      }
    }
    return true;
  }

  private void displayGesture(){
    String gestureString = "";
    Iterator it = gestureStack.iterator();
    while(it.hasNext()){
      gestureString = gestureString + (String)it.next() + ", ";
    }
    System.err.println("Gesture: "  +gestureString);
  }

  private String getGesture(){
    String gestureString = "";
    Iterator it = gestureStack.iterator();
    while(it.hasNext()){
      gestureString = gestureString + (String)it.next();
    }
    return gestureString;
  }


  private String determineGesture(){
    if(points.size() > 1){
      Point a = (Point)points.get(points.size()-2);
      Point b = (Point)points.get(points.size()-1);

      double dx = a.x - b.x;
      double dy = a.y - b.y;

      if(dx == 0 && dy > 0){
        return "U";
      }
      if(dx == 0 && dy < 0){
        return "D";
      }
      if(dy == 0 && dx > 0){
        return "L";
      }
      if(dy == 0 && dx < 0){
        return "R";
      }

      double rc = dy/dx;

      if(dx>0 && dy>0 && rc>=0.5 && rc <=2){
        return "7";
      }

      if(dx>0 && dy<0&& rc<=-0.5 && rc >=-2){
        return "1";
      }

      if(dx<0 && dy>0 && rc<=-0.5 && rc >=-2){
        return "9";
      }

      if(dx<0 && dy<0 && rc>=0.5 && rc <=2){
        return "3";
      }

      if(dx>0 && rc>=-0.5 && rc<=0.5){
        return "L";
      }

      if(dy<0 && (rc<=-2 || rc >=2)){
        return "D";
      }

      if(dx<0 && rc>=-0.5 && rc<=0.5){
        return "R";
      }

      if(dy>0 && (rc<=-2 || rc>2)){
        return "U";
      }

      return "WTF!!!!";
    }
    return null;
  }

}
