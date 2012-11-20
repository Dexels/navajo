package com.dexels.navajo.tipi.jabber.lockimpl.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class King {
	private String king;
	
	private final static Logger logger = LoggerFactory.getLogger(King.class);
	
	public synchronized String getKing() {
		return king;
	}

	public void setKing(String king) {
		if(getKing()!=null) {
			throw new RuntimeException("Can not create king: " +king+ ". There is already a king: "+getKing());
		}
		logger.info("Ok, "+king+" is now king");
		this.king = king;
	}

	public void removeKing(String king) {
		if(getKing()==null) {
			throw new RuntimeException("Can not remove king. There is no king");
		}
		if(getKing().equals(king)) {
			logger.info("Ok, removed king");
			this.king = null;
		}
		throw new RuntimeException("Requested king remove of: "+king+" while there is a king: "+this.getKing());
	}

	
}
