package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JViewport;

import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingViewport;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
 * @author not attributable
 * @version 1.0
 */

public class TipiViewport extends TipiSwingDataComponentImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5736061458396880332L;

	public TipiViewport() {
	}

	private JPanel left = null;
	private JPanel right = null;
	private int axis = BoxLayout.X_AXIS;
	private LayoutManager layout;
	private JPanel clientPanel;
	private JViewport view;

	public Object createContainer() {
		view = new TipiSwingViewport();
		left = new JPanel();
		right = new JPanel();
		left.setLayout(new BorderLayout());
		right.setLayout(new BorderLayout());
		clientPanel = new JPanel();
		layout = new GridLayout();
		// layout = new BoxLayout(clientPanel, axis);
		clientPanel.setLayout(layout);
		clientPanel.add(left);
		clientPanel.add(right);
		view.setOpaque(false);
		view.addComponentListener(new ComponentAdapter() {

			public void componentResized(ComponentEvent e) {
				updateClientSize(view);
			}

		});

		view.setView(clientPanel);
		return view;
	}

	public void initBeforeBuildingChildren(XMLElement instance,
			XMLElement classdef, XMLElement definition) {
		String constraint = null;
		if (definition != null) {
			constraint = definition.getStringAttribute("constraint");
		}
		if (instance != null) {
			String c = instance.getStringAttribute("constraint");
			if (c != null) {
				c = constraint;
			}
		}
		System.err.println("Consttraints::: " + constraint);
		super.initBeforeBuildingChildren(instance, classdef, definition);
	}

	protected void updateClientSize(JViewport view) {
		Dimension d = view.getSize();
		if (axis == BoxLayout.Y_AXIS) {
			System.err.println("y, vertical");
			Dimension vw = new Dimension(d.width, d.height * 2);
			clientPanel.setPreferredSize(vw);
		} else {
			System.err.println("x, horizontal");
			Dimension vw = new Dimension(d.width * 2, d.height);
			clientPanel.setPreferredSize(vw);
		}
		clientPanel.doLayout();
	}

	public void addToContainer(Object c, Object constraints) {
		if (constraints == null) {
			throw new IllegalArgumentException(
					"Constraint required when adding to a viewport");
		}
		String constr = constraints.toString();
		Component cc = (Component) c;
		if ("bottom".equals(constr)) {
			setAxis("vertical");
			right.add(cc, BorderLayout.CENTER);
		}
		if ("right".equals(constr)) {
			setAxis("horizontal");
			right.add(cc, BorderLayout.CENTER);
		}
		if ("top".equals(constr)) {
			setAxis("vertical");
			left.add(cc, BorderLayout.CENTER);
		}
		if ("left".equals(constr)) {
			setAxis("horizontal");
			left.add(cc, BorderLayout.CENTER);
		}
		// left.setMinimumSize(minimumSize);
		// right.setMinimumSize(minimumSize);
		// left.setPreferredSize(minimumSize);
		// right.setPreferredSize(minimumSize);
	}

	private void setAxis(String ax) {
		if ("vertical".equals(ax)) {
			if (BoxLayout.Y_AXIS != axis) {
				axis = BoxLayout.Y_AXIS;
				updateLayout();
			}
		}
		if ("horizontal".equals(ax)) {

			if (BoxLayout.X_AXIS != axis) {
				axis = BoxLayout.X_AXIS;
				updateLayout();
			}
		}
	}

	/**
 * 
 */
	private void updateLayout() {
		clientPanel.setLayout(new BoxLayout(clientPanel, axis));
	}

	public void setComponentValue(String name, final Object object) {
		super.setComponentValue(name, object);
		// if (name.equals("x")) {
		// runSyncInEventThread(new Runnable() {
		// public void run() {
		// System.err.println("Setx: "+object);
		// view.setViewPosition(new Point((Integer) object, 0));
		// }
		// });
		// }
		//
		// if (name.equals("y")) {
		// runSyncInEventThread(new Runnable() {
		// public void run() {
		// System.err.println("Sety: "+object);
		// view.setViewPosition(new Point(0, (Integer) object));
		// }
		// });
		//
		// }
		// if (name.equals("dividersize")) {
		// int size = ( (Integer) object).intValue();
		// ((TipiSwingSplitPane) getContainer()).setDividerSize(size);
		// }
		// if (name.equals("onetouchexpandable")) {
		// boolean otex = ( (Boolean) object).booleanValue();
		// ((TipiSwingSplitPane) getContainer()).setOneTouchExpandable(otex);
		// }

	}

	// private void updateDividerLocation(){
	// if(!inverse_oriented){
	// ((TipiSwingSplitPane)
	// getContainer()).setDividerLocation(dividerlocation);
	// }else{
	// int orientation = ((TipiSwingSplitPane) getContainer()).getOrientation();
	// int loc = dividerlocation;
	// if(orientation == JSplitPane.HORIZONTAL_SPLIT){
	// loc = ((TipiSwingSplitPane) getContainer()).getWidth() - dividerlocation;
	// }else if(orientation == JSplitPane.VERTICAL_SPLIT){
	// loc = ((TipiSwingSplitPane) getContainer()).getHeight() -
	// dividerlocation;
	// }
	// ((TipiSwingSplitPane) getContainer()).setDividerLocation(loc);
	// }
	// }

}
