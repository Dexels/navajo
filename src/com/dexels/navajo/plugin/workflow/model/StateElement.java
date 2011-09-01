package com.dexels.navajo.plugin.workflow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class StateElement extends WorkflowModelElement {

	
	
	/** 
	 * A static array of property descriptors.
	 * There is one IPropertyDescriptor entry per editable property.
	 * @see #getPropertyDescriptors()
	 * @see #getPropertyValue(Object)
	 * @see #setPropertyValue(Object, Object)
	 */
	private static IPropertyDescriptor[] descriptors;
	/** ID for the Height property value (used for by the corresponding property descriptor). */
	private static final String HEIGHT_PROP = "Shape.Height";
	/** Property ID to use when the location of this shape is modified. */
	public static final String LOCATION_PROP = "Shape.Location";
	private static final long serialVersionUID = 1;
	/** Property ID to use then the size of this shape is modified. */
	public static final String SIZE_PROP = "Shape.Size";
	/** Property ID to use when the list of outgoing connections is modified. */
	public static final String SOURCE_CONNECTIONS_PROP = "Shape.SourceConn";
	/** Property ID to use when the list of incoming connections is modified. */
	public static final String TARGET_CONNECTIONS_PROP = "Shape.TargetConn";
	/** ID for the Width property value (used for by the corresponding property descriptor). */
	private static final String WIDTH_PROP = "Shape.Width";

	/** ID for the X property value (used for by the corresponding property descriptor).  */
	private static final String XPOS_PROP = "Shape.xPos";
	/** ID for the Y property value (used for by the corresponding property descriptor).  */
	private static final String YPOS_PROP = "Shape.yPos";
	

	public static final String ID_PROP = "State.id";

	
	/** Location of this shape. */
	private Point location = new Point((int)(Math.random()*200), (int)(Math.random()*200));
	/** Size of this shape. */
	private Dimension size = new Dimension(100, 50);
	/** List of outgoing Connections. */
	private List sourceConnections = new ArrayList();
	/** List of incoming Connections. */
	private List targetConnections = new ArrayList();

	
	private List tasks = new ArrayList();

	private XMLElement myElement;

	private WorkflowElement root = null;

	private String id = null;
	
	public StateElement() {
		id = "new";
	}
	
	
	
	public void setRoot(WorkflowElement root) {
		this.root = root;
	}

	/*
	 * Initializes the property descriptors array.
	 * @see #getPropertyDescriptors()
	 * @see #getPropertyValue(Object)
	 * @see #setPropertyValue(Object, Object)
	 */
	static {
		descriptors = new IPropertyDescriptor[] { 
//				new TextPropertyDescriptor(XPOS_PROP, "X"), // id and description pair
//				new TextPropertyDescriptor(YPOS_PROP, "Y"),
//				new TextPropertyDescriptor(WIDTH_PROP, "Width"),
//				new TextPropertyDescriptor(HEIGHT_PROP, "Height"),
				new TextPropertyDescriptor(ID_PROP, "Id"),
				
				
		};
		// use a custom cell editor validator for all four array entries
//		for (int i = 0; i < 4; i++) {
//			((PropertyDescriptor) descriptors[i]).setValidator(new ICellEditorValidator() {
//				public String isValid(Object value) {
//					int intValue = -1;
//					try {
//						intValue = Integer.parseInt((String) value);
//					} catch (NumberFormatException exc) {
//						return "Not a number";
//					}
//					return (intValue >= 0) ? null : "Value must be >=  0";
//				}
//			});
//		}
	} // static



	/**
	 * Add an incoming or outgoing connection to this shape.
	 * @param conn a non-null connection instance
	 * @throws IllegalArgumentException if the connection is null or has not distinct endpoints
	 */
	void addConnection(TransitionElement conn) {
		if (conn == null || conn.getSource() == conn.getTarget()) {
			throw new IllegalArgumentException();
		}
		if (conn.getSource() == this) {
			sourceConnections.add(conn);
			firePropertyChange(SOURCE_CONNECTIONS_PROP, null, conn);
		} else if (conn.getTarget() == this) {
			targetConnections.add(conn);
			firePropertyChange(TARGET_CONNECTIONS_PROP, null, conn);
		}
	}



	/**
	 * Return the Location of this shape.
	 * @return a non-null location instance
	 */
	public Point getLocation() {
		return location.getCopy();
	}

	public String getId() {
		return id ;
	}
	
	/**
	 * Returns an array of IPropertyDescriptors for this shape.
	 * <p>The returned array is used to fill the property view, when the edit-part corresponding
	 * to this model element is selected.</p>
	 * @see #descriptors
	 * @see #getPropertyValue(Object)
	 * @see #setPropertyValue(Object, Object)
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	/**
	 * Return the property value for the given propertyId, or null.
	 * <p>The property view uses the IDs from the IPropertyDescriptors array 
	 * to obtain the value of the corresponding properties.</p>
	 * @see #descriptors
	 * @see #getPropertyDescriptors()
	 */
	public Object getPropertyValue(Object propertyId) {
		if (XPOS_PROP.equals(propertyId)) {
			return Integer.toString(location.x);
		}
		if (YPOS_PROP.equals(propertyId)) {
			return Integer.toString(location.y);
		}
		if (HEIGHT_PROP.equals(propertyId)) {
			return Integer.toString(size.height);
		}
		if (WIDTH_PROP.equals(propertyId)) {
			return Integer.toString(size.width);
		}
		if (ID_PROP.equals(propertyId)) {
			return id;
		}

		return super.getPropertyValue(propertyId);
	}

	/**
	 * Return the Size of this shape.
	 * @return a non-null Dimension instance
	 */
	public Dimension getSize() {
		return size.getCopy();
	}

	/**
	 * Return a List of outgoing Connections.
	 */
	public List getSourceConnections() {
		return new ArrayList(sourceConnections);
	}

	/**
	 * Return a List of incoming Connections.
	 */
	public List getTargetConnections() {
		return new ArrayList(targetConnections);
	}

	/**
	 * Remove an incoming or outgoing connection from this shape.
	 * @param conn a non-null connection instance
	 * @throws IllegalArgumentException if the parameter is null
	 */
	void removeConnection(TransitionElement conn) {
		if (conn == null) {
			throw new IllegalArgumentException();
		}
		if (conn.getSource() == this) {
			sourceConnections.remove(conn);
			firePropertyChange(SOURCE_CONNECTIONS_PROP, null, conn);
		} else if (conn.getTarget() == this) {
			targetConnections.remove(conn);
			firePropertyChange(TARGET_CONNECTIONS_PROP, null, conn);
		}
	}

	/**
	 * Set the Location of this shape.
	 * @param newLocation a non-null Point instance
	 * @throws IllegalArgumentException if the parameter is null
	 */
	public void setLocation(Point newLocation) {
		if (newLocation == null) {
			throw new IllegalArgumentException();
		}
		location.setLocation(newLocation);
		firePropertyChange(LOCATION_PROP, null, location);
	}

	/**
	 * Set the property value for the given property id.
	 * If no matching id is found, the call is forwarded to the superclass.
	 * <p>The property view uses the IDs from the IPropertyDescriptors array to set the values
	 * of the corresponding properties.</p>
	 * @see #descriptors
	 * @see #getPropertyDescriptors()
	 */
	public void setPropertyValue(Object propertyId, Object value) {
		if (XPOS_PROP.equals(propertyId)) {
			int x = Integer.parseInt((String) value);
			setLocation(new Point(x, location.y));
		} else if (YPOS_PROP.equals(propertyId)) {
			int y = Integer.parseInt((String) value);
			setLocation(new Point(location.x, y));
		} else if (HEIGHT_PROP.equals(propertyId)) {
			int height = Integer.parseInt((String) value);
			setSize(new Dimension(size.width, height));
		} else if (WIDTH_PROP.equals(propertyId)) {
			int width = Integer.parseInt((String) value);
			setSize(new Dimension(width, size.height));
		} else if (ID_PROP.equals(propertyId)) {
			
			id = (String) value;
		} else {
			super.setPropertyValue(propertyId, value);
		}
	}

	/**
	 * Set the Size of this shape.
	 * Will not modify the size if newSize is null.
	 * @param newSize a non-null Dimension instance or null
	 */
	public void setSize(Dimension newSize) {
		if (newSize != null) {
			size.setSize(newSize);
			firePropertyChange(SIZE_PROP, null, size);
		}
	}

	public void load(XMLElement st) {
		myElement = st;
		if(myElement!=null) {
			id = myElement.getStringAttribute("id");
		}
	}

	public void loadState() {
		if(myElement==null) {
			return;
		}
		Vector transitions = myElement.getChildren();
		for (int i = 0; i < transitions.size(); i++) {
			XMLElement currentChild = (XMLElement)transitions.get(i);
			if (currentChild.getName().equals("transition")) {
				
				
				String id = currentChild.getStringAttribute("nextstate");
				StateElement dest = root.getShape(id);
				try {
					TransitionElement s = new TransitionElement(this,dest);
					s.load(currentChild);
				
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//	addConnection(s);
			} else {
				if(currentChild.getName().equals("task")) {
					TaskElement te = new TaskElement();
					te.setRoot(root);
					te.load(currentChild);
					te.setState(this);
					tasks.add(te);
				}
		
			}
		}
		setSize(new Dimension(150,30*tasks.size()+50));
	}

	public Image getIcon() {
		return ELLIPSE_ICON;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		if(id==null) {
			return "Finished";
		}
		if(id.equals("null")) {
			return "Finished";
		}
		return id;
	}
	
	public List getChildren() {
		return sourceConnections;
	}



	public List getTasks() {
		// TODO Auto-generated method stub
		return tasks;
	}

	


	public XMLElement toXml() {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.setName("state");
		for (int i = 0; i < tasks.size(); i++) {
			TaskElement st = (TaskElement)tasks.get(i);
			xe.addChild(st.toXml());
		}
		for (int i = 0; i < sourceConnections.size(); i++) {
			TransitionElement st = (TransitionElement)sourceConnections.get(i);
			xe.addChild(st.toXml());
		}
		xe.setAttribute("id", id);
		return xe;
	}
	
}
