package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;
import echopoint.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiDialog
    extends TipiEchoDataComponentImpl {
  public TipiDialog() {
  }

  public Object createContainer() {
    DialogPanel dp = new DialogPanel();
    dp.setBackgroundDithered(false);
    dp.setVisible(false);
    return dp;
  }

  protected void setComponentValue(String name, Object object) {
    DialogPanel w = (DialogPanel) getContainer();
    if ("title".equals(name)) {
      w.getTitleBar().setText("" + object);
    }
    if ("w".equals(name)) {
      w.setWidth( ( (Integer) object).intValue());
    }
    if ("h".equals(name)) {
      w.setHeight( ( (Integer) object).intValue());
    }
    if ("x".equals(name)) {
      w.setLeft( ( (Integer) object).intValue());
    }
    if ("y".equals(name)) {
      w.setTop( ( (Integer) object).intValue());
    }
    super.setComponentValue(name, object);
  }

  protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent e) {
    try {
      super.performComponentMethod(name, compMeth, e);
      DialogPanel myDialog = (DialogPanel) getContainer();
      if (name.equals("show")) {
        myDialog.setVisible(true);
      }
      if (name.equals("hide")) {
        System.err.println("Hiding dialog!!!\n\n\n\n");
        myDialog.setVisible(false);
      }
      if (name.equals("dispose")) {
        System.err.println("Hide dialog: Disposing dialog!");
        myDialog.setVisible(false);
        myContext.disposeTipiComponent(this);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}
