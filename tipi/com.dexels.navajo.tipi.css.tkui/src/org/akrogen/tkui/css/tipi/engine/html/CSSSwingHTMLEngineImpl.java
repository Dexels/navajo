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
package org.akrogen.tkui.css.tipi.engine.html;

import org.akrogen.tkui.css.tipi.dom.html.TipiHTMLElementProvider;
import org.akrogen.tkui.css.tipi.engine.CSSTipiEngineImpl;

/**
 * CSS Swing Engine implementation which configure CSSEngineImpl to apply styles
 * to Swing containers.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSSwingHTMLEngineImpl extends CSSTipiEngineImpl {

	public CSSSwingHTMLEngineImpl() {
		this(false);
	}

	public CSSSwingHTMLEngineImpl(boolean lazyApplyingStyles) {
		super(lazyApplyingStyles);
		// Register Swing Element Provider to retrieve
		// w3c Element SwingElement coming from Swing container.
		super.setElementProvider(TipiHTMLElementProvider.INSTANCE);

	}
}
