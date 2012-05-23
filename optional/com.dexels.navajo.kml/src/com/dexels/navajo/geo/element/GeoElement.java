package com.dexels.navajo.geo.element;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public abstract class GeoElement {
	String id = null;
	public abstract XMLElement createElement();
		
	public void writeElement(String file, XMLElement elt) throws IOException {
		FileWriter fw = new FileWriter(file);
		elt.write(fw);
		fw.flush();
		fw.close();
	}
	
	public abstract XMLElement createPlaceMark();

	public List<XMLElement> createPlaceMarkList() {
		List<XMLElement> x = new ArrayList<XMLElement>();
		x.add(createPlaceMark());
		return x;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
