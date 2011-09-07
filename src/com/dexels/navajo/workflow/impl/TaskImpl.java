/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.workflow.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.dexels.navajo.workflow.Task;
import com.dexels.navajo.workflow.WorkflowPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Task</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.dexels.navajo.workflow.impl.TaskImpl#getNavajo <em>Navajo
 * </em>}</li>
 * <li>{@link com.dexels.navajo.workflow.impl.TaskImpl#getService <em>Service
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TaskImpl extends EObjectImpl implements Task {
	/**
	 * The default value of the '{@link #getNavajo() <em>Navajo</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNavajo()
	 * @generated
	 * @ordered
	 */
	protected static final String NAVAJO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNavajo() <em>Navajo</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getNavajo()
	 * @generated
	 * @ordered
	 */
	protected String navajo = NAVAJO_EDEFAULT;

	/**
	 * The default value of the '{@link #getService() <em>Service</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getService()
	 * @generated
	 * @ordered
	 */
	protected static final String SERVICE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getService() <em>Service</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getService()
	 * @generated
	 * @ordered
	 */
	protected String service = SERVICE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return WorkflowPackage.Literals.TASK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getNavajo() {
		return navajo;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setNavajo(String newNavajo) {
		String oldNavajo = navajo;
		navajo = newNavajo;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					WorkflowPackage.TASK__NAVAJO, oldNavajo, navajo));
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getService() {
		return service;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setService(String newService) {
		String oldService = service;
		service = newService;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET,
					WorkflowPackage.TASK__SERVICE, oldService, service));
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
		case WorkflowPackage.TASK__NAVAJO:
			return getNavajo();
		case WorkflowPackage.TASK__SERVICE:
			return getService();
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
		case WorkflowPackage.TASK__NAVAJO:
			setNavajo((String) newValue);
			return;
		case WorkflowPackage.TASK__SERVICE:
			setService((String) newValue);
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
		case WorkflowPackage.TASK__NAVAJO:
			setNavajo(NAVAJO_EDEFAULT);
			return;
		case WorkflowPackage.TASK__SERVICE:
			setService(SERVICE_EDEFAULT);
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
		case WorkflowPackage.TASK__NAVAJO:
			return NAVAJO_EDEFAULT == null ? navajo != null : !NAVAJO_EDEFAULT
					.equals(navajo);
		case WorkflowPackage.TASK__SERVICE:
			return SERVICE_EDEFAULT == null ? service != null
					: !SERVICE_EDEFAULT.equals(service);
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
		result.append(" (navajo: ");
		result.append(navajo);
		result.append(", service: ");
		result.append(service);
		result.append(')');
		return result.toString();
	}

} // TaskImpl
