package com.dexels.navajo.tipi.swingclient.components;

import java.awt.event.*;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class KeyEventHandler
    extends AbstractAction {
  private String myMod;
  private Component myComp;

  public KeyEventHandler(Component c, String modifier) {
    myMod = modifier;
    myComp = c;
  }

  public void actionPerformed(ActionEvent e) {
    if (StandardWindow.class.isInstance(myComp)) {
      final StandardWindow sw = (StandardWindow) myComp;
      if ("Save".equals(myMod)) {
        sw.saveButton_actionPerformed(e);
      }
      if ("Clear".equals(myMod)) {
        sw.clearButton_actionPerformed(e);
      }
      if ("SaveExit".equals(myMod)) {
        sw.save();
        // Closing here is to quick
        sw.closeButton_actionPerformed(e);
      }
      if ("Exit".equals(myMod)) {
        sw.closeButton_actionPerformed(e);
      }
      if ("Insert".equals(myMod)) {
        sw.insertButton_actionPerformed(e);
      }
      if ("Fetch".equals(myMod)) {
        sw.transferFocus();
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            sw.reloadWindow();
          }
        });
      }

      if ("UpPressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, '.'));
      }
      if ("DownPressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, '.'));
      }
      if ("LeftPressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, '.'));
      }
      if ("RightPressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, '.'));
      }
      if ("EnterPressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '.'));
      }

      if ("UpReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, '.'));
      }
      if ("DownReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, '.'));
      }
      if ("LeftReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, '.'));
      }
      if ("RightReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, '.'));
      }
      if ("EnterReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '.'));
      }
      if ("ShiftEnterReleased".equals(myMod)) {
          sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), InputEvent.SHIFT_DOWN_MASK, KeyEvent.VK_ENTER, '.'));
        }
      if ("WPressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, '.'));
      }
      if ("SPressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, '.'));
      }
      if ("APressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, '.'));
      }
      if ("DPressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, '.'));
      }
      if ("QPressed".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_Q, '.'));
      }

      if ("WReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_W, '.'));
      }
      if ("SReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_S, '.'));
      }
      if ("AReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A, '.'));
      }
      if ("DReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_D, '.'));
      }
      if ("QReleased".equals(myMod)) {
        sw.performKeyEvent(new KeyEvent(sw, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_Q, '.'));
      }

    }
    if (StandardDialog.class.isInstance(myComp)) {
      StandardDialog sd = (StandardDialog) myComp;
      if ("Save".equals(myMod)) {
        sd.commit();
      }
      if ("SaveExit".equals(myMod)) {
        sd.commit();
        sd.closeWindow();
      }
      if ("Exit".equals(myMod)) {
        sd.discard();
        sd.closeWindow();
      }
      if ("Insert".equals(myMod)) {
        sd.insert();
      }
      if ("Delete".equals(myMod)) {
        sd.delete();
      }
    }
    if(PropertyTextArea.class.isInstance(myComp)){
      PropertyTextArea ta = (PropertyTextArea)myComp;
      if ("ShiftEnterReleased".equals(myMod)) {
     	  System.err.println("Next: "+ta.getFocusCycleRootAncestor().getFocusTraversalPolicy().getComponentAfter(ta.getFocusCycleRootAncestor(), ta).getClass().getSuperclass());
     	  System.err.println("Previous: "+ta.getFocusCycleRootAncestor().getFocusTraversalPolicy().getComponentBefore(ta.getFocusCycleRootAncestor(), ta).getClass().getSuperclass());
     	       ta.transferFocus();
     	       
      }
      if ("ShiftTabReleased".equals(myMod)) {
     	  ta.transferFocusBackward();
        }
    }
  }

}
