package com.dexels.navajo.document.comparator;

import java.util.Comparator;

import com.dexels.navajo.document.Message;

public interface ComparatorFactory {
	public Comparator<Message> createComparator();
	public String getName();
}
