/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.dexels.navajo.dsl.model.expression.Expression;
import com.dexels.navajo.dsl.model.expression.ExpressionPackage;
import com.dexels.navajo.dsl.model.expression.ReferenceParams;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference Params</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ReferenceParamsImpl#getGetterParams <em>Getter Params</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReferenceParamsImpl extends EObjectImpl implements ReferenceParams {
	/**
	 * The cached value of the '{@link #getGetterParams() <em>Getter Params</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetterParams()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> getterParams;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReferenceParamsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionPackage.Literals.REFERENCE_PARAMS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getGetterParams() {
		if (getterParams == null) {
			getterParams = new EObjectContainmentEList<Expression>(Expression.class, this, ExpressionPackage.REFERENCE_PARAMS__GETTER_PARAMS);
		}
		return getterParams;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExpressionPackage.REFERENCE_PARAMS__GETTER_PARAMS:
				return ((InternalEList<?>)getGetterParams()).basicRemove(otherEnd, msgs);
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
			case ExpressionPackage.REFERENCE_PARAMS__GETTER_PARAMS:
				return getGetterParams();
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
			case ExpressionPackage.REFERENCE_PARAMS__GETTER_PARAMS:
				getGetterParams().clear();
				getGetterParams().addAll((Collection<? extends Expression>)newValue);
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
			case ExpressionPackage.REFERENCE_PARAMS__GETTER_PARAMS:
				getGetterParams().clear();
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
			case ExpressionPackage.REFERENCE_PARAMS__GETTER_PARAMS:
				return getterParams != null && !getterParams.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ReferenceParamsImpl
