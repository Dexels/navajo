/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import com.dexels.navajo.document.types.Binary;

public class BinaryDataSource implements DataSource {


    private Binary mData;
    private String mName;


   

    public BinaryDataSource (Binary data, String name)
    {
        mData = data;
        mName = name;
    }

  

    @Override
	public InputStream getInputStream() throws IOException {
        if (mData == null) {
            throw new IOException("no data");
        }
        return mData.getDataAsStream();
    }

    @Override
	public OutputStream getOutputStream() throws IOException {
        throw new IOException("not implemented");
    }

    @Override
	public String getContentType() {
        return mData.getMimeType();
    }

    @Override
	public String getName() {
        return mName;
    }
}
