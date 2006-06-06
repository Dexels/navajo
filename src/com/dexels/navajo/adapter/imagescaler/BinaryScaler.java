/*
 * Created on Jun 1, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.adapter.imagescaler;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.imageio.stream.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;

public class BinaryScaler implements Mappable {

    // Static stuff
    private final static double DEFAULT_COMPRESSION = 0.8;
    private static double compressionQuality = DEFAULT_COMPRESSION;
    
    
    // Mappable stuff
    public int width = 1;
    public int height = 1;
    public Binary source = null;
    public Binary scaledToMax = null;
    public Binary scaledToMin = null;
    public Binary scaledFree = null;
    
    public static void setCompressionQuality(double d) {
        compressionQuality = d;
    }

    public static void main(String[] args) throws Exception {
        
        FileInputStream fis = new FileInputStream("c:/aap/groot.jpg");
        Binary b = new Binary(fis);
        Binary c = scaleToMax(b,1000,50);
        FileOutputStream fos = new FileOutputStream("c:/aap/klein.jpg");
        c.write(fos);
        fis.close();
    }
    
    
    private static Binary scale(Binary b, int width, int height, boolean keepAspect) throws IOException {
        Binary c = new Binary();
        InputStream is = b.getDataAsStream();
        ImageInputStream iis = ImageIO.createImageInputStream(is);
        FileOutputStream fos = new FileOutputStream("c:/aap/grunka.jpg");
        b.write(fos);
        fos.flush();
        fos.close();
         
     
        OutputStream os = c.getOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        os.flush();
//        os.close();
        ImageScaler.scale(iis, ios, width, height, keepAspect, (float)compressionQuality);
        ios.flush();
        ios.close();
        is.close();
        return c;
    }

    public static Binary scaleToMax(Binary b, int width, int height) throws IOException {
        if (width>height) {
            height = width;
        }
        if (height>width) {
            width = height;
        }
        return scale(b,width,height,true);
    }

    public static Binary scaleToMin(Binary b, int width, int height) throws IOException {
        return scale(b,width,height,true);
    }

    public static Binary scaleFree(Binary b, int width, int height) throws IOException {
        return scale(b,width,height,false);
    }


    
    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
        // TODO Auto-generated method stub
        
    }

    public void store() throws MappableException, UserException {
        // TODO Auto-generated method stub
        
    }

    public void kill() {
        // TODO Auto-generated method stub
        
    }

    public final Binary getScaledFree() throws UserException {
        if (source!=null) {
            try {
                return scaleFree(source, width, height);
            } catch (IOException e) {
                e.printStackTrace();
                throw new UserException(-2020,"Error scaling image ", e);
            }
        }
        throw new UserException(-1010,"No image source defined");
    }

    public final Binary getScaledToMax()  throws UserException{
        if (source!=null) {
            try {
                return scaleToMax(source, width, height);
            } catch (IOException e) {
                e.printStackTrace();
                throw new UserException(-2020,"Error scaling image ", e);
            }
        }
        throw new UserException(-1010,"No image source defined");
    }

    public final Binary getScaledToMin()  throws UserException{
        if (source!=null) {
            try {
                return scaleToMin(source, width, height);
             } catch (IOException e) {
                 e.printStackTrace();
                throw new UserException(-2020,"Error scaling image ", e);
            }
        }
        throw new UserException(-1010,"No image source defined");
    }

    public final void setHeight(int height) {
        this.height = height;
    }

    public final void setWidth(int width) {
        this.width = width;
    }
    public final void setSource(Binary source) {
        if (source==null) {
            System.err.println("Setting to null source?!");
            return;
        }
        System.err.println("Source set. Size: "+source.getLength());
        this.source = source;
    }

    

}
