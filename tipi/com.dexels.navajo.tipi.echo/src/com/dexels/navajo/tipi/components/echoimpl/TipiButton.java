package com.dexels.navajo.tipi.components.echoimpl;

import java.net.URL;

import nextapp.echo2.app.Button;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import com.dexels.navajo.echoclient.components.ButtonImpl;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.image.URLImageReference;

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

public class TipiButton extends TipiEchoComponentImpl {
  
	private static final long serialVersionUID = -4423443422594659998L;
	private Button myButton;

    public TipiButton() {
    }

    public Object createContainer() {
        // ContainerEx ex = new ContainerEx();
        myButton = new ButtonImpl();
//        myButton.setTextAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
//        myButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
        myButton.addActionListener(new ActionListener(){
			private static final long serialVersionUID = 9170319735538270910L;

			public void actionPerformed(ActionEvent arg0) {
				try {
					performTipiEvent("onActionPerformed",null, false);
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}});
        return myButton;
    }

//    public void processStyles() {
////        System.err.println("Processing styles.... "+styleHintMap);
//        super.processStyles();
//        Color c = ColorParser.parseColor(getStyle("foreground"));
//        if (c!=null) {
//            myButton.setForeground(c);
//        }
//        c = ColorParser.parseColor(getStyle("background"));
//        if (c!=null) {
//            myButton.setBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("pressedforeground"));
//        if (c!=null) {
//            myButton.setPressedForeground(c);
//        }
//        c = ColorParser.parseColor(getStyle("pressedbackground"));
//        if (c!=null) {
//            myButton.setPressedBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("rolloverbackground"));
//        if (c!=null) {
//            myButton.setRolloverBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("rolloverforeground"));
//        if (c!=null) {
//            myButton.setRolloverForeground(c);
//        }
//      
//    }
    /**
     * getComponentValue
     * 
     * @param name
     *            String
     * @return Object
     * @todo Implement this
     *       com.dexels.navajo.tipi.components.core.TipiComponentImpl method
     */
    protected Object getComponentValue(String name) {
        return "";
    }
    
    protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
        if ("fireAction".equals(name)) {
          for (int i = 0; i < getEventList().size(); i++) {
            TipiEvent current = getEventList().get(i);
            if (current.isTrigger("onActionPerformed")) {
              try {
//            	  System.err.println("Button performing action (explicit fire): "+current.getEventName());
                current.performAction(current,current,0);
              }
              catch (TipiException ex) {
                ex.printStackTrace();
              } catch (TipiBreakException e) {
            	  System.err.println("Break in event");
              }
            }
          }
        }
      }

    public Object getActualComponent() {
        return myButton;
    }

    protected void setComponentValue(String name, Object object) {
        // Button b = (Button) getContainer();
       if ("text".equals(name)) {
            myButton.setText("" + object);
        }
       if ("visible".equals(name)) {
    	   System.err.println("Setting visible to: "+object);
           myButton.setVisible((Boolean)object);
       }

        if ("tooltip".equals(name)) {
            myButton.setToolTipText("" + object);
        }
//        if ("style".equals(name)) {
//            myButton.setStyle(Styles.DEFAULT_STYLE_SHEET.getStyle(Button.class, (String)object));
//        }
        if ("icon".equals(name)) {
            if (object instanceof URL) {
                URL u = (URL) object;
//                System.err.println("Setting URL icon for button: "+u);
                myButton.setIcon(new URLImageReference(u));
            } else {
                System.err.println("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }
        
         super.setComponentValue(name, object);
    }

}
