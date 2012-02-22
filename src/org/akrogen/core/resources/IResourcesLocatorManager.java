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
package org.akrogen.core.resources;

/**
 * Resources locator manage to register/unregister {@link IResourceLocator}.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public interface IResourcesLocatorManager extends IResourceLocator {

	/**
	 * Register <code>resourceLocator</code>.
	 * 
	 * @param resourceLocator
	 */
	public void registerResourceLocator(IResourceLocator resourceLocator);

	/**
	 * Unregister <code>resourceLocator</code>.
	 * 
	 * @param resourceLocator
	 */
	public void unregisterResourceLocator(IResourceLocator resourceLocator);

}
