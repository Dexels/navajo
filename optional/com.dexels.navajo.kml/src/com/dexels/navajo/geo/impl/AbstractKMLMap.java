package com.dexels.navajo.geo.impl;

import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.geo.GeoColorizer;
import com.dexels.navajo.geo.LegendCreator;
import com.dexels.navajo.geo.color.BlackWhiteColorizer;
import com.dexels.navajo.geo.color.BlueRedGeoColorizer;
import com.dexels.navajo.geo.color.HueGeoColorizer;
import com.dexels.navajo.geo.color.RedGeoColorizer;
import com.dexels.navajo.geo.color.WhiteBlackColorizer;
import com.dexels.navajo.geo.color.WhiteOrangeColorizer;
import com.dexels.navajo.geo.color.WhiteRedColorizer;

public abstract class AbstractKMLMap {

	protected GeoColorizer myColorizer = null;

	protected String title = "";
	protected String colorizer = "redblue";
	protected boolean useLegend = true;
	protected int legendHeight = 400;
	protected int legendWidth = 200;

	protected int bitmapHeight = 800;
	protected int bitmapWidth = 500;

	protected int legendSteps = 6;

	protected Binary kmlData = null;
	protected Binary kmzData = null;

	protected String mapPath = "com/dexels/navajo/geo/nederland.kml";

	public boolean useLOD = false;

	//
	// protected double min = 0;
	// protected double max = 1;
	//

	protected double minLegend = 0;
	protected double maxLegend = 1000d;

	public XMLElement mapData(Message m) throws XMLParseException, IOException {

		Map<String, Message> messageMap = new HashMap<String, Message>();

		XMLElement xe = new CaseSensitiveXMLElement();

		xe.parseFromReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(mapPath)));
		if (m != null) {
			List<Message> msgElements = m.getAllMessages();
			for (Message message : msgElements) {
				Property key = message.getProperty("Key");
				String keyVal = "" + key.getTypedValue();
				messageMap.put(keyVal, message);
			}
		}

		// Create a map of all marker placemarks, for reference
		Map<String, XMLElement> markerMap = new HashMap<String, XMLElement>();
		List<XMLElement> markers = xe.getElementsByTagName("Placemark");
		for (XMLElement element : markers) {
			String id = element.getStringAttribute("id", "");
			if (id.startsWith("marker_")) {
				markerMap.put(id, element);
			}
		}

		List<XMLElement> l = xe.getElementsByTagName("Style");
		for (XMLElement element : l) {
			String stringAttribute = element.getStringAttribute("id");
			String id = stringAttribute.substring(8, stringAttribute.length() - 4);
			Message message = messageMap.get(id);
			renderStyle(element, message, markerMap);
		}

		return xe;
	}

	private void renderStyle(XMLElement style, Message message, Map<String, XMLElement> markerMap) {
		XMLElement polyStyle = style.getElementByTagName("PolyStyle");
		if (polyStyle == null) {
			return;
		}
		String styleId = style.getStringAttribute("id");
		if (styleId == null) {
			System.err.println("Huh?");
			System.err.println("style:\n" + polyStyle);
			return;
		}
		String markerId = null;
		if (styleId.startsWith("style")) {
			markerId = "marker_" + styleId.substring(5);
		}

		XMLElement markerPlacemark = markerMap.get(markerId);
		Object value = null;
		Property descriptionProperty;
		String descriptionString = null;
		// double dValue;
		if (message != null) {
			value = message.getProperty("Value").getTypedValue();
			descriptionProperty = message.getProperty("Description");
			descriptionString = (String) descriptionProperty.getTypedValue();
			// dValue = Double.parseDouble((String) value);
		} else {
			value = "0";
			// dValue = Double.parseDouble((String) value);
			descriptionString = "Geen data.";

		}

		XMLElement description = markerPlacemark.getElementByTagName("description");

		description.setContent(descriptionString);

		XMLElement lineStyle = style.getElementByTagName("LineStyle");
		lineStyle.addTagKeyValue("color", "55ffffff");
		lineStyle.addTagKeyValue("width", "1");

		double d = Double.parseDouble((String) value);

		String createColor = getSelectedColorizer().createGeoColorString(d, 0, 1);
		polyStyle.addTagKeyValue("color", createColor);
		polyStyle.addTagKeyValue("color", createColor);
	}

	public File createPointKmlFile(Navajo n, String messagePath) throws XMLParseException, IOException {
		Message m = n.getMessage(messagePath);
		List<Message> ll = ( m != null ? m.getAllMessages() : null );
		XMLElement kml = new CaseSensitiveXMLElement("kml");
		XMLElement document = new CaseSensitiveXMLElement("Document");
		kml.addChild(document);

		Message c = n.getMessage("Styles");

		if (c != null) {
			List<Message> colors = c.getAllMessages();
			for (Message message : colors) {
				XMLElement style = new CaseSensitiveXMLElement("Style");
				XMLElement iconStyle = new CaseSensitiveXMLElement("IconStyle");

				style.setAttribute("id", message.getProperty("StyleRef").getTypedValue());
				style.addChild(iconStyle);

				if (message.getProperty("IconColor") != null) {
					iconStyle.addTagKeyValue("color", "ff" + message.getProperty("IconColor").getTypedValue() + "");
				} else {
					iconStyle.addTagKeyValue("colorMode", "random");
				}

				XMLElement icon = new CaseSensitiveXMLElement("Icon");
				iconStyle.addChild(icon);
				String iconShape = "http://maps.google.com/mapfiles/kml/shapes/play.png";
				if (message.getProperty("Icon") != null) {
					iconShape = "http://maps.google.com/mapfiles/kml/" + message.getProperty("Icon").getTypedValue();
				}
				icon.addTagKeyValue("href", iconShape);

				document.addChild(style);
			}
		}

		Message hasFolders = n.getMessage("Folders");
		
		if ( hasFolders != null ) {
			List<Message> folders = hasFolders.getAllMessages();
			for ( Message folder: folders ) {
				String name = folder.getProperty("Name").getValue();
				XMLElement folderElt = createFolder(name);
				m = folder.getMessage(messagePath);
				ll = m.getAllMessages();
				for (Message message : ll) {
					XMLElement placemark = createPointPlaceMark(message);
					if (placemark != null) {
						folderElt.addChild(placemark);
					}
				}
				if ( folderElt != null ) {
					document.addChild(folderElt);
				}
			}
		} else {
			for (Message message : ll) {
				XMLElement placemark = createPointPlaceMark(message);
				//XMLElement placemark = createCirclePlacemark(message);
				if (placemark != null) {
					document.addChild(placemark);
				}
			}
		}

		File res = File.createTempFile("pointData", ".kml");
		FileWriter fw = new FileWriter(res);
		kml.write(fw);
		fw.flush();
		fw.close();

		return res;
	}

	protected XMLElement createCirclePlacemark(Message message) {

		int members =  Integer.parseInt(message.getProperty("MemberCount").getValue());

		XMLElement placemark = new CaseSensitiveXMLElement("Placemark");
		placemark.setAttribute("id", message.getProperty("OrganizationId").getTypedValue());
		placemark.addTagKeyValue("name", message.getProperty("Name").getValue());

		XMLElement style = new CaseSensitiveXMLElement("Style");
		XMLElement lineStyle = new CaseSensitiveXMLElement("LineStyle");
		lineStyle.addTagKeyValue("width", "1");
		lineStyle.addTagKeyValue("color", "99ff0000");
		
		XMLElement polyStyle = new CaseSensitiveXMLElement("PolyStyle");
		polyStyle.addTagKeyValue("color", "99ff0000");
		
		style.addChild(lineStyle);
		style.addChild(polyStyle);
		placemark.addChild(style);		
		
		XMLElement polygon = new CaseSensitiveXMLElement("Polygon");
		polygon.addTagKeyValue("extrude", "0");
		polygon.addTagKeyValue("tessellate", "1");
		polygon.addTagKeyValue("altitudeMode", "clampToGround");

		placemark.addChild(polygon);
		XMLElement outerBoundaryIs = new CaseSensitiveXMLElement("outerBoundaryIs");
		polygon.addChild(outerBoundaryIs);
		XMLElement LinearRing = new CaseSensitiveXMLElement("LinearRing");
		outerBoundaryIs.addChild(LinearRing);

		// Set coordinates as tagkeyvalue for the linear ring.

		String slon = message.getProperty("Longitude").getValue();
		String slat = message.getProperty("Latitude").getValue();
		if (slon.equals("-1.0") || slat.equals("-1.0")) {
			return null;
		}
		// point.addTagKeyValue("coordinates", slon+","+slat);

		double clat = Double.parseDouble(slat);
		double clon = Double.parseDouble(slon);
		double radius = 100 + members;
		int sides = 25;
		if(radius > 10000){
			sides = 50;
		}
		ArrayList<Double> coords = generateCircleCoordinates(clat, clon, radius, sides);
		String coordinates = "";
		for (int i = 0; i < coords.size(); i += 2) {
			double lon = coords.get(i + 1);
			double lat = coords.get(i);

			if (i > 0) {
				coordinates = coordinates + "\n";
			}
			coordinates = coordinates + lon + "," + lat + ",10";
		}

		LinearRing.addTagKeyValue("coordinates", coordinates);
		return placemark;
	}

	private ArrayList<Double> generateCircleCoordinates(double clat, double clon, double radius, int sides) {
		ArrayList<Double> polygon = new ArrayList<Double>();

		double lat_rad = Math.toRadians(clat);
		double lon_rad = Math.toRadians(clon);
		double d = radius / 6378137.0;

		double delta = 360.0 / sides;
		for (int i = 0; i <= sides; i++) {
			double radial = Math.toRadians(i * delta);
			
			double lat = Math.asin(Math.sin(lat_rad) * Math.cos(d) + Math.cos(lat_rad) * Math.sin(d) * Math.cos(radial));
			double dlon_rad = Math.atan2(Math.sin(radial) * Math.sin(d) * Math.cos(lat_rad), Math.cos(d) - Math.sin(lat_rad) * Math.sin(lat));
			double lon = ((lon_rad + dlon_rad + Math.PI) % (2 * Math.PI)) - Math.PI;
			
			polygon.add(Math.toDegrees(lat));
			polygon.add(Math.toDegrees(lon));

//			System.err.println("Lat: " + Math.toDegrees(lat) + ", lon: " + Math.toDegrees(lon));
		}
		return polygon;

	}

	private XMLElement createFolder(String name) {
	
		XMLElement folder = new CaseSensitiveXMLElement("Folder");
		XMLElement nameElt = new CaseSensitiveXMLElement("name");
		nameElt.setContent(name);
		folder.addChild(nameElt);
		
		return folder;
	}
	
	private XMLElement createPointPlaceMark(Message message) {
		XMLElement placemark = new CaseSensitiveXMLElement("Placemark");
		placemark.setAttribute("id", message.getProperty("Id").getTypedValue());
		placemark.addTagKeyValue("name", message.getProperty("Name").getValue());

		XMLElement point = new CaseSensitiveXMLElement("Point");
		placemark.addChild(point);
		String lon = message.getProperty("Longitude").getValue();
		String lat = message.getProperty("Latitude").getValue();
		if (lon.equals("-1.0") || lat.equals("-1.0")) {
			return null;
		}
		point.addTagKeyValue("coordinates", lon + "," + lat);

		// Introspect other properties.
		StringBuffer descr = new StringBuffer();
		ArrayList<Property> properties = message.getAllProperties();
		for (Iterator<Property> iterator = properties.iterator(); iterator.hasNext();) {
			Property property = iterator.next();
			if (!(property.getName().equals("Longitude") || property.getName().equals("Latitude") || property.getName().equals("Id") || property.getName().equals("Name"))) {
				descr.append(property.getDescription() + ": " + property.getValue() + "<br/>");
			}
		}

		placemark.addTagKeyValue("description", descr.toString());
		System.err.println("descr: " + descr);

		XMLElement region = new CaseSensitiveXMLElement("Region");
		if (useLOD) {
			placemark.addChild(region);
		}
		XMLElement latLonAltBox = new CaseSensitiveXMLElement("LatLonAltBox");
		region.addChild(latLonAltBox);
		latLonAltBox.addTagKeyValue("north", lat);
		latLonAltBox.addTagKeyValue("south", "" + (Double.parseDouble(lat) - 0.1));
		latLonAltBox.addTagKeyValue("east", lon);
		latLonAltBox.addTagKeyValue("west", "" + (Double.parseDouble(lon) - 0.1));

		XMLElement lod = new CaseSensitiveXMLElement("Lod");
		lod.addTagKeyValue("minLodPixels", "64");
		// lod.addTagKeyValue("maxLodPixels", "5024");
		lod.addTagKeyValue("minFadeExtent", "828");
		lod.addTagKeyValue("maxFadeExtent", "428");
		region.addChild(lod);

		if (message.getProperty("StyleRef") != null) {
			placemark.addTagKeyValue("styleUrl", "#" + message.getProperty("StyleRef").getTypedValue());
		}

		return placemark;

	}

	public File createKmlFile(Navajo n) throws IOException, XMLParseException {
		String messagePath = "Data";
		// workingDir.mkdirs();
		XMLElement xx = mapData(n.getMessage(messagePath));
		XMLElement overlay = xx.getElementByTagName("ScreenOverlay");

		Random rr = new Random(System.currentTimeMillis());
		String legendHref = "files/legendImage" + Math.abs(rr.nextInt()+1) + ".png";

		if (!useLegend) {
			overlay.getParent().removeChild(overlay);
		} else {
			XMLElement href = overlay.getElementByTagName("href");
			System.err.println("HREF Found: " + href);
			href.setContent(legendHref);
		}

		//		
		// FileWriter fw = new FileWriter(new File(workingDir,"data.tml"));
		// n.write(fw);
		// fw.flush();
		// fw.close();
		//		
		// File kmlFile = new File(workingDir,filePrefix+".kml");
		File kmlFile = File.createTempFile("locData", ".kml");

		FileWriter fw = new FileWriter(kmlFile);
		xx.write(fw);
		fw.close();

		File tempZip = File.createTempFile("locData", ".kmz");
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tempZip));
		// start kml entry:
		ZipEntry zipEntry = new ZipEntry("doc.kml");
		zos.putNextEntry(zipEntry);
		OutputStreamWriter osw = new OutputStreamWriter(zos);
		xx.write(osw);
		osw.flush();
		zos.flush();
		zos.closeEntry();

		GeoColorizer colorize = getSelectedColorizer();

		if (useLegend) {
			LegendCreator lc = new LegendCreator();
			File imageFile = lc.createLegendImagePath(title, legendSteps, minLegend, maxLegend, new Dimension(legendWidth, legendHeight), colorize);
			System.err.println("Legend link: " + legendHref);
			zipEntry = new ZipEntry(legendHref);
			zos.putNextEntry(zipEntry);
			FileInputStream fis = new FileInputStream(imageFile);
			copyResource(zos, fis);
			zos.closeEntry();
			fis.close();
			// imageFile.delete();
			zos.flush();
		}
		zos.close();
		// kmlFile.delete();

		return tempZip;
	}

	private GeoColorizer getSelectedColorizer() {
		if ("blackwhite".equals(colorizer)) {
			return new BlackWhiteColorizer();
		}
		if ("bluered".equals(colorizer)) {
			return new BlueRedGeoColorizer();
		}
		if ("hue".equals(colorizer)) {
			return new HueGeoColorizer();
		}
		if ("red".equals(colorizer)) {
			return new RedGeoColorizer();
		}
		if ("whiteblack".equals(colorizer)) {
			return new WhiteBlackColorizer();
		}
		if ("whiteorange".equals(colorizer)) {
			return new WhiteOrangeColorizer();
		}
		if ("whitered".equals(colorizer)) {
			return new WhiteRedColorizer();
		}
		// default:
		return new BlueRedGeoColorizer();
	}

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
	}

	public boolean isUseLOD() {
		return useLOD;
	}

	public void setUseLOD(boolean useLOD) {
		this.useLOD = useLOD;
	}

}
