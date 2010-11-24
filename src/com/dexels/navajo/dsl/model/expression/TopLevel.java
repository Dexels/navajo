/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Top Level</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.TopLevel#getToplevelExpression <em>Toplevel Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getTopLevel()
 * @model
 * @generated
 */
public interface TopLevel extends EObject {
	/**
	 * Returns the value of the '<em><b>Toplevel Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Toplevel Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Toplevel Expression</em>' containment reference.
	 * @see #setToplevelExpression(Expression)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getTopLevel_ToplevelExpression()
	 * @model containment="true"
	 * @generated
	 */
	Expression getToplevelExpression();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.TopLevel#getToplevelExpression <em>Toplevel Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Toplevel Expression</em>' containment reference.
	 * @see #getToplevelExpression()
	 * @generated
	 */
	void setToplevelExpression(Expression value);

} // TopLevel
