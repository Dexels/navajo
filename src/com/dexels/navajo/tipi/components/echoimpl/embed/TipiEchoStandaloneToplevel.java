package com.dexels.navajo.tipi.components.echoimpl.embed;

import nextapp.echo2.app.*;

import com.dexels.navajo.tipi.components.echoimpl.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiEchoStandaloneToplevel extends TipiPanel
     {

private final ContentPane myPanel; //= new JPanel();
  private final BorderLayout myLayout = new BorderLayout();

  public TipiEchoStandaloneToplevel(ContentPane jj) {
//    myPanel.setLayout(myLayout);
 	  super.setName("init");
 	  if (jj==null) {
		myPanel = new ContentPane();
	} else {
	   myPanel = jj;
	}
 	  setId("init");
    initContainer();
  }


  public TipiEchoStandaloneToplevel() {
		 this(null);
//		super.setName("init");
//		myPanel = new ContentPane();
//		setId("init");
//		initContainer();
	}
  public void addToContainer(final Object c, final Object constraints) {
//	  runAsyncInEventThread(new Runnable(){

//		public void run() {
			  if (myPanel!=null) {
				  //System.err.println("Adding to toplevel: "+c.getClass()+ " -- "+c.hashCode());
				  System.err.println("ADDING TO PANEL: "+myPanel.getComponentCount()+" - "+c);
				  try {
					myPanel.add((Component) c);
				} catch (RuntimeException e) {
					System.err.println("Whatever");
					e.printStackTrace();
				}
				  System.err.println("BEWArE: Tiplet hack");
			
			} 
//		}});
	
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

//  public ContentPane getContentPane() {
//    return myPanel;
//  }
//
//  public void setContentPane(ContentPane contentPane) {
//  }
//
  }
