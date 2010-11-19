/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.tsl.tsl;

import com.dexels.navajo.dsl.expression.navajoExpression.TopLevel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expression Tag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.ExpressionTag#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getExpressionTag()
 * @model
 * @generated
 */
public interface ExpressionTag extends EObject
{
  /**
   * Returns the value of the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Value</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Value</em>' containment reference.
   * @see #setValue(TopLevel)
   * @see com.dexels.navajo.dsl.tsl.tsl.TslPackage#getExpressionTag_Value()
   * @model containment="true"
   * @generated
   */
  TopLevel getValue();

  /**
   * Sets the value of the '{@link com.dexels.navajo.dsl.tsl.tsl.ExpressionTag#getValue <em>Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Value</em>' containment reference.
   * @see #getValue()
   * @generated
   */
  void setValue(TopLevel value);

} // ExpressionTag
