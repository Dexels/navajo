package com.dexels.navajo.tipi.components.swingimpl.embed;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiStandaloneToplevel extends TipiPanel
    implements RootPaneContainer {

private final JComponent myPanel; //= new JPanel();
  private final BorderLayout myLayout = new BorderLayout();

  public TipiStandaloneToplevel(JComponent jj) {
//    myPanel.setLayout(myLayout);
 	  super.setName("init");
 	  if (jj==null) {
		myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout());
	} else {
	 	  myPanel = jj;
	}
 	  setId("init");
    initContainer();
  }


  public TipiStandaloneToplevel() {
	  this(null);
  }
  public void addToContainer(final Object c, final Object constraints) {
	  if (myPanel!=null) {
		  //System.err.println("Adding to toplevel: "+c.getClass()+ " -- "+c.hashCode());
		  
		  System.err.println("BEWArE: Tiplet hack");
		  runSyncInEventThread(new Runnable(){

			  public void run() {
//				  myPanel.add((Component)c,BorderLayout.CENTER);
			}});
	} 
  }

  public void setLayout(TipiLayout tl) {
    // no way jose
  }

  public Object getContainerLayout() {
    return myLayout;
  }

  public void setContainerLayout(Object o) {
    // no way jose
  }

  public Object createContainer() {
    return myPanel;
  }

  public Component getGlassPane() {
    return null;
  }

  public void setGlassPane(Component glassPane) {
  }

  public Container getContentPane() {
    return myPanel;
  }

  public void setContentPane(Container contentPane) {
  }

  public JLayeredPane getLayeredPane() {
	  System.err.println("GETTING LAYERED PANE. BEWARE");
    return null;
  }

  public void setLayeredPane(JLayeredPane layeredPane) {
  }

  public JRootPane getRootPane() {
	  System.err.println("GETTING ROOT PANE. BEWARE");
    return null;
  }

}
