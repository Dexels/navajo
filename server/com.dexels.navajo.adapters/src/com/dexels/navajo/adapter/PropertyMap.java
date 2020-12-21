/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.mapping.MappingUtils;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;


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

	public List<Option> optionList = new ArrayList<>();
	public Option currentOption = new Option();


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
			// this will create if property not found and otherwise update value and other attributes, except for subtype and the options
			// Do not have to pass inDoc as we're not creating a param
		    prop = MappingUtils.setProperty(false, msg, name, currentValue, type, null, direction, description, length == null ? -1 : length, outMessage, null, false);

			if (subtype != null)
			{
				prop.setSubType(subtype);
			}

			// For the selection property, parse the given optionList and the multiple setting
			if (Property.SELECTION_PROPERTY.equals(type))
			{
				if (optionList != null)
				{
					prop.clearSelections();
					for(Option o : optionList)
					{
						Selection sel = NavajoFactory.getInstance().createSelection(outMessage, o.getName(), o.getValue(), o.getSelected());
						prop.addSelectionWithoutReplace(sel);
					}
				}
				prop.setCardinality(multiple ? "+" : "1");
			}

		} catch (Exception e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}

	public void setOptionName(String name) {
		currentOption.setName(name);
		possiblyFinalizeOption();
	}

	public void setOptionValue(String value) {
		currentOption.setValue(value);
		possiblyFinalizeOption();
	}

	public void setOptionSelected(boolean selected) {
		currentOption.setSelected(selected);
		possiblyFinalizeOption();
	}

	private void possiblyFinalizeOption()
	{
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
