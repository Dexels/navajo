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
package org.akrogen.tkui.css.tipi.properties.css2.lazy.background;

import java.awt.Image;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.tipi.properties.AbstractCSSPropertySwingHandler;
import org.akrogen.tkui.css.tipi.properties.ImageBackgroundDecorator;
import org.w3c.dom.css.CSSValue;

import com.dexels.navajo.tipi.TipiComponent;

public class CSSPropertyBackgroundImageHandler extends
		AbstractCSSPropertySwingHandler {

	public void applyCSSProperty(TipiComponent component, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		Image image = (Image) engine.convert(value, Image.class, null);
		if (image != null) {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setIcon(new ImageIcon(image));
			} else
				new ImageBackgroundDecorator((JComponent) component, image);
		} else {
			if (component instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) component;
				button.setIcon(null);
			}
		}
	}

	public String retrieveCSSProperty(TipiComponent component, String property,
			CSSEngine engine) throws Exception {
		return "none";
	}
}
