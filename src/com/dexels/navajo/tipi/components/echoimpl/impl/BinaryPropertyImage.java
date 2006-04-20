package com.dexels.navajo.tipi.components.echoimpl.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.StreamImageReference;

public class BinaryPropertyImage extends StreamImageReference {

    private byte[] datas;

    private Property myProperty = null;

    private static final int BUFFER_SIZE = 1000;

    public BinaryPropertyImage(Property p) {
        setProperty(p);
    }

    public void setProperty(Property p) {
        Binary b = (Binary) p.getTypedValue();
        datas = b.getData();
        myProperty = p;
    }

    public String getContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    public void render(OutputStream out) throws IOException {
        if (datas == null) {
            System.err.println("Oh dear, bad binary");
            return;
        }
        InputStream in = new ByteArrayInputStream(datas);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;

        try {
            do {
                bytesRead = in.read(buffer);
                if (bytesRead > 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } while (bytesRead > 0);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public String getRenderId() {
        try {
            return myProperty.getFullPropertyName();
        } catch (NavajoException e) {
            return "unknown_property";
        }
    }

}
