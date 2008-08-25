package com.dexels.navajo.monitor;

import java.util.HashMap;

import com.crazysquirrel.util.TidyBits;
import com.crazysquirrel.util.bwu.BandwidthUsage;
import com.crazysquirrel.util.bwu.BandwidthUsageConfiguration;

public class BandwidthMonitor {

	public String interfaceName;
	public String measure;
	public boolean removeMonitor;
	public BandwithValue bandwidth;
	
	public static HashMap<String,BandwidthUsage> monitors = new HashMap<String,BandwidthUsage>();
	
	/**
	 * Sets the interface that you want to monitor. If interface is not yet monitored start monitoring thread.
	 * 
	 * @param interfaceName
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
		if ( monitors.get(interfaceName) == null ) {
			synchronized (monitors) {
				BandwidthUsageConfiguration configuration = new BandwidthUsageConfiguration();
				configuration.setInterfaceName( interfaceName );
				configuration.setDelay( 5000 );
				BandwidthUsage usage = new BandwidthUsage( configuration );
				usage.getRunner().startThread(usage.getRunner());
				monitors.put(interfaceName, usage);
			}
		}
	}
	
	/**
	 * Removes the monitor of a specified interface.
	 * 
	 * @param removeMonitor
	 * @throws Exception
	 */
	public void setRemoveMonitor(boolean removeMonitor) throws Exception {
		this.removeMonitor = removeMonitor;
		synchronized (monitors) {
			if ( interfaceName != null ) {
				monitors.remove(this.interfaceName);
			} else {
				throw new Exception("Specify what interface to remove from monitors.");
			}
		}
	}
	
	/**
	 * Gets the bandwidth of a specified measure:
	 * - transmitted
	 * - received
	 * - maxtransmitted
	 * - maxreceived
	 * 
	 * @param what
	 * @return a BandwithValue object
	 * @throws Exception
	 */
	public BandwithValue getBandwidth(String what) throws Exception {
		if ( interfaceName == null ) {
			throw new Exception("Specify for which interface you want to read the bandwidth.");
		}
		BandwidthUsage usage = monitors.get(interfaceName);
		
		if ( what == null ) {
			throw new Exception("Specify what measure you want to monitor.");
		}
		
		if ( usage == null ) {
			throw new Exception("Unknown network interface: " + interfaceName);
		}
		
		String v = usage.getBandwidth(what, TidyBits.KILOBIT );
		String d = v.split(" ")[0];
		String m = v.split(" ")[1];
		
		return new BandwithValue(Double.parseDouble( d.replace(",", "") ), m, what);
	}
	
	public BandwithValue getBandwidth() throws Exception {
		return getBandwidth(measure);
	}
	
	public static void main(String [] args) throws Exception {
		while ( true ) {
			BandwidthMonitor bw = new BandwidthMonitor();
			bw.setInterfaceName("eth0");
			bw.setMeasure("transmitted");
			System.err.println(bw.getBandwidth());
			
			Thread.sleep(3000);
		}
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}
	
}
