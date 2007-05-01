package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.Label;
import echopointng.ContainerEx;
import echopointng.LabelEx;
import echopointng.able.Positionable;

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

public class TipiPanel extends TipiEchoDataComponentImpl {

    private ContentPane myContainer;
//    private ContainerEx myContentPane;

	public TipiPanel() {
    }

    public Object createContainer() {
        myContainer = new ContentPane();
//        myContentPane = new ContainerEx();
//		myContainer.add(myContentPane);
//		myContainer.setBackground(new Color(255,0,0));
//		myContentPane.setBackground(new Color(0,0,255));
//		myContentPane.setInsets(new Insets(5,5,5,5));
//		myContainer.setInsets(new Insets(0,0,0,0));
//		
        return myContainer;
    }

 public void addToContainer(Object o, Object contraints){
	 System.err.println("############ Adding to PANEL.");
	 if(layoutComponent!=null) {
		 System.err.println("############ Layout found: "+layoutComponent.getClass());		 
	 }
	 System.err.println("############ Comeponet found: "+getContainer());
	 System.err.println("############ Inner found: "+getInnerComponent());
	 System.err.println("############ My container: "+myContainer.getComponentCount());
	 for (int i = 0; i < myContainer.getComponentCount(); i++) {
		System.err.println("CHILD: "+myContainer.getComponent(i));
	}
	 super.addToContainer(o, contraints);
 }
    //
//     public void setContainerLayout(Object l){
//    
//     }
//    public Component getInnerComponent() {
//    	return myContentPane;
//    }
//  
    public void setComponentValue(final String name, final Object object) {
        if ("background".equals(name)) {
            if (object instanceof Color) {
                myContainer.setBackground((Color) object);
        		return;
            }
        }
//        if ("border".equals(name)) {
//        	if(object instanceof Border) {
//        		Border border = (Border)object;
//        		System.err.println("Border set: "+border);
//    			myContentPane.setBorder(border);
//        		myContainer.setInsets(new Insets(0,20,0,0));
//        		LabelEx l = new LabelEx("Bla");
//        		myContentPane.add(l);
//        		myContentPane.setTop(new Extent(20,Extent.PX));
//        	} else {
//        		System.err.println("HUH? Strange border: "+object);
//        	}
//    		
//    		return;
//        }
        super.setComponentValue(name, object);
    }

}
