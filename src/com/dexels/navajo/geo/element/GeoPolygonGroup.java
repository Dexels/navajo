package com.dexels.navajo.geo.element;

import java.io.*;
import java.util.*;

import com.dexels.navajo.document.nanoimpl.*;

public class GeoPolygonGroup extends GeoElement {
	private final List<GeoPolygon> myPolygons = new ArrayList<GeoPolygon>();
	List<GeoPoint> allPoints = new ArrayList<GeoPoint>();
	List<GeoPoint> allDistinctPoints = new ArrayList<GeoPoint>();
	private final String myName;
	Map<GeoPolygon, Set<GeoPoint>> ownershipMap = new HashMap<GeoPolygon, Set<GeoPoint>>();
	Map<GeoPoint, Set<GeoPolygon>> invOwnershipMap = new HashMap<GeoPoint, Set<GeoPolygon>>();

	private int joinSequence = 0;
	private int reverseStack = 0;

	public GeoPolygonGroup(List<GeoPolygon> a, String name) throws GeoException {
		myName = name;
		myPolygons.addAll(a);
		normalize();
	}

	public XMLElement createDocument() throws UnsupportedEncodingException, IOException {
		XMLElement template = new CaseSensitiveXMLElement();
		template.parseFromStream(getClass().getResourceAsStream("template.kml"));
		XMLElement doc = template.getElementByTagName("Document");
		int count = 0;
		for (GeoPolygon g : myPolygons) {
			XMLElement x = g.createPlaceMark();
			doc.addChild(x);
			count++;
		}
		return template;
	}

	@Override
	public XMLElement createPlaceMark() {
		XMLElement c = new CaseSensitiveXMLElement("Placemark");
		c.addTagKeyValue("name", myName);
		c.addChild(createElement());
		return c;
	}

	public List<XMLElement> createPlaceMarkList() {
		List<XMLElement> result = new ArrayList<XMLElement>();
		result.add(createPlaceMark());
		return result;
	}

	public XMLElement createElement() {
		XMLElement c = new CaseSensitiveXMLElement("MultiGeometry");
		List<GeoPolygon> polys;
		try {
			polys = createOutlinePoly();
			for (GeoPolygon g : polys) {
				XMLElement poly = g.createElement();
				c.addChild(poly);
			}
			return c;
		} catch (GeoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public GeoPoint getClosest(GeoPoint g) {
		GeoPoint current = null;
		for (GeoPoint p : allDistinctPoints) {
			if (current == null || p.distance(current) > p.distance(g)) {
				current = p;
			}
		}
		if (current == null) {
			return null;
		}
		return current;
	}

	private void normalize() throws GeoException {
		long currentTime = System.currentTimeMillis();
		for (GeoPolygon g : myPolygons) {
			if (!g.isClockwise()) {
				// don't know whats wrong here
				// g.reverseOrder();
			}
		}

		for (GeoPolygon g : myPolygons) {
			List<GeoPoint> currentPoints = g.getPoints();
			List<GeoPoint> newPoints = new ArrayList<GeoPoint>();
			for (int i = 0; i < currentPoints.size(); i++) {
				GeoPoint geoPoint = currentPoints.get(i);
				boolean found = false;
				GeoPoint ggg = getClosest(geoPoint);
				if (ggg != null) {
					found = ggg.distance(geoPoint) < GeoPoint.MIN_DISTANCE;
				}
				if (!found) {
					ggg = null;
					newPoints.add(geoPoint);
					allDistinctPoints.add(geoPoint);
				} else {
					if (newPoints.size() > 0) {
						GeoPoint last = newPoints.get(newPoints.size() - 1);
						if (!last.equals(geoPoint)) {
							newPoints.add(geoPoint);
							// addOwnership(geoPoint, g);
						} else {
							// this point is the same as last point, so ignore
						}
					} else {
						newPoints.add(ggg);
					}
				}
			}
			System.err.println("Distinct points found");
			currentPoints.clear();
			currentPoints.addAll(newPoints);
			for (GeoPoint geoPoint2 : newPoints) {
				addOwnership(geoPoint2, g);
			}
			// polyCount++;
		}

		System.err.println("Starting outline");
		createOutlinePoly();
		long currentTime2 = System.currentTimeMillis();
		System.err.println("Normalize took: " + (currentTime2 - currentTime));
		// dumpMe("polyGroupComplete.kml");

		// System.err.println("Distinct points: " + allDistinctPoints.size());

	}

	private List<GeoPolygon> createOutlinePoly() throws GeoException {
		int changeCount = 0;
		// dumpMe("debu_baInit.kml");
		List<GeoPolygon> polys = new LinkedList<GeoPolygon>(myPolygons);
		boolean complete = false;
		System.err.println("# of poly's: " + polys.size());
		while (!complete) {
			try {
				int outer = 0;
				for (GeoPolygon g : polys) {
					int inner = 0;
					for (GeoPolygon h : polys) {
						if (g != h) {
							// System.err.println("Checking: "+inner+"/"+outer);
							if (h.contains(g)) {
								// overlap found
								polys.remove(g);
								// dumpMe("debu_ba"+changeCount+".kml");

								changeCount++;
								throw new GeoChangeException("joined  poly. Remaining: " + polys.size());
							}
							boolean shared = g.hasSharedPoints(h);
							if (shared) {
								boolean aClockwise = g.isClockwise();
								System.err.println("A CLOCKWISITY: " + aClockwise);
								boolean bClockwise = h.isClockwise();
								System.err.println("B CLOCKWISITY: " + bClockwise);

								GeoPolygon gpp;
								try {
									gpp = join(g, h);
								} catch (GeoException e) {
									e.printStackTrace();
									continue;
								}
								boolean resultClockwise = gpp.isClockwise();
								if (!resultClockwise) {
									// ok, we have a problem, we are in an inner
									// ring
									System.err.println("BEWARE, INNER RING");
									List<GeoPoint> prohibit = gpp.getPoints();
									gpp = join(g, h, prohibit);
								}
								g.setId("Old1");
								h.setId("Old2");
								gpp.setId("Result");
								System.err.println("RESULT CLOCKWISITY: " + resultClockwise);
								polys.remove(g);
								polys.remove(h);
								changeCount++;
								throw new GeoChangeException("joined  poly. Remaining: " + polys.size());
							}
						}
						inner++;
					}

					// polyCount++;

					outer++;

				}
				complete = true;
			} catch (GeoChangeException e) {
				System.err.println("change detected, continuing: " + e.getMessage());
			}

		}
		return polys;
	}

	public void dumpMe(String filename) {
		dumpMe(filename, getPolygons());
	}

	private void dumpMe(String filename, List<GeoPolygon> pp) {
		List<XMLElement> debugList = new ArrayList<XMLElement>();
		for (GeoPolygon geoPoly : pp) {
			debugList.addAll(geoPoly.createPlaceMarkList());
		}

		XMLElement kml = new CaseSensitiveXMLElement("kml");
		XMLElement doc = new CaseSensitiveXMLElement("Document");
		kml.addChild(doc);
		for (XMLElement element : debugList) {
			doc.addChild(element);
		}

		try {
			writeElement(filename, kml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private GeoPolygon join(GeoPolygon a, GeoPolygon b) throws GeoException {
		return join(a, b, new ArrayList<GeoPoint>());
	}

	private GeoPolygon join(GeoPolygon a, GeoPolygon b, List<GeoPoint> prohibited) throws GeoException {
		GeoPoint unJoinedPoint = null;
		if (a == b) {
			throw new GeoException("WTfuuck?");
		}
		for (GeoPoint aPoint : a.getPoints()) {
			if (!b.getPoints().contains(aPoint) && !prohibited.contains(aPoint)) {
				unJoinedPoint = aPoint;
				break;
			}
		}

		if (unJoinedPoint == null) {

			if (reverseStack > 2) {
				System.err.println("Reversal error. can not isolate point");
				System.err.println("Getting desparate, reversing and seeing if they are identical.");
				GeoPolygon temp = new GeoPolygon(a.getPoints());
				temp.reverseOrder();
				if (temp.equals(b)) {
					System.err.println("True equality! just discarding a!");
					return b;
				}
				XMLElement ee = a.createPlaceMarkList().get(0);
				XMLElement ff = b.createPlaceMarkList().get(0);
				System.err.println("<kml><Document>");
				System.err.println(ee.toString() + "\n" + ff.toString());
				System.err.println("</Document></kml>");
				throw new GeoException("WTF?");
			}
			reverseStack++;
			GeoPolygon join;
			try {
			join = join(b, a, prohibited);
			if (!join.isClockwise()) {
				prohibited.addAll(join.getPoints());
				join = join(b, a, prohibited);
			}
			} catch(GeoException g) {
				g.printStackTrace();
				System.err.println("Geo exception: "+g.getMessage()+" continuing");
				return null;
			}
			
			joinSequence++;

			reverseStack--;
			return join;
		}
		List<GeoPoint> ppp = new ArrayList<GeoPoint>();
		ppp.add(unJoinedPoint);
		if (!a.isClockwise()) {
			a.reverseOrder();
		}
		if (!b.isClockwise()) {
			b.reverseOrder();
		}

		try {
			join(null, a, b, ppp, prohibited);

		} catch (Exception e) {
			e.printStackTrace();
		}
		GeoPolygon gp = new GeoPolygon(ppp);

		if (!gp.isClockwise()) {
			prohibited.addAll(ppp);
			ppp.clear();
			gp = join(a, b, prohibited);
			ppp = gp.getPoints();
			if (!gp.isClockwise()) {
			}

		}
		return gp;

	}

	private void join(XMLElement debugDoc, GeoPolygon a, GeoPolygon b, List<GeoPoint> result, List<GeoPoint> prohibited)
			throws GeoException {
		GeoPoint currentPoint = result.get(result.size() - 1);

		GeoPoint nextA = a.getNextPoint(currentPoint);
		if (debugDoc != null) {
			XMLElement xxx = nextA.createPlaceMark("NEXT:" + result.size());
			debugDoc.addChild(xxx);
		}

		while (prohibited.contains(nextA)) {
			nextA = a.getNextPoint(nextA);
		}
		if (nextA == null) {
			throw new GeoException("HOH?");
		}

		boolean hasPoint = b.hasPoint(nextA);
		if (!hasPoint) {

			if (result.contains(nextA)) {
				return;
			}
			result.add(nextA);
			join(debugDoc, a, b, result, prohibited);
		} else {
			if (result.contains(nextA)) {
				return; // poly complete;
			}
			result.add(nextA);
			join(debugDoc, b, a, result, prohibited);
		}
	}

	private void addOwnership(GeoPoint newPoint, GeoPolygon g) {
		Set<GeoPoint> l = ownershipMap.get(g);
		if (l == null) {
			l = new HashSet<GeoPoint>();
			ownershipMap.put(g, l);
		}
		l.add(newPoint);

		Set<GeoPolygon> l2 = invOwnershipMap.get(newPoint);
		if (l2 == null) {
			l2 = new HashSet<GeoPolygon>();
			invOwnershipMap.put(newPoint, l2);
		}
		l2.add(g);

	}

	public List<GeoPolygon> getPolygons() {
		List<GeoPolygon> result = new ArrayList<GeoPolygon>();
		result.addAll(myPolygons);
		return result;
	}

}
