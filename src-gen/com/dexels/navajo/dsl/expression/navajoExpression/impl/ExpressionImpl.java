/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.expression.navajoExpression.impl;

import com.dexels.navajo.dsl.expression.navajoExpression.Expression;
import com.dexels.navajo.dsl.expression.navajoExpression.NavajoExpressionPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.impl.ExpressionImpl#getOperands <em>Operands</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.impl.ExpressionImpl#getOp <em>Op</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.impl.ExpressionImpl#getFunctionoperands <em>Functionoperands</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.impl.ExpressionImpl#getLiteral <em>Literal</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.expression.navajoExpression.impl.ExpressionImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExpressionImpl extends MinimalEObjectImpl.Container implements Expression
{
  /**
   * The cached value of the '{@link #getOperands() <em>Operands</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOperands()
   * @generated
   * @ordered
   */
  protected EList<Expression> operands;

  /**
   * The default value of the '{@link #getOp() <em>Op</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOp()
   * @generated
   * @ordered
   */
  protected static final String OP_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getOp() <em>Op</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOp()
   * @generated
   * @ordered
   */
  protected String op = OP_EDEFAULT;

  /**
   * The cached value of the '{@link #getFunctionoperands() <em>Functionoperands</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFunctionoperands()
   * @generated
   * @ordered
   */
  protected Expression functionoperands;

  /**
   * The default value of the '{@link #getLiteral() <em>Literal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLiteral()
   * @generated
   * @ordered
   */
  protected static final int LITERAL_EDEFAULT = 0;

  /**
   * The cached value of the '{@link #getLiteral() <em>Literal</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLiteral()
   * @generated
   * @ordered
   */
  protected int literal = LITERAL_EDEFAULT;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ExpressionImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return NavajoExpressionPackage.Literals.EXPRESSION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Expression> getOperands()
  {
    if (operands == null)
    {
      operands = new EObjectContainmentEList<Expression>(Expression.class, this, NavajoExpressionPackage.EXPRESSION__OPERANDS);
    }
    return operands;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getOp()
  {
    return op;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setOp(String newOp)
  {
    String oldOp = op;
    op = newOp;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NavajoExpressionPackage.EXPRESSION__OP, oldOp, op));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Expression getFunctionoperands()
  {
    return functionoperands;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetFunctionoperands(Expression newFunctionoperands, NotificationChain msgs)
  {
    Expression oldFunctionoperands = functionoperands;
    functionoperands = newFunctionoperands;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NavajoExpressionPackage.EXPRESSION__FUNCTIONOPERANDS, oldFunctionoperands, newFunctionoperands);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFunctionoperands(Expression newFunctionoperands)
  {
    if (newFunctionoperands != functionoperands)
    {
      NotificationChain msgs = null;
      if (functionoperands != null)
        msgs = ((InternalEObject)functionoperands).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NavajoExpressionPackage.EXPRESSION__FUNCTIONOPERANDS, null, msgs);
      if (newFunctionoperands != null)
        msgs = ((InternalEObject)newFunctionoperands).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NavajoExpressionPackage.EXPRESSION__FUNCTIONOPERANDS, null, msgs);
      msgs = basicSetFunctionoperands(newFunctionoperands, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NavajoExpressionPackage.EXPRESSION__FUNCTIONOPERANDS, newFunctionoperands, newFunctionoperands));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getLiteral()
  {
    return literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setLiteral(int newLiteral)
  {
    int oldLiteral = literal;
    literal = newLiteral;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NavajoExpressionPackage.EXPRESSION__LITERAL, oldLiteral, literal));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, NavajoExpressionPackage.EXPRESSION__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case NavajoExpressionPackage.EXPRESSION__OPERANDS:
        return ((InternalEList<?>)getOperands()).basicRemove(otherEnd, msgs);
      case NavajoExpressionPackage.EXPRESSION__FUNCTIONOPERANDS:
        return basicSetFunctionoperands(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case NavajoExpressionPackage.EXPRESSION__OPERANDS:
        return getOperands();
      case NavajoExpressionPackage.EXPRESSION__OP:
        return getOp();
      case NavajoExpressionPackage.EXPRESSION__FUNCTIONOPERANDS:
        return getFunctionoperands();
      case NavajoExpressionPackage.EXPRESSION__LITERAL:
        return getLiteral();
      case NavajoExpressionPackage.EXPRESSION__NAME:
        return getName();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case NavajoExpressionPackage.EXPRESSION__OPERANDS:
        getOperands().clear();
        getOperands().addAll((Collection<? extends Expression>)newValue);
        return;
      case NavajoExpressionPackage.EXPRESSION__OP:
        setOp((String)newValue);
        return;
      case NavajoExpressionPackage.EXPRESSION__FUNCTIONOPERANDS:
        setFunctionoperands((Expression)newValue);
        return;
      case NavajoExpressionPackage.EXPRESSION__LITERAL:
        setLiteral((Integer)newValue);
        return;
      case NavajoExpressionPackage.EXPRESSION__NAME:
        setName((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case NavajoExpressionPackage.EXPRESSION__OPERANDS:
        getOperands().clear();
        return;
      case NavajoExpressionPackage.EXPRESSION__OP:
        setOp(OP_EDEFAULT);
        return;
      case NavajoExpressionPackage.EXPRESSION__FUNCTIONOPERANDS:
        setFunctionoperands((Expression)null);
        return;
      case NavajoExpressionPackage.EXPRESSION__LITERAL:
        setLiteral(LITERAL_EDEFAULT);
        return;
      case NavajoExpressionPackage.EXPRESSION__NAME:
        setName(NAME_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case NavajoExpressionPackage.EXPRESSION__OPERANDS:
        return operands != null && !operands.isEmpty();
      case NavajoExpressionPackage.EXPRESSION__OP:
        return OP_EDEFAULT == null ? op != null : !OP_EDEFAULT.equals(op);
      case NavajoExpressionPackage.EXPRESSION__FUNCTIONOPERANDS:
        return functionoperands != null;
      case NavajoExpressionPackage.EXPRESSION__LITERAL:
        return literal != LITERAL_EDEFAULT;
      case NavajoExpressionPackage.EXPRESSION__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (op: ");
    result.append(op);
    result.append(", literal: ");
    result.append(literal);
    result.append(", name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //ExpressionImpl
