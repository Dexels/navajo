/*
 * BandwidthUsageRunner.java
 *
 * Created on February 3, 2006, 6:12 PM
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.util.AuditLog;

/**
 *
 * @author doozer
 */
public class BandwidthUsageRunner extends GenericThread {
	
	//Logger log = Logger.getLogger( "com.crazysquirrel.utils.bwu" );
	
	private BandwidthUsageConfiguration configuration;
	private InterfaceStatistics previous;
	private InterfaceStatistics current;
	
	
	/** Creates a new instance of BandwidthUsageRunner */
	public BandwidthUsageRunner( BandwidthUsageConfiguration configuration ) {
		setConfiguration( configuration );
	}
	
	public void worker() {
		try {
			BufferedReader in = new BufferedReader( new FileReader( new File( "/proc/net/dev" ) ) );
			String read = null;
			in.readLine(); //skip the first header line
			in.readLine(); //skip the second header line
			while( null != ( read = in.readLine() ) ) {
				read = read.trim();
				InterfaceStatistics is = new InterfaceStatistics( read );
				if( configuration.getInterfaceName().equals( is.getName() ) ) { 
					setPrevious(getCurrent());
					setCurrent(is);
				}
			}
		} catch( IOException ioe ) {
			AuditLog.log( "BANDWIDTHMONITOR: Problem reading statistics file", ioe+"", Level.INFO );
		}
	}

	public BandwidthUsageConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(BandwidthUsageConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public long getBandwidthReceviedUsage() {
		if( getPrevious() == null || getCurrent() == null ) {
			return 0;
		}
		
		return ( getCurrent().getBytesReceived() - getPrevious().getBytesReceived() ) / ( ( getCurrent().getSampleTime() - getPrevious().getSampleTime() ) / 1000 );
	}
	
	public long getBandwidthTransmittedUsage() {
		if( getPrevious() == null || getCurrent() == null ) {
			return 0;
		}
		
		return ( getCurrent().getBytesTransmitted() - getPrevious().getBytesTransmitted() ) / ( ( getCurrent().getSampleTime() - getPrevious().getSampleTime() ) / 1000 );
	}

	public InterfaceStatistics getCurrent() {
		return current;
	}

	public void setCurrent(InterfaceStatistics current) {
		this.current = current;
	}

	public InterfaceStatistics getPrevious() {
		return previous;
	}

	public void setPrevious(InterfaceStatistics previous) {
		this.previous = previous;
	}
}
