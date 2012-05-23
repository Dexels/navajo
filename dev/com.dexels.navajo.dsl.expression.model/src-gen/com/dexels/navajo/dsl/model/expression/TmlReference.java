/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tml Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.TmlReference#getPathElements <em>Path Elements</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.TmlReference#isAbsolute <em>Absolute</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.TmlReference#isParam <em>Param</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getTmlReference()
 * @model
 * @generated
 */
public interface TmlReference extends Expression {
	/**
	 * Returns the value of the '<em><b>Path Elements</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path Elements</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path Elements</em>' attribute list.
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getTmlReference_PathElements()
	 * @model
	 * @generated
	 */
	EList<String> getPathElements();

	/**
	 * Returns the value of the '<em><b>Absolute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Absolute</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Absolute</em>' attribute.
	 * @see #setAbsolute(boolean)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getTmlReference_Absolute()
	 * @model
	 * @generated
	 */
	boolean isAbsolute();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.TmlReference#isAbsolute <em>Absolute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Absolute</em>' attribute.
	 * @see #isAbsolute()
	 * @generated
	 */
	void setAbsolute(boolean value);

	/**
	 * Returns the value of the '<em><b>Param</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Param</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Param</em>' attribute.
	 * @see #setParam(boolean)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getTmlReference_Param()
	 * @model
	 * @generated
	 */
	boolean isParam();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.TmlReference#isParam <em>Param</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Param</em>' attribute.
	 * @see #isParam()
	 * @generated
	 */
	void setParam(boolean value);

} // TmlReference
