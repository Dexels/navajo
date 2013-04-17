package com.dexels.navajo.document.comparator.impl;

import java.util.Comparator;

import com.dexels.navajo.document.Message;

class PropertyCountComparator implements Comparator<Message> {


	@Override
	public int compare(Message o1, Message o2) {
		if(o1==null || o2==null) {
			return 0;
		}
		int count1 = o1.getProperties().size();
		int count2 = o2.getProperties().size();
		if(count1==count2) {
			return 0;
		}
		return count1-count2;
	}

}