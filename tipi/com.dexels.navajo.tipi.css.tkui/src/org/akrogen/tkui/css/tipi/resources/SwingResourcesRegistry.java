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
package org.akrogen.tkui.css.tipi.resources;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;

import org.akrogen.tkui.css.core.resources.AbstractResourcesRegistry;
import org.akrogen.tkui.css.core.resources.IResourcesRegistry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Swing Resources Registry to cache Swing Resource like Color, Cursor and Font
 * and dispose it if need.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class SwingResourcesRegistry extends AbstractResourcesRegistry {

	private static Log logger = LogFactory.getLog(SwingResourcesRegistry.class);

	/**
	 * SwingResourcesRegistry Singleton
	 */
	public static IResourcesRegistry INSTANCE = new SwingResourcesRegistry();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.resources.AbstractResourcesRegistry#registerResource(java.lang.Object,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void registerResource(Object type, Object key, Object resource) {
		if (resource == null)
			return;
		if (logger.isDebugEnabled()) {
			if (resource instanceof Color) {
				logger.debug("Cache Swing Color key=" + key);
			} else if (resource instanceof Cursor) {
				logger.debug("Cache Swing Cursor key=" + key);
			} else if (resource instanceof Font) {
				logger.debug("Cache Swing Font key=" + key);
			} else if (resource instanceof Image) {
				logger.debug("Cache Swing Image key=" + key);
			} else
				logger.debug("Cache Resource key=" + key);
		}
		super.registerResource(type, key, resource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.resources.AbstractResourcesRegistry#disposeResource(java.lang.Object,
	 *      java.lang.String, java.lang.Object)
	 */
	public void disposeResource(Object type, String key, Object resource) {
		if (logger.isDebugEnabled()) {
			if (resource instanceof Color) {
				logger.debug("Dispose Swing Color key=" + key);
			} else if (resource instanceof Cursor) {
				logger.debug("Dispose Swing Cursor key=" + key);
			} else if (resource instanceof Font) {
				logger.debug("Dispose Swing Font key=" + key);
			} else
				logger.debug("Dispose Resource key=" + key);
		}
	}
}
