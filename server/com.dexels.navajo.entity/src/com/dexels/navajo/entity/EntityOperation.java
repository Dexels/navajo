package com.dexels.navajo.entity;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operation;

public interface EntityOperation {
	public Navajo perform(Navajo input, Operation o) throws EntityException;
}
