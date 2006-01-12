package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.document.*;

import java.awt.print.*;
import com.dexels.navajo.tipi.internal.*;
import java.awt.*;
import java.awt.geom.*;
import java.net.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiFixedPane
    extends TipiPanel implements Pageable, Printable {


  private PageFormat pf = null;
//  private TipiSwingPanel myPanel = null;
  private CardLayout cardLayout;

  public Object createContainer() {
     super.createContainer();
      cardLayout = new CardLayout();
    myPanel.setLayout(cardLayout);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    return myPanel;
  }

  protected ImageIcon getIcon(URL u) {
    return new ImageIcon(u);
  }
  // Hack. dont know how to do this directly
  private void doLoadData(Navajo n, TipiContext tc, String method)  throws TipiException {
      super.loadData(n, tc, method);
  }
      
    public void addToContainer(final Object c, final Object constraints) {
        myPanel.add((Component) c);
        if (constraints != null && constraints instanceof LayoutData) {
            ((ContainerEx) getContainer())
                    .setLayoutData((LayoutData) constraints);

        }
        // ((SwingTipiContext)myContext).addTopLevel(c);
        // });
    }

    public void removeFromContainer(final Object c) {
        // runSyncInEventThread(new Runnable() {
        // public void run() {
        innerContainer.remove((Component) c);
        // ( (JInternalFrame) getContainer()).getContentPane().remove(
        // (Component) c);
        // ((SwingTipiContext)myContext).removeTopLevel(c);
        // }
        // });
    }

    public void setContainerLayout(final Object layout) {
        // runSyncInEventThread(new Runnable() {
        // public void run() {

        // eueueuh
        // ( (JInternalFrame) getContainer()).getContentPane().setLayout(
        // (LayoutManager) layout);
        // }
        // });
    }

    public final void setComponentValue(final String name, final Object object) {
        super.setComponentValue(name, object);
        if (object == null) {
            System.err.println("Null object. Name = " + name);
        } else {
            // System.err.println("Class: "+object.getClass()+" name: "+name);
        }
            // runSyncInEventThread(new Runnable() {
        // public void run() {
        // if (name.equals("iconifiable")) {
        // boolean b = ( (Boolean) object).booleanValue();
        // jj.setIconifiable(b);
        // }
        if (name.equals("background")) {
            innerContainer.setBackground((Color) object);
        }


}
