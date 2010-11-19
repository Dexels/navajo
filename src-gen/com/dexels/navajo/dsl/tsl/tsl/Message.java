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
 * A representation of the model object '<em><b>Message</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Message#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Message#getMessages <em>Messages</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Message#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Message#getMaps <em>Maps</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMessage()
 * @model
 * @generated
 */
public interface Message extends EObject
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
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMessage_Attributes()
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
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMessage_Messages()
   * @model containment="true"
   * @generated
   */
  EList<Message> getMessages();

  /**
   * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
   * The list contents are of type {@link com.dexels.navajo.dsl.tsl.tsl.Property}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Properties</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Properties</em>' containment reference list.
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMessage_Properties()
   * @model containment="true"
   * @generated
   */
  EList<Property> getProperties();

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
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMessage_Maps()
   * @model containment="true"
   * @generated
   */
  EList<Map> getMaps();

} // Message
