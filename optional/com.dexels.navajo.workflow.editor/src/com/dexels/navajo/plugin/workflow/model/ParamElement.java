package com.dexels.navajo.plugin.workflow.model;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class ParamElement extends WorkflowModelElement {

	private static final Image RECTANGLE_ICON = createImage("icons/rectangle16.gif");

	/**
	 * A static array of property descriptors. There is one IPropertyDescriptor
	 * entry per editable property.
	 * 
	 * @see #getPropertyDescriptors()
	 * @see #getPropertyValue(Object)
	 * @see #setPropertyValue(Object, Object)
	 */
	private static IPropertyDescriptor[] descriptors;

	private XMLElement myElement;

	private String name = null;
	private String expression = null;

//	private TransitionElement myTransitionElement;

	public static final String NAME_PROP = "Name.name";
	public static final String EXPRESSION_PROP = "Param.expression";

	public ParamElement() {
		name = "Unparked";
		expression = "true";
	}

	static {
		descriptors = new IPropertyDescriptor[] {
				new TextPropertyDescriptor(NAME_PROP, "Name"),
				new TextPropertyDescriptor(EXPRESSION_PROP, "Expression"),

		};
	} // static

	protected static Image createImage(String name) {
		InputStream stream = ParamElement.class.getClassLoader()
				.getResourceAsStream(name);
		Image image = new Image(null, stream);
		try {
			stream.close();
		} catch (IOException ioe) {
		}
		return image;
	}

	public Image getIcon() {
		return RECTANGLE_ICON;
	}

	/**
	 * Returns an array of IPropertyDescriptors for this shape.
	 * <p>
	 * The returned array is used to fill the property view, when the edit-part
	 * corresponding to this model element is selected.
	 * </p>
	 * 
	 * @see #descriptors
	 * @see #getPropertyValue(Object)
	 * @see #setPropertyValue(Object, Object)
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	/**
	 * Return the property value for the given propertyId, or null.
	 * <p>
	 * The property view uses the IDs from the IPropertyDescriptors array to
	 * obtain the value of the corresponding properties.
	 * </p>
	 * 
	 * @see #descriptors
	 * @see #getPropertyDescriptors()
	 */
	@Override
	public Object getPropertyValue(Object propertyId) {
		if (NAME_PROP.equals(propertyId)) {
			return name;
		}
		if (EXPRESSION_PROP.equals(propertyId)) {
			return expression;
		}
		return super.getPropertyValue(propertyId);
	}

	@Override
	public void setPropertyValue(Object propertyId, Object value) {
		if (NAME_PROP.equals(propertyId)) {
			expression = (String) value;
		} else if (EXPRESSION_PROP.equals(propertyId)) {
			expression = (String) value;
		} else {
			super.setPropertyValue(propertyId, value);
		}
	}

	public void load(XMLElement st) {
		myElement = st;
		if (myElement != null) {
			name = myElement.getStringAttribute("name");
			XMLElement e = myElement.getElementByTagName("expression");
			if (e != null) {
				expression = e.getStringAttribute("value");
			}
		}
	}

	@Override
	public String toString() {
		return name + ": " + expression;
	}

	public String getName() {
		return name;
	}

	public String getExpression() {
		return expression;
	}

	public XMLElement toXml() {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.setName("param");
		if (name != null) {
			xe.setAttribute("name", name);
		}
		if (expression != null) {
			XMLElement x = new CaseSensitiveXMLElement();
			x.setName("expression");
			xe.addChild(x);
			x.setAttribute("value", expression);
		}
		return xe;
	}

//	public void setTransition(TransitionElement transitionElement) {
//		myTransitionElement = transitionElement;
//
//	}

}
