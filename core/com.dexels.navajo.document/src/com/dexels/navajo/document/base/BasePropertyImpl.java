package com.dexels.navajo.document.base;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.DocumentPropertyChangeEvent;
import com.dexels.navajo.document.ExpressionChangedException;
import com.dexels.navajo.document.ExpressionTag;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.PropertyTypeChecker;
import com.dexels.navajo.document.PropertyTypeException;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.NavajoExpression;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.types.StopwatchTime;

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

public class BasePropertyImpl extends BaseNode implements Property, Comparable<Property> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5167262782916246791L;
	protected String myName;
	protected String myValue = null;
	private static final Logger logger = LoggerFactory.getLogger(BasePropertyImpl.class);

	protected final ArrayList<BaseSelectionImpl> selectionList = new ArrayList<BaseSelectionImpl>() {
		private static final long serialVersionUID = 2460743050491944290L;

		public String toString() {
			if(size()==0) {
				return Selection.DUMMY_ELEMENT;
			}
			if(size()==1) {
				return get(0).getValue();
			}
			int index = 0;
			StringBuffer sb = new StringBuffer();
			for (Selection s  : this) {
				sb.append(s.getValue());
				if(index<size()-1) {
					sb.append(",");
				} 
				index++;
			}
			return sb.toString();
		}
	};
	protected String type = null;
	protected String cardinality = null;
	protected String description = null;
	protected String direction = Property.DIR_IN;
	protected String key = null;
	protected String reference = null;
	protected String myExtends = null;
	protected int length = -1;
	private Map<String,String> subtypeMap = null;

	protected Binary myBinary = null;

	protected Property definitionProperty = null;

	// private String myMessageName = null;
	private Message myParent = null;
//	private Vector<String>[] myPoints = null;

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
		this.type = SELECTION_PROPERTY;
		if (subType == null && NavajoFactory.getInstance().getDefaultSubtypeForType(type) != null) {
			setSubType(NavajoFactory.getInstance().getDefaultSubtypeForType(type));
		}

	}

	/**
	 * Alternative (different case), to ease the mapping onto the tsl
	 * @param subType
	 */
	public void setSubtype(String subType) {
		setSubType(subType);
	}

	/**
	 * Alternative (different case), to ease the mapping onto the tsl
	 */
	public String getSubtype() {
		return getSubType();
	}

	
	public void setSubType(String subType) {
		this.subType = subType;
		subtypeMap = NavajoFactory.getInstance().parseSubTypes(subType);
	}
	

	public void addSubType(String extra) {
		StringTokenizer st = new StringTokenizer(extra, "=");
		String key = st.nextToken();
		String value = st.nextToken();
		addSubType(key, value);
	}

	public void addSubType(String key, String value) {
		String oldSubType = subType;
		if (subtypeMap == null) {
			subtypeMap = NavajoFactory.getInstance().parseSubTypes(subType);
		}
		subtypeMap.put(key, value);
		subType = serializeSubtypes();
		firePropertyChanged("subType", oldSubType, subType);
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
		int old = length;
		length = i;
		firePropertyChanged(PROPERTY_LENGTH, old, length);
	}

	public final String getDescription() {
		return description;
	}

	public final void setDescription(String s) {
		String old = description;
		description = s;
		firePropertyChanged(PROPERTY_DESCRIPTION, old, description);
	}

	public final String getCardinality() {
		return cardinality;
	}

	public final void setCardinality(String c) {
		String old = cardinality;
		cardinality = c;
		firePropertyChanged(PROPERTY_CARDINALITY, old, cardinality);
	}

	public final String getDirection() {
		return direction;
	}

	public final void setDirection(String s) {
		String old = direction;
		direction = s;
		firePropertyChanged(PROPERTY_DIRECTION, old, direction);
	}

	// This is NOT the FULL name!!
	public final String getFullPropertyName() {
		if (getParentMessage() != null) {
			return getParentMessage().getFullMessageName() + "/" + getName();
		}
		return getName();
	}

	@SuppressWarnings("deprecation")
	public final String getValue() {
		if (myBinary != null) {
			logger.info("Do you REALLY want this binary as a string? You really shouldn't..");
			Thread.dumpStack();
			return myBinary.getBase64();
		}
		return myValue;
	}

	public Object peekEvaluatedValue() {
		return evaluatedValue;
	}

	private boolean isStringType(String type) {
		for (String c : STRING_DATA_TYPES) {
			if(c.equals(type)) {
				return true;
			}
		}
		return false;
	}
	
	public final void clearValue() {
		clearValue(false);
	}
	
	private final void clearValue(Boolean internal) {
		//setValue((String) null);
		Object o  = getTypedValue();
		tipiProperty = null;
		// Similar to setAnyValue, only here the type will be determined based on the current type,
		// not on the new type.
		// This is still important for firing update events
		myValue = null;
		myBinary = null;
		selectionList.clear();
		
		firePropertyChanged(o, null, internal);
	}
	public final void setAnyValue(Object o) {
		setAnyValue(o, false);
	}
	public final void setAnyValue(Object o, Boolean internal) {
//		myBinary = null;
		if(myBinary!=null) {
			if(o instanceof Binary) {
				// piggy backing on a similar implementation that just inverts the meaning
				setValue((Binary)o, !internal);
			} else {
				myBinary = null;
			}
		}
		if (o == null) {
			clearValue(internal);
			return;
		}
		if (o instanceof Integer) {
			setValue((Integer) o, internal);
			return;
		}
		if (o instanceof Double) {
			setValue((Double) o, internal);
			return;
		}
		if (o instanceof Float) {
			setValue((Float) o, internal);
			return;
		}
		if (o instanceof Binary) {
			// piggy backing on a similar implementation that just inverts the meaning
			setValue((Binary) o, !internal);
			return;
		}
		if (o instanceof ClockTime) {
			setValue((ClockTime) o, internal);
			return;
		}
		if (o instanceof StopwatchTime) {
			setValue((StopwatchTime) o, internal);
			return;
		}
		if (o instanceof Date) {
			setValue((Date) o, internal);
			return;
		}
		if (o instanceof Long) {
			setLongValue(((Long) o).longValue(), internal);
			return;
		}
		if (o instanceof Money) {
			setValue((Money) o, internal);
			return;
		}
		if (o instanceof Percentage) {
			setValue((Percentage) o, internal);
			return;
		}
		if (o instanceof Boolean) {
			setValue((Boolean) o, internal);
			return;
		}
		if (o instanceof ArrayList) {
			setValue((ArrayList<?>) o);
			return;
		}		
		if (o instanceof String) {
			if(!isStringType(getType())) {
				setType(Property.STRING_PROPERTY);
			}
			setValue((String) o, internal);
			return;
		}
		Object old = getTypedValue();
		setType(Property.TIPI_PROPERTY);
		tipiProperty = o;
		setCheckedValue("" + o);
		setType(Property.TIPI_PROPERTY);
		firePropertyChanged(old, getTypedValue(), internal);
	}

	private void setValue(List<?> list){
		// first, determine content of list.
		if(list.isEmpty()) {
			// tricky. Will assume it is a selection property, for backward compatibility.
			setSelectionList(list);
			return;
		}
		
		Object first = list.get(0);
		if(first==null) {
			logger.info("Cannot process null values in list or selection");
			return;
		}
		if(first instanceof Selection) {
			// based on this, I'll assume it is a  selection property
			setSelectionList(list);
			return;
		}
		setListProperty(list);
		
	}

	private void setListProperty(List<?> list) {
		tipiProperty = list;
		setType(Property.LIST_PROPERTY);
	}

	private void setSelectionList(List<?> list) {
		ArrayList<String> values = new ArrayList<String>();
		for (Object s : list) {
			values.add(((Selection)s).getValue());
		}
		try {
			setSelected(values);
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		setType(Property.SELECTION_PROPERTY);
	
	}
	
	public final Object getEvaluatedValue()  {
		// logger.info("Evaluating property: "+getValue());
		Operand o;
		// No evaluator present.
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
					logger.info("Exception while evaluating property: " + getFullPropertyName() + " expression: " + getValue());
					return null;
				}

			} catch (NavajoException ex) {
				logger.error("Error: ", ex);
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

	@SuppressWarnings("unchecked")
	public final void refreshExpression() throws NavajoException, ExpressionChangedException {
		if (getType().equals(Property.EXPRESSION_PROPERTY)) {
			// also sets evaluatedType
			Object oldEvaluatedValue = evaluatedValue;
			evaluatedValue = getEvaluatedValue();
			if (evaluatedValue instanceof ArrayList) {
				updateExpressionSelections((ArrayList<Selection>) evaluatedValue);
				firePropertyChanged("selection", "", " ");
				write(System.err);
			} else {
				if (oldEvaluatedValue != null) {
					if (!oldEvaluatedValue.equals(evaluatedValue)) {
						firePropertyChanged(PROPERTY_VALUE, oldEvaluatedValue, evaluatedValue);
						throw new ExpressionChangedException();
					}
				}
				if (evaluatedValue != null) {
					if (!evaluatedValue.equals(oldEvaluatedValue)) {
						firePropertyChanged(PROPERTY_VALUE, "" + oldEvaluatedValue, "" + evaluatedValue);
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
		// logger.info("MYVALUE: "+myValue);
		if (getType().equals(Property.STRING_PROPERTY)) {
			return getValue();
		}
		
		if (getType().equals(Property.BOOLEAN_PROPERTY)) {
			if (getValue() != null && !getValue().equals("")) {
				return Boolean.valueOf(getValue().equals("true"));
			} else {
				return null;
			}
		}
		
		if (getType().equals(EXPRESSION_LITERAL_PROPERTY)) {
			if (getValue() != null && !getValue().equals("")) {
				return new NavajoExpression(getValue());
			} else {
				return null;
			}
		}
		
		if (getType().equals(EXPRESSION_PROPERTY)) {
				if (evaluatedValue == null) {
					evaluatedValue = getEvaluatedValue();
					return evaluatedValue;
				} else {
					return evaluatedValue;
				}
			
		}

		if (getType().equals(Property.PERCENTAGE_PROPERTY)) {
			if (getValue() != null) {
				return new Percentage(getValue());
			} else {
				return null;
			}
		}

		if (getType().equals(Property.MONEY_PROPERTY)) {
			if (getValue() == null || "".equals(getValue())) {
				return new Money((Double) null, getSubType());
			}
			String val = getValue();
//			logger.info("VALUEEEEEE: "+val);
//			String val2 = val.replace(".", "");
//			String val3 = val2.replace(',', '.');
			
			NumberFormat fn = NumberFormat.getNumberInstance(Locale.US);
			
			Number parse;
			try {
				parse = fn.parse(val);
			} catch (ParseException e) {
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
				logger.error("Error: ", e);
			}
		} else if (getType().equals(Property.STOPWATCHTIME_PROPERTY)) {
			try {
				return new StopwatchTime(getValue(), getSubType());
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		}

		else if (getType().equals(Property.DATE_PROPERTY)) {
			if (getValue() == null || getValue().equals("")) {
				return null;
			}

			SimpleDateFormat dateFormat1 = new SimpleDateFormat( Property.DATE_FORMAT1 );
		  
			try {
				Date d = dateFormat1.parse(getValue());
				return d;
			} catch (Exception ex) {
				try {
					SimpleDateFormat dateFormat2 = new SimpleDateFormat( Property.DATE_FORMAT4 );
					Date d = dateFormat2.parse(getValue());
					return d;
				} catch (Exception ex2) {
					try {
						SimpleDateFormat dateFormat2 = new SimpleDateFormat( Property.DATE_FORMAT2 );
						Date d = dateFormat2.parse(getValue());
						return d;
					} catch (Exception ex3) {
						logger.info("Sorry I really can't parse that date: " + getValue());
					}
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
				logger.info("Numberformat exception...:"+getValue().trim());
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
				logger.info("Numberformat exception...");
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
				logger.info("Can not format double with: " + w);
				return null;
			}
			return d;
		} else if (getType().equals(Property.BINARY_PROPERTY)) {
			try {
				return myBinary;
			} catch (Exception e) {
				logger.error("Error: ", e);
			}
		} else if (getType().equals(Property.SELECTION_PROPERTY)) {
			List<Selection> all = getAllSelectedSelections();
			return all;
		} else if (getType().equals(Property.TIPI_PROPERTY) || getType().equals(Property.LIST_PROPERTY) ) {
			return tipiProperty;
		}

		return getValue();
	}

//	public final void clearValue() {
//		myValue = null;
//	}

//	private final void copyResource(OutputStream out, InputStream in) throws IOException {
//		BufferedInputStream bin = new BufferedInputStream(in);
//		BufferedOutputStream bout = new BufferedOutputStream(out);
//		byte[] buffer = new byte[1024];
//		int read;
//		while ((read = bin.read(buffer)) > -1) {
//			bout.write(buffer, 0, read);
//		}
//		bin.close();
//		bout.flush();
//		bout.close();
//	}

	public final void setValue(Binary b) {
		setValue(b,true);
	}
	public final void setValue(Binary b, boolean fireUpdateEvent) {
		Object old = getTypedValue();
		myBinary = b;
		myValue = null;
		setType(BINARY_PROPERTY);
		if (b != null) {
			addSubType("handle=" + b.getHandle());
			addSubType("mime=" + b.getMimeType());
			addSubType("extension=" + b.getExtension());
		}
		if(fireUpdateEvent) {
			firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), false);
		}

	}

	/**
	 * @deprecated Not really deprecated but needs a less memory intensive
	 *             rewrite. 
	 * @see com.dexels.navajo.document.Property#setValue(java.net.URL)
	 */

	@Deprecated
	public final void setValue(URL url) {
			setValue(url, false);
	}

	/**
	 * @deprecated Not really deprecated but needs a less memory intensive
	 *             rewrite. 
	 * @see com.dexels.navajo.document.Property#setValue(java.net.URL)
	 */

	@Deprecated
	private final void setValue(URL url, Boolean internal) {
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
				logger.info("-------> setValue(URL) not supported for other property types than BINARY_PROPERTY");
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}

	public final void setValue(java.util.Date value) {
		setValue(value, false);
	}
	
	private final void setValue(java.util.Date value, Boolean internal) {
		
		SimpleDateFormat dateFormat1 = new SimpleDateFormat( Property.DATE_FORMAT1 );
	    
		Object old = getTypedValue();
		setType(DATE_PROPERTY);

		if (value != null) {
			setCheckedValue(dateFormat1.format(value));
		} else {
			myValue = null;
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}

	public final void setValue(Boolean value) {
		setValue(value, false);
	}
	
	private final void setValue(Boolean value, Boolean internal) {
		Object old = getTypedValue();
		setType(BOOLEAN_PROPERTY);
		if (value != null) {
			setCheckedValue((value.booleanValue() ? "true" : "false"));
		} else {
			myValue = null;
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}

	public void setValue(boolean value) {
		setValue(value, false);
	}
	
	public final void setValue(NavajoExpression ne) {
		setType(Property.EXPRESSION_LITERAL_PROPERTY);
		if ( ne != null ) {
			setCheckedValue(ne.toString());
		}
	}
	
	public final void setValue(Money value) {
		setValue(value, false);
	}
	
	private final void setValue(Money value, Boolean internal) {
		Object old = getTypedValue();
		setType(MONEY_PROPERTY);

		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}

	public final void setValue(Percentage value) {
		setValue(value, false);
	}
	
	private final void setValue(Percentage value, Boolean internal) {
		Object old = getTypedValue();
		setType(PERCENTAGE_PROPERTY);
		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}

	public final void setValue(ClockTime value) {
		setValue(value, false);
	}
	
	private final void setValue(ClockTime value, Boolean internal) {
		Object old = getTypedValue();
		setType(CLOCKTIME_PROPERTY);
		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}

	public final void setValue(StopwatchTime value) {
		setValue(value, false);
	}
	
	private final void setValue(StopwatchTime value, Boolean internal) {
		Object old = getTypedValue();
		setType(STOPWATCHTIME_PROPERTY);

		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}

	public final void setValue(double value) {
		setValue(value, false);
	}
	
	public final void setValue(Double value) {
		setValue(value, false);
	}
	
	private final void setValue(Double value, Boolean internal) {
		Object old = getTypedValue();
		setType(FLOAT_PROPERTY);
		if (value != null) {
			setCheckedValue(value.doubleValue() + "");
		} else {
			myValue = null;
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}
	
	public final void setValue(float value) {
		setValue(value, false);
	}
	
	private final void setValue(Float value, Boolean internal) {
		Object old = getTypedValue();
		setType(FLOAT_PROPERTY);
		if (value != null) {
			setCheckedValue(value.floatValue() + "");
		} else {
			myValue = null;
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}

	public final void setValue(int value) {
		setValue(Integer.valueOf(value), false);
	}

	public final void setValue(Integer value) {
		setValue(value, false);
	}
	
	private final void setValue(Integer value, Boolean internal) {
		Object old = getTypedValue();
		setType(INTEGER_PROPERTY);
		if (value != null) {
			setCheckedValue(value.intValue() + "");
		} else {
			myValue = null;
		}
		firePropertyChanged(PROPERTY_VALUE, old, value, internal);
	}

	public final void setLongValue(long value) {
		setLongValue(value, false);
	}
	
	private final void setLongValue(long value, Boolean internal) {
		Object old = getTypedValue();
		setType(LONG_PROPERTY);
		setCheckedValue(value + "");
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
	}

	public final void setValue(long value) {
		setLongValue(value, false);
	}
	
	public final void setValue(String value) {
		setValue(value, false);
	}
	
	private final void setValue(String value, Boolean internal) {
		String old = getValue();
		if (BINARY_PROPERTY.equals(getType())) {
			//logger.info("Warning: Very deprecated. use setValue(Binary) instead");
			try {
				if (value != null) {
					myBinary = new Binary(new StringReader(value));
				} else {
					myBinary = null;
				}
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			return;
		} 
		myBinary = null;
		setCheckedValue(value);
		firePropertyChanged(PROPERTY_VALUE, old, value, internal);

	}

	protected void firePropertyChanged(Object oldValue, Object newValue) {
		firePropertyChanged(oldValue, newValue, false);
	}
	
	protected void firePropertyChanged(Object oldValue, Object newValue, Boolean internal) {
		firePropertyChanged(PROPERTY_VALUE, oldValue, newValue, internal);
	}

	/**
	 * Will check for non-changes, comprendes?
	 * 
	 * @param name
	 * @param oldValue
	 * @param newValue
	 */
	protected void firePropertyChanged(String name, Object oldValue, Object newValue) {
		firePropertyChanged(name, oldValue, newValue, false);
	}
	
	/**
	 * Will check for non-changes, comprendes?
	 * 
	 * @param name
	 * @param oldValue
	 * @param newValue
	 */
	protected void firePropertyChanged(String name, Object oldValue, Object newValue, Boolean internal) {
		if (myPropertyDataListeners != null) {

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
					c.propertyChange(new DocumentPropertyChangeEvent(this, name, oldValue, newValue, internal));

				}
			}
		}

		if (getParentMessage() != null) {
			getParentMessage().firePropertyDataChanged(this, oldValue, newValue);
		}
	}

	public final void setValue(Selection[] l) {
		setValue(l, false);
	}
	
	private final void setValue(Selection[] l, Boolean internal) {
		Object old = getTypedValue();
		myBinary = null;
		if (l == null) {
			return;
		}
		try {
			this.removeAllSelections();
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		}
		for (int i = 0; i < l.length; i++) {
			this.addSelection(l[i]);
		}
		firePropertyChanged(PROPERTY_VALUE, old, getTypedValue());
	}

	public final String getSubType(String key) {
		if (subtypeMap != null) {
			return subtypeMap.get(key);
		}
		return null;

	}
	public final Map<String,String> getSubTypes() {
		if(subtypeMap==null) {
			return  new HashMap<String, String>();
		}
		return new HashMap<String, String>(subtypeMap);
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
			logger.info("Subtypes: " + sb.toString());
			return sb.toString();
		}

	}

	public final void setUnCheckedStringAsValue(String s) {
		myValue = s;
	}
	
	public final String setCheckedValue(String v) {

		// if (EXPRESSION_PROPERTY.equals(getType())&&
		// PROPERTY_DESCRIPTION.equals(getName())) {
		// logger.info("SETTING VALUE: "+value);
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
					// logger.info("Setting value of selection
					// property");
					setSelectedByValue(value);
				} else {
					myValue = value;
				}
			} catch (NavajoException ex) {
				logger.error("Error: ", ex);
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
			// logger.info("Warning: Property without type. Reverting
			// to String type");
			return STRING_PROPERTY;
		} else {
			return (this.type);
		}
	}

	public final void setType(String t) {
		String old = type;
		type = t;
		firePropertyChanged(PROPERTY_TYPE, old, type);
	}

	public final String toString() {
		
	    SimpleDateFormat dateFormat3 = new SimpleDateFormat( Property.DATE_FORMAT3 );
	    
		if (getType().equals(Property.DATE_PROPERTY)) {
			return (this.getTypedValue() != null) ? dateFormat3.format((Date) this.getTypedValue()) : "";
		} else if (getType().equals(Property.SELECTION_PROPERTY)) {
			return this.getSelected().getName();
		} else if (getType().equals(Property.BOOLEAN_PROPERTY)) {
			if(getValue()==null) {
				return "";
			}
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
			logger.warn("setSelected(selection) got null input"); 
			return;
		}
		List<Selection> old;
			old = getAllSelectedSelections();
		
//		String oldSel = null;
//		Selection old = getSelected();
//		if (old != null) {
//			oldSel = old.getValue();
//		}

		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			if (current.getName().equals(s.getName())) {
				current.setSelected(true);
			} else {
				if (!"+".equals(getCardinality())) {
					current.setSelected(false);
				}
			}
		}
//		if(!old.equals(current))
		
		List<Selection> newValue = getAllSelectedSelections();

		boolean isEqual = isEqual(old, newValue);
//		logger.info("OLD: "+old);
//		logger.info("NEW: "+newValue);
//		logger.info("isEqual: "+isEqual);
		if(!isEqual) { 
			firePropertyChanged("selection", old,newValue);
		}
	}
	private boolean isEqual(List<Selection> a,List<Selection> b) {
		if(a.size()!=b.size()) {
			return false;
		}
		int i = 0;
		for (Selection selection : a) {
			if(selection.getValue().equals(b.get(i++).getValue())) {
				//
			} else {
				return false;
			}
		}
		return true;
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
//		if (selectionList == null) {
//			selectionList = new ArrayList<BaseSelectionImpl>();
//		}

		int max = selectionList.size();
		boolean selected = s.isSelected();
		for (int i = 0; i < max; i++) {
			Selection t = selectionList.get(i);
			if (t.getName().equals(s.getName())) {
				// logger.info("REMOVING SELECTION!");
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
		selectionList.clear();
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

	/**
	 * I don't like this function, with its silly way of indicating null selections (== dummy selection)
	 * @deprecated
	 */
	public final Selection getSelected() {
		if (selectionList == null) {
			return NavajoFactory.getInstance().createDummySelection();
		}
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			// logger.info("CHECKING:::: "+current);
			if (current != null && current.isSelected()) {
				return current;
			}
		}
		// return null;
		return NavajoFactory.getInstance().createDummySelection();
	}

	public final Property copy(Navajo n) {
		BasePropertyImpl cp;
		if (getType() == null || "".equals(getType())) {
			throw new IllegalStateException("Can not copy property without type: " + getFullPropertyName());
		}
		if (SELECTION_PROPERTY.equals(getType())) {

			cp = (BasePropertyImpl) NavajoFactory.getInstance().createProperty(n, getName(), getCardinality(), getDescription(),
					getDirection());
		} else {

			cp = (BasePropertyImpl) NavajoFactory.getInstance().createProperty(n, getName(), getType(), null, getLength(),
					getDescription(), getDirection(), getSubType());
			
			if(EXPRESSION_PROPERTY.equals(getType())) {
				cp.setType(EXPRESSION_PROPERTY);
			} else {
				Object value = getTypedValue();
				cp.setAnyValue(value);
			}
			cp.setType(getType());
		}
		if ( getKey() != null ) {
			cp.setKey(getKey());
		}
		if ( getExtends() != null ) {
			cp.setExtends(getExtends());
		}
		if ( getReference() != null ) {
			cp.setReference(getReference());
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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

			if (ob1 == null) {
				return -1;
			}
			if (ob2 == null) {
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
//		if (selectionList == null) {
//			selectionList = new ArrayList<BaseSelectionImpl>();
//		}

		selectionList.add((BaseSelectionImpl)s);
		((BaseSelectionImpl) s).setParent(this);
	}

	public final void setSelected(ArrayList<String> al) throws com.dexels.navajo.document.NavajoException {
		List<Selection> old = new ArrayList<Selection>(getAllSelectedSelections());
		setAllSelected(false);
		for (int i = 0; i < al.size(); i++) {
			String s = al.get(i);
			Selection sl = getSelectionByValue(s);
			sl.setSelected(true);
//			setSelected(sl.getValue());
		}
		firePropertyChanged("selection", old, getAllSelectedSelections());
	}

	public final ArrayList<Selection> getAllSelectedSelections() {
		ArrayList<Selection>  list = new ArrayList<Selection>() {
			private static final long serialVersionUID = -2914783936108056853L;

			public String toString() {
				if(size()==0) {
					return Selection.DUMMY_ELEMENT;
				}
				if(size()==1) {
					return get(0).getValue();
				}
				int index = 0;
				StringBuffer sb = new StringBuffer();
				for (Selection s  : this) {
					sb.append(s.getValue());
					if(index<size()-1) {
						sb.append(",");
					} 
					index++;
				}
				return sb.toString();
			}
		};
		
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
			ArrayList<Selection> al = getAllSelections();
			for (int i = 0; i < al.size(); i++) {
				BaseSelectionImpl s = (BaseSelectionImpl) al.get(i);
				s.setSelected(false);
			}
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
		List<Selection> old = new ArrayList<Selection>(getAllSelectedSelections());
		ArrayList<Selection> al = getAllSelections();
		for (int i = 0; i < al.size(); i++) {
			BaseSelectionImpl s = (BaseSelectionImpl) al.get(i);
			s.setSelected(false);
		}
		firePropertyChanged("selection", old, getAllSelectedSelections());
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

	    SimpleDateFormat dateFormat2 = new SimpleDateFormat( Property.DATE_FORMAT2 );
	    
		if (!getName().equals(p.getName())) {
			return false;
		}

		if ((this.getType() != null && p.getType() != null) && !this.getType().equals(p.getType())) {
			logger.info("isEqual() property, types are not equal");
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
				logger.error("Error: ", e);
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
				logger.info("isEqual() property, values are not equal");
			}
			return result;
		}

	}

	public void addExpression(ExpressionTag e) {
		throw new java.lang.UnsupportedOperationException("Method addExpression() not yet implemented.");
	}

	public Map<String,String> getAttributes() {
		Map<String,String> m = new HashMap<String,String>();
		
		if ( myExtends != null ) {
			m.put(Property.PROPERTY_EXTENDS, myExtends);
		}
		
		if ( key != null ) {
			m.put(Property.PROPERTY_KEY, key);
		}
		
		if ( reference != null ) {
			m.put(Property.PROPERTY_REFERENCE, reference);
		}
		
		if (cardinality != null && Property.SELECTION_PROPERTY.equals(getType())) {
			m.put(PROPERTY_CARDINALITY, cardinality);
		}
		if (direction != null) {
			m.put(PROPERTY_DIRECTION, direction);
		}
		
		if (description != null && !description.equals("") ) {
			m.put(PROPERTY_DESCRIPTION, description);
		}
		if (myBinary != null) {
			m.put(PROPERTY_LENGTH, "" + myBinary.getLength());
		} else {
			if (length > 0) {
				m.put(PROPERTY_LENGTH, "" + length);
			} //else {
//				m.put(PROPERTY_LENGTH, "");
//			}
		}
		if (myName != null) {
			m.put(PROPERTY_NAME, myName);
		}
		if (myValue != null) {
			m.put(PROPERTY_VALUE, myValue);
		}
		if (type != null) {
			m.put(PROPERTY_TYPE, type);
		}
		if (subType != null && !subType.equals("") ) {
			m.put(PROPERTY_SUBTYPE, subType);
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
			Property prop = new BasePropertyImpl(getRootDoc(), getName(), getType(), null, getLength(), getDescription(), getDirection());
			if ( getKey() != null ) {
				prop.setKey(getKey());
			}
			if ( getReference() != null ) {
				prop.setReference(getReference());
			}
			if ( getExtends() != null ) {
				prop.setExtends(getExtends());
			}
			return prop;
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
		if(myPropertyDataListeners.contains(p)) {
			logger.info("IDENTICAL LISTENER!!!\n"+getFullPropertyName());
			return;
		}
		myPropertyDataListeners.add(p);
//		logger.info("Adding: "+p.getClass()+" to: "+getFullPropertyName());
//		Thread.dumpStack();

		if (myPropertyDataListeners.size() > 5) {
			logger.debug("Multiple property listeners detected!" + myPropertyDataListeners.size()+" path: "+getFullPropertyName());
			logger.debug(">>> "+myPropertyDataListeners);
		}
		if (myPropertyDataListeners.size() > 5) {
//			for (PropertyChangeListener xxp : myPropertyDataListeners) {
//				logger.info("Listener: "+xxp);
//			}
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

		NavajoFactory nf = NavajoFactory.getInstance();
		BaseNavajoImpl n = new BaseNavajoImpl(nf);
		BaseMessageImpl m = new BaseMessageImpl(n, "Aap");
		BasePropertyImpl p1 = new BasePropertyImpl(n, "Noot");
		m.addProperty(p1);
		p1.setType("string");
		p1.setValue("CHGP12Y");

		BaseNavajoImpl n2 = new BaseNavajoImpl(nf);
		BaseMessageImpl m2 = new BaseMessageImpl(n2, "Aap");
		BasePropertyImpl p2 = new BasePropertyImpl(n2, "Noot");
		m2.addProperty(p2);
		p2.setType("string");
		p2.setValue("CHGP12Y");

	}

	public void setSelected(Selection s, boolean selected) throws NavajoException {
		List<Selection> l = new ArrayList<Selection>( getAllSelectedSelections());
		for (Selection selection : getAllSelections()) {
			if (selection.equals(s)) {
				s.setSelected(selected);
			} else {
				if (!"+".equals(getCardinality())) {
					selection.setSelected(false);
				}
			}
		}

		List<Selection> l2 = getAllSelectedSelections();
		if(!l.equals(l2)) {
			firePropertyChanged(l, l2);
		}
	}

	public void forcePropertyChange() {
		if (myPropertyDataListeners != null) {
			for (int i = 0; i < myPropertyDataListeners.size(); i++) {
				PropertyChangeListener c = myPropertyDataListeners.get(i);
				
				c.propertyChange(new PropertyChangeEvent(this, PROPERTY_VALUE, getTypedValue(), getTypedValue()));
				// logger.info("Alpha: PROPERTY DATA CHANGE Fired: " +
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

	public Enumeration<?> children() {
		return null;
	}
	
	public void printElementJSONTypeless(final Writer sw) throws IOException {
		String value = getValue();
		if(getType().equals(Property.SELECTION_PROPERTY)){
			Selection s = getSelected();
			if(s != null){
				value = s.getValue();
			}
		}
		value = value.replace("\"", "\\\"");
		writeElement(sw, "\"" + getName() + "\" : \"" + value + "\"");		
	}

	@Override
	public void setKey(String key) {
		String old = this.key;
		this.key = key;
		firePropertyChanged(old, key);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void setReference(String ref) {
		String old = this.reference;
		this.reference = ref;
		firePropertyChanged(old, reference);
	}

	@Override
	public String getReference() {
		return reference;
	}

	@Override
	public void setExtends(String s) {
		String old = myExtends;
		this.myExtends = s;
		firePropertyChanged(old, myExtends);
	}

	@Override
	public String getExtends() {
		return myExtends;
	}

}
