package com.dexels.navajo.geo.impl;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.zip.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.geo.*;
import com.dexels.navajo.geo.color.*;

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
	protected String messagePath = "Data";
	
	protected Binary kmlData = null;
	protected Binary kmzData = null;



	protected String mapPath = "com/dexels/navajo/geo/nederland.kml";


//
//	protected double min = 0;
//	protected double max = 1;
//



	protected double minLegend = 0;
	protected double maxLegend = 1000d;



	public XMLElement mapData(Message m) throws XMLParseException, IOException {
		
		
		Map<String, Message> messageMap = new HashMap<String, Message>();
		
		
		XMLElement xe = new CaseSensitiveXMLElement();
		
		xe.parseFromReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(mapPath )));
		if (m != null) {
			List<Message> msgElements = m.getAllMessages();
			for (Message message : msgElements) {
				Property key = message.getProperty("Key");
				String keyVal = "" + key.getTypedValue();
				messageMap.put(keyVal, message);
			}
		}
		
		// Create a map of all marker placemarks, for reference
		Map<String,XMLElement> markerMap = new HashMap<String, XMLElement>();
		List<XMLElement> markers = xe.getElementsByTagName("Placemark");
		for (XMLElement element : markers) {
			String id = element.getStringAttribute("id","");
			if(id.startsWith("marker_")) {
				markerMap.put(id, element);
			}
		}
		
		List<XMLElement> l = xe.getElementsByTagName("Style");
		for (XMLElement element : l) {
			String stringAttribute = element.getStringAttribute("id");
			String id = stringAttribute.substring(8,stringAttribute.length()-4);
			Message message = messageMap.get(id);
			renderStyle(element,message,markerMap);
		}

		return xe;
	}

	
	private void renderStyle(XMLElement style, Message message, Map<String,XMLElement> markerMap) {
		XMLElement polyStyle = style.getElementByTagName("PolyStyle");
		if(polyStyle==null) {
			return;
		}
		String styleId = style.getStringAttribute("id");
		if(styleId==null) {
			System.err.println("Huh?");
			System.err.println("style:\n"+polyStyle);
			return;
		}
		String markerId = null;
		if(styleId.startsWith("style")) {
			markerId = "marker_"+styleId.substring(5);
		}
		
		XMLElement markerPlacemark = markerMap.get(markerId);
		Object value =  null;
		Property descriptionProperty;
		String descriptionString = null;
		double dValue;
		if (message!=null) {
			value = message.getProperty("Value").getTypedValue();
			descriptionProperty = message.getProperty("Description");
			descriptionString = (String) descriptionProperty.getTypedValue();
			dValue = Double.parseDouble((String) value);
		} else {
			value = "0";
			dValue = Double.parseDouble((String) value);
			descriptionString = "Geen data, dat is wel een mooie tekst";
				
		}
		
		XMLElement description = markerPlacemark.getElementByTagName("description");
		
		description.setContent(descriptionString);
		
		XMLElement lineStyle = style.getElementByTagName("LineStyle");
		lineStyle.addTagKeyValue("color", "55ffffff");
		lineStyle.addTagKeyValue("width", "1");
		
		
		double d = Double.parseDouble((String)value) ;
	
		String createColor = getSelectedColorizer().createGeoColorString(d,0,1);
		polyStyle.addTagKeyValue("color", createColor);
		polyStyle.addTagKeyValue("color", createColor);
	}

	public File createKmlFile(Navajo n, String filePrefix) throws IOException, XMLParseException, NavajoException {
		messagePath = "Data";
//		workingDir.mkdirs();
		XMLElement xx = mapData(n.getMessage(messagePath));
		XMLElement overlay = xx.getElementByTagName("ScreenOverlay");

		Random rr = new Random(System.currentTimeMillis());
		String legendHref = "files/legendImage"+Math.abs(rr.nextInt())+".png";
		
		if(!useLegend) {
			overlay.getParent().removeChild(overlay);
		} else {
			XMLElement href = overlay.getElementByTagName("href");
			System.err.println("HREF Found: "+href);
			href.setContent(legendHref);
		}

		
//		
//		FileWriter fw = new FileWriter(new File(workingDir,"data.tml"));
//		n.write(fw);
//		fw.flush();
//		fw.close();
//		
//		File kmlFile = new File(workingDir,filePrefix+".kml");
		File kmlFile = File.createTempFile("locData", ".kml");
		
		FileWriter fw = new FileWriter(kmlFile);
		xx.write(fw);
		fw.close();


		File tempZip  = File.createTempFile("locData", ".kmz");
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
		
		if(useLegend) {
			LegendCreator lc = new LegendCreator();
			File imageFile = lc.createLegendImagePath(title, legendSteps, minLegend, maxLegend, new Dimension(legendWidth,legendHeight), colorize);
			System.err.println("Legend link: "+legendHref);
			zipEntry = new ZipEntry(legendHref);
			zos.putNextEntry(zipEntry);
			FileInputStream fis = new FileInputStream(imageFile);
			copyResource(zos, fis);
			zos.closeEntry();
			fis.close();
//			imageFile.delete();
			zos.flush();
		}
		zos.close();
//		kmlFile.delete();
		
		return tempZip;
	}

	private GeoColorizer getSelectedColorizer() {
		if("blackwhite".equals(colorizer)) {
			return new BlackWhiteColorizer();
		}
		if("bluered".equals(colorizer)) {
			return new BlueRedGeoColorizer();
		}
		if("hue".equals(colorizer)) {
			return new HueGeoColorizer();
		}
		if("red".equals(colorizer)) {
			return new RedGeoColorizer();
		}
		if("whiteblack".equals(colorizer)) {
			return new WhiteBlackColorizer();
		}
		if("whiteorange".equals(colorizer)) {
			return new WhiteOrangeColorizer();
		}
		if("whitered".equals(colorizer)) {
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


}
