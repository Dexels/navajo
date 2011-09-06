/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.dexels.navajo.dsl.model.tsl.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import com.dexels.navajo.dsl.model.tsl.Element;
import com.dexels.navajo.dsl.model.tsl.PossibleExpression;
import com.dexels.navajo.dsl.model.tsl.TslPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.ElementImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.ElementImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.ElementImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.ElementImpl#getContent <em>Content</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.ElementImpl#isSplitTag <em>Split Tag</em>}</li>
 *   <li>{@link com.dexels.navajo.dsl.model.tsl.impl.ElementImpl#isClosedTag <em>Closed Tag</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ElementImpl extends EObjectImpl implements Element {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<Element> children;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<PossibleExpression> attributes;

	/**
	 * The default value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected String content = CONTENT_EDEFAULT;

	/**
	 * The default value of the '{@link #isSplitTag() <em>Split Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSplitTag()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SPLIT_TAG_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSplitTag() <em>Split Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSplitTag()
	 * @generated
	 * @ordered
	 */
	protected boolean splitTag = SPLIT_TAG_EDEFAULT;

	/**
	 * The default value of the '{@link #isClosedTag() <em>Closed Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isClosedTag()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CLOSED_TAG_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isClosedTag() <em>Closed Tag</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isClosedTag()
	 * @generated
	 * @ordered
	 */
	protected boolean closedTag = CLOSED_TAG_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TslPackage.Literals.ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Element> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<Element>(Element.class, this, TslPackage.ELEMENT__CHILDREN, TslPackage.ELEMENT__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Element getParent() {
		if (eContainerFeatureID() != TslPackage.ELEMENT__PARENT) return null;
		return (Element)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(Element newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, TslPackage.ELEMENT__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(Element newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != TslPackage.ELEMENT__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, TslPackage.ELEMENT__CHILDREN, Element.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.ELEMENT__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PossibleExpression> getAttributes() {
		if (attributes == null) {
			attributes = new EObjectContainmentEList<PossibleExpression>(PossibleExpression.class, this, TslPackage.ELEMENT__ATTRIBUTES);
		}
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(String newContent) {
		String oldContent = content;
		content = newContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.ELEMENT__CONTENT, oldContent, content));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSplitTag() {
		return splitTag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSplitTag(boolean newSplitTag) {
		boolean oldSplitTag = splitTag;
		splitTag = newSplitTag;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.ELEMENT__SPLIT_TAG, oldSplitTag, splitTag));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isClosedTag() {
		return closedTag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClosedTag(boolean newClosedTag) {
		boolean oldClosedTag = closedTag;
		closedTag = newClosedTag;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TslPackage.ELEMENT__CLOSED_TAG, oldClosedTag, closedTag));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int calculateDepth() {
		EObject parent = eContainer();
		if(parent instanceof Element) {
			return ((Element)parent).calculateDepth()+1;
		}
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TslPackage.ELEMENT__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
			case TslPackage.ELEMENT__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((Element)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TslPackage.ELEMENT__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case TslPackage.ELEMENT__PARENT:
				return basicSetParent(null, msgs);
			case TslPackage.ELEMENT__ATTRIBUTES:
				return ((InternalEList<?>)getAttributes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case TslPackage.ELEMENT__PARENT:
				return eInternalContainer().eInverseRemove(this, TslPackage.ELEMENT__CHILDREN, Element.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TslPackage.ELEMENT__CHILDREN:
				return getChildren();
			case TslPackage.ELEMENT__PARENT:
				return getParent();
			case TslPackage.ELEMENT__ATTRIBUTES:
				return getAttributes();
			case TslPackage.ELEMENT__CONTENT:
				return getContent();
			case TslPackage.ELEMENT__SPLIT_TAG:
				return isSplitTag();
			case TslPackage.ELEMENT__CLOSED_TAG:
				return isClosedTag();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TslPackage.ELEMENT__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends Element>)newValue);
				return;
			case TslPackage.ELEMENT__PARENT:
				setParent((Element)newValue);
				return;
			case TslPackage.ELEMENT__ATTRIBUTES:
				getAttributes().clear();
				getAttributes().addAll((Collection<? extends PossibleExpression>)newValue);
				return;
			case TslPackage.ELEMENT__CONTENT:
				setContent((String)newValue);
				return;
			case TslPackage.ELEMENT__SPLIT_TAG:
				setSplitTag((Boolean)newValue);
				return;
			case TslPackage.ELEMENT__CLOSED_TAG:
				setClosedTag((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TslPackage.ELEMENT__CHILDREN:
				getChildren().clear();
				return;
			case TslPackage.ELEMENT__PARENT:
				setParent((Element)null);
				return;
			case TslPackage.ELEMENT__ATTRIBUTES:
				getAttributes().clear();
				return;
			case TslPackage.ELEMENT__CONTENT:
				setContent(CONTENT_EDEFAULT);
				return;
			case TslPackage.ELEMENT__SPLIT_TAG:
				setSplitTag(SPLIT_TAG_EDEFAULT);
				return;
			case TslPackage.ELEMENT__CLOSED_TAG:
				setClosedTag(CLOSED_TAG_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TslPackage.ELEMENT__CHILDREN:
				return children != null && !children.isEmpty();
			case TslPackage.ELEMENT__PARENT:
				return getParent() != null;
			case TslPackage.ELEMENT__ATTRIBUTES:
				return attributes != null && !attributes.isEmpty();
			case TslPackage.ELEMENT__CONTENT:
				return CONTENT_EDEFAULT == null ? content != null : !CONTENT_EDEFAULT.equals(content);
			case TslPackage.ELEMENT__SPLIT_TAG:
				return splitTag != SPLIT_TAG_EDEFAULT;
			case TslPackage.ELEMENT__CLOSED_TAG:
				return closedTag != CLOSED_TAG_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (content: ");
		result.append(content);
		result.append(", splitTag: ");
		result.append(splitTag);
		result.append(", closedTag: ");
		result.append(closedTag);
		result.append(')');
		return result.toString();
	}

} //ElementImpl
