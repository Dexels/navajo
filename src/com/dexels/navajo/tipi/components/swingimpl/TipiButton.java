package com.dexels.navajo.tipi.components.swingimpl;

import java.net.*;
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiButton
    extends TipiSwingComponentImpl {
  private TipiSwingButton myButton;
  public Object createContainer() {
    myButton = new TipiSwingButton(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myButton;
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if (name.equals("text")) {
      myButton.setText( (String) object);
    }
    if (name.equals("icon")) {
      myButton.setIcon(getIcon( (URL) object));
    }
  }

  private ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myButton.getText();
    }
    return super.getComponentValue(name);
  }

  private boolean enabled = false;
  public void eventStarted(TipiEvent te, Object event) {
    System.err.println("EVENT STARTED IN BUTTON!!\n");
    if (Container.class.isInstance(getContainer())) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          enabled = ( (Container) getContainer()).isEnabled();
          System.err.println("WAS ENABLED: " + enabled);
          ( (Container) getContainer()).setEnabled(false);
        }
      });
    }
  }

  public void eventFinished(TipiEvent te, Object event) {
    System.err.println("EVENT ENDED IN BUTTON\n");
    if (Container.class.isInstance(getContainer())) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          ( (Container) getContainer()).setEnabled(enabled);
        }
      });
    }
  }
}