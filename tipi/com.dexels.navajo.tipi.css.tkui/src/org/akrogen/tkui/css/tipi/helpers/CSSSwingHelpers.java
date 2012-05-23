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
package org.akrogen.tkui.css.tipi.helpers;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.akrogen.tkui.css.core.dom.properties.CSSBorderProperties;
import org.akrogen.tkui.css.core.resources.IResourcesRegistry;

/**
 * Swing Helper to transform CSS w3c object (org.w3c.dom.css.RGBColor....) into
 * Swing object (java.awt.Color...).
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSSwingHelpers {

	/*--------------- Swing Color Helper -----------------*/

	/*--------------- Swing Font Helper -----------------*/

	

	/*--------------- Swing Cursor Helper -----------------*/

	/*--------------- Swing Image Helper -----------------*/


	/*--------------- Swing Border Helper -----------------*/

	/**
	 * Return Swing Border.
	 * 
	 * @param border
	 * @return
	 */
	public static Border getBorder(CSSBorderProperties border,
			IResourcesRegistry resourcesRegistry) {
		if (border == null)
			return null;
		Color color = (border.getColor() != null ? CSSSwingColorHelper
				.getSwingColor(border.getColor()) : Color.BLACK);
		int width = border.getWidth();
		if (width == 0)
			return null;
		return BorderFactory.createLineBorder(color, width);
	}
}
