package com.dexels.navajo.tipi.components.rich;

import java.util.HashMap;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.rich.components.LocationListener;
import com.dexels.navajo.rich.components.MapPanel;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;

public class TipiMapHollandPanel extends TipiPanel implements LocationListener {
	MapPanel myContainer;
	@Override
	public Object createContainer() {
		myContainer = new MapPanel();
		myContainer.addLocationListener(this);
		return myContainer;
	}

	@Override
	public void loadData(Navajo n, String method) throws TipiException {
		myContainer.setMessage(n.getMessage("Clubs"));		
	}

	public void locationRequested(String locationId, String description, String union, double lat, double lon) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("clubid", locationId);
		params.put("clubname", description);
		params.put("union", union);
		params.put("lat", lat);
		params.put("lon", lon);
		try{
			performTipiEvent("onLocation", params, false);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
