package com.dexels.navajo.datasource;

import javax.activation.*;
import java.io.*;

public class ByteArrayDataSource implements DataSource {


    private byte[] m_data;
    private String m_type;
    private String m_name;


    public ByteArrayDataSource (InputStream is,
                                String type,
                                String name)
    {
        m_type = type;
        m_name = name;

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int ch;

            while ((ch = is.read()) != -1) {
                os.write(ch);
            }

            m_data = os.toByteArray();
        } catch (IOException ioex) {
           ioex.printStackTrace(System.err);
        }
    }

    public ByteArrayDataSource (byte[] data,
                                String type,
                                String name)
    {
        m_data = data;
        m_type = type;
        m_name = name;
    }


    public ByteArrayDataSource (String data,
                                String type,
                                String name)
    {
        this(data,type,name,"iso-8859-1");
    }


    public ByteArrayDataSource (String data,
                                String type,
                                String name,
                                String charset)
    {
        m_type = type;
        m_name = name;

        try {
            m_data = data.getBytes(charset);
        } catch (UnsupportedEncodingException uex) {
            uex.printStackTrace(System.err);
        }
    }

    public InputStream getInputStream() throws IOException {
        if (m_data == null) {
            throw new IOException("no data");
        }
        return new ByteArrayInputStream(m_data);
    }

    public OutputStream getOutputStream() throws IOException {
        throw new IOException("not implemented");
    }

    public String getContentType() {
        return m_type;
    }

    public String getName() {
        return m_name;
    }
}
