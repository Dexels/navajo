package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.UserException;
import java.io.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NavajoFileListAdapter
    implements Mappable {
  private Access access = null;

  public String pathPrefix;

  public String descriptionPath = null;

  public String conditionPath = null;

  public String fileNameFilter = null;

  // a dummy, actually
  public String column = null;

  private final ArrayList columns = new ArrayList();

  public NavajoFileListAdapter() {
  }

  public void setPathPrefix(String p) {
    pathPrefix = p;
  }


  public void setFileNameFilter(String sfnf) {
    fileNameFilter = sfnf;
  }

  public void setColumn(String s) {
    columns.add(s);
  }

  public String getPathPrefix() {
    return pathPrefix;
  }

  public void setConditionPath(String p) {
    conditionPath = p;
  }


  public String getConditionPath() {
    return conditionPath;
  }


  public void setDescriptionPath(String p) {
    descriptionPath = p;
  }


  public String getDescriptionPath() {
    return descriptionPath;
  }


  public void kill() {
  }

  public void load(Access access) throws MappableException, UserException {

//    Property nameProperty = inMessage.getProperty(namePropertyPath);
//    String name = nameProperty.getValue();
    this.access = access;
    columns.clear();
  }

  public void store() throws MappableException, UserException {
//    String totalPath = pathPrefix + fileName;
//    System.err.println("Total: "+totalPath);
    File f = new File(pathPrefix);
    if (!f.exists()) {
      if (!f.mkdirs()) {
        throw new MappableException(
            "Could not open directory, and could also not create it.");
      }
    }
    Navajo n = NavajoFactory.getInstance().createNavajo();
    Message filesMessage = NavajoFactory.getInstance().createMessage(n,"Files",Message.MSG_TYPE_ARRAY);
    try {
      n.addMessage(filesMessage);
    }
    catch (NavajoException ex2) {
      ex2.printStackTrace();
    }
    File[] files;
    if (fileNameFilter!=null) {
      FilenameFilter ff = new FilenameFilter() {
        public boolean accept(File f, String s) {
          return s.endsWith(fileNameFilter);
        }
      };
      files = f.listFiles(ff);
    } else {
      files = f.listFiles();
    }
    for (int i = 0; i < files.length; i++) {
      try {
        Message m = createFileMessage(filesMessage, files[i], descriptionPath);
        if (m!=null) {
          filesMessage.addMessage(m);
        }
      }
      catch (NavajoException ex1) {
        ex1.printStackTrace();
      }
    }
    access.setOutputDoc(n);
  }

  private final void copyResource(OutputStream out, InputStream in) throws IOException{
      BufferedInputStream bin = new BufferedInputStream(in);
      BufferedOutputStream bout = new BufferedOutputStream(out);
      byte[] buffer = new byte[1024];
      int read;
      while ((read = bin.read(buffer)) > -1) {
        bout.write(buffer,0,read);
      }
      bin.close();
      bout.flush();
      bout.close();
  }



private Message createFileMessage(Message parent, File entry, String pathToDescription) throws NavajoException {
  Message m = NavajoFactory.getInstance().createMessage(parent.getRootDoc(),parent.getName(),Message.MSG_TYPE_ARRAY_ELEMENT);
  Property filePath = NavajoFactory.getInstance().createProperty(parent.getRootDoc(),"File",Property.STRING_PROPERTY,entry.toString(),0,"",Property.DIR_OUT);
  Property fileFullPath = NavajoFactory.getInstance().createProperty(parent.getRootDoc(),"FilePath",Property.STRING_PROPERTY,entry.getAbsoluteFile().toString(),0,"",Property.DIR_OUT);
  Property description = NavajoFactory.getInstance().createProperty(parent.getRootDoc(),"Description",Property.STRING_PROPERTY,"No description",0,"",Property.DIR_OUT);
  Property fileSize = NavajoFactory.getInstance().createProperty(parent.getRootDoc(),"Size",Property.INTEGER_PROPERTY,""+entry.length(),0,"",Property.DIR_OUT);

  if (pathToDescription!=null || conditionPath!=null || !columns.isEmpty()) {
    try {

      System.err.println("Reading description. Might be slow");
      System.err.println("Entry: "+entry);


      FileInputStream fis = new FileInputStream(entry);
      Navajo n = NavajoFactory.getInstance().createNavajo(fis);
      fis.close();
          Property desc = n.getProperty(pathToDescription);

          if (conditionPath!=null) {
            System.err.println("Evaluating: "+conditionPath);
            Operand o = Expression.evaluate(conditionPath, n);
            System.err.println("Result: "+o.value);
            Boolean b = (Boolean)o.value;
            if (!b.booleanValue()) {
              return null;
            }
          }

      for (int i = 0; i < columns.size(); i++) {
        String current = (String)columns.get(i);
        Property p = n.getProperty(current);
        if (p!=null) {
//          Property q = (Property)p.clone();
          Property q = NavajoFactory.getInstance().createProperty(parent.getRootDoc(),"Column"+i,p.getType(),p.getValue(),p.getLength(),p.getDescription(),p.getDescription());
//          q.setName("Column"+i);
          m.addProperty(q);
        }
      }
      if (desc!=null) {
        description.setValue(desc.getValue());
      }


    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  m.addProperty(filePath);
  m.addProperty(fileFullPath);
  m.addProperty(description);
  m.addProperty(fileSize);

  return m;
}

}
