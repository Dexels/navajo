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
 * A representation of the model object '<em><b>Path Sequence</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.PathSequence#getPathElements <em>Path Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getPathSequence()
 * @model
 * @generated
 */
public interface PathSequence extends EObject {
	/**
	 * Returns the value of the '<em><b>Path Elements</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path Elements</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path Elements</em>' attribute.
	 * @see #setPathElements(String)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getPathSequence_PathElements()
	 * @model
	 * @generated
	 */
	String getPathElements();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.PathSequence#getPathElements <em>Path Elements</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path Elements</em>' attribute.
	 * @see #getPathElements()
	 * @generated
	 */
	void setPathElements(String value);

} // PathSequence
