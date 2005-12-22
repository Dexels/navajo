package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.ContentPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.LayoutData;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.WindowPane;

import com.dexels.navajo.tipi.components.echoimpl.impl.TipiLayoutManager;

import echopointng.ContainerEx;
import echopointng.MenuBar;
import echopointng.able.Positionable;
import echopointng.able.Sizeable;

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
		myWindow = new Window();
		// myWindow.getContent().setBackground(new Color(200,200,230));
		innerContainer = new ContainerEx();
		// innerContainer.setBackground(new Color(230,200,250));
		myWindow.getContent().add(innerContainer);
//		innerContainer.setWidth(new Extent(100, Extent.PERCENT));
//		innerContainer.setHeight(new Extent(100, Extent.PERCENT));

		contentPane = new ContainerEx();
		// contentPane.setBackground(new Color(230,200,250));
		innerContainer.add(contentPane);
//		contentPane.setWidth(new Extent(100, Extent.PERCENT));
//		contentPane.setHeight(new Extent(100, Extent.PERCENT));

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


	// NEARLY IDENTICAL TO PARENT TODO: REFACTOR!

	public void setContainerLayout(Object layout) {
		System.err.println("*************** SETTTING CONTAINER LAYOUT: "+layout);
//		layoutComponent = layout;
//
//		if (layout instanceof TipiLayoutManager) {
//			/* 'Real layout' */
//			// layoutComponent = (TipiLayoutManager)layout;
//		} else {
//			if (layout instanceof Component) {
//				layoutComponent = (Component)layout;
//				if (layoutComponent instanceof Sizeable) {
//					((Sizeable)layoutComponent).setWidth(new Extent(100,Extent.PERCENT));
//					((Sizeable)layoutComponent).setHeight(new Extent(100,Extent.PERCENT));
//				}
//				System.err.println("Component layout found!!");
//				contentPane.add(layoutComponent);
//				
//			} else {
//				System.err.println("*********************\nStrange layout found!\n*********************");
//			}
//		}
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
			Component child = (Component)c;
//			if (layoutComponent!=null) {
//				layoutComponent.add(child);
//
//			} else {
				contentPane.add(child);
//			}
			if (constraints != null && constraints instanceof LayoutData) {
				child.setLayoutData((LayoutData) constraints);
				System.err.println(">>>>>>>>>>>" + (LayoutData) constraints);
			}
			
//			if (getLayout() != null) {
//				getLayout().childAdded(c);
//			}
			}
	}

	public void removeFromContainer(Object c) {
		Component cc = (Component) getContainer();
		Component child = (Component) c;
		if (c instanceof MenuBar) {
			innerContainer.remove((Component)c);
			
		} else {
			contentPane.remove((Component)c);
			
			
		}
//		cc.remove(child);
	}	
	
	protected Object getComponentValue(String name) {
		if ("title".equals(name)) {
			return myWindow.getTitle();
		}
		return super.getComponentValue(name);
	}

}
