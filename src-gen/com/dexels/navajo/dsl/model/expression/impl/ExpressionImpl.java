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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import com.dexels.navajo.dsl.model.expression.Expression;
import com.dexels.navajo.dsl.model.expression.ExpressionPackage;
import com.dexels.navajo.dsl.model.expression.PathSequence;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl#getOperations <em>Operations</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl#getSubliteral <em>Subliteral</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl#getValueString <em>Value String</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl#getExpressionType <em>Expression Type</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl#getPathSequence <em>Path Sequence</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl#getExistsPathSequence <em>Exists Path Sequence</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExpressionImpl extends EObjectImpl implements Expression {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> parameters;

	/**
	 * The cached value of the '{@link #getOperations() <em>Operations</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperations()
	 * @generated
	 * @ordered
	 */
	protected EList<String> operations;

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<String> elements;

	/**
	 * The cached value of the '{@link #getSubliteral() <em>Subliteral</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubliteral()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> subliteral;

	/**
	 * The default value of the '{@link #getValueString() <em>Value String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueString()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValueString() <em>Value String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueString()
	 * @generated
	 * @ordered
	 */
	protected String valueString = VALUE_STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #getExpressionType() <em>Expression Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpressionType()
	 * @generated
	 * @ordered
	 */
	protected static final String EXPRESSION_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExpressionType() <em>Expression Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpressionType()
	 * @generated
	 * @ordered
	 */
	protected String expressionType = EXPRESSION_TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPathSequence() <em>Path Sequence</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPathSequence()
	 * @generated
	 * @ordered
	 */
	protected PathSequence pathSequence;

	/**
	 * The cached value of the '{@link #getExistsPathSequence() <em>Exists Path Sequence</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExistsPathSequence()
	 * @generated
	 * @ordered
	 */
	protected PathSequence existsPathSequence;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExpressionPackage.Literals.EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getParent() {
		if (eContainerFeatureID() != ExpressionPackage.EXPRESSION__PARENT) return null;
		return (Expression)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(Expression newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, ExpressionPackage.EXPRESSION__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Expression newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != ExpressionPackage.EXPRESSION__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, ExpressionPackage.EXPRESSION__PARAMETERS, Expression.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionPackage.EXPRESSION__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentWithInverseEList<Expression>(Expression.class, this, ExpressionPackage.EXPRESSION__PARAMETERS, ExpressionPackage.EXPRESSION__PARENT);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getOperations() {
		if (operations == null) {
			operations = new EDataTypeUniqueEList<String>(String.class, this, ExpressionPackage.EXPRESSION__OPERATIONS);
		}
		return operations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getElements() {
		if (elements == null) {
			elements = new EDataTypeUniqueEList<String>(String.class, this, ExpressionPackage.EXPRESSION__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getSubliteral() {
		if (subliteral == null) {
			subliteral = new EObjectResolvingEList<Expression>(Expression.class, this, ExpressionPackage.EXPRESSION__SUBLITERAL);
		}
		return subliteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValueString() {
		return valueString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueString(String newValueString) {
		String oldValueString = valueString;
		valueString = newValueString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionPackage.EXPRESSION__VALUE_STRING, oldValueString, valueString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExpressionType() {
		return expressionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpressionType(String newExpressionType) {
		String oldExpressionType = expressionType;
		expressionType = newExpressionType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionPackage.EXPRESSION__EXPRESSION_TYPE, oldExpressionType, expressionType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PathSequence getPathSequence() {
		if (pathSequence != null && pathSequence.eIsProxy()) {
			InternalEObject oldPathSequence = (InternalEObject)pathSequence;
			pathSequence = (PathSequence)eResolveProxy(oldPathSequence);
			if (pathSequence != oldPathSequence) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ExpressionPackage.EXPRESSION__PATH_SEQUENCE, oldPathSequence, pathSequence));
			}
		}
		return pathSequence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PathSequence basicGetPathSequence() {
		return pathSequence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPathSequence(PathSequence newPathSequence) {
		PathSequence oldPathSequence = pathSequence;
		pathSequence = newPathSequence;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionPackage.EXPRESSION__PATH_SEQUENCE, oldPathSequence, pathSequence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PathSequence getExistsPathSequence() {
		if (existsPathSequence != null && existsPathSequence.eIsProxy()) {
			InternalEObject oldExistsPathSequence = (InternalEObject)existsPathSequence;
			existsPathSequence = (PathSequence)eResolveProxy(oldExistsPathSequence);
			if (existsPathSequence != oldExistsPathSequence) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ExpressionPackage.EXPRESSION__EXISTS_PATH_SEQUENCE, oldExistsPathSequence, existsPathSequence));
			}
		}
		return existsPathSequence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PathSequence basicGetExistsPathSequence() {
		return existsPathSequence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExistsPathSequence(PathSequence newExistsPathSequence) {
		PathSequence oldExistsPathSequence = existsPathSequence;
		existsPathSequence = newExistsPathSequence;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ExpressionPackage.EXPRESSION__EXISTS_PATH_SEQUENCE, oldExistsPathSequence, existsPathSequence));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExpressionPackage.EXPRESSION__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((Expression)otherEnd, msgs);
			case ExpressionPackage.EXPRESSION__PARAMETERS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getParameters()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ExpressionPackage.EXPRESSION__PARENT:
				return basicSetParent(null, msgs);
			case ExpressionPackage.EXPRESSION__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ExpressionPackage.EXPRESSION__PARENT:
				return eInternalContainer().eInverseRemove(this, ExpressionPackage.EXPRESSION__PARAMETERS, Expression.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ExpressionPackage.EXPRESSION__PARENT:
				return getParent();
			case ExpressionPackage.EXPRESSION__PARAMETERS:
				return getParameters();
			case ExpressionPackage.EXPRESSION__OPERATIONS:
				return getOperations();
			case ExpressionPackage.EXPRESSION__ELEMENTS:
				return getElements();
			case ExpressionPackage.EXPRESSION__SUBLITERAL:
				return getSubliteral();
			case ExpressionPackage.EXPRESSION__VALUE_STRING:
				return getValueString();
			case ExpressionPackage.EXPRESSION__EXPRESSION_TYPE:
				return getExpressionType();
			case ExpressionPackage.EXPRESSION__PATH_SEQUENCE:
				if (resolve) return getPathSequence();
				return basicGetPathSequence();
			case ExpressionPackage.EXPRESSION__EXISTS_PATH_SEQUENCE:
				if (resolve) return getExistsPathSequence();
				return basicGetExistsPathSequence();
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
			case ExpressionPackage.EXPRESSION__PARENT:
				setParent((Expression)newValue);
				return;
			case ExpressionPackage.EXPRESSION__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Expression>)newValue);
				return;
			case ExpressionPackage.EXPRESSION__OPERATIONS:
				getOperations().clear();
				getOperations().addAll((Collection<? extends String>)newValue);
				return;
			case ExpressionPackage.EXPRESSION__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends String>)newValue);
				return;
			case ExpressionPackage.EXPRESSION__SUBLITERAL:
				getSubliteral().clear();
				getSubliteral().addAll((Collection<? extends Expression>)newValue);
				return;
			case ExpressionPackage.EXPRESSION__VALUE_STRING:
				setValueString((String)newValue);
				return;
			case ExpressionPackage.EXPRESSION__EXPRESSION_TYPE:
				setExpressionType((String)newValue);
				return;
			case ExpressionPackage.EXPRESSION__PATH_SEQUENCE:
				setPathSequence((PathSequence)newValue);
				return;
			case ExpressionPackage.EXPRESSION__EXISTS_PATH_SEQUENCE:
				setExistsPathSequence((PathSequence)newValue);
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
			case ExpressionPackage.EXPRESSION__PARENT:
				setParent((Expression)null);
				return;
			case ExpressionPackage.EXPRESSION__PARAMETERS:
				getParameters().clear();
				return;
			case ExpressionPackage.EXPRESSION__OPERATIONS:
				getOperations().clear();
				return;
			case ExpressionPackage.EXPRESSION__ELEMENTS:
				getElements().clear();
				return;
			case ExpressionPackage.EXPRESSION__SUBLITERAL:
				getSubliteral().clear();
				return;
			case ExpressionPackage.EXPRESSION__VALUE_STRING:
				setValueString(VALUE_STRING_EDEFAULT);
				return;
			case ExpressionPackage.EXPRESSION__EXPRESSION_TYPE:
				setExpressionType(EXPRESSION_TYPE_EDEFAULT);
				return;
			case ExpressionPackage.EXPRESSION__PATH_SEQUENCE:
				setPathSequence((PathSequence)null);
				return;
			case ExpressionPackage.EXPRESSION__EXISTS_PATH_SEQUENCE:
				setExistsPathSequence((PathSequence)null);
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
			case ExpressionPackage.EXPRESSION__PARENT:
				return getParent() != null;
			case ExpressionPackage.EXPRESSION__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case ExpressionPackage.EXPRESSION__OPERATIONS:
				return operations != null && !operations.isEmpty();
			case ExpressionPackage.EXPRESSION__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case ExpressionPackage.EXPRESSION__SUBLITERAL:
				return subliteral != null && !subliteral.isEmpty();
			case ExpressionPackage.EXPRESSION__VALUE_STRING:
				return VALUE_STRING_EDEFAULT == null ? valueString != null : !VALUE_STRING_EDEFAULT.equals(valueString);
			case ExpressionPackage.EXPRESSION__EXPRESSION_TYPE:
				return EXPRESSION_TYPE_EDEFAULT == null ? expressionType != null : !EXPRESSION_TYPE_EDEFAULT.equals(expressionType);
			case ExpressionPackage.EXPRESSION__PATH_SEQUENCE:
				return pathSequence != null;
			case ExpressionPackage.EXPRESSION__EXISTS_PATH_SEQUENCE:
				return existsPathSequence != null;
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
		result.append(" (operations: ");
		result.append(operations);
		result.append(", elements: ");
		result.append(elements);
		result.append(", valueString: ");
		result.append(valueString);
		result.append(", expressionType: ");
		result.append(expressionType);
		result.append(')');
		return result.toString();
	}

} //ExpressionImpl
