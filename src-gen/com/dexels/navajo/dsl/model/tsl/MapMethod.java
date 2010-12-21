/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl;

import com.dexels.navajo.dsl.model.expression.TopLevel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Map Method</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getMapName <em>Map Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getMethodClosingName <em>Method Closing Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getMethodName <em>Method Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getMethodClosingMethod <em>Method Closing Method</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getMapMethod()
 * @model
 * @generated
 */
public interface MapMethod extends Element {
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
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getMapMethod_MapName()
	 * @model
	 * @generated
	 */
	String getMapName();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getMapName <em>Map Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Name</em>' attribute.
	 * @see #getMapName()
	 * @generated
	 */
	void setMapName(String value);

	/**
	 * Returns the value of the '<em><b>Method Closing Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method Closing Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method Closing Name</em>' attribute.
	 * @see #setMethodClosingName(String)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getMapMethod_MethodClosingName()
	 * @model
	 * @generated
	 */
	String getMethodClosingName();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getMethodClosingName <em>Method Closing Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method Closing Name</em>' attribute.
	 * @see #getMethodClosingName()
	 * @generated
	 */
	void setMethodClosingName(String value);

	/**
	 * Returns the value of the '<em><b>Method Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method Name</em>' attribute.
	 * @see #setMethodName(String)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getMapMethod_MethodName()
	 * @model
	 * @generated
	 */
	String getMethodName();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getMethodName <em>Method Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method Name</em>' attribute.
	 * @see #getMethodName()
	 * @generated
	 */
	void setMethodName(String value);

	/**
	 * Returns the value of the '<em><b>Method Closing Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method Closing Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method Closing Method</em>' attribute.
	 * @see #setMethodClosingMethod(String)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getMapMethod_MethodClosingMethod()
	 * @model
	 * @generated
	 */
	String getMethodClosingMethod();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getMethodClosingMethod <em>Method Closing Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method Closing Method</em>' attribute.
	 * @see #getMethodClosingMethod()
	 * @generated
	 */
	void setMethodClosingMethod(String value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(TopLevel)
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#getMapMethod_Expression()
	 * @model containment="true"
	 * @generated
	 */
	TopLevel getExpression();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.tsl.MapMethod#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(TopLevel value);

} // MapMethod
