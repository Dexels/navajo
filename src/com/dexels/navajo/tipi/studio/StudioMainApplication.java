package com.dexels.navajo.tipi.studio;

import javax.swing.*;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StudioMainApplication {
  private StudioMainFrame myFrame;

  public StudioMainApplication() {
    try{
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      myFrame = new StudioMainFrame();
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      myFrame.setSize(screen);
      myFrame.show();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    StudioMainApplication mainApplication1 = new StudioMainApplication();
  }

}