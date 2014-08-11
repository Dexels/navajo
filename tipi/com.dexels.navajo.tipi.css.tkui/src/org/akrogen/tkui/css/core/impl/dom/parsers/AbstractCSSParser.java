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
package org.akrogen.tkui.css.core.impl.dom.parsers;

import java.io.IOException;
import java.util.Stack;

import org.akrogen.tkui.css.core.dom.parsers.CSSParser;
import org.akrogen.tkui.css.core.impl.dom.CSSStyleDeclarationImpl;
import org.akrogen.tkui.css.core.impl.dom.CSSValueImpl;
import org.akrogen.tkui.css.core.sac.DocumentHandlerFactory;
import org.akrogen.tkui.css.core.sac.ExtendedDocumentHandler;
import org.akrogen.tkui.css.core.sac.ISACParserFactory;
import org.akrogen.tkui.css.core.sac.ParserNotFoundException;
import org.akrogen.tkui.css.core.sac.SACParserFactory;
import org.w3c.css.sac.ConditionFactory;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;
import org.w3c.css.sac.SelectorFactory;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSValue;

/**
 * Abstract {@link CSSParser} implementation.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class AbstractCSSParser implements CSSParser {

	private static DocumentHandlerFactory defaultDocumentHandlerFactory;
	private static ISACParserFactory defaultParserFactory;
	static {
		defaultDocumentHandlerFactory = DocumentHandlerFactory.newInstance();
		defaultParserFactory = SACParserFactory.newInstance();
	}

	// SAC
	private Parser parser = null;
	private DocumentHandlerFactory documentHandlerFactory;
	private ISACParserFactory parserFactory;

	private ConditionFactory conditionFactory = null;
	private SelectorFactory selectorFactory = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#parseStyleSheet(org.w3c.css.sac.InputSource)
	 */
	public CSSStyleSheet parseStyleSheet(InputSource source) throws IOException {
		ExtendedDocumentHandler documentHandler = getDocumentHandlerFactory()
				.makeDocumentHandler();
		Parser parser = getParser();
		parser.setDocumentHandler(documentHandler);
		parser.parseStyleSheet(source);
		return (CSSStyleSheet) documentHandler.getNodeRoot();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#parseStyleDeclaration(org.w3c.css.sac.InputSource)
	 */
	public CSSStyleDeclaration parseStyleDeclaration(InputSource source)
			throws IOException {
		CSSStyleDeclarationImpl styleDeclaration = new CSSStyleDeclarationImpl(
				null);
		parseStyleDeclaration(((CSSStyleDeclaration) (styleDeclaration)),
				source);
		return styleDeclaration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#parseStyleDeclaration(org.w3c.dom.css.CSSStyleDeclaration,
	 *      org.w3c.css.sac.InputSource)
	 */
	public void parseStyleDeclaration(CSSStyleDeclaration styleDeclaration,
			InputSource source) throws IOException {
		Stack stack = new Stack();
		stack.push(styleDeclaration);
		ExtendedDocumentHandler documentHandler = getDocumentHandlerFactory()
				.makeDocumentHandler();
		documentHandler.setNodeStack(stack);
		Parser parser = getParser();
		parser.setDocumentHandler(documentHandler);
		parser.parseStyleDeclaration(source);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#parsePropertyValue(org.w3c.css.sac.InputSource)
	 */
	public CSSValue parsePropertyValue(InputSource source) throws IOException {
		Parser parser = getParser();
		ExtendedDocumentHandler documentHandler = getDocumentHandlerFactory()
				.makeDocumentHandler();
		parser.setDocumentHandler(documentHandler);
		return new CSSValueImpl(parser.parsePropertyValue(source));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#parseRule(org.w3c.css.sac.InputSource)
	 */
	public CSSRule parseRule(InputSource source) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#parseSelectors(org.w3c.css.sac.InputSource)
	 */
	public SelectorList parseSelectors(InputSource source) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#setParentStyleSheet(org.w3c.dom.css.CSSStyleSheet)
	 */
	public void setParentStyleSheet(CSSStyleSheet parentStyleSheet) {

	}

	/**
	 * Return instance of {@link DocumentHandlerFactory}.
	 * 
	 * @return
	 */
	public DocumentHandlerFactory getDocumentHandlerFactory() {
		if (documentHandlerFactory == null)
			return defaultDocumentHandlerFactory;
		return documentHandlerFactory;
	}

	/**
	 * Set instance of {@link DocumentHandlerFactory}.
	 * 
	 * @param documentHandlerFactory
	 */
	public void setDocumentHandlerFactory(
			DocumentHandlerFactory documentHandlerFactory) {
		this.documentHandlerFactory = documentHandlerFactory;
	}

	/**
	 * Return SAC {@link Parser} to use.
	 * 
	 * @return
	 */
	public Parser getParser() {
		if (parser == null)
			try {
				parser = getSACParserFactory().makeParser();
				if (conditionFactory != null)
					parser.setConditionFactory(conditionFactory);
				if (selectorFactory != null)
					parser.setSelectorFactory(selectorFactory);
			} catch (Exception e) {
				// TODO : manage error.
				// logger.error("Error: ",e);
				throw new ParserNotFoundException(e);
			}
		return parser;
	}

	/**
	 * Set SAC {@link Parser} to use.
	 * 
	 * @param parser
	 */
	public void setParser(Parser parser) {
		this.parser = parser;
	}

	/**
	 * Return factory {@link ISACParserFactory} to use.
	 * 
	 * @return
	 */
	public ISACParserFactory getSACParserFactory() {
		if (parserFactory == null)
			return defaultParserFactory;
		return parserFactory;
	}

	/**
	 * Set factory {@link ISACParserFactory} to use.
	 * 
	 * @param parserFactory
	 */
	public void setSACParserFactory(ISACParserFactory parserFactory) {
		this.parserFactory = parserFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#getConditionFactory()
	 */
	public ConditionFactory getConditionFactory() {
		return conditionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#setConditionFactory(org.w3c.css.sac.ConditionFactory)
	 */
	public void setConditionFactory(ConditionFactory conditionFactory) {
		this.conditionFactory = conditionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#getSelectorFactory()
	 */
	public SelectorFactory getSelectorFactory() {
		return selectorFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.akrogen.tkui.css.core.dom.parsers.CSSParser#setSelectorFactory(org.w3c.css.sac.SelectorFactory)
	 */
	public void setSelectorFactory(SelectorFactory selectorFactory) {
		this.selectorFactory = selectorFactory;
	}

}
