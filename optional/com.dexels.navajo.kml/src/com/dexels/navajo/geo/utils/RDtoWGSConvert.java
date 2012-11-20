package com.dexels.navajo.geo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bbn.openmap.dataAccess.shape.DbfFile;
import com.bbn.openmap.io.BinaryFile;
import com.bbn.openmap.layer.shape.ESRIPoly;
import com.bbn.openmap.layer.shape.ESRIPoly.ESRIFloatPoly;
import com.bbn.openmap.layer.shape.ESRIPolygonRecord;
import com.bbn.openmap.layer.shape.ESRIRecord;
import com.bbn.openmap.layer.shape.ShapeFile;

public class RDtoWGSConvert {
	File input;
	
	private final static Logger logger = LoggerFactory
			.getLogger(RDtoWGSConvert.class);

	public RDtoWGSConvert(File input){
		this.input = input;
	}
	
	public void convert(){
		try{
			BufferedReader br = new BufferedReader(new FileReader(input));
			String line = "";
			StringBuffer target = new StringBuffer();
			
			int count = 1;
			while((line = br.readLine()) != null){				
				int offset = 0;
				int index = -1;
				while((index = line.indexOf("<coordinates>", offset)) > -1 ){
					System.err.println("Converting wijk: " + count);
					count++;
					int end = line.indexOf("</coordinates>", offset);
					String coordString = line.substring(index, end);
					target.append(line.substring(offset, index));					
					offset = line.indexOf("</coordinates>", offset) + 14;
					String transformed = parseCoords(coordString.substring(13, coordString.length()));
					target.append(transformed);					
				}
				target.append(line.substring(offset));	
			}				
			br.close();
			File newFile = new File("buurten.kml");
			BufferedWriter out = new BufferedWriter(new FileWriter(newFile));
			out.write(target.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			logger.error("Error: ", e);
		}
	}
	
	private String parseCoords(String coordString){
		//System.err.println("Parsing coords: " + coordString);
		
		StringTokenizer tok = new StringTokenizer(coordString, " " );
		String converted = "";
		
		
		while(tok.hasMoreTokens()){
			String coordinate = tok.nextToken();			
			String[] coords = coordinate.split(",");
			
						
			for(int i =0;i<coords.length;i+=3){
				String x = coords[i];
				String y = coords[i+1];
				String z = coords[i+2];
				
				double xd = Double.parseDouble(x);
				double yd = Double.parseDouble(y);
				
				double dX = (xd - 155000) / 100000.0;
				double dY = (yd - 463000) / 100000.0;
				
				double SomN = (3235.65389 * dY) + (-32.58297 * Math.pow(dX, 2.0)) + (-0.2475 * Math.pow(dY, 2.0)) + (-0.84978 * Math.pow(dX, 2.0) * dY) + (-0.0655 * Math.pow(dY, 3.0)) + (-0.01709 * Math.pow(dX, 2.0) * Math.pow(dY, 2.0)) + (-0.00738 * dX) + (0.0053 * Math.pow(dX, 4.0)) + (-0.00039 * Math.pow(dX, 2.0) * Math.pow(dY, 3.0)) + (0.00033 * Math.pow(dX, 4.0) * dY) + (-0.00012 * dX * dY);
				double SomE = (5260.52916 * dX) + (105.94684 * dX * dY) + (2.45656 * dX * Math.pow(dY, 2.0)) + (-0.81885 * Math.pow(dX, 3.0)) + (0.05594 * dX * Math.pow(dY, 3.0)) + (-0.05607 * Math.pow(dX, 3.0) * dY) + (0.01199 * dY) + (-0.00256 * Math.pow(dX, 3.0) * Math.pow(dY, 2.0)) + (0.00128 * dX * Math.pow(dY, 4.0)) + (0.00022 * Math.pow(dY, 2.0)) + (-0.00022 * Math.pow(dX, 2.0)) + (0.00026 * Math.pow(dX, 5.0));
				
				
				double lat = 52.15517440 + (SomN / 3600.0);
				double lon = 5.38720621 + (SomE / 3600.0);
				
				converted = converted + " " + lon + "," + lat + "," + z; 
				
			}
		}
		
		return "<coordinates>" + converted + "</coordinates>";
	}


	public static void main(String[] args){
//		File input = new File("brt_2010_gn1.kml");
//		RDtoWGSConvert conv = new RDtoWGSConvert(input);
//		conv.convert();
		
		try{
			File shapeFile = new File("c:/cbs/gem_2010_gn1.shp");
			DbfFile db = new DbfFile(new BinaryFile(new File("c:/cbs/gem_2010_gn1.dbf")));
			db.readData();
			ShapeFile sf = new ShapeFile(shapeFile);
			ESRIRecord record = null;
			int count = 0;
			
			System.err.println("Rows in db: " + db.getRowCount());
			
			while((record = sf.getNextRecord()) != null){				
				Object val = db.getValueAt(count, 0);
				//System.err.println("Record: " + count + ", value: " + val.toString());
				count++;
				
				String id = val.toString();
				
				
				if(record instanceof ESRIPolygonRecord){
					ESRIPolygonRecord polygon = (ESRIPolygonRecord) record;
					
					ESRIPoly[] pols = polygon.polygons;
					
					System.err.println(id + ": " + pols.length + " polygons");
					
					for(int i=0;i<pols.length;i++){
						ESRIPoly poly = pols[i];
						ESRIFloatPoly fp = (ESRIFloatPoly)poly;
						for(int j=0;j<fp.nPoints;j++){
						}
					}
				
				}
				
			}
			
		}catch(Exception e){
			logger.error("Error: ", e);
		}
	}
}

