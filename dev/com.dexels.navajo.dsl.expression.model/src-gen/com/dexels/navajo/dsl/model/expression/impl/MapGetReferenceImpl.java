/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.dexels.navajo.dsl.model.expression.ExpressionPackage;
import com.dexels.navajo.dsl.model.expression.MapGetReference;
import com.dexels.navajo.dsl.model.expression.ReferenceParams;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Map Get Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.MapGetReferenceImpl#getPathElements <em>Path Elements</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.MapGetReferenceImpl#getReferenceParams <em>Reference Params</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MapGetReferenceImpl extends ExpressionImpl implements MapGetReference {
	/**
	 * The cached value of the '{@link #getPathElements() <em>Path Elements</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPathElements()
	 * @generated
	 * @ordered
	 */
	protected EList<String> pathElements;

	/**
	 * The cached value of the '{@link #getReferenceParams() <em>Reference Params</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferenceParams()
	 * @generated
	 * @ordered
	 */
	protected ReferenceParams referenceParams;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MapGetReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionPackage.Literals.MAP_GET_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPathElements() {
		if (pathElements == null) {
			pathElements = new EDataTypeUniqueEList<String>(String.class, this, ExpressionPackage.MAP_GET_REFERENCE__PATH_ELEMENTS);
		}
		return pathElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceParams getReferenceParams() {
		return referenceParams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReferenceParams(ReferenceParams newReferenceParams, NotificationChain msgs) {
		ReferenceParams oldReferenceParams = referenceParams;
		referenceParams = newReferenceParams;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ExpressionPackage.MAP_GET_REFERENCE__REFERENCE_PARAMS, oldReferenceParams, newReferenceParams);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReferenceParams(ReferenceParams newReferenceParams) {
		if (newReferenceParams != referenceParams) {
			NotificationChain msgs = null;
			if (referenceParams != null)
				msgs = ((InternalEObject)referenceParams).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ExpressionPackage.MAP_GET_REFERENCE__REFERENCE_PARAMS, null, msgs);
			if (newReferenceParams != null)
				msgs = ((InternalEObject)newReferenceParams).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ExpressionPackage.MAP_GET_REFERENCE__REFERENCE_PARAMS, null, msgs);
			msgs = basicSetReferenceParams(newReferenceParams, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionPackage.MAP_GET_REFERENCE__REFERENCE_PARAMS, newReferenceParams, newReferenceParams));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExpressionPackage.MAP_GET_REFERENCE__REFERENCE_PARAMS:
				return basicSetReferenceParams(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExpressionPackage.MAP_GET_REFERENCE__PATH_ELEMENTS:
				return getPathElements();
			case ExpressionPackage.MAP_GET_REFERENCE__REFERENCE_PARAMS:
				return getReferenceParams();
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
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ExpressionPackage.MAP_GET_REFERENCE__PATH_ELEMENTS:
				getPathElements().clear();
				getPathElements().addAll((Collection<? extends String>)newValue);
				return;
			case ExpressionPackage.MAP_GET_REFERENCE__REFERENCE_PARAMS:
				setReferenceParams((ReferenceParams)newValue);
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
			case ExpressionPackage.MAP_GET_REFERENCE__PATH_ELEMENTS:
				getPathElements().clear();
				return;
			case ExpressionPackage.MAP_GET_REFERENCE__REFERENCE_PARAMS:
				setReferenceParams((ReferenceParams)null);
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
			case ExpressionPackage.MAP_GET_REFERENCE__PATH_ELEMENTS:
				return pathElements != null && !pathElements.isEmpty();
			case ExpressionPackage.MAP_GET_REFERENCE__REFERENCE_PARAMS:
				return referenceParams != null;
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
		result.append(" (pathElements: ");
		result.append(pathElements);
		result.append(')');
		return result.toString();
	}

} //MapGetReferenceImpl
