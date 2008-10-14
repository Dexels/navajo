package com.dexels.navajo.tipi.jxlayer.impl;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.plaf.ext.*;
import org.jdesktop.swingx.painter.*;

public class BusyPainterUI extends LockableUI implements ActionListener{
	  private BusyPainter<JComponent> busyPainter;
      private Timer timer;
      private int frameNumber;

      public BusyPainterUI() {
          busyPainter = new BusyPainter<JComponent>() {
              protected void doPaint(Graphics2D g, JComponent object,
                                     int width, int height) {
                  // centralize the effect
                  Rectangle r = getTrajectory().getBounds();
                  int tw = width - r.width - 2 * r.x;
                  int th = height - r.height - 2 * r.y;
                  g.translate(tw / 2, th / 2);
                  super.doPaint(g, object, width, height);
              }
          };
          busyPainter.setPointShape(new Ellipse2D.Double(0, 0, 20, 20));
          busyPainter.setTrajectory(new Ellipse2D.Double(0, 0, 100, 100));
          timer = new Timer(100, this);
      } 
@Override
      protected void paintLayer(Graphics2D g2, JXLayer<JComponent> l) {
          super.paintLayer(g2, l);
          if (isLocked()) {
              busyPainter.paint(g2, l, l.getWidth(), l.getHeight());
          }
      }

      @Override
      public void setLocked(boolean isLocked) {
          super.setLocked(isLocked);
          if (isLocked) {
              timer.start();
          } else {
              timer.stop();
          }
          
      }

      // Change the frame for the busyPainter
      // and mark BusyPainterUI as dirty 
      public void actionPerformed(ActionEvent e) {
          frameNumber = (frameNumber + 1) % 8;
          busyPainter.setFrame(frameNumber);
          // this will repaint the layer
          setDirty(true);
      }
}
