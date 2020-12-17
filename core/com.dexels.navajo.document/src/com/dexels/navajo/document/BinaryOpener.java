/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

import java.io.File;

import com.dexels.navajo.document.types.Binary;

public interface BinaryOpener {

	public boolean browse(String scheme, String url);

	public boolean browse(String url);

	public boolean mail(String url);
	
	public boolean open(Binary b);
	
	public boolean open(File f);
	
	public boolean open(String s);
	
	public boolean exportCsv(String fileName, Message m, String delimiter);
}