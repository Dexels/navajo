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
 * A representation of the model object '<em><b>Function Operands</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.FunctionOperands#getParamList <em>Param List</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getFunctionOperands()
 * @model
 * @generated
 */
public interface FunctionOperands extends Expression {
	/**
	 * Returns the value of the '<em><b>Param List</b></em>' reference list.
	 * The list contents are of type {@link com.dexels.navajo.dsl.model.expression.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Param List</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Param List</em>' reference list.
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getFunctionOperands_ParamList()
	 * @model
	 * @generated
	 */
	EList<Expression> getParamList();

} // FunctionOperands
