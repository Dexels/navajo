/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tester.js.model;

public interface NavajoFileSystemEntry {
    public static final String TYPE_FILE = "FILE";
    public static final String TYPE_FOLDER = "FOLDER";
    
    public String getName();
    public String getPath();
    public String getType();
}
