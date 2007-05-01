package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.*;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.components.core.TipiLayoutImpl;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.impl.TipiLayoutManager;
import com.dexels.navajo.tipi.components.echoimpl.impl.layout.EchoLayoutImpl;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;
import com.dexels.navajo.tipi.internal.TipiLayout;

import echopointng.ExtentEx;
import echopointng.able.*;

/**
 * <p>
 * Title:
 * </p>
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

public abstract class TipiEchoDataComponentImpl extends TipiDataComponentImpl {

    // protected TipiLayout layout = null;
    protected EchoLayoutImpl layoutComponent;

    public TipiEchoDataComponentImpl() {
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
        
        
        
    }

//    public Component getLayoutComponent() {
//        return layoutComponent;
//    }

    // public TipiLayout getLayout() {
    // return layout;
    // }

    public void removeFromContainer(Object c) {
        Component cc = (Component) getContainer();
        Component child = (Component) c;
        child.dispose();
        cc.remove(child);
    }

    public Component getInnerComponent() {
    	return (Component)getContainer();
    }
    
    
    public void addToContainer(Object c, Object constraints) {

      	System.err.println("\n********************************\nAttempting to add component: "+c.getClass()+" to: "+getClass()+"\n********************************\n");
      	Component cc;
        cc = (Component) getInnerComponent();
        Component child = (Component) c;
//        if (layoutComponent != null) {
//            cc = layoutComponent;
//        }
        
        
        
        if(layoutComponent!=null) {
        	// do layoutstuff
        	layoutComponent.setParentComponent(this);
        	layoutComponent.addChildComponent(child, constraints);
        } else {
            if (child instanceof WindowPane) {
                TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
                // Watch this.
                final Window w = (Window) s.getTopLevel();
                w.getContent().add(child);
            } else {
                cc.add(child);
                if (constraints != null && constraints instanceof LayoutData) {
                    child.setLayoutData((LayoutData) constraints);
                }
                if (getLayout() != null) {
                    getLayout().childAdded(c);
                }

            }
        }
    
    }

    public void setContainerLayout(Object layout) {
    	if(layout==null) {
    		layoutComponent = null;
    	}
    	if (layout instanceof EchoLayoutImpl) {
    		System.err.println("Setting layout");
                layoutComponent = (EchoLayoutImpl) layout;
//                ((Component) getInnerComponent()).add(layoutComponent);
            }
    }

    /**
     * loadData
     * 
     * @param n
     *            Navajo
     * @param context
     *            TipiContext
     * @throws TipiException
     * @todo Implement this com.dexels.navajo.tipi.TipiDataComponent method
     */
    public void loadData(Navajo n, TipiContext context, String method,String server) throws TipiException, TipiBreakException {
        super.loadData(n, context, method,server);
    }
    
    public void processStyles() {
        super.processStyles();
        if (getContainer()!=null && getContainer() instanceof Positionable) {
            Positionable pos = (Positionable)getContainer();
            String s = getStyle("x");
            if (s!=null) {
//                 pos.setLeft(ExtentParser.parseExtent(s));
//                 pos.setPosition(Positionable.ABSOLUTE);
            }
            s = getStyle("y");
            if (s!=null) {
//                 pos.setTop(ExtentParser.parseExtent(s));
//                 pos.setPosition(Positionable.ABSOLUTE);
            }
         }
        if (getContainer()!=null && getContainer() instanceof Sizeable) {
            Sizeable pos = (Sizeable)getContainer();
            String s = getStyle("w");
            if (s!=null) {
                 pos.setWidth(ExtentParser.parseExtent(s));
             }
            s = getStyle("h");
            if (s!=null) {
                 pos.setHeight(ExtentParser.parseExtent(s));
            }
         }
   
    }
    

}
