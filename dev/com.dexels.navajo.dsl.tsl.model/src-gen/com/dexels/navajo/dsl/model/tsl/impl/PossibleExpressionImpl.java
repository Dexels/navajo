/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.dexels.navajo.dsl.model.expression.TopLevel;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.TslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Possible Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.PossibleExpressionImpl#getKey <em>Key</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.PossibleExpressionImpl#getExpressionValue <em>Expression Value</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.PossibleExpressionImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.PossibleExpressionImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.PossibleExpressionImpl#getEndToken <em>End Token</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PossibleExpressionImpl extends EObjectImpl implements PossibleExpression {
	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExpressionValue() <em>Expression Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpressionValue()
	 * @generated
	 * @ordered
	 */
	protected TopLevel expressionValue;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMESPACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected String namespace = NAMESPACE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndToken() <em>End Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndToken()
	 * @generated
	 * @ordered
	 */
	protected static final String END_TOKEN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEndToken() <em>End Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndToken()
	 * @generated
	 * @ordered
	 */
	protected String endToken = END_TOKEN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PossibleExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TslPackage.Literals.POSSIBLE_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.POSSIBLE_EXPRESSION__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TopLevel getExpressionValue() {
		return expressionValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpressionValue(TopLevel newExpressionValue, NotificationChain msgs) {
		TopLevel oldExpressionValue = expressionValue;
		expressionValue = newExpressionValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TslPackage.POSSIBLE_EXPRESSION__EXPRESSION_VALUE, oldExpressionValue, newExpressionValue);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpressionValue(TopLevel newExpressionValue) {
		if (newExpressionValue != expressionValue) {
			NotificationChain msgs = null;
			if (expressionValue != null)
				msgs = ((InternalEObject)expressionValue).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TslPackage.POSSIBLE_EXPRESSION__EXPRESSION_VALUE, null, msgs);
			if (newExpressionValue != null)
				msgs = ((InternalEObject)newExpressionValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TslPackage.POSSIBLE_EXPRESSION__EXPRESSION_VALUE, null, msgs);
			msgs = basicSetExpressionValue(newExpressionValue, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.POSSIBLE_EXPRESSION__EXPRESSION_VALUE, newExpressionValue, newExpressionValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.POSSIBLE_EXPRESSION__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespace(String newNamespace) {
		String oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.POSSIBLE_EXPRESSION__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEndToken() {
		return endToken;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndToken(String newEndToken) {
		String oldEndToken = endToken;
		endToken = newEndToken;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.POSSIBLE_EXPRESSION__END_TOKEN, oldEndToken, endToken));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isExpression() {
		return(getExpressionValue()!=null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TslPackage.POSSIBLE_EXPRESSION__EXPRESSION_VALUE:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TslPackage.POSSIBLE_EXPRESSION__KEY:
				return getKey();
			case TslPackage.POSSIBLE_EXPRESSION__EXPRESSION_VALUE:
				return getExpressionValue();
			case TslPackage.POSSIBLE_EXPRESSION__VALUE:
				return getValue();
			case TslPackage.POSSIBLE_EXPRESSION__NAMESPACE:
				return getNamespace();
			case TslPackage.POSSIBLE_EXPRESSION__END_TOKEN:
				return getEndToken();
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
			case TslPackage.POSSIBLE_EXPRESSION__KEY:
				setKey((String)newValue);
				return;
			case TslPackage.POSSIBLE_EXPRESSION__EXPRESSION_VALUE:
				setExpressionValue((TopLevel)newValue);
				return;
			case TslPackage.POSSIBLE_EXPRESSION__VALUE:
				setValue((String)newValue);
				return;
			case TslPackage.POSSIBLE_EXPRESSION__NAMESPACE:
				setNamespace((String)newValue);
				return;
			case TslPackage.POSSIBLE_EXPRESSION__END_TOKEN:
				setEndToken((String)newValue);
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
			case TslPackage.POSSIBLE_EXPRESSION__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case TslPackage.POSSIBLE_EXPRESSION__EXPRESSION_VALUE:
				setExpressionValue((TopLevel)null);
				return;
			case TslPackage.POSSIBLE_EXPRESSION__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case TslPackage.POSSIBLE_EXPRESSION__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
				return;
			case TslPackage.POSSIBLE_EXPRESSION__END_TOKEN:
				setEndToken(END_TOKEN_EDEFAULT);
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
			case TslPackage.POSSIBLE_EXPRESSION__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case TslPackage.POSSIBLE_EXPRESSION__EXPRESSION_VALUE:
				return expressionValue != null;
			case TslPackage.POSSIBLE_EXPRESSION__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case TslPackage.POSSIBLE_EXPRESSION__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
			case TslPackage.POSSIBLE_EXPRESSION__END_TOKEN:
				return END_TOKEN_EDEFAULT == null ? endToken != null : !END_TOKEN_EDEFAULT.equals(endToken);
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
		result.append(" (key: ");
		result.append(key);
		result.append(", value: ");
		result.append(value);
		result.append(", namespace: ");
		result.append(namespace);
		result.append(", endToken: ");
		result.append(endToken);
		result.append(')');
		return result.toString();
	}

} //PossibleExpressionImpl
