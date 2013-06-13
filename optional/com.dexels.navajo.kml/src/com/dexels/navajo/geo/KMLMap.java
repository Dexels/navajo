package com.dexels.navajo.geo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.dexels.navajo.adapters.svg.NavajoSvgRenderAdapter;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.metadata.FormatDescription;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.geo.impl.AbstractKMLMap;
import com.dexels.navajo.geo.renderer.SvgRenderer;
import com.dexels.navajo.geo.zipcode.Gemeente2Population;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.UserException;

public class KMLMap extends AbstractKMLMap implements Mappable {

	Navajo inMessage = null;

	Binary myKmzData = null;
	Binary mySvgData = null;
	Binary myBitmapData = null;

	public boolean pointFile = false;
	public String messagePath = null;
	
	private final static Logger logger = LoggerFactory.getLogger(KMLMap.class);
	
	public void load( Access access) {
		this.inMessage = access.getInDoc();
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
			if ( !isPointFile() ) {
				File kmz = createKmlFile(inMessage);
				myKmzData = new Binary(kmz, false);
				kmz.delete();
				FormatDescription fd = new FormatDescription();
				fd.addFileExtension("kmz");
				fd.addMimeType("application/vnd.google-earth.kmz");
				myKmzData.setMimeType("application/vnd.google-earth.kmz");
				myKmzData.setFormatDescriptor(fd);
				return myKmzData;
			} else {
				File kml = createPointKmlFile(inMessage, messagePath);
				myKmzData = new Binary(kml, false);
				kml.delete();
				FormatDescription fd = new FormatDescription();
				fd.addFileExtension("kml");
				fd.addMimeType("application/vnd.google-earth.kml");
				myKmzData.setMimeType("application/vnd.google-earth.kml");
				myKmzData.setFormatDescriptor(fd);
				return myKmzData;
			}
		} catch (XMLParseException e) {
			logger.error("Error: ", e);
		} catch (IOException e) {
			logger.error("Error: ", e);
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
			if(ze!=null && ze.getName().equals("doc.kml")) {
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
		NavajoSvgRenderAdapter.renderPNG(mySvgData.getDataAsStream(), bos, bitmapWidth, bitmapHeight);
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
	
	public static void main(String[] args) {
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
//			Test t = new Test();
//			hm.inMessage = t.createTestMessage();
			FileInputStream fis = new FileInputStream("InitGenerateClubDataKnvb.tml");
			Navajo n = NavajoFactory.getInstance().createNavajo(fis);
			fis.close();
			hm.inMessage = n;
//			try{
//				n.write(System.err);
//			}catch(Exception e){
//				System.err.println("Null navajo");
//			}
			
			File xxx = hm.createPointKmlFile(hm.inMessage, "Clubs");
			
			logger.info("Saved file: " + xxx.getAbsolutePath());
			//File res = new File("c:/clubs.kml");
//			FileWriter fw = new FileWriter(res);
//			xxx.write(fw);
//			fw.flush();
//			fw.close();
			//System.err.println("KML Run finished...: "+res); 
			
		} catch (XMLParseException e) {
			logger.error("Error: ", e);
		} catch (IOException e) {
			logger.error("Error: ", e);
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


	public boolean isPointFile() {
		return pointFile;
	}


	public void setPointFile(boolean pointFile) {
		this.pointFile = pointFile;
	}


	public String getMessagePath() {
		return messagePath;
	}




}
