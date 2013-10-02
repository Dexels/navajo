package com.dexels.navajo.document;

import com.dexels.navajo.document.metadata.GenericBinaryOpener;

public class BinaryOpenerFactory {
	
	private static BinaryOpener instance;
	public static BinaryOpener getInstance()
	{
		if (instance == null)
		{
			instance = new GenericBinaryOpener();
		}
		return instance;
	}
	
	public static void setInstance(BinaryOpener instance)
	{
		BinaryOpenerFactory.instance = instance;
	}

}
