/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.expression.navajoExpression;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage
 * @generated
 */
public interface NavajoExpressionFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  NavajoExpressionFactory eINSTANCE = com.dexels.navajo.dsl.expression.navajoExpression.impl.NavajoExpressionFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Top Level</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Top Level</em>'.
   * @generated
   */
  TopLevel createTopLevel();

  /**
   * Returns a new object of class '<em>Expression</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Expression</em>'.
   * @generated
   */
  Expression createExpression();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  NavajoExpressionPackage getNavajoExpressionPackage();

} //NavajoExpressionFactory
