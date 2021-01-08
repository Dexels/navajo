package com.dexels.navajo.document.navascript.tags;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AttributeAssignments {

	private Set<AttributeAssignment> attributes = new HashSet<>();

	public void addMap(Map<String,String> map, String... ignoreKeys) {

		for ( String key : map.keySet() ) {
			boolean ignore = false;
			for ( String ig : ignoreKeys ) {
				if ( key.equals(ig)) {
					ignore = true;
				}
			}
			if ( !ignore ) {
				AttributeAssignment aa = new AttributeAssignment(key, map.get(key));
				attributes.add(aa);
			}
		}

	}

	public AttributeAssignments add(String attribute, String value) {
		AttributeAssignment a = new AttributeAssignment(attribute, value);
		add(a);
		return this;
	}
	
	public AttributeAssignments add(AttributeAssignment a) {
		if ( a.getAttribValue() != null && !a.getAttribValue().equals("")  ) {
			attributes.add(a);
		}
		return this;
	}

	public String format(boolean skipEmpty) {

		StringBuffer sb = new StringBuffer();

		int count = 0;
		sb.append(NS3Constants.PARAMETERS_START);

		for ( AttributeAssignment aa : attributes ) {

			if ( aa.getAttribValue() != null ) {
				if ( count > 0 ) {
					sb.append(", ");
				}
				sb.append(aa.getAttribName());
				sb.append(NS3Constants.ATTRIBUTE_ASSIGNMENT);
				sb.append(aa.getAttribValue().replaceAll("_", ""));
				count++;
			}
		}

		if ( skipEmpty && count == 0 ) {
			return "";
		}

		sb.append(NS3Constants.PARAMETERS_END);
		
		return sb.toString();
	}

	public static void main(String [] args) {

		AttributeAssignment a1 = new AttributeAssignment("type","string");
		AttributeAssignment a2 = new AttributeAssignment("condition", null);
		AttributeAssignment a3 = new AttributeAssignment("value","aap");

		AttributeAssignments aa = new AttributeAssignments();
		//aa.add(a1).add(a2).add(a3);

		Map<String,String> map = new HashMap<>();
		map.put("value", "noot");
		map.put("type", "string");
		map.put("direction", "in");
		map.put("condition", "true");
		
		aa.addMap(map, "value", "condition");
		
		System.err.println(aa.format(false));

	}
}
