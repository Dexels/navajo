package com.dexels.navajo.tipi.components.echoimpl;

import java.lang.reflect.*;

import javax.swing.*;

import nextapp.echo2.app.*;
import nextapp.echo2.webcontainer.*;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.echoclient.components.*;
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


    
    public void removeFromContainer(final Object c) {
		runSyncInEventThread(new Runnable() {

			public void run() {

				Component cc = (Component) getContainer();
				Component child = (Component) c;
				child.dispose();
				cc.remove(child);
			}
		});
	}

    public Component getInnerComponent() {
    	return (Component)getContainer();
    }
    
    
    public void addToContainer(final Object c, final Object constraints) {
    	runSyncInEventThread(new Runnable(){

			public void run() {
				Component cc;
		        cc = (Component) getActualComponent();
		        Component child = (Component) c;
		          if(layoutComponent!=null) {
		        	layoutComponent.setParentComponent(TipiEchoDataComponentImpl.this);
		        	layoutComponent.addChildComponent(child, constraints);
		        } else {
		            if (child instanceof WindowPane) {
		                TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
		                cc.add(child);
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
    	});
      	
    }

    public void setContainerLayout(Object layout) {
    	if(layout==null) {
    		layoutComponent = null;
    	}
    	if (layout instanceof EchoLayoutImpl) {
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
    public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {
        super.loadData(n,  method);
    }

    protected void setComponentValue(final String name, final Object object) {
        
    	runSyncInEventThread(new Runnable(){

			public void run() {
			       if ("border".equals(name)) {
		            if (getContainer()!=null && getContainer() instanceof Borderable) {
		                Borderable comp = (Borderable)getContainer();
		                comp.setBorder((Border)object);
//		                comp.set
		            }
	             }
		            if ("style".equals(name)) {
					if (getContainer() instanceof Component) {
						Component c = (Component) getContainer();
						c.setStyle(Styles.DEFAULT_STYLE_SHEET.getStyle(c.getClass(), (String) object));

					} else {
						myContext.showInternalError("NO echo component: " + getContainer());
					}
				}

			}
    	});
        
        super.setComponentValue(name, object);

    }
//    public void processStyles() {
//        super.processStyles();
//        if (getContainer()!=null && getContainer() instanceof Positionable) {
//            Positionable pos = (Positionable)getContainer();
//            String s = getStyle("x");
//            if (s!=null) {
////                 pos.setLeft(ExtentParser.parseExtent(s));
////                 pos.setPosition(Positionable.ABSOLUTE);
//            }
//            s = getStyle("y");
//            if (s!=null) {
////                 pos.setTop(ExtentParser.parseExtent(s));
////                 pos.setPosition(Positionable.ABSOLUTE);
//            }
//         }
//        if (getContainer()!=null && getContainer() instanceof Sizeable) {
//            Sizeable pos = (Sizeable)getContainer();
//            String s = getStyle("w");
//            if (s!=null) {
//                 pos.setWidth(ExtentParser.parseExtent(s));
//             }
//            s = getStyle("h");
//            if (s!=null) {
//                 pos.setHeight(ExtentParser.parseExtent(s));
//            }
//         }
//   
//    }
    

}
