package com.dexels.navajo.tipi;

import java.io.Serializable;

import com.dexels.navajo.tipi.tipixml.XMLElement;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public interface TipiDefinitionListener extends Serializable {
	public void definitionLoaded(String definitionName, XMLElement definition);

}
