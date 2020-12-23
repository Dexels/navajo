/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.elasticsearch.adapters;

import java.io.IOException;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.elasticsearch.FscrawlerFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class FscrawlerAdapter implements Mappable {

    private Binary binaryFile = null;
    private String id = null;
    private String name = null;

    @Override
    public void load(Access access) throws MappableException, UserException {}

    @Override
    public void store() throws MappableException, UserException {

        try {
            if (binaryFile == null) {
                throw new MappableException("binary file is missing...");
            }

            FscrawlerFactory.getInstance().upload(binaryFile, id, name);
        } catch (IOException e) {
            throw new MappableException("error uploading, id: " + id, e);
        }
    }

    @Override
    public void kill() {}

    public void setBinary(Binary binaryFile) {
        this.binaryFile = binaryFile;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
