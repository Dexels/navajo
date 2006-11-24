package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiLabelManager;

public class BaseLabelManager implements TipiLabelManager {

	private final Map localizedMap = new HashMap();
	private String applicationId = null;
	private String instanceId = null;
	
	public void setApplicationId(String id) {
	}

	public void setInstanceId(String id) {

	}

	public String getLabel(String locale, String id) throws TipiException {

		return null;
	}

}
