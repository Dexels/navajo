package com.dexels.navajo.echoclient.components;

import java.io.*;
import java.util.*;

import nextapp.echo2.app.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.functions.scale.*;

public class BinaryPropertyImage extends StreamImageReference {

//    private byte[] datas;

    private final Property myProperty;
    private final Binary myBinary;
    private static final int BUFFER_SIZE = 1000;
    private int height = 0;
    private int width = 0;
    
    public BinaryPropertyImage(Property p, int width, int height) {
    	 Binary b = (Binary) p.getTypedValue();
       	myBinary = b;
         myProperty = p;
         this.height = height;
         this.width = width;
    }

    
    
      @Override
	public Extent getHeight() {
    	  return new Extent(height, Extent.PX);
      }



	@Override
	public Extent getWidth() {
  	  return new Extent(width, Extent.PX);
	}



	public String getContentType() {
        // TODO Auto-generated method stub
    	if(myBinary==null) {
    		
    		return "image/gif";
    	}
    	String contentType = myBinary.guessContentType();
    	System.err.println("Binary image: Content Type: "+contentType);
    	System.err.println("Length: "+myBinary.getLength());
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
//            Binary b = new Binary(myBinary.getDataAsStream(),false);
            
            Binary b = ImageScaler.scaleToMax(myBinary, width, height, 0.8);
            if(b!=null) {
            	System.err.println("Good binary: rendering. Size: "+myBinary.getLength()+" width: "+width+" height: "+height);
                in = b.getDataAsStream();
                if(in==null) {
                	return;
                }
            	
            } else {
            	System.err.println("Trouble, not rendering");
            	return;
            }
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
