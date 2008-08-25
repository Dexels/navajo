/*
 * TidyBits.java
 *
 * Created on February 3, 2006, 10:58 PM
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
 * This class contains a number of utility methods for converting a number (of bits)
 * into more user friendly formats such as kilobits and megabits.
 * @author doozer
 */
public class TidyBits {
	
	public static final long BIT = 1;
	
	public static final long KILOBIT = 1000;
	public static final long MEGABIT = 1000000;
	public static final long GIGABIT = 1000000000;
	public static final long TERABIT = 1000000000000l;
	public static final long PETABIT = 1000000000000000l;
	public static final long EXABIT = 1000000000000000000l;
	//public static final long ZETTABIT = 1000000000000000000000l;
	//public static final long YOTTABIT = 1000000000000000000000000l;
	
	public static final long KIBIBIT = 1024;
	public static final long MEBIBIT = 1048576;
	public static final long GIBIBIT = 1073741824;
	public static final long TEBIBIT = 1099511627776l;
	public static final long PEBIBIT = 1125899906842624l;
	public static final long EXBIBIT = 1152921504606846976l;
	//public static final long ZEBIBIT = 1180591620717411303424l;
	//public static final long YOBIBIT = 1208925819614629174706176l;
	
	
	/** Creates a new instance of TidyBytes */
	private TidyBits() {}
	
	public static final String getAppropriateSI( long value ) {
		return getAppropriateSI ( value, (long) -1 );
	}
	
	public static final String getAppropriateSI( long value, long SI ) {
		String result = "";
		
		if( SI == BIT || ( SI == -1 && value < KILOBIT ) ) {
			result = getBits( value );
		} else if( SI == KILOBIT || ( SI == -1 && value >= KILOBIT && value < MEGABIT ) ) {
			result = getKiloBits( value );
		} else if( SI == MEGABIT || ( SI == -1 && value >= MEGABIT && value < GIGABIT ) ) {
			result = getMegaBits( value );
		} else if( SI == GIGABIT || ( SI == -1 && value >= GIGABIT && value < TERABIT ) ) {
			result = getGigaBits( value );
		} else if( SI == TERABIT || ( SI == -1 && value >= TERABIT && value <= PETABIT ) ) {
			result = getTeraBits( value );
		} else if( SI == PETABIT || ( SI == -1 && value >= PETABIT && value < EXABIT ) ) {
			result = getPetaBits( value );
		} else if( SI == EXABIT || ( SI == -1 && value >= EXABIT ) ) {
			result = getExaBits( value );
		}
		
		return result;
	}
	
	public static final String getAppropriateBinary( long value ) {
		String result = "";
		
		if( value < KIBIBIT ) {
			result = getBits( value );
		} else if( value >= KIBIBIT && value < MEBIBIT ) {
			result = getKibiBits( value );
		} else if( value >= MEBIBIT && value < GIBIBIT ) {
			result = getMebiBits( value );
		} else if( value >= GIBIBIT && value < TEBIBIT ) {
			result = getGibiBits( value );
		} else if( value >= TEBIBIT && value < PEBIBIT ) {
			result = getTebiBits( value );
		} else if( value >= PEBIBIT && value < EXBIBIT ) {
			result = getPebiBits( value );
		} else if( value >= EXBIBIT ) {
			result = getExiBits( value );
		}
		
		return result;
	}
	
	public static final String getBits( long value ) {
		return format( value, BIT, "b" );
	}
	
	public static final String getKiloBits( long value ) {
		return format( value, KILOBIT, "Kb" );
	}
	
	public static final String getKibiBits( long value ) {
		return format( value, KIBIBIT, "Kib" );
	}
	
	public static final String getMegaBits( long value ) {
		return format( value, MEGABIT, "Mb" );
	}
	
	public static final String getMebiBits( long value ) {
		return format( value, MEBIBIT, "Mib" );
	}
	
	public static final String getGigaBits( long value ) {
		return format( value, GIGABIT, "Gb" );
	}
	
	public static final String getGibiBits( long value ) {
		return format( value, GIBIBIT, "Gib" );
	}
	
	public static final String getTeraBits( long value ) {
		return format( value, TERABIT, "Tb" );
	}
	
	public static final String getTebiBits( long value ) {
		return format( value, TEBIBIT, "Tib" );
	}
	
	public static final String getPetaBits( long value ) {
		return format( value, PETABIT, "Pb" );
	}
	
	public static final String getPebiBits( long value ) {
		return format( value, PEBIBIT, "Pib" );
	}
	
	public static final String getExaBits( long value ) {
		return format( value, EXABIT, "Eb" );
	}
	
	public static final String getExiBits( long value ) {
		return format( value, EXBIBIT, "Eib" );
	}
	
	private static final String format( long value, long size, String units ) {
		DecimalFormat format = new DecimalFormat( "#,##0.00" );
		return format.format( (double)value / size ) + " " + units;
	}
	
}
