package com.dexels.navajo.mapping.wrapper;


import com.dexels.navajo.mapping.base.MappableTreeNodeInterface;
import com.dexels.navajo.script.api.UserException;

public interface MappingUtilInterface {
	  public Object getAttributeValue(MappableTreeNodeInterface o, String name, Object[] arguments) throws UserException;

}
