package com.dexels.navajo.geo.renderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.dexels.navajo.adapters.svg.NavajoSvgRenderAdapter;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.document.types.Binary;

public class SvgRenderer {

	private double centreLon = 5;
	private double centreLat = 51.5;
	
	private double rootX = -1.2;
	private double rootY = -2.5;
	private double rootWidth = 3;
	private double rootHeight = 3.5;

	private String background = "#333399";
	
	public static void main(String[] args) throws XMLParseException, IOException {
		SvgRenderer sv = new SvgRenderer();
		sv.renderToSvg(new File("locData51652.kml"));
		sv.renderSvgToPng();
		
	}

	public Binary renderToBinary(InputStream is) throws XMLParseException, IOException {
		XMLElement svgRoot = renderStream(is);
		Binary result = new Binary();
		OutputStream os = result.getOutputStream();
		Writer fw = new OutputStreamWriter(os);
		svgRoot.write(fw);
		fw.flush();
		fw.close();
		os.close();
		
		return result;
	}	
	public void renderToSvg(File kml) throws XMLParseException, IOException {
		FileInputStream fis = new FileInputStream(kml);
		XMLElement svgRoot = renderStream(fis);
		FileWriter fw = new FileWriter("output.svg");
		svgRoot.write(fw);
		fw.flush();
		fw.close();
//		renderSvgToPng();
	}


	private XMLElement renderStream(InputStream fis) throws IOException {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.parseFromReader(new InputStreamReader(fis,"UTF-8"));
		fis.close();

		Map<String,XMLElement> styleMap = new HashMap<String, XMLElement>();

		List<XMLElement> style = xe.getElementsByTagName("Style");
		for (XMLElement element : style) {
			String styleId = element.getStringAttribute("id");
			styleMap.put(styleId, element);
		}
		System.err.println("# of styles: "+styleMap.size());
		// Create a map of all marker placemarks, for reference
		Map<String,XMLElement> markerMap = new HashMap<String, XMLElement>();

		List<XMLElement> markers = xe.getElementsByTagName("Placemark");

		XMLElement svgRoot = createSvgRoot();
		
		for (XMLElement element : markers) {
			String id = element.getStringAttribute("id","");
			if(id.startsWith("marker_")) {
				markerMap.put(id, element);
			}
			XMLElement multi = element.getElementByTagName("MultiGeometry");
			String styleUrl = element.getElementByTagName("styleUrl").getContent();
			if(multi!=null) {
				XMLElement styleElement = styleMap.get(styleUrl.substring(1));
				XMLElement svgElement = createSvgElement(multi,styleElement);
				svgRoot.addChild(svgElement);
			}
		}
		return svgRoot;
	}


	private void renderSvgToPng() throws FileNotFoundException, IOException {
		FileInputStream fizz = new FileInputStream("output.svg");
		FileOutputStream fozz = new FileOutputStream("output.png");
		NavajoSvgRenderAdapter.renderPNG(fizz, fozz,400,800);
		fozz.flush();
		fozz.close();
		fizz.close();
	}


	private XMLElement createSvgRoot() {
//		<svg  viewBox="3 50 3 4" width="100%" height="100%" version="1.1" xmlns="http://www.w3.org/2000/svg">
		XMLElement root = new CaseSensitiveXMLElement("svg");
		root.setAttribute("viewBox", ""+rootX+" "+rootY+" "+rootWidth+" "+rootHeight);
		root.setAttribute("height", "100%");
		root.setAttribute("width", "100%");
		root.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		if(background!=null) {
			XMLElement backgroundRect = new CaseSensitiveXMLElement("rect");
			backgroundRect.setAttribute("x", rootX);
			backgroundRect.setAttribute("y", rootY);
			backgroundRect.setAttribute("width", rootWidth);
			backgroundRect.setAttribute("height", rootHeight);
			backgroundRect.setAttribute("fill", background);
			root.addChild(backgroundRect);
		}
//		<rect x="-2" y="-2" width="4" height="4" fill="#000088"/>
		return root;
	}


	private XMLElement createSvgElement(XMLElement multi, XMLElement styleElement) {
//		<MultiGeometry>
//        <Polygon id="Result">
//            <outerBoundaryIs>
//                <LinearRing>
//                    <coordinates>5.623634,52.650148 5.622156,52.650151 5.618618,52.650696 5.616777,52.651468 5.616309,52.651052 5.61625,52.651061 5.601499,52.656481 5.601517,52.660075 5.598908,52.660874 5.59885,52.660892 5.598565,52.660979 5.592649,52.660091 5.592662,52.662787 5.592663,52.663039 5.592663,52.663087 5.592695,52.669884 5.592695,52.669897 5.592695,52.669977 5.592725,52.676267 5.59716,52.67626 5.607537,52.681633 5.613452,52.681622 5.625287,52.682498 5.643032,52.682461 5.641542,52.678015 5.641527,52.67797 5.641546,52.677954 5.654763,52.666258 5.642906,52.660892 5.64315,52.660683 5.643336,52.660524 5.650259,52.654585 5.648749,52.649196 5.628067,52.650139 5.627379,52.65014</coordinates>
//                </LinearRing>
//            </outerBoundaryIs>
//        </Polygon>
//    </MultiGeometry>

		XMLElement group = new CaseSensitiveXMLElement("g");
		
		Vector<XMLElement> poly = multi.getElementsByTagName("Polygon");
		for (XMLElement element : poly) {
			XMLElement lin = element.getElementByTagName("LinearRing");
			String coordinates = lin.getElementByTagName("coordinates").getContent();
			XMLElement polygon = new CaseSensitiveXMLElement("polygon");
			applyPoints(polygon,coordinates);
			if(styleElement!=null) {
				System.err.println("Applying style");
				applyKmlStyle(polygon, styleElement);
			}
			group.addChild(polygon);
		}
		
//		 <Style id="stylegm_1509.kml">
//         <IconStyle>
//             <color>ffffffaa</color>
//             <scale>0.8</scale>
//         </IconStyle>
//         <LabelStyle>
//             <scale>0.8</scale>
//         </LabelStyle>
//         <LineStyle>
//             <color>55ffffff</color>
//             <width>1</width>
//         </LineStyle>
//         <PolyStyle>
//             <fill>1</fill>
//             <color>c03e9fff</color>
//         </PolyStyle>
//     </Style>
		
		return group;
	}
	
	private void applyPoints(XMLElement polygon, String coordinates) {
		StringTokenizer st = new StringTokenizer(coordinates," ");
		StringBuffer sb = new StringBuffer();
		while (st.hasMoreElements()) {
			String object = (String) st.nextElement();
			StringTokenizer coords = new StringTokenizer(object,",");
			double lon = Double.parseDouble(coords.nextToken());
			double lat = Double.parseDouble(coords.nextToken());
			double[] result = normalizeCoordinates(lon,lat);
			sb.append(result[0]);
			sb.append(",");
			sb.append(result[1]);
			sb.append(" ");
		}
		polygon.setAttribute("points", sb.toString());
		
	}


	private double[] normalizeCoordinates(double lon, double lat) {
		double scale = Math.cos(lat * Math.PI /180);
		double newlat = centreLat - lat;
		double templon = lon - centreLon;
		double newlon = templon * scale;
//		System.err.println("Scale: "+scale);
		double[] result = new double[] {newlon,newlat};
		return result;
	}

	public void applyKmlStyle(XMLElement svgElement, XMLElement kmlStyle) {
		// assuming fill = 1
//		style="fill:blue;stroke:red;stroke-width:20;fill-opacity:0.2;stroke-opacity:0.8"/>
		XMLElement poly = kmlStyle.getElementByTagName("PolyStyle");
//		XMLElement line = kmlStyle.getElementByTagName("LineStyle");
		String polyColor = poly.getElementByTagName("color").getContent();
		double opacity = getOpacity(polyColor);
		svgElement.setAttribute("opacity", opacity);
		svgElement.setAttribute("fill", getSvgColor(polyColor));
	}


	private String getSvgColor(String kmlColor) {
		if(kmlColor.length()<8) {
			throw new IllegalArgumentException("Whoops, no alpha, can't deal with that");
		}
		String red = kmlColor.substring(6,8);
		String green = kmlColor.substring(4,6);
		String blue = kmlColor.substring(2,4);
		return "#"+red+green+blue;
	}
	
	private double getOpacity(String kmlColor) {
		// KML uses ABGR, Svg RGB with separate opacity
		if(kmlColor.length()<0) {
			// no alpha?
			return 1;
		}
		String alpha = kmlColor.substring(0,2);
		Integer b = Integer.decode("0x"+alpha);
		double res = b.intValue();
		System.err.println("opa: "+res);
		return res/255;
	}
	
}
