package com.crazysquirrel.util.bwu;

public class Test {

	public static void main(String [] args) {
		BandwidthUsageConfiguration configuration = new BandwidthUsageConfiguration();
		configuration.setInterfaceName( "eth0" );
		configuration.setDelay( 5000 );
		BandwidthUsage usage = new BandwidthUsage( configuration );
		while ( true ) {
			System.err.println(usage.getAllStatistics());
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
