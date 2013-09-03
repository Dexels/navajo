package com.dexels.navajo.document.types;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PushbackReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import org.dexels.utils.Base64;
import org.dexels.utils.Base64DecodingFinishedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.metadata.FormatDescription;
import com.dexels.navajo.document.saximpl.qdxml.QDParser;

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
    private String mimetype = null; //"text/plain";

    private transient File dataFile = null;
    private transient File lazySourceFile = null;
//    private transient InputStream lazyInputStream = null;
    private String myRef = null;
    private String extension = null;
    
    private byte[] inMemory = null;
    
    public final static String MSEXCEL = "application/msexcel";

    public final static String MSWORD = "application/msword";

    public final static String PDF = "application/pdf";

    public final static String GIF = "image/gif";

    public final static String TEXT = "plain/text";

    private FormatDescription currentFormatDescription;

	private transient MessageDigest messageDigest;

	private byte[] digest;
    
    private final static HashMap<String,Binary> persistedBinaries = new HashMap<String,Binary>();
    
    private static final Logger logger = LoggerFactory.getLogger(Binary.class);
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
        createDigest();
        if (lazy) {
        	throw new UnsupportedOperationException("Constructing lazy binary based on a stream. This is not working.");
//            lazyInputStream = is;
        } else {
            try {
                loadBinaryFromStream(is);
            } catch (Exception e) {
            	logger.error("Error: ", e);
            }
            this.mimetype = guessContentType();
        }
     }

	private void createDigest() {
		try {
			this.messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			logger.warn("Failed creating messageDigest in binary. Expect problems", e1);
		}
	}    
    
    public Binary() {
        super(Property.BINARY_PROPERTY);
        createDigest();
        setMimeType(guessContentType());
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
            throw new IllegalStateException("Can not create tempfile?!",e);
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
        OutputStream os = createTempFileOutputStream();
        messageDigest.reset();
        copyResource(os, is,close);


		setDigest(messageDigest.digest());
//    	if(NavajoFactory.getInstance().isSandboxMode()) {
//    		inMemory = ((ByteArrayOutputStream)os).toByteArray();
//    	}
    	if ( this.mimetype == null ) {
        	this.mimetype = getSubType("mime");
        	this.mimetype = (mimetype == null || mimetype.equals("") ? guessContentType() : mimetype);
        }
        
    }
    
    public int hashCode() {
    	if(getDigest()!=null) {
    		return getHexDigest().hashCode();
    	}
    	return super.hashCode();
    }
    
    public String getHexDigest() {
    	return bytesToHex(getDigest());
    }

    public void setDigest(byte[] digest) {
    	this.digest = digest;
	}

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    
	private OutputStream createTempFileOutputStream() throws IOException, FileNotFoundException {
		if(messageDigest==null) {
			createDigest();
		} else {
			messageDigest.reset();
		}
    	if(NavajoFactory.getInstance().isSandboxMode()) {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream() {
    			public void close() {
    				try {
						super.close();
					} catch (IOException e) {
						logger.error("Error: ", e);
					}
	    			inMemory = toByteArray();
    			}
    		};
    		OutputStream dos = new DigestOutputStream(baos, messageDigest) {

				@Override
				public void close() throws IOException {
					setDigest(messageDigest.digest());
					super.close();
				}
    			
    		};
    		
    		return dos;
    	} else {
            dataFile = File.createTempFile("binary_object", "navajo", NavajoFactory.getInstance().getTempDir());
            
            FileOutputStream fos = new FileOutputStream(dataFile);
            OutputStream dos = new DigestOutputStream(fos, messageDigest) {

				@Override
				public void close() throws IOException {
					super.close();
					setDigest(messageDigest.digest());
				}
            	
            };
            return dos;
    	}
    }
	
	public byte[] getDigest() {
		return this.digest;
	}

    public Binary(File f) throws IOException {
        this(f, true);
    }

    public Binary(File f, boolean lazy) throws IOException {
        super(Property.BINARY_PROPERTY);
        if (lazy) {
            lazySourceFile = f;
        } else {
            loadBinaryFromStream(new FileInputStream(f));
        }
        this.mimetype = guessContentType();
    }
    
//    private final void init(File f, boolean lazy) throws IOException {
//    	 expectedLength = f.length();
//         if (lazy) {
//             lazySourceFile = f;
//         } else {
//             loadBinaryFromStream(new FileInputStream(f));
//         }
//         this.mimetype = guessContentType();
//    }

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
        createDigest();
        // TODO: For sandbox, set inMemory directly to data
        if (data != null) {
            try {
                OutputStream fos = createTempFileOutputStream();
                fos.write(data);
                fos.close();
            } catch (IOException e) {
            	logger.error("Error: ", e);
            }
            
            this.mimetype = guessContentType();
         }
    }

    /**
     * Does NOT close the reader, it is shared with the qdXML parser
     * 
     * @param reader
     * @throws IOException 
     */
    public Binary(Reader reader) throws IOException {
        super(Property.BINARY_PROPERTY);
//        createDigest();
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
		setDigest(messageDigest.digest());
   }


    /**
     * Buffered
     * @param out
     * @param in
     * @throws IOException
     */
    private void copyBufferedBase64Resource(Writer out, PushbackReader in) throws IOException {
        int read;
        int iterations = 0;
        char[] buffer = new char[QDParser.PUSHBACK_SIZE];
        while ((read = in.read(buffer, 0 ,buffer.length)) > -1) {
//            progress +=read;
            iterations++;
            if (iterations % 100 == 0) {
//                logger.info("Reading data. "+progress+"/"+expectedLength);
            }
            int ii = getIndexOf(buffer, '<');
//            logger.info("Buffer size: "+buffer.length+" '<' index: "+ii+" read: "+read);
            if (ii==-1) {
                out.write(buffer,0,read);
            } else {
//                debug("Writing", buffer, 0, ii);
                out.write(buffer, 0, ii);
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
    		if(inMemory!=null) {
    			return inMemory.length;
    		}
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
                	currentFormatDescription = com.dexels.navajo.document.metadata.FormatIdentification.identify(inMemory);
            	} else {
                	currentFormatDescription = com.dexels.navajo.document.metadata.FormatIdentification.identify(f);
            	}
            }
//            logger.info("Guessed: "+currentFormatDescription.getMimeType());
//            logger.info("Guessed: "+currentFormatDescription.getFileExtensions());
            if (currentFormatDescription == null) {
                return "application/unknown";
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
    
    public void setExtension(String e) {
    	this.extension = e;
    }
    
    public String getExtension() {
    	if(extension!=null) {
    		return extension;
    	}
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

		if (inMemory != null) {
			return inMemory;
		}

		File file = null;
		if (lazySourceFile != null) {
			file = lazySourceFile;
		} else {
			file = dataFile;
		}
		RandomAccessFile in = null;
		try {
			if (file != null) {
				in = new RandomAccessFile(file, "r");
				byte[] data = new byte[(int) file.length()];// + 1 ];
				in.readFully(data);
				return data;
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
		return null;

    }

    private final void copyResource(OutputStream out, InputStream in, boolean closeInput) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        try {
			while ((read = in.read(buffer)) > -1) {
			    out.write(buffer, 0, read);
			}
		} finally {
			if(closeInput) {
				in.close();
				out.close();
			}
		}
    }

    public final void write(OutputStream to, boolean close) throws IOException {
        InputStream in = getDataAsStream();
        if (in != null) {
            copyResource(to, in, close);
            try {
                in.close();
            } catch (Exception e) {
                // Don't care
            }
        }
    }

    public final void write(OutputStream to) throws IOException {
        write( to, true );
    }

    public File getFile() {
    	if (lazySourceFile != null) {
    		return lazySourceFile;
    	} else {
    		return dataFile;
    	}
    }
    
    public final InputStream getDataAsStream() {
//    	if (NavajoFactory.getInstance().isSandboxMode()) {
//    		ByteArrayInputStream bais = new ByteArrayInputStream(NavajoFactory.getInstance().getHandle(dataFile.getName()));
//    		return bais;
//    	} else {
    		if(inMemory!=null) {
    			logger.info("Binary: in memory detected bytes: "+inMemory.length);
    			return new ByteArrayInputStream(inMemory);
    		}
	        if (lazySourceFile != null) {
	            try {
	                return new FileInputStream(lazySourceFile);
	            } catch (FileNotFoundException e) {
	            	logger.error("Error: ", e);
	            	return null;
	            }
	        }
	        if (dataFile != null) {
	            try {
	                return new FileInputStream(dataFile);
	            } catch (FileNotFoundException e) {
	            	logger.error("Error: ", e);
	            	return null;
	            }
	        } else {
	            return null;
	        }
//		}
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
    	
    	if(b.getDigest()!=null && getDigest()!=null) {
    		logger.debug("Digests found, comparing those");
    		final String other = b.getHexDigest();
			final String mine = getHexDigest();
			return mine.equals(other);
    	}
    
    	logger.warn("Digest based equality failed. Comparing binary sizes: "+b.getLength()+" with "+getLength());
    	if(b.getLength()!=getLength()) {
    		return false;
    	}
    	logger.info("Comparing binary mime: "+b.getMimeType()+" with "+getMimeType());
    	if(!(getMimeType().equals(b.getMimeType()))) {
    		return false;
    	}
    	logger.info("DUBIOUS EQUALITY IN BINARY!");
    	
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

    public  Binary cloneWithExtension() throws IOException {
    	File f = File.createTempFile("navajo", "."+getExtension());
    	FileOutputStream fos = new FileOutputStream(f);
    	InputStream dataAsStream = getDataAsStream();
		copyResource(fos, dataAsStream,true);
    	fos.flush();
    	dataAsStream.close();
    	fos.close();
    	return new Binary(f,true);
    }
    
    @Override
	public String toString() {
    	if(digest!=null) {
    		return bytesToHex(digest);
    	}
    	return super.toString();
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
	            	logger.error("Error: ", t);
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
			copyResource(os, dataAsStream,true);
			 os.close();
			 dataAsStream.close();
		} catch (IOException e) {
			logger.error("Error: ", e);
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
        	copyResource(os, dataInStream,true);
		}
//        logger.info("Copied to stream");
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
    	logger.info("In removeRef(), myRef = " + myRef);
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
    		logger.error("Error: ", e);
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
    		copyResource(aStream, new FileInputStream(dataFile),true);
    	} else if ( lazySourceFile != null ) {
    		copyResource(aStream, new FileInputStream(lazySourceFile),true);
    	}
    	aStream.flush();
    }
     
    public static void main(String [] args) throws Exception {
    	
    	Navajo doc = NavajoFactory.getInstance().createNavajo();
    	Message m = NavajoFactory.getInstance().createMessage(doc, "Test");
    	doc.addMessage(m);
    	Property p = NavajoFactory.getInstance().createProperty(doc, "Bin", "binary", "", 0, "", "out");
    	m.addProperty(p);
    	Binary b1 = new Binary( new File("responsetimes_290307.xls" ), false );
    	b1.setMimeType("application/excel");
    	p.setValue(b1);
    	
    	FileOutputStream fos = new FileOutputStream(new File("a"));
    	doc.write(fos);
    	fos.close();
    	
//    	doc.write(System.err);
    	
//    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/home/arjen/a")));
//    	oos.writeObject(b1);
//    	oos.close();
//    	
//    	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("/home/arjen/a")));
//    	Binary b2 = (Binary) ois.readObject();
//    	ois.close();
    	
//    	logger.info(b2.getMimeType());
    	
    	FileInputStream fis = new FileInputStream(new File("a"));
    	Navajo doc2 = NavajoFactory.getInstance().createNavajo(fis);
    	fis.close();
    	
    	Binary b2 = (Binary) doc2.getProperty("/Test/Bin").getTypedValue();
    	logger.info(b2.getMimeType());
    	
    }

	public void setFormatDescriptor(FormatDescription fd) {
		currentFormatDescription = fd;
	}    	
}
