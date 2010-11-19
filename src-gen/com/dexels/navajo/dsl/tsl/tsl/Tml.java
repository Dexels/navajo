/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.tsl.tsl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tml</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Tml#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Tml#getMessages <em>Messages</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Tml#getMaps <em>Maps</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getTml()
 * @model
 * @generated
 */
public interface Tml extends EObject
{
  /**
   * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
   * The list contents are of type {@link com.dexels.navajo.dsl.tsl.tsl.PossibleExpression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attributes</em>' containment reference list.
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getTml_Attributes()
   * @model containment="true"
   * @generated
   */
  EList<PossibleExpression> getAttributes();

  /**
   * Returns the value of the '<em><b>Messages</b></em>' containment reference list.
   * The list contents are of type {@link com.dexels.navajo.dsl.tsl.tsl.Message}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Messages</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Messages</em>' containment reference list.
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getTml_Messages()
   * @model containment="true"
   * @generated
   */
  EList<Message> getMessages();

  /**
   * Returns the value of the '<em><b>Maps</b></em>' containment reference list.
   * The list contents are of type {@link com.dexels.navajo.dsl.tsl.tsl.Map}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Maps</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Maps</em>' containment reference list.
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getTml_Maps()
   * @model containment="true"
   * @generated
   */
  EList<Map> getMaps();

} // Tml
