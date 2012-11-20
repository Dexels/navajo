package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

public class TipiSwingVerticalFlowLayout extends FlowLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8713494348779974979L;
	int wrapWidth = 0;

	public TipiSwingVerticalFlowLayout(int i, int j, int k) {
		super(i, j, k);
	}

	public int getWrapWidth(Container container) {
		if (wrapWidth != 0) {
			return wrapWidth;
		}
		Component parent = container.getParent();
		if (parent != null) {
			return parent.getSize().width;
		}
		return 0;
	}

	public Dimension preferredLayoutSize(Container container) {
		Dimension dimension2;
		synchronized (container.getTreeLock()) {
			int hgap = getHgap();
			int vgap = getHgap();
			int wrapWidth = getWrapWidth(container);
			Dimension dimension = new Dimension(0, 0);
			Dimension line = new Dimension(0, 0);
			int i = container.getComponentCount();
			boolean firstInLine = true;
			for (int j = 0; j < i; j++) {
				Component component = container.getComponent(j);
				if (component.isVisible()) {
					Dimension compSize = component.getPreferredSize();
					// logger.info(j+" "+compSize+" "+firstInLine+" "+wrapWidth);
					Dimension oldLine = new Dimension(line);
					boolean oldFirstInLine = firstInLine;
					line.height = Math.max(line.height, compSize.height);
					if (firstInLine) {
						firstInLine = false;
					} else {
						line.width += hgap;
					}
					line.width += compSize.width;
					if (line.width > wrapWidth && !oldFirstInLine) {
						dimension.height += vgap + oldLine.height;
						dimension.width += Math.max(dimension.width,
								oldLine.width);
						line = new Dimension(0, 0);
						firstInLine = true;
						j--;
					}
				}
			}
			if (!firstInLine) {
				dimension.height += vgap + line.height;
				dimension.width += Math.max(dimension.width, line.width);
			}

			Insets insets = container.getInsets();
			dimension.width += insets.left + insets.right + hgap * 2;
			dimension.height += insets.top + insets.bottom + vgap * 2;
			dimension2 = dimension;
		}
		return dimension2;
	}

	/*
	 * public Dimension maximumLayoutSize(Container container) { return
	 * preferredLayoutSize(container); }
	 */
	public Dimension oldpreferredLayoutSize(Container container) {
		Dimension dimension2;
		int hgap = getHgap();
		int vgap = getHgap();
		synchronized (container.getTreeLock()) {
			Dimension dimension = new Dimension(0, 0);
			int i = container.getComponentCount();
			boolean flag = true;
			for (int j = 0; j < i; j++) {
				Component component = container.getComponent(j);
				if (component.isVisible()) {
					Dimension dimension1 = component.getPreferredSize();
					dimension.height = Math.max(dimension.height,
							dimension1.height);
					if (flag) {
						flag = false;
					} else {
						dimension.width += hgap;
					}
					dimension.width += dimension1.width;
				}
			}

			Insets insets = container.getInsets();
			dimension.width += insets.left + insets.right + hgap * 2;
			dimension.height += insets.top + insets.bottom + vgap * 2;
			dimension2 = dimension;
		}
		return dimension2;
	}
}