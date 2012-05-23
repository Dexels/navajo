/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.expression;

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
 * @see com.dexels.navajo.dsl.model.expression.ExpressionFactory
 * @model kind="package"
 * @generated
 */
public interface ExpressionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "expression";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.dexels.com/expression/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "expression";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExpressionPackage eINSTANCE = com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.TopLevelImpl <em>Top Level</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.TopLevelImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getTopLevel()
	 * @generated
	 */
	int TOP_LEVEL = 0;

	/**
	 * The feature id for the '<em><b>Toplevel Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_LEVEL__TOPLEVEL_EXPRESSION = 0;

	/**
	 * The number of structural features of the '<em>Top Level</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOP_LEVEL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__PARENT = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__PARAMETERS = 1;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__OPERATIONS = 2;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__ELEMENTS = 3;

	/**
	 * The feature id for the '<em><b>Subliteral</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__SUBLITERAL = 4;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__VALUE_STRING = 5;

	/**
	 * The feature id for the '<em><b>Expression Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__EXPRESSION_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__PATH_SEQUENCE = 7;

	/**
	 * The feature id for the '<em><b>Exists Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__EXISTS_PATH_SEQUENCE = 8;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.FunctionCallImpl <em>Function Call</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.FunctionCallImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getFunctionCall()
	 * @generated
	 */
	int FUNCTION_CALL = 2;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__PARENT = EXPRESSION__PARENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__PARAMETERS = EXPRESSION__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__OPERATIONS = EXPRESSION__OPERATIONS;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__ELEMENTS = EXPRESSION__ELEMENTS;

	/**
	 * The feature id for the '<em><b>Subliteral</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__SUBLITERAL = EXPRESSION__SUBLITERAL;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__VALUE_STRING = EXPRESSION__VALUE_STRING;

	/**
	 * The feature id for the '<em><b>Expression Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__EXPRESSION_TYPE = EXPRESSION__EXPRESSION_TYPE;

	/**
	 * The feature id for the '<em><b>Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__PATH_SEQUENCE = EXPRESSION__PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Exists Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__EXISTS_PATH_SEQUENCE = EXPRESSION__EXISTS_PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__NAME = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operands</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL__OPERANDS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Function Call</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_CALL_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.MapGetReferenceImpl <em>Map Get Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.MapGetReferenceImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getMapGetReference()
	 * @generated
	 */
	int MAP_GET_REFERENCE = 3;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__PARENT = EXPRESSION__PARENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__PARAMETERS = EXPRESSION__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__OPERATIONS = EXPRESSION__OPERATIONS;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__ELEMENTS = EXPRESSION__ELEMENTS;

	/**
	 * The feature id for the '<em><b>Subliteral</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__SUBLITERAL = EXPRESSION__SUBLITERAL;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__VALUE_STRING = EXPRESSION__VALUE_STRING;

	/**
	 * The feature id for the '<em><b>Expression Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__EXPRESSION_TYPE = EXPRESSION__EXPRESSION_TYPE;

	/**
	 * The feature id for the '<em><b>Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__PATH_SEQUENCE = EXPRESSION__PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Exists Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__EXISTS_PATH_SEQUENCE = EXPRESSION__EXISTS_PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Path Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__PATH_ELEMENTS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Reference Params</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE__REFERENCE_PARAMS = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Map Get Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_GET_REFERENCE_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.ReferenceParamsImpl <em>Reference Params</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.ReferenceParamsImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getReferenceParams()
	 * @generated
	 */
	int REFERENCE_PARAMS = 4;

	/**
	 * The feature id for the '<em><b>Getter Params</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_PARAMS__GETTER_PARAMS = 0;

	/**
	 * The number of structural features of the '<em>Reference Params</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_PARAMS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.TmlReferenceImpl <em>Tml Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.TmlReferenceImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getTmlReference()
	 * @generated
	 */
	int TML_REFERENCE = 5;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__PARENT = EXPRESSION__PARENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__PARAMETERS = EXPRESSION__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__OPERATIONS = EXPRESSION__OPERATIONS;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__ELEMENTS = EXPRESSION__ELEMENTS;

	/**
	 * The feature id for the '<em><b>Subliteral</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__SUBLITERAL = EXPRESSION__SUBLITERAL;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__VALUE_STRING = EXPRESSION__VALUE_STRING;

	/**
	 * The feature id for the '<em><b>Expression Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__EXPRESSION_TYPE = EXPRESSION__EXPRESSION_TYPE;

	/**
	 * The feature id for the '<em><b>Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__PATH_SEQUENCE = EXPRESSION__PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Exists Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__EXISTS_PATH_SEQUENCE = EXPRESSION__EXISTS_PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Path Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__PATH_ELEMENTS = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Absolute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__ABSOLUTE = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Param</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE__PARAM = EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Tml Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TML_REFERENCE_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.ExistsTmlReferenceImpl <em>Exists Tml Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExistsTmlReferenceImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getExistsTmlReference()
	 * @generated
	 */
	int EXISTS_TML_REFERENCE = 6;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__PARENT = TML_REFERENCE__PARENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__PARAMETERS = TML_REFERENCE__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__OPERATIONS = TML_REFERENCE__OPERATIONS;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__ELEMENTS = TML_REFERENCE__ELEMENTS;

	/**
	 * The feature id for the '<em><b>Subliteral</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__SUBLITERAL = TML_REFERENCE__SUBLITERAL;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__VALUE_STRING = TML_REFERENCE__VALUE_STRING;

	/**
	 * The feature id for the '<em><b>Expression Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__EXPRESSION_TYPE = TML_REFERENCE__EXPRESSION_TYPE;

	/**
	 * The feature id for the '<em><b>Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__PATH_SEQUENCE = TML_REFERENCE__PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Exists Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__EXISTS_PATH_SEQUENCE = TML_REFERENCE__EXISTS_PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Path Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__PATH_ELEMENTS = TML_REFERENCE__PATH_ELEMENTS;

	/**
	 * The feature id for the '<em><b>Absolute</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__ABSOLUTE = TML_REFERENCE__ABSOLUTE;

	/**
	 * The feature id for the '<em><b>Param</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE__PARAM = TML_REFERENCE__PARAM;

	/**
	 * The number of structural features of the '<em>Exists Tml Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXISTS_TML_REFERENCE_FEATURE_COUNT = TML_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.FunctionOperandsImpl <em>Function Operands</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.FunctionOperandsImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getFunctionOperands()
	 * @generated
	 */
	int FUNCTION_OPERANDS = 7;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__PARENT = EXPRESSION__PARENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__PARAMETERS = EXPRESSION__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__OPERATIONS = EXPRESSION__OPERATIONS;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__ELEMENTS = EXPRESSION__ELEMENTS;

	/**
	 * The feature id for the '<em><b>Subliteral</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__SUBLITERAL = EXPRESSION__SUBLITERAL;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__VALUE_STRING = EXPRESSION__VALUE_STRING;

	/**
	 * The feature id for the '<em><b>Expression Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__EXPRESSION_TYPE = EXPRESSION__EXPRESSION_TYPE;

	/**
	 * The feature id for the '<em><b>Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__PATH_SEQUENCE = EXPRESSION__PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Exists Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__EXISTS_PATH_SEQUENCE = EXPRESSION__EXISTS_PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Param List</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS__PARAM_LIST = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Function Operands</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERANDS_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.OperationImpl <em>Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.OperationImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getOperation()
	 * @generated
	 */
	int OPERATION = 8;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__PARENT = EXPRESSION__PARENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__PARAMETERS = EXPRESSION__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__OPERATIONS = EXPRESSION__OPERATIONS;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__ELEMENTS = EXPRESSION__ELEMENTS;

	/**
	 * The feature id for the '<em><b>Subliteral</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__SUBLITERAL = EXPRESSION__SUBLITERAL;

	/**
	 * The feature id for the '<em><b>Value String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__VALUE_STRING = EXPRESSION__VALUE_STRING;

	/**
	 * The feature id for the '<em><b>Expression Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__EXPRESSION_TYPE = EXPRESSION__EXPRESSION_TYPE;

	/**
	 * The feature id for the '<em><b>Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__PATH_SEQUENCE = EXPRESSION__PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Exists Path Sequence</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__EXISTS_PATH_SEQUENCE = EXPRESSION__EXISTS_PATH_SEQUENCE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__TYPE = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.dexels.navajo.dsl.model.expression.impl.PathSequenceImpl <em>Path Sequence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.dexels.navajo.dsl.model.expression.impl.PathSequenceImpl
	 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getPathSequence()
	 * @generated
	 */
	int PATH_SEQUENCE = 9;

	/**
	 * The feature id for the '<em><b>Path Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_SEQUENCE__PATH_ELEMENTS = 0;

	/**
	 * The number of structural features of the '<em>Path Sequence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_SEQUENCE_FEATURE_COUNT = 1;


	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.TopLevel <em>Top Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Top Level</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.TopLevel
	 * @generated
	 */
	EClass getTopLevel();

	/**
	 * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.model.expression.TopLevel#getToplevelExpression <em>Toplevel Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Toplevel Expression</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.TopLevel#getToplevelExpression()
	 * @see #getTopLevel()
	 * @generated
	 */
	EReference getTopLevel_ToplevelExpression();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for the container reference '{@link com.dexels.navajo.dsl.model.expression.Expression#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getParent()
	 * @see #getExpression()
	 * @generated
	 */
	EReference getExpression_Parent();

	/**
	 * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.model.expression.Expression#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getParameters()
	 * @see #getExpression()
	 * @generated
	 */
	EReference getExpression_Parameters();

	/**
	 * Returns the meta object for the attribute list '{@link com.dexels.navajo.dsl.model.expression.Expression#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Operations</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getOperations()
	 * @see #getExpression()
	 * @generated
	 */
	EAttribute getExpression_Operations();

	/**
	 * Returns the meta object for the attribute list '{@link com.dexels.navajo.dsl.model.expression.Expression#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Elements</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getElements()
	 * @see #getExpression()
	 * @generated
	 */
	EAttribute getExpression_Elements();

	/**
	 * Returns the meta object for the reference list '{@link com.dexels.navajo.dsl.model.expression.Expression#getSubliteral <em>Subliteral</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Subliteral</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getSubliteral()
	 * @see #getExpression()
	 * @generated
	 */
	EReference getExpression_Subliteral();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.expression.Expression#getValueString <em>Value String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value String</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getValueString()
	 * @see #getExpression()
	 * @generated
	 */
	EAttribute getExpression_ValueString();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.expression.Expression#getExpressionType <em>Expression Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression Type</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getExpressionType()
	 * @see #getExpression()
	 * @generated
	 */
	EAttribute getExpression_ExpressionType();

	/**
	 * Returns the meta object for the reference '{@link com.dexels.navajo.dsl.model.expression.Expression#getPathSequence <em>Path Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Path Sequence</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getPathSequence()
	 * @see #getExpression()
	 * @generated
	 */
	EReference getExpression_PathSequence();

	/**
	 * Returns the meta object for the reference '{@link com.dexels.navajo.dsl.model.expression.Expression#getExistsPathSequence <em>Exists Path Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Exists Path Sequence</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Expression#getExistsPathSequence()
	 * @see #getExpression()
	 * @generated
	 */
	EReference getExpression_ExistsPathSequence();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.FunctionCall <em>Function Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Call</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.FunctionCall
	 * @generated
	 */
	EClass getFunctionCall();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.expression.FunctionCall#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.FunctionCall#getName()
	 * @see #getFunctionCall()
	 * @generated
	 */
	EAttribute getFunctionCall_Name();

	/**
	 * Returns the meta object for the reference '{@link com.dexels.navajo.dsl.model.expression.FunctionCall#getOperands <em>Operands</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Operands</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.FunctionCall#getOperands()
	 * @see #getFunctionCall()
	 * @generated
	 */
	EReference getFunctionCall_Operands();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.MapGetReference <em>Map Get Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Get Reference</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.MapGetReference
	 * @generated
	 */
	EClass getMapGetReference();

	/**
	 * Returns the meta object for the attribute list '{@link com.dexels.navajo.dsl.model.expression.MapGetReference#getPathElements <em>Path Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Path Elements</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.MapGetReference#getPathElements()
	 * @see #getMapGetReference()
	 * @generated
	 */
	EAttribute getMapGetReference_PathElements();

	/**
	 * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.model.expression.MapGetReference#getReferenceParams <em>Reference Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Reference Params</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.MapGetReference#getReferenceParams()
	 * @see #getMapGetReference()
	 * @generated
	 */
	EReference getMapGetReference_ReferenceParams();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.ReferenceParams <em>Reference Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference Params</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.ReferenceParams
	 * @generated
	 */
	EClass getReferenceParams();

	/**
	 * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.model.expression.ReferenceParams#getGetterParams <em>Getter Params</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Getter Params</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.ReferenceParams#getGetterParams()
	 * @see #getReferenceParams()
	 * @generated
	 */
	EReference getReferenceParams_GetterParams();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.TmlReference <em>Tml Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tml Reference</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.TmlReference
	 * @generated
	 */
	EClass getTmlReference();

	/**
	 * Returns the meta object for the attribute list '{@link com.dexels.navajo.dsl.model.expression.TmlReference#getPathElements <em>Path Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Path Elements</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.TmlReference#getPathElements()
	 * @see #getTmlReference()
	 * @generated
	 */
	EAttribute getTmlReference_PathElements();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.expression.TmlReference#isAbsolute <em>Absolute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Absolute</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.TmlReference#isAbsolute()
	 * @see #getTmlReference()
	 * @generated
	 */
	EAttribute getTmlReference_Absolute();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.expression.TmlReference#isParam <em>Param</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Param</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.TmlReference#isParam()
	 * @see #getTmlReference()
	 * @generated
	 */
	EAttribute getTmlReference_Param();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.ExistsTmlReference <em>Exists Tml Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exists Tml Reference</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.ExistsTmlReference
	 * @generated
	 */
	EClass getExistsTmlReference();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.FunctionOperands <em>Function Operands</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Operands</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.FunctionOperands
	 * @generated
	 */
	EClass getFunctionOperands();

	/**
	 * Returns the meta object for the reference list '{@link com.dexels.navajo.dsl.model.expression.FunctionOperands#getParamList <em>Param List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Param List</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.FunctionOperands#getParamList()
	 * @see #getFunctionOperands()
	 * @generated
	 */
	EReference getFunctionOperands_ParamList();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.Operation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Operation
	 * @generated
	 */
	EClass getOperation();

	/**
	 * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.model.expression.Operation#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.Operation#getType()
	 * @see #getOperation()
	 * @generated
	 */
	EAttribute getOperation_Type();

	/**
	 * Returns the meta object for class '{@link com.dexels.navajo.dsl.model.expression.PathSequence <em>Path Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Path Sequence</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.PathSequence
	 * @generated
	 */
	EClass getPathSequence();

	/**
	 * Returns the meta object for the attribute list '{@link com.dexels.navajo.dsl.model.expression.PathSequence#getPathElements <em>Path Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Path Elements</em>'.
	 * @see com.dexels.navajo.dsl.model.expression.PathSequence#getPathElements()
	 * @see #getPathSequence()
	 * @generated
	 */
	EAttribute getPathSequence_PathElements();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExpressionFactory getExpressionFactory();

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
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.TopLevelImpl <em>Top Level</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.TopLevelImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getTopLevel()
		 * @generated
		 */
		EClass TOP_LEVEL = eINSTANCE.getTopLevel();

		/**
		 * The meta object literal for the '<em><b>Toplevel Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOP_LEVEL__TOPLEVEL_EXPRESSION = eINSTANCE.getTopLevel_ToplevelExpression();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION__PARENT = eINSTANCE.getExpression_Parent();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION__PARAMETERS = eINSTANCE.getExpression_Parameters();

		/**
		 * The meta object literal for the '<em><b>Operations</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPRESSION__OPERATIONS = eINSTANCE.getExpression_Operations();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPRESSION__ELEMENTS = eINSTANCE.getExpression_Elements();

		/**
		 * The meta object literal for the '<em><b>Subliteral</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION__SUBLITERAL = eINSTANCE.getExpression_Subliteral();

		/**
		 * The meta object literal for the '<em><b>Value String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPRESSION__VALUE_STRING = eINSTANCE.getExpression_ValueString();

		/**
		 * The meta object literal for the '<em><b>Expression Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPRESSION__EXPRESSION_TYPE = eINSTANCE.getExpression_ExpressionType();

		/**
		 * The meta object literal for the '<em><b>Path Sequence</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION__PATH_SEQUENCE = eINSTANCE.getExpression_PathSequence();

		/**
		 * The meta object literal for the '<em><b>Exists Path Sequence</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION__EXISTS_PATH_SEQUENCE = eINSTANCE.getExpression_ExistsPathSequence();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.FunctionCallImpl <em>Function Call</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.FunctionCallImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getFunctionCall()
		 * @generated
		 */
		EClass FUNCTION_CALL = eINSTANCE.getFunctionCall();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FUNCTION_CALL__NAME = eINSTANCE.getFunctionCall_Name();

		/**
		 * The meta object literal for the '<em><b>Operands</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_CALL__OPERANDS = eINSTANCE.getFunctionCall_Operands();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.MapGetReferenceImpl <em>Map Get Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.MapGetReferenceImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getMapGetReference()
		 * @generated
		 */
		EClass MAP_GET_REFERENCE = eINSTANCE.getMapGetReference();

		/**
		 * The meta object literal for the '<em><b>Path Elements</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_GET_REFERENCE__PATH_ELEMENTS = eINSTANCE.getMapGetReference_PathElements();

		/**
		 * The meta object literal for the '<em><b>Reference Params</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_GET_REFERENCE__REFERENCE_PARAMS = eINSTANCE.getMapGetReference_ReferenceParams();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.ReferenceParamsImpl <em>Reference Params</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.ReferenceParamsImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getReferenceParams()
		 * @generated
		 */
		EClass REFERENCE_PARAMS = eINSTANCE.getReferenceParams();

		/**
		 * The meta object literal for the '<em><b>Getter Params</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REFERENCE_PARAMS__GETTER_PARAMS = eINSTANCE.getReferenceParams_GetterParams();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.TmlReferenceImpl <em>Tml Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.TmlReferenceImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getTmlReference()
		 * @generated
		 */
		EClass TML_REFERENCE = eINSTANCE.getTmlReference();

		/**
		 * The meta object literal for the '<em><b>Path Elements</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TML_REFERENCE__PATH_ELEMENTS = eINSTANCE.getTmlReference_PathElements();

		/**
		 * The meta object literal for the '<em><b>Absolute</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TML_REFERENCE__ABSOLUTE = eINSTANCE.getTmlReference_Absolute();

		/**
		 * The meta object literal for the '<em><b>Param</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TML_REFERENCE__PARAM = eINSTANCE.getTmlReference_Param();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.ExistsTmlReferenceImpl <em>Exists Tml Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExistsTmlReferenceImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getExistsTmlReference()
		 * @generated
		 */
		EClass EXISTS_TML_REFERENCE = eINSTANCE.getExistsTmlReference();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.FunctionOperandsImpl <em>Function Operands</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.FunctionOperandsImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getFunctionOperands()
		 * @generated
		 */
		EClass FUNCTION_OPERANDS = eINSTANCE.getFunctionOperands();

		/**
		 * The meta object literal for the '<em><b>Param List</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_OPERANDS__PARAM_LIST = eINSTANCE.getFunctionOperands_ParamList();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.OperationImpl <em>Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.OperationImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getOperation()
		 * @generated
		 */
		EClass OPERATION = eINSTANCE.getOperation();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION__TYPE = eINSTANCE.getOperation_Type();

		/**
		 * The meta object literal for the '{@link com.dexels.navajo.dsl.model.expression.impl.PathSequenceImpl <em>Path Sequence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.dexels.navajo.dsl.model.expression.impl.PathSequenceImpl
		 * @see com.dexels.navajo.dsl.model.expression.impl.ExpressionPackageImpl#getPathSequence()
		 * @generated
		 */
		EClass PATH_SEQUENCE = eINSTANCE.getPathSequence();

		/**
		 * The meta object literal for the '<em><b>Path Elements</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATH_SEQUENCE__PATH_ELEMENTS = eINSTANCE.getPathSequence_PathElements();

	}

} //ExpressionPackage
