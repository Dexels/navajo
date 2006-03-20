package com.dexels.navajo.tipi.components.echoimpl.helpers;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Button;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.able.Borderable;

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

public class EchoTipiHelper implements TipiHelper {
	public EchoTipiHelper() {
	}

	private TipiComponent myComponent = null;

	public void initHelper(TipiComponent tc) {
//		System.err.println("initHelper: " + tc);
		myComponent = tc;
	}

	public void setComponentValue(String name, Object object) {
//		System.err.println("setComponentValue: " + name);
		if (!Component.class.isInstance(myComponent.getActualComponent())) {
			if (name.equals("visible")) {
				System.err
						.println("Sorry, should not assign a EchoHelper to a non-Echo component");
				((Component) myComponent.getActualComponent())
						.setVisible(((Boolean) object).booleanValue());
			}
			System.err
					.println("returning...from helper.. cause myComponents container is: "
							+ myComponent.getActualComponent());
			return;
		}
		Component c = (Component) myComponent.getActualComponent();
		if (name.equals("background")) {
				c.setBackground((Color) object);
		}
		if (name.equals("foreground")) {
			c.setForeground((Color) object);
		}
		if (name.equals("font")) {
			c.setFont((Font) object);
		}
		if (name.equals("border")) {
System.err.println("SETTING BORDER!!!!!!!!!!!!!!! "+c);
			if (c  instanceof Borderable && object instanceof Border) {
				Borderable b = (Borderable)c;
				b.setBorder((Border)object);
			}
		}

		// if (name.equals("tooltip")) {
		// if (ToolTipSupport.class.isInstance(c)) {
		// ( (Component) c).setToolTipText( (String) object);
		// }
		// }
		if (name.equals("visible")) {
			c.setVisible(((Boolean) object).booleanValue());
		}
		if (name.equals("enabled")) {
			c.setEnabled(((Boolean) object).booleanValue());
		}
		// SHOULD CALL myCOmponent.setComponentValue, I guess.
	}

	public Object getComponentValue(String name) {
		if (!Component.class.isInstance(myComponent.getContainer())) {
			System.err
					.println("Sorry, should not assign a EchoHelper to a non-Echo component");
			return null;
		}
		Component c = (Component) myComponent.getActualComponent();
		// if (name.equals("tooltip")) {
		// if (ToolTipSupport.class.isInstance(c)) {
		// return ( (ToolTipSupport) c).getToolTipText();
		// }
		// }
		if (name.equals("visible")) {
			return new Boolean(((Component) myComponent.getActualComponent())
					.isVisible());
		}
		if (name.equals("background")) {
			return c.getBackground();
		}
		if (name.equals("foreground")) {
			return c.getForeground();
		}
		if (name.equals("font")) {
			return c.getFont();
		}
		if (name.equals("enabled")) {
			return new Boolean(c.isEnabled());
		}
		return null;
	}

	public void deregisterEvent(TipiEvent e) {
//		System.err
//				.println("BEWARE..EVENT IS STILL CONNECTED TO THE COMPONENT!!");
	}

	public void registerEvent(final TipiEvent te) {
		Component c = (Component) myComponent.getActualComponent();
		if (c == null) {
			System.err
					.println("Cannot register echo event: Container is null!");
			return;
		}
		if (te.isTrigger("onActionPerformed", null)) {
			// System.err.println("\nAttempting to REGISTER:
			// onActionPerformed!!\n\n\n\n");
			// MenuItem m;
			Button b;
			try {
				java.lang.reflect.Method m = c.getClass().getMethod(
						"addActionListener",
						new Class[] { ActionListener.class });
				ActionListener bert = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							// System.err.println("Firing component: " +c);
							myComponent.performTipiEvent("onActionPerformed",
									null, true);
						} catch (Throwable ex) {
							ex.printStackTrace();
						}
					}
				};
				m.invoke(c, new Object[] { bert });
			} catch (Exception exe) {
				exe.printStackTrace();
			}
		}

		// void windowClosed(WindowEvent windowEvent);
		// void windowClosing(WindowEvent windowEvent);
		// void windowOpened(WindowEvent windowEvent);

		// if (te.isTrigger("onWindowClosed", null)) {
		// if (Window.class.isInstance(c)) {
		// Window jj = (Window) c;
		// jj.addWindowListener(new WindowAdapter() {
		// public void onWindowClosing(WindowEvent e) {
		// try {
		// myComponent.performTipiEvent("onWindowClosed", null, true);
		// }
		// catch (TipiException ex) {
		// ex.printStackTrace();
		// }
		// }
		// });
		// }
		// }

	}

}
