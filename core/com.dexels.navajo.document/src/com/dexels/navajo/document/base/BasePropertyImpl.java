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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.customtypes.CoordinateType;
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
import com.dexels.navajo.document.types.BinaryDigest;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Coordinate;
import com.dexels.navajo.document.types.Memo;
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
	
	/*
	 * If we set a Date property, we keep our own copy of the date object. Uses a little more memory
	 * but saves us from having to use DateFormat.parse() on getTypedValue()
	 */
	protected Date myDate;
	
	private static final Logger logger = LoggerFactory.getLogger(BasePropertyImpl.class);
	private static final ThreadLocal<SimpleDateFormat> dateFormat1 = ThreadLocal.withInitial(()->{
        SimpleDateFormat sdf = new SimpleDateFormat(Property.DATE_FORMAT1);
        sdf.setLenient(false);
        return sdf;
	});
	
	private static final ThreadLocal<SimpleDateFormat> dateFormat4 = ThreadLocal.withInitial(()->{
        SimpleDateFormat sdf = new SimpleDateFormat(Property.DATE_FORMAT4);
        sdf.setLenient(false);
        return sdf;
	});
	private static final ThreadLocal<SimpleDateFormat> dateFormat2 = ThreadLocal.withInitial(()->{
        SimpleDateFormat sdf = new SimpleDateFormat(Property.DATE_FORMAT2);
        sdf.setLenient(false);
        return sdf;
	});
	private static final ThreadLocal<SimpleDateFormat> dateFormat3 = ThreadLocal.withInitial(()->{
            SimpleDateFormat sdf = new SimpleDateFormat(Property.DATE_FORMAT3);
            sdf.setLenient(false);
            return sdf;
    });
	private static final ThreadLocal<SimpleDateFormat> timestampFormat = ThreadLocal.withInitial(()->{
            SimpleDateFormat sdf = new SimpleDateFormat(Property.TIMESTAMP_FORMAT);
            sdf.setLenient(false);
            return sdf;
    });
	
	protected final transient ArrayList<BaseSelectionImpl> selectionList = new ArrayList<BaseSelectionImpl>() {
		private static final long serialVersionUID = 2460743050491944290L;

		@Override
		public String toString() {
			if(size()==0) {
				return Selection.DUMMY_ELEMENT;
			}
			if(size()==1) {
				return get(0).getValue();
			}
			int index = 0;
			StringBuilder sb = new StringBuilder();
			for (Selection s  : this) {
				sb.append(s.getValue());
				if(index<size()-1) {
					sb.append(',');
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
	protected String bind = null;
	protected String method = "";
	protected String myExtends = null;
	protected int length = -1;
	private Map<String,String> subtypeMap = null;

	protected Binary myBinary = null;

	protected Property definitionProperty = null;

	private Message myParent = null;
	protected boolean isListType = false;

	private transient Object evaluatedValue = null;
	private String evaluatedType = null;
	private transient List<PropertyChangeListener> myPropertyDataListeners;
	protected String subType = null;
	private transient Object tipiProperty = null;
	   
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

	
	@Override
	public void setSubType(String subType) {
		this.subType = subType;
		subtypeMap = NavajoFactory.getInstance().parseSubTypes(subType);
	}
	

	@Override
	public void addSubType(String extra) {
		StringTokenizer st = new StringTokenizer(extra, "=");
		String keySub = st.nextToken();
		String value = st.nextToken();
		addSubType(keySub, value);
	}

	@Override
	public void addSubType(String key, String value) {
		String oldSubType = subType;
		if (subtypeMap == null) {
			subtypeMap = NavajoFactory.getInstance().parseSubTypes(subType);
		}
		subtypeMap.put(key, value);
		subType = serializeSubtypes();
		firePropertyChanged("subType", oldSubType, subType);
	}


	
	@Override
	public String getSubType() {
		return subType;
	}

	public BasePropertyImpl(Navajo n, String name) {
		super(n);
		myName = name;

	}
	
	private boolean hasPropertyDataListeners() {
	    return myPropertyDataListeners != null;
	}

	@Override
	public final String getName() {
		return myName;
	}

	@Override
	public final int getLength() {
		return length;
	}

	@Override
	public final void setLength(int i) {
		int old = length;
		length = i;
		firePropertyChanged(PROPERTY_LENGTH, old, length);
	}

	@Override
	public final String getDescription() {
		return description;
	}

	@Override
	public final void setDescription(String s) {
		String old = description;
		description = s;
		firePropertyChanged(PROPERTY_DESCRIPTION, old, description);
	}

	@Override
	public final String getCardinality() {
		return cardinality;
	}

	@Override
	public final void setCardinality(String c) {
		String old = cardinality;
		cardinality = c;
		firePropertyChanged(PROPERTY_CARDINALITY, old, cardinality);
	}

	@Override
	public final String getDirection() {
		return direction;
	}

	@Override
	public final void setDirection(String s) {
		String old = direction;
		direction = s;
		firePropertyChanged(PROPERTY_DIRECTION, old, direction);
	}

	// This is NOT the FULL name!!
	@Override
	public final String getFullPropertyName() {
		if (getParentMessage() != null) {
			return getParentMessage().getFullMessageName() + "/" + getName();
		}
		return getName();
	}

	@Override
	@SuppressWarnings("deprecation")
	public final String getValue() {
		if (myBinary != null) {
			logger.info("Do you REALLY want this binary as a string? You really shouldn't..");
			return myBinary.getBase64();
		}
		return myValue;
	}

	@Override
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
	
	@Override
	public final void clearValue() {
		clearValue(false);
	}
	
	private final void clearValue(Boolean internal) {
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
	@Override
	public final void setAnyValue(Object o) {
		setAnyValue(o, false);
	}
	@Override
	public final void setAnyValue(Object o, Boolean internal) {
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
        if (o instanceof Coordinate) {
            setValue((Coordinate) o, internal);
            return;
        }
        if (o instanceof CoordinateType) {
            setValue((CoordinateType) o, internal);
            return;
        }
		if (o instanceof Memo) {
            setValue((Memo) o, internal);
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
		if (o instanceof List) {
			setValue((List<?>) o);
			return;
		}		
		if (o instanceof BinaryDigest) {
			setValue(((BinaryDigest) o).hex());
			setType(Property.BINARY_DIGEST_PROPERTY);
			return;
        }
        if (o instanceof String) {
            if (!isStringType(getType())) {
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

    private void setValue(List<?> list) {
		// first, determine content of list.
		if(list.isEmpty()) {
			// tricky. Will assume it is a selection property, for backward compatibility.
			setSelectionList(list);
			setListProperty(list);
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
		myValue = list.toString();
		setType(Property.LIST_PROPERTY);
	}

	private void setSelectionList(List<?> list) {
		ArrayList<String> values= new ArrayList<String>();
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
					o = NavajoFactory.getInstance().getExpressionEvaluator().evaluate(getValue(), getRootDoc(), null, getParentMessage(),null);
					evaluatedType = o.type;
					return o.value;
				} catch (Throwable e) {
					logger.info("Exception while evaluating property: {} expression: {}",getFullPropertyName(), getValue());
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

	@Override
	public final String getEvaluatedType() {
		if (!EXPRESSION_PROPERTY.equals(getType())) {
			return getType();
		}
		if (evaluatedType == null) {
			try {
				refreshExpression();
			} catch (ExpressionChangedException e) {
			    logger.warn("ExpressionChangedException error on getEvaluatedType: {}", e);
			}
		}
		return evaluatedType;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final void refreshExpression() throws ExpressionChangedException {
		if (getType().equals(Property.EXPRESSION_PROPERTY)) {
			// also sets evaluatedType
			Object oldEvaluatedValue = evaluatedValue;
			evaluatedValue = getEvaluatedValue();
			if (evaluatedValue instanceof ArrayList) {
				updateExpressionSelections((ArrayList<Selection>) evaluatedValue);
				firePropertyChanged("selection", "", " ");
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

	private void updateExpressionSelections(List<Selection> list) {
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
	@Override
	public final Object getTypedValue() {

		if (getType().equals(Property.STRING_PROPERTY)) {
			return getValue();
		}
		
		if (getType().equals(Property.BOOLEAN_PROPERTY)) {
			if (getValue() != null && !getValue().equals("")) {
                return Boolean.valueOf(getValue().equalsIgnoreCase("true"));
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
        } else if (getType().equals(Property.DATE_PROPERTY) || getType().equals(Property.TIMESTAMP_PROPERTY)) {
            if (getValue() == null || getValue().equals("") || getValue().equals("null")) {
                return null;
            }
            if (myDate != null) {
                return myDate;
            }
            // Try in order from most specific to least specific
            try {
            	return timestampFormat.get().parse(getValue());
            } catch (Exception ex) {
                try {
                	return dateFormat4.get().parse(getValue());
                } catch (Exception ex2) {
                    try {
                    	return dateFormat1.get().parse(getValue());
                    } catch (Exception ex3) {
                        try {
                        	return dateFormat2.get().parse(getValue());
                        } catch (Exception ex4) {
                            try {
                                Long l = Long.parseLong(getValue());
                                Date d = new java.util.Date();
                                d.setTime(l);
                                return d;
                            } catch (Exception e5) {
                                logger.info("Sorry I really can't parse that date: {}", getValue());
                                return null;
                            }
                        }
                    }
                }
            }
            
        } else if (getType().equals(Property.INTEGER_PROPERTY)) {
			if (getValue() == null || getValue().equals("") || getValue().trim().equals("null")) {
				return null;
			}
			try {
				return Integer.valueOf(Integer.parseInt(getValue().trim()));
			} catch (NumberFormatException ex3) {
				logger.info("Numberformat exception...: {}",getValue().trim());
				return null;
			}
		} else if (getType().equals(Property.LONG_PROPERTY)) {
			if (getValue() == null || getValue().equals("")) {
				return null;
			}
			try {
				// Added a trim. Frank.
				return Long.valueOf(Long.parseLong(getValue().trim()));
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
			// Sometimes the number formatting creates
			if (v.indexOf(',') != -1) {
				w = v.replaceAll(",", "");
			}
			Double d;
			try {
				d = Double.valueOf(Double.parseDouble(w));
			} catch (NumberFormatException ex) {
				logger.info("Can not format double with: {}", w);
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
			return getAllSelectedSelections();
		} else if (getType().equals(Property.TIPI_PROPERTY)) {
		    return tipiProperty;
		} else if (getType().equals(Property.LIST_PROPERTY) ) {
			if (tipiProperty != null || myValue == null) {
			    return tipiProperty;
			}
			try {
			    if (myValue.indexOf('[') == 0) {
	                // Parse back into a list
	                String stripped = myValue.substring(1,  myValue.length() -1);
	                tipiProperty = Arrays.asList(stripped.split(", "));
	                return tipiProperty;
	            } else if (myValue.length() > 0) {
	                logger.info("Failed to parse {} as a list!", myValue);
	            }
			} catch (Exception e ) {
			    logger.warn("Exception on parsing {} as a list!", myValue, e);
			}
			return null;
		} else if (getType().equals(Property.BINARY_DIGEST_PROPERTY) ) {
			return new BinaryDigest(getValue());
        } else if (getType().equals(Property.COORDINATE_PROPERTY)) {
            try {
                 return new Coordinate(myValue);
            } catch (Exception e) {
                logger.error("Cannot create Coordinate Property: ", e);
            }
		}

		return getValue();
	}

	@Override
	public final void setValue(Binary b) {
		setValue(b,true);
	}
	public final void setValue(Binary b, boolean fireUpdateEvent) {
	    Object old = null;
	    if (hasPropertyDataListeners()) {
	        old = getTypedValue();
	    }
		
		myBinary = b;
		myValue = null;
		setType(BINARY_PROPERTY);
		if (b != null) {
//			addSubType("handle=" + b.getHandle());     // Disabled to allow generating a hash over the output
			addSubType("mime=" + b.getMimeType());
			addSubType("extension=" + b.getExtension());
		}
		if(fireUpdateEvent && hasPropertyDataListeners()) {
			firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), false);
		}

	}

	/**
	 * @deprecated Not really deprecated but needs a less memory intensive
	 *             rewrite. 
	 * @see com.dexels.navajo.document.Property#setValue(java.net.URL)
	 */

	@Override
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
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
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
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
	}

	@Override
	public final void setValue(java.util.Date value) {
		setValue(value, false);
	}
	
	private final void setValue(java.util.Date value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		final ThreadLocal<SimpleDateFormat> formatter;
		if (type.equals(TIMESTAMP_PROPERTY)) {
		    formatter = timestampFormat;
		} else {
		    setType(DATE_PROPERTY);
		    formatter = dateFormat1;
		}
		
		if (value != null) {
			setCheckedValue(formatter.get().format(value));
			this.myDate = value;
		} else {
			myValue = null;
			myDate = null;
		}
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
	}

	@Override
	public final void setValue(Boolean value) {
		setValue(value, false);
	}
	
	private final void setValue(Boolean value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		setType(BOOLEAN_PROPERTY);
		if (value != null) {
			setCheckedValue((value.booleanValue() ? "true" : "false"));
		} else {
			myValue = null;
		}
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
	}

	@Override
	public void setValue(boolean value) {
		setValue(value, false);
	}
	
	@Override
	public final void setValue(NavajoExpression ne) {
		setType(Property.EXPRESSION_LITERAL_PROPERTY);
		if ( ne != null ) {
			setCheckedValue(ne.toString());
		}
	}
	
	@Override
	public final void setValue(Money value) {
		setValue(value, false);
	}
	
	private final void setValue(Money value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		setType(MONEY_PROPERTY);

		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
	}
	
    @Override
    public final void setValue(Coordinate value) {
        setValue(value, false);
    }

    private final void setValue(Coordinate value, Boolean internal) {
        Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
        setType(COORDINATE_PROPERTY);

        if (value != null) {
            setCheckedValue(value.toString());
        } else {
            myValue = null;
        }
        if (hasPropertyDataListeners()) {
            firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
        }
    }
    
    private final void setValue(CoordinateType value, Boolean internal) {
        Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
        setType(COORDINATE_PROPERTY);

        if (value != null) {
            setCheckedValue(value.toString());
        } else {
            myValue = null;
        }
        if (hasPropertyDataListeners()) {
            firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
        }
    }

	private final void setValue(Memo value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
        setType(MEMO_PROPERTY);

        if (value != null) {
            setCheckedValue(value.toString());
        } else {
            myValue = null;
        }
        if (hasPropertyDataListeners()) {
            firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
        }
    }

	@Override
	public final void setValue(Percentage value) {
		setValue(value, false);
	}
	
	private final void setValue(Percentage value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		setType(PERCENTAGE_PROPERTY);
		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
	}

	@Override
	public final void setValue(ClockTime value) {
		setValue(value, false);
	}
	
	private final void setValue(ClockTime value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		setType(CLOCKTIME_PROPERTY);
		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
	}

	@Override
	public final void setValue(StopwatchTime value) {
		setValue(value, false);
	}
	
	private final void setValue(StopwatchTime value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		setType(STOPWATCHTIME_PROPERTY);

		if (value != null) {
			setCheckedValue(value.toString());
		} else {
			myValue = null;
		}
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
	}

	@Override
	public final void setValue(double value) {
		setValue(value, false);
	}
	
	@Override
	public final void setValue(Double value) {
		setValue(value, false);
	}
	
	private final void setValue(Double value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		setType(FLOAT_PROPERTY);
		if (value != null) {
			setCheckedValue(value.doubleValue() + "");
		} else {
			myValue = null;
		}
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
		
	}
	
	@Override
	public final void setValue(float value) {
		setValue(value, false);
	}
	
	private final void setValue(Float value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		setType(FLOAT_PROPERTY);
		if (value != null) {
			setCheckedValue(value.floatValue() + "");
		} else {
			myValue = null;
		}
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
	}

	@Override
	public final void setValue(int value) {
		setValue(Integer.valueOf(value), false);
	}

	@Override
	public final void setValue(Integer value) {
		setValue(value, false);
	}
	
	private final void setValue(Integer value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		setType(INTEGER_PROPERTY);
		if (value != null) {
			setCheckedValue(value.intValue() + "");
		} else {
			myValue = null;
		}
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, value, internal);
		}
	}

	@Override
	public final void setLongValue(long value) {
		setLongValue(value, false);
	}
	
	private final void setLongValue(long value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		setType(LONG_PROPERTY);
		setCheckedValue(value + "");
		if (hasPropertyDataListeners()) {
		    firePropertyChanged(PROPERTY_VALUE, old, getTypedValue(), internal);
		}
	}

	@Override
	public final void setValue(long value) {
		setLongValue(value, false);
	}
	
	@Override
	public final void setValue(String value) {
		setValue(value, false);
	}
	
	private final void setValue(String value, Boolean internal) {
	    Object old = null;
        if (hasPropertyDataListeners()) {
            old = getTypedValue();
        }
		if (BINARY_PROPERTY.equals(getType())) {
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
		if (hasPropertyDataListeners()) {

			if (oldValue == null && newValue == null) {
				return;
			}
			if (oldValue != null && oldValue.equals(newValue)) {
				return;
			}

			if (hasPropertyDataListeners()) {
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

	@Override
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

	@Override
	public final String getSubType(String key) {
		if (subtypeMap != null) {
			return subtypeMap.get(key);
		}
		return null;

	}
	@Override
	public final Map<String,String> getSubTypes() {
		if(subtypeMap==null) {
			return  new HashMap<>();
		}
		return new HashMap<>(subtypeMap);
	}	

	private String serializeSubtypes() {
		if (subtypeMap == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
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
			logger.info("Subtypes: {}", sb);
			return sb.toString();
		}

	}

	@Override
	public final void setUnCheckedStringAsValue(String s) {
		myValue = s;
	}
	
	@Override
	public final String setCheckedValue(String v) {

		String value = null;
		try {
			value = PropertyTypeChecker.getInstance().verify(this, v);
		} catch (PropertyTypeException ex1) {
			value = null;
		}

		if (value != null) {
			try {
				if (getType().equals(SELECTION_PROPERTY)) {
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

	@Override
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

	@Override
	public final String getType() {
		if (this.type == null || "".equals(this.type)) {
			return STRING_PROPERTY;
		} else {
			return (this.type);
		}
	}

	@Override
	public final void setType(String t) {
	    String old = type;
		type = t;
		firePropertyChanged(PROPERTY_TYPE, old, type);
	}

	@Override
	public final String toString() {
		
	    
		if (getType().equals(Property.DATE_PROPERTY)) {
			return (this.getTypedValue() != null) ? dateFormat3.get().format((Date) this.getTypedValue()) : "";
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

	/**
	 * TODO return immutable result?
	 */
	@Override
	public final ArrayList<Selection> getAllSelections() {
		return new ArrayList<Selection>(selectionList);
	}

	@Override
	public final void setSelected(Selection s) {
		List<Selection> old;
			old = getAllSelectedSelections();


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
		
		List<Selection> newValue = getAllSelectedSelections();

		boolean isEqual = isEqual(old, newValue);
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
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			current.setSelected(b);
		}
	}

	public final void setSelectedByValue(Object value) {
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
			}
		}
	}

	public final boolean isEditable() {
		return (Property.DIR_IN.equals(direction) || Property.DIR_INOUT.equals(direction));
	}

	@Override
	public final void addSelection(Selection s) {
		int max = selectionList.size();
		boolean selected = s.isSelected();
		for (int i = 0; i < max; i++) {
			Selection t = selectionList.get(i);
			if (t.getName().equals(s.getName())) {
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

	@Override
	public final void removeSelection(Selection s) {
		selectionList.remove(s);
	}

	@Override
	public final void removeAllSelections() {
		selectionList.clear();
	}

	@Override
	public final Selection getSelection(String name) {
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			if (current.getName().equals(name)) {
				return current;
			}
		}
		return NavajoFactory.getInstance().createDummySelection();
	}

	@Override
	public final Selection getSelectionByValue(String value) {
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			if (current != null && current.getValue().equals(value)) {
				return current;
			}
		}
		return NavajoFactory.getInstance().createDummySelection();
	}

	/**
	 * I don't like this function, with its silly way of indicating null selections (== dummy selection)
	 * @deprecated
	 */
	@Deprecated
	@Override
	public final Selection getSelected() {
		for (int i = 0; i < selectionList.size(); i++) {
			Selection current = selectionList.get(i);
			if (current != null && current.isSelected()) {
				return current;
			}
		}
		return NavajoFactory.getInstance().createDummySelection();
	}

	@Override
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
		if ( getBind() != null) {
			cp.setBind(getBind());
		}
		if ( getMethod() != null) {
			cp.setMethod(getMethod());
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

	@Override
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

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
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
			String selectionSubType = getSubType("name");
			if (getType().equals(Property.SELECTION_PROPERTY)) {
				if (selectionSubType != null && selectionSubType.equals("integer")) {
					ob1 = Integer.valueOf(getSelected().getName());
				} else {
					ob1 = getSelected().getName();
				}
			} else {
				ob1 = (Comparable<?>) getTypedValue();
			}

			// Get second argument.
			if (((BasePropertyImpl) p).getType().equals(Property.SELECTION_PROPERTY)) {
				BasePropertyImpl cp = (BasePropertyImpl) p;
				if (selectionSubType != null && cp.getSubType("name").equals("integer")) {
					ob2 = Integer.valueOf(cp.getSelected().getName());
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
				return ob1.compareTo(ob2);
			} catch (Throwable t) {
				return 0;
			}

		} catch (Throwable t2) {
			return 0;
		}

	}


	@Override
	public void setSelected(String[] s) {
		if (!getType().equals(SELECTION_PROPERTY)) {
			throw NavajoFactory.getInstance().createNavajoException("Setting selected of non-selection property!");
		}

		setAllSelected(false);
		for (int i = 0; i < s.length; i++) {
			Selection st = getSelectionByValue(s[i]);
			st.setSelected(true);
		}

	}

	@Override
	public final void addSelectionWithoutReplace(Selection s) {
		selectionList.add((BaseSelectionImpl)s);
		((BaseSelectionImpl) s).setParent(this);
	}

	@Override
	public final void setSelected(ArrayList<String> al) {
		List<Selection> old = new ArrayList<>(getAllSelectedSelections());
		setAllSelected(false);
		for (int i = 0; i < al.size(); i++) {
			String s = al.get(i);
			Selection sl = getSelectionByValue(s);
			sl.setSelected(true);
		}
		firePropertyChanged("selection", old, getAllSelectedSelections());
	}

	@Override
	public final ArrayList<Selection> getAllSelectedSelections() {
		ArrayList<Selection>  list = new ArrayList<Selection>() {
			private static final long serialVersionUID = -2914783936108056853L;

			@Override
			public String toString() {
				if(size()==0) {
					return Selection.DUMMY_ELEMENT;
				}
				if(size()==1) {
					return get(0).getValue();
				}
				int index = 0;
				StringBuilder sb = new StringBuilder();
				for (Selection s  : this) {
					sb.append(s.getValue());
					if(index<size()-1) {
						sb.append(',');
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

	@Override
	public final void setSelected(String value) {
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

	@Override
	public final void clearSelections() {
		List<Selection> old = new ArrayList<>(getAllSelectedSelections());
		ArrayList<Selection> al = getAllSelections();
		for (int i = 0; i < al.size(); i++) {
			BaseSelectionImpl s = (BaseSelectionImpl) al.get(i);
			s.setSelected(false);
		}
		firePropertyChanged("selection", old, getAllSelectedSelections());
	}

	@Override
	public final Selection existsSelection(String name) {
		return getSelection(name);
	}

	@Override
	public final boolean isDirIn() {
		if (getDirection() == null) {
			return false;
		}
		return getDirection().equals(DIR_IN) || getDirection().equals(DIR_INOUT);
	}

	@Override
	public final boolean isDirOut() {
		if (getDirection() == null) {
			return false;
		}
		return getDirection().equals(DIR_OUT) || getDirection().equals(DIR_INOUT);
	}



	@Override
	public final boolean isEqual(Property p) {

		if (!getName().equals(p.getName())) {
			return false;
		}

		if ((this.getType() != null && p.getType() != null) && !this.getType().equals(p.getType())) {
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
			return dateFormat2.get().format(myDate).equals(dateFormat2.get().format(otherDate));
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
				logger.warn("Error: ", e);
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
			if (p.getValue() == null && this.getValue() == null) {
				return true;
			}

			// If only one of them is null they're not equal.
			if (p.getValue() == null || this.getValue() == null) {
				return false;
			}

			// We are only equal if our values match exactly..

			return p.getValue().equals(this.getValue());
		}

	}

	@Override
	public void addExpression(ExpressionTag e) {
		throw new java.lang.UnsupportedOperationException("Method addExpression() not yet implemented.");
	}

	@Override
	public Map<String,String> getAttributes() {
		Map<String,String> m = new HashMap<>();
		
		if ( myExtends != null ) {
			m.put(Property.PROPERTY_EXTENDS, myExtends);
		}
		
		if ( key != null ) {
			m.put(Property.PROPERTY_KEY, key);
		}
		
		if ( reference != null ) {
			m.put(Property.PROPERTY_REFERENCE, reference);
		}
		
		if ( bind != null ) {
			m.put(Property.PROPERTY_BIND, bind);
		}
		
		if ( method != null && !"".equals(method) ) {
			m.put(Property.PROPERTY_METHOD, method);
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
			} 
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

	@Override
	public List<? extends BaseNode> getChildren() {
		return selectionList;
	}

	@Override
	public Object getRef() {
		throw new UnsupportedOperationException("getRef not possible on base type");
	}

	@Override
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
			if ( getSubtype() != null ) {
				prop.setSubType(getSubType());
			}
			if ( getKey() != null ) {
				prop.setKey(getKey());
			}
			if ( getReference() != null ) {
				prop.setReference(getReference());
			}
			if ( getExtends() != null ) {
				prop.setExtends(getExtends());
			}
			if ( getBind() != null) {
				prop.setBind(getBind());
			}
			if ( getMethod() != null) {
				prop.setMethod(getMethod());
			}
			return prop;
		}
	}

	@Override
	public Object clone(String newName) {
		Property p = cloneWithoutValue();
		p.setName(newName);
		return p;
	}

	@Override
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

	@Override
	public String getTagName() {
		return Property.PROPERTY_DEFINITION;
	}

	@Override
	public void writeText(Writer w) throws IOException {
		if (myBinary != null) { 
			myBinary.writeBase64(w);
		}

	}

	@Override
	public boolean hasTextNode() {
		return myBinary != null;
	}



	@Override
	public void addPropertyChangeListener(PropertyChangeListener p) {
		if (myPropertyDataListeners == null) {
			myPropertyDataListeners = new ArrayList<>();
		}
		if(myPropertyDataListeners.contains(p)) {
			logger.info("IDENTICAL LISTENER {}",getFullPropertyName());
			return;
		}
		myPropertyDataListeners.add(p);

		if (myPropertyDataListeners.size() > 5) {
			logger.trace("Multiple property listeners detected {} path: {}",myPropertyDataListeners.size(),getFullPropertyName());
			logger.trace(">>> {}",myPropertyDataListeners);
		}
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener p) {
		if (myPropertyDataListeners == null) {
			return;
		}
		myPropertyDataListeners.remove(p);
	}

	@Override
	public void setSelected(Selection s, boolean selected) {
		List<Selection> l = new ArrayList<>( getAllSelectedSelections());
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

	@Override
	public void forcePropertyChange() {
		if (hasPropertyDataListeners()) {
			for (int i = 0; i < myPropertyDataListeners.size(); i++) {
				PropertyChangeListener c = myPropertyDataListeners.get(i);
				
				c.propertyChange(new PropertyChangeEvent(this, PROPERTY_VALUE, getTypedValue(), getTypedValue()));
			}
		}
	}
	@Override
	public void printElementJSONTypeless(final Writer sw) throws IOException {
		String value = getValue();
		if(getType().equals(Property.SELECTION_PROPERTY)){
			Selection s = getSelected();
			if(s != null){
				value = s.getValue();
			}
		}
		value = ( value != null ? value.replace("\"", "\\\"") : "" );
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
	public String getBind() {
		return bind;
	}
	
	@Override
	public void setBind(String b) {
		String old = this.bind;
		this.bind = b;
		firePropertyChanged(old, b);
	}
	
	
	@Override
	public String getMethod() {
		return method;
	}
	
	@Override
	public void setMethod(String b) {
		String old = this.method;
		this.method = b;
		firePropertyChanged(old, b);
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
