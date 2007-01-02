package com.dexels.navajo.tipi.components.swingimpl;

import java.net.*;
import java.util.*;
import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMenuItem
    extends TipiSwingComponentImpl {
  private TipiContext myContext = null;
  private ArrayList myEvents = new ArrayList();
  private TipiSwingMenuItem myItem;


  private boolean iAmEnabled = true;

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myItem.getText();
    }
    if (name.equals("icon")) {
      return myItem.getIcon();
    }
    if (name.equals("mnemonic")) {
      return "" + myItem.getMnemonic();
    }
    if (name.equals("icon")) {
      return myItem.getIcon();
    }
    if (name.equals("accelerator")) {
      if (myItem.getAccelerator()!=null) {
        return myItem.getAccelerator().toString();
      } else {
        return "";
      }
    }
    return super.getComponentValue(name);
  }

  public void addEvent(TipiEvent te) {
    myEvents.add(te);
  }

  public Object createContainer() {
    myItem = new TipiSwingMenuItem(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myItem;
  }

  public Object getContainer() {
    return myItem;
  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name, object);
    if ("text".equals(name)) {
      myItem.setText( (String) object);
    }
    if ("mnemonic".equals(name)) {
      String ch = (String) object;
      char mn = ch.charAt(0);
      myItem.setMnemonic(mn);
    }
    if ("accelerator".equals(name)) {
      myItem.setAccelerator(KeyStroke.getKeyStroke( (String) object));
    }
    if (name.equals("icon")) {
        //System.err.println("Type: "+object.getClass());
      if (object instanceof URL) {
          myItem.setIcon(getIcon( (URL) object));
      } else {
          System.err.println("Ignoring strange resource");
      }
  }
    if (name.equals("enabled")) {
      // Just for the record.
    	if (object instanceof String) {
    	    iAmEnabled = "true".equals(object);
    	    myItem.setEnabled(iAmEnabled);
    	} else {
    	      iAmEnabled = ((Boolean)object).booleanValue();
    	}
    }
  }
  
  private ImageIcon getIcon(URL u) {
      return new ImageIcon(u);
    }
  
//  public void load(XMLElement def, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
//    super.load(def, instance, context);
//  }

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
