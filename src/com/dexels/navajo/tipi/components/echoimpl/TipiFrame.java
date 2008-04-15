package com.dexels.navajo.tipi.components.echoimpl;

//import tucana.echo2.app.ModalDimmer;
import nextapp.echo2.app.*;
import nextapp.echo2.app.layout.GridLayoutData;
import nextapp.echo2.extras.app.MenuBarPane;
import nextapp.echo2.extras.app.menu.DefaultMenuModel;
import nextapp.echo2.extras.app.menu.DefaultOptionModel;

import com.dexels.navajo.tipi.components.echoimpl.impl.*;
import com.dexels.navajo.tipi.components.echoimpl.impl.layout.EchoLayoutImpl;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;

import echopointng.ContainerEx;
import echopointng.MenuBar;
import echopointng.able.Positionable;
import echopointng.able.Sizeable;

/**
 * <p>
 * Title:
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */


public class TipiFrame extends TipiEchoDataComponentImpl {


    private Window myWindow;


    private ContentPane contentPane;

	private SplitPane mySplit;

    private ContentPane realContent;

	private ContentPane menuPane;

    public TipiFrame() {
   }

    public Window getWindow() {

    	return myWindow;
    }

	
    public Object createContainer() {
       contentPane = new GradientContentPane();
         mySplit = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
        contentPane.add(mySplit);
        menuPane = new ContentPane();
        realContent = new ContentPane();
        mySplit.add(menuPane); 
        mySplit.add(realContent);
        realContent.setInsets(new Insets(10,10,10,10));
        
        mySplit.setSeparatorHeight(new Extent(0,Extent.PX));
        mySplit.setSeparatorPosition(new Extent(0,Extent.PX));
//        realContent.setBackgroundImage(new FillImage(new ResourceImageReference("gradient.png","image/png",new Extent(1,Extent.PX),new Extent(100,Extent.PX))));
        
      	if(getContext().getParentContext()!=null) {
      		return contentPane;
      	} else {
            myWindow = new Window();
            myWindow.setContent(contentPane);
            return myWindow;
      	}
      		      

    }

    
    
    /**
     * setComponentValue
     * 
     * @param name
     *            String
     * @param object
     *            Object
     * @todo Implement this
     *       com.dexels.navajo.tipi.components.core.TipiComponentImpl method
     */
    protected void setComponentValue(final String name, final Object object) {
//    	runAsyncInEventThread(new Runnable(){
//
//			public void run() {
			  	if ("title".equals(name)) {
			  		if(myWindow!=null) {
			            myWindow.setTitle("" + object);
			  		}
		        }
		        if ("background".equals(name)) {
		            if (object instanceof Color) {
		                contentPane.setBackground((Color) object);
		                myWindow.setBackground((Color) object);
		                         }
		        }
				
//			}});

          super.setComponentValue(name, object);

    }

    
    

    public Component getInnerComponent() {
    	return realContent;
    }
    
    
    public void addToContainer(final Object c, final Object constraints) {
    	runSyncInEventThread(new Runnable(){
			public void run() {
		        if (c instanceof nextapp.echo2.extras.app.MenuBarPane) {
		        	MenuBarPane m = (MenuBarPane) c;
		        	mySplit.setSeparatorPosition(new Extent(25,Extent.PX));
		        	menuPane.add(m); 
		        } else {
		            Component child = (Component) c;
		            Component cc = realContent;
		            if (child instanceof WindowPane) {
		            	addWindowPane((WindowPane)child);
		            } else {
		                 if(layoutComponent!=null) {
		                 	// do layoutstuff
		                 	layoutComponent.setParentComponent(TipiFrame.this);
		                 	layoutComponent.addChildComponent(child, constraints);
		                 } else {
		                     cc.add(child);
		                         	 
		                 }
		               if (constraints != null && constraints instanceof LayoutData) {
		                    child.setLayoutData((LayoutData) constraints);
		                }
		                if (getLayout() != null) {
		                    getLayout().childAdded(c);
		                }
		            }
		        }			}});
    	

    }

	private void addWindowPane(WindowPane child) {
		System.err.println("Windowpane detected.");
		System.err.println("is viz: "+child.isVisible()+" w: "+child.getWidth()+" h: "+child.getHeight());
		TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
		// Watch this.
		final Window w = (Window) s.getTopLevel();
		if(w==null) {
			System.err.println("oh dear, no toplevel found");
		}
		w.getContent().add(child);
	}

    public void removeFromContainer(final Object c) {
       	runSyncInEventThread(new Runnable(){
			public void run() {
	        	realContent.remove((Component) c);
			}
       	});

    }

    protected Object getComponentValue(String name) {
        if ("title".equals(name)) {
            return myWindow.getTitle();
        }
        return super.getComponentValue(name);
    }

}
