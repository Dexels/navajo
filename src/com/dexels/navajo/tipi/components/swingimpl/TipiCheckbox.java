package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.core.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import java.net.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiCheckbox extends TipiSwingComponentImpl {
  private JCheckBox myButton;

  private boolean iAmEnabled = true;

  public Object createContainer() {
    myButton = new JCheckBox();
    myButton.setOpaque(false);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myButton;
  }

  public void setComponentValue(final String name, final Object object) {
    super.setComponentValue(name, object);
    runSyncInEventThread(new Runnable() {
      public void run() {
        if (name.equals("text")) {
          myButton.setText( (String) object);
        }
        if (name.equals("icon")) {
          myButton.setIcon(getIcon( (URL) object));
        }
        if (name.equals("enabled")) {
          // Just for the record.
          iAmEnabled = ((Boolean)object).booleanValue();
        }
        if (name.equals("selected")) {
          myButton.setSelected(((Boolean)object).booleanValue());
         }
      }
    });
  }

  private ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myButton.getText();
    }
    if (name.equals("selected")) {
      return new Boolean(myButton.isSelected());
    }

    return super.getComponentValue(name);
  }

//  private boolean enabled = false;
  public void eventStarted(TipiExecutable te, Object event) {
    if (Container.class.isInstance(getContainer())) {
      runSyncInEventThread(new Runnable() {
        public void run() {
//          enabled = ( (Container) getContainer()).isEnabled();
          getSwingContainer().setEnabled(false);
        }
      });
    }
  }

  public void eventFinished(TipiExecutable te, Object event) {
    if (Container.class.isInstance(getContainer())) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (Container) getContainer()).setEnabled(iAmEnabled);
        }
      });
    }
  }

}
