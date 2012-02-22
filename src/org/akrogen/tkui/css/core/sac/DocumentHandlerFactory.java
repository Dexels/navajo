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
package org.akrogen.tkui.css.core.sac;

import org.akrogen.tkui.css.core.impl.sac.DocumentHandlerFactoryImpl;

/**
 * Factory to get instance of {@link DocumentHandlerFactory}.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class DocumentHandlerFactory implements IDocumentHandlerFactory {

	/**
	 * Return instance of {@link DocumentHandlerFactory}.
	 * 
	 * @return
	 */
	public static DocumentHandlerFactory newInstance() {
		return new DocumentHandlerFactoryImpl();
	}
}
