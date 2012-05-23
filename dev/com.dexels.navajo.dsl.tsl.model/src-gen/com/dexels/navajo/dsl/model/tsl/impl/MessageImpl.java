/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl.impl;

import org.eclipse.emf.ecore.EClass;

import com.dexels.navajo.dsl.model.tsl.Message;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.TslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class MessageImpl extends ElementImpl implements Message {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MessageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TslPackage.Literals.MESSAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		for (PossibleExpression possibleExpression : getAttributes()) {
			if("name".equals(possibleExpression.getKey())) {
				if(possibleExpression.getValue()!=null) {
					return possibleExpression.getValue();
				} else {
					return "[[expression]]";
				}
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getType() {
		for (PossibleExpression possibleExpression : getAttributes()) {
			if("type".equals(possibleExpression.getKey())) {
				if(possibleExpression.getValue()!=null) {
					return possibleExpression.getValue();
				} else {
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isArray() {
		return "array".equals(getType());
	}

} //MessageImpl
