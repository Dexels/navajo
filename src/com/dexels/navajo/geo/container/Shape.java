package com.dexels.navajo.geo.container;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;

public class Shape {
	
	private final static Logger logger = LoggerFactory.getLogger(Shape.class);
	
	ArrayList<Polygon> polygons = new ArrayList<Polygon>();	
	Binary data;
	String id;
	String coordinates;
	
	public Shape(Binary b){
		this.data = b;
		createShape();
	}
	
	private void createShape(){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(data.getDataAsStream()));
			String line = "";
			
			Polygon currentPolygon = null;
			while((line = br.readLine()) != null){
				if(line.indexOf("POLYGON") > -1){
					currentPolygon = new Polygon();
					polygons.add(currentPolygon);					
				}
				if(line.indexOf("SHAPE") < 0 && line.indexOf("POLYGON") < 0  && currentPolygon != null){
					currentPolygon.appendCoordinates(line + "\n");
				}
				if(line.indexOf("SHAPE") > 0){
					id = line.substring(line.indexOf("SHAPE") + 7);
				}
			}			
			br.close();
			
		}catch(Exception e){
			logger.error("Error: ", e);
		}
	}
	
	public int getPolygonCount(){
		return polygons.size();
	}
	
	public String getCoordinatesForPolygon(int polygon){
		return polygons.get(polygon).getCoordinates();
	}
	
	public String getCoordinates(){
		return getCoordinatesForPolygon(0);
	}
	
	public ArrayList<Polygon> getPolygons(){
		return polygons;
	}
		
	
}
