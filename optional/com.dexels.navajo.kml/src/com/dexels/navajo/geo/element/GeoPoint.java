/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.geo.element;

import java.util.StringTokenizer;

import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;

public class GeoPoint extends GeoElement {
	public static final double MIN_DISTANCE = 0.00001;
	public double lat,lon;
//	
	private static int pointCounter = 0;
	
	public GeoPoint(double lon, double lat) {
		this.lat = lat;
		this.lon = lon;
	}
	
	@Override
	public String getId() {
		return id;
	}
	/**
	 * LON,LAT format!!!
	 * @param coordinate
	 */
	public GeoPoint(String coordinate) {
		StringTokenizer st = new StringTokenizer(coordinate,",");

		id = "point_"+pointCounter++;
			String lonString = st.nextToken();
			String latString = st.nextToken();
			this.lon = Double.parseDouble(lonString);
			this.lat = Double.parseDouble(latString);
	}
	
	@Override
	public String toString() {

		if(id!=null) {
			return id;
		}
		return getCoordinates();
	}
	
	public String getCoordinates() {

		String sss =""+lon+","+lat;
		return sss;
	}

	@Override
	public XMLElement createElement() {
		return createElement(null);
	}
	public XMLElement createElement(String label) {
		XMLElement c = new CaseSensitiveXMLElement("Point");
		c.addTagKeyValue("extrude", "0");
		c.addTagKeyValue("altitudeMode", "clampToGround");
		c.addTagKeyValue("coordinates", getCoordinates());
		if (label==null) {
			c.addTagKeyValue("name", id);
		} else {
			c.addTagKeyValue("name", label);
		}
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
	public XMLElement createPlaceMark(String label) {
		XMLElement c = new CaseSensitiveXMLElement("Placemark");
		c.setAttribute("id",label);
		c.addTagKeyValue("name", label);
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
		if(!(o instanceof GeoPoint)) {
			return false;
		}
		GeoPoint g = (GeoPoint)o;
		double distance = distance(g);
		if(distance<MIN_DISTANCE) {
			return true;
		}
		return false;
	}
	
	public double distance(GeoPoint g) {
		double eucl = Math.sqrt(Math.pow((g.lat-lat),2) + Math.pow((g.lon-lon),2));
		return eucl;
		
	}
	
	public int compareTo(Object o) {
		GeoPoint g = (GeoPoint)o;
		Double d = new Double(lat);
		Double e = new Double(g.lat);
		int x = d.compareTo(e);
		if(x!=0) {
			return x;
		}
		d = new Double(lon);
		e = new Double(g.lon);
		return d.compareTo(e);
	}

}
