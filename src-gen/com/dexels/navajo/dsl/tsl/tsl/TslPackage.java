/**
 * <copyright>
 * </copyright>
 *
 */
package com.dexels.navajo.dsl.tsl.tsl;

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
 * @see com.dexels.navajo.dsl.tsl.tsl.TslFactory
 * @model kind="package"
 * @generated
 */
public interface TslPackage extends EPackage
{
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
  TslPackage eINSTANCE = com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl.init();

  /**
   * The meta object id for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.TmlImpl <em>Tml</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.TmlImpl
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getTml()
   * @generated
   */
  int TML = 0;

  /**
   * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TML__ATTRIBUTES = 0;

  /**
   * The feature id for the '<em><b>Messages</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TML__MESSAGES = 1;

  /**
   * The feature id for the '<em><b>Maps</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TML__MAPS = 2;

  /**
   * The number of structural features of the '<em>Tml</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TML_FEATURE_COUNT = 3;

  /**
   * The meta object id for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.PossibleExpressionImpl <em>Possible Expression</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.PossibleExpressionImpl
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getPossibleExpression()
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
   * The meta object id for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.MessageImpl <em>Message</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.MessageImpl
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getMessage()
   * @generated
   */
  int MESSAGE = 2;

  /**
   * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE__ATTRIBUTES = 0;

  /**
   * The feature id for the '<em><b>Messages</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE__MESSAGES = 1;

  /**
   * The feature id for the '<em><b>Properties</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE__PROPERTIES = 2;

  /**
   * The feature id for the '<em><b>Maps</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE__MAPS = 3;

  /**
   * The number of structural features of the '<em>Message</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MESSAGE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl <em>Map</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getMap()
   * @generated
   */
  int MAP = 3;

  /**
   * The feature id for the '<em><b>Map Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP__MAP_NAME = 0;

  /**
   * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP__ATTRIBUTES = 1;

  /**
   * The feature id for the '<em><b>Messages</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP__MESSAGES = 2;

  /**
   * The feature id for the '<em><b>Properties</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP__PROPERTIES = 3;

  /**
   * The feature id for the '<em><b>Maps</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP__MAPS = 4;

  /**
   * The feature id for the '<em><b>Map Closing Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP__MAP_CLOSING_NAME = 5;

  /**
   * The number of structural features of the '<em>Map</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int MAP_FEATURE_COUNT = 6;

  /**
   * The meta object id for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.PropertyImpl <em>Property</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.PropertyImpl
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getProperty()
   * @generated
   */
  int PROPERTY = 4;

  /**
   * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__ATTRIBUTES = 0;

  /**
   * The feature id for the '<em><b>Expression Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY__EXPRESSION_VALUE = 1;

  /**
   * The number of structural features of the '<em>Property</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int PROPERTY_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.ExpressionTagImpl <em>Expression Tag</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.ExpressionTagImpl
   * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getExpressionTag()
   * @generated
   */
  int EXPRESSION_TAG = 5;

  /**
   * The feature id for the '<em><b>Value</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_TAG__VALUE = 0;

  /**
   * The number of structural features of the '<em>Expression Tag</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EXPRESSION_TAG_FEATURE_COUNT = 1;


  /**
   * Returns the meta object for class '{@link com.dexels.navajo.dsl.tsl.tsl.Tml <em>Tml</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Tml</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Tml
   * @generated
   */
  EClass getTml();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Tml#getAttributes <em>Attributes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Attributes</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Tml#getAttributes()
   * @see #getTml()
   * @generated
   */
  EReference getTml_Attributes();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Tml#getMessages <em>Messages</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Messages</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Tml#getMessages()
   * @see #getTml()
   * @generated
   */
  EReference getTml_Messages();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Tml#getMaps <em>Maps</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Maps</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Tml#getMaps()
   * @see #getTml()
   * @generated
   */
  EReference getTml_Maps();

  /**
   * Returns the meta object for class '{@link com.dexels.navajo.dsl.tsl.tsl.PossibleExpression <em>Possible Expression</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Possible Expression</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.PossibleExpression
   * @generated
   */
  EClass getPossibleExpression();

  /**
   * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.tsl.tsl.PossibleExpression#getKey <em>Key</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Key</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.PossibleExpression#getKey()
   * @see #getPossibleExpression()
   * @generated
   */
  EAttribute getPossibleExpression_Key();

  /**
   * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.tsl.tsl.PossibleExpression#getExpressionValue <em>Expression Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression Value</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.PossibleExpression#getExpressionValue()
   * @see #getPossibleExpression()
   * @generated
   */
  EReference getPossibleExpression_ExpressionValue();

  /**
   * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.tsl.tsl.PossibleExpression#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Value</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.PossibleExpression#getValue()
   * @see #getPossibleExpression()
   * @generated
   */
  EAttribute getPossibleExpression_Value();

  /**
   * Returns the meta object for class '{@link com.dexels.navajo.dsl.tsl.tsl.Message <em>Message</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Message</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Message
   * @generated
   */
  EClass getMessage();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Message#getAttributes <em>Attributes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Attributes</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Message#getAttributes()
   * @see #getMessage()
   * @generated
   */
  EReference getMessage_Attributes();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Message#getMessages <em>Messages</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Messages</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Message#getMessages()
   * @see #getMessage()
   * @generated
   */
  EReference getMessage_Messages();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Message#getProperties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Properties</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Message#getProperties()
   * @see #getMessage()
   * @generated
   */
  EReference getMessage_Properties();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Message#getMaps <em>Maps</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Maps</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Message#getMaps()
   * @see #getMessage()
   * @generated
   */
  EReference getMessage_Maps();

  /**
   * Returns the meta object for class '{@link com.dexels.navajo.dsl.tsl.tsl.Map <em>Map</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Map</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Map
   * @generated
   */
  EClass getMap();

  /**
   * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMapName <em>Map Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Map Name</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Map#getMapName()
   * @see #getMap()
   * @generated
   */
  EAttribute getMap_MapName();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Map#getAttributes <em>Attributes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Attributes</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Map#getAttributes()
   * @see #getMap()
   * @generated
   */
  EReference getMap_Attributes();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMessages <em>Messages</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Messages</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Map#getMessages()
   * @see #getMap()
   * @generated
   */
  EReference getMap_Messages();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Map#getProperties <em>Properties</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Properties</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Map#getProperties()
   * @see #getMap()
   * @generated
   */
  EReference getMap_Properties();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMaps <em>Maps</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Maps</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Map#getMaps()
   * @see #getMap()
   * @generated
   */
  EReference getMap_Maps();

  /**
   * Returns the meta object for the attribute '{@link com.dexels.navajo.dsl.tsl.tsl.Map#getMapClosingName <em>Map Closing Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Map Closing Name</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Map#getMapClosingName()
   * @see #getMap()
   * @generated
   */
  EAttribute getMap_MapClosingName();

  /**
   * Returns the meta object for class '{@link com.dexels.navajo.dsl.tsl.tsl.Property <em>Property</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Property</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Property
   * @generated
   */
  EClass getProperty();

  /**
   * Returns the meta object for the containment reference list '{@link com.dexels.navajo.dsl.tsl.tsl.Property#getAttributes <em>Attributes</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Attributes</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Property#getAttributes()
   * @see #getProperty()
   * @generated
   */
  EReference getProperty_Attributes();

  /**
   * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.tsl.tsl.Property#getExpressionValue <em>Expression Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Expression Value</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.Property#getExpressionValue()
   * @see #getProperty()
   * @generated
   */
  EReference getProperty_ExpressionValue();

  /**
   * Returns the meta object for class '{@link com.dexels.navajo.dsl.tsl.tsl.ExpressionTag <em>Expression Tag</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Expression Tag</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.ExpressionTag
   * @generated
   */
  EClass getExpressionTag();

  /**
   * Returns the meta object for the containment reference '{@link com.dexels.navajo.dsl.tsl.tsl.ExpressionTag#getValue <em>Value</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference '<em>Value</em>'.
   * @see com.dexels.navajo.dsl.tsl.tsl.ExpressionTag#getValue()
   * @see #getExpressionTag()
   * @generated
   */
  EReference getExpressionTag_Value();

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
  interface Literals
  {
    /**
     * The meta object literal for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.TmlImpl <em>Tml</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.TmlImpl
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getTml()
     * @generated
     */
    EClass TML = eINSTANCE.getTml();

    /**
     * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TML__ATTRIBUTES = eINSTANCE.getTml_Attributes();

    /**
     * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TML__MESSAGES = eINSTANCE.getTml_Messages();

    /**
     * The meta object literal for the '<em><b>Maps</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TML__MAPS = eINSTANCE.getTml_Maps();

    /**
     * The meta object literal for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.PossibleExpressionImpl <em>Possible Expression</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.PossibleExpressionImpl
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getPossibleExpression()
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
     * The meta object literal for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.MessageImpl <em>Message</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.MessageImpl
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getMessage()
     * @generated
     */
    EClass MESSAGE = eINSTANCE.getMessage();

    /**
     * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE__ATTRIBUTES = eINSTANCE.getMessage_Attributes();

    /**
     * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE__MESSAGES = eINSTANCE.getMessage_Messages();

    /**
     * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE__PROPERTIES = eINSTANCE.getMessage_Properties();

    /**
     * The meta object literal for the '<em><b>Maps</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MESSAGE__MAPS = eINSTANCE.getMessage_Maps();

    /**
     * The meta object literal for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl <em>Map</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.MapImpl
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getMap()
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
     * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAP__ATTRIBUTES = eINSTANCE.getMap_Attributes();

    /**
     * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAP__MESSAGES = eINSTANCE.getMap_Messages();

    /**
     * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAP__PROPERTIES = eINSTANCE.getMap_Properties();

    /**
     * The meta object literal for the '<em><b>Maps</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference MAP__MAPS = eINSTANCE.getMap_Maps();

    /**
     * The meta object literal for the '<em><b>Map Closing Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute MAP__MAP_CLOSING_NAME = eINSTANCE.getMap_MapClosingName();

    /**
     * The meta object literal for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.PropertyImpl <em>Property</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.PropertyImpl
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getProperty()
     * @generated
     */
    EClass PROPERTY = eINSTANCE.getProperty();

    /**
     * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPERTY__ATTRIBUTES = eINSTANCE.getProperty_Attributes();

    /**
     * The meta object literal for the '<em><b>Expression Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference PROPERTY__EXPRESSION_VALUE = eINSTANCE.getProperty_ExpressionValue();

    /**
     * The meta object literal for the '{@link com.dexels.navajo.dsl.tsl.tsl.impl.ExpressionTagImpl <em>Expression Tag</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.ExpressionTagImpl
     * @see com.dexels.navajo.dsl.tsl.tsl.impl.TslPackageImpl#getExpressionTag()
     * @generated
     */
    EClass EXPRESSION_TAG = eINSTANCE.getExpressionTag();

    /**
     * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference EXPRESSION_TAG__VALUE = eINSTANCE.getExpressionTag_Value();

  }

} //TslPackage
