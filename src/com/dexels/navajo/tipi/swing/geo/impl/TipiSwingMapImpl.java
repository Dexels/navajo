package com.dexels.navajo.tipi.swing.geo.impl;

import java.awt.*;
import java.beans.*;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import com.dexels.navajo.tipi.swing.geo.*;
import com.dexels.navajo.tipi.swing.geo.impl.tilefactory.*;

public class TipiSwingMapImpl extends JXMapKit {
	
	protected GeoPosition myCenter = null;
	protected int myZoom = 0;
	// need this ugly construction to prevent the change in factory to reset the location + zoomlevel.
	private boolean allowEvents = true;
	public void setLat(double lat) {
		if(myCenter==null) {
			myCenter = getCenterPosition();
		}
		double lon = myCenter.getLongitude();
		myCenter = new GeoPosition(lat,lon);
		setCenterPosition(myCenter);
		getMainMap().addPropertyChangeListener(new PropertyChangeListener(){
			public void propertyChange(PropertyChangeEvent p) {
				if(p.getPropertyName().equals("zoom")) {
					Integer newZoom = (Integer) p.getNewValue();
					if(allowEvents) {
						myZoom = newZoom;
					}
//					myZoom = newZoom;
					
				}
				if(p.getPropertyName().equals("centerPosition")) {
//					myCenter = (GeoPosition) p.getNewValue();
//					System.err.println("My center: "+myCenter);
					if(allowEvents) {
						myCenter = (GeoPosition) p.getNewValue();
					}
				}
			}});
	}

	public void setZoomExternal(int z) {
		myZoom = z;
		super.setZoom(z);
	}
	
	public void setLon(double lon) {
		if(myCenter==null) {
			myCenter = getCenterPosition();
		}
		double lat = myCenter.getLatitude();
    	myCenter = new GeoPosition(lat,lon);
		setCenterPosition(myCenter);
	} 

	public void setMapFactory(String factory) {
		allowEvents = false;
		if(factory.equals("google")) {
			// zoom and center is lost after switching:
			setTileFactory(new DefaultTileFactory(new GoogleTileFactoryInfo(0, 15, 17, 256,  true,true,false)));
			if(myCenter!=null) {
				setCenterPosition(myCenter);
				setZoomExternal(myZoom);
			}
		}
		if(factory.equals("openstreetmap")) {
			setTileFactory(new DefaultTileFactory(new DexelsProxySteetMapTileFactoryInfo(17)));
			// zoom and center is lost after switching:
			if(myCenter!=null) {
				setCenterPosition(myCenter);
				setZoomExternal(myZoom);
			}
		}
		allowEvents = true;
	}
}
