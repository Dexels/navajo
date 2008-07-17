package com.dexels.navajo.geo.element;

import java.util.*;

import com.dexels.navajo.document.nanoimpl.*;

public class GeoPolygon extends GeoElement {
	// private List<GeoEdge> myEdges = new ArrayList<GeoEdge>();
	// private List<GeoEdge> myOutlineEdges = new ArrayList<GeoEdge>();
	private List<GeoPoint> myStartPoints = new LinkedList<GeoPoint>();
	// private GeoEdge myInitialOutlineEdge;
	// private GeoPoint lastPoint = null;

	private static int idCounter = 0;

	public double getCircumference() {
		double distance = 0;
		int count = myStartPoints.size()-1;
		for (int i=0; i<count;i++) {
			GeoPoint p = myStartPoints.get(i);
			GeoPoint q = myStartPoints.get(i+1);
			distance += p.distance(q);
		}
		GeoPoint p = myStartPoints.get(count);
		GeoPoint q = myStartPoints.get(0);
		distance += p.distance(q);
		return distance;
	}
	
	public GeoPolygon(XMLElement polygon) {
		XMLElement outer = polygon.getElementByTagName("outerBoundaryIs");
		String coordinates = outer.getElementByTagName("coordinates").getContent();
		StringTokenizer st = new StringTokenizer(coordinates);
		// int count = 0;

		List<GeoPoint> glist = new ArrayList<GeoPoint>();
		while (st.hasMoreTokens()) {
			String coord = st.nextToken();
			// GeoPoint gpoint = new GeoPoint(coord);
			GeoPoint gpoint = GeoPointStore.getInstance().getPoint(coord);
			glist.add(gpoint);
		}
		// addPointList(glist);
		id = "Polygon" + idCounter++;
		if (glist.get(glist.size() - 1).equals(glist.get(0))) {
			glist.remove(glist.size() - 1);
		}
		myStartPoints.addAll(glist);
	}

	public GeoPolygon(List<GeoPoint> glist) {
		myStartPoints.addAll(glist);
		id = "Polygon" + idCounter++;
	}

	/**
	 * Basically it is not correct, it only checks if the size is the same, and
	 * all points of a are also in b, so it completely discards order.
	 * 
	 * @param p
	 * @return
	 */
	public boolean equals(GeoPolygon p) {
		List<GeoPoint> thisp = getPoints();
		List<GeoPoint> otherP = p.getPoints();
		if (thisp.size() == otherP.size()) {
			System.err.println("SIZES EQUAL");
		} else {
			return false;
		}
		for (int i = 0; i < thisp.size(); i++) {
			// if(thisp.get(i).equals(otherP.get(i))) {
			// System.err.println("eq!");
			// } else {
			// System.err.println("Element # "+i+" not equal");
			// System.err.println("Element: "+thisp.get(i).getCoordinates()+" -
			// "+otherP.get(i).getCoordinates());
			// return false;
			// }

			if (otherP.contains(thisp.get(i))) {
				// whatever
			} else {
				return false;
			}
		}
		return true;
	}

	public XMLElement createElement() {
		XMLElement c = new CaseSensitiveXMLElement("Polygon");

		XMLElement d = new CaseSensitiveXMLElement("outerBoundaryIs");

		XMLElement e = new CaseSensitiveXMLElement("LinearRing");
		c.addChild(d);
		d.addChild(e);
		StringBuffer sb = new StringBuffer();
		// for (GeoEdge g : createEdgeList()) {
		// sb.append(g.a);
		// sb.append(" ");
		// }

		c.addTagKeyValue("name", id);
		c.setAttribute("id", id);
		for (GeoPoint a : myStartPoints) {
			sb.append(a.getCoordinates());
			sb.append(" ");
		}
		e.addTagKeyValue("coordinates", sb.toString());
		return c;
	}

	public GeoPoint getPreviousPoint(GeoPoint p) {
		int i = getPoints().indexOf(p);
		if (i < 0) {
			return null;
		}
		if (i == 0) {
			// last point
			return getPoints().get(getPoints().size() - 1);
		}
		return getPoints().get(i - 1);
	}

	public GeoPoint getNextPoint(GeoPoint p) {
		int i = getPoints().indexOf(p);
		if (i < 0) {
			System.err.println("Not found in poly: " + getId());
			return null;
		}
		if (i == getPoints().size() - 1) {
			// last point
			GeoPoint geoPoint = getPoints().get(0);
			return geoPoint;
		}
		GeoPoint geoPoint = getPoints().get(i + 1);
		return geoPoint;
	}

	public XMLElement createPlaceMark() {
		return createPlaceMark(null);
	}

	public XMLElement createPlaceMark(String label) {
		XMLElement c = new CaseSensitiveXMLElement("Placemark");
		if (label == null) {
			c.setAttribute("id", id);
			c.addTagKeyValue("name", id);
		} else {
			c.setAttribute("id", label);
			c.addTagKeyValue("name", label);
		}
		XMLElement poly = createElement();
		c.addChild(poly);
		return c;

	}

	public List<XMLElement> createPlaceMarkList() {
		List<XMLElement> result = new ArrayList<XMLElement>();
		int index = 0;
		result.add(createElement());
		System.err.println("Creating debug. stating points: " + myStartPoints.size());
		for (GeoPoint g : myStartPoints) {
			GeoPoint gg = new GeoPoint(g.getCoordinates());

			XMLElement xx = gg.createPlaceMark();
			result.add(xx);
			index++;
		}
		result.add(createPlaceMark());

		return result;
	}

	public boolean hasPoint(GeoPoint p) {
		// System.err.println("Checking point: " + p + " in poly: " +
		// getPoints());
		for (GeoPoint gp : myStartPoints) {
			boolean equals = gp.equals(p);
			// System.err.println(gp + " equals " + p + " ? " + equals);
			if (equals) {
				return true;
			}
		}
		return false;
	}

	private boolean containsPoint(GeoPoint point) {
		List<GeoPoint> points = getPoints();
		int nvert = points.size();
		int i, j = 0;
		boolean c = false;
		for (i = 0, j = nvert - 1; i < nvert; j = i++) {
			if (((points.get(i).lat > point.lat) != (points.get(j).lat > point.lat))
					&& (point.lon < (points.get(j).lon - points.get(i).lon) * (point.lat - points.get(i).lat)
							/ (points.get(j).lat - points.get(i).lat) + points.get(i).lon))
				c = !c;
		}
		return c;
	}

	public List<GeoPoint> getPoints() {
		return myStartPoints;
	}

	public boolean overlaps(GeoPolygon g) {
		List<GeoPoint> p = g.getPoints();
		for (GeoPoint geoPoint : p) {
			boolean b = containsPoint(geoPoint);
			if (b) {
				return true;
			}
		}

		return false;
	}

	public boolean contains(GeoPolygon g) {
		List<GeoPoint> p = g.getPoints();
		for (GeoPoint geoPoint : p) {
			boolean b = containsPoint(geoPoint);
			if (!b) {
				return false;
			}
		}

		return true;
	}

	/*
	 * Return the clockwise status of a curve, clockwise or counterclockwise n
	 * vertices making up curve p return 0 for incomputables eg: colinear points
	 * CLOCKWISE == 1 COUNTERCLOCKWISE == -1 It is assumed that - the polygon is
	 * closed - the last point is not repeated. - the polygon is simple (does
	 * not intersect itself or have holes)
	 */

	public boolean isClockwise() {
		boolean clockwise = isClockwise(getPoints());
		return clockwise;
	}

	private boolean isClockwise(List<GeoPoint> input) {
		// first find rightmost lowest vertex of the polygon
		List<GeoPoint> p = new ArrayList<GeoPoint>(input);
		if (!p.get(0).equals(p.get(p.size() - 1))) {
			// BEWARE, NEED CLOSED POLY!
			p.add(p.get(0));
		}
		int rmin = 0;
		double xmin = p.get(0).lon;
		double ymin = p.get(0).lat;
		int n = input.size();

		for (int i = 1; i < n-1; i++) {
			if (p.get(i).lat > ymin)
				continue;
			if (p.get(i).lat == ymin) { // just as low
				if (p.get(i).lon < xmin) // and to left
					continue;
			}
			rmin = i; // a new rightmost lowest vertex
			xmin = p.get(i).lon;
			ymin = p.get(i).lat;
		}

		// test orientation at this rmin vertex
		// ccw <=> the edge leaving is left of the entering edge
		
		if (rmin == 0) {
			double left = isLeft(p.get(n - 1), p.get(0), p.get(1));
			boolean result = left < 0;
//			System.err.println("Left: "+left+" result: "+result);
//			System.err.println("Pim: " + p.get(n - 1).createPlaceMark("Pim"));
//			System.err.println("Pam " + p.get(0).createPlaceMark("Pam"));
//			System.err.println("Pet: " + p.get(1).createPlaceMark("Pet"));
//			System.err.println("Poly: \n" + createPlaceMark());

			return result;
		} else {
//			System.err.println("Aap: " + p.get(rmin - 1).createPlaceMark("Aap"));
//			System.err.println("Noot: " + p.get(rmin).createPlaceMark("Noot"));
//			System.err.println("Mies: " + p.get(rmin + 1).createPlaceMark("Mies"));
			double left = isLeft(p.get(rmin - 1), p.get(rmin), p.get(rmin + 1));

			boolean result = left < 0;

//			System.err.println("Left: "+left+" result: "+result);
			return result;
		}
	}

	// private boolean isClockwise(List<GeoPoint> p) throws GeoException {
	// int i, j, k;
	// int count = 0;
	// double z;
	// int n = p.size();
	// double totalz = 0;
	// if (n < 3)
	// throw new GeoException("WTf? < 3 poly points?");
	//
	// for (i = 0; i < n; i++) {
	// j = (i + 1) % n;
	// k = (i + 2) % n;
	//
	// z = (p.get(j).lon - p.get(i).lon) * (p.get(k).lat - p.get(j).lat);
	// z -= (p.get(j).lat - p.get(i).lat) * (p.get(k).lon - p.get(j).lon);
	// if (z < 0) {
	// count--;
	// } else if (z > 0) {
	// count++;
	// }
	// totalz += z;
	// }
	// if (count < 0 && totalz > 0) {
	// }
	// if (totalz > 0) {
	// return (false);
	// } else if (totalz < 0) {
	// return (true);
	// } else {
	// throw new GeoException("WTf? No comprendo?");
	//
	// }
	// }

	private double isLeft(GeoPoint P0, GeoPoint P1, GeoPoint P2) {
		return ((P1.lon - P0.lon) * (P2.lat - P0.lat) - (P2.lon - P0.lon) * (P1.lat - P0.lat));
	}

	public boolean isEdgeOf(GeoPoint a, GeoPoint b) {
		int aIndex = myStartPoints.indexOf(a);
		int bIndex = myStartPoints.indexOf(b);
		if (aIndex < 0 || bIndex < 0) {
			return false;
		}

		return Math.abs(aIndex - bIndex) == 1;
	}

	public String getId() {
		return id;
	}

	public boolean hasSharedPoints(GeoPolygon g) {
		for (GeoPoint p : getPoints()) {
			GeoPoint next = getNextPoint(p);
			GeoPoint previous = getPreviousPoint(p);
			if (g.hasPoint(p) && (g.hasPoint(previous) || g.hasPoint(next))) {
				return true;
			}
		}
		return false;
	}

	// ACtually doesn't do anything
	public void reverseOrder() {
		System.err.println("BEFORE: " + getPoints().toString());
		// System.err.println("REVERSING:");
		List<GeoPoint> dd = new LinkedList<GeoPoint>();
		dd.addAll(getPoints());
		for (int i = 0; i < dd.size(); i++) {
			dd.set(i, getPoints().get((dd.size() - i) - 1));
		}
		myStartPoints = dd;
		System.err.println("AFTER: " + getPoints().toString());
	}
	


}
