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
package org.akrogen.tkui.css.core.impl.sac;

import org.w3c.css.sac.AttributeCondition;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CombinatorCondition;
import org.w3c.css.sac.Condition;
import org.w3c.css.sac.ConditionFactory;
import org.w3c.css.sac.ContentCondition;
import org.w3c.css.sac.LangCondition;
import org.w3c.css.sac.NegativeCondition;
import org.w3c.css.sac.PositionalCondition;

/**
 * This class provides an implementation of the
 * {@link org.w3c.css.sac.ConditionFactory} interface.
 * 
 * @version 1.0.0
 * @author <a href="mailto:angelo.zerr@gmail.com">Angelo ZERR</a>
 * 
 */
public class CSSConditionFactoryImpl implements ConditionFactory {

	/**
	 * The class attribute namespace URI.
	 */
	protected String classNamespaceURI;

	/**
	 * The class attribute local name.
	 */
	protected String classLocalName;

	/**
	 * The id attribute namespace URI.
	 */
	protected String idNamespaceURI;

	/**
	 * The id attribute local name.
	 */
	protected String idLocalName;

	/**
	 * Creates a new condition factory.
	 */
	public CSSConditionFactoryImpl(String cns, String cln, String idns,
			String idln) {
		classNamespaceURI = cns;
		classLocalName = cln;
		idNamespaceURI = idns;
		idLocalName = idln;
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * ConditionFactory#createAndCondition(Condition,Condition)}.
	 */
	public CombinatorCondition createAndCondition(Condition first,
			Condition second) throws CSSException {
		return new CSSAndConditionImpl(first, second);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * ConditionFactory#createOrCondition(Condition,Condition)}.
	 */
	public CombinatorCondition createOrCondition(Condition first,
			Condition second) throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.ConditionFactory#createNegativeCondition(Condition)}.
	 */
	public NegativeCondition createNegativeCondition(Condition condition)
			throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * ConditionFactory#createPositionalCondition(int,boolean,boolean)}.
	 */
	public PositionalCondition createPositionalCondition(int position,
			boolean typeNode, boolean type) throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * ConditionFactory#createAttributeCondition(String,String,boolean,String)}.
	 */
	public AttributeCondition createAttributeCondition(String localName,
			String namespaceURI, boolean specified, String value)
			throws CSSException {
		return new CSSAttributeConditionImpl(localName, namespaceURI, specified,
				value);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.ConditionFactory#createIdCondition(String)}.
	 */
	public AttributeCondition createIdCondition(String value)
			throws CSSException {
		return new CSSIdConditionImpl(idNamespaceURI, idLocalName, value);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.ConditionFactory#createLangCondition(String)}.
	 */
	public LangCondition createLangCondition(String lang) throws CSSException {
		return new CSSLangConditionImpl(lang);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * ConditionFactory#createOneOfAttributeCondition(String,String,boolean,String)}.
	 */
	public AttributeCondition createOneOfAttributeCondition(String localName,
			String nsURI, boolean specified, String value) throws CSSException {
		return new CSSOneOfAttributeConditionImpl(localName, nsURI, specified,
				value);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * ConditionFactory#createBeginHyphenAttributeCondition(String,String,boolean,String)}.
	 */
	public AttributeCondition createBeginHyphenAttributeCondition(
			String localName, String namespaceURI, boolean specified,
			String value) throws CSSException {
		return new CSSBeginHyphenAttributeConditionImpl(localName,
				namespaceURI, specified, value);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.ConditionFactory#createClassCondition(String,String)}.
	 */
	public AttributeCondition createClassCondition(String namespaceURI,
			String value) throws CSSException {
		return new CSSClassConditionImpl(classLocalName, classNamespaceURI, value);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * ConditionFactory#createPseudoClassCondition(String,String)}.
	 */
	public AttributeCondition createPseudoClassCondition(String namespaceURI,
			String value) throws CSSException {
		return new CSSPseudoClassConditionImpl(namespaceURI, value);
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.ConditionFactory#createOnlyChildCondition()}.
	 */
	public Condition createOnlyChildCondition() throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.ConditionFactory#createOnlyTypeCondition()}.
	 */
	public Condition createOnlyTypeCondition() throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}

	/**
	 * <b>SAC</b>: Implements {@link
	 * org.w3c.css.sac.ConditionFactory#createContentCondition(String)}.
	 */
	public ContentCondition createContentCondition(String data)
			throws CSSException {
		throw new CSSException("Not implemented in CSS2");
	}
}
