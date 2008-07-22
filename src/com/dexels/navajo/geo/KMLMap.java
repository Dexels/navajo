package com.dexels.navajo.geo;

import java.io.*;
import java.util.zip.*;

import metadata.*;

import com.dexels.navajo.adapters.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.geo.impl.*;
import com.dexels.navajo.geo.renderer.*;
import com.dexels.navajo.geo.test.*;
import com.dexels.navajo.geo.zipcode.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;

public class KMLMap extends AbstractKMLMap implements Mappable {

	Navajo inMessage = null;

	Binary myKmzData = null;
	Binary mySvgData = null;
	Binary myBitmapData = null;
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		this.inMessage = inMessage;
	}


	public void store() throws MappableException, UserException {
		
	}
	 
	public GeoColorizer getMyColorizer() {
		return myColorizer;
	}


	public void setMyColorizer(GeoColorizer myColorizer) {
		this.myColorizer = myColorizer;
	}


	public boolean isUseLegend() {
		return useLegend;
	}

	
	public void setBitmapHeight(int bitmapHeight) {
		this.bitmapHeight = bitmapHeight;
	}


	public void setBitmapWidth(int bitmapWidth) {
		this.bitmapWidth = bitmapWidth;
	}

	public void setUseLegend(boolean useLegend) {
		this.useLegend = useLegend;
	}


	public int getLegendHeight() {
		return legendHeight;
	}


	public void setLegendHeight(int legendHeight) {
		this.legendHeight = legendHeight;
	}


	public int getLegendWidth() {
		return legendWidth;
	}


	public void setLegendWidth(int legendWidth) {
		this.legendWidth = legendWidth;
	}


	public int getLegendSteps() {
		return legendSteps;
	}


	public void setLegendSteps(int legendSteps) {
		this.legendSteps = legendSteps;
	}

/**
 * TODO: Make thread safe
 * @return
 */
	public Binary getKmlData() {
		try {
			File kmz = createKmlFile(inMessage,colorizer);
			myKmzData = new Binary(kmz, false);
			kmz.delete();
			FormatDescription fd = new FormatDescription();
			fd.addFileExtension("kmz");
			fd.addMimeType("application/vnd.google-earth.kmz");
			myKmzData.setMimeType("application/vnd.google-earth.kmz");
			myKmzData.setFormatDescriptor(fd);
			return myKmzData;
		} catch (XMLParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Binary getSvgData() throws IOException {
		if(myKmzData==null) {
			getKmlData();
		}
		InputStream bis = myKmzData.getDataAsStream();
		ZipInputStream zis = new ZipInputStream(bis);
		ZipEntry ze = null;
		do {
			ze = zis.getNextEntry();
			if(ze.getName().equals("doc.kml")) {
				SvgRenderer sr = new SvgRenderer();
				mySvgData = sr.renderToBinary(zis);
				FormatDescription fd = new FormatDescription();
				fd.addFileExtension("svg");
				fd.addMimeType("image/svg");
				myKmzData.setMimeType("image/svg");

				zis.close();
				return mySvgData;
			}
			
		} while(ze!=null);
		zis.close();
		return null;
	}
	
	public Binary getBitmapData() throws IOException {
		if(mySvgData==null) {
			getSvgData();
		}
		myBitmapData = new Binary();
		OutputStream bos = myBitmapData.getOutputStream();
		NavajoSvgRenderAdapter.render(mySvgData.getDataAsStream(), bos, bitmapWidth, bitmapHeight);
		return myBitmapData;
	}




	public void setTitle(String title) {
		this.title = title;
	}


	public void setColorizer(String colorizer) {
		this.colorizer = colorizer;
	}
	
	public void setMapPath(String mapPath) {
		this.mapPath = mapPath;
	}
	
	public static void main(String[] args) throws NavajoException {
		KMLMap hm = new KMLMap();
		hm.setColorizer("whiteorange") ;
		hm.setLegendHeight(250);
		hm.setLegendSteps(7);
		hm.setTitle("Aantal mannekes (Per 1000)");
//		hm.setMin(0.05);
//		hm.setMax(0.11);
		hm.setMinLegend(0);
		hm.setMaxLegend(1000);
		Gemeente2Population.init();
		try {
			Test t = new Test();
			hm.inMessage = t.createTestMessage();
			File res = hm.createKmlFile(hm.inMessage, "KnvbMember");
			System.err.println("KML Run finished...: "+res); 
			
			} catch (XMLParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setMinLegend(double minLegend) {
		this.minLegend = minLegend;
	}


	public void setMaxLegend(double maxLegend) {
		this.maxLegend = maxLegend;
	}



	public void setMessagePath(String messagePath) {
		this.messagePath = messagePath;
	}

//
//	public void setMin(double min) {
//		this.min = min;
//	}
//
//
//	public void setMax(double max) {
//		this.max = max;
//	}


	public void kill() {
	}



}
