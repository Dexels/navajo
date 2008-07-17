package com.dexels.navajo.geo.element;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.nanoimpl.*;

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
