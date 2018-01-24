package com.dexels.navajo.tipi.swing.geo.impl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.jxmapviewer.JXMapKit;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import com.dexels.navajo.tipi.swing.geo.impl.tilefactory.GoogleTileFactoryInfo;
import com.dexels.navajo.tipi.swing.geo.impl.tilefactory.OpenStreetMapTileFactoryInfo;

public class TipiSwingMapImpl extends JXMapKit {
	
	private static final long serialVersionUID = 3381042625940446945L;
	protected GeoPosition myCenter = null;
	protected boolean allowZoom = true;
	protected int myZoom = 0;
	protected int myMaxZoom = 10;
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
			@Override
			public void propertyChange(PropertyChangeEvent p) {
				if(p.getPropertyName().equals("zoom")) {
					Integer newZoom = (Integer) p.getNewValue();
					if(allowEvents) {
						myZoom = newZoom;
					}
//					myZoom = newZoom;
					
				}
				if(p.getPropertyName().equals("maxZoom")) {
					Integer newMaxZoom = (Integer) p.getNewValue();
					if(allowEvents) {
						myMaxZoom = newMaxZoom;
					}
				}
				if (p.getPropertyName().equals("allowZoom")) {
					boolean newAllowZoom = (Boolean) p.getNewValue();
					if (allowEvents) {
						allowZoom = newAllowZoom;
					}
				}
				if(p.getPropertyName().equals("centerPosition")) {
//					myCenter = (GeoPosition) p.getNewValue();
//					logger.info("My center: "+myCenter);
					if(allowEvents) {
						myCenter = (GeoPosition) p.getNewValue();
						double newLat = ((GeoPosition) p.getNewValue()).getLatitude();
						double oldLat = ((GeoPosition) p.getOldValue()).getLatitude();
						if(Math.abs(newLat-oldLat)>0.01) {
							firePropertyChange("lat", oldLat, newLat);
						}
						double oldLon = ((GeoPosition) p.getOldValue()).getLongitude();
						double newLon = ((GeoPosition) p.getNewValue()).getLongitude();
						if(Math.abs(oldLon-newLon)>0.01) {
							firePropertyChange("lon", oldLon, newLon);
						}
					}
				}
			}});
	}

	public void setZoomExternal(int z) {
		myZoom = z;
		// Prevent zooming when a limit has been set
		if (myZoom > myMaxZoom) {
			myZoom = myMaxZoom;
		}
		super.setZoom(z);
	}
	public void setMaxZoomExternal(int z) {
		myMaxZoom = z;
	}

	public void setAllowZoom(boolean z) {
		allowZoom = z;
		super.setZoomButtonsVisible(z);
		super.setZoomSliderVisible(z);
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
			setAllowZoom(allowZoom);
			if(myCenter!=null) {
				setCenterPosition(myCenter);
				setZoomExternal(myZoom);
			}
			allowEvents = true;
			return;
		} else if(factory.equals("openstreetmap")) {
			setTileFactory(new DefaultTileFactory(new OpenStreetMapTileFactoryInfo(19)));
			// zoom and center is lost after switching:
			setAllowZoom(allowZoom);
			if(myCenter!=null) {
				setCenterPosition(myCenter);
				setZoomExternal(myZoom);
			}
			allowEvents = true;
			return;
		}
		throw new IllegalArgumentException("Can not set mapfactory: "+factory+" use 'google' or 'openstreetmap'");
		
	}
}
