package com.dexels.navajo.tipi.components.swingimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import tipi.*;

public class TipiDesktop
    extends TipiSwingDataComponentImpl {
  public Object createContainer() {
    TipiSwingDesktop jp = new TipiSwingDesktop(this);
    jp.setDesktopManager(new DefaultDesktopManager());
    jp.setDragMode(TipiSwingDesktop.LIVE_DRAG_MODE);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return jp;
  }

  public void addToContainer(final Object c, final Object constraints) {
    final TipiDesktop td = this;
    runSyncInEventThread(new Runnable() {
      public void run() {
        System.err.println("I am: "+getPath());

//        System.err.println("StudioScreenPatH: "+myContext.getStudioScreen().getPath()+"=="+myContext.getStudioScreen().toString());
//        System.err.println("And I am:: "+getPath()+"=="+this.toString());
//        if (myContext.getStudioScreen() == td) {
//          System.err.println("Yes! \n  I am the studio desktop!\n         YIPEE!");
//        }
        getSwingContainer().add( (Component) c, constraints);
        TipiSwingWindow tw = (TipiSwingWindow) c;
        tw.toFront();
        getSwingContainer().repaint();
      }
    });
  }

  public void removeFromContainer(final Object c) {
    runSyncInEventThread(new Runnable() {
      public void run() {
        getSwingContainer().remove( (Component) c);
        getSwingContainer().repaint();
      }
    });
  }

//  public DefaultTipiDesktop() {
//    initContainer();
//  }
//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    super.load(definition,instance,context);
//  }
  public void setComponentValue(final String name, final Object value) {
    if ("logo".equals(name)) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ImageIcon im = new ImageIcon(MainApplication.class.getResource( (String) value));
          if (im != null) {
            ( (TipiSwingDesktop) getContainer()).setImage(im.getImage());
          }
        }
      });
    }
    super.setComponentValue(name, value);
  }

  protected void addedToParent() {
    runSyncInEventThread(new Runnable() {
      public void run() {
        ( (TipiSwingDesktop) getContainer()).revalidate();
      }
    });
//    ( (TipiSwingDesktop) getContainer()).paintImmediately(0, 0, 100, 100);
    super.addedToParent();
  }
}
