package com.dexels.navajo.geo.zipcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Converts 4-digit zipcodes to gemeente nr.
 * @author Frank Lyaruu
 *
 */
public class Postcode2Gemeente {

	private static Map<String,String> zipMap = new HashMap<String,String>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(Postcode2Gemeente.class);
	
	/**
	 * @param args
	 */
	
	public static void init() {
		String line;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(Postcode2Gemeente.class.getResourceAsStream("postcode.csv")));
				line = br.readLine();
				while(line!=null) {
					processLine(line);
					line = br.readLine();
				}
				br.close();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}
	
	private static void processLine(String line) {
		//"5392 Maasdonk";"795";"1671";"Maasdonk"
		StringTokenizer st = new StringTokenizer(line,";");
		String name = st.nextToken();
		st.nextToken();
		String code = st.nextToken();
		st.nextToken();
		name = name.substring(1,5);
		code = code.substring(1,code.length()-1);
		zipMap.put(name, code);
		
	}
	
	public static String getGemeente(String postcode) {
		return zipMap.get(postcode);
	}
	
	public static void main(String[] args) {
		init();
	}

}
