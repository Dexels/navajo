package com.dexels.navajo.document.types;

import java.io.*;
import java.net.*;
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
 * @version $Id$
 */

public final class Binary extends NavajoType implements Serializable,Comparable<Binary> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6392612802747438142L;

	// private byte [] data;
    private String mimetype = "text/plain";

    private transient File dataFile = null;
    private transient File lazySourceFile = null;
    private transient InputStream lazyInputStream = null;
    private String myRef = null;
    
    public final static String MSEXCEL = "application/msexcel";

    public final static String MSWORD = "application/msword";

    public final static String PDF = "application/pdf";

    public final static String GIF = "image/gif";

    public final static String TEXT = "plain/text";

    private long expectedLength = 0;

    private FormatDescription currentFormatDescription;
    
    private final static HashMap<String,Binary> persistedBinaries = new HashMap<String,Binary>();
    
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
            this.mimetype = guessContentType();
        }
     }    
    
    public Binary() {
        super(Property.BINARY_PROPERTY);
     }    
    
    
    public String getHandle() {
        if (dataFile!=null) {
            return dataFile.getName();
        }
        if (lazySourceFile!=null) {
            return lazySourceFile.getAbsolutePath();
        }
        return "none";
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
        
    
    /**
     * Some components like a URL to point to their data.
     * Don't forget that this is a local (file) url, and is only valid on this machine 
     * @return
     * @throws MalformedURLException 
     */
    public URL getURL() throws MalformedURLException {
        if (lazySourceFile != null && lazySourceFile.exists()) {
            return lazySourceFile.toURI().toURL();
        } else {
            if (dataFile != null && dataFile.exists()) {
                return dataFile.toURI().toURL();
            } else {
                return null;
            }
        }
    }
	        
    private void loadBinaryFromStream(InputStream is) throws IOException, FileNotFoundException {
    	loadBinaryFromStream(is, true);
    }
    
    private void loadBinaryFromStream(InputStream is, boolean close) throws IOException, FileNotFoundException {
        int b = -1;
        long fileSize = 0;
        OutputStream fos = createTempFileOutputStream();
		byte[] buffer = new byte[1024];
		try {
			while ((b = is.read(buffer, 0, buffer.length)) != -1) {
				fos.write(buffer, 0, b);
				fileSize += b;
			}
		} finally {

			try {
				fos.close();
			} catch (IOException e) {}
			if ( close ) {
				try {
					is.close();
				} catch (IOException e) {}
			}
		}
        expectedLength = fileSize;

        this.mimetype = getSubType("mime");
        this.mimetype = (mimetype == null || mimetype.equals("") ? guessContentType() : mimetype);
        
    }

    private OutputStream createTempFileOutputStream() throws IOException, FileNotFoundException {
    	if(NavajoFactory.getInstance().isSandboxMode()) {
    		dataFile = NavajoFactory.getInstance().createStorageHandle();
    		ByteArrayOutputStream baos = new ByteArrayOutputStream() {
    			public void close() {
    				try {
						super.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
	    			NavajoFactory.getInstance().storeHandle(dataFile.getName(), toByteArray());
    			}
    		};
    		return baos;
    	} else {
            dataFile = File.createTempFile("binary_object", "navajo", NavajoFactory.getInstance().getTempDir());
            
            FileOutputStream fos = new FileOutputStream(dataFile);
            return fos;
    	}
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
        this.mimetype = guessContentType();
    }
    
    private final void init(File f, boolean lazy) throws IOException {
    	 expectedLength = f.length();
         if (lazy) {
             lazySourceFile = f;
         } else {
             loadBinaryFromStream(new FileInputStream(f));
         }
         this.mimetype = guessContentType();
    }

    /**
     * Construct a new Binary object from a byte array
     * 
     * @param data
     *            byte[]
     *           
     */
    public Binary(byte[] data) {
        super(Property.BINARY_PROPERTY);
        //Thread.dumpStack();

        if (data != null) {
            try {
                OutputStream fos = createTempFileOutputStream();
                fos.write(data);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
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
    @Deprecated
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
        this.mimetype = getSubType("mime");
        this.mimetype = (mimetype == null || mimetype.equals("") ? guessContentType() : mimetype);
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
    	if (NavajoFactory.getInstance().isSandboxMode()) {
    		byte[] b = NavajoFactory.getInstance().getHandle(dataFile.getName());
    		if (b==null) {
				return 0;
			} else {
				return b.length;
			}
		} else {
	        if (lazySourceFile != null && lazySourceFile.exists()) {
	            return lazySourceFile.length();
	        } else {
	            if (dataFile != null && dataFile.exists()) {
	            	if (NavajoFactory.getInstance().isSandboxMode()) {
	            		byte[] res = NavajoFactory.getInstance().getHandle(dataFile.getName());
	            		if(res==null) {
	            			return 0;
	            		} else {
	            			return res.length;
	            		}
	            	} else {
		            	return dataFile.length();
					}
	            } else {
	                return 0;
	            }
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
            if ( f != null ) {
            	if(NavajoFactory.getInstance().isSandboxMode()) {
                	currentFormatDescription = metadata.FormatIdentification.identify(NavajoFactory.getInstance().getHandle(f.getName()));
            	} else {
                	currentFormatDescription = metadata.FormatIdentification.identify(f);
            	}
            }
//            System.err.println("Guessed: "+currentFormatDescription.getMimeType());
//            System.err.println("Guessed: "+currentFormatDescription.getFileExtensions());
            if (currentFormatDescription == null) {
                return "text/plain";
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
            List<String> exts = currentFormatDescription.getFileExtensions();
            if (exts!=null && !exts.isEmpty()) {
                return exts.get(0);
            }
        }
        return "dat";
    }
    
    /**
     * Get this Binary's data
     * 
     * This one is a bit odd. Why on earth not simply get the data as a stream, and copyResource to a ByteArrayOutputStream?
     * @return byte[]
     */
    public final byte[] getData() {

    	if (NavajoFactory.getInstance().isSandboxMode()) {
			if(dataFile==null) {
				return null;
			} else {
				byte[] bb= NavajoFactory.getInstance().getHandle(dataFile.getName());
				return bb;
			}
		} else {
	        RandomAccessFile in = null;

	        File file = null;
	        if (lazySourceFile != null) {
	            file = lazySourceFile;
	        } else {
	            file = dataFile;
	        }
	        try {
	            if (file != null) {
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
	                e.printStackTrace();
	            }
	        }
	        return null;
		}
    	
    	// return this.data;

    }

    private final void copyResource(String name, OutputStream out, InputStream in, long totalSize) throws IOException {
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
                NavajoFactory.getInstance().fireBinaryProgress(name, size, totalSize);
                
            }
        }
        out.flush();

        in.close();
        out.flush();
        } finally {
            NavajoFactory.getInstance().fireBinaryFinished("Finished", totalSize);
        }
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

    public File getFile() {
    	if (lazySourceFile != null) {
    		return lazySourceFile;
    	} else {
    		return dataFile;
    	}
    }
    
    public final InputStream getDataAsStream() {
    	if (NavajoFactory.getInstance().isSandboxMode()) {
    		ByteArrayInputStream bais = new ByteArrayInputStream(NavajoFactory.getInstance().getHandle(dataFile.getName()));
    		return bais;
    	} else {
	        if (lazySourceFile != null) {
	            try {
	                return new FileInputStream(lazySourceFile);
	            } catch (FileNotFoundException e) {
	                e.printStackTrace(System.err);
	                return null;
	            }
	        }
	        if (dataFile != null) {
	            try {
	                return new FileInputStream(dataFile);
	            } catch (FileNotFoundException e) {
	                e.printStackTrace(System.err);
	                return null;
	            }
	        } else {
	            return null;
	        }
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


    
    public final boolean equals(Object o) {
    	if(!(o instanceof Binary)) {
        	return false;
    	} 
    	Binary b = (Binary)o;
    	System.err.println("Comparing binary sizes: "+b.getLength()+" with "+getLength());
    	if(b.getLength()!=getLength()) {
    		return false;
    	}
    	System.err.println("Comparing binary mime: "+b.getMimeType()+" with "+getMimeType());
    	if(!(getMimeType().equals(b.getMimeType()))) {
    		return false;
    	}
    	// TODO REALLY implement this 
    	System.err.println("DUBIOUS EQUALITY IN BINARY!");
     	Thread.dumpStack();
    	return true;
    	
    }
    
    // for sorting. Not really much to sort
    public final int compareTo(Binary o) {
    	int h = hashCode();
    	int i = o.hashCode();
    	if(h>i) {
    		return 1;
    	}
    	if(h<i) {
    		return -1;
    	}
        return 0;
    }

    protected void finalize() {
    
    	if (NavajoFactory.getInstance().isSandboxMode()) {
    		if(dataFile!=null) {
    			NavajoFactory.getInstance().removeHandle(dataFile.getName());
    		}
		} else {
	        if (dataFile != null && dataFile.exists() ) {
	            try {
	                dataFile.delete();
	            } catch (Throwable t) {
	                t.printStackTrace();
	            }
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
    @Deprecated
	public final String getBase64() {
         final StringWriter sw = new StringWriter();
         final OutputStream os = Base64.newEncoder( sw );
            
         InputStream dataAsStream = getDataAsStream();
         if(dataAsStream==null) {
        	 return null;
         }
   		 try {
			copyResource("base64", os, dataAsStream, getLength());
			 os.close();
			 dataAsStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
         return sw.toString();

//        if (getData() != null) {
//            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
//            String data = enc.encode(getData());
//            data = data.replaceAll("\n", "\n  ");
//            return data;
//        } else {
//            return null;
//        }
    }

    /**
     * writes the base64 to a Writer. Will not close the writer.
     * 
     * @throws IOException
     */
    public final void writeBase64(Writer sw) throws IOException {
        final OutputStream os = Base64.newEncoder(sw);
        InputStream dataInStream = getDataAsStream();
        if (dataInStream!=null) {
        	copyResource("Writing data",os, dataInStream,getLength());
		}
//        System.err.println("Copied to stream");
        os.close();

    }

    /**
     * Get the file reference to a binary.
     * If persist is set, the binary object is put in a persistent store to
     * prevent it from being garbage collected.
     * 
     * @param persist
     * @return
     */
    public String getTempFileName(boolean persist) {
    	
    	String ref = null;
    	if (lazySourceFile != null) {
    		ref = lazySourceFile.getAbsolutePath();
    	} else if (dataFile != null) {    		
    		ref =  dataFile.getAbsolutePath();
    	} 
    	
    	if ( persist && ref != null && !persistedBinaries.containsKey(ref) ) {
    		persistedBinaries.put(ref, this);
    	}
    	myRef = ref;
    	
    	return ref;
    }	
    
    public void removeRef() {
    	System.err.println("In removeRef(), myRef = " + myRef);
    	if ( myRef != null ) {
    		persistedBinaries.remove(myRef);
    	}
    }
    /**
     * Remove a persisted binary.
     * 
     * @param ref
     */
    public static void removeRef(String ref) {
    	if ( ref != null ) {
    	persistedBinaries.remove(ref);
    	}
    }

    public boolean isEmpty() {
        if (dataFile==null && lazySourceFile==null) {
            return true;
        }
        return false;
    }
    
    public boolean isEqual(Binary other ) {
    	
    	if ( other == null ) {
    		return false;
    	}
    	
    	if ( getLength() != other.getLength() ) {
    		return false;
    	}
    	
    	// compare byte by byte.
    	RandomAccessFile otherFile = null;
    	RandomAccessFile myFile = null;
    	try {
    		otherFile = new RandomAccessFile( other.getFile(), "r");
    		myFile = new RandomAccessFile( getFile(), "r" );
    		byte [] otherBuffer = new byte[1024];
    		byte [] myBuffer = new byte[1024];
    		
    		for (int i = 0; i < ( other.getLength() / 1024 ); i++) {
    			otherFile.readFully(otherBuffer);
    			myFile.readFully(myBuffer);
    			// compare buffers.
    			for (int j = 0; j < otherBuffer.length; j++) {
    				if ( otherBuffer[j] != myBuffer[j]) {
    					return false;
    				}
    			}
    		}
    		
    		long otherP = otherFile.getFilePointer();
    		long myP = myFile.getFilePointer();
    		
    		otherFile.readFully( otherBuffer, 0, (int) (other.getLength() - otherP) );
    		myFile.readFully( myBuffer, 0, (int) (getLength() - myP) );
    		
    		for (int j = 0; j < (other.getLength() - otherP); j++) {
				if ( otherBuffer[j] != myBuffer[j]) {
					return false;
				}
			}
    		
    		return true;
    		
    	} catch (Exception e) {
    		e.printStackTrace(System.err);
    		return false;
    	} finally {
    		if ( otherFile != null ) {
    			try {
    				otherFile.close();
    			} catch (IOException e) {
    				
    			}
    		}
    		if ( myFile != null ) {
    			try {
    				myFile.close();
    			} catch (IOException e) {
    				
    			}
    		}
    	}
    }


    
    /**
     * Custom deserialization is needed.
     */
    private void readObject(ObjectInputStream aStream) throws IOException, ClassNotFoundException {
    	aStream.defaultReadObject();
    	//manually deserialize
    	loadBinaryFromStream(aStream, false);
    }

     /**
     * Custom serialization is needed.
     */
    private void writeObject(ObjectOutputStream aStream) throws IOException {
    	aStream.defaultWriteObject();
    	//manually serialize superclass
    	if ( dataFile != null ) {
    		copyResource("", aStream, new FileInputStream(dataFile), 0);
    	} else if ( lazySourceFile != null ) {
    		copyResource("", aStream, new FileInputStream(lazySourceFile), 0);
    	}
    	
    }
     
    public static void main(String [] args) throws Exception {
    	Binary b1 = new Binary( new File("/home/arjen/Pictures/photo.jpg" ), false );
    	
    	Navajo n = NavajoFactory.getInstance().createNavajo();
    	Message m = NavajoFactory.getInstance().createMessage(n, "Test");
    	n.addMessage(m);
    	Property p = NavajoFactory.getInstance().createProperty(n, "Photo", Property.BINARY_PROPERTY, null, 0, "", "");
    	p.setValue(b1);
    	m.addProperty(p);
    	
    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/home/arjen/serialized"));
    	oos.writeObject(n);
    	oos.close();
    	
    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/home/arjen/serialized"));
    	Navajo n2 = (Navajo) ois.readObject();
    	
    	n2.write(System.err);
    	
    	
    	
    }    	
}