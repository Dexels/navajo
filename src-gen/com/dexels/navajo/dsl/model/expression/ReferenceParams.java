/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference Params</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.ReferenceParams#getGetterParams <em>Getter Params</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getReferenceParams()
 * @model
 * @generated
 */
public interface ReferenceParams extends EObject {
	/**
	 * Returns the value of the '<em><b>Getter Params</b></em>' containment reference list.
	 * The list contents are of type {@link com.dexels.navajo.dsl.model.expression.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Getter Params</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Getter Params</em>' containment reference list.
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getReferenceParams_GetterParams()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getGetterParams();

} // ReferenceParams
