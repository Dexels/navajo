package com.dexels.navajo.document;

public interface Navascript extends Navajo {

	public Param addParam(Param p);
	
	public MapAdapter addMap(MapAdapter map);
	
	public void addInclude(Include inc);

	public void addValidations(Validations val);
	
}
