package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Title: VerticalFlowLayout
 * </p>
 * <p>
 * Description: Vertical implementation of the wellknown FlowLayout
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class VerticalFlowLayout implements LayoutManager {
	private List<Component> components = new ArrayList<Component>();
	private String location;

	public VerticalFlowLayout() {
		this(BorderLayout.EAST);
	}

	public VerticalFlowLayout(String location) {
		this.location = location;
		if (location.equals(BorderLayout.EAST)
				|| location.equals(BorderLayout.WEST)) {
			// We're fine
		} else {
			throw new IllegalArgumentException(
					"Location must be one of: BorderLayout.EAST or BorderLayout.WEST");
		}
	}

	public void addLayoutComponent(String id, Component obj) {
		components.add(obj);
	}

	public void removeLayoutComponent(Component obj) {
		components.remove(obj);
	}

	public Dimension preferredLayoutSize(Container obj) {
		return obj.getPreferredSize();
	}

	public Dimension minimumLayoutSize(Container obj) {
		return obj.getMinimumSize();
	}

	public void layoutContainer(Container obj) {
		int bar_height = obj.getHeight();
		int columns = determineRequiredColumns(bar_height);
		int column = 1;
		int used_space = 0;
		Iterator<Component> it = components.iterator();
		while (it.hasNext()) {
			Component current = it.next();
			int y_pos = used_space;
			used_space += current.getPreferredSize().height;
			if (used_space > bar_height) {
				column++;
				used_space = current.getPreferredSize().height;
				y_pos = 0;
			}
			if (columns == 1) {
				// We're only going to need one column
				current.setBounds(0, y_pos,
						current.getPreferredSize().width + 2,
						current.getPreferredSize().height);
				current.setVisible(true);
			} else {
				if (location.equals(BorderLayout.EAST)) {
					int multiplier = column - 1;
					current.setBounds(
							(current.getPreferredSize().width * multiplier),
							y_pos, current.getPreferredSize().width + 2,
							current.getPreferredSize().height);
					current.setVisible(true);
				} else {
					int x_pos = ((columns - 1) * current.getPreferredSize().width)
							- ((column - 1) * current.getPreferredSize().width);
					// System.err.println("adding: " + x_pos + ", " + y_pos +
					// " , cols: " + columns + ", col: " + column);
					current.setBounds(x_pos, y_pos,
							current.getPreferredSize().width + 2,
							current.getPreferredSize().height);
					current.setVisible(true);
				}
			}
		}
	}

	public int determineRequiredColumns(int available_height) {
		int required = 0;
		int req_cols = 1;
		Iterator<Component> it = components.iterator();
		while (it.hasNext()) {
			Component c = it.next();
			required += c.getHeight();
			if (required > available_height) {
				req_cols++;
				required = c.getHeight();
			}
		}
		return req_cols;
	}

}
