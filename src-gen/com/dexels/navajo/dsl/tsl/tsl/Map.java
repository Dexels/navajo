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
 * A representation of the model object '<em><b>Map</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMapName <em>Map Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Map#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMessages <em>Messages</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Map#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMaps <em>Maps</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMapClosingName <em>Map Closing Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMap()
 * @model
 * @generated
 */
public interface Map extends EObject
{
  /**
   * Returns the value of the '<em><b>Map Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Map Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Map Name</em>' attribute.
   * @see #setMapName(String)
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMap_MapName()
   * @model
   * @generated
   */
  String getMapName();

  /**
   * Sets the value of the '{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMapName <em>Map Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Map Name</em>' attribute.
   * @see #getMapName()
   * @generated
   */
  void setMapName(String value);

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
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMap_Attributes()
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
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMap_Messages()
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
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMap_Properties()
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
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMap_Maps()
   * @model containment="true"
   * @generated
   */
  EList<Map> getMaps();

  /**
   * Returns the value of the '<em><b>Map Closing Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Map Closing Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Map Closing Name</em>' attribute.
   * @see #setMapClosingName(String)
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getMap_MapClosingName()
   * @model
   * @generated
   */
  String getMapClosingName();

  /**
   * Sets the value of the '{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMapClosingName <em>Map Closing Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Map Closing Name</em>' attribute.
   * @see #getMapClosingName()
   * @generated
   */
  void setMapClosingName(String value);

} // Map
