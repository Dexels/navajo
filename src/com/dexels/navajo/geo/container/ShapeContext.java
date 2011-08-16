package com.dexels.navajo.geo.container;

import com.dexels.navajo.document.types.Binary;

public class ShapeContext {
	Shape s;
	Binary b;
	
	public ShapeContext(){
		
	}
	
	public void setBinary(Binary b){
		this.b = b;
		s = new Shape(b);
	}
	
	public Shape getShape(){
		return s;
	}
	
	
}
