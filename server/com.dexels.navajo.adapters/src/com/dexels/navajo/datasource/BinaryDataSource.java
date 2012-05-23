package com.dexels.navajo.datasource;

import javax.activation.*;

import com.dexels.navajo.document.types.Binary;

import java.io.*;

public class BinaryDataSource implements DataSource {


    private Binary m_data;
    private String m_name;


   

    public BinaryDataSource (Binary data, String name)
    {
        m_data = data;
        m_name = name;
    }

  

    public InputStream getInputStream() throws IOException {
        if (m_data == null) {
            throw new IOException("no data");
        }
        return m_data.getDataAsStream();
    }

    public OutputStream getOutputStream() throws IOException {
        throw new IOException("not implemented");
    }

    public String getContentType() {
        return m_data.getMimeType();
    }

    public String getName() {
        return m_name;
    }
}
