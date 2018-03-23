package com.dexels.navajo.adapter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * <p>
 * Title:
 * <h3>SportLink Services</h3><br>
 * </p>
 * <p>
 * Description: Web Services for the SportLink Project<br>
 * <br>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002<br>
 * </p>
 * <p>
 * Company: Dexels.com<br>
 * </p>
 * 
 * @author unascribed
 * @version $Id$
 */
public class PropertyMap implements Mappable {

	/*
    <map>
        <tagname>property</tagname>
        <object>com.dexels.navajo.adapter.PropertyMap</object>
        <values>
            <value name="name" required="true" direction="in"/>
            <value name="description" required="true" direction="in"/>
            <value name="direction" required="true" direction="in"/>
            <value name="type" required="true" direction="in"/>
            <value name="currentValue" required="false" direction="in"/>
            <value name="length" required="false" direction="in"/>
            <value name="multiple" required="false" direction="in"/>
            <value name="subtype" required="false" direction="in"/>
            <value name="removeExisting" required="false" direction="in"/>
        </values>
        <methods>
            <method name="addOption">
                <param name="name"     field="optionName" required="true"/>
                <param name="value"    field="optionValue" required="true"/>
                <param name="selected" field="optionSelected" required="true"/>
            </method>
        </methods>
    </map>
	 */
	
	private Navajo outMessage;
	private Access access;

	public String name;
	public String description;
	public String direction;
	public Integer length = null;
	public String type;

	public Object currentValue = null;    
	public String subtype = null;
	public boolean multiple = false;
	public boolean removeExisting = false;
	
	public List<Option> optionList = new ArrayList<Option>();
	public Option currentOption = new Option();


	private final static Logger logger = LoggerFactory
			.getLogger(PropertyMap.class);
	

	@Override
	public void load(Access access) throws MappableException, UserException {
		outMessage = access.getOutputDoc();
		this.access = access;
	}

	@Override
	public void store() throws MappableException, UserException {

		try {
			Message msg = access.getCurrentOutMessage();
			if (msg == null) {
				throw new UserException(-1, "PropertyMap adapter can only be called from within a constructed output message");
			}

			Property prop = null;
			prop = msg.getProperty(name);
			if (prop != null && !removeExisting)
			{
				throw new UserException(-1, "Cannot add already existing property " + name + " when removeExisting is false."); 
			}
			else if (prop == null) {
				prop = NavajoFactory.getInstance().createProperty(outMessage, name, (multiple ? "+" : "1"), description, direction);
				msg.addProperty(prop);
			} else {
				prop.setDirection(direction);
				prop.removeAllSelections();
			}

            if (description != null) {
                prop.setDescription(description);
            }

			// continue with shared setters
            prop.setType(type);
			if (length != null)
			{
			    prop.setLength(length);
			}
			if (subtype != null) 
			{
				prop.setSubType(subtype);
			}

			// One of two cases: Either a selection property or another type of property
			if (Property.SELECTION_PROPERTY.equals(type))
			{
				if (optionList != null)
				{
					for(Option o : optionList)
					{
						Selection sel = NavajoFactory.getInstance().createSelection(outMessage, o.getName(), o.getValue(), o.getSelected());
						prop.addSelectionWithoutReplace(sel);
					}
				}
				prop.setCardinality(multiple ? "+" : "1");
			}
			else
			{
				prop.setAnyValue(currentValue);
			}

		} catch (NavajoException ne) {
			throw new UserException(-1, ne.getMessage(),ne);
		}
	}
	
	public void setOptionName(String name) {
		currentOption.setName(name);
		if (currentOption.completelyDefined())
		{
			optionList.add(currentOption);
			currentOption = new Option();
		}
	}
	
	public void setOptionValue(String value) {
		currentOption.setValue(value);
		if (currentOption.completelyDefined())
		{
			optionList.add(currentOption);
			currentOption = new Option();
		}
	}
	
	public void setOptionSelected(boolean selected) {
		currentOption.setSelected(selected);
		if (currentOption.completelyDefined())
		{
			optionList.add(currentOption);
			currentOption = new Option();
		}
	}
	
	@Override
	public void kill() {
	}
	
	// ------------------------------------------------------------ public
	// setters

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getDirection() {
		return direction;
	}

	public int getLength() {
		return length;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setDescription(String d) {
		this.description = d;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setRemoveExisting(boolean removeExisting) {
		this.removeExisting = removeExisting;
	}

	public boolean getRemoveExisting() {
		return this.removeExisting;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Object currentValue) {
		this.currentValue = currentValue;
	}

	public static void main(String[] args) {
	}
	
	class Option
	{
		private String name = null;
		private String value = null;
		private Boolean selected = null;
		
		public boolean completelyDefined()
		{
			return (name != null && value != null && selected != null);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Boolean getSelected() {
			return selected;
		}
		public void setSelected(Boolean selected) {
			this.selected = selected;
		}
	}
	
}
