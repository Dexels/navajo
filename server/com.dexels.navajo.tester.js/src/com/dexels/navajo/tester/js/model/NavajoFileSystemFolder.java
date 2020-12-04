/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tester.js.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NavajoFileSystemFolder implements NavajoFileSystemEntry {
    private String name;
    private List<NavajoFileSystemEntry> contents;
    private String path;
    
    public NavajoFileSystemFolder(File file) {
        this.name = file.getName();
        this.path = file.getAbsolutePath();
        contents = new ArrayList<>();
    }
    
    public void addEntry(NavajoFileSystemEntry e) {
        contents.add(e);
    }
    
    public List<NavajoFileSystemEntry> getEntries() {
        return contents;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return NavajoFileSystemEntry.TYPE_FOLDER;
    }
    
    @Override
    public String getPath() {
        return path;
    }

}
