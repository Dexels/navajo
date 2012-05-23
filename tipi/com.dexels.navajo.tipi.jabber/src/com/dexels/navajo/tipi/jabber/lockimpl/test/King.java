package com.dexels.navajo.tipi.jabber.lockimpl.test;

public class King {
	private String king;

	public synchronized String getKing() {
		return king;
	}

	public void setKing(String king) {
		if(getKing()!=null) {
			throw new RuntimeException("Can not create king: " +king+ ". There is already a king: "+getKing());
		}
		System.err.println("Ok, "+king+" is now king");
		this.king = king;
	}

	public void removeKing(String king) {
		if(getKing()==null) {
			throw new RuntimeException("Can not remove king. There is no king");
		}
		if(getKing().equals(king)) {
			System.err.println("Ok, removed king");
			this.king = null;
		}
		throw new RuntimeException("Requested king remove of: "+king+" while there is a king: "+this.getKing());
	}

	
}
