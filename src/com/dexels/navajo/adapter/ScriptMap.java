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

public final class ScriptMap
    implements Mappable {
  private File scriptFile;
  public String result;
  public ScriptMap() {
  }

  public void load(Parameters parms, Navajo inMessage, Access access,
                   NavajoConfig config) throws MappableException, UserException {
    try {
      String scriptPath = new File(config.getScriptPath()).getCanonicalPath() +  System.getProperty("file.separator");
      System.err.println("Navajo scriptroot: " + scriptPath);
      Message saveScriptMsg = inMessage.getMessage("StoreScript");
      if (saveScriptMsg != null) {

        String name = saveScriptMsg.getProperty("ScriptName").getValue();

        // Return if someone tries to write outside the scriptPath
        //        if(name.indexOf(".") >= 0){
        //          result = "WARNING: The scriptname can not contain the character '.'";
        //          return;
        //        }

        String data = saveScriptMsg.getProperty("ScriptData").getValue();
        byte[] buffer;
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        buffer = dec.decodeBuffer(data);
        File storeScript = new File(scriptPath + name + ".xml");
        if (storeScript.getCanonicalPath().startsWith(scriptPath)) {
          BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(storeScript));
          out.write(buffer, 0, buffer.length);
          out.flush();
          result = "Written: " + storeScript.getCanonicalPath();
          return;
        }
        else {
          result = "ERROR: Can not store outside of the scriptPath";
        }

        return;
      }
      else {
        Property p = inMessage.getProperty("RequestScript/ScriptName");
        if (p != null) {
          scriptFile = new File(scriptPath + p.getValue() + ".xml");
        }
      }
    }
    catch (Exception e) {
      result = "ERROR: Script not stored";
      e.printStackTrace();
    }
  }

  public String getResult() {
    return result;
  }

  public String getScript() throws Exception {

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