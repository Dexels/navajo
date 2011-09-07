/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.workflow.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.dexels.navajo.workflow.State;
import com.dexels.navajo.workflow.Transition;
import com.dexels.navajo.workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Transition</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.dexels.navajo.workflow.impl.TransitionImpl#getName <em>Name
 * </em>}</li>
 * <li>{@link com.dexels.navajo.workflow.impl.TransitionImpl#getTrigger <em>
 * Trigger</em>}</li>
 * <li>{@link com.dexels.navajo.workflow.impl.TransitionImpl#getCondition <em>
 * Condition</em>}</li>
 * <li>{@link com.dexels.navajo.workflow.impl.TransitionImpl#getUsername <em>
 * Username</em>}</li>
 * <li>{@link com.dexels.navajo.workflow.impl.TransitionImpl#getFrom <em>From
 * </em>}</li>
 * <li>{@link com.dexels.navajo.workflow.impl.TransitionImpl#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TransitionImpl extends EObjectImpl implements Transition {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTrigger() <em>Trigger</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrigger()
	 * @generated
	 * @ordered
	 */
	protected static final String TRIGGER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTrigger() <em>Trigger</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrigger()
	 * @generated
	 * @ordered
	 */
	protected String trigger = TRIGGER_EDEFAULT;

	/**
	 * The default value of the '{@link #getCondition() <em>Condition</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected static final String CONDITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected String condition = CONDITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getUsername() <em>Username</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected static final String USERNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUsername() <em>Username</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected String username = USERNAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected State from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected State to;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WorkflowPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					WorkflowPackage.TRANSITION__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getTrigger() {
		return trigger;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTrigger(String newTrigger) {
		String oldTrigger = trigger;
		trigger = newTrigger;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					WorkflowPackage.TRANSITION__TRIGGER, oldTrigger, trigger));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setCondition(String newCondition) {
		String oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					WorkflowPackage.TRANSITION__CONDITION, oldCondition,
					condition));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setUsername(String newUsername) {
		String oldUsername = username;
		username = newUsername;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					WorkflowPackage.TRANSITION__USERNAME, oldUsername, username));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public State getFrom() {
		if (from != null && from.eIsProxy()) {
			InternalEObject oldFrom = (InternalEObject) from;
			from = (State) eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							WorkflowPackage.TRANSITION__FROM, oldFrom, from));
				}
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public State basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFrom(State newFrom) {
		State oldFrom = from;
		from = newFrom;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					WorkflowPackage.TRANSITION__FROM, oldFrom, from));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public State getTo() {
		if (to != null && to.eIsProxy()) {
			InternalEObject oldTo = (InternalEObject) to;
			to = (State) eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							WorkflowPackage.TRANSITION__TO, oldTo, to));
				}
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public State basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setTo(State newTo) {
		State oldTo = to;
		to = newTo;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					WorkflowPackage.TRANSITION__TO, oldTo, to));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case WorkflowPackage.TRANSITION__NAME:
			return getName();
		case WorkflowPackage.TRANSITION__TRIGGER:
			return getTrigger();
		case WorkflowPackage.TRANSITION__CONDITION:
			return getCondition();
		case WorkflowPackage.TRANSITION__USERNAME:
			return getUsername();
		case WorkflowPackage.TRANSITION__FROM:
			if (resolve) {
				return getFrom();
			}
			return basicGetFrom();
		case WorkflowPackage.TRANSITION__TO:
			if (resolve) {
				return getTo();
			}
			return basicGetTo();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case WorkflowPackage.TRANSITION__NAME:
			setName((String) newValue);
			return;
		case WorkflowPackage.TRANSITION__TRIGGER:
			setTrigger((String) newValue);
			return;
		case WorkflowPackage.TRANSITION__CONDITION:
			setCondition((String) newValue);
			return;
		case WorkflowPackage.TRANSITION__USERNAME:
			setUsername((String) newValue);
			return;
		case WorkflowPackage.TRANSITION__FROM:
			setFrom((State) newValue);
			return;
		case WorkflowPackage.TRANSITION__TO:
			setTo((State) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case WorkflowPackage.TRANSITION__NAME:
			setName(NAME_EDEFAULT);
			return;
		case WorkflowPackage.TRANSITION__TRIGGER:
			setTrigger(TRIGGER_EDEFAULT);
			return;
		case WorkflowPackage.TRANSITION__CONDITION:
			setCondition(CONDITION_EDEFAULT);
			return;
		case WorkflowPackage.TRANSITION__USERNAME:
			setUsername(USERNAME_EDEFAULT);
			return;
		case WorkflowPackage.TRANSITION__FROM:
			setFrom((State) null);
			return;
		case WorkflowPackage.TRANSITION__TO:
			setTo((State) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case WorkflowPackage.TRANSITION__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT
					.equals(name);
		case WorkflowPackage.TRANSITION__TRIGGER:
			return TRIGGER_EDEFAULT == null ? trigger != null
					: !TRIGGER_EDEFAULT.equals(trigger);
		case WorkflowPackage.TRANSITION__CONDITION:
			return CONDITION_EDEFAULT == null ? condition != null
					: !CONDITION_EDEFAULT.equals(condition);
		case WorkflowPackage.TRANSITION__USERNAME:
			return USERNAME_EDEFAULT == null ? username != null
					: !USERNAME_EDEFAULT.equals(username);
		case WorkflowPackage.TRANSITION__FROM:
			return from != null;
		case WorkflowPackage.TRANSITION__TO:
			return to != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", trigger: ");
		result.append(trigger);
		result.append(", condition: ");
		result.append(condition);
		result.append(", username: ");
		result.append(username);
		result.append(')');
		return result.toString();
	}

} // TransitionImpl
