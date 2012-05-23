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

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.akrogen.core.resources.IResourcesLocatorManager;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

public class CSSSwingImageHelper {

	public static Image getImage(CSSValue cssValue,
			IResourcesLocatorManager manager) throws Exception {
		Image image = null;
		if (cssValue.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			CSSPrimitiveValue pv = (CSSPrimitiveValue) cssValue;
			switch (pv.getPrimitiveType()) {
			case CSSPrimitiveValue.CSS_URI:
				String path = pv.getStringValue();
				image = loadImageFromURL(path, manager);
				break;
			}
		}
		return image;
	}

	public static Image loadImageFromURL(String path,
			IResourcesLocatorManager manager) throws Exception {
		InputStream in = null;
		try {
			in = manager.getInputStream(path);
			BufferedImage bufferedImage = ImageIO.read(in);
			return bufferedImage;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}

}
