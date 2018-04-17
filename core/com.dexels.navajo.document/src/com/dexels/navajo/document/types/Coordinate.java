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

    private Double latitude;
    private Double longitude;

    public Coordinate(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinate(String arrStr) {

        // check if format is correct.
        System.out.println(arrStr.matches("(*)"));
        if (!arrStr.matches("\\[[+-]{0,1}\\d+.*\\d+,[+-]{0,1}\\d+.*\\d+\\]")) {
            logger.error("Not valid format given.");
        } else {
            String mydata = arrStr.substring(1, arrStr.length() - 1);
            String[] vals = mydata.split(",");

            this.latitude = Double.parseDouble(vals[0]);
            this.longitude = Double.parseDouble(vals[1]);
        }

        // String mydata = arrStr.substring(1, arrStr.length() - 1);
        // String[] vals = mydata.split(",");
        // System.out.println(vals[0] + " " + vals[1]);
        //
        //
        // this.latitude = latitude;
        // this.longitude = longitude;
    }

    public Coordinate(Object longitude, Object latitude) {

        if (latitude instanceof String) {
            this.latitude = Double.parseDouble((String) latitude);
        } else if (latitude instanceof Double) {
            this.latitude = (Double) latitude;
        } else if (latitude instanceof Integer) {
            this.latitude = new Double(((Integer) latitude).intValue());
        } else {
            this.latitude = new Double(latitude + "");
        }

        if (longitude instanceof String) {
            this.longitude = Double.parseDouble((String) longitude);
        } else if (longitude instanceof Double) {
            this.longitude = (Double) longitude;
        } else if (longitude instanceof Integer) {
            this.longitude = new Double(((Integer) longitude).intValue());
        } else {
            this.longitude = new Double(longitude + "");
        }

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
            this.latitude = new Double(((Integer) latitude).intValue());
        } else {
            this.latitude = new Double(latitude + "");
        }
    }

    public void setLongitude(Object longitude) {
        if (longitude instanceof String) {
            this.longitude = Double.parseDouble((String) longitude);
        } else if (longitude instanceof Double) {
            this.longitude = (Double) longitude;
        } else if (longitude instanceof Integer) {
            this.longitude = new Double(((Integer) longitude).intValue());
        } else {
            this.longitude = new Double(longitude + "");
        }
    }

    @Override
    public boolean isEmpty() {

        return false;
    }

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
        
        String arrStr = "[1,-1]";
        System.out.println(arrStr.matches("\\[[+-]{0,1}\\d+.*\\d*,[+-]{0,1}\\d+.*\\d*\\]"));

        // arrStr = "11.12";
        // System.out.println(arrStr.matches("\\d+.*\\d+"));
        
        // Coordinate test = new Coordinate("[123.12,13]");

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
