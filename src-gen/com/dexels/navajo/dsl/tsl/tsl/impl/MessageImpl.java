/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.tsl.tsl.impl;

import com.dexels.navajo.dsl.tsl.tsl.Map;
import com.dexels.navajo.dsl.tsl.tsl.Message;
import com.dexels.navajo.dsl.tsl.tsl.PossibleExpression;
import com.dexels.navajo.dsl.tsl.tsl.Property;
import com.dexels.navajo.dsl.tsl.tsl.TslPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MessageImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MessageImpl#getMessages <em>Messages</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MessageImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MessageImpl#getMaps <em>Maps</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MessageImpl extends MinimalEObjectImpl.Container implements Message
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
   * The cached value of the '{@link #getMessages() <em>Messages</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMessages()
   * @generated
   * @ordered
   */
  protected EList<Message> messages;

  /**
   * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getProperties()
   * @generated
   * @ordered
   */
  protected EList<Property> properties;

  /**
   * The cached value of the '{@link #getMaps() <em>Maps</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMaps()
   * @generated
   * @ordered
   */
  protected EList<Map> maps;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MessageImpl()
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
    return TslPackage.Literals.MESSAGE;
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
      attributes = new EObjectContainmentEList<PossibleExpression>(PossibleExpression.class, this, TslPackage.MESSAGE__ATTRIBUTES);
    }
    return attributes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Message> getMessages()
  {
    if (messages == null)
    {
      messages = new EObjectContainmentEList<Message>(Message.class, this, TslPackage.MESSAGE__MESSAGES);
    }
    return messages;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Property> getProperties()
  {
    if (properties == null)
    {
      properties = new EObjectContainmentEList<Property>(Property.class, this, TslPackage.MESSAGE__PROPERTIES);
    }
    return properties;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Map> getMaps()
  {
    if (maps == null)
    {
      maps = new EObjectContainmentEList<Map>(Map.class, this, TslPackage.MESSAGE__MAPS);
    }
    return maps;
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
      case TslPackage.MESSAGE__ATTRIBUTES:
        return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
      case TslPackage.MESSAGE__MESSAGES:
        return ((InternalEList<?>)getMessages()).basicRemove(otherEnd, msgs);
      case TslPackage.MESSAGE__PROPERTIES:
        return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
      case TslPackage.MESSAGE__MAPS:
        return ((InternalEList<?>)getMaps()).basicRemove(otherEnd, msgs);
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
      case TslPackage.MESSAGE__ATTRIBUTES:
        return getAttributes();
      case TslPackage.MESSAGE__MESSAGES:
        return getMessages();
      case TslPackage.MESSAGE__PROPERTIES:
        return getProperties();
      case TslPackage.MESSAGE__MAPS:
        return getMaps();
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
      case TslPackage.MESSAGE__ATTRIBUTES:
        getAttributes().clear();
        getAttributes().addAll((Collection<? extends PossibleExpression>)newValue);
        return;
      case TslPackage.MESSAGE__MESSAGES:
        getMessages().clear();
        getMessages().addAll((Collection<? extends Message>)newValue);
        return;
      case TslPackage.MESSAGE__PROPERTIES:
        getProperties().clear();
        getProperties().addAll((Collection<? extends Property>)newValue);
        return;
      case TslPackage.MESSAGE__MAPS:
        getMaps().clear();
        getMaps().addAll((Collection<? extends Map>)newValue);
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
      case TslPackage.MESSAGE__ATTRIBUTES:
        getAttributes().clear();
        return;
      case TslPackage.MESSAGE__MESSAGES:
        getMessages().clear();
        return;
      case TslPackage.MESSAGE__PROPERTIES:
        getProperties().clear();
        return;
      case TslPackage.MESSAGE__MAPS:
        getMaps().clear();
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
      case TslPackage.MESSAGE__ATTRIBUTES:
        return attributes != null && !attributes.isEmpty();
      case TslPackage.MESSAGE__MESSAGES:
        return messages != null && !messages.isEmpty();
      case TslPackage.MESSAGE__PROPERTIES:
        return properties != null && !properties.isEmpty();
      case TslPackage.MESSAGE__MAPS:
        return maps != null && !maps.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //MessageImpl
