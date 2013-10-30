package com.dexels.navajo.datasource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.activation.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteArrayDataSource implements DataSource {

	
	private final static Logger logger = LoggerFactory
			.getLogger(ByteArrayDataSource.class);
	
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
        	logger.error("Error: ", ioex);
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
        	logger.error("Error: ", uex);
        }
    }

    @Override
	public InputStream getInputStream() throws IOException {
        if (m_data == null) {
            throw new IOException("no data");
        }
        return new ByteArrayInputStream(m_data);
    }

    @Override
	public OutputStream getOutputStream() throws IOException {
        throw new IOException("not implemented");
    }

    @Override
	public String getContentType() {
        return m_type;
    }

    @Override
	public String getName() {
        return m_name;
    }
}
