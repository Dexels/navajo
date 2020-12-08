/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

import com.dexels.navajo.document.metadata.GenericBinaryOpener;

public class BinaryOpenerFactory {
	
	private static BinaryOpener instance;
	
	private BinaryOpenerFactory() { // no instance 
	}

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
