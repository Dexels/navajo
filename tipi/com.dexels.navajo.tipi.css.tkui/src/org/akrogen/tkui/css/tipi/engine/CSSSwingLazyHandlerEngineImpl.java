/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
package org.akrogen.tkui.css.tipi.engine;


public class CSSSwingLazyHandlerEngineImpl extends AbstractCSSSwingEngineImpl {

	public CSSSwingLazyHandlerEngineImpl() {
		super();
	}

	public CSSSwingLazyHandlerEngineImpl(boolean lazyApplyingStyles) {
		super(lazyApplyingStyles);
	}

	protected void initializeCSSPropertyHandlers() {
		super
				.registerPackage("org.akrogen.tkui.css.tipi.properties.css2.lazy.classification");
		super
				.registerPackage("org.akrogen.tkui.css.tipi.properties.css2.lazy.border");
		super
				.registerPackage("org.akrogen.tkui.css.tipi.properties.css2.lazy.font");
		super
				.registerPackage("org.akrogen.tkui.css.tipi.properties.css2.lazy.background");
		super
				.registerPackage("org.akrogen.tkui.css.tipi.properties.css2.lazy.text");

	}

}
