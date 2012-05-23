/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.workflow;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see com.dexels.navajo.workflow.WorkflowFactory
 * @model kind="package"
 * @generated
 */
public interface WorkflowPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "workflow";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.dexels.com/navajo/workflow";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "com.dexels.navajo.workflow.model";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	WorkflowPackage eINSTANCE = com.dexels.navajo.workflow.impl.WorkflowPackageImpl
			.init();

	/**
	 * The meta object id for the '
	 * {@link com.dexels.navajo.workflow.impl.WorkflowImpl <em>Workflow</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.dexels.navajo.workflow.impl.WorkflowImpl
	 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getWorkflow()
	 * @generated
	 */
	int WORKFLOW = 0;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__STATES = 0;

	/**
	 * The feature id for the '<em><b>Transient</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORKFLOW__TRANSIENT = 1;

	/**
	 * The number of structural features of the '<em>Workflow</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORKFLOW_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '
	 * {@link com.dexels.navajo.workflow.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.dexels.navajo.workflow.impl.StateImpl
	 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getState()
	 * @generated
	 */
	int STATE = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATE__PARENT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATE__ID = 1;

	/**
	 * The number of structural features of the '<em>State</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '
	 * {@link com.dexels.navajo.workflow.impl.TransitionImpl
	 * <em>Transition</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see com.dexels.navajo.workflow.impl.TransitionImpl
	 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Trigger</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TRIGGER = 1;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION__CONDITION = 2;

	/**
	 * The feature id for the '<em><b>Username</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION__USERNAME = 3;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION__FROM = 4;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION__TO = 5;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '
	 * {@link com.dexels.navajo.workflow.impl.ParamImpl <em>Param</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.dexels.navajo.workflow.impl.ParamImpl
	 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getParam()
	 * @generated
	 */
	int PARAM = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAM__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Param</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAM_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '
	 * {@link com.dexels.navajo.workflow.impl.TaskImpl <em>Task</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.dexels.navajo.workflow.impl.TaskImpl
	 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getTask()
	 * @generated
	 */
	int TASK = 4;

	/**
	 * The feature id for the '<em><b>Navajo</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TASK__NAVAJO = 0;

	/**
	 * The feature id for the '<em><b>Service</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TASK__SERVICE = 1;

	/**
	 * The number of structural features of the '<em>Task</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TASK_FEATURE_COUNT = 2;

	/**
	 * Returns the meta object for class '
	 * {@link com.dexels.navajo.workflow.Workflow <em>Workflow</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Workflow</em>'.
	 * @see com.dexels.navajo.workflow.Workflow
	 * @generated
	 */
	EClass getWorkflow();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link com.dexels.navajo.workflow.Workflow#getStates <em>States</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '
	 *         <em>States</em>'.
	 * @see com.dexels.navajo.workflow.Workflow#getStates()
	 * @see #getWorkflow()
	 * @generated
	 */
	EReference getWorkflow_States();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.Workflow#isTransient
	 * <em>Transient</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Transient</em>'.
	 * @see com.dexels.navajo.workflow.Workflow#isTransient()
	 * @see #getWorkflow()
	 * @generated
	 */
	EAttribute getWorkflow_Transient();

	/**
	 * Returns the meta object for class '
	 * {@link com.dexels.navajo.workflow.State <em>State</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>State</em>'.
	 * @see com.dexels.navajo.workflow.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the reference '
	 * {@link com.dexels.navajo.workflow.State#getParent <em>Parent</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see com.dexels.navajo.workflow.State#getParent()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Parent();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.State#getId <em>Id</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.dexels.navajo.workflow.State#getId()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Id();

	/**
	 * Returns the meta object for class '
	 * {@link com.dexels.navajo.workflow.Transition <em>Transition</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see com.dexels.navajo.workflow.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.Transition#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.dexels.navajo.workflow.Transition#getName()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Name();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.Transition#getTrigger <em>Trigger</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Trigger</em>'.
	 * @see com.dexels.navajo.workflow.Transition#getTrigger()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Trigger();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.Transition#getCondition
	 * <em>Condition</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Condition</em>'.
	 * @see com.dexels.navajo.workflow.Transition#getCondition()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Condition();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.Transition#getUsername
	 * <em>Username</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Username</em>'.
	 * @see com.dexels.navajo.workflow.Transition#getUsername()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Username();

	/**
	 * Returns the meta object for the reference '
	 * {@link com.dexels.navajo.workflow.Transition#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see com.dexels.navajo.workflow.Transition#getFrom()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_From();

	/**
	 * Returns the meta object for the reference '
	 * {@link com.dexels.navajo.workflow.Transition#getTo <em>To</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see com.dexels.navajo.workflow.Transition#getTo()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_To();

	/**
	 * Returns the meta object for class '
	 * {@link com.dexels.navajo.workflow.Param <em>Param</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Param</em>'.
	 * @see com.dexels.navajo.workflow.Param
	 * @generated
	 */
	EClass getParam();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.Param#getName <em>Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.dexels.navajo.workflow.Param#getName()
	 * @see #getParam()
	 * @generated
	 */
	EAttribute getParam_Name();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.Param#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.dexels.navajo.workflow.Param#getValue()
	 * @see #getParam()
	 * @generated
	 */
	EAttribute getParam_Value();

	/**
	 * Returns the meta object for class '
	 * {@link com.dexels.navajo.workflow.Task <em>Task</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Task</em>'.
	 * @see com.dexels.navajo.workflow.Task
	 * @generated
	 */
	EClass getTask();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.Task#getNavajo <em>Navajo</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Navajo</em>'.
	 * @see com.dexels.navajo.workflow.Task#getNavajo()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Navajo();

	/**
	 * Returns the meta object for the attribute '
	 * {@link com.dexels.navajo.workflow.Task#getService <em>Service</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Service</em>'.
	 * @see com.dexels.navajo.workflow.Task#getService()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Service();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WorkflowFactory getWorkflowFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link com.dexels.navajo.workflow.impl.WorkflowImpl
		 * <em>Workflow</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see com.dexels.navajo.workflow.impl.WorkflowImpl
		 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getWorkflow()
		 * @generated
		 */
		EClass WORKFLOW = eINSTANCE.getWorkflow();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WORKFLOW__STATES = eINSTANCE.getWorkflow_States();

		/**
		 * The meta object literal for the '<em><b>Transient</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute WORKFLOW__TRANSIENT = eINSTANCE.getWorkflow_Transient();

		/**
		 * The meta object literal for the '
		 * {@link com.dexels.navajo.workflow.impl.StateImpl <em>State</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.dexels.navajo.workflow.impl.StateImpl
		 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference STATE__PARENT = eINSTANCE.getState_Parent();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute STATE__ID = eINSTANCE.getState_Id();

		/**
		 * The meta object literal for the '
		 * {@link com.dexels.navajo.workflow.impl.TransitionImpl
		 * <em>Transition</em>}' class. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see com.dexels.navajo.workflow.impl.TransitionImpl
		 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRANSITION__NAME = eINSTANCE.getTransition_Name();

		/**
		 * The meta object literal for the '<em><b>Trigger</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRANSITION__TRIGGER = eINSTANCE.getTransition_Trigger();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRANSITION__CONDITION = eINSTANCE.getTransition_Condition();

		/**
		 * The meta object literal for the '<em><b>Username</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TRANSITION__USERNAME = eINSTANCE.getTransition_Username();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRANSITION__FROM = eINSTANCE.getTransition_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TRANSITION__TO = eINSTANCE.getTransition_To();

		/**
		 * The meta object literal for the '
		 * {@link com.dexels.navajo.workflow.impl.ParamImpl <em>Param</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.dexels.navajo.workflow.impl.ParamImpl
		 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getParam()
		 * @generated
		 */
		EClass PARAM = eINSTANCE.getParam();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PARAM__NAME = eINSTANCE.getParam_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PARAM__VALUE = eINSTANCE.getParam_Value();

		/**
		 * The meta object literal for the '
		 * {@link com.dexels.navajo.workflow.impl.TaskImpl <em>Task</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.dexels.navajo.workflow.impl.TaskImpl
		 * @see com.dexels.navajo.workflow.impl.WorkflowPackageImpl#getTask()
		 * @generated
		 */
		EClass TASK = eINSTANCE.getTask();

		/**
		 * The meta object literal for the '<em><b>Navajo</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TASK__NAVAJO = eINSTANCE.getTask_Navajo();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TASK__SERVICE = eINSTANCE.getTask_Service();

	}

} // WorkflowPackage
