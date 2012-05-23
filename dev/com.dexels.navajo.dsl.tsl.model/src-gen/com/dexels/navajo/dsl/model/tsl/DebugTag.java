/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl;

import com.dexels.navajo.dsl.model.expression.TopLevel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Debug Tag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.DebugTag#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getDebugTag()
 * @model
 * @generated
 */
public interface DebugTag extends Element {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(TopLevel)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getDebugTag_Expression()
	 * @model containment="true"
	 * @generated
	 */
	TopLevel getExpression();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.DebugTag#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(TopLevel value);

} // DebugTag
