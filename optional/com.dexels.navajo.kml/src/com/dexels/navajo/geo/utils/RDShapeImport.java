package com.dexels.navajo.geo.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbn.openmap.dataAccess.shape.DbfFile;
import com.bbn.openmap.io.BinaryFile;
import com.bbn.openmap.layer.shape.ESRIBoundingBox;
import com.bbn.openmap.layer.shape.ESRIPoint;
import com.bbn.openmap.layer.shape.ESRIPoly;
import com.bbn.openmap.layer.shape.ESRIPoly.ESRIFloatPoly;
import com.bbn.openmap.layer.shape.ESRIPolygonRecord;
import com.bbn.openmap.layer.shape.ESRIRecord;
import com.bbn.openmap.layer.shape.ShapeFile;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;

public class RDShapeImport {

	private ShapeFile sf;
	private DbfFile db;
	
	private final static Logger logger = LoggerFactory
			.getLogger(RDShapeImport.class);

	public RDShapeImport(String prefix) {
		try {
			NavajoClientFactory.resetClient();
			NavajoClientFactory.createDefaultClient();
			NavajoClientFactory.getClient().setServerUrl("penelope1.dexels.com/sportlink/test/knvb/Postman");
			NavajoClientFactory.getClient().setUsername("ROOT");
			NavajoClientFactory.getClient().setPassword("R20T");
			
			File shapeFile = new File(prefix + ".shp");
			db = new DbfFile(new BinaryFile(new File(prefix + ".dbf")));
			db.readData();
			sf = new ShapeFile(shapeFile);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	@SuppressWarnings("rawtypes")
	public void parseData(boolean storeBoundingBox) {
		try {
			ESRIRecord record = null;
			int count = 0;
//			File csv = new File("c:/gemeente.csv");
//			BufferedWriter fw = new BufferedWriter(new FileWriter(csv));
			
			while ((record = sf.getNextRecord()) != null) {
				// Record numbers??
				ArrayList data = (ArrayList) db.getRecord(record.getRecordNumber()-1);
								
				db.getValueAt(count, 0);
				count++;
				String id = (String)data.get(0);

				File recordFile = File.createTempFile("shape", "dat");
				BufferedWriter fw = new BufferedWriter(new FileWriter(recordFile));
				fw.write("SHAPE: " + id + "\n");
				if (record instanceof ESRIPolygonRecord) {
					ESRIPolygonRecord polygon = (ESRIPolygonRecord) record;
					ESRIPoly[] pols = polygon.polygons;
					ESRIBoundingBox bbox = polygon.getBoundingBox();
					ESRIPoint min = bbox.min;
					ESRIPoint max = bbox.max;
					
					double[] min_latlon = getLatLonForRD(min.x, min.y);
					double[] max_latlon = getLatLonForRD(max.x, max.y);
					
					if(storeBoundingBox){
						storeBoundingBox(id, min_latlon[0], min_latlon[1], max_latlon[0], max_latlon[1]);
					}
					
					for (int i = 0; i < pols.length; i++) {
						fw.write("POLYGON: " + (i+1) + "\n");
						
						ESRIPoly poly = pols[i];						
						ESRIFloatPoly fp = (ESRIFloatPoly) poly;						
						for (int j = 0; j < fp.nPoints; j++) {
							double xd = fp.getX(j);
							double yd = fp.getY(j);

							double[] latlon = getLatLonForRD(xd, yd);
							//storePolygonPoints(id, i + 1, latlon[0], latlon[1]);
							fw.write(latlon[1] + "," + latlon[0] + ",0\n");
							
						}						
					}
					
					
				}	
				fw.flush();
				fw.close();
				
				storeBinaryShapeRecord(id, recordFile);
			}
			
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}
	
	private void storeBinaryShapeRecord(String shapeId, File recordFile){
		try{
			Binary data = new Binary(recordFile);
			Navajo pms = NavajoClientFactory.getClient().doSimpleSend("geospatial/InitInsertCBSPolyPoint");
			Message params =pms.getMessage("Parameters");
			if(params != null){
				params.getProperty("ShapeId").setValue(shapeId);
				params.getProperty("ShapeData").setValue(data);
				NavajoClientFactory.getClient().doSimpleSend(params.getRootDoc(), "geospatial/ProcessInsertCBSPolyPoint");
			}
		}catch(Exception e){
			logger.error("Error: ", e);
		}
	}
	

	private double[] getLatLonForRD(double x, double y) {
		double dX = (x - 155000) / 100000.0;
		double dY = (y - 463000) / 100000.0;
		double SomN = (3235.65389 * dY) + (-32.58297 * Math.pow(dX, 2.0)) + (-0.2475 * Math.pow(dY, 2.0)) + (-0.84978 * Math.pow(dX, 2.0) * dY) + (-0.0655 * Math.pow(dY, 3.0)) + (-0.01709 * Math.pow(dX, 2.0) * Math.pow(dY, 2.0)) + (-0.00738 * dX) + (0.0053 * Math.pow(dX, 4.0)) + (-0.00039 * Math.pow(dX, 2.0) * Math.pow(dY, 3.0)) + (0.00033 * Math.pow(dX, 4.0) * dY) + (-0.00012 * dX * dY);
		double SomE = (5260.52916 * dX) + (105.94684 * dX * dY) + (2.45656 * dX * Math.pow(dY, 2.0)) + (-0.81885 * Math.pow(dX, 3.0)) + (0.05594 * dX * Math.pow(dY, 3.0)) + (-0.05607 * Math.pow(dX, 3.0) * dY) + (0.01199 * dY) + (-0.00256 * Math.pow(dX, 3.0) * Math.pow(dY, 2.0)) + (0.00128 * dX * Math.pow(dY, 4.0)) + (0.00022 * Math.pow(dY, 2.0)) + (-0.00022 * Math.pow(dX, 2.0)) + (0.00026 * Math.pow(dX, 5.0));
		double lat = 52.15517440 + (SomN / 3600.0);
		double lon = 5.38720621 + (SomE / 3600.0);
		return new double[] { lat, lon };
	}

	private void storeBoundingBox(String shapeId, double min_lat, double min_lon, double max_lat, double max_lon) {
		try {
			System.err.println(shapeId + ",  BBOX: " + min_lat + ", " + min_lon + " - " + max_lat + ", " + max_lon);

			Navajo pms = NavajoClientFactory.getClient().doSimpleSend("geospatial/InitUpdateCBSBoundingBox");
			Message params = pms.getMessage( "Parameters");
			if(params != null){				
				params.getProperty("ShapeId").setValue(shapeId);
				params.getProperty("MinLat").setValue(min_lat);
				params.getProperty("MinLon").setValue(min_lon);
				params.getProperty("MaxLat").setValue(max_lat);
				params.getProperty("MaxLon").setValue(max_lon);
			} else {
				System.err.println("No connection");
				return;
			}
			
			if (shapeId.startsWith("GM")) {
				// Gemeente bounding box
				params.getProperty("Table").setValue("cbs_gemeente");
				params.getProperty("ShapeIdKey").setValue("gm_code");
			}

			if (shapeId.startsWith("WK")) {
				// Wijk bounding box
				params.getProperty("Table").setValue("cbs_wijk");
				params.getProperty("ShapeIdKey").setValue("wk_code");
			}

			if (shapeId.startsWith("BU")) {
				// Buurt bounding box
				params.getProperty("Table").setValue("cbs_buurt");
				params.getProperty("ShapeIdKey").setValue("bu_code");
			}
			
			NavajoClientFactory.getClient().doSimpleSend(params.getRootDoc(), "geospatial/ProcessUpdateCBSBoundingBox");

		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}


	public static void main(String[] args) {
		RDShapeImport rdsi = new RDShapeImport("c:/cbs/brt_2010_gn1");
		rdsi.parseData(true);
	}

}
