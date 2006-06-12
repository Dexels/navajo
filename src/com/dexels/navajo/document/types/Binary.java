package com.dexels.navajo.document.types;

import java.io.*;
import java.util.*;

import metadata.*;

import org.dexels.utils.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.saximpl.qdxml.*;

/**
 * <p>
 * Title: Binary
 * </p>
 * <p>
 * Description: Binary datacontainer
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author aphilip
 * @version 1.0
 */

public final class Binary extends NavajoType {

    // private byte [] data;
    private String mimetype = "";

    private File dataFile = null;
    private File lazySourceFile = null;
    private InputStream lazyInputStream = null;
    
    public final static String MSEXCEL = "application/msexcel";

    public final static String MSWORD = "application/msword";

    public final static String PDF = "application/pdf";

    public final static String GIF = "image/gif";

    public final static String TEXT = "plain/text";

    private long expectedLength = 0;

    private FormatDescription currentFormatDescription;
    /**
     * Construct a new Binary object with data from an InputStream It does close
     * the stream. I don't like it, but as yet I don't see any situation where
     * this would be a problem
     * 
     * @param is
     *            InputStream
     */
    public Binary(InputStream is) {
        this(is,false);
    }

    public Binary(InputStream is, boolean lazy) {
        super(Property.BINARY_PROPERTY);
        if (lazy) {
            lazyInputStream = is;
        } else {
            try {
                loadBinaryFromStream(is);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
     }    
    
    public Binary() {
        super(Property.BINARY_PROPERTY);
     }    
    
    /**
     * Returns an outputstream. Write the data for this binary to the stream, flush and close it.
     * @return
     */
    public OutputStream getOutputStream() {
        try {
            return createTempFileOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can not create tempfile?!");
        }
    }
        
    
    private void loadBinaryFromStream(InputStream is) throws IOException, FileNotFoundException {
        int b = -1;
        long fileSize = 0;
        OutputStream fos = createTempFileOutputStream();
        byte[] buffer = new byte[1024];
        while ((b = is.read(buffer, 0, buffer.length)) != -1) {
            fos.write(buffer, 0, b);
            fileSize+=b;
        }
        fos.close();
        is.close();
        expectedLength = fileSize;

        this.mimetype = getSubType("mime");
        this.mimetype = (mimetype == null || mimetype.equals("") ? guessContentType() : mimetype);
        
    }

    private OutputStream createTempFileOutputStream() throws IOException, FileNotFoundException {
        dataFile = File.createTempFile("binary_object", "navajo");
        dataFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(dataFile);
        return fos;
    }

    public Binary(File f) throws IOException {
        this(f, true);
    }

    public Binary(File f, boolean lazy) throws IOException {
        super(Property.BINARY_PROPERTY);
        expectedLength = f.length();
        if (lazy) {
            lazySourceFile = f;
        } else {
            loadBinaryFromStream(new FileInputStream(f));
        }
    }

    /**
     * Construct a new Binary object from a byte array
     * 
     * @param data
     *            byte[]
     *            @deprecated
     */
    public Binary(byte[] data) {
        super(Property.BINARY_PROPERTY);
        Thread.dumpStack();
        try {
            OutputStream fos = createTempFileOutputStream();
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        if (data != null) {
            this.mimetype = guessContentType();
         }
    }

    /**
     * Construct a new Binary object from a byte array, with a given subtype
     * 
     * @param data
     *            byte[]
     * @param subtype
     *            String
     *            @deprecated
     */
    public Binary(byte[] data, String subtype) {
        super(Property.BINARY_PROPERTY, subtype);
        try {
            OutputStream fos = createTempFileOutputStream();
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        this.mimetype = getSubType("mime");
        this.mimetype = (mimetype == null || mimetype.equals("") ? guessContentType() : mimetype);

    }

    
    public Binary(Reader reader, long length) throws IOException {
        super(Property.BINARY_PROPERTY);
        expectedLength = length;
        parseFromReader(reader);
    }
    /**
     * Does NOT close the reader, it is shared with the qdXML parser
     * 
     * @param reader
     * @throws IOException 
     */
    public Binary(Reader reader) throws IOException {
        super(Property.BINARY_PROPERTY);
        parseFromReader(reader);
    }

    private void parseFromReader(Reader reader) throws IOException {
        OutputStream fos = createTempFileOutputStream();
        PushbackReader pr = null;
        if (reader instanceof PushbackReader) {
            pr = (PushbackReader) reader;
        } else {
            pr = new PushbackReader(reader, QDParser.PUSHBACK_SIZE);
        }

        Writer w = Base64.newDecoder(fos);
        try {
            copyBufferedBase64Resource(w, pr);
        } catch (Base64DecodingFinishedException e) {
        }
        w.flush();
        fos.close();
    }

    /**
     * Unbuffered, @deprecated
     * @param out
     * @param in
     * @throws IOException
     */
    private void copyBase64Resource(Writer out, PushbackReader in) throws IOException {
         long progress = 0;
        int read;
        int iterations = 0;
        while ((read = in.read()) > -1) {
            progress ++;
            iterations++;
            if (iterations % 100 == 0) {
                NavajoFactory.getInstance().fireBinaryProgress("Reading data", progress, expectedLength);
            }
             if (read == '<') {
                in.unread('<');
                break;
            } else {
                out.write(read);
            }
        }
        
        out.flush();
    }

    /**
     * Buffered
     * @param out
     * @param in
     * @throws IOException
     */
    private void copyBufferedBase64Resource(Writer out, PushbackReader in) throws IOException {
         long progress = 0;
        int read;
        int iterations = 0;
        char[] buffer = new char[QDParser.PUSHBACK_SIZE];
        while ((read = in.read(buffer, 0 ,buffer.length)) > -1) {
//            progress +=read;
            iterations++;
            if (iterations % 100 == 0) {
//                System.err.println("Reading data. "+progress+"/"+expectedLength);
                NavajoFactory.getInstance().fireBinaryProgress("Reading data", progress, expectedLength);
            }
            int ii = getIndexOf(buffer, '<');
//            System.err.println("Buffer size: "+buffer.length+" '<' index: "+ii+" read: "+read);
            if (ii==-1) {
                out.write(buffer,0,read);
                progress+=read;
            } else {
//                debug("Writing", buffer, 0, ii);
                out.write(buffer, 0, ii);
                progress+=ii;
                NavajoFactory.getInstance().fireBinaryProgress("Reading data", progress, expectedLength);
//                debug("Pushback", buffer, ii, read-1);
                in.unread(buffer, ii, read-ii);
                break;
            }
            
//             if (read == '<') {
//                in.unread('<');
//                break;
//            } else {
//                out.write(read);
//            }
        }
        
        out.flush();
    }

    private void debug(String prefix, char[] buffer, int offset, int length) {
        System.err.print("Prefix: "+prefix+"DEBUG >");
        for (int i = offset; i < length; i++) {
            System.err.print(buffer[i]);
        }
        System.err.println("<");
    }

    private int getIndexOf(char[] buffer, char c) {
        for (int i = 0; i < buffer.length; i++) {
            if (c==buffer[i]) {
                return i;
            }
        }
        return -1;
    }
    
    
    public long getLength() {
        if (lazySourceFile != null && lazySourceFile.exists()) {
            return lazySourceFile.length();
        } else {
            if (dataFile != null && dataFile.exists()) {
                return dataFile.length();
            } else {
                return 0;
            }
        }
    }

    /**
     * Guess the internal data's mimetype
     * 
     * @return String
     */
    public final String guessContentType() {
//        if (mimetype != null && !mimetype.equals("")) {
//            return mimetype;
//        } else {
            File f;
            if (lazySourceFile!=null) {
                f = lazySourceFile;
            } else {
                f = dataFile;
            }
            currentFormatDescription = metadata.FormatIdentification.identify(f);
//            System.err.println("Guessed: "+currentFormatDescription.getMimeType());
//            System.err.println("Guessed: "+currentFormatDescription.getFileExtensions());
            if (currentFormatDescription == null) {
                return "unknown type";
            } else if (currentFormatDescription.getMimeType() != null) {
                return currentFormatDescription.getMimeType();
            } else {
                return currentFormatDescription.getShortName();
            }
//        }
    }

    
    public FormatDescription getFormatDescription() {
        return currentFormatDescription;
        
    }
    
    public String getExtension() {
        if(currentFormatDescription==null) {
            guessContentType();
        }
        if (currentFormatDescription!=null) {
            List exts = currentFormatDescription.getFileExtensions();
            if (exts!=null && !exts.isEmpty()) {
                return (String)exts.get(0);
            }
        }
        return "dat";
    }
    
    /**
     * Get this Binary's data
     * 
     * @return byte[]
     */
    public final byte[] getData() {
        // return this.data;
        RandomAccessFile in = null;

        File file = null;
        if (lazySourceFile != null) {
            file = lazySourceFile;
        } else {
            file = dataFile;
        }
        try {
            if (dataFile != null) {
                in = new RandomAccessFile(file, "r");
                byte[] data = new byte[(int) file.length()];// + 1 ];
                in.readFully(data);
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    private final void copyResource(String name, OutputStream out, InputStream in, long totalSize) throws IOException {
//        BufferedInputStream bin = new BufferedInputStream(in, 2000);
//        BufferedOutputStream bout = new BufferedOutputStream(out, 2000);
         
        byte[] buffer = new byte[1024];
        int read;
        long size = 0;
        int iter = 0;
        try {
        while ((read = in.read(buffer)) > -1) {
            out.write(buffer, 0, read);
            size += read;
            iter++;
            if (iter % 100 == 0) {
//                System.err.println("Size: " + size+" bytes");
                NavajoFactory.getInstance().fireBinaryProgress(name, (long)size, (long)totalSize);
                
            }
            // System.err.println("COPIED: "+read);
        }
        out.flush();

        in.close();
        out.flush();
        } finally {
            NavajoFactory.getInstance().fireBinaryFinished("Finished", (long)totalSize);
        }
        // bout.close();
    }

    public final void write(OutputStream to) throws IOException {
        InputStream in = getDataAsStream();
        if (in != null) {
            copyResource("Writing data",to, in,getLength());
            try {
                in.close();
            } catch (Exception e) {
                // Don't care
            }
        }
    }

    public final InputStream getDataAsStream() {
        if (lazySourceFile != null) {
            try {
                return new FileInputStream(lazySourceFile);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace(System.err);
                return null;
            }
        }
        if (dataFile != null) {
            try {
                return new FileInputStream(dataFile);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace(System.err);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Get this Binary's mimetype
     * 
     * @return String
     */
    public final String getMimeType() {
        return this.mimetype;
    }

    /**
     * Set this Binary's mimetype
     * 
     * @param mime
     *            String
     */
    public final void setMimeType(String mime) {
        this.mimetype = mime;
    }

    // for sorting. Not really much to sort
    public final int compareTo(Object o) {
        return 0;
    }

    public void finalize() {
        if (dataFile != null && dataFile.exists()) {
            try {
                dataFile.delete();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    /**
     * @deprecated Returns base64. Preferrably don't use this, unless you REALLY
     *             want the data in a string, as it is quite memory hungry
     *             Even then, rather write to a StringWriter or something. 
     *             This is an old-school function, and it uses the Sun base64 encoder
     *             so it is quite possible that large binaries will result in out of
     *             memory exceptions
     */
    public final String getBase64() {
        // final StringWriter sw = new StringWriter();
        // final OutputStream os = Base64.newEncoder( sw );
        //    
        // copyResource( os, datastream );
        //     
        // os.close();
        // datastream.close();
        // return sw.toString();

        if (getData() != null) {
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            String data = enc.encode(getData());
            data = data.replaceAll("\n", "\n  ");
            return data;
        } else {
            return null;
        }
    }

    /**
     * writes the base64 to a Writer. Will not close the writer.
     * 
     * @throws IOException
     */
    public final void writeBase64(Writer sw) throws IOException {
        final OutputStream os = Base64.newEncoder(sw);
//        System.err.println("Encoder created, my length: "+getLength());
        copyResource("Writing data",os, getDataAsStream(),getLength());
//        System.err.println("Copied to stream");
        os.close();

    }

}
