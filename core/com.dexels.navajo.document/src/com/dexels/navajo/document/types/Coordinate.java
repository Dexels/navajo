package com.dexels.navajo.document.types;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Coordinate extends NavajoType implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3899354902954075174L;
    private final static Logger logger = LoggerFactory.getLogger(Coordinate.class);
    public final static String VERSION = "$Id$";

    private Double latitude = new Double(0);
    private Double longitude = new Double(0);

    public Coordinate(double longitude, double latitude) throws Exception {
        this.latitude = latitude;
        this.longitude = longitude;
        verifyCoordinates();
    }

    public Coordinate(String arrStr) throws Exception {
        // check if format is correct.
        if (!arrStr.matches("\\[[+-]{0,1}\\d+\\.{0,1}\\d*,[+-]{0,1}\\d+\\.{0,1}\\d*\\]")) {
            logger.error("Not valid format given.");
            throw new Exception("Not valid format given :: " + arrStr + ". Please use number representations");
        } else {
            String mydata = arrStr.substring(1, arrStr.length() - 1);
            String[] vals = mydata.split(",");

            this.latitude = Double.parseDouble(vals[1]);
            this.longitude = Double.parseDouble(vals[0]);
            verifyCoordinates();
        }

    }

    private void verifyCoordinates() throws Exception {
        if (this.latitude > 90 || this.latitude < -90) {
            throw new Exception("Latitude must be in [-90,90]");
        }
        if (this.longitude > 180 || this.longitude < -180) {
            throw new Exception("Longitute must be in [-180,180]");
        }
    }

    public Coordinate(Object longitude, Object latitude) throws Exception {
        setLatitude(longitude);
        setLongitude(longitude);
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLatitude(Object latitude) throws Exception {
        if (latitude instanceof String) {
            this.latitude = Double.parseDouble((String) latitude);
        } else if (latitude instanceof Double) {
            this.latitude = (Double) latitude;
        } else if (latitude instanceof Integer) {
            this.latitude = new Double(((Integer) latitude).intValue());
        } else {
            this.latitude = new Double(latitude + "");
        }
        verifyCoordinates();

    }

    public void setLongitude(Object longitude) throws Exception {
        if (longitude instanceof String) {
            this.longitude = Double.parseDouble((String) longitude);
        } else if (longitude instanceof Double) {
            this.longitude = (Double) longitude;
        } else if (longitude instanceof Integer) {
            this.longitude = new Double(((Integer) longitude).intValue());
        } else {
            this.longitude = new Double(longitude + "");
        }
        verifyCoordinates();
    }

    @Override
    public boolean isEmpty() {

        return false;
    }

    @Override
    public String toString() {
        return "[" + this.longitude + "," + this.latitude + "]";
    }

    // public String toString() {
    // return "{\"latitude\": " + this.latitude + ", \"longitude\": " +
    // this.longitude + "}";
    // }

    // public String toString() {
    // return "{coordinate : {latitude:" + this.latitude + ", longitude:" +
    // this.longitude + "}}";
    // }

    public static void main(String[] args) {
        
        String arrStr = "[1.23,-1]";
        System.out.println(arrStr.matches("\\[[+-]{0,1}\\d+\\.{0,1}\\d*,[+-]{0,1}\\d+\\.{0,1}\\d*\\]"));

        // arrStr = "11.12";
        // System.out.println(arrStr.matches("\\d+.*\\d+"));
        
        // Coordinate test = new Coordinate("[1,1]");

        // Coordinate test = new Coordinate("123.33", 11);
        // System.out.println(test.toString());
        //
        // String mydata = test.toString();
        //
        //
        // mydata.replace("[", "");
        // mydata.replace("]", "");
        // String[] vals = mydata.split(",");
        // System.out.println(vals[0] + " " + vals[1]);

    }

}
