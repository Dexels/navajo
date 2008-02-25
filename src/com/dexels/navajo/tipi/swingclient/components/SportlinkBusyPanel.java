package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.dexels.navajo.tipi.swingclient.components.mousegestures.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author not attributable
 * @version 1.0
 */

public class SportlinkBusyPanel extends BaseGlassPane implements Runnable {
  private volatile transient Thread t;
  int size = 10;
  int spacing = 2;
  boolean right_drag = false;


  float[] composites = new float[] {
      1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.05f};
  Color[] colors = new Color[] {
      Color.blue, Color.red, Color.yellow, Color.green, Color.orange, Color.magenta};
  boolean isRunning = true;
  private final Color foreGround = Color.orange;

  public SportlinkBusyPanel() {
    if (t == null) {
      t = new Thread(this);
    }
    this.addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        System.err.println(">>>>>>>>>>>>>>>>-- You clicked on Glass --<<<<<<<<<<<<<<<<<<");
      }
    });
  }

  public void addBusyPanel(BasePanel p, int mode) {
    super.addBusyPanel(p, mode);
    isRunning = true;
    start();
  }

  public void removeBusyPanel(BasePanel p) {
    super.removeBusyPanel(p);
    isRunning = false;
  }

  private final void rotateComposites() {
    float[] new_comp = new float[composites.length];
    for (int i = 0; i < new_comp.length; i++) {
      if (i < new_comp.length - 1) {
        new_comp[i] = composites[i + 1];
      }
      else {
        new_comp[i] = composites[0];
      }
    }
    composites = new_comp;
  }

  public void start() {
    try {
      t.start();
    } catch (Exception e) {
      System.err.println("Could not start busy panel.....");
      repaint();
    }
  }

  public void run() {
    while (isRunning) {
      try {
        t.sleep(150);
        rotateComposites();
        repaint();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void stop() {
    isRunning = false;
  }

  public void update(Graphics g) {
    paint(g);
  }

  public void paintComponent(Graphics g) {
    int width = this.getWidth();
    int height = this.getHeight();
    Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    AlphaComposite ac1 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, composites[0]);
    g2.setComposite(ac1);
    g2.setColor(colors[0]);
    g2.fillRoundRect( (width / 2) - (int) (1.5 * size) - spacing, (height / 2) - size - spacing, size, size, 15, 15);
    g2.setColor(Color.black);
    g2.drawRoundRect( (width / 2) - (int) (1.5 * size) - spacing, (height / 2) - size - spacing, size, size, 15, 15);

    AlphaComposite ac2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, composites[1]);
    g2.setComposite(ac2);
    g2.setColor(colors[1]);
    g2.fillRoundRect( (width / 2) - (int) (0.5 * size), (height / 2) - (int) (1.75 * size) - spacing, size, size, 15, 15);
    g2.setColor(Color.black);
    g2.drawRoundRect( (width / 2) - (int) (0.5 * size), (height / 2) - (int) (1.75 * size) - spacing, size, size, 15, 15);

    AlphaComposite ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, composites[2]);
    g2.setComposite(ac3);
    g2.setColor(colors[2]);
    g2.fillRoundRect( (width / 2) + spacing + (int) (0.5 * size), (height / 2) - size - spacing, size, size, 15, 15);
    g2.setColor(Color.black);
    g2.drawRoundRect( (width / 2) + spacing + (int) (0.5 * size), (height / 2) - size - spacing, size, size, 15, 15);

    AlphaComposite ac4 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, composites[3]);
    g2.setComposite(ac4);
    g2.setColor(colors[3]);
    g2.fillRoundRect( (width / 2) + spacing + (int) (0.5 * size), (height / 2) + spacing, size, size, 15, 15);
    g2.setColor(Color.black);
    g2.drawRoundRect( (width / 2) + spacing + (int) (0.5 * size), (height / 2) + spacing, size, size, 15, 15);

    AlphaComposite ac5 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, composites[4]);
    g2.setComposite(ac5);
    g2.setColor(colors[4]);
    g2.fillRoundRect( (width / 2) - (int) (0.5 * size), (height / 2) + (int) (0.95 * size) - spacing, size, size, 15, 15);
    g2.setColor(Color.black);
    g2.drawRoundRect( (width / 2) - (int) (0.5 * size), (height / 2) + (int) (0.95 * size) - spacing, size, size, 15, 15);

    AlphaComposite ac6 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, composites[5]);
    g2.setComposite(ac6);
    g2.setColor(colors[5]);
    g2.fillRoundRect( (width / 2) - (int) (1.5 * size) - spacing, (height / 2) + spacing, size, size, 15, 15);
    g2.setColor(Color.black);
    g2.drawRoundRect( (width / 2) - (int) (1.5 * size) - spacing, (height / 2) + spacing, size, size, 15, 15);

    g2.drawString("Loading..", (width / 2) - 18, (height / 2) + 34);
  }

  public static void main(String[] args) {
    JFrame aap = new JFrame();
    SportlinkBusyPanel sbp = new SportlinkBusyPanel();
    aap.getContentPane().add(sbp);
    sbp.start();
    aap.setSize(800, 600);
    aap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    aap.setVisible(true);
  }
}
