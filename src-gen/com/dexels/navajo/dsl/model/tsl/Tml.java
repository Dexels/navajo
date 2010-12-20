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
 * A representation of the model object '<em><b>Tml</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.Tml#getMethods <em>Methods</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getTml()
 * @model
 * @generated
 */
public interface Tml extends Element {

	/**
	 * Returns the value of the '<em><b>Methods</b></em>' containment reference list.
	 * The list contents are of type {@link com.dexels.navajo.dsl.model.tsl.Methods}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Methods</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Methods</em>' containment reference list.
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getTml_Methods()
	 * @model containment="true"
	 * @generated
	 */
	EList<Methods> getMethods();

} // Tml
