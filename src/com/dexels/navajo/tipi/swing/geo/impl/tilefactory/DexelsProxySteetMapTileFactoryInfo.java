package com.dexels.navajo.tipi.swing.geo.impl.tilefactory;

import java.net.*;

import org.dexels.utils.*;
import org.jdesktop.swingx.mapviewer.*;

public class DexelsProxySteetMapTileFactoryInfo extends TileFactoryInfo {
	final int max ;
	public DexelsProxySteetMapTileFactoryInfo(int maxZoom) {
		super(0,maxZoom,maxZoom,
		        256, true, true,
		        "http://distel:8080/MapTileProxy/Tile",
		        "x","y","z");
		max = maxZoom;
		setDefaultZoomLevel(1);
	}
    public String getTileUrl(int x, int y, int zoom) {
        zoom = max-zoom;
        String tileString = "http://tile.openstreetmap.org" +"/"+zoom+"/"+x+"/"+y+".png";
        String res = Base64.encode(tileString.getBytes());
        String resss = URLEncoder.encode(tileString);
        System.err.println("AAP: "+resss);
        System.err.println("RES: "+res.replaceAll("=", "|"));
        return "http://distel:8080/MapTileProxy/Tile?tile="+resss;
//        return tileString;
    }
}

