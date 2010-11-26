/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl.impl;

import com.dexels.navajo.dsl.model.expression.ExpressionPackage;

import com.dexels.navajo.dsl.model.tsl.Element;
import com.dexels.navajo.dsl.model.tsl.ExpressionTag;
import com.dexels.navajo.dsl.model.tsl.Map;
import com.dexels.navajo.dsl.model.tsl.Message;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.Property;
import com.dexels.navajo.dsl.model.tsl.Tml;
import com.dexels.navajo.dsl.model.tsl.TslFactory;
import com.dexels.navajo.dsl.model.tsl.TslPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TslPackageImpl extends EPackageImpl implements TslPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tmlEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass possibleExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass messageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionTagEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.dexels.navajo.dsl.model.tsl.TslPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TslPackageImpl() {
		super(eNS_URI, TslFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link TslPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TslPackage init() {
		if (isInited) return (TslPackage)EPackage.Registry.INSTANCE.getEPackage(TslPackage.eNS_URI);

		// Obtain or create and register package
		TslPackageImpl theTslPackage = (TslPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TslPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TslPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ExpressionPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTslPackage.createPackageContents();

		// Initialize created meta-data
		theTslPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTslPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TslPackage.eNS_URI, theTslPackage);
		return theTslPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTml() {
		return tmlEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPossibleExpression() {
		return possibleExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPossibleExpression_Key() {
		return (EAttribute)possibleExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPossibleExpression_ExpressionValue() {
		return (EReference)possibleExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPossibleExpression_Value() {
		return (EAttribute)possibleExpressionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElement() {
		return elementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElement_Children() {
		return (EReference)elementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElement_Parent() {
		return (EReference)elementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElement_Attributes() {
		return (EReference)elementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMessage() {
		return messageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMap() {
		return mapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMap_MapName() {
		return (EAttribute)mapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMap_MapClosingName() {
		return (EAttribute)mapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProperty_ExpressionValue() {
		return (EReference)propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpressionTag() {
		return expressionTagEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getExpressionTag_Expression() {
		return (EReference)expressionTagEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TslFactory getTslFactory() {
		return (TslFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		tmlEClass = createEClass(TML);

		possibleExpressionEClass = createEClass(POSSIBLE_EXPRESSION);
		createEAttribute(possibleExpressionEClass, POSSIBLE_EXPRESSION__KEY);
		createEReference(possibleExpressionEClass, POSSIBLE_EXPRESSION__EXPRESSION_VALUE);
		createEAttribute(possibleExpressionEClass, POSSIBLE_EXPRESSION__VALUE);

		elementEClass = createEClass(ELEMENT);
		createEReference(elementEClass, ELEMENT__CHILDREN);
		createEReference(elementEClass, ELEMENT__PARENT);
		createEReference(elementEClass, ELEMENT__ATTRIBUTES);

		messageEClass = createEClass(MESSAGE);

		mapEClass = createEClass(MAP);
		createEAttribute(mapEClass, MAP__MAP_NAME);
		createEAttribute(mapEClass, MAP__MAP_CLOSING_NAME);

		propertyEClass = createEClass(PROPERTY);
		createEReference(propertyEClass, PROPERTY__EXPRESSION_VALUE);

		expressionTagEClass = createEClass(EXPRESSION_TAG);
		createEReference(expressionTagEClass, EXPRESSION_TAG__EXPRESSION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ExpressionPackage theExpressionPackage = (ExpressionPackage)EPackage.Registry.INSTANCE.getEPackage(ExpressionPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tmlEClass.getESuperTypes().add(this.getElement());
		messageEClass.getESuperTypes().add(this.getElement());
		mapEClass.getESuperTypes().add(this.getElement());
		propertyEClass.getESuperTypes().add(this.getElement());
		expressionTagEClass.getESuperTypes().add(this.getElement());

		// Initialize classes and features; add operations and parameters
		initEClass(tmlEClass, Tml.class, "Tml", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(possibleExpressionEClass, PossibleExpression.class, "PossibleExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPossibleExpression_Key(), ecorePackage.getEString(), "key", null, 0, 1, PossibleExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPossibleExpression_ExpressionValue(), theExpressionPackage.getTopLevel(), null, "expressionValue", null, 0, 1, PossibleExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPossibleExpression_Value(), ecorePackage.getEString(), "value", null, 0, 1, PossibleExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(elementEClass, Element.class, "Element", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getElement_Children(), this.getElement(), this.getElement_Parent(), "children", null, 0, -1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElement_Parent(), this.getElement(), this.getElement_Children(), "parent", null, 0, 1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElement_Attributes(), this.getPossibleExpression(), null, "attributes", null, 0, -1, Element.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(messageEClass, Message.class, "Message", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(mapEClass, Map.class, "Map", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMap_MapName(), ecorePackage.getEString(), "mapName", null, 0, 1, Map.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMap_MapClosingName(), ecorePackage.getEString(), "mapClosingName", null, 0, 1, Map.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProperty_ExpressionValue(), this.getExpressionTag(), null, "expressionValue", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionTagEClass, ExpressionTag.class, "ExpressionTag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getExpressionTag_Expression(), theExpressionPackage.getTopLevel(), null, "expression", null, 0, 1, ExpressionTag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TslPackageImpl
