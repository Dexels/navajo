package com.dexels.navajo.tipi.components;

import javax.swing.JDesktopPane;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class LogoDeskTop extends JDesktopPane {
  private Image myLogo;

  public LogoDeskTop() {
  }

  public LogoDeskTop(Image i) {
    myLogo = i;
  }

  public void setImage(Image i){
    myLogo = i;
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    if(myLogo != null){
      int img_width = myLogo.getWidth(this);
      int img_height = myLogo.getHeight(this);
      g.drawImage(myLogo, (getWidth()/2)-(img_width/2), (getHeight()/2)-(img_height/2), this);
    }
  }

}