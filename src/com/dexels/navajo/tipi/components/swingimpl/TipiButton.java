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

  private boolean iAmEnabled = true;

  public Object createContainer() {
    myButton = new TipiSwingButton(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
     return myButton;
  }

  public final void setComponentValue(final String name, final Object object) {
    super.setComponentValue(name, object);
    runSyncInEventThread(new Runnable() {
      public void run() {
        if (name.equals("text")) {
          myButton.setText( (String) object);
        }
        if (name.equals("icon")) {
              //System.err.println("Type: "+object.getClass());
            if (object instanceof URL) {
                myButton.setIcon(getIcon( (URL) object));
            } else {
                System.err.println("Ignoring strange resource");
            }
        }
        if (name.equals("enabled")) {
          // Just for the record.
          iAmEnabled = ((Boolean)object).booleanValue();
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

  protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
    if ("fireAction".equals(name)) {
      for (int i = 0; i < getEventList().size(); i++) {
        TipiEvent current = (TipiEvent) getEventList().get(i);
        if (current.isTrigger("onActionPerformed", "aap")) {
          try {
            current.performAction(current);
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      }
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
