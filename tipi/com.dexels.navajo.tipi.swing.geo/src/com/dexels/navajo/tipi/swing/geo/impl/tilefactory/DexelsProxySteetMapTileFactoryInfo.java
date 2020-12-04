/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swing.geo.impl.tilefactory;

import org.jxmapviewer.viewer.TileFactoryInfo;

public class DexelsProxySteetMapTileFactoryInfo extends TileFactoryInfo {
	final int max ;
	public DexelsProxySteetMapTileFactoryInfo(int maxZoom) {
		super(5,maxZoom,maxZoom,
		        256, true, true,
		        "http://distel:8080/MapTileProxy/Tile",
		        "x","y","z");
		max = maxZoom;
		setDefaultZoomLevel(1);
	}
    @Override
	public String getTileUrl(int x, int y, int zoom) {
//        zoom = max-zoom;
//        String tileString = "http://tile.openstreetmap.org" +"/"+zoom+"/"+x+"/"+y+".png";
//        String res = Base64.encode(tileString.getBytes());
//        String resss = URLEncoder.encode(tileString);
//        logger.info("AAP: "+resss);
//        logger.info("RES: "+res.replaceAll("=", "|"));
//        return "http://distel:8080/MapTileProxy/Tile?tile="+resss;
        return null;
    }
}

