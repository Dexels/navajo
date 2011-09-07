/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.workflow;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Task</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.dexels.navajo.workflow.Task#getNavajo <em>Navajo</em>}</li>
 * <li>{@link com.dexels.navajo.workflow.Task#getService <em>Service</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.dexels.navajo.workflow.WorkflowPackage#getTask()
 * @model
 * @generated
 */
public interface Task extends EObject {
	/**
	 * Returns the value of the '<em><b>Navajo</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Navajo</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Navajo</em>' attribute.
	 * @see #setNavajo(String)
	 * @see com.dexels.navajo.workflow.WorkflowPackage#getTask_Navajo()
	 * @model
	 * @generated
	 */
	String getNavajo();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.workflow.Task#getNavajo
	 * <em>Navajo</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Navajo</em>' attribute.
	 * @see #getNavajo()
	 * @generated
	 */
	void setNavajo(String value);

	/**
	 * Returns the value of the '<em><b>Service</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Service</em>' attribute.
	 * @see #setService(String)
	 * @see com.dexels.navajo.workflow.WorkflowPackage#getTask_Service()
	 * @model
	 * @generated
	 */
	String getService();

	/**
	 * Sets the value of the '{@link com.dexels.navajo.workflow.Task#getService
	 * <em>Service</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Service</em>' attribute.
	 * @see #getService()
	 * @generated
	 */
	void setService(String value);

} // Task
