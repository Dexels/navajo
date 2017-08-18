package com.dexels.navajo.tipi.swing.geo.impl.tilefactory;

import org.jxmapviewer.viewer.TileFactoryInfo;

public class OpenStreetMapTileFactoryInfo extends TileFactoryInfo {
    final int max;

    public OpenStreetMapTileFactoryInfo(int maxZoom) {
        super(0, maxZoom, maxZoom, 256, true, true, "http://tile.openstreetmap.org", "x", "y", "z");
        max = maxZoom;
        setDefaultZoomLevel(1);
    }

    @Override
    public String getTileUrl(int x, int y, int zoom) {
        zoom = max - zoom;
        return this.baseURL + "/" + zoom + "/" + x + "/" + y + ".png";
    }
}
