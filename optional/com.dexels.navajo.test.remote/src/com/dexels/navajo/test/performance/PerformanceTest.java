package com.dexels.navajo.test.performance;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Navajo;

public class PerformanceTest {

	private ClientInterface client;
	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rnd = new Random();

	public PerformanceTest() {
	}

	@Before
	public void setup() {
		this.client = NavajoClientFactory.getClient();
		this.client.setUsername("username");
		this.client.setPassword("password");
		this.client.setServers(new String[]{"test.ortlink.com/navajo"});
	}
	
	@Test
	public void testSearchClub() throws ClientException {
		int count = 5;
		long start = System.currentTimeMillis();
		for (int i=0; i<count;i++) {
			Navajo init = client.doSimpleSend("club/InitSearchClub");
			init.getProperty("ClubSearch/ClubName").setAnyValue(randomString(3));
			Navajo process = client.doSimpleSend(init, "club/ProcessSearchClub");
		}
		long after = System.currentTimeMillis();
		System.err.println("elapsed: "+(after-start));
	}
	

	private String randomString( int len ) 
	{
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
}
