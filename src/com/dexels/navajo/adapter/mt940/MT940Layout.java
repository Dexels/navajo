package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;

/**
 * Holds the record layout for the entire file depending on the selected bank
 * @author Erik Versteeg
 *
 */
public class MT940Layout implements Serializable {
	private static final long serialVersionUID = -2241780276010081113L;
	private String selectedBank;
	// all tags consist of 2 numbers. 1st = starting point after the tag and 2nd = max length
	// Example - line = :20:1602359
	// Tag = :20:, so setting starting point for this tag at 1
	// Some tags consist of multiple fields. This will be done with tagparts like tag86_field1
	private int[] tag20;
	private int[] tag21;
	private int[] tag25;
	private int[] tag28;
	private int[] tag28C;
	private int[] tag60F;
	private int[] tag61;
	private int[] tag62F;
	private int[] tag64;
	private int[] tag86;
	private int[] tagNS;
	private String tag86_subfieldStartingChar; // default <, but in some cases ?
	
	public MT940Layout(String bank) {
		this.setSelectedBank(bank);
		this.setGenericLayoutValues();
		// Override defaults for the given bank
		this.setBankSpecificLayoutValues();
	}
	
	/**
	 * Sets all the tag values for all the banks
	 */
	private void setGenericLayoutValues() {
		this.setTag20( new int[] {1, 16});
		this.setTag21( new int[] {1, 16});
		this.setTag25( new int[] {1, 28});
		this.setTag28( new int[] {1, 36});
		this.setTag28C(new int[] {1, 9});
		this.setTag60F(new int[] {1, 25});
		this.setTag61( new int[] {1, 100});
		this.setTag62F(new int[] {1, 25});
		this.setTag64( new int[] {1, 25});
		this.setTag86( new int[] {1, (6*65)});
		this.setTag86_subfieldStartingChar("<");
		this.setTagNS( new int[] {1, 16});
	}
	
	/**
	 * Sets all the tag values for the selected bank
	 */
	private void setBankSpecificLayoutValues() {
		if (this.getSelectedBank().equalsIgnoreCase(MT940Constants.ABNAMRO)) {
//			this.setTag86_subfieldStartingChar("?");
		}
	}

	
	public String getSelectedBank() {
		return selectedBank;
	}
	public void setSelectedBank(String selectedBank) {
		this.selectedBank = selectedBank;
	}
	
	public int[] getTag20() {
		return tag20;
	}
	public void setTag20(int[] tag20) {
		this.tag20 = tag20;
	}
	
	public int[] getTag21() {
		return tag21;
	}
	public void setTag21(int[] tag21) {
		this.tag21 = tag21;
	}

	public int[] getTag25() {
		return tag25;
	}
	public void setTag25(int[] tag25) {
		this.tag25 = tag25;
	}

	public int[] getTag28() {
		return tag28;
	}

	public void setTag28(int[] tag28) {
		this.tag28 = tag28;
	}

	public int[] getTag28C() {
		return tag28C;
	}

	public void setTag28C(int[] tag28c) {
		tag28C = tag28c;
	}

	public int[] getTag60F() {
		return tag60F;
	}

	public void setTag60F(int[] tag60f) {
		tag60F = tag60f;
	}

	public int[] getTag61() {
		return tag61;
	}

	public void setTag61(int[] tag61) {
		this.tag61 = tag61;
	}

	public int[] getTag62F() {
		return tag62F;
	}

	public void setTag62F(int[] tag62f) {
		tag62F = tag62f;
	}

	public int[] getTag64() {
		return tag64;
	}

	public void setTag64(int[] tag64) {
		this.tag64 = tag64;
	}

	public int[] getTag86() {
		return tag86;
	}

	public void setTag86(int[] tag86) {
		this.tag86 = tag86;
	}

	public int[] getTagNS() {
		return tagNS;
	}

	public void setTagNS(int[] tagNS) {
		this.tagNS = tagNS;
	}

	public String getTag86_subfieldStartingChar() {
		return tag86_subfieldStartingChar;
	}

	public void setTag86_subfieldStartingChar(String tag86_subfieldStartingChar) {
		this.tag86_subfieldStartingChar = tag86_subfieldStartingChar;
	}
}
