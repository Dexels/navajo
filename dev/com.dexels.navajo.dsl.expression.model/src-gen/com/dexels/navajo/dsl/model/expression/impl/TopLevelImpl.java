/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.dexels.navajo.dsl.model.expression.Expression;
import com.dexels.navajo.dsl.model.expression.ExpressionPackage;
import com.dexels.navajo.dsl.model.expression.TopLevel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Top Level</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.TopLevelImpl#getToplevelExpression <em>Toplevel Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TopLevelImpl extends EObjectImpl implements TopLevel {
	/**
	 * The cached value of the '{@link #getToplevelExpression() <em>Toplevel Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getToplevelExpression()
	 * @generated
	 * @ordered
	 */
	protected Expression toplevelExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TopLevelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionPackage.Literals.TOP_LEVEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getToplevelExpression() {
		return toplevelExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetToplevelExpression(Expression newToplevelExpression, NotificationChain msgs) {
		Expression oldToplevelExpression = toplevelExpression;
		toplevelExpression = newToplevelExpression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ExpressionPackage.TOP_LEVEL__TOPLEVEL_EXPRESSION, oldToplevelExpression, newToplevelExpression);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setToplevelExpression(Expression newToplevelExpression) {
		if (newToplevelExpression != toplevelExpression) {
			NotificationChain msgs = null;
			if (toplevelExpression != null)
				msgs = ((InternalEObject)toplevelExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ExpressionPackage.TOP_LEVEL__TOPLEVEL_EXPRESSION, null, msgs);
			if (newToplevelExpression != null)
				msgs = ((InternalEObject)newToplevelExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ExpressionPackage.TOP_LEVEL__TOPLEVEL_EXPRESSION, null, msgs);
			msgs = basicSetToplevelExpression(newToplevelExpression, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionPackage.TOP_LEVEL__TOPLEVEL_EXPRESSION, newToplevelExpression, newToplevelExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExpressionPackage.TOP_LEVEL__TOPLEVEL_EXPRESSION:
				return basicSetToplevelExpression(null, msgs);
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
			case ExpressionPackage.TOP_LEVEL__TOPLEVEL_EXPRESSION:
				return getToplevelExpression();
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
			case ExpressionPackage.TOP_LEVEL__TOPLEVEL_EXPRESSION:
				setToplevelExpression((Expression)newValue);
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
			case ExpressionPackage.TOP_LEVEL__TOPLEVEL_EXPRESSION:
				setToplevelExpression((Expression)null);
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
			case ExpressionPackage.TOP_LEVEL__TOPLEVEL_EXPRESSION:
				return toplevelExpression != null;
		}
		return super.eIsSet(featureID);
	}

} //TopLevelImpl
