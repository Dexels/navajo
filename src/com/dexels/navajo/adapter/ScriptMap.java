package com.dexels.navajo.adapter;

import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.document.*;
import java.io.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class ScriptMap implements Mappable {
  private File scriptFile;
  public ScriptMap() {
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    String scriptPath = config.getScriptPath();
    System.err.println("Navajo scriptroot: " + scriptPath);
    Property p = inMessage.getProperty("RequestScript/ScriptName");
    if(p != null){
      scriptFile = new File(scriptPath + p.getValue() + ".xml");
    }
  }

  public String getScript() throws Exception{


    InputStream in = new FileInputStream(scriptFile);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    byte[] data;
    byte[] buffer = new byte[1024];
    int available;
    while ( (available = in.read(buffer)) > -1) {
      bos.write(buffer, 0, available);
    }
    bos.flush();
    data = bos.toByteArray();
    bos.close();
    in.close();

    if (data != null && data.length > 0) {
      sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
      return enc.encode(data);
    }

    return "WARNING: file not found";

  }


  public void store() throws MappableException, UserException {
    //nip
  }
  public void kill() {
    //nip
  }

}