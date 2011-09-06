/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.dexels.navajo.dsl.model.tsl.Map;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.TslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.MapImpl#getMapName <em>Map Name</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.MapImpl#getMapClosingName <em>Map Closing Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MapImpl extends ElementImpl implements Map {
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
	protected MapImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TslPackage.Literals.MAP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMapName() {
		return mapName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapName(String newMapName) {
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
	public String getMapClosingName() {
		return mapClosingName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMapClosingName(String newMapClosingName) {
		String oldMapClosingName = mapClosingName;
		mapClosingName = newMapClosingName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.MAP__MAP_CLOSING_NAME, oldMapClosingName, mapClosingName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getRef() {
		for (PossibleExpression possibleExpression : getAttributes()) {
			if("ref".equals(possibleExpression.getKey())) {
				if(possibleExpression.getValue()!=null) {
					return possibleExpression.getValue();
				} else {
					return null;
				}
			}
		}
		return null;	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TslPackage.MAP__MAP_NAME:
				return getMapName();
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TslPackage.MAP__MAP_NAME:
				setMapName((String)newValue);
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
	public void eUnset(int featureID) {
		switch (featureID) {
			case TslPackage.MAP__MAP_NAME:
				setMapName(MAP_NAME_EDEFAULT);
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
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TslPackage.MAP__MAP_NAME:
				return MAP_NAME_EDEFAULT == null ? mapName != null : !MAP_NAME_EDEFAULT.equals(mapName);
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
	public String toString() {
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
