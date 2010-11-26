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
 * A representation of the model object '<em><b>Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.Expression#getParent <em>Parent</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.Expression#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.Expression#getOperations <em>Operations</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.Expression#getElements <em>Elements</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.Expression#getSubliteral <em>Subliteral</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.Expression#getValueString <em>Value String</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.Expression#getExpressionType <em>Expression Type</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.Expression#getPathSequence <em>Path Sequence</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.Expression#getExistsPathSequence <em>Exists Path Sequence</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression()
 * @model
 * @generated
 */
public interface Expression extends EObject {
	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.dexels.navajo.dsl.model.expression.Expression#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(Expression)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression_Parent()
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getParameters
	 * @model opposite="parameters" transient="false"
	 * @generated
	 */
	Expression getParent();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.Expression#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Expression value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link com.dexels.navajo.dsl.model.expression.Expression}.
	 * It is bidirectional and its opposite is '{@link com.dexels.navajo.dsl.model.expression.Expression#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression_Parameters()
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<Expression> getParameters();

	/**
	 * Returns the value of the '<em><b>Operations</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operations</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' attribute list.
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression_Operations()
	 * @model
	 * @generated
	 */
	EList<String> getOperations();

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' attribute list.
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression_Elements()
	 * @model
	 * @generated
	 */
	EList<String> getElements();

	/**
	 * Returns the value of the '<em><b>Subliteral</b></em>' reference list.
	 * The list contents are of type {@link com.dexels.navajo.dsl.model.expression.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subliteral</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subliteral</em>' reference list.
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression_Subliteral()
	 * @model
	 * @generated
	 */
	EList<Expression> getSubliteral();

	/**
	 * Returns the value of the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value String</em>' attribute.
	 * @see #setValueString(String)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression_ValueString()
	 * @model
	 * @generated
	 */
	String getValueString();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.Expression#getValueString <em>Value String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value String</em>' attribute.
	 * @see #getValueString()
	 * @generated
	 */
	void setValueString(String value);

	/**
	 * Returns the value of the '<em><b>Expression Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression Type</em>' attribute.
	 * @see #setExpressionType(String)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression_ExpressionType()
	 * @model
	 * @generated
	 */
	String getExpressionType();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.Expression#getExpressionType <em>Expression Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression Type</em>' attribute.
	 * @see #getExpressionType()
	 * @generated
	 */
	void setExpressionType(String value);

	/**
	 * Returns the value of the '<em><b>Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path Sequence</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path Sequence</em>' reference.
	 * @see #setPathSequence(PathSequence)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression_PathSequence()
	 * @model
	 * @generated
	 */
	PathSequence getPathSequence();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.Expression#getPathSequence <em>Path Sequence</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path Sequence</em>' reference.
	 * @see #getPathSequence()
	 * @generated
	 */
	void setPathSequence(PathSequence value);

	/**
	 * Returns the value of the '<em><b>Exists Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exists Path Sequence</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exists Path Sequence</em>' reference.
	 * @see #setExistsPathSequence(PathSequence)
	 * @see com.dexels.navajo.dsl.model.expression.ExpressionPackage#getExpression_ExistsPathSequence()
	 * @model
	 * @generated
	 */
	PathSequence getExistsPathSequence();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.dsl.model.expression.Expression#getExistsPathSequence <em>Exists Path Sequence</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exists Path Sequence</em>' reference.
	 * @see #getExistsPathSequence()
	 * @generated
	 */
	void setExistsPathSequence(PathSequence value);

} // Expression
