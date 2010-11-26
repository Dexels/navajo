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
 * A representation of the model object '<em><b>Map Get Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.MapGetReference#getPathElements <em>Path Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getMapGetReference()
 * @model
 * @generated
 */
public interface MapGetReference extends Expression {
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
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getMapGetReference_PathElements()
	 * @model
	 * @generated
	 */
	EList<String> getPathElements();

} // MapGetReference
