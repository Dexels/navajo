/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function Call</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.FunctionCall#getName <em>Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.FunctionCall#getOperands <em>Operands</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getFunctionCall()
 * @model
 * @generated
 */
public interface FunctionCall extends Expression {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getFunctionCall_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.FunctionCall#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Operands</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operands</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operands</em>' reference.
	 * @see #setOperands(FunctionOperands)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getFunctionCall_Operands()
	 * @model
	 * @generated
	 */
	FunctionOperands getOperands();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.FunctionCall#getOperands <em>Operands</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operands</em>' reference.
	 * @see #getOperands()
	 * @generated
	 */
	void setOperands(FunctionOperands value);

} // FunctionCall
