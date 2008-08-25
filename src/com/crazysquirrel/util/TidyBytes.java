/*
 * TidyBytes.java
 *
 * Created on February 2, 2006, 10:58 PM
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

package com.crazysquirrel.util;

import java.text.DecimalFormat;

/**
 * This class contains a number of utility methods for converting a number (of bytes)
 * into more user friendly formats such as kilobytes and megabytes.
 * @author doozer
 */
public class TidyBytes {
	
	public static final long BYTE = 1;
	
	public static final long KILOBYTE = 1000;
	public static final long MEGABYTE = 1000000;
	public static final long GIGABYTE = 1000000000;
	public static final long TERABYTE = 1000000000000l;
	public static final long PETABYTE = 1000000000000000l;
	public static final long EXABYTE = 1000000000000000000l;
	//public static final long ZETTABYTE = 1000000000000000000000l;
	//public static final long YOTTABYTE = 1000000000000000000000000l;
	
	public static final long KIBIBYTE = 1024;
	public static final long MEBIBYTE = 1048576;
	public static final long GIBIBYTE = 1073741824;
	public static final long TEBIBYTE = 1099511627776l;
	public static final long PEBIBYTE = 1125899906842624l;
	public static final long EXBIBYTE = 1152921504606846976l;
	//public static final long ZEBIBYTE = 1180591620717411303424l;
	//public static final long YOBIBYTE = 1208925819614629174706176l;
	
	
	/** Creates a new instance of TidyBytes */
	private TidyBytes() {}
	
	public static final String getAppropriateSI( long value ) {
		String result = "";
		
		if( value < KILOBYTE ) {
			result = getBytes( value );
		} else if( value >= KILOBYTE && value < MEGABYTE ) {
			result = getKiloBytes( value );
		} else if( value >= MEGABYTE && value < GIGABYTE ) {
			result = getMegaBytes( value );
		} else if( value >= GIGABYTE && value < TERABYTE ) {
			result = getGigaBytes( value );
		} else if( value >= TERABYTE && value <= PETABYTE ) {
			result = getTeraBytes( value );
		} else if( value >= PETABYTE && value < EXABYTE ) {
			result = getPetaBytes( value );
		} else if( value >= EXABYTE ) {
			result = getExaBytes( value );
		}
		
		return result;
	}
	
	public static final String getAppropriateBinary( long value ) {
		String result = "";
		
		if( value < KIBIBYTE ) {
			result = getBytes( value );
		} else if( value >= KIBIBYTE && value < MEBIBYTE ) {
			result = getKibiBytes( value );
		} else if( value >= MEBIBYTE && value < GIBIBYTE ) {
			result = getMebiBytes( value );
		} else if( value >= GIBIBYTE && value < TEBIBYTE ) {
			result = getGibiBytes( value );
		} else if( value >= TEBIBYTE && value < PEBIBYTE ) {
			result = getTebiBytes( value );
		} else if( value >= PEBIBYTE && value < EXBIBYTE ) {
			result = getPebiBytes( value );
		} else if( value >= EXBIBYTE ) {
			result = getExiBytes( value );
		}
		
		return result;
	}
	
	public static final String getBytes( long value ) {
		return format( value, BYTE, "B" );
	}
	
	public static final String getKiloBytes( long value ) {
		return format( value, KILOBYTE, "KB" );
	}
	
	public static final String getKibiBytes( long value ) {
		return format( value, KIBIBYTE, "KiB" );
	}
	
	public static final String getMegaBytes( long value ) {
		return format( value, MEGABYTE, "MB" );
	}
	
	public static final String getMebiBytes( long value ) {
		return format( value, MEBIBYTE, "MiB" );
	}
	
	public static final String getGigaBytes( long value ) {
		return format( value, GIGABYTE, "GB" );
	}
	
	public static final String getGibiBytes( long value ) {
		return format( value, GIBIBYTE, "GiB" );
	}
	
	public static final String getTeraBytes( long value ) {
		return format( value, TERABYTE, "TB" );
	}
	
	public static final String getTebiBytes( long value ) {
		return format( value, TEBIBYTE, "TiB" );
	}
	
	public static final String getPetaBytes( long value ) {
		return format( value, PETABYTE, "PB" );
	}
	
	public static final String getPebiBytes( long value ) {
		return format( value, PEBIBYTE, "PiB" );
	}
	
	public static final String getExaBytes( long value ) {
		return format( value, EXABYTE, "EB" );
	}
	
	public static final String getExiBytes( long value ) {
		return format( value, EXBIBYTE, "EiB" );
	}
	
	private static final String format( long value, long size, String units ) {
		DecimalFormat format = new DecimalFormat( "#,##0.00" );
		return format.format( (double)value / size ) + " " + units;
	}
	
}
