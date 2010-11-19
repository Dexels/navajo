/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.expression.navajoExpression;

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
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getOperands <em>Operands</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getOp <em>Op</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getFunctionoperands <em>Functionoperands</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getLiteral <em>Literal</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage#getExpression()
 * @model
 * @generated
 */
public interface Expression extends EObject
{
  /**
   * Returns the value of the '<em><b>Operands</b></em>' containment reference list.
   * The list contents are of type {@link com.dexels.navajo.dsl.expression.navajoExpression.Expression}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operands</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operands</em>' containment reference list.
   * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage#getExpression_Operands()
   * @model containment="true"
   * @generated
   */
  EList<Expression> getOperands();

  /**
   * Returns the value of the '<em><b>Op</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Op</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Op</em>' attribute.
   * @see #setOp(String)
   * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage#getExpression_Op()
   * @model
   * @generated
   */
  String getOp();

  /**
   * Sets the value of the '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getOp <em>Op</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Op</em>' attribute.
   * @see #getOp()
   * @generated
   */
  void setOp(String value);

  /**
   * Returns the value of the '<em><b>Functionoperands</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Functionoperands</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Functionoperands</em>' containment reference.
   * @see #setFunctionoperands(Expression)
   * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage#getExpression_Functionoperands()
   * @model containment="true"
   * @generated
   */
  Expression getFunctionoperands();

  /**
   * Sets the value of the '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getFunctionoperands <em>Functionoperands</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Functionoperands</em>' containment reference.
   * @see #getFunctionoperands()
   * @generated
   */
  void setFunctionoperands(Expression value);

  /**
   * Returns the value of the '<em><b>Literal</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Literal</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Literal</em>' attribute.
   * @see #setLiteral(int)
   * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage#getExpression_Literal()
   * @model
   * @generated
   */
  int getLiteral();

  /**
   * Sets the value of the '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getLiteral <em>Literal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Literal</em>' attribute.
   * @see #getLiteral()
   * @generated
   */
  void setLiteral(int value);

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage#getExpression_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

} // Expression
