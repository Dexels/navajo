package com.dexels.navajo.tipi.components;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiPanel extends JPanel {

  private boolean scrollable = false;
  private JScrollPane myScrollPane;
  private JPanel myPanel = new JPanel();

  public TipiPanel() {
    this.setLayout(new BorderLayout());
    this.add(myPanel, BorderLayout.CENTER);
  }

  public void addBorder(){
    //System.err.println("_----------------------------------_>> Addborder called");
    this.setBorder(new EtchedBorder());
  }

  public void setScrollable(boolean state){
    if(state){
      //myPanel.setLayout(this.getLayout());
      myPanel.setLayout(new TipiTableLayout());
//      myPanel.setPreferredSize(new Dimension(1000,1000));
      myScrollPane = new JScrollPane();
      //this.setLayout(new BorderLayout());
      this.remove(myPanel);
      this.add(myScrollPane, BorderLayout.CENTER);
      myScrollPane.getViewport().add(myPanel,null);
      doLayout();
      myPanel.doLayout();
      myScrollPane.doLayout();
    }
    scrollable = state;
  }

  public void tipiAdd(Component comp, Object constraints) {
    //if(!scrollable){
    //  super.add(comp, constraints);
    //}else{
      myPanel.add(comp, constraints);
    //}
  }

  public void setTipiLayout(LayoutManager layout){
    if(!scrollable){
      super.setLayout(layout);
    }else{
      myPanel.setLayout(layout);
    }
  }

  public LayoutManager getTipiLayout(){
    //if(scrollable){
      return myPanel.getLayout();
    //}else{
    //  return super.getLayout();
    //}
  }


//  public LayoutManager getTipiLayoutManager
}