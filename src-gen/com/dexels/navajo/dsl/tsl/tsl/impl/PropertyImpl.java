/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.tsl.tsl.impl;

import com.dexels.navajo.dsl.tsl.tsl.ExpressionTag;
import com.dexels.navajo.dsl.tsl.tsl.PossibleExpression;
import com.dexels.navajo.dsl.tsl.tsl.Property;
import com.dexels.navajo.dsl.tsl.tsl.TslPackage;

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
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.PropertyImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.PropertyImpl#getExpressionValue <em>Expression Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyImpl extends MinimalEObjectImpl.Container implements Property
{
  /**
   * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAttributes()
   * @generated
   * @ordered
   */
  protected EList<PossibleExpression> attributes;

  /**
   * The cached value of the '{@link #getExpressionValue() <em>Expression Value</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getExpressionValue()
   * @generated
   * @ordered
   */
  protected ExpressionTag expressionValue;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PropertyImpl()
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
    return TslPackage.Literals.PROPERTY;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<PossibleExpression> getAttributes()
  {
    if (attributes == null)
    {
      attributes = new EObjectContainmentEList<PossibleExpression>(PossibleExpression.class, this, TslPackage.PROPERTY__ATTRIBUTES);
    }
    return attributes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ExpressionTag getExpressionValue()
  {
    return expressionValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetExpressionValue(ExpressionTag newExpressionValue, NotificationChain msgs)
  {
    ExpressionTag oldExpressionValue = expressionValue;
    expressionValue = newExpressionValue;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TslPackage.PROPERTY__EXPRESSION_VALUE, oldExpressionValue, newExpressionValue);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setExpressionValue(ExpressionTag newExpressionValue)
  {
    if (newExpressionValue != expressionValue)
    {
      NotificationChain msgs = null;
      if (expressionValue != null)
        msgs = ((InternalEObject)expressionValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TslPackage.PROPERTY__EXPRESSION_VALUE, null, msgs);
      if (newExpressionValue != null)
        msgs = ((InternalEObject)newExpressionValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TslPackage.PROPERTY__EXPRESSION_VALUE, null, msgs);
      msgs = basicSetExpressionValue(newExpressionValue, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.PROPERTY__EXPRESSION_VALUE, newExpressionValue, newExpressionValue));
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
      case TslPackage.PROPERTY__ATTRIBUTES:
        return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
      case TslPackage.PROPERTY__EXPRESSION_VALUE:
        return basicSetExpressionValue(null, msgs);
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
      case TslPackage.PROPERTY__ATTRIBUTES:
        return getAttributes();
      case TslPackage.PROPERTY__EXPRESSION_VALUE:
        return getExpressionValue();
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
      case TslPackage.PROPERTY__ATTRIBUTES:
        getAttributes().clear();
        getAttributes().addAll((Collection<? extends PossibleExpression>)newValue);
        return;
      case TslPackage.PROPERTY__EXPRESSION_VALUE:
        setExpressionValue((ExpressionTag)newValue);
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
      case TslPackage.PROPERTY__ATTRIBUTES:
        getAttributes().clear();
        return;
      case TslPackage.PROPERTY__EXPRESSION_VALUE:
        setExpressionValue((ExpressionTag)null);
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
      case TslPackage.PROPERTY__ATTRIBUTES:
        return attributes != null && !attributes.isEmpty();
      case TslPackage.PROPERTY__EXPRESSION_VALUE:
        return expressionValue != null;
    }
    return super.eIsSet(featureID);
  }

} //PropertyImpl
