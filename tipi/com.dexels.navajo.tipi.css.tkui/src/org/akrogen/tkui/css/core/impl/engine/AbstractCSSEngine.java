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
package org.akrogen.tkui.css.core.impl.engine;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.akrogen.core.impl.resources.ResourcesLocatorManager;
import org.akrogen.core.resources.IResourcesLocatorManager;
import org.akrogen.tkui.css.core.dom.CSSStylableElement;
import org.akrogen.tkui.css.core.dom.ExtendedDocumentCSS;
import org.akrogen.tkui.css.core.dom.IElementProvider;
import org.akrogen.tkui.css.core.dom.parsers.CSSParser;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyCompositeHandler;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandler2Delegate;
import org.akrogen.tkui.css.core.dom.properties.ICSSPropertyHandlerProvider;
import org.akrogen.tkui.css.core.dom.properties.converters.ICSSValueConverter;
import org.akrogen.tkui.css.core.dom.selectors.IDynamicPseudoClassesHandler;
import org.akrogen.tkui.css.core.engine.CSSElementContext;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.engine.CSSErrorHandler;
import org.akrogen.tkui.css.core.exceptions.UnsupportedPropertyException;
import org.akrogen.tkui.css.core.impl.dom.DocumentCSSImpl;
import org.akrogen.tkui.css.core.impl.dom.ViewCSSImpl;
import org.akrogen.tkui.css.core.impl.sac.ExtendedSelector;
import org.akrogen.tkui.css.core.resources.CSSResourcesHelpers;
import org.akrogen.tkui.css.core.resources.IResourcesRegistry;
import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionalSelector;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Selector;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.DocumentCSS;
import org.w3c.dom.css.ViewCSS;
import org.w3c.dom.stylesheets.StyleSheet;

/**
 * Abstract CSS Engine manage style sheet parsing and store the
 * {@link CSSStyleSheet} into {@link DocumentCSS}.
 * 
 * To apply styles, call the {@link #applyStyles(Object, boolean, boolean)}
 * method. This method check if {@link ICSSPropertyHandler} is registered for
 * apply the CSS property.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public abstract class AbstractCSSEngine implements CSSEngine {

	/**
	 * Default {@link IResourcesLocatorManager} used to get InputStream, Reader
	 * resource like Image.
	 */
	private final static IResourcesLocatorManager defaultResourcesLocatorManager = ResourcesLocatorManager.INSTANCE;

	/**
	 * w3c {@link DocumentCSS}.
	 */
	private ExtendedDocumentCSS documentCSS;

	/**
	 * w3c {@link ViewCSS}.
	 */
	private ViewCSS viewCSS;

	/**
	 * {@link IElementProvider} used to retrieve w3c Element linked to the
	 * widget.
	 */
	private IElementProvider elementProvider;

	protected boolean computeDefaultStyle = false;

	private Map elementsWithDynamicPseudoClasses = null;

	private Map elementsContext = null;

	/**
	 * CSS Error Handler to intercept error while parsing, applying styles.
	 */
	private CSSErrorHandler errorHandler;

	private IResourcesLocatorManager resourcesLocatorManager;

	private Map dynamicPseudoClassesHandler = new HashMap();

	private IResourcesRegistry resourcesRegistry;

	/**
	 * ICSSPropertyHandlerProvider
	 */
	private List propertyHandlerProviders = new ArrayList();

	private Map currentCSSPropertiesApplyed = new HashMap();

	private Map valueConverters = null;

	public AbstractCSSEngine() {
		this(new DocumentCSSImpl());
	}

	public AbstractCSSEngine(ExtendedDocumentCSS documentCSS) {
		this.documentCSS = documentCSS;
		this.viewCSS = new ViewCSSImpl(documentCSS);
	}

	/*--------------- Parse style sheet -----------------*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseStyleSheet(java.io.Reader)
	 */
	public StyleSheet parseStyleSheet(Reader reader) throws IOException {
		InputSource source = new InputSource();
		source.setCharacterStream(reader);
		return parseStyleSheet(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseStyleSheet(java.io.InputStream)
	 */
	public StyleSheet parseStyleSheet(InputStream stream) throws IOException {
		InputSource source = new InputSource();
		source.setByteStream(stream);
		return parseStyleSheet(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseStyleSheet(org.w3c.css.sac.InputSource)
	 */
	public StyleSheet parseStyleSheet(InputSource source) throws IOException {
		// Check that CharacterStream or ByteStream is not null
		checkInputSource(source);
		CSSParser parser = makeCSSParser();
		CSSStyleSheet styleSheet = parser.parseStyleSheet(source);
		if (documentCSS instanceof ExtendedDocumentCSS) {
			((ExtendedDocumentCSS) documentCSS).addStyleSheet(styleSheet);
		}
		return styleSheet;
	}

	/**
	 * Return true if <code>source</code> is valid and false otherwise.
	 * 
	 * @param source
	 * @throws IOException
	 */
	private void checkInputSource(InputSource source) throws IOException {
		Reader reader = source.getCharacterStream();
		InputStream stream = source.getByteStream();
		if (reader == null && stream == null)
			throw new IOException(
					"CharacterStream or ByteStream cannot be null for the InputSource.");
	}

	/*--------------- Parse style declaration -----------------*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseStyleDeclaration(java.lang.String)
	 */
	public CSSStyleDeclaration parseStyleDeclaration(String style)
			throws IOException {
		Reader reader = new StringReader(style);
		return parseStyleDeclaration(reader);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseStyleDeclaration(java.io.Reader)
	 */
	public CSSStyleDeclaration parseStyleDeclaration(Reader reader)
			throws IOException {
		InputSource source = new InputSource();
		source.setCharacterStream(reader);
		return parseStyleDeclaration(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseStyleDeclaration(java.io.InputStream)
	 */
	public CSSStyleDeclaration parseStyleDeclaration(InputStream stream)
			throws IOException {
		InputSource source = new InputSource();
		source.setByteStream(stream);
		return parseStyleDeclaration(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseStyleDeclaration(org.w3c.css.sac.InputSource)
	 */
	public CSSStyleDeclaration parseStyleDeclaration(InputSource source)
			throws IOException {
		checkInputSource(source);
		CSSParser parser = makeCSSParser();
		CSSStyleDeclaration styleDeclaration = parser
				.parseStyleDeclaration(source);
		return styleDeclaration;
	}

	/*--------------- Parse CSS Property Value-----------------*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parsePropertyValue(java.io.Reader)
	 */
	public CSSValue parsePropertyValue(Reader reader) throws IOException {
		InputSource source = new InputSource();
		source.setCharacterStream(reader);
		return parsePropertyValue(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parsePropertyValue(java.io.InputStream)
	 */
	public CSSValue parsePropertyValue(InputStream stream) throws IOException {
		InputSource source = new InputSource();
		source.setByteStream(stream);
		return parsePropertyValue(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parsePropertyValue(java.lang.String)
	 */
	public CSSValue parsePropertyValue(String value) throws IOException {
		Reader reader = new StringReader(value);
		return parsePropertyValue(reader);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parsePropertyValue(org.w3c.css.sac.InputSource)
	 */
	public CSSValue parsePropertyValue(InputSource source) throws IOException {
		checkInputSource(source);
		CSSParser parser = makeCSSParser();
		return parser.parsePropertyValue(source);
	}

	/*--------------- Apply styles -----------------*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.engine.CSSEngine#applyStyles(java.lang.Object,
	 *      boolean)
	 */
	public void applyStyles(Object element, boolean applyStylesToChildNodes) {
		applyStyles(element, applyStylesToChildNodes, computeDefaultStyle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.engine.CSSEngine#applyStyles(java.lang.Object,
	 *      boolean, boolean)
	 */
	public void applyStyles(Object element, boolean applyStylesToChildNodes,
			boolean computeDefaultStyle) {
		Element elt = getElement(element);
		if (elt != null) {
			/*
			 * Compute new Style to apply.
			 */
			CSSStyleDeclaration style = viewCSS.getComputedStyle(elt, null);
			if (computeDefaultStyle) {
				if (applyStylesToChildNodes)
					this.computeDefaultStyle = computeDefaultStyle;
				/*
				 * Apply default style.
				 */
				applyDefaultStyleDeclaration(element, false, style, null);
			}
			/*
			 * Initialize dynamic pseudo classes if needed
			 */
			initializeDynamicPseudoClassesIfNeeded(element);

			/*
			 * Manage static pseudo instances
			 */
			String[] pseudoInstances = getStaticPseudoInstances(elt);
			if (pseudoInstances != null) {
				// there are static pseudo instances definied, loop for it and
				// apply styles for each pseudo instance.
				for (int i = 0; i < pseudoInstances.length; i++) {
					String pseudoInstance = pseudoInstances[i];
					CSSStyleDeclaration styleWithPseudoInstance = viewCSS
							.getComputedStyle(elt, pseudoInstance);
					if (computeDefaultStyle) {
						/*
						 * Apply default style for the current pseudo instance.
						 */
						applyDefaultStyleDeclaration(element, false,
								styleWithPseudoInstance, pseudoInstance);
					}
					if (styleWithPseudoInstance != null) {
						applyStyleDeclaration(element, styleWithPseudoInstance,
								pseudoInstance);
					}
				}
			}

			if (style != null) {
				applyStyleDeclaration(element, style, null);
			}
			try {
				// Apply inline style
				applyInlineStyle(elt, false);
			} catch (Exception e) {
				handleExceptions(e);
			}

			if (applyStylesToChildNodes) {
				/*
				 * Style all children recursive.
				 */
				NodeList nodes = elt.getChildNodes();
				if (nodes != null) {
					for (int k = 0; k < nodes.getLength(); k++) {
						applyStyles(nodes.item(k), applyStylesToChildNodes);
					}
					onStylesAppliedToChildNodes(elt, nodes);
				}
			}
		}

	}

	protected String[] getStaticPseudoInstances(Element element) {
		if (element instanceof CSSStylableElement) {
			CSSStylableElement stylableElement = (CSSStylableElement) element;
			return stylableElement.getStaticPseudoInstances();
		}
		return null;
	}

	/**
	 * Callback method called when styles applied of <code>nodes</code>
	 * children of the <code>element</code>.
	 * 
	 * @param element
	 * @param nodes
	 */
	protected void onStylesAppliedToChildNodes(Element element, NodeList nodes) {
		if (element instanceof CSSStylableElement)
			((CSSStylableElement) element).onStylesApplied(nodes);
	}

	/*--------------- Apply style declaration -----------------*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#applyStyleDeclaration(java.lang.Object,
	 *      org.w3c.dom.css.CSSStyleDeclaration, java.lang.String)
	 */
	public void applyStyleDeclaration(Object element,
			CSSStyleDeclaration style, String pseudo) {
		// Apply style
		currentCSSPropertiesApplyed.clear();
		List handlers2 = null;
		for (int i = 0; i < style.getLength(); i++) {
			String property = style.item(i);
			CSSValue value = style.getPropertyCSSValue(property);
			try {
				ICSSPropertyHandler handler = this.applyCSSProperty(element,
						property, value, pseudo);
				ICSSPropertyHandler2 propertyHandler2 = null;
				if (handler instanceof ICSSPropertyHandler2) {
					propertyHandler2 = (ICSSPropertyHandler2) handler;
				} else {
					if (handler instanceof ICSSPropertyHandler2Delegate) {
						propertyHandler2 = ((ICSSPropertyHandler2Delegate) handler)
								.getCSSPropertyHandler2();
					}
				}
				if (propertyHandler2 != null) {
					if (handlers2 == null)
						handlers2 = new ArrayList();
					if (!handlers2.contains(propertyHandler2))
						handlers2.add(propertyHandler2);
				}
			} catch (Exception e) {
				handleExceptions(e);
			}
		}
		if (handlers2 != null) {
			for (Iterator iterator = handlers2.iterator(); iterator.hasNext();) {
				ICSSPropertyHandler2 handler2 = (ICSSPropertyHandler2) iterator
						.next();
				try {
					handler2.onAllCSSPropertiesApplyed(element, this);
				} catch (Exception e) {
					handleExceptions(e);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseAndApplyStyleDeclaration(java.io.Reader,
	 *      java.lang.Object)
	 */
	public CSSStyleDeclaration parseAndApplyStyleDeclaration(Object node,
			Reader reader) throws IOException {
		CSSStyleDeclaration style = parseStyleDeclaration(reader);
		this.applyStyleDeclaration(node, style, null);
		return style;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseAndApplyStyleDeclaration(java.io.InputStream,
	 *      java.lang.Object)
	 */
	public CSSStyleDeclaration parseAndApplyStyleDeclaration(Object node,
			InputStream stream) throws IOException {
		CSSStyleDeclaration style = parseStyleDeclaration(stream);
		this.applyStyleDeclaration(node, style, null);
		return style;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseAndApplyStyleDeclaration(org.w3c.css.sac.InputSource,
	 *      java.lang.Object)
	 */
	public CSSStyleDeclaration parseAndApplyStyleDeclaration(Object node,
			InputSource source) throws IOException {
		CSSStyleDeclaration style = parseStyleDeclaration(source);
		this.applyStyleDeclaration(node, style, null);
		return style;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#parseAndApplyStyleDeclaration(java.lang.Object,
	 *      java.lang.String)
	 */
	public CSSStyleDeclaration parseAndApplyStyleDeclaration(Object node,
			String style) throws IOException {
		CSSStyleDeclaration styleDeclaration = parseStyleDeclaration(style);
		this.applyStyleDeclaration(node, styleDeclaration, null);
		return styleDeclaration;
	}

	/*--------------- Apply inline style -----------------*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.core.css.engine.CSSEngine#applyInlineStyle(java.lang.Object,
	 *      boolean)
	 */
	public void applyInlineStyle(Object node, boolean applyStylesToChildNodes)
			throws IOException {
		Element elt = getElement(node);
		if (elt != null) {
			if (elt instanceof CSSStylableElement) {
				CSSStylableElement stylableElement = (CSSStylableElement) elt;
				String style = stylableElement.getCSSStyle();
				if (style != null && style.length() > 0) {
					parseAndApplyStyleDeclaration(stylableElement
							.getNativeWidget(), style);
				}
			}
			if (applyStylesToChildNodes) {
				/*
				 * Style all children recursive.
				 */
				NodeList nodes = elt.getChildNodes();
				if (nodes != null) {
					for (int k = 0; k < nodes.getLength(); k++) {
						applyInlineStyle(nodes.item(k), applyStylesToChildNodes);
					}
				}
			}
		}
	}

	/*--------------- Initial Style -----------------*/

	public CSSStyleDeclaration getDefaultStyleDeclaration(Object element,
			String pseudoE) {
		return getDefaultStyleDeclaration(element, null, pseudoE);
	}

	public CSSStyleDeclaration getDefaultStyleDeclaration(Object widget,
			CSSStyleDeclaration newStyle, String pseudoE) {
		CSSStyleDeclaration style = null;
		for (Iterator iterator = propertyHandlerProviders.iterator(); iterator
				.hasNext();) {
			ICSSPropertyHandlerProvider provider = (ICSSPropertyHandlerProvider) iterator
					.next();
			try {
				style = provider.getDefaultCSSStyleDeclaration(this, widget,
						newStyle, pseudoE);
			} catch (Exception e) {
				handleExceptions(e);
			}
		}
		return style;
	}

	public void applyDefaultStyleDeclaration(Object element,
			boolean applyStylesToChildNodes) {
		applyDefaultStyleDeclaration(element, applyStylesToChildNodes, null,
				null);
	}

	public void applyDefaultStyleDeclaration(Object element,
			boolean applyStylesToChildNodes, CSSStyleDeclaration newStyle,
			String pseudoE) {
		// Initial styles must be computed or applied
		Element elt = getElement(element);
		if (elt != null) {
			if (elt instanceof CSSStylableElement) {
				CSSStylableElement stylableElement = (CSSStylableElement) elt;
				CSSStyleDeclaration oldDefaultStyleDeclaration = stylableElement
						.getDefaultStyleDeclaration(pseudoE);
				// CSSStyleDeclaration defaultStyleDeclaration =
				// computeDefaultStyleDeclaration(
				// stylableElement, newStyle);
				CSSStyleDeclaration defaultStyleDeclaration = getDefaultStyleDeclaration(
						element, newStyle, pseudoE);
				if (oldDefaultStyleDeclaration != null) {
					// Second apply styles, apply the initial style
					// before apply the new style
					applyStyleDeclaration(element, defaultStyleDeclaration,
							pseudoE);
				}
			}
			if (applyStylesToChildNodes) {
				/*
				 * Style all children recursive.
				 */
				NodeList nodes = elt.getChildNodes();
				if (nodes != null) {
					for (int k = 0; k < nodes.getLength(); k++) {
						applyDefaultStyleDeclaration(nodes.item(k),
								applyStylesToChildNodes);
					}
					onStylesAppliedToChildNodes(elt, nodes);
				}
			}
		}
	}

	/**
	 * Delegates the handle method.
	 * 
	 * @param control
	 * @param property
	 * @param value
	 */
	public ICSSPropertyHandler applyCSSProperty(Object widget, String property,
			CSSValue value, String pseudo) throws Exception {
		if (currentCSSPropertiesApplyed.containsKey(property))
			// CSS Property was already applied, ignore it.
			return null;
		Collection handlers = getCSSPropertyHandlers(property);
		if (handlers == null) {
			throw new UnsupportedPropertyException(property);
		}
		try {
			for (Iterator iterator = handlers.iterator(); iterator.hasNext();) {
				ICSSPropertyHandler handler = (ICSSPropertyHandler) iterator
						.next();
				boolean result = handler.applyCSSProperty(widget, property,
						value, pseudo, this);
				if (result) {
					// Add CSS Property to flag that this CSS Property was
					// applied.
					currentCSSPropertiesApplyed.put(property, property);
					return handler;
				}
			}

		} catch (Exception e) {
			handleExceptions(e);
		}
		return null;
	}

	public String retrieveCSSProperty(Object widget, String property) {
		try {
			Collection handlers = getCSSPropertyHandlers(property);
			if (handlers == null) {
				return null;
			}
			for (Iterator iterator = handlers.iterator(); iterator.hasNext();) {
				ICSSPropertyHandler handler = (ICSSPropertyHandler) iterator
						.next();
				return handler.retrieveCSSProperty(widget, property, this);
			}
		} catch (Exception e) {
			handleExceptions(e);
		}
		return null;
	}

	public String[] getCSSCompositePropertiesNames(String property) {
		try {
			Collection handlers = getCSSPropertyHandlers(property);
			if (handlers == null) {
				return null;
			}
			for (Iterator iterator = handlers.iterator(); iterator.hasNext();) {
				ICSSPropertyHandler handler = (ICSSPropertyHandler) iterator
						.next();
				if (handler instanceof ICSSPropertyCompositeHandler) {
					ICSSPropertyCompositeHandler compositeHandler = (ICSSPropertyCompositeHandler) handler;
					if (compositeHandler.isCSSPropertyComposite(property))
						return compositeHandler.getCSSPropertiesNames(property);
				}
			}
		} catch (Exception e) {
			handleExceptions(e);
		}
		return null;
	}

	protected Collection getCSSPropertyHandlers(String property)
			throws Exception {
		Collection handlers = null;
		for (Iterator iterator = propertyHandlerProviders.iterator(); iterator
				.hasNext();) {
			ICSSPropertyHandlerProvider provider = (ICSSPropertyHandlerProvider) iterator
					.next();
			Collection h = provider.getCSSPropertyHandlers(property);
			if (handlers == null) {
				handlers = h;
			} else {
				handlers = new ArrayList(handlers);
				handlers.addAll(h);
			}
		}
		return handlers;
	}

	/*--------------- Dynamic pseudo classes -----------------*/

	public void registerDynamicPseudoClassHandler(String pseudo,
			IDynamicPseudoClassesHandler dynamicPseudoClassHandler) {
		dynamicPseudoClassesHandler.put(pseudo, dynamicPseudoClassHandler);
	}

	protected void initializeDynamicPseudoClassesIfNeeded(Object node) {
		if (dynamicPseudoClassesHandler == null
				|| dynamicPseudoClassesHandler.values().size() < 1)
			return;
		Element element = getElement(node);
		if (element == null)
			return;
		if (isElementsWithDynamicPseudoClassesInitialized(element))
			return;
		// Create
		// Loop for pseudo class condition
		List list = documentCSS
				.queryConditionSelector(Condition.SAC_PSEUDO_CLASS_CONDITION);
		if (list == null)
			return;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Selector selector = (Selector) iterator.next();
			ConditionalSelector conditionalSelector = (ConditionalSelector) selector;
			String value = ((AttributeCondition) conditionalSelector
					.getCondition()).getValue();

			if (((ExtendedSelector) conditionalSelector.getSimpleSelector())
					.match(element, value)) {
				IDynamicPseudoClassesHandler handler = (IDynamicPseudoClassesHandler) dynamicPseudoClassesHandler
						.get(value);
				if (handler != null) {
					handler.intialize(element, this);
					addElementsWithDynamicPseudoClasses(element);
				}
			}

		}
	}

	protected void addElementsWithDynamicPseudoClasses(Element element) {
		if (elementsWithDynamicPseudoClasses == null)
			elementsWithDynamicPseudoClasses = new HashMap();
		elementsWithDynamicPseudoClasses.put(element, element);
	}

	protected boolean isElementsWithDynamicPseudoClassesInitialized(
			Element element) {
		if (elementsWithDynamicPseudoClasses == null)
			return false;
		return (elementsWithDynamicPseudoClasses.get(element) != null);
	}

	/*--------------- w3c Element -----------------*/

	public IElementProvider getElementProvider() {
		return elementProvider;
	}

	public void setElementProvider(IElementProvider elementProvider) {
		this.elementProvider = elementProvider;
		// this.elementsContext = null;
	}

	/**
	 * Return the w3c Element linked to the Object element.
	 * 
	 * @param element
	 * @return
	 */
	public Element getElement(Object element) {
		Element elt = null;
		CSSElementContext elementContext = getCSSElementContext(element);
		if (elementContext != null) {
			if (!elementContext.elementMustBeRefreshed(elementProvider)) {
				return elementContext.getElement();
			}
		}
		if (element instanceof Element)
			elt = (Element) element;
		else if (elementProvider != null) {
			elt = elementProvider.getElement(element, this);
		}
		if (elt != null) {
			if (elementContext == null) {
				elementContext = new CSSElementContextImpl();
				getElementsContext().put(getNativeWidget(element),
						elementContext);
			}
			elementContext.setElementProvider(elementProvider);
			elementContext.setElement(elt);
		}
		return elt;
	}

	public Object getDocument() {
		return null;
	}

	public CSSElementContext getCSSElementContext(Object element) {
		Object o = getNativeWidget(element);
		return (CSSElementContext) getElementsContext().get(o);
	}

	public Object getNativeWidget(Object element) {
		Object o = element;
		if (element instanceof CSSStylableElement) {
			o = ((CSSStylableElement) o).getNativeWidget();
		}
		return o;
	}

	protected Map getElementsContext() {
		if (elementsContext == null)
			elementsContext = new HashMap();
		return elementsContext;
	}

	/*--------------- Error Handler -----------------*/

	/**
	 * Handle exceptions thrown while parsing, applying styles. By default this
	 * method call CSS Error Handler if it is initialized.
	 * 
	 */
	public void handleExceptions(Exception e) {
		if (errorHandler != null)
			errorHandler.error(e);
	}

	public CSSErrorHandler getErrorHandler() {
		return errorHandler;
	}

	/**
	 * Set the CSS Error Handler to manage exception.
	 */
	public void setErrorHandler(CSSErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	/*--------------- Resources Locator Manager -----------------*/

	public IResourcesLocatorManager getResourcesLocatorManager() {
		if (resourcesLocatorManager == null)
			return defaultResourcesLocatorManager;
		return resourcesLocatorManager;
	}

	public void setResourcesLocatorManager(
			IResourcesLocatorManager resourcesLocatorManager) {
		this.resourcesLocatorManager = resourcesLocatorManager;
	}

	/*--------------- Document/View CSS -----------------*/

	public DocumentCSS getDocumentCSS() {
		return documentCSS;
	}

	public ViewCSS getViewCSS() {
		return viewCSS;
	}

	public void dispose() {
		reset();
		elementsContext = null;
		if (resourcesRegistry != null)
			resourcesRegistry.dispose();
	}

	public void reset() {
		// Remove All Style Sheets
		((ExtendedDocumentCSS) documentCSS).removeAllStyleSheets();
		if (elementsWithDynamicPseudoClasses != null) {
			Collection elements = elementsWithDynamicPseudoClasses.values();
			for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
				Element element = (Element) iterator.next();
				Collection handlers = dynamicPseudoClassesHandler.values();
				for (Iterator iterator2 = handlers.iterator(); iterator2
						.hasNext();) {
					IDynamicPseudoClassesHandler handler = (IDynamicPseudoClassesHandler) iterator2
							.next();
					handler.dispose(element, this);
				}
			}
			elementsWithDynamicPseudoClasses = null;
		}
	}

	/*--------------- Resources Registry -----------------*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.engine.CSSEngine#getResourcesRegistry()
	 */
	public IResourcesRegistry getResourcesRegistry() {
		return resourcesRegistry;
	}

	public void setResourcesRegistry(IResourcesRegistry resourcesRegistry) {
		this.resourcesRegistry = resourcesRegistry;
	}

	public void registerCSSPropertyHandlerProvider(
			ICSSPropertyHandlerProvider handlerProvider) {
		propertyHandlerProviders.add(handlerProvider);
	}

	public void unregisterCSSPropertyHandlerProvider(
			ICSSPropertyHandlerProvider handlerProvider) {
		propertyHandlerProviders.remove(handlerProvider);
	}

	/*--------------- CSS Value Converter -----------------*/

	public void registerCSSValueConverter(ICSSValueConverter converter) {
		if (valueConverters == null)
			valueConverters = new HashMap();
		valueConverters.put(converter.getToType(), converter);
	}

	public void unregisterCSSValueConverter(ICSSValueConverter converter) {
		if (valueConverters == null)
			return;
		valueConverters.remove(converter);
	}

	public ICSSValueConverter getCSSValueConverter(Object toType) {
		if (valueConverters != null) {
			return (ICSSValueConverter) valueConverters.get(toType);
		}
		return null;
	}

	public Object convert(CSSValue value, Object toType, Object context)
			throws Exception {
		Object newValue = null;
		String key = CSSResourcesHelpers.getCSSValueKey(value);
		IResourcesRegistry resourcesRegistry = getResourcesRegistry();
		if (resourcesRegistry != null) {
			if (key != null)
				newValue = resourcesRegistry.getResource(toType, key);
		}
		if (newValue == null) {
			ICSSValueConverter converter = getCSSValueConverter(toType);
			if (converter != null) {
				newValue = converter.convert(value, this, context);
				if (newValue != null) {
					// cache it
					if (resourcesRegistry != null) {
						if (key != null)
							resourcesRegistry.registerResource(toType, key,
									newValue);
					}
				}
			}
		}
		return newValue;
	}

	public String convert(Object value, Object toType, Object context)
			throws Exception {
		ICSSValueConverter converter = getCSSValueConverter(toType);
		if (converter != null) {
			return converter.convert(value, this, context);
		}
		return null;
	}

	/*--------------- Abstract methods -----------------*/

	/**
	 * Return instance of CSS Parser.
	 */
	public abstract CSSParser makeCSSParser();

}
