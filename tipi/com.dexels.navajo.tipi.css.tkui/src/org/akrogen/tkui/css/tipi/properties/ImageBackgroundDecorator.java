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

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class ImageBackgroundDecorator extends AbstractComponentDecorator {

	private Image image;

	public ImageBackgroundDecorator(JComponent c, Image image) {
		super(c, -1);
		this.image = image;
	}

	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

}
