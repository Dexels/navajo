package com.dexels.navajo.document.comparator.impl;

import java.util.Comparator;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.comparator.ComparatorFactory;

public class PropertyCountComparatorFactory implements ComparatorFactory {

	@Override
	public Comparator<Message> createComparator() {
		return new PropertyCountComparator();
	}

	@Override
	public String getName() {
		return PropertyCountComparator.class.getName();
	}
}
