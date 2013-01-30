package com.dexels.navajo.geo.tools;

public class Tools {

	public Tools(){
		
	}
	
	private static final double EARTHRADIUS = 6365;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// nijenrodeweg
//		52.327340
//		 4.857465
	
//		hilton:
	//		 52.351484
//			  4.872136
//		Tools t = new Tools();
		
		System.err.println("Dist = "+distFrom(52.327340, 4.857465, 52.351484, 4.872136));
//		double[] res = move(52.327340, 4.857465, 2.68, 20);
//		System.err.println("lat: "+res[0]+" lon: "+res[1]);
		double[] res = go(52.327340, 4.857465, 2.87, 21);
		System.err.println("aalat: "+res[0]+" lon: "+res[1]);
	}
	
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

//	  if (Math. isNaN(lat2) || isNaN(lon2)) return null;
	  //System.err.println(">>>>>Lat: "+Math.toDegrees(lat2)+" lon: "+Math.toDegrees(lon2) );
	  return new double[]{Math.toDegrees(lat2), Math.toDegrees(lon2)};
	}

	public LatLon move(LatLon base, double dist, double brng) {
		brng = brng % 360.0;
		System.err.println("Calculating: "+base.getCoordinates()+" distance: "+dist+" bearing: "+brng);
		double[] arrayPos = go(base.getLat(),base.getLon(),dist,brng);
		//System.err.println(">>>>>Lat: "+arrayPos[0]+" lon: "+arrayPos[1] );		
		LatLon result = new LatLon(arrayPos);
		System.err.println("Result: "+result.getCoordinates());
//		Thread.dumpStack();
		return result;
	}
	
//	public double[] placeObject(LatLon, double distance, int max, int index) {
//		double fraction = 360 / max;
//		double brng = index * fraction;
//		double[] pos = go(lat,lon,distance,brng);
//		return new double[]{pos[0],pos[1],brng};
//	}
	
}
