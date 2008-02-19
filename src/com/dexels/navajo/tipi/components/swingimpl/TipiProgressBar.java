package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiProgressBar
    extends TipiSwingComponentImpl {
  private TipiSwingProgressBar myProgressBar = null;
  public Object createContainer() {
    myProgressBar = new TipiSwingProgressBar();
    myProgressBar.setMinimum(0);
    myProgressBar.setMaximum(100);
    myProgressBar.setValue(0);
    myProgressBar.setStringPainted(true);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myProgressBar;
  }

  public Object getContainer() {
    return myProgressBar;
  }

  public void setComponentValue(final String name, final Object object) {
    if (name.equals("text")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          myProgressBar.setString( (String) object);
        }
      });
      return;
    }
    if (name.equals("value")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          int value = (int) Float.parseFloat("" + object);
          myProgressBar.setValue(value);
          myProgressBar.setString("" + value + "%");
        }
      });
      return;
    }
    if (name.equals("orientation")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          String or = (String) object;
          if ("horizontal".equals(or)) {
            myProgressBar.setOrientation(JProgressBar.HORIZONTAL);
          }
          if ("vertical".equals(or)) {
            myProgressBar.setOrientation(JProgressBar.VERTICAL);
          }
        }
      });
      return;
    }
    if (name.equals("indeterminate")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          myProgressBar.setIndeterminate( ( (Boolean) object).booleanValue());
          if (! ( (Boolean) object).booleanValue()) {
            myProgressBar.setMinimum(0);
            myProgressBar.setMaximum(100);
          }
        }
      });
      return;
    }
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myProgressBar.getString();
    }
    if (name.equals("value")) {
      return new Integer(myProgressBar.getValue());
    }
    if (name.equals("orientation")) {
      int orientation = myProgressBar.getOrientation();
      switch (orientation) {
        case JProgressBar.HORIZONTAL:
          return "horizontal";
        case JProgressBar.VERTICAL:
          return "vertical";
      }
    }
    return super.getComponentValue(name);
  }
}
