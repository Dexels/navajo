package com.dexels.navajo.tipi.components.swingimpl;

import java.net.*;
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
public class TipiLabel
    extends TipiSwingComponentImpl {
  public Object createContainer() {
    TipiSwingLabel myLabel = new TipiSwingLabel(this);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myLabel;
  }

  protected ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }

  public void setComponentValue(final String name, final Object object) {
    if (name.equals("text")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (TipiSwingLabel) getContainer()).setText("" + object);
        }
      });
      ( (TipiSwingLabel) getContainer()).revalidate();
      return;
    }
    if (name.equals("icon")) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (TipiSwingLabel) getContainer()).setIcon(getIcon( (URL) object));
        }
      });
      ( (TipiSwingLabel) getContainer()).revalidate();
      return;
    }
    super.setComponentValue(name, object);
  }

  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return ( (TipiSwingLabel) getContainer()).getText();
    }
    if (name.equals("icon")) {
      return ( (TipiSwingLabel) getContainer()).getIcon();
    }
    return super.getComponentValue(name);
  }
}
