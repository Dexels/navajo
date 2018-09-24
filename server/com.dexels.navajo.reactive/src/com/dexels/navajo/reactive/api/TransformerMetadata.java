package com.dexels.navajo.reactive.api;

import java.util.Set;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;

public interface TransformerMetadata extends ParameterValidator {

	public Set<Type> inType();
//	public Type inferredType();

//	default public Type inferredType() {
//		return Type.ANY;
//	}
	public DataItem.Type outType();
	public String name();
	
	

}
