/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingViewport;
import com.dexels.navajo.tipi.tipixml.XMLElement;

public class TipiViewport extends TipiSwingDataComponentImpl {

	private static final long serialVersionUID = 5736061458396880332L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiViewport.class);
	
	public TipiViewport() {
	}

	private JPanel left = null;
	private JPanel right = null;
	private int axis = BoxLayout.X_AXIS;
	private LayoutManager layout;
	private JPanel clientPanel;
	private JViewport view;

	@Override
	public Object createContainer() {
		view = new TipiSwingViewport();
		left = new JPanel();
		right = new JPanel();
		left.setOpaque(false);
		right.setOpaque(false);
		left.setLayout(new BorderLayout());
		right.setLayout(new BorderLayout());
		clientPanel = new JPanel();
		layout = new GridLayout();
		// layout = new BoxLayout(clientPanel, axis);
		clientPanel.setLayout(layout);
		clientPanel.add(left);
		clientPanel.add(right);
		clientPanel.setOpaque(false);
		view.setOpaque(false);
		view.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				updateClientSize(view);
			}

		});

		view.setView(clientPanel);
		return view;
	}

	@Override
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
		logger.debug("Constraints::: " + constraint);
		super.initBeforeBuildingChildren(instance, classdef, definition);
	}

	protected void updateClientSize(JViewport view) {
		Dimension d = view.getSize();
		if (axis == BoxLayout.Y_AXIS) {
			logger.debug("y, vertical");
			Dimension vw = new Dimension(d.width, d.height * 2);
			clientPanel.setPreferredSize(vw);
		} else {
			logger.debug("x, horizontal");
			Dimension vw = new Dimension(d.width * 2, d.height);
			clientPanel.setPreferredSize(vw);
		}
		clientPanel.doLayout();
	}

	@Override
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

	@Override
	public void setComponentValue(String name, final Object object) {
		super.setComponentValue(name, object);
	}
}
