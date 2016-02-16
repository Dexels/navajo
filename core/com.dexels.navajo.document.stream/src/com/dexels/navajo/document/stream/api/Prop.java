package com.dexels.navajo.document.stream.api;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.types.Binary;

public class Prop {
	private final String name;
	private final Object value;
	private final String type;
	private final List<Select> selections;
	private final int length;
	private final String description;
	private final Direction direction;
	private final String subtype;
	
	public enum Direction {
		IN,OUT
	}
	
	Prop(String name, Object value, String type, List<Select> selections) {
		this(name,value,type,selections,Direction.OUT,"",-1,"");
	}
	
	public Prop copy() {
		return new Prop(name, value, type,selections,direction,description,length,subtype);
	}

	public Prop(String name, Object value, String type) {
		this(name, value, type, Collections.emptyList());
	}

	public Prop(String name, Object value, String type, List<Select> selections, Prop.Direction direction,
			String description, int length, String subtype) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.selections = selections;
		this.direction = Direction.OUT;
		this.description = description;
		this.length = length;
		this.subtype = subtype;
	}

	public static Prop create(String name) {
		return new Prop(name,null,null);
	}
	
	public static Prop create(Map<String,String> attributes, List<Select> selections) {
		String lngth = attributes.get("length");
		int len = lngth==null || "".equals(lngth)?-1:Integer.parseInt(lngth);
		return create(attributes.get("name"),attributes.get("value"),attributes.get("type"),selections,attributes.get("direction").equals("dir_in")?Direction.IN:Direction.OUT,attributes.get("description"),len,attributes.get("subtype"));
	}

	public static Prop create(Map<String, String> attributes, Binary currentBinary) {
		int len =  (int) currentBinary.getLength(); // lngth==null || "".equals(lngth)?-1:Integer.parseInt(lngth);
		System.err.println("Creating property with binary: "+currentBinary.getLength());
		return create(attributes.get("name"),currentBinary,attributes.get("type"),Collections.emptyList(),attributes.get("direction").equals("dir_in")?Direction.IN:Direction.OUT,attributes.get("description"),len,attributes.get("subtype"));
	}

	public static Prop create(String name, Object value) {
		return new Prop(name,value,null);
	}
	
	public static Prop create(String name, Object value, String type) {
		return new Prop(name,value,type);
	}
	
	public static Prop create(String name, Object value, String type,List<Select> selections) {
		return new Prop(name,value,type,selections,Prop.Direction.OUT,"",-1,"");
	}
	
	private static Prop create(String name, Object value, String type,List<Select> selections, Prop.Direction direction, String description, int length, String subtype ) {
		return new Prop(name,value,type,selections,direction,description,length,subtype);
	}
	

	
	public Prop withSelections(List<Select> currentSelections) {
		return new Prop(name, value, type,currentSelections);
	}

	public Prop withValue(Object val) {
		return new Prop(name, val, type,selections,direction,description,length,subtype);
	}
	
	public Prop emptyWithType(String type) {
		return new Prop(name,null,type);
	}

	public String name() {
		return name;
	}

	public String type() {
		return type;
	}

	public int length() {
		return this.length;
	}
	
	public String toString() {
		return name+":"+value;
	}

	public String description() {
		return this.description;
	}

	public String direction() {
		switch(this.direction) {
		case IN:
			return "in";
		default:
			return "out";
		}

	}

	public Object value() {
		return value;
	}

	public void write(Writer sw, int indent) throws IOException {
		 for (int a = 0; a < indent; a++) {
			 sw.write(" ");
		 }
		 sw.write("<property");
		 if(name!=null) {
			 sw.write(" name=\""+name+"\"");
		 }
		 if(type!=null) {
			 sw.write(" type=\""+type+"\"");
		 }
		 if(value!=null) {
			 sw.write(" value=\""+value+"\"");
		 }
		 if(direction!=null) {
			 sw.write(" direction=\""+direction()+"\"");
		 }
		 if(description!=null && !"".equals(description)) {
			 sw.write(" description=\""+description+"\"");
		 }
		 if(length>0) {
			 sw.write(" length=\""+length+"\"");
		 }
		 if(!isBinary() && selections==null || selections.isEmpty()) {
				sw.write("/>\n");
		 } else {
			 if (isBinary()) {
				Binary b = (Binary)value;
				b.writeBase64(sw);
			} else {
				 for (Select select : selections) {
						sw.write("<option name=\""+select.name()+"\" value=\""+select.value()+"\" selected=\""+(select.selected()?"1":"0")+"\"/>");
					}

			}
			sw.write("</property>\n");

		 }
	}
	
	private boolean isBinary() {
		if(value!=null && value instanceof Binary) {
			return true;
		}
		return false;
	}
	public void printStartTag(final Writer sw, int indent,boolean forceDualTags,String tag,  String[] attributes) throws IOException {
		 for (int a = 0; a < indent; a++) {
			 sw.write(" ");
		 }
		 sw.write("<");
		 sw.write(tag);
		 sw.write(" ");
		 for (String attribute : attributes) {
			sw.write(attribute);
		}
		sw.write(">");
	}

	public String subtype() {

		return this.subtype;
	}

	public void subtype(String sub) {
		// TODO, don't want to make it mutable
	}

	public String subtypes(String subTypeKey) {
		Map<String,String> subtypes = Collections.emptyMap();
		return subtypes.get(subTypeKey);
	}



}
