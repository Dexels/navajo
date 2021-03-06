/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.geo.element;


import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class GeoEdge extends GeoElement {
	public GeoPoint a,b;
	
	public GeoEdge(GeoPoint a,GeoPoint b) throws GeoException {
		if(a == b) {
			throw new GeoException("VERY Illegal edge: "+a+" - "+b);
		}
		if(a.equals(b)) {
			throw new GeoException("Illegal edge: "+a+" - "+b);
		}
		this.a = a;
		this.b = b;
	}

	@Override
	public String toString() {
		return a+" "+b;
	}
	
	@Override
	public XMLElement createElement() {
		XMLElement c = new CaseSensitiveXMLElement("LineString");
		c.addTagKeyValue("extrude", "0");
		c.addTagKeyValue("altitudeMode", "clampToGround");
		c.addTagKeyValue("tesselate", "0");
		c.addTagKeyValue("coordinates", toString());
		
		return c;
	}
	@Override
	public XMLElement createPlaceMark() {
		XMLElement c = new CaseSensitiveXMLElement("Placemark");
		c.setAttribute("id", id);
		c.addTagKeyValue("name", id);
		XMLElement point = createElement();
		c.addChild(point);
		return c;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof GeoEdge)) {
			return false;
		}
		GeoEdge e = (GeoEdge)o;
		return (e.a.equals(a) && e.b.equals(b)) || (e.a.equals(b) && e.b.equals(a));
	}
}
