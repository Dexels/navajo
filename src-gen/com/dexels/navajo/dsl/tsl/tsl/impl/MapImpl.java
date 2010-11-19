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
 * An implementation of the model object '<em><b>Map</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl#getMapName <em>Map Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl#getMessages <em>Messages</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl#getMaps <em>Maps</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl#getMapClosingName <em>Map Closing Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MapImpl extends MinimalEObjectImpl.Container implements Map
{
  /**
   * The default value of the '{@link #getMapName() <em>Map Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMapName()
   * @generated
   * @ordered
   */
  protected static final String MAP_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMapName() <em>Map Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMapName()
   * @generated
   * @ordered
   */
  protected String mapName = MAP_NAME_EDEFAULT;

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
   * The default value of the '{@link #getMapClosingName() <em>Map Closing Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMapClosingName()
   * @generated
   * @ordered
   */
  protected static final String MAP_CLOSING_NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getMapClosingName() <em>Map Closing Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMapClosingName()
   * @generated
   * @ordered
   */
  protected String mapClosingName = MAP_CLOSING_NAME_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected MapImpl()
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
    return TslPackage.Literals.MAP;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getMapName()
  {
    return mapName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMapName(String newMapName)
  {
    String oldMapName = mapName;
    mapName = newMapName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.MAP__MAP_NAME, oldMapName, mapName));
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
      attributes = new EObjectContainmentEList<PossibleExpression>(PossibleExpression.class, this, TslPackage.MAP__ATTRIBUTES);
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
      messages = new EObjectContainmentEList<Message>(Message.class, this, TslPackage.MAP__MESSAGES);
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
      properties = new EObjectContainmentEList<Property>(Property.class, this, TslPackage.MAP__PROPERTIES);
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
      maps = new EObjectContainmentEList<Map>(Map.class, this, TslPackage.MAP__MAPS);
    }
    return maps;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getMapClosingName()
  {
    return mapClosingName;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setMapClosingName(String newMapClosingName)
  {
    String oldMapClosingName = mapClosingName;
    mapClosingName = newMapClosingName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.MAP__MAP_CLOSING_NAME, oldMapClosingName, mapClosingName));
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
      case TslPackage.MAP__ATTRIBUTES:
        return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
      case TslPackage.MAP__MESSAGES:
        return ((InternalEList<?>)getMessages()).basicRemove(otherEnd, msgs);
      case TslPackage.MAP__PROPERTIES:
        return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
      case TslPackage.MAP__MAPS:
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
      case TslPackage.MAP__MAP_NAME:
        return getMapName();
      case TslPackage.MAP__ATTRIBUTES:
        return getAttributes();
      case TslPackage.MAP__MESSAGES:
        return getMessages();
      case TslPackage.MAP__PROPERTIES:
        return getProperties();
      case TslPackage.MAP__MAPS:
        return getMaps();
      case TslPackage.MAP__MAP_CLOSING_NAME:
        return getMapClosingName();
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
      case TslPackage.MAP__MAP_NAME:
        setMapName((String)newValue);
        return;
      case TslPackage.MAP__ATTRIBUTES:
        getAttributes().clear();
        getAttributes().addAll((Collection<? extends PossibleExpression>)newValue);
        return;
      case TslPackage.MAP__MESSAGES:
        getMessages().clear();
        getMessages().addAll((Collection<? extends Message>)newValue);
        return;
      case TslPackage.MAP__PROPERTIES:
        getProperties().clear();
        getProperties().addAll((Collection<? extends Property>)newValue);
        return;
      case TslPackage.MAP__MAPS:
        getMaps().clear();
        getMaps().addAll((Collection<? extends Map>)newValue);
        return;
      case TslPackage.MAP__MAP_CLOSING_NAME:
        setMapClosingName((String)newValue);
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
      case TslPackage.MAP__MAP_NAME:
        setMapName(MAP_NAME_EDEFAULT);
        return;
      case TslPackage.MAP__ATTRIBUTES:
        getAttributes().clear();
        return;
      case TslPackage.MAP__MESSAGES:
        getMessages().clear();
        return;
      case TslPackage.MAP__PROPERTIES:
        getProperties().clear();
        return;
      case TslPackage.MAP__MAPS:
        getMaps().clear();
        return;
      case TslPackage.MAP__MAP_CLOSING_NAME:
        setMapClosingName(MAP_CLOSING_NAME_EDEFAULT);
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
      case TslPackage.MAP__MAP_NAME:
        return MAP_NAME_EDEFAULT == null ? mapName != null : !MAP_NAME_EDEFAULT.equals(mapName);
      case TslPackage.MAP__ATTRIBUTES:
        return attributes != null && !attributes.isEmpty();
      case TslPackage.MAP__MESSAGES:
        return messages != null && !messages.isEmpty();
      case TslPackage.MAP__PROPERTIES:
        return properties != null && !properties.isEmpty();
      case TslPackage.MAP__MAPS:
        return maps != null && !maps.isEmpty();
      case TslPackage.MAP__MAP_CLOSING_NAME:
        return MAP_CLOSING_NAME_EDEFAULT == null ? mapClosingName != null : !MAP_CLOSING_NAME_EDEFAULT.equals(mapClosingName);
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
    result.append(" (mapName: ");
    result.append(mapName);
    result.append(", mapClosingName: ");
    result.append(mapClosingName);
    result.append(')');
    return result.toString();
  }

} //MapImpl
