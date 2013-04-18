package com.dexels.navajo.comparatormanager;

import java.util.Comparator;

import com.dexels.navajo.document.Message;

public interface ComparatorManager {
	public Comparator<Message> getComparator(String name);
}
