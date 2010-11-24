/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.Property#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.Property#getExpressionValue <em>Expression Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getProperty()
 * @model
 * @generated
 */
public interface Property extends Element {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link com.dexels.navajo.dsl.model.tsl.PossibleExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getProperty_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<PossibleExpression> getAttributes();

	/**
	 * Returns the value of the '<em><b>Expression Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression Value</em>' containment reference.
	 * @see #setExpressionValue(ExpressionTag)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getProperty_ExpressionValue()
	 * @model containment="true"
	 * @generated
	 */
	ExpressionTag getExpressionValue();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.Property#getExpressionValue <em>Expression Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression Value</em>' containment reference.
	 * @see #getExpressionValue()
	 * @generated
	 */
	void setExpressionValue(ExpressionTag value);

} // Property
