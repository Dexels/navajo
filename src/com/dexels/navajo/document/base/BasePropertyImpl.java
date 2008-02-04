package com.dexels.navajo.document.base;

import java.beans.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

import javax.swing.tree.TreeNode;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;

/**
 * <p>
 * Title: ShellApplet
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Part of the Navajo mini client, based on the NanoXML parser
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * c
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class BasePropertyImpl extends BaseNode implements Property, Comparable<Property>, TreeNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5167262782916246791L;
	protected String myName;
	protected String myValue = null;

	protected ArrayList<BaseSelectionImpl> selectionList = new ArrayList<BaseSelectionImpl>();
	protected String type = null;
	protected String cardinality = null;
	protected String description = null;
	protected String direction = Property.DIR_IN;
	protected int length = -1;
	private Map<String,String> subtypeMap = null;

	protected Binary myBinary = null;

	protected Property definitionProperty = null;

	// private String myMessageName = null;
	private Message myParent = null;
	private Vector<String>[] myPoints = null;

	protected boolean isListType = false;

	private Object evaluatedValue = null;
	private String evaluatedType = null;
	private List<PropertyChangeListener> myPropertyDataListeners;
	protected String subType = null;
	private Object tipiProperty = null;

	public BasePropertyImpl(Navajo n, String name, String type, String value, int i, String desc, String direction) {
		super(n);
		isListType = false;
		myName = name;
		myValue = value;
		this.type = type;
		this.length = i;
		this.description = desc;
		this.direction = direction;
		if (subType == null && NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
			setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
		}

		// dateFormat.pa
	}

	public BasePropertyImpl(Navajo n, String name, String type, String value, int i, String desc, String direction, String subType) {
		super(n);
		isListType = false;
		myName = name;
		myValue = value;
		// if (type==null || "".equals(type)) {
		// type = STRING_PROPERTY;
		// }
		this.type = type;

		this.length = i;
		this.description = desc;
		this.direction = direction;
		if (subType == null && NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
			setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
		} else {
			setSubType(subType);
		}
	}

	public BasePropertyImpl(Navajo n, String name, String cardinality, String desc, String direction) {
		super(n);
		isListType = true;
		myName = name;
		// myValue = "list";
		this.cardinality = cardinality;
		this.description = desc;
		this.direction = direction;
		this.type = "selection";
		if (subType == null && NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
			setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
		}

	}

	public void setSubType(String subType) {
		this.subType = subType;
		subtypeMap = NavajoFactory.getInstance().parseSubTypes(subType);
	}

	private void addSubType(String extra) {
		if (subtypeMap == null) {
			subtypeMap = NavajoFactory.getInstance().parseSubTypes(subType);
		}
		StringTokenizer st = new StringTokenizer(extra, "=");
		String key = st.nextToken();
		String value = st.nextToken();
		subtypeMap.put(key, value);
		subType = serializeSubtypes();
		// TODO Auto-generated method stub

	}

	public String getSubType() {
		return subType;
	}

	public BasePropertyImpl(Navajo n, String name) {
		super(n);
		myName = name;

	}

	public final String getName() {
		return myName;
	}

	public final int getLength() {
		return length;
	}

	public final void setLength(int i) {
		length = i;
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String s) {
		description = s;
	}

	public final String getCardinality() {
		return cardinality;
	}

	public final void setCardinality(String c) {
		cardinality = c;
	}

	public final String getDirection() {
		return direction;
	}

	public final void setDirection(String s) {
		direction = s;
	}

	// This is NOT the FULL name!!
	public final String getFullPropertyName() {
		if (getParentMessage() != null) {
			return getParentMessage().getFullMessageName() + "/" + getName();
		} else {
			return getName();
		}
	}

	@SuppressWarnings("deprecation")
	public final String getValue() {
		if (myBinary != null) {
			System.err.println("Do you REALLY want this binary as a string? You really shouldn't..");
			Thread.dumpStack();
			return myBinary.getBase64();
		}
		return myValue;
	}

	public Object peekEvaluatedValue() {
		return evaluatedValue;
	}

	public final void setAnyValue(Object o) {
		myBinary = null;
		
		if (o == null) {
//			setType(Property.STRING_PROPERTY);
			setValue((String) null);
			return;
		}
		if (o instanceof Integer) {
//			setType(Property.INTEGER_PROPERTY);
			setValue((Integer) o);
			return;
		}
		if (o instanceof Double) {
//			setType(Property.FLOAT_PROPERTY);
			setValue((Double) o);
			return;
		}
		if (o instanceof Float) {
//			setType(Property.FLOAT_PROPERTY);
			setValue((Float) o);
			return;
		}
		if (o instanceof Binary) {
//			setType(Property.BINARY_PROPERTY);
			setValue((Binary) o);
			return;
		}
		if (o instanceof ClockTime) {
//			setType(Property.CLOCKTIME_PROPERTY);
			setValue((ClockTime) o);
			return;
		}
		if (o instanceof StopwatchTime) {
//			setType(Property.STOPWATCHTIME_PROPERTY);
			setValue((StopwatchTime) o);
			return;
		}
		if (o instanceof Date) {
//			setType(Property.DATE_PROPERTY);
			setValue((Date) o);
			return;
		}
		if (o instanceof Long) {
//			setType(Property.LONG_PROPERTY);
			setLongValue(((Long) o).longValue());
			return;
		}
		if (o instanceof Money) {
//			setType(Property.MONEY_PROPERTY);
			setValue((Money) o);
			return;
		}
		if (o instanceof Percentage) {
//			setType(Property.PERCENTAGE_PROPERTY);
			setValue((Percentage) o);
			return;
		}
		if (o instanceof Boolean) {
//			setType(Property.BOOLEAN_PROPERTY);
			setValue((Boolean) o);
			return;
		}
		if (o instanceof String) {
			setType(Property.STRING_PROPERTY);
			setValue((String) o);
			return;
		}
		Object old = getTypedValue();
		setType(Property.TIPI_PROPERTY);
		tipiProperty = o;
		setCheckedValue("" + o);
		setType(Property.TIPI_PROPERTY);
		firePropertyChanged(old, getTypedValue());
	}

	public final Object getEvaluatedValue()  {
		// System.err.println("Evaluating property: "+getValue());
		Operand o;
		// No evaluator present.
		// Object oldEvaluatedValue = evaluatedValue;
		if (NavajoFactory.getInstance().getExpressionEvaluator() == null) {
			return null;
		}
		try {
			try {
				if (!EXPRESSION_PROPERTY.equals(getType())) {
					throw NavajoFactory.getInstance().createNavajoException("Can only evaluate expression type properties!");
				}
				try {
					o = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(getValue(), getRootDoc(), null, getParentMessage());
					evaluatedType = o.type;
					return o.value;
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					System.err.println("Exception while evaluating property: " + getFullPropertyName() + " expression: " + getValue());

					// throw(e);
					// e.printStackTrace();
					return null;
				}

			} catch (NavajoException ex) {
				// System.err.println("value problem: "+ex.getMessage());

				ex.printStackTrace();
				// The expression could not be evaluated. This happens
				// sometimes, but
				// some ui components still want to know the type. This
				// elaborate construction
				// will try to retrieve the type from a definition message in an
				// array message.
				// This, of course, only works for array message with a
				// definition message present.

				if (myParent != null) {
					Message pp = myParent.getArrayParentMessage();
					if (pp != null && Message.MSG_TYPE_ARRAY.equals(pp.getType())) {
						Message def = pp.getDefinitionMessage();
						if (def != null) {
							Property ppp = def.getProperty(getName());
							if (ppp != null) {
								evaluatedType = ppp.getType();
								return null;
							}
						}
					}
				}
				evaluatedType = "string";
				return null;
			}
		} catch (Throwable ex1) {
			evaluatedType = "string";
			return null;
		}
	}

	public final String getEvaluatedType() throws NavajoException {
		if (!EXPRESSION_PROPERTY.equals(getType())) {
			return getType();
		}
		if (evaluatedType == null) {
			try {
				refreshExpression();
			} catch (ExpressionChangedException e) {
			}
		}
		return evaluatedType;
	}

	public final void refreshExpression() throws NavajoException, ExpressionChangedException {
		if (getType().equals(Property.EXPRESSION_PROPERTY)) {
			// also sets evaluatedType
			Object oldEvaluatedValue = evaluatedValue;
			evaluatedValue = getEvaluatedValue();
			if (evaluatedValue instanceof ArrayList) {
				updateExpressionSelections((ArrayList) evaluatedValue);
				firePropertyChanged("selection", "", " ");
				write(System.err);
			} else {
				if (oldEvaluatedValue != null) {
					if (!oldEvaluatedValue.equals(evaluatedValue)) {
						firePropertyChanged("value", "" + oldEvaluatedValue, "" + evaluatedValue);
						throw new ExpressionChangedException();
					}
				}
				if (evaluatedValue != null) {
					if (!evaluatedValue.equals(oldEvaluatedValue)) {
						firePropertyChanged("value", "" + oldEvaluatedValue, "" + evaluatedValue);
						throw new ExpressionChangedException();
					}

				}
			}

		}
	}

	private void updateExpressionSelections(ArrayList<Selection> list) throws NavajoException {
		removeAllSelections();
		for (int i = 0; i < list.size(); i++) {
			Selection s = list.get(i);
			Selection copy = NavajoFactory.getInstance().createSelection(getRootDoc(), s.getName(), s.getValue(), false);
			addSelection(copy);
		}

	}

	/**
	 * Get the value of a property as a Java object.
	 * 
	 * @return
	 */
	public final Object getTypedValue() {

		// if (myValue == null && !SELECTION_PROPERTY.equals(getType())) {
		// return null;
		// }
		// System.err.println("MYVALUE: "+myValue);
		if (getType().equals(EXPRESSION_PROPERTY)) {
				if (evaluatedValue == null) {
					evaluatedValue = getEvaluatedValue();
					return evaluatedValue;
				} else {
					return evaluatedValue;
				}
			
		}

		if (getType().equals(Property.BOOLEAN_PROPERTY)) {
			if (getValue() != null && !getValue().equals("")) {
				return Boolean.valueOf(getValue().equals("true"));
			} else {
				return null;
			}
		}
		if (getType().equals(Property.PERCENTAGE_PROPERTY)) {
			if (getValue() != null) {
				return new Percentage(getValue());
			} else {
				return null;
			}
		}

		else if (getType().equals(Property.STRING_PROPERTY)) {
			return getValue();
		} else if (getType().equals(Property.MONEY_PROPERTY)) {
			if (getValue() == null || "".equals(getValue())) {
				return new Money((Double) null, getSubType());
			}
			String val = getValue();
//			System.err.println("VALUEEEEEE: "+val);
//			String val2 = val.replace(".", "");
//			String val3 = val2.replace(',', '.');
			
			NumberFormat fn = NumberFormat.getNumberInstance(Locale.US);
			
			Number parse;
			try {
				parse = fn.parse(val);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
			return new Money(parse.doubleValue(), getSubType());
		} else if (getType().equals(Property.CLOCKTIME_PROPERTY)) {
			if (getValue() == null || getValue().equals("")) {
				return null;
			}
			try {
				return new ClockTime(getValue(), getSubType());
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		} else if (getType().equals(Property.STOPWATCHTIME_PROPERTY)) {
			try {
				return new StopwatchTime(getValue(), getSubType());
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}

		else if (getType().equals(Property.DATE_PROPERTY)) {
			if (getValue() == null || getValue().equals("")) {
				return null;
			}

			try {
				Date d = dateFormat1.parse(getValue());
				return d;
			} catch (Exception ex) {
				try {
					Date d = dateFormat2.parse(getValue());
					return d;
				} catch (Exception ex2) {
					ex2.printStackTrace();
					System.err.println("Sorry I really can't parse that date: " + getValue());

					ex2.printStackTrace();
				}
			}
		} else if (getType().equals(Property.INTEGER_PROPERTY)) {
			if (getValue() == null || getValue().equals("")) {
				return null;
			}
			try {
				// Added a trim. Frank.
				return new Integer(Integer.parseInt(getValue().trim()));
			} catch (NumberFormatException ex3) {
				System.err.println("Numberformat exception...:"+getValue().trim());
				ex3.printStackTrace();
				return null;
			}
		} else if (getType().equals(Property.LONG_PROPERTY)) {
			if (getValue() == null || getValue().equals("")) {
				return null;
			}
			try {
				// Added a trim. Frank.
				return new Long(Long.parseLong(getValue().trim()));
			} catch (NumberFormatException ex3) {
				System.err.println("Numberformat exception...");
				return null;
			}
		} else if (getType().equals(Property.FLOAT_PROPERTY)) {
			if (getValue() == null || getValue().equals("")) {
				return null;
			}
			String v = getValue();
			String w = v;
			// Sometimes the numberformatting creates
			if (v.indexOf(",") != -1) {
				w = v.replaceAll(",", "");
			}
			Double d;
			try {
				d = new Double(Double.parseDouble(w));
			} catch (NumberFormatException ex) {
				System.err.println("Can not format double with: " + w);
				return null;
			}
			return d;
		} else if (getType().equals(Property.BINARY_PROPERTY)) {
			try {
				// if (myBinary!=null) {
				return myBinary;
				// }
				// if (myValue==null) {
				// return null;
				// }
				// // This is weird, should not happen.
				// return new Binary(new StringReader(getValue()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (getType().equals(Property.SELECTION_PROPERTY)) {

			Selection s = getSelected();
			if (s != null) {
				return s.getValue();
			} else {
				return null;
			}

		} else if (getType().equals(Property.TIPI_PROPERTY)) {
			return tipiProperty;
		}

		return getValue();
	}

	public final void clearValue() {
		myValue = null;
	}

	public final void setValue(InputStream is) {
		throw new UnsupportedOperationException("Unsupported method. Please use setValue(Binary)");
	}

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
	}

	public final void setValue(Binary b) {
		myBinary = b;
		myValue = null;
		Object old = getTypedValue();
		setType(BINARY_PROPERTY);
		if (b != null) {
			addSubType("handle=" + b.getHandle());
			addSubType("mime=" + b.getMimeType());
			addSubType("extension=" + b.getExtension());
		}
		firePropertyChanged("value", old, getTypedValue());

	}

	/**
	 * @deprecated Not really deprecated but needs a less memory intensive
	 *             rewrite. TODO (non-Javadoc)
	 * @see com.dexels.navajo.document.Property#setValue(java.net.URL)
	 */

	@Deprecated
	public final void setValue(URL url) {
		Object old = getTypedValue();
		try {
			if (type.equals(BINARY_PROPERTY)) {
				InputStream in = url.openStream();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] data;
				byte[] buffer = new byte[1024];
				int available;
				while ((available = in.read(buffer)) > -1) {
					bos.write(buffer, 0, available);
				}
				bos.flush();
				data = bos.toByteArray();
				bos.close();
				in.close();
				setValue(new Binary(data));
			} else {
				System.err.println("-------> setValue(URL) not supported for other property types than BINARY_PROPERTY");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(java.util.Date value) {
		Object old = getTypedValue();
		setType(DATE_PROPERTY);

		if (value != null) {
			setCheckedValue(dateFormat1.format(value));
		} else {
			myValue = null;
		}
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(Boolean value) {
		Object old = getTypedValue();
		setType(BOOLEAN_PROPERTY);
		if (value != null) {
			setCheckedValue((value.booleanValue() ? "true" : "false"));
		} else {
			myValue = null;
		}
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(Money value) {
		Object old = getTypedValue();
		setType(MONEY_PROPERTY);

		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(Percentage value) {
		Object old = getTypedValue();
		setType(PERCENTAGE_PROPERTY);
		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(ClockTime value) {
		Object old = getTypedValue();
		setType(CLOCKTIME_PROPERTY);
		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(StopwatchTime value) {
		Object old = getTypedValue();
		setType(STOPWATCHTIME_PROPERTY);

		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(Double value) {
		Object old = getTypedValue();
		setType(FLOAT_PROPERTY);
		if (value != null) {
			setCheckedValue(value.doubleValue() + "");
		} else {
			myValue = null;
		}
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(Integer value) {
		Object old = getTypedValue();
		setType(INTEGER_PROPERTY);
		if (value != null) {
			setCheckedValue(value.intValue() + "");
		} else {
			myValue = null;
		}
		firePropertyChanged("value", old, value);
	}

	public final void setValue(int value) {
		Object old = getTypedValue();
		setType(INTEGER_PROPERTY);
		setCheckedValue(value + "");
		firePropertyChanged("value", old, value);
	}

	public final void setValue(double value) {
		Object old = getTypedValue();
		setType(FLOAT_PROPERTY);
		setCheckedValue(value + "");
		firePropertyChanged("value", old, value);
	}

	public final void setValue(float value) {
		Object old = getTypedValue();
		String floatString = "" + value;
		setType(FLOAT_PROPERTY);
		setCheckedValue(floatString);
		firePropertyChanged("value", old, value);
	}

	public void setValue(boolean value) {
		Object old = getTypedValue();
		setType(BOOLEAN_PROPERTY);
		setCheckedValue((value ? "true" : "false"));
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setLongValue(long value) {
		Object old = getTypedValue();
		setType(LONG_PROPERTY);
		setCheckedValue(value + "");
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(long value) {
		Object old = getTypedValue();
		setType(LONG_PROPERTY);
		setCheckedValue(value + "");
		firePropertyChanged("value", old, getTypedValue());
	}

	public final void setValue(String value) {
		String old = getValue();
		if (BINARY_PROPERTY.equals(getType())) {
			System.err.println("Warning: Very deprecated. use setValue(Binary) instead");
			try {
				if (value != null) {
					myBinary = new Binary(new StringReader(value));
				} else {
					myBinary = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		} 
		myBinary = null;
		setCheckedValue(value);
		firePropertyChanged("value", old, value);

	}

	protected void firePropertyChanged(Object oldValue, Object newValue) {
		firePropertyChanged("value", oldValue, newValue);
	}

	/**
	 * Will check for non-changes, comprendes?
	 * 
	 * @param name
	 * @param oldValue
	 * @param newValue
	 */
	protected void firePropertyChanged(String name, Object oldValue, Object newValue) {
		// System.err.println("Property changed: "+getName()+" old: "+oldValue+"
		// new:
		// "+newValue);
		if (myPropertyDataListeners == null) {
			return;
		}
		if (oldValue == null && newValue == null) {
			return;
		}
		if (oldValue != null) {
			if (oldValue.equals(newValue)) {
				return;
			}
		}
		if (newValue != null) {
			if (newValue.equals(oldValue)) {
				return;
			}
		}

		if (myPropertyDataListeners != null) {
			for (int i = 0; i < myPropertyDataListeners.size(); i++) {
				PropertyChangeListener c = myPropertyDataListeners.get(i);
				c.propertyChange(new PropertyChangeEvent(this, name, oldValue, newValue));
				// System.err.println("Alpha: PROPERTY DATA CHANGE Fired: " +
				// oldValue + " - " +
				// newValue);
				// Thread.dumpStack();
			}
		}
		if (getParentMessage() != null) {
			getParentMessage().firePropertyDataChanged(this, oldValue, newValue);
		}
	}

	public final void setValue(Selection[] l) {
		Object old = getTypedValue();
		myBinary = null;
		if (l == null) {
			return;
		}
		try {
			this.removeAllSelections();
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < l.length; i++) {
			this.addSelection(l[i]);
		}
		firePropertyChanged("value", old, getTypedValue());
	}

	public final String getSubType(String key) {
		if (subtypeMap != null) {
			return subtypeMap.get(key);
		}
		return null;

	}

	private String serializeSubtypes() {
		if (subtypeMap == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		if (definitionProperty == null) {
			for (Iterator<String> iter = subtypeMap.keySet().iterator(); iter.hasNext();) {
				String item = iter.next();
				String value = subtypeMap.get(item);
				sb.append(item + "=" + value + (iter.hasNext() ? "," : ""));
			}
			return sb.toString();
		} else {
			for (Iterator<String> iter = subtypeMap.keySet().iterator(); iter.hasNext();) {
				String item = iter.next();
				String value = subtypeMap.get(item);
				String defvalue = definitionProperty.getSubType(item);
				if (value == null) {
					sb.append(item + "=" + (iter.hasNext() ? "," : ""));
				} else {
					if (!value.equals(defvalue)) {
						sb.append(item + "=" + value + (iter.hasNext() ? "," : ""));
					}
				}
			}
			System.err.println("Subtypes: " + sb.toString());
			return sb.toString();
		}

	}

	public final String setCheckedValue(String v) {

		// if (EXPRESSION_PROPERTY.equals(getType())&&
		// "Description".equals(getName())) {
		// System.err.println("SETTING VALUE: "+value);
		// Thread.dumpStack();
		// }
		String value = null;
		try {
			value = PropertyTypeChecker.getInstance().verify(this, v);
		} catch (PropertyTypeException ex1) {
			value = null;
		}

		if (value != null) {
			try {
				if (getType().equals(SELECTION_PROPERTY)) {
					// System.err.println("Setting value of selection
					// property");
					setSelectedByValue(value);
				} else {
					myValue = value;
				}
			} catch (NavajoException ex) {
				ex.printStackTrace();
			}
		} else {
			myValue = null;
		}
		return value;
	}

	public final void setName(String name) {
		myName = name;
	}

	public Property getDefinitionProperty() {
		if (getParentMessage() == null) {
			return null;
		}
		Message arrayParent = getParentMessage().getArrayParentMessage();
		if (arrayParent == null) {
			return null;
		}
		return arrayParent.getPathProperty(getName());

	}

	public final String getType() {
		if (this.type == null || "".equals(this.type)) {
			Property def = getDefinitionProperty();
			if (def != null) {
				return def.getType();
			} else {
				// System.err.println("Warning: Property without type. Reverting
				// to String type");
				return STRING_PROPERTY;
			}
		} else {
			return (this.type);
		}
	}

	public final void setType(String t) {
		type = t;
	}

	public final String toString() {
		if (getType().equals(Property.DATE_PROPERTY)) {
			return (this.getTypedValue() != null) ? dateFormat3.format((Date) this.getTypedValue()) : null;
		} else if (getType().equals(Property.SELECTION_PROPERTY)) {
			return this.getSelected().getName();
		} else if (getType().equals(Property.BOOLEAN_PROPERTY)) {
			if (Locale.getDefault().getLanguage().equals("nl")) {
				return (getValue().equals("true") ? "ja" : "nee");
			} else {
				return (getValue().equals("true") ? "yes" : "no");
			}
		} else {
			return getValue();
		}
	}

	public final ArrayList<Selection> getAllSelections() {
		if (selectionList == null) {
			return new ArrayList<Selection>();
		}
		ArrayList<Selection> l = new ArrayList<Selection>(selectionList);
		return l;
	}

	public final void setSelected(Selection s) {
		if (selectionList == null) {
			return;
		}
		String oldSel = null;
		Selection old = getSelected();
		if (old != null) {
			oldSel = old.getValue();
		}

		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			if (current == s) {
				current.setSelected(true);
			} else {
				if (!"+".equals(getCardinality())) {
					current.setSelected(false);
				}
			}
			firePropertyChanged("selection", oldSel, current.getValue());
		}
	}

	public final void setAllSelected(boolean b) {
		if (selectionList == null) {
			return;
		}
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			current.setSelected(b);
		}
	}

	public final void setSelectedByValue(Object value) throws NavajoException {
		if (selectionList == null) {
			return;
		}
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			if (current.getValue() == null) {
				continue;
			}
			if (current.getValue().equals(value)) {
				if (!getCardinality().equals("+")) {
					clearSelections();
				}
				setSelected(current);
				// ((BaseSelectionImpl) current).setSelected(true);
			} else {
				// current.setSelected(false);
			}
		}
	}

	public final boolean isEditable() {
		return (Property.DIR_IN.equals(direction) || Property.DIR_INOUT.equals(direction));
	}

	public final void addSelection(Selection s) {
		if (selectionList == null) {
			selectionList = new ArrayList<BaseSelectionImpl>();
		}

		int max = selectionList.size();
		boolean selected = s.isSelected();
		for (int i = 0; i < max; i++) {
			Selection t = selectionList.get(i);
			if (t.getName().equals(s.getName())) {
				// System.err.println("REMOVING SELECTION!");
				selected = t.isSelected();
				selectionList.remove(i);
				max--;
			}

		}
		((BaseSelectionImpl) s).setParent(this);

		if (selected) {
			s.setSelected(true);
		}
		selectionList.add((BaseSelectionImpl) s);

	}

	public final void removeSelection(Selection s) {
		if (selectionList == null) {
			return;
		}
		selectionList.remove(s);
	}

	public final void removeAllSelections() throws NavajoException {
		if (selectionList == null) {
			return;
		}
		selectionList = new ArrayList<BaseSelectionImpl>();
	}

	public final Selection getSelection(String name) {
		if (selectionList == null) {
			return NavajoFactory.getInstance().createDummySelection();
		}
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			if (current.getName().equals(name)) {
				return current;
			}
		}
		return NavajoFactory.getInstance().createDummySelection();
	}

	public final Selection getSelectionByValue(String value) {
		if (selectionList == null) {
			return NavajoFactory.getInstance().createDummySelection();
		}
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			if (current != null && current.getValue().equals(value)) {
				return current;
			}
		}
		return NavajoFactory.getInstance().createDummySelection();
		// return null;
	}

	public final Selection getSelected() {
		if (selectionList == null) {
			return NavajoFactory.getInstance().createDummySelection();
		}
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			// System.err.println("CHECKING:::: "+current);
			if (current != null && current.isSelected()) {
				return current;
			}
		}
		// return null;
		return NavajoFactory.getInstance().createDummySelection();
	}

	public final Property copy(Navajo n) {
		BasePropertyImpl cp;
		try {
			if (getType() == null || "".equals(getType())) {
				throw new IllegalStateException("Can not copy property without type: " + getFullPropertyName());
			}
			if (SELECTION_PROPERTY.equals(getType())) {

				cp = (BasePropertyImpl) NavajoFactory.getInstance().createProperty(n, getName(), getCardinality(), getDescription(),
						getDirection());
			} else {

				cp = (BasePropertyImpl) NavajoFactory.getInstance().createProperty(n, getName(), getType(), null, getLength(),
						getDescription(), getDirection(), getSubType());
				Object value = getTypedValue();
				cp.setAnyValue(value);
				cp.setType(getType());
			}
		} catch (NavajoException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex.toString());
		}
		cp.setRootDoc(n);
		ArrayList<Selection> mySel = getAllSelections();
		for (int i = 0; i < mySel.size(); i++) {
			BaseSelectionImpl current = (BaseSelectionImpl) mySel.get(i);
			BaseSelectionImpl cc = (BaseSelectionImpl) current.copy(n);
			cp.addSelection(cc);
		}
		return cp;
	}

	public final void prune() {
		ArrayList<Selection> mySel = getAllSelections();
		for (int i = 0; i < mySel.size(); i++) {
			Selection current = mySel.get(i);
			if (!current.isSelected()) {
				removeSelection(current);
			}
		}
	}

	public final void setParent(Message m) {
		myParent = m;
	}

	public final void setParentMessage(Message m) {
		setParent(m);
	}

	public final Message getParentMessage() {
		return myParent;
	}

	public final String getPath() {
		if (myParent != null) {
			return myParent.getFullMessageName() + "/" + getName();
		} else {
			return "/" + getName();
		}
	}

	public int hashCode() {
		return super.hashCode();
	}

	public boolean equals(Object o) {
		return super.equals(o);
	}

	// public int compare(Property p) {
	// Comparable ob1 = (Comparable)getAlternativeTypedValue();
	// Comparable ob2 = (Comparable)p.getAlternativeTypedValue();
	// return ob1.compareTo(ob2);
	// }
	@SuppressWarnings("null")
	public final int compareTo(Property p) {

		try {
			if (p == null) {
				return 0;
			}
			Comparable ob1;
			Comparable ob2;

			if (getType().equals(Property.BOOLEAN_PROPERTY)) {
				Boolean bool1 = (Boolean) getTypedValue();
				boolean b1 = bool1.booleanValue();
				Boolean bool2 = (Boolean) p.getTypedValue();
				boolean b2 = bool2.booleanValue();
				if (b1 == b2) {
					return 0;
				} else if (b1) { // Define false < true
					return 1;
				} else {
					return -1;
				}
			}

			// Get first argument.
			if (getType().equals(Property.SELECTION_PROPERTY)) {
				if (getSubType("name") != null && getSubType("name").equals("integer")) {
					ob1 = new Integer(getSelected().getName());
				} else {
					ob1 = getSelected().getName();
				}
			} else {
				ob1 = (Comparable<?>) getTypedValue();
			}

			// Get second argument.
			if (((BasePropertyImpl) p).getType().equals(Property.SELECTION_PROPERTY)) {
				BasePropertyImpl cp = (BasePropertyImpl) p;
				if (getSubType("name") != null && cp.getSubType("name").equals("integer")) {
					ob2 = new Integer(cp.getSelected().getName());
				} else {
					ob2 = ((BasePropertyImpl) p).getSelected().getName();
				}
			} else {
				ob2 = (Comparable<?>) ((BasePropertyImpl) p).getTypedValue();
			}

			if (ob1 == null && ob2 == null) {
				return 0;
			}

			if (ob1 == null && ob2 != null) {
				return -1;
			}

			if (ob2 == null && ob1 != null) {
				return 1;
			}

			if (!Property.class.isInstance(p)) {
				return 0;
			}

			try {
				int i = ob1.compareTo(ob2);
				return i;
			} catch (Throwable t) {
				return 0;
			}

		} catch (Throwable t2) {
			return 0;
		}

	}


	public void setSelected(String[] s) throws com.dexels.navajo.document.NavajoException {
		if (!getType().equals(SELECTION_PROPERTY)) {
			throw NavajoFactory.getInstance().createNavajoException("Setting selected of non-selection property!");
		}

		setAllSelected(false);
		for (int i = 0; i < s.length; i++) {
			Selection st = getSelectionByValue(s[i]);
			st.setSelected(true);
		}

	}

	public final void addSelectionWithoutReplace(Selection s) throws com.dexels.navajo.document.NavajoException {
		if (selectionList == null) {
			selectionList = new ArrayList<BaseSelectionImpl>();
		}

		selectionList.add((BaseSelectionImpl)s);
		((BaseSelectionImpl) s).setParent(this);
	}

	public final void setSelected(ArrayList<String> al) throws com.dexels.navajo.document.NavajoException {
		setAllSelected(false);

		for (int i = 0; i < al.size(); i++) {
			String s = al.get(i);
			Selection sl = getSelectionByValue(s);
			setSelected(sl.getValue());
		}

	}

	public final ArrayList<Selection> getAllSelectedSelections() throws com.dexels.navajo.document.NavajoException {
		ArrayList<Selection> list = new ArrayList<Selection>();
		ArrayList<Selection> al = getAllSelections();
		for (int i = 0; i < al.size(); i++) {
			BaseSelectionImpl s = (BaseSelectionImpl) al.get(i);
			if (s.isSelected()) {
				list.add(s);
			}
		}
		return list;

	}

	public final void setSelected(String value) throws com.dexels.navajo.document.NavajoException {
		if (!"+".equals(getCardinality())) {
			clearSelections();
		}
		String oldSel = null;
		Selection sel = getSelected();
		if (sel != null) {
			oldSel = sel.getValue();
		}
		Selection s = getSelectionByValue(value);
		firePropertyChanged("selection", oldSel, value);
		s.setSelected(true);
	}

	public final void clearSelections() throws com.dexels.navajo.document.NavajoException {
		ArrayList<Selection> al = getAllSelections();
		for (int i = 0; i < al.size(); i++) {
			BaseSelectionImpl s = (BaseSelectionImpl) al.get(i);
			s.setSelected(false);
		}

	}

	public final Selection existsSelection(String name) throws com.dexels.navajo.document.NavajoException {
		return getSelection(name);
	}

	// public String getFullPropertyName() throws NavajoException {
	// return getPath();
	// }
	public final boolean isDirIn() {
		if (getDirection() == null) {
			return false;
		}
		return getDirection().equals(DIR_IN) || getDirection().equals(DIR_INOUT);
	}

	public final boolean isDirOut() {
		if (getDirection() == null) {
			return false;
		}
		return getDirection().equals(DIR_OUT) || getDirection().equals(DIR_INOUT);
	}



	public final boolean isEqual(Property p) {

		if (!getName().equals(p.getName())) {
			return false;
		}

		if ((this.getType() != null && p.getType() != null) && !this.getType().equals(p.getType())) {
			System.err.println("isEqual() property, types are not equal");
			return false;
		}

		// Check for date properties.
		if (p.getType().equals(Property.DATE_PROPERTY)) {

			// If both values are null they're equal.
			if (p.getTypedValue() == null && this.getTypedValue() == null) {
				return true;
			}

			// If only one of them is null they're not equal.
			if (p.getTypedValue() == null || this.getTypedValue() == null) {
				return false;
			}

			java.util.Date myDate = (java.util.Date) getTypedValue();
			java.util.Date otherDate = (java.util.Date) p.getTypedValue();
			if (dateFormat2.format(myDate).equals(dateFormat2.format(otherDate))) {
				return true;
			} else {
				return false;
			}
		}
		// Check for selection properties.
		else if (p.getType().equals(Property.SELECTION_PROPERTY)) {
			try {
				ArrayList<Selection> l = p.getAllSelectedSelections();
				ArrayList<Selection> me = this.getAllSelectedSelections();

				// If number of selected selections is not equal they're not
				// equal.
				if (me.size() != l.size()) {
					return false;
				}

				for (int j = 0; j < l.size(); j++) {
					Selection other = l.get(j);
					boolean match = false;
					for (int k = 0; k < me.size(); k++) {
						Selection mysel = me.get(k);
						if (mysel.getValue().equals(other.getValue())) {
							match = true;
							k = me.size() + 1;
						}
					}
					if (!match) {
						return false;
					}
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} else if (p.getType().equals(Property.BINARY_PROPERTY)) {

			// If both values are null they're equal.
			if (p.getTypedValue() == null && this.getTypedValue() == null) {
				return true;
			}

			// If only one of them is null they're not equal.
			if (p.getTypedValue() == null || this.getTypedValue() == null) {
				return false;
			}

			return ((Binary) this.getTypedValue()).isEqual((Binary) p.getTypedValue());
		}
		// Else I am some other property.
		else {

			// If both values are null they're equal.
			// if (p.getTypedValue() == null && this.getTypedValue() == null) {
			// return true;
			// }
			if (p.getValue() == null && this.getValue() == null) {
				return true;
			}

			// If only one of them is null they're not equal.
			// if (p.getTypedValue() == null || this.getTypedValue() == null) {
			// return false;
			// }
			if (p.getValue() == null || this.getValue() == null) {
				return false;
			}

			// We are only equal if our values match exactly..

			boolean result = p.getValue().equals(this.getValue());
			if (!result) {
				System.err.println("isEqual() property, values are not equal");
			}
			return result;
		}

	}

	public void addExpression(ExpressionTag e) {
		throw new java.lang.UnsupportedOperationException("Method addExpression() not yet implemented.");
	}

	public Map<String,String> getAttributes() {
		Map<String,String> m = new HashMap<String,String>();
		if (cardinality != null) {
			m.put("cardinality", cardinality);
		}
		if (direction != null) {
			m.put("direction", direction);
		}
		if (description != null) {
			m.put("description", description);
		}
		if (myBinary != null) {
			m.put("length", "" + myBinary.getLength());
		} else {
			if (length > 0) {
				m.put("length", "" + length);
			} else {
				m.put("length", "");
			}
		}
		if (myName != null) {
			m.put("name", myName);
		}
		if (myValue != null) {
			m.put("value", myValue);
		}
		if (type != null) {
			m.put("type", type);
		}
		if (subType != null) {
			m.put("subtype", subType);
		}
		return m;
	}

	public List<? extends BaseNode> getChildren() {
		return selectionList;
	}

	public Object getRef() {
		throw new UnsupportedOperationException("getRef not possible on base type");
	}

	public Property cloneWithoutValue() {
		if (isListType) {
			BasePropertyImpl prop = new BasePropertyImpl(getRootDoc(), getName(), cardinality, getDescription(), getDirection());
			ArrayList<Selection> ap = getAllSelections();
			for (int i = 0; i < ap.size(); i++) {
				Selection s = ap.get(i);
				Selection newS = NavajoFactory.getInstance().createSelection(getRootDoc(), s.getName(), s.getValue(), s.isSelected());
				prop.addSelection(newS);
			}
			return prop;
		} else {
			return new BasePropertyImpl(getRootDoc(), getName(), getType(), null, getLength(), getDescription(), getDirection());
		}
	}

	public Object clone(String newName) {
		Property p = cloneWithoutValue();
		p.setName(newName);
		return p;
	}

	public Object clone() {

		Property p = cloneWithoutValue();
		p.setName(getName());
		if (BINARY_PROPERTY.equals(p.getType())) {
			p.setValue(myBinary);
		} else {
			p.setValue(getValue());
		}
		return p;
	}

	public String getTagName() {
		return Property.PROPERTY_DEFINITION;
	}

	public void writeText(Writer w) throws IOException {
		if (myBinary != null) { 
			myBinary.writeBase64(w);
		}

	}

	public boolean hasTextNode() {
		return myBinary != null;
	}



	public void addPropertyChangeListener(PropertyChangeListener p) {
		if (myPropertyDataListeners == null) {
			myPropertyDataListeners = new ArrayList<PropertyChangeListener>();
		}
		myPropertyDataListeners.add(p);
		if (myPropertyDataListeners.size() > 1) {
			System.err.println("Multiple property listeners detected!" + myPropertyDataListeners.size());
		}
	}

	public void removePropertyChangeListener(PropertyChangeListener p) {
		if (myPropertyDataListeners == null) {
			return;
		}
		myPropertyDataListeners.remove(p);
	}

	public static void main(String[] args) {

		System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");

		BaseNavajoImpl n = new BaseNavajoImpl();
		BaseMessageImpl m = new BaseMessageImpl(n, "Aap");
		BasePropertyImpl p1 = new BasePropertyImpl(n, "Noot");
		m.addProperty(p1);
		p1.setType("string");
		p1.setValue("CHGP12Y");

		BaseNavajoImpl n2 = new BaseNavajoImpl();
		BaseMessageImpl m2 = new BaseMessageImpl(n2, "Aap");
		BasePropertyImpl p2 = new BasePropertyImpl(n2, "Noot");
		m2.addProperty(p2);
		p2.setType("string");
		p2.setValue("CHGP12Y");

		System.err.println(m.isEqual(m2));
	}

	public void setSelected(Selection s, boolean selected) throws NavajoException {
		// TODO Auto-generated method stub
		// System.err.println("Wrning not really good");
		if (selected) {
			setSelected(s);
		} else {
			if (!"+".equals(getCardinality())) {
				s.setSelected(false);
				clearSelections();
			}
		}
	}

	public void forcePropertyChange() {
		if (myPropertyDataListeners != null) {
			for (int i = 0; i < myPropertyDataListeners.size(); i++) {
				PropertyChangeListener c = myPropertyDataListeners.get(i);
				
				c.propertyChange(new PropertyChangeEvent(this, "value", getTypedValue(), getTypedValue()));
				// System.err.println("Alpha: PROPERTY DATA CHANGE Fired: " +
				// oldValue + " - " +
				// newValue);
				// Thread.dumpStack();
			}
		}
	}
	
	/**
	 * Methods below are needed for TreeNode interface. Should be refactored in the future, but there
	 * is a dependency in JTreeTable (NavajoSwingClient).
	 */
	public int getChildCount() {
		return 0;
	}

	public boolean getAllowsChildren() {
		return false;
	}

	public boolean isLeaf() {
		return true;
	}

	public Enumeration children() {
		return null;
	}
	
	public TreeNode getChildAt(int childIndex) {
		return null;
	}

	public TreeNode getParent() {
		return (TreeNode) getParentMessage();
	}

	public int getIndex(TreeNode node) {
		return 0;
	}

}
