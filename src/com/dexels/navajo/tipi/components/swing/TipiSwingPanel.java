package com.dexels.navajo.tipi.components.swing;

import javax.swing.JPanel;
import java.awt.*;
import com.dexels.navajo.tipi.TipiComponent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSwingPanel extends JPanel implements Designable{
  private boolean gridFlag = false;
  private boolean selected = false;

  private TipiComponent me;

  public TipiSwingPanel(TipiComponent me) {
    this.me = me;
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Color old = g.getColor();
    if(gridFlag){
      me.paintGrid(this, g);
    }
    if(selected){
      me.highLight(this, g);
    }
    g.setColor(old);
  }

  public void setHighlighted(boolean value){
    selected = value;
  }

  public boolean isHighlighted(){
    return selected;
  }

  public void showGrid(boolean value){
    gridFlag = value;
  }

  public boolean isGridShowing(){
    return gridFlag;
  }

}