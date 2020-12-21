/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
