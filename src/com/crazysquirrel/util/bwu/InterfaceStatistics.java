/*
 * InterfaceStatistics.java
 *
 * Created on February 2, 2006, 10:26 PM
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

/**
 *
 * @author doozer
 */
public class InterfaceStatistics {
	
	//The name of the interface
	private String name;
	
	//Received Statistics
	private long bytesReceived;
	private long packetsReceived;
	private long errsReceived;
	private long dropsReceived;
	private long fifoReceived;
	private long framesReceived;
	private long compressedReceived;
	private long multicastReceived;
	
	//Transmitted Statistics
	private long bytesTransmitted;
	private long packetsTransmitted;
	private long errsTransmitted;
	private long dropsTransmitted;
	private long fifoTransmitted;
	private long collisionsTransmitted;
	private long carriersTransmitted;
	private long compressedTransmitted;
	
	//Other Parameters
	private long sampleTime;
	
	/** Creates a new instance of InterfaceStatistics */
	public InterfaceStatistics( String statisticsLine ) {
		String[] parts = statisticsLine.split( "[\\s^:]+|:" );
		setName( parts[ 0 ] );
		
		//Set received statistics
		setBytesReceived( Long.parseLong( parts[ 1 ] ) );
		setPacketsReceived( Long.parseLong( parts[ 2 ] ) );
		setErrsReceived( Long.parseLong( parts[ 3 ] ) );
		setDropsReceived( Long.parseLong( parts[ 4 ] ) );
		setFifoReceived( Long.parseLong( parts[ 5 ] ) );
		setFramesReceived( Long.parseLong( parts[ 6 ] ) );
		setCompressedReceived( Long.parseLong( parts[ 7 ] ) );
		setMulticastReceived( Long.parseLong( parts[ 8 ] ) );
		
		//Set transmitted statistics
		setBytesTransmitted( Long.parseLong( parts[ 9 ] ) );
		setPacketsTransmitted( Long.parseLong( parts[ 10 ] ) );
		setErrsTransmitted( Long.parseLong( parts[ 11 ] ) );
		setDropsTransmitted( Long.parseLong( parts[ 12 ] ) );
		setFifoTransmitted( Long.parseLong( parts[ 13 ] ) );
		setCollisionsTransmitted( Long.parseLong( parts[ 14 ] ) );
		setCarriersTransmitted( Long.parseLong( parts[ 15 ] ) );
		setCompressedTransmitted( Long.parseLong( parts[ 16 ] ) );
		
		setSampleTime( System.currentTimeMillis() );
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBytesReceived() {
		return bytesReceived;
	}

	public void setBytesReceived(long bytesReceived) {
		this.bytesReceived = bytesReceived;
	}

	public long getPacketsReceived() {
		return packetsReceived;
	}

	public void setPacketsReceived(long packetsReceived) {
		this.packetsReceived = packetsReceived;
	}

	public long getErrsReceived() {
		return errsReceived;
	}

	public void setErrsReceived(long errsReceived) {
		this.errsReceived = errsReceived;
	}

	public long getDropsReceived() {
		return dropsReceived;
	}

	public void setDropsReceived(long dropsReceived) {
		this.dropsReceived = dropsReceived;
	}

	public long getFifoReceived() {
		return fifoReceived;
	}

	public void setFifoReceived(long fifoReceived) {
		this.fifoReceived = fifoReceived;
	}

	public long getFramesReceived() {
		return framesReceived;
	}

	public void setFramesReceived(long framesReceived) {
		this.framesReceived = framesReceived;
	}

	public long getCompressedReceived() {
		return compressedReceived;
	}

	public void setCompressedReceived(long compressedReceived) {
		this.compressedReceived = compressedReceived;
	}

	public long getMulticastReceived() {
		return multicastReceived;
	}

	public void setMulticastReceived(long multicastReceived) {
		this.multicastReceived = multicastReceived;
	}

	public long getBytesTransmitted() {
		return bytesTransmitted;
	}

	public void setBytesTransmitted(long bytesTransmitted) {
		this.bytesTransmitted = bytesTransmitted;
	}

	public long getPacketsTransmitted() {
		return packetsTransmitted;
	}

	public void setPacketsTransmitted(long packetsTransmitted) {
		this.packetsTransmitted = packetsTransmitted;
	}

	public long getErrsTransmitted() {
		return errsTransmitted;
	}

	public void setErrsTransmitted(long errsTransmitted) {
		this.errsTransmitted = errsTransmitted;
	}

	public long getDropsTransmitted() {
		return dropsTransmitted;
	}

	public void setDropsTransmitted(long dropsTransmitted) {
		this.dropsTransmitted = dropsTransmitted;
	}

	public long getFifoTransmitted() {
		return fifoTransmitted;
	}

	public void setFifoTransmitted(long fifoTransmitted) {
		this.fifoTransmitted = fifoTransmitted;
	}

	public long getCollisionsTransmitted() {
		return collisionsTransmitted;
	}

	public void setCollisionsTransmitted(long collisionsTransmitted) {
		this.collisionsTransmitted = collisionsTransmitted;
	}

	public long getCarriersTransmitted() {
		return carriersTransmitted;
	}

	public void setCarriersTransmitted(long carriersTransmitted) {
		this.carriersTransmitted = carriersTransmitted;
	}

	public long getCompressedTransmitted() {
		return compressedTransmitted;
	}

	public void setCompressedTransmitted(long compressedTransmitted) {
		this.compressedTransmitted = compressedTransmitted;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "Interface: " + getName() + "\n" );
		builder.append( "Bytes Received: " + TidyBits.getAppropriateSI( getBytesReceived() ) + "\n" );
		builder.append( "Packets Received: " + getPacketsReceived() + "\n" );
		builder.append( "Errors Reveived: " + getErrsReceived() + "\n" );
		builder.append( "Drops Received: " + getDropsReceived() + "\n" );
		builder.append( "FIFO Reveived: " + getFifoReceived() + "\n" );
		builder.append( "Frames Received: " + getFramesReceived() + "\n" );
		builder.append( "Compressed Received: " + getCompressedReceived() + "\n" );
		builder.append( "Bytes Transmitted: " + TidyBits.getAppropriateSI( getBytesTransmitted() ) + "\n" );
		builder.append( "Packets Transmitted: " + getPacketsTransmitted() + "\n" );
		builder.append( "Errors Transmitted: " + getErrsTransmitted() + "\n" );
		builder.append( "Drops Transmitted: " + getDropsTransmitted() + "\n" );
		builder.append( "FIFO Transmitted: " + getFifoTransmitted() + "\n" );
		builder.append( "Collisions Transmitted: " + getCollisionsTransmitted() + "\n" );
		builder.append( "Carriers Transmitted: " + getCarriersTransmitted() + "\n" );
		builder.append( "Commpressed Transmitted: " + getCompressedTransmitted() + "\n" );
		return builder.toString();
	}

	public long getSampleTime() {
		return sampleTime;
	}

	public void setSampleTime(long sampleTime) {
		this.sampleTime = sampleTime;
	}
	
}
