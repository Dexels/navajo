/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.NavajoException;

public class Coordinate extends NavajoType implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3899354902954075174L;

    public static final Coordinate nowhere = new Coordinate(Double.NaN,Double.NaN);
    private Double latitude = null;
    private Double longitude = null;

    public Coordinate(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        verifyCoordinates();
    }

    public Coordinate(String arrStr) {
        // check if format is correct.
        if (!(arrStr.matches(
                "\\[[+-]{0,1}\\d+\\.{0,1}\\d*, [+-]{0,1}\\d+\\.{0,1}\\d*\\]|\\[[+-]{0,1}\\d+\\.{0,1}\\d*,[+-]{0,1}\\d+\\.{0,1}\\d*\\]|[+-]{0,1}\\d+\\.{0,1}\\d*,[+-]{0,1}\\d+\\.{0,1}\\d*|[+-]{0,1}\\d+\\.{0,1}\\d* [+-]{0,1}\\d+\\.{0,1}\\d*"))) { // -18
            throw new NavajoException("Not valid format given :: " + arrStr + ". Please use '[x,y]', 'x,y' or 'x y'");
        } else {
            String mydata = arrStr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(", ", ",");
            String[] vals = mydata.split(",| ");

            this.latitude = Double.parseDouble(vals[1]);
            this.longitude = Double.parseDouble(vals[0]);
            verifyCoordinates();
        }

    }

    private void verifyCoordinates() {
        if (this.latitude != null && (this.latitude > 90 || this.latitude < -90)) {
            throw new NavajoException("Latitude must be in [-90,90]");
        }
        if (this.longitude != null && (this.longitude > 180 || this.longitude < -180)) {
            throw new NavajoException("Longitute must be in [-180,180]");
        }
    }

    public Coordinate(Object longitude, Object latitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLatitude(Object latitude) {
        if (latitude instanceof String) {
            this.latitude = Double.parseDouble((String) latitude);
        } else if (latitude instanceof Double) {
            this.latitude = (Double) latitude;
        } else if (latitude instanceof Integer) {
            this.latitude = Double.valueOf(((Integer) latitude).intValue() + "");
        } else {
            this.latitude = Double.valueOf(latitude + "");
        }
        verifyCoordinates();

    }

    public void setLongitude(Object longitude) {
        if (longitude instanceof String) {
            this.longitude = Double.parseDouble((String) longitude);
        } else if (longitude instanceof Double) {
            this.longitude = (Double) longitude;
        } else if (longitude instanceof Integer) {
            this.longitude = Double.valueOf(((Integer) longitude).intValue() + "");
        } else {
            this.longitude = Double.valueOf(longitude + "");
        }
        verifyCoordinates();
    }

    public List<Double> getCoordinatesAsList() {
        ArrayList<Double> arr = new ArrayList<>();
        arr.add(this.getLongitude());
        arr.add(this.getLatitude());
        return arr;
    }

    @Override
    public boolean isEmpty() {
        return this.latitude == null || this.longitude == null;
    }

    @Override
    public String toString() {
    	if(this.longitude==Double.NaN || this.latitude==Double.NaN) {
    		return "[NullCoordinate]";
    	}
        return "[" + this.longitude + "," + this.latitude + "]";
    }

}
