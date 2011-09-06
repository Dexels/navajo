/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl;

import org.eclipse.emf.ecore.EObject;

import com.dexels.navajo.dsl.model.expression.TopLevel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Possible Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getKey <em>Key</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getExpressionValue <em>Expression Value</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getValue <em>Value</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getEndToken <em>End Token</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getPossibleExpression()
 * @model
 * @generated
 */
public interface PossibleExpression extends EObject {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getPossibleExpression_Key()
	 * @model
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Expression Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression Value</em>' containment reference.
	 * @see #setExpressionValue(TopLevel)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getPossibleExpression_ExpressionValue()
	 * @model containment="true"
	 * @generated
	 */
	TopLevel getExpressionValue();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getExpressionValue <em>Expression Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression Value</em>' containment reference.
	 * @see #getExpressionValue()
	 * @generated
	 */
	void setExpressionValue(TopLevel value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getPossibleExpression_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Namespace</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Namespace</em>' attribute.
	 * @see #setNamespace(String)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getPossibleExpression_Namespace()
	 * @model
	 * @generated
	 */
	String getNamespace();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getNamespace <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Namespace</em>' attribute.
	 * @see #getNamespace()
	 * @generated
	 */
	void setNamespace(String value);

	/**
	 * Returns the value of the '<em><b>End Token</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Token</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Token</em>' attribute.
	 * @see #setEndToken(String)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getPossibleExpression_EndToken()
	 * @model
	 * @generated
	 */
	String getEndToken();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getEndToken <em>End Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Token</em>' attribute.
	 * @see #getEndToken()
	 * @generated
	 */
	void setEndToken(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isExpression();

} // PossibleExpression
