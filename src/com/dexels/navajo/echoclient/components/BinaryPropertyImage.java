package com.dexels.navajo.echoclient.components;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

import echopointng.image.URLImageReference;

import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.StreamImageReference;

public class BinaryPropertyImage extends StreamImageReference {

//    private byte[] datas;

    private final Property myProperty;
    private final Binary myBinary;
    private static final int BUFFER_SIZE = 1000;

    public BinaryPropertyImage(Property p) {
    	 Binary b = (Binary) p.getTypedValue();
       	myBinary = b;
         myProperty = p;
    }

      public String getContentType() {
        // TODO Auto-generated method stub
    	if(myBinary==null) {
    		
    		return "image/gif";
    	}
    	String contentType = myBinary.guessContentType();
    	System.err.println("Binary image: Content Type: "+contentType);
    	if(contentType!=null) {
    		return contentType;
    	}
    	return "image/jpeg";
    }

    public void render(OutputStream out) throws IOException {
    	InputStream in = null;
    	if (myBinary == null) {
            System.err.println("Oh dear, bad binary");
            in =  getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/resource/image/bw_questionmark.gif").openStream();
        } else {
        	System.err.println("Good binary: rendering. Size: "+myBinary.getLength());
            in = myBinary.getDataAsStream();
        }
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
        	if(myProperty!=null) {
                return myProperty.getFullPropertyName()+new Random(System.currentTimeMillis()).nextDouble();
        	} else {
        	      return "unknown_property";
        	      		
        	}
        } catch (NavajoException e) {
            return "unknown_property";
        }
    }

}
