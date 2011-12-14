/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl.impl;

import com.dexels.navajo.dsl.model.tsl.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.dexels.navajo.dsl.model.tsl.Break;
import com.dexels.navajo.dsl.model.tsl.Check;
import com.dexels.navajo.dsl.model.tsl.Comment;
import com.dexels.navajo.dsl.model.tsl.DebugTag;
import com.dexels.navajo.dsl.model.tsl.Element;
import com.dexels.navajo.dsl.model.tsl.ExpressionTag;
import com.dexels.navajo.dsl.model.tsl.Field;
import com.dexels.navajo.dsl.model.tsl.Map;
import com.dexels.navajo.dsl.model.tsl.MapMethod;
import com.dexels.navajo.dsl.model.tsl.Message;
import com.dexels.navajo.dsl.model.tsl.Method;
import com.dexels.navajo.dsl.model.tsl.Methods;
import com.dexels.navajo.dsl.model.tsl.Option;
import com.dexels.navajo.dsl.model.tsl.Param;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.Property;
import com.dexels.navajo.dsl.model.tsl.Required;
import com.dexels.navajo.dsl.model.tsl.Tml;
import com.dexels.navajo.dsl.model.tsl.TslFactory;
import com.dexels.navajo.dsl.model.tsl.TslPackage;
import com.dexels.navajo.dsl.model.tsl.Validations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TslFactoryImpl extends EFactoryImpl implements TslFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TslFactory init() {
		try {
			TslFactory theTslFactory = (TslFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.dexels.com/navajo/dsl/tsl/NavajoTsl"); 
			if (theTslFactory != null) {
				return theTslFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TslFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TslFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TslPackage.TML: return createTml();
			case TslPackage.METHODS: return createMethods();
			case TslPackage.METHOD: return createMethod();
			case TslPackage.POSSIBLE_EXPRESSION: return createPossibleExpression();
			case TslPackage.ELEMENT: return createElement();
			case TslPackage.MESSAGE: return createMessage();
			case TslPackage.MAP: return createMap();
			case TslPackage.PROPERTY: return createProperty();
			case TslPackage.PARAM: return createParam();
			case TslPackage.EXPRESSION_TAG: return createExpressionTag();
			case TslPackage.DEBUG_TAG: return createDebugTag();
			case TslPackage.MAP_METHOD: return createMapMethod();
			case TslPackage.REQUIRED: return createRequired();
			case TslPackage.OPTION: return createOption();
			case TslPackage.CHECK: return createCheck();
			case TslPackage.VALIDATIONS: return createValidations();
			case TslPackage.COMMENT: return createComment();
			case TslPackage.BREAK: return createBreak();
			case TslPackage.FIELD: return createField();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tml createTml() {
		TmlImpl tml = new TmlImpl();
		return tml;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Methods createMethods() {
		MethodsImpl methods = new MethodsImpl();
		return methods;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Method createMethod() {
		MethodImpl method = new MethodImpl();
		return method;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PossibleExpression createPossibleExpression() {
		PossibleExpressionImpl possibleExpression = new PossibleExpressionImpl();
		return possibleExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element createElement() {
		ElementImpl element = new ElementImpl();
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Message createMessage() {
		MessageImpl message = new MessageImpl();
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map createMap() {
		MapImpl map = new MapImpl();
		return map;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Param createParam() {
		ParamImpl param = new ParamImpl();
		return param;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionTag createExpressionTag() {
		ExpressionTagImpl expressionTag = new ExpressionTagImpl();
		return expressionTag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DebugTag createDebugTag() {
		DebugTagImpl debugTag = new DebugTagImpl();
		return debugTag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MapMethod createMapMethod() {
		MapMethodImpl mapMethod = new MapMethodImpl();
		return mapMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Required createRequired() {
		RequiredImpl required = new RequiredImpl();
		return required;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Option createOption() {
		OptionImpl option = new OptionImpl();
		return option;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Check createCheck() {
		CheckImpl check = new CheckImpl();
		return check;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Validations createValidations() {
		ValidationsImpl validations = new ValidationsImpl();
		return validations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Comment createComment() {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Break createBreak() {
		BreakImpl break_ = new BreakImpl();
		return break_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Field createField() {
		FieldImpl field = new FieldImpl();
		return field;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TslPackage getTslPackage() {
		return (TslPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TslPackage getPackage() {
		return TslPackage.eINSTANCE;
	}

} //TslFactoryImpl
