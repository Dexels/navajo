/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.dexels.navajo.dsl.model.expression.ExpressionPackage;
import com.dexels.navajo.dsl.model.expression.TmlReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tml Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.TmlReferenceImpl#getPathElements <em>Path Elements</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.TmlReferenceImpl#isAbsolute <em>Absolute</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.TmlReferenceImpl#isParam <em>Param</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TmlReferenceImpl extends ExpressionImpl implements TmlReference {
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
	 * The default value of the '{@link #isAbsolute() <em>Absolute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbsolute()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ABSOLUTE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAbsolute() <em>Absolute</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAbsolute()
	 * @generated
	 * @ordered
	 */
	protected boolean absolute = ABSOLUTE_EDEFAULT;

	/**
	 * The default value of the '{@link #isParam() <em>Param</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isParam()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PARAM_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isParam() <em>Param</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isParam()
	 * @generated
	 * @ordered
	 */
	protected boolean param = PARAM_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TmlReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionPackage.Literals.TML_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPathElements() {
		if (pathElements == null) {
			pathElements = new EDataTypeUniqueEList<String>(String.class, this, ExpressionPackage.TML_REFERENCE__PATH_ELEMENTS);
		}
		return pathElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAbsolute() {
		return absolute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbsolute(boolean newAbsolute) {
		boolean oldAbsolute = absolute;
		absolute = newAbsolute;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionPackage.TML_REFERENCE__ABSOLUTE, oldAbsolute, absolute));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isParam() {
		return param;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParam(boolean newParam) {
		boolean oldParam = param;
		param = newParam;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionPackage.TML_REFERENCE__PARAM, oldParam, param));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExpressionPackage.TML_REFERENCE__PATH_ELEMENTS:
				return getPathElements();
			case ExpressionPackage.TML_REFERENCE__ABSOLUTE:
				return isAbsolute();
			case ExpressionPackage.TML_REFERENCE__PARAM:
				return isParam();
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
			case ExpressionPackage.TML_REFERENCE__PATH_ELEMENTS:
				getPathElements().clear();
				getPathElements().addAll((Collection<? extends String>)newValue);
				return;
			case ExpressionPackage.TML_REFERENCE__ABSOLUTE:
				setAbsolute((Boolean)newValue);
				return;
			case ExpressionPackage.TML_REFERENCE__PARAM:
				setParam((Boolean)newValue);
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
			case ExpressionPackage.TML_REFERENCE__PATH_ELEMENTS:
				getPathElements().clear();
				return;
			case ExpressionPackage.TML_REFERENCE__ABSOLUTE:
				setAbsolute(ABSOLUTE_EDEFAULT);
				return;
			case ExpressionPackage.TML_REFERENCE__PARAM:
				setParam(PARAM_EDEFAULT);
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
			case ExpressionPackage.TML_REFERENCE__PATH_ELEMENTS:
				return pathElements != null && !pathElements.isEmpty();
			case ExpressionPackage.TML_REFERENCE__ABSOLUTE:
				return absolute != ABSOLUTE_EDEFAULT;
			case ExpressionPackage.TML_REFERENCE__PARAM:
				return param != PARAM_EDEFAULT;
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
		result.append(", absolute: ");
		result.append(absolute);
		result.append(", param: ");
		result.append(param);
		result.append(')');
		return result.toString();
	}

} //TmlReferenceImpl
