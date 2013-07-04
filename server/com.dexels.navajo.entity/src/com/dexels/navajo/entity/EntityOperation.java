package com.dexels.navajo.entity;

import com.dexels.navajo.document.Navajo;

public interface EntityOperation {
	public Navajo perform(Navajo input) throws EntityException;
}
