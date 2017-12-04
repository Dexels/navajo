package com.dexels.navajo.geo.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tools {

	public Tools(){
		
	}
	
	private static final double EARTHRADIUS = 6365;
	
	private final static Logger logger = LoggerFactory.getLogger(Tools.class);

	
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = EARTHRADIUS * c;

	    return dist;
	 }
	
	
	/**
	 * Returns the destination point from this point having travelled the given distance (in km) on the 
	 * given initial bearing (bearing may vary before destination is reached)
	 *
	 *   see http://williams.best.vwh.net/avform.htm#LL
	 *
	 * @param   {Number} brng: Initial bearing in degrees
	 * @param   {Number} dist: Distance in km
	 * @returns {LatLon} Destination point
	 */
	public static  double[] go(double lat, double lon, double dist, double brng) {
	  dist = dist/EARTHRADIUS;  // convert dist to angular distance in radians
	  brng = Math.toRadians(brng);  // 
	  double lat1 = Math.toRadians(lat);  // 
	  double lon1 = Math.toRadians(lon);  // 
	  

	  double lat2 = Math.asin( Math.sin(lat1)*Math.cos(dist) + 
	                        Math.cos(lat1)*Math.sin(dist)*Math.cos(brng) );
	  double lon2 = lon1 + Math.atan2(Math.sin(brng)*Math.sin(dist)*Math.cos(lat1), 
	                               Math.cos(dist)-Math.sin(lat1)*Math.sin(lat2));
	  lon2 = (lon2+3*Math.PI)%(2*Math.PI) - Math.PI;  // normalise to -180...+180

	  return new double[]{Math.toDegrees(lat2), Math.toDegrees(lon2)};
	}

	public LatLon move(LatLon base, double dist, double brng) {
		brng = brng % 360.0;
		logger.info("Calculating: "+base.getCoordinates()+" distance: "+dist+" bearing: "+brng);
		double[] arrayPos = go(base.getLat(),base.getLon(),dist,brng);
		LatLon result = new LatLon(arrayPos);
		logger.info("Result: "+result.getCoordinates());
//		Thread.dumpStack();
		return result;
	}
	
}
