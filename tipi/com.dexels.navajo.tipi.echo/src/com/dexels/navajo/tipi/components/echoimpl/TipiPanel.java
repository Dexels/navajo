package com.dexels.navajo.tipi.components.echoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.LayoutData;
import echopointng.GroupBox;


public class TipiPanel extends TipiEchoDataComponentImpl {

	private static final long serialVersionUID = 5875923124427412379L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiPanel.class);
	
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

   public Object getActualComponent() {
	   return myContainer;
   }

//public void addToContainer(Object o, Object contraints){
//	 logger.info("############ Adding to PANEL.");
//	 if(layoutComponent!=null) {
//		 logger.info("############ Layout found: "+layoutComponent.getClass());		 
//	 }
//	 logger.info("############ Comeponet found: "+getContainer());
//	 logger.info("############ Inner found: "+getInnerComponent());
//	 logger.info("############ My container: "+myContainer.getComponentCount());
//	 for (int i = 0; i < myContainer.getComponentCount(); i++) {
//		logger.info("CHILD: "+myContainer.getComponent(i));
//	}
//	 super.addToContainer(o, contraints);
// }
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

        if ("border".equals(name)) {
          	logger.info("SET_BORDER_GRIDPANEL: "+object);
            Component parent = myContainer.getParent();
            if(parent!=null) {
            	LayoutData ld = parent.getLayoutData();
            	parent.remove(myContainer);
            	GroupBox gb = new GroupBox(""+object);
            	gb.add(myContainer);
            	if(ld!=null) {
            		gb.setLayoutData(ld);
            	}
            	parent.add(gb);
            	setContainer(gb);

            } else {
            	GroupBox gb = new GroupBox(""+object);
            	gb.add(myContainer);
            	setContainer(gb);
            }

        	
        	return;
         }                 
//        if ("border".equals(name)) {
//        	if(object instanceof Border) {
//        		Border border = (Border)object;
//        		logger.info("Border set: "+border);
//    			myContentPane.setBorder(border);
//        		myContainer.setInsets(new Insets(0,20,0,0));
//        		LabelEx l = new LabelEx("Bla");
//        		myContentPane.add(l);
//        		myContentPane.setTop(new Extent(20,Extent.PX));
//        	} else {
//        		logger.info("HUH? Strange border: "+object);
//        	}
//    		
//    		return;
//        }
        super.setComponentValue(name, object);
    }

}
