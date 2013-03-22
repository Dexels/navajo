package com.dexels.navajo.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NavajoSaveAdapter
    implements Mappable {

  private Navajo inMessage = null;
  
private final static Logger logger = LoggerFactory
		.getLogger(NavajoSaveAdapter.class);



  public NavajoSaveAdapter() {
  }

  public String pathPrefix;
  public String fileNamePropertyPath;
  public String fileName;
  public boolean overwrite = false;
  public boolean backup = false;

  public Binary data = null;
  public void setPathPrefix(String p) {
    pathPrefix = p;
  }

  public void setFileName(String fn) {
    fileName = fn;
  }

  /**
   * This adapter is sort of overloaded:
   * If no data binary is supplied, it will write the incoming Navajo.
   * If binary data is supplied, it will assume it contains the Navajo
   * object, disguised as a byte array
   */

  public void setData(Binary data) {
    this.data = data;
  }

  public void setFileNamePropertyPath(String p) {
    fileNamePropertyPath = p;
  }

  public String getPathPrefixPropertyPath() {
    return pathPrefix;
  }

  public void setOverwrite(boolean b) {
    overwrite = b;
  }
/**
 * If set to true, it will:
 * If the path already exists, it will rename the old one, by appending a '.old'
 * suffix.
 * If set to false, it will overwrite the old file.
 */
  public void setBackup(boolean b) {
    backup = b;
  }


  public String getFileNamePropertyPath() {
    return fileNamePropertyPath;
  }


  public void kill() {
  }

  public void load(Access access) throws MappableException, UserException {
    this.inMessage = access.getInDoc();
   }

  public void store() throws MappableException, UserException {
    logger.debug("fileNamePropertyPath: "+fileNamePropertyPath);
    logger.debug("Using prefix: "+pathPrefix);

     String totalPath = null;
     if (fileName==null) {
       logger.debug("No explicit filename found:");
       Property fileNameProp =  inMessage.getProperty(fileNamePropertyPath);
       totalPath = pathPrefix+fileNameProp.getValue();
     } else {
       logger.debug("Explicit path found: "+fileName);
       totalPath = pathPrefix+fileName;
     }
     logger.debug("Resolved path to: "+totalPath);
     File path = new File(totalPath);
     if (path.exists() && !overwrite) {
       throw new UserException(-1, "File exists");
     }
     if (path.exists() && backup) {
       path.renameTo(new File(totalPath+".old"));
     }

     File parentFile = path.getParentFile();
     if (!parentFile.exists()) {
       parentFile.mkdirs();
     }

     if (data!=null) {
       logger.debug("Data found");
       FileOutputStream fw = null;
      try {
        fw = new FileOutputStream(path);
        data.write(fw);
//        fw.write(data.getData());
        fw.flush();
        fw.close();
      }
      catch (IOException ex1) {
    	  throw new UserException("Can not access file: "+fileName, ex1);
      }
     finally {
       if (fw!=null) {
        try {
          fw.close();
        }
        catch (IOException ex2) {
        	logger.error("Error: ", ex2);
        }
       }
     }

     } else {
       logger.debug("No data found.");
       FileOutputStream fw = null;
       try {
         fw = new FileOutputStream(path);
         inMessage.write(fw);
         fw.close();
       }
       catch (NavajoException ex) {
         throw new UserException(-1,"Error writing Navajo!");
       }
       catch (IOException ex) {
         throw new UserException(-1,"Error writing Navajo!");
       } finally {
         if (fw!=null) {
          try {
            fw.close();
          }
          catch (IOException ex3) {
        	  logger.error("Error: ", ex3);
          }
         }
       }
     }
  }

}
