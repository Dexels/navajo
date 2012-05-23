/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.workflow;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Workflow</b></em>'. <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.dexels.navajo.workflow.Workflow#getStates <em>States</em>}</li>
 * <li>{@link com.dexels.navajo.workflow.Workflow#isTransient <em>Transient
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.dexels.navajo.workflow.WorkflowPackage#getWorkflow()
 * @model
 * @generated
 */
public interface Workflow extends EObject {
	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link com.dexels.navajo.workflow.State}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>States</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see com.dexels.navajo.workflow.WorkflowPackage#getWorkflow_States()
	 * @model containment="true"
	 * @generated
	 */
	EList<State> getStates();

	/**
	 * Returns the value of the '<em><b>Transient</b></em>' attribute. The
	 * default value is <code>"false"</code>. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transient</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Transient</em>' attribute.
	 * @see #setTransient(boolean)
	 * @see com.dexels.navajo.workflow.WorkflowPackage#getWorkflow_Transient()
	 * @model default="false"
	 * @generated
	 */
	boolean isTransient();

	/**
	 * Sets the value of the '
	 * {@link com.dexels.navajo.workflow.Workflow#isTransient
	 * <em>Transient</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Transient</em>' attribute.
	 * @see #isTransient()
	 * @generated
	 */
	void setTransient(boolean value);

} // Workflow
