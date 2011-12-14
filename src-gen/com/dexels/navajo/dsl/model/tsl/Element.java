/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.Element#getChildren <em>Children</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.Element#getParent <em>Parent</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.Element#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.Element#getContent <em>Content</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.Element#isSplitTag <em>Split Tag</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.Element#isClosedTag <em>Closed Tag</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getElement()
 * @model
 * @generated
 */
public interface Element extends EObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link com.dexels.navajo.dsl.model.tsl.Element}.
	 * It is bidirectional and its opposite is '{@link com.dexels.navajo.dsl.model.tsl.Element#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getElement_Children()
	 * @see com.dexels.navajo.dsl.model.tsl.Element#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<Element> getChildren();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.dexels.navajo.dsl.model.tsl.Element#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(Element)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getElement_Parent()
	 * @see com.dexels.navajo.dsl.model.tsl.Element#getChildren
	 * @model opposite="children" transient="false"
	 * @generated
	 */
	Element getParent();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.Element#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Element value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link com.dexels.navajo.dsl.model.tsl.PossibleExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getElement_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<PossibleExpression> getAttributes();

	/**
	 * Returns the value of the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Content</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Content</em>' attribute.
	 * @see #setContent(String)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getElement_Content()
	 * @model
	 * @generated
	 */
	String getContent();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.Element#getContent <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content</em>' attribute.
	 * @see #getContent()
	 * @generated
	 */
	void setContent(String value);

	/**
	 * Returns the value of the '<em><b>Split Tag</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Split Tag</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Split Tag</em>' attribute.
	 * @see #setSplitTag(boolean)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getElement_SplitTag()
	 * @model default="false"
	 * @generated
	 */
	boolean isSplitTag();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.Element#isSplitTag <em>Split Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Split Tag</em>' attribute.
	 * @see #isSplitTag()
	 * @generated
	 */
	void setSplitTag(boolean value);

	/**
	 * Returns the value of the '<em><b>Closed Tag</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Closed Tag</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Closed Tag</em>' attribute.
	 * @see #setClosedTag(boolean)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getElement_ClosedTag()
	 * @model default="false"
	 * @generated
	 */
	boolean isClosedTag();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.Element#isClosedTag <em>Closed Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Closed Tag</em>' attribute.
	 * @see #isClosedTag()
	 * @generated
	 */
	void setClosedTag(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	int calculateDepth();

} // Element
