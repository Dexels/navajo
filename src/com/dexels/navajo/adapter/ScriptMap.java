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
import java.util.*;
import com.dexels.navajo.document.jaxpimpl.xml.*;
import org.w3c.dom.*;

/**
 * <p>Title: ScriptMap </p>
 * <p>Description: Map to provide detailed information about a single script.
 *    This Map is part of the Navajo Studio environment.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV </p>
 * @author not attributable
 * @version 1.0
 */

public final class ScriptMap implements Mappable {
  private File scriptFile;
  public String result = "-";
  public String notes = "-";
  public String author = "-";
  public String repository = "-";
  public String id = "-";
  private ArrayList methodList = new ArrayList();
  public ScriptMethodMap[] methodlist;

  public ScriptMap() {
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    try {
      String scriptPath = null;
      scriptPath = new File(config.getScriptPath()).getCanonicalPath() + System.getProperty("file.separator");

      System.err.println("Navajo scriptroot: " + scriptPath);
      Message saveScriptMsg = inMessage.getMessage("StoreScript");

      if (saveScriptMsg != null) {
        String name = saveScriptMsg.getProperty("ScriptName").getValue();
        String data = saveScriptMsg.getProperty("ScriptData").getValue();
        byte[] buffer;
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        buffer = dec.decodeBuffer(data);
        File storeScript = new File(scriptPath + name + ".xml");
        File parent = storeScript.getParentFile();
        if(parent != null){
          parent.mkdirs();
        }
        if (storeScript.getCanonicalPath().startsWith(scriptPath)) {
          BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(storeScript));
          out.write(buffer, 0, buffer.length);
          out.flush();
          result = "Written: " + storeScript.getCanonicalPath();
          return;
        } else {
          result = "ERROR: Can not store outside of the scriptPath";
        }
        return;
      }

      Message requestScriptMessage = inMessage.getMessage("RequestScript");
      if(requestScriptMessage != null) {
        Property p = inMessage.getProperty("RequestScript/ScriptName");
        if (p != null) {
          scriptFile = new File(scriptPath + p.getValue() + ".xml");
          parseMetaData();
        }
        return;
      }

      Message deleteScriptMessage = inMessage.getMessage("DeleteScript");
      if(deleteScriptMessage != null){
        Property p = deleteScriptMessage.getProperty("ScriptName");
        File script = new File(scriptPath + p.getValue() + ".xml");
        if(script.delete()){
          result = "Script deleted";
        }else{
          result = "WARNING: Could not delete script";
        }
        return;
      }
    } catch (Throwable e) {
      result = "ERROR: Script not stored";
      e.printStackTrace();
    }
  }

  public String getResult() {
    return result;
  }

  public String getNotes(){
    return notes;
  }

  public String getId(){
    return id;
  }

  public String getAuthor(){
    return author;
  }

  public String getRepository(){
    return repository;
  }

  public String getScript() {
    try{
      if(scriptFile != null){
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
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    return "WARNING: file not found";
  }

  public ScriptMethodMap[] getMethodlist(){
    methodlist = new ScriptMethodMap[methodList.size()];
    for(int i=0;i<methodlist.length;i++){
      methodlist[i] = new ScriptMethodMap();
      methodlist[i].name = (String)methodList.get(i);
    }
    return methodlist;
  }

  private void parseMetaData(){
    try{
      // Uses JAXPIMPL ======================================================================
      Document doc = XMLDocumentUtils.createDocument(new FileInputStream(scriptFile), false);
      Node tsl = XMLutils.findNode(doc, "tsl");
      if(tsl != null){
        NamedNodeMap nnm = tsl.getAttributes();
        Node nodeAuthor = nnm.getNamedItem("author");
        Node nodeNotes = nnm.getNamedItem("notes");
        Node nodeRepository = nnm.getNamedItem("repository");
        Node nodeId = nnm.getNamedItem("id");
        if(nodeAuthor != null){
          author = nodeAuthor.getNodeValue();
        }
        if(nodeNotes != null){
          notes = nodeNotes.getNodeValue();
        }
        if(nodeRepository != null){
          repository = nodeRepository.getNodeValue();
        }
        if(nodeId != null){
          id = nodeId.getNodeValue();
        }
      }
      Node methods = XMLutils.findNode(doc, "methods");
      if(methods != null && methods.hasChildNodes()){
        NodeList children = methods.getChildNodes();
        for(int i=0;i<children.getLength();i++){
          Node current = children.item(i);
          if(current.hasAttributes()){
            NamedNodeMap attributes = current.getAttributes();
            Node nodeName = attributes.getNamedItem("name");
            if(nodeName != null){
              String name = nodeName.getNodeValue();
              methodList.add(name);
            }
          }
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void setScript(String script){
    System.err.println("Setter called in ScriptMap");
  }

  public void setAuthor(String author){
    System.err.println("Setter called in ScriptMap");
  }

  public void setId(String id){
    System.err.println("Setter called in ScriptMap");
  }

  public void setNotes(String notes){
    System.err.println("Setter called in ScriptMap");
  }

  public void setRepository(String rep){
    System.err.println("Setter called in ScriptMap");
  }

  public void store() throws MappableException, UserException {
    //nip
  }

  public void kill() {
    //nip
  }

}