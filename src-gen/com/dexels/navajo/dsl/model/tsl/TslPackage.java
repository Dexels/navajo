/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.dexels.navajo.dsl.model.tsl.TslFactory
 * @model kind="package"
 * @generated
 */
public interface TslPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "tsl";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.dexels.com/navajo/dsl/tsl/NavajoTsl";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "tsl";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TslPackage eINSTANCE = com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.tsl.impl.ElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.tsl.impl.ElementImpl
	 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__CHILDREN = 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__PARENT = 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT__ATTRIBUTES = 2;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.tsl.impl.TmlImpl <em>Tml</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.tsl.impl.TmlImpl
	 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getTml()
	 * @generated
	 */
	int TML = 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML__CHILDREN = ELEMENT__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML__PARENT = ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML__ATTRIBUTES = ELEMENT__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Tml</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.tsl.impl.PossibleExpressionImpl <em>Possible Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.tsl.impl.PossibleExpressionImpl
	 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getPossibleExpression()
	 * @generated
	 */
	int POSSIBLE_EXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSSIBLE_EXPRESSION__KEY = 0;

	/**
	 * The feature id for the '<em><b>Expression Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSSIBLE_EXPRESSION__EXPRESSION_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSSIBLE_EXPRESSION__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Possible Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSSIBLE_EXPRESSION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.tsl.impl.MessageImpl <em>Message</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.tsl.impl.MessageImpl
	 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getMessage()
	 * @generated
	 */
	int MESSAGE = 3;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE__CHILDREN = ELEMENT__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE__PARENT = ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE__ATTRIBUTES = ELEMENT__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Message</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.tsl.impl.MapImpl <em>Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.tsl.impl.MapImpl
	 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getMap()
	 * @generated
	 */
	int MAP = 4;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP__CHILDREN = ELEMENT__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP__PARENT = ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP__ATTRIBUTES = ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Map Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP__MAP_NAME = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Map Closing Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP__MAP_CLOSING_NAME = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.tsl.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.tsl.impl.PropertyImpl
	 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 5;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__CHILDREN = ELEMENT__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__PARENT = ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__ATTRIBUTES = ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Expression Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__EXPRESSION_VALUE = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.tsl.impl.ExpressionTagImpl <em>Expression Tag</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.tsl.impl.ExpressionTagImpl
	 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getExpressionTag()
	 * @generated
	 */
	int EXPRESSION_TAG = 6;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_TAG__CHILDREN = ELEMENT__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_TAG__PARENT = ELEMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_TAG__ATTRIBUTES = ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_TAG__EXPRESSION = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression Tag</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_TAG_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.tsl.Tml <em>Tml</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tml</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Tml
	 * @generated
	 */
	EClass getTml();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression <em>Possible Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Possible Expression</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.PossibleExpression
	 * @generated
	 */
	EClass getPossibleExpression();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.PossibleExpression#getKey()
	 * @see #getPossibleExpression()
	 * @generated
	 */
	EAttribute getPossibleExpression_Key();

	/**
	 * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getExpressionValue <em>Expression Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression Value</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.PossibleExpression#getExpressionValue()
	 * @see #getPossibleExpression()
	 * @generated
	 */
	EReference getPossibleExpression_ExpressionValue();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.tsl.PossibleExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.PossibleExpression#getValue()
	 * @see #getPossibleExpression()
	 * @generated
	 */
	EAttribute getPossibleExpression_Value();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.tsl.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Element
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.model.tsl.Element#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Element#getChildren()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_Children();

	/**
	 * Returns the meta object for the container reference '{@link com.dexels.navajo.dsl.model.tsl.Element#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Element#getParent()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_Parent();

	/**
	 * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.model.tsl.Element#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Element#getAttributes()
	 * @see #getElement()
	 * @generated
	 */
	EReference getElement_Attributes();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.tsl.Message <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Message</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Message
	 * @generated
	 */
	EClass getMessage();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.tsl.Map <em>Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Map
	 * @generated
	 */
	EClass getMap();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.tsl.Map#getMapName <em>Map Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Name</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Map#getMapName()
	 * @see #getMap()
	 * @generated
	 */
	EAttribute getMap_MapName();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.tsl.Map#getMapClosingName <em>Map Closing Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Map Closing Name</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Map#getMapClosingName()
	 * @see #getMap()
	 * @generated
	 */
	EAttribute getMap_MapClosingName();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.tsl.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.model.tsl.Property#getExpressionValue <em>Expression Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression Value</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.Property#getExpressionValue()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_ExpressionValue();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.tsl.ExpressionTag <em>Expression Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression Tag</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.ExpressionTag
	 * @generated
	 */
	EClass getExpressionTag();

	/**
	 * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.model.tsl.ExpressionTag#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see com.dexels.navajo.dsl.model.tsl.ExpressionTag#getExpression()
	 * @see #getExpressionTag()
	 * @generated
	 */
	EReference getExpressionTag_Expression();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TslFactory getTslFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.tsl.impl.TmlImpl <em>Tml</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.tsl.impl.TmlImpl
		 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getTml()
		 * @generated
		 */
		EClass TML = eINSTANCE.getTml();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.tsl.impl.PossibleExpressionImpl <em>Possible Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.tsl.impl.PossibleExpressionImpl
		 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getPossibleExpression()
		 * @generated
		 */
		EClass POSSIBLE_EXPRESSION = eINSTANCE.getPossibleExpression();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSSIBLE_EXPRESSION__KEY = eINSTANCE.getPossibleExpression_Key();

		/**
		 * The meta object literal for the '<em><b>Expression Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POSSIBLE_EXPRESSION__EXPRESSION_VALUE = eINSTANCE.getPossibleExpression_ExpressionValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSSIBLE_EXPRESSION__VALUE = eINSTANCE.getPossibleExpression_Value();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.tsl.impl.ElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.tsl.impl.ElementImpl
		 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__CHILDREN = eINSTANCE.getElement_Children();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__PARENT = eINSTANCE.getElement_Parent();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT__ATTRIBUTES = eINSTANCE.getElement_Attributes();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.tsl.impl.MessageImpl <em>Message</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.tsl.impl.MessageImpl
		 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getMessage()
		 * @generated
		 */
		EClass MESSAGE = eINSTANCE.getMessage();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.tsl.impl.MapImpl <em>Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.tsl.impl.MapImpl
		 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getMap()
		 * @generated
		 */
		EClass MAP = eINSTANCE.getMap();

		/**
		 * The meta object literal for the '<em><b>Map Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP__MAP_NAME = eINSTANCE.getMap_MapName();

		/**
		 * The meta object literal for the '<em><b>Map Closing Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP__MAP_CLOSING_NAME = eINSTANCE.getMap_MapClosingName();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.tsl.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.tsl.impl.PropertyImpl
		 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Expression Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__EXPRESSION_VALUE = eINSTANCE.getProperty_ExpressionValue();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.tsl.impl.ExpressionTagImpl <em>Expression Tag</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.tsl.impl.ExpressionTagImpl
		 * @see com.dexels.navajo.dsl.model.tsl.impl.TslPackageImpl#getExpressionTag()
		 * @generated
		 */
		EClass EXPRESSION_TAG = eINSTANCE.getExpressionTag();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION_TAG__EXPRESSION = eINSTANCE.getExpressionTag_Expression();

	}

} //TslPackage
