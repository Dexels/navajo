package com.dexels.navajo.plugin.workflow.model;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class TaskElement extends WorkflowModelElement {

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

	/**
	 * ID for the Height property value (used for by the corresponding property
	 * descriptor).
	 */
	/** Property ID to use when the location of this shape is modified. */
	// private static final long serialVersionUID = 1;
	/** Property ID to use then the size of this shape is modified. */
	/** Property ID to use when the list of outgoing connections is modified. */
	// public static final String SOURCE_CONNECTIONS_PROP = "Shape.SourceConn";
	/** Property ID to use when the list of incoming connections is modified. */
	// public static final String TARGET_CONNECTIONS_PROP = "Shape.TargetConn";
	/**
	 * ID for the Width property value (used for by the corresponding property
	 * descriptor).
	 */

	public static final String NAVAJO_PROP = "Task.navajo";
	public static final String SERVICE_PROP = "Task.service";

	/** Location of this shape. */
	private Point location = new Point((int) (Math.random() * 200),
			(int) (Math.random() * 200));
	/** Size of this shape. */
	private Dimension size = new Dimension(100, 50);
	/** List of outgoing Connections. */

	private XMLElement myElement;


	private String navajo = null;
	private String service = null;

	public TaskElement() {
		navajo = "request";
		service = "InitPing";
	}
	/*
	 * Initializes the property descriptors array.
	 * 
	 * @see #getPropertyDescriptors()
	 * 
	 * @see #getPropertyValue(Object)
	 * 
	 * @see #setPropertyValue(Object, Object)
	 */
	static {
		descriptors = new IPropertyDescriptor[] {
				// new TextPropertyDescriptor(XPOS_PROP, "X"), // id and
				// description pair
				// new TextPropertyDescriptor(YPOS_PROP, "Y"),
				// new TextPropertyDescriptor(WIDTH_PROP, "Width"),
				// new TextPropertyDescriptor(HEIGHT_PROP, "Height"),
				new TextPropertyDescriptor(NAVAJO_PROP, "Navajo"),
				new TextPropertyDescriptor(SERVICE_PROP, "Service"),

		};
		// use a custom cell editor validator for all four array entries
		// for (int i = 0; i < 4; i++) {
		// ((PropertyDescriptor) descriptors[i]).setValidator(new
		// ICellEditorValidator() {
		// public String isValid(Object value) {
		// int intValue = -1;
		// try {
		// intValue = Integer.parseInt((String) value);
		// } catch (NumberFormatException exc) {
		// return "Not a number";
		// }
		// return (intValue >= 0) ? null : "Value must be >= 0";
		// }
		// });
		// }
	} // static

	protected static Image createImage(String name) {
		InputStream stream = TaskElement.class.getClassLoader()
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
	 * Return the Location of this shape.
	 * 
	 * @return a non-null location instance
	 */
	public Point getLocation() {
		return location.getCopy();
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
		if (NAVAJO_PROP.equals(propertyId)) {
			return navajo;
		}
		if (SERVICE_PROP.equals(propertyId)) {
			return service;
		}
		return super.getPropertyValue(propertyId);
	}

	/**
	 * Return the Size of this shape.
	 * 
	 * @return a non-null Dimension instance
	 */
	public Dimension getSize() {
		return size.getCopy();
	}

	/**
	 * Set the property value for the given property id. If no matching id is
	 * found, the call is forwarded to the superclass.
	 * <p>
	 * The property view uses the IDs from the IPropertyDescriptors array to set
	 * the values of the corresponding properties.
	 * </p>
	 * 
	 * @see #descriptors
	 * @see #getPropertyDescriptors()
	 */
	@Override
	public void setPropertyValue(Object propertyId, Object value) {
		if (NAVAJO_PROP.equals(propertyId)) {
			navajo = (String) value;
		} else if (SERVICE_PROP.equals(propertyId)) {
			service = (String) value;
		} else {
			super.setPropertyValue(propertyId, value);
		}
	}

	public void load(XMLElement st) {
		myElement = st;
		if (myElement != null) {
			service = myElement.getStringAttribute("service");
			navajo = myElement.getStringAttribute("navajo");
		}
	}

	@Override
	public String toString() {
		return navajo + "->" + service;
	}

	public String getRequest() {
		return navajo;
	}

	public String getService() {
		return service;
	}

	public XMLElement toXml() {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.setName("task");
		if (service != null) {
			xe.setAttribute("service", service);
		}
		if (navajo != null) {
			xe.setAttribute("navajo", navajo);
		}
		return xe;
	}

}
