/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.expression.navajoExpression.impl;

import com.dexels.navajo.dsl.expression.navajoExpression.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NavajoExpressionFactoryImpl extends EFactoryImpl implements NavajoExpressionFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static NavajoExpressionFactory init()
  {
    try
    {
      NavajoExpressionFactory theNavajoExpressionFactory = (NavajoExpressionFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.dexels.com/navajo/dsl/expression/NavajoExpression"); 
      if (theNavajoExpressionFactory != null)
      {
        return theNavajoExpressionFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new NavajoExpressionFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NavajoExpressionFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case NavajoExpressionPackage.TOP_LEVEL: return createTopLevel();
      case NavajoExpressionPackage.EXPRESSION: return createExpression();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TopLevel createTopLevel()
  {
    TopLevelImpl topLevel = new TopLevelImpl();
    return topLevel;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression createExpression()
  {
    ExpressionImpl expression = new ExpressionImpl();
    return expression;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NavajoExpressionPackage getNavajoExpressionPackage()
  {
    return (NavajoExpressionPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static NavajoExpressionPackage getPackage()
  {
    return NavajoExpressionPackage.eINSTANCE;
  }

} //NavajoExpressionFactoryImpl
