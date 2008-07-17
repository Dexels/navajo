package com.dexels.navajo.geo.element;


import com.dexels.navajo.document.nanoimpl.*;

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

	public String toString() {
		return a+" "+b;
	}
	
	public XMLElement createElement() {
		XMLElement c = new CaseSensitiveXMLElement("LineString");
		c.addTagKeyValue("extrude", "0");
		c.addTagKeyValue("altitudeMode", "clampToGround");
		c.addTagKeyValue("tesselate", "0");
		c.addTagKeyValue("coordinates", toString());
		
		return c;
	}
	public XMLElement createPlaceMark() {
		XMLElement c = new CaseSensitiveXMLElement("Placemark");
		c.setAttribute("id", id);
		c.addTagKeyValue("name", id);
		XMLElement point = createElement();
		c.addChild(point);
		return c;
	}
	public boolean equals(Object o) {
		if(!(o instanceof GeoEdge)) {
			return false;
		}
		GeoEdge e = (GeoEdge)o;
		return (e.a.equals(a) && e.b.equals(b)) || (e.a.equals(b) && e.b.equals(a));
	}
}
