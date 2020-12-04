/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
