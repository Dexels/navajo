/*******************************************************************************
 * Copyright (c) 2008, Original authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo ZERR <angelo.zerr@gmail.com>
 *******************************************************************************/
package org.akrogen.tkui.css.tipi.properties;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import org.akrogen.tkui.css.core.dom.properties.Gradient;

public class GradientBackgroundDecorator extends AbstractComponentDecorator {

	private Gradient grad;
	private static Map handlers = new HashMap();

	public GradientBackgroundDecorator(JComponent c, Gradient grad) {
		super(c, -1);
		this.grad = grad;
	}

	public static void handle(JComponent component, Gradient grad) {
		GradientBackgroundDecorator handler = (GradientBackgroundDecorator) handlers
				.get(component);
		if (handler == null) {
			handler = new GradientBackgroundDecorator(component, grad);
			handlers.put(component, handler);
		}
	}

	public void paint(Graphics graphics) {
		// TODO : manage Gradient better like SWT.
		Graphics2D g = (Graphics2D) graphics;
		JComponent jc = getComponent();
		int h = jc.getHeight() / 2;

		Color c1 = jc.getBackground().darker();
		Color c2 = jc.getBackground();

		List rgbs = grad.getRGBs();
		if (rgbs.size() > 0) {
			c1 = (Color) rgbs.get(0);
			if (rgbs.size() > 1) {
				c2 = (Color) rgbs.get(1);
			}
		}

		GradientPaint gp = new GradientPaint(0, h, c1, jc.getWidth() / 2, h, c2);
		g.setPaint(gp);
		Insets insets = jc.getInsets();
		g.fillRect(insets.left, insets.top, jc.getWidth() - insets.right, jc
				.getHeight()
				- insets.bottom);
	}
}
