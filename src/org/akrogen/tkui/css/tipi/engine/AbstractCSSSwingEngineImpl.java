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

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.impl.engine.CSSEngineImpl;
import org.akrogen.tkui.css.tipi.dom.TipiElementProvider;
import org.akrogen.tkui.css.tipi.properties.converters.CSSValueSwingColorConverterImpl;
import org.akrogen.tkui.css.tipi.properties.converters.CSSValueSwingCursorConverterImpl;
import org.akrogen.tkui.css.tipi.properties.converters.CSSValueSwingFontConverterImpl;
import org.akrogen.tkui.css.tipi.properties.converters.CSSValueSwingGradientConverterImpl;
import org.akrogen.tkui.css.tipi.properties.converters.CSSValueSwingImageConverterImpl;
import org.akrogen.tkui.css.tipi.resources.SwingResourcesRegistry;
import org.akrogen.tkui.css.tipi.selectors.DynamicPseudoClassesSwingFocusHandler;
import org.akrogen.tkui.css.tipi.selectors.DynamicPseudoClassesSwingHoverHandler;

/**
 * CSS Swing Engine implementation which configure CSSEngineImpl to apply styles
 * to Swing containers.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractCSSSwingEngineImpl extends CSSEngineImpl {
	/**
	 * Store the CSS Engine into Thread Locale.
	 */
	private static ThreadLocal threadLocalEngine = null;

	public AbstractCSSSwingEngineImpl() {
		this(false);
	}

	public AbstractCSSSwingEngineImpl(boolean lazyApplyingStyles) {
		// Register Swing Element Provider to retrieve
		// w3c Element SwingElement coming from Swing container.
		super.setElementProvider(TipiElementProvider.INSTANCE);

		/** Initialize CSS Property Handlers * */

		// initialize CSS Property Handlers
		initializeCSSPropertyHandlers();

		/** Initialize Dynamic pseudo classes * */

		// Register Swing Focus Handler
		super.registerDynamicPseudoClassHandler("focus",
				DynamicPseudoClassesSwingFocusHandler.INSTANCE);

		// Register Swing Hover Handler
		super.registerDynamicPseudoClassHandler("hover",
				DynamicPseudoClassesSwingHoverHandler.INSTANCE);

		/** Initialize Swing CSSValue converter * */

		// Register Swing Color CSSValue Converter
		super
				.registerCSSValueConverter(CSSValueSwingColorConverterImpl.INSTANCE);
		// Register Swing Gradient CSSValue Converter
		super
				.registerCSSValueConverter(CSSValueSwingGradientConverterImpl.INSTANCE);
		// Register Swing Cursor CSSValue Converter
		super
				.registerCSSValueConverter(CSSValueSwingCursorConverterImpl.INSTANCE);
		// Register Swing Font CSSValue Converter
		super.registerCSSValueConverter(CSSValueSwingFontConverterImpl.INSTANCE);
		// Register Swing Image CSSValue Converter
		super.registerCSSValueConverter(CSSValueSwingImageConverterImpl.INSTANCE);

		// Swing Resources registry used to cache Resources
		super.setResourcesRegistry(SwingResourcesRegistry.INSTANCE);
		
		if (lazyApplyingStyles) {
			// Store the CSS engine instance into Thread Local
			threadLocalEngine = new ThreadLocal();
			threadLocalEngine.set(this);
			// Start CSS Swing Watcher
			CSSSwingApplyStylesListener.start();
		}
	}

	public static CSSEngine getCurrentCSSEngine() {
		if (threadLocalEngine != null)
			return (CSSEngine) threadLocalEngine.get();
		return null;
	}

	/**
	 * Initialize CSS Properties Handlers.
	 * 
	 */
	protected abstract void initializeCSSPropertyHandlers();

}
