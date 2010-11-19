/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.expression.navajoExpression;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionFactory
 * @model kind="package"
 * @generated
 */
public interface NavajoExpressionPackage extends EPackage
{
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "navajoExpression";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://www.dexels.com/navajo/dsl/expression/NavajoExpression";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "navajoExpression";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  NavajoExpressionPackage eINSTANCE = com.dexels.navajo.dsl.expression.navajoExpression.impl.NavajoExpressionPackageImpl.init();

  /**
   * The meta object id for the '{@link com.dexels.navajo.dsl.expression.navajoExpression.impl.TopLevelImpl <em>Top Level</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.dexels.navajo.dsl.expression.navajoExpression.impl.TopLevelImpl
   * @see com.dexels.navajo.dsl.expression.navajoExpression.impl.NavajoExpressionPackageImpl#getTopLevel()
   * @generated
   */
  int TOP_LEVEL = 0;

  /**
   * The feature id for the '<em><b>Expression</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TOP_LEVEL__EXPRESSION = 0;

  /**
   * The number of structural features of the '<em>Top Level</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TOP_LEVEL_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link com.dexels.navajo.dsl.expression.navajoExpression.impl.ExpressionImpl <em>Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.dexels.navajo.dsl.expression.navajoExpression.impl.ExpressionImpl
   * @see com.dexels.navajo.dsl.expression.navajoExpression.impl.NavajoExpressionPackageImpl#getExpression()
   * @generated
   */
  int EXPRESSION = 1;

  /**
   * The feature id for the '<em><b>Operands</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__OPERANDS = 0;

  /**
   * The feature id for the '<em><b>Op</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__OP = 1;

  /**
   * The feature id for the '<em><b>Functionoperands</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__FUNCTIONOPERANDS = 2;

  /**
   * The feature id for the '<em><b>Literal</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__LITERAL = 3;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION__NAME = 4;

  /**
   * The number of structural features of the '<em>Expression</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_FEATURE_COUNT = 5;


  /**
   * Returns the meta object for class '{@link com.dexels.navajo.dsl.expression.navajoExpression.TopLevel <em>Top Level</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Top Level</em>'.
   * @see com.dexels.navajo.dsl.expression.navajoExpression.TopLevel
   * @generated
   */
  EClass getTopLevel();

  /**
   * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.expression.navajoExpression.TopLevel#getExpression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression</em>'.
   * @see com.dexels.navajo.dsl.expression.navajoExpression.TopLevel#getExpression()
   * @see #getTopLevel()
   * @generated
   */
  EReference getTopLevel_Expression();

  /**
   * Returns the meta object for class '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression <em>Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression</em>'.
   * @see com.dexels.navajo.dsl.expression.navajoExpression.Expression
   * @generated
   */
  EClass getExpression();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getOperands <em>Operands</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Operands</em>'.
   * @see com.dexels.navajo.dsl.expression.navajoExpression.Expression#getOperands()
   * @see #getExpression()
   * @generated
   */
  EReference getExpression_Operands();

  /**
   * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getOp <em>Op</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Op</em>'.
   * @see com.dexels.navajo.dsl.expression.navajoExpression.Expression#getOp()
   * @see #getExpression()
   * @generated
   */
  EAttribute getExpression_Op();

  /**
   * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getFunctionoperands <em>Functionoperands</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Functionoperands</em>'.
   * @see com.dexels.navajo.dsl.expression.navajoExpression.Expression#getFunctionoperands()
   * @see #getExpression()
   * @generated
   */
  EReference getExpression_Functionoperands();

  /**
   * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getLiteral <em>Literal</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Literal</em>'.
   * @see com.dexels.navajo.dsl.expression.navajoExpression.Expression#getLiteral()
   * @see #getExpression()
   * @generated
   */
  EAttribute getExpression_Literal();

  /**
   * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.expression.navajoExpression.Expression#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see com.dexels.navajo.dsl.expression.navajoExpression.Expression#getName()
   * @see #getExpression()
   * @generated
   */
  EAttribute getExpression_Name();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  NavajoExpressionFactory getNavajoExpressionFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals
  {
    /**
     * The meta object literal for the '{@link com.dexels.navajo.dsl.expression.navajoExpression.impl.TopLevelImpl <em>Top Level</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.dexels.navajo.dsl.expression.navajoExpression.impl.TopLevelImpl
     * @see com.dexels.navajo.dsl.expression.navajoExpression.impl.NavajoExpressionPackageImpl#getTopLevel()
     * @generated
     */
    EClass TOP_LEVEL = eINSTANCE.getTopLevel();

    /**
     * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TOP_LEVEL__EXPRESSION = eINSTANCE.getTopLevel_Expression();

    /**
     * The meta object literal for the '{@link com.dexels.navajo.dsl.expression.navajoExpression.impl.ExpressionImpl <em>Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.dexels.navajo.dsl.expression.navajoExpression.impl.ExpressionImpl
     * @see com.dexels.navajo.dsl.expression.navajoExpression.impl.NavajoExpressionPackageImpl#getExpression()
     * @generated
     */
    EClass EXPRESSION = eINSTANCE.getExpression();

    /**
     * The meta object literal for the '<em><b>Operands</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION__OPERANDS = eINSTANCE.getExpression_Operands();

    /**
     * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION__OP = eINSTANCE.getExpression_Op();

    /**
     * The meta object literal for the '<em><b>Functionoperands</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION__FUNCTIONOPERANDS = eINSTANCE.getExpression_Functionoperands();

    /**
     * The meta object literal for the '<em><b>Literal</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION__LITERAL = eINSTANCE.getExpression_Literal();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EXPRESSION__NAME = eINSTANCE.getExpression_Name();

  }

} //NavajoExpressionPackage
