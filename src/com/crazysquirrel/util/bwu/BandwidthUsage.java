/*
 * A very simple bandwidth monitoring program that is probably
 * massivly over engineered for what it does. 
 *
 * Created on February 2, 2006, 9:53 PM By Graham Smith
 * Copyright (C) 2006 Graham Smith
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.crazysquirrel.util.bwu;

import com.crazysquirrel.util.TidyBits;
import com.crazysquirrel.util.TidyBytes;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * This application monitors a Linux systes /proc/net/dev file and builds statistics for
 * bandwidth usage. The /proc/net/dev file contains a number of space seperated
 * columns that contain different information about a given interface. Many of the
 * columns generally contain 0 in most cases. An example is shown below but might have
 * been mangled by the JavaDoc process. If it has it is a simple matter to grab your own sample
 * from the file on your Linux machine (you are using Linux right?).
 *
 * Inter-|   Receive                                                |  Transmit
 * face |bytes    packets errs drop fifo frame compressed multicast|bytes    packets errs drop fifo colls carrier compressed
 * lo: 4400318    6769    0    0    0     0          0         0  4400318    6769    0    0    0     0       0          0
 * eth0:6849280224 5971997    0    0    0     0          0         0 1183784068 2317848    0    0    0     0       0          0
 * eth1:       0       0    0    0    0     0          0         0        0       0    0    0    0     0       0          0
 * sit0:       0       0    0    0    0     0          0         0        0       0    0    0    0     0       0          0
 *
 * @author doozer
 */
public class BandwidthUsage {
	
	private Logger log = Logger.getLogger( "com.crazysquirrel.utils.bwu" );
	private BandwidthUsageRunner runner;
	private long maxReceived;
	private long maxTransmitted;
	private Date started;
	
	/** Creates a new instance of BandwidthUsage */
	public BandwidthUsage( BandwidthUsageConfiguration configuration ) {
		Timer timer = new Timer( "bandwidth-usage", true );
		runner = new BandwidthUsageRunner( configuration );
		runner.myId = "Bandwidth Monitor Thread";
		runner.setSleepTime((int) configuration.getDelay());
		setStarted( new Date() );
	}
	
	public static void main( String[] args ) {
		BandwidthUsageConfiguration configuration = new BandwidthUsageConfiguration();
		configuration.setInterfaceName( "eth0" );
		configuration.setDelay( 5000 );
		
		final BandwidthUsage usage = new BandwidthUsage( configuration );
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate( new TimerTask() {
			public void run() {
				System.out.println( usage.getAllStatistics() );
				System.out.println( "" );
			}
		}, 0, configuration.getDelay() );
		
	}
	
	/**
	 * Get bandwidth of specific measure:
	 * 
	 * @param what, can be either: received, transmitted, maxReceived, maxTransmitted
	 * @return the bandwidth value of the specified measure
	 */
	public String getBandwidth(String what, long SI) {
		long received = runner.getBandwidthReceviedUsage() * 8;
		if( received > maxReceived ) {
			maxReceived = received;
		}
		
		long transmitted = runner.getBandwidthTransmittedUsage() * 8;
		if( transmitted > maxTransmitted ) {
			maxTransmitted = transmitted;
		}
		
		if ( what.equalsIgnoreCase("received") ) {
			return TidyBits.getAppropriateSI( received, SI );
		} else if ( what.equalsIgnoreCase("transmitted")) { 
			return TidyBits.getAppropriateSI( transmitted, SI );
		} else if ( what.equalsIgnoreCase("maxReceived")) { 
			return TidyBits.getAppropriateSI( maxReceived, SI );
		} else if ( what.equalsIgnoreCase("maxtransmitted")) { 
			return TidyBits.getAppropriateSI( maxTransmitted, SI );
		} else {
			return "";
		}
		
	}
	
	public String getBandwidthUsage() {
		long received = runner.getBandwidthReceviedUsage() * 8;
		if( received > maxReceived ) {
			maxReceived = received;
		}
		
		long transmitted = runner.getBandwidthTransmittedUsage() * 8;
		if( transmitted > maxTransmitted ) {
			maxTransmitted = transmitted;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append( "Current bandwidth utilization is: " );
		builder.append( TidyBits.getAppropriateSI( received ) );
		builder.append( "/sec in, " );
		builder.append( TidyBits.getAppropriateSI( transmitted ) );
		builder.append( "/sec out, " );
		builder.append( TidyBits.getAppropriateSI( received + transmitted ) );
		builder.append( "/sec total." );
		return builder.toString();
	}
	
	public String getMaxBandwidthUsage() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Maximum bandwidth utilization is: " );
		builder.append( TidyBits.getAppropriateSI( maxReceived ) );
		builder.append( "/sec in, " );
		builder.append( TidyBits.getAppropriateSI( maxTransmitted ) );
		builder.append( "/sec out." );
		return builder.toString();
	}
	
	public String getTotalBandwidth() {
		if( runner.getCurrent() == null ) {
			return "Waiting for first data collection...";
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append( "Total data transferred: " );
		builder.append( TidyBytes.getAppropriateSI( runner.getCurrent().getBytesReceived() ) );
		builder.append( " in, " );
		builder.append( TidyBytes.getAppropriateSI( runner.getCurrent().getBytesTransmitted() ) );
		builder.append( " out, " );
		builder.append( TidyBytes.getAppropriateSI( runner.getCurrent().getBytesReceived() + runner.getCurrent().getBytesTransmitted() ) );
		builder.append( " total." );
		return builder.toString();
	}

	public Date getStarted() {
		return started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}
	
	public String getAllStatistics() {
		StringBuilder builder = new StringBuilder();
		builder.append( getBandwidthUsage() + "\n" );
		builder.append( getMaxBandwidthUsage() + "\n" );
		builder.append( getTotalBandwidth() + "\n" );
		builder.append( "Data collection started: " + getStarted().toString() + "\n" );
		builder.append( "Current system time: " + new Date().toString() );
		return builder.toString();
	}

	public BandwidthUsageRunner getRunner() {
		return runner;
	}
}
