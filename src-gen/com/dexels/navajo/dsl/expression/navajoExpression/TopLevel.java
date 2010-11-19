/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.expression.navajoExpression;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Top Level</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.TopLevel#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage#getTopLevel()
 * @model
 * @generated
 */
public interface TopLevel extends EObject
{
  /**
   * Returns the value of the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Expression</em>' containment reference.
   * @see #setExpression(Expression)
   * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage#getTopLevel_Expression()
   * @model containment="true"
   * @generated
   */
  Expression getExpression();

  /**
   * Sets the value of the '{@link com.dexels.navajo.dsl.expression.navajoExpression.TopLevel#getExpression <em>Expression</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Expression</em>' containment reference.
   * @see #getExpression()
   * @generated
   */
  void setExpression(Expression value);

} // TopLevel
