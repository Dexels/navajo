package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Window;

import com.dexels.navajo.tipi.components.echoimpl.impl.TipiLayoutManager;

import echopointng.ContainerEx;
import echopointng.MenuBar;
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

public class TipiFrame extends TipiEchoDataComponentImpl {

	private ContentPane myContentPane = new ContentPane();

	private Window myWindow;

	private ContainerEx innerContainer;

	private ContainerEx contentPane;

	// private ContentPane myMainPanel = null;
	// private ContentPane myPanel = null;
	// private ContentPane myMenuPanel = null;

	// private String cssSheet = "c:/echotipi.css";

	public TipiFrame() {
	}

	public Window getWindow() {
		return myWindow;
	}

	public Object createContainer() {
		System.err.println("Creating window...................");
		myWindow = new Window();
		// myWindow.getContent().setBackground(new Color(200,200,230));
		innerContainer = new ContainerEx();
		// innerContainer.setBackground(new Color(230,200,250));
		myWindow.getContent().add(innerContainer);
		innerContainer.setWidth(new Extent(100, Extent.PERCENT));
		innerContainer.setHeight(new Extent(100, Extent.PERCENT));

		contentPane = new ContainerEx();
		// contentPane.setBackground(new Color(230,200,250));
		innerContainer.add(contentPane);
		contentPane.setWidth(new Extent(100, Extent.PERCENT));
		contentPane.setHeight(new Extent(100, Extent.PERCENT));

		// myPanel.add(myMenuPanel);
		// manager.newLine();
		// myPanel.add(myMainPanel);
		// myContentPane.add(myPanel);
		// HourGlass h = new HourGlass();
		// w.getContent().add(h);
		return myWindow;

	}

	// public Object getActualComponent() {
	// return myWindow;
	// }

	public void setContainerLayout(Object layout) {
		// if (getLayoutComponent()!=null) {
		// innerContainer.remove(getLayoutComponent());
		// }
		layoutComponent = (TipiLayoutManager) layout;
		// innerContainer.add(layoutComponent);

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
	protected void setComponentValue(String name, Object object) {
		if ("title".equals(name)) {
			myWindow.setTitle("" + object);
		}
		// if ("w".equals(name)) {
		// w.setWidth( ( (Integer) object).intValue());
		// }
		// if ("h".equals(name)) {
		// w.setHeight( ( (Integer) object).intValue());
		// }
		super.setComponentValue(name, object);
	}

	public void addToContainer(Object c, Object constraints) {
		if (c instanceof MenuBar) {
			System.err.println("Menubar found");
			MenuBar m = (MenuBar) c;
			m.setPosition(Positionable.ABSOLUTE);
			m.setTop(new Extent(0, Extent.PX));
			m.setWidth(new Extent(100, Extent.PERCENT));
			m.setHeight(new Extent(20, Extent.PX));
			innerContainer.add(m);
			contentPane.setTop(new Extent(20, Extent.PX));
		} else {
			// if (c instanceof WindowPane || c instanceof ContainerEx) {
			contentPane.add((Component) c);
			// innerContainer.add((Component)c);
			// } else {
			// contentPane.add((Component)c);
			// }
		}
		// System.err.println("MYCONTAINER: "+innerContainer.getWidth());
		// System.err.println("MYCONTAINER: "+innerContainer.getHeight());
		// if (layoutComponent!=null) {
		// layoutComponent.add((Component)c);
		// } else {
		// innerContainer.add((Component)c);

		// }
		// if (layoutComponent!=null) {
		// layoutComponent.layoutContainer((Component)getContainer());
		// }

		// myContentPane.add((Component)c);
		// if (MenuBar.class.isInstance(c)) {
		// GridLayoutManager manager = new GridLayoutManager(1, 1);
		// myMenuPanel.setLayoutManager(manager);
		// manager.setFullWidth(true);
		// myMenuPanel.setBackground( ( (MenuBar) c).getBackground());
		//
		// Label l = new Label(" \n ");
		// myMenuPanel.add( (Component) c);
		// myMenuPanel.add(l);
		//
		// }
		// else {
		// myMainPanel.add( (Component) c);
		// }
	}

	protected Object getComponentValue(String name) {
		if ("title".equals(name)) {
			return myWindow.getTitle();
		}
		return super.getComponentValue(name);
	}

}
