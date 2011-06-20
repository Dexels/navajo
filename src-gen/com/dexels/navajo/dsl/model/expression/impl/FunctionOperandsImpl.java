/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.dexels.navajo.dsl.model.expression.Expression;
import com.dexels.navajo.dsl.model.expression.ExpressionPackage;
import com.dexels.navajo.dsl.model.expression.FunctionOperands;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Operands</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.FunctionOperandsImpl#getParamList <em>Param List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionOperandsImpl extends ExpressionImpl implements FunctionOperands {
	/**
	 * The cached value of the '{@link #getParamList() <em>Param List</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParamList()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> paramList;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionOperandsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionPackage.Literals.FUNCTION_OPERANDS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getParamList() {
		if (paramList == null) {
			paramList = new EObjectResolvingEList<Expression>(Expression.class, this, ExpressionPackage.FUNCTION_OPERANDS__PARAM_LIST);
		}
		return paramList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExpressionPackage.FUNCTION_OPERANDS__PARAM_LIST:
				return getParamList();
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
			case ExpressionPackage.FUNCTION_OPERANDS__PARAM_LIST:
				getParamList().clear();
				getParamList().addAll((Collection<? extends Expression>)newValue);
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
			case ExpressionPackage.FUNCTION_OPERANDS__PARAM_LIST:
				getParamList().clear();
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
			case ExpressionPackage.FUNCTION_OPERANDS__PARAM_LIST:
				return paramList != null && !paramList.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //FunctionOperandsImpl
