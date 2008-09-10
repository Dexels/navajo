package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import com.dexels.navajo.tipi.swingclient.*;

public class BaseGlassPane extends JComponent{

  private ArrayList myBusyPanels = new ArrayList();
  private Map imageMap  = new HashMap();

  private ImageIcon[] icons = new ImageIcon[2];

  public static final int MODE_HOURGLASS = 0;
  public static final int MODE_SKULL = 1;

  public BaseGlassPane() {
    icons[0] = new ImageIcon(UserInterface.class.getResource("images/hourglass-a.gif"));
    icons[1] = new ImageIcon();
  }


  public void addBusyPanel(BasePanel p, int mode) {
    imageMap.put(p,new Integer(mode));
    if (!myBusyPanels.contains(p)) {
      myBusyPanels.add(p);
    }
    setVisible(true);
  }

  public void removeBusyPanel(BasePanel p) {
    myBusyPanels.remove(p);
    repaint();
    if (myBusyPanels.size()==0) {
      setVisible(false);
    }
  }

  public int getBusyPanelCount() {
    return myBusyPanels.size();
  }

  @Override
public void update(Graphics g) {
    paint(g);
  }

  private Image getImage(BasePanel p) {
    int j = ((Integer)imageMap.get(p)).intValue();
    return icons[j].getImage();
  }



  @Override
public void paintComponent(Graphics g) {
    for (int i = 0; i < myBusyPanels.size(); i++) {
      BasePanel current = (BasePanel)myBusyPanels.get(i);
      Dimension screenSize = current.getSize();
      Dimension frameSize = new Dimension(100,50);
      if (frameSize.height > screenSize.height) {
        frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
        frameSize.width = screenSize.width;
      }
      java.awt.Point p = new java.awt.Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

      Graphics2D  g2 = (Graphics2D)g;
      Composite old = g2.getComposite();
      AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);

      java.awt.Point q = SwingUtilities.convertPoint(current,p,current.getRootPane());
      g.setColor(Color.orange);
      g2.setComposite(ac);
      Image hg = getImage(current);
      if(hg!=null){
        g.drawImage(hg, q.x, q.y, hg.getWidth(this), hg.getHeight(this), this);
      }
      g2.setComposite(old);
      g.setColor(Color.black);
    }

  }
}
