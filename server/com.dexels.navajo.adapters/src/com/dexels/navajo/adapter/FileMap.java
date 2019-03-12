/*
 * Created on May 17, 2005
 *
 */
package com.dexels.navajo.adapter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.filemap.FileLineMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

/**
 * @author arjen
 *
 */
public class FileMap implements Mappable {

	public String fileName;
	public String charsetName = null;
	public String separator;

	public FileLineMap line;
	public FileLineMap[] lines;

	public boolean persist = true;
	public boolean exists  = false;
	public boolean dosMode = false;

	public Binary content;
	
	private static final Logger logger = LoggerFactory.getLogger(FileMap.class);
	
	private ArrayList<FileLineMap> lineArray = null;

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#load(com.dexels.navajo.server.Parameters, com.dexels.navajo.document.Navajo, com.dexels.navajo.api.Access, com.dexels.navajo.server.NavajoConfig)
	 */
	@Override
	public void load(Access access) throws MappableException, UserException {

	}

	private byte[] getBytes() throws IOException  {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		for (int i = 0; i < lineArray.size(); i++) {
			FileLineMap flm = lineArray.get(i);

			if (flm != null) {
				String nextLine = handleLineEnds( flm.getLine() );

				baos.write( ( charsetName == null ) ? nextLine.getBytes() : nextLine.getBytes( charsetName ) );
			}
		}
		baos.close();
		return baos.toByteArray();
	}

	// TODO Should be streaming, easy rewrite
	public Binary getContent() throws UserException {
		try {
			Binary b = new Binary(getBytes());
			b.setMimeType("application/text");
			return b;
		} catch (Exception e) {
			throw new UserException(-1, e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#store()
	 */
	@Override
	public void store() throws MappableException, UserException {
		if (persist && fileName != null) {
			File f = new File(fileName);
			if (f.exists()) {
				logger.info("Deleting existing file");
				try {
					Files.delete(f.toPath());
				} catch (IOException e) {
					throw new UserException(-1, "Error deleting existing file at path: "+f.getAbsolutePath(),e);
				}
			}
			try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f))) {
				bos.write(getBytes());
			} catch (IOException e) {
				throw new UserException(-1, e.getMessage(),e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.script.api.Mappable#kill()
	 */
	@Override
	public void kill() {
	}
	
    public void setMessage(Object o) throws MappableException {
        if (o instanceof Message) {
            Message arrraymessage = (Message) o;
            if (!arrraymessage.getType().equals(Message.MSG_TYPE_ARRAY)) {
                throw new MappableException("SetMssage only accepts array message");
            }
            for (Message m : arrraymessage.getElements()) {
                FileLineMap line = new FileLineMap();
                line.setSeparator(separator);
                for (Property p : m.getAllProperties()) {
                    line.setColumn(p.getTypedValue().toString());
                }
                setLine(line);
            }
        } else if (o instanceof List) {
            @SuppressWarnings("rawtypes")
            List maps = (List) o;
            for (Object mapobject : maps) {
                if (mapobject instanceof com.dexels.navajo.adapter.navajomap.MessageMap) {
                    com.dexels.navajo.adapter.navajomap.MessageMap map = (com.dexels.navajo.adapter.navajomap.MessageMap) mapobject;
                    FileLineMap line = new FileLineMap();
                    line.setSeparator(separator);
                    for (Property p : map.getMsg().getAllProperties()) {
                        if (p.getName().equals("__id")) {
                            continue;
                        }
                        if (p.getTypedValue() != null) {
                            line.setColumn(p.getTypedValue().toString());
                        } else {
                            line.setColumn("");
                        }
                    }
                    setLine(line);
                }
            }
        } else {
            throw new MappableException("SetMessage only accepts array message or List, but got a " + o.getClass());
        }
    }

	public void setLines(FileLineMap[] l) {
		if (lineArray == null) {
			lineArray = new ArrayList<>();
		}
		for (int i = 0; i < l.length; i++) {
			lineArray.add(l[i]);
		}
	}

	public void setLine(FileLineMap l) {
		if (lineArray == null) {
			lineArray = new ArrayList<>();
		}
		lineArray.add(l);
	}

	public void setFileName(String f) {
		this.fileName = f;
	}

	public void setCharsetName(String charset) {
		this.charsetName = charset;
	}

	public void setDosMode( boolean mode ) {
		this.dosMode = mode;
	}

	public void setContent(Binary b) throws UserException {

		if (fileName == null) {
			throw new UserException(-1, "Set filename before setting content");
		}
		if (b == null) {
			throw new UserException(-1, "No or empty content specified");
		}

		this.content = b;
		try {
			FileOutputStream fo = new FileOutputStream(this.fileName);

			b.write(fo);
			fo.flush();
			fo.close();

			this.fileName = null;
		} catch (Exception e) {
			throw new UserException("Error writing file: "+this.fileName,e);
		}
	}

	public void setSeparator(String s) {
		this.separator = s;
	}

	public boolean getExists() {
		return new File(fileName).exists();
	}

	private String handleLineEnds( String input ) {
		if ( ! this.dosMode || input.endsWith( "\r\n" ) || ! input.endsWith( "\n" ) )
			return input;

		return input.substring( 0, input.length() - 1 ) + "\r\n";
	}
}
