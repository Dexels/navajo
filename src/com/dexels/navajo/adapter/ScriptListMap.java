package com.dexels.navajo.adapter;

import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.Mappable;
import java.util.*;
import java.io.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ScriptListMap implements Mappable {
  private ArrayList scripts = new ArrayList();
  private File scriptDir;
  public ScriptEntryMap[] scriptlist;
  public ScriptListMap() {
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    String scriptPath = config.getScriptPath();
    scriptDir = new File(scriptPath);
    if(scriptDir.isDirectory()){
      getScripts(scriptDir);
    }
  }

  public ScriptEntryMap[] getScriptlist(){
    //implement
    ScriptEntryMap[] scriptMaps = new ScriptEntryMap[scripts.size()];
    for(int i=0;i<scripts.size();i++){
      scriptMaps[i] = (ScriptEntryMap)scripts.get(i);
    }
    return scriptMaps;
  }

  private void getScripts(File dir){
    File[] files = dir.listFiles();
    for(int i=0;i<files.length;i++){
      File cur = files[i];
      if(cur.isDirectory()){
        getScripts(cur);
      }else{
        if(cur.getName().endsWith(".xml")){
          ScriptEntryMap map = new ScriptEntryMap();
          if(dir.getName().equals(scriptDir.getName())){
            map.name = cur.getName();
            map.date = new Date(cur.lastModified());
          }else{
            map.name = dir.getName() + "/" + cur.getName();
            map.date = new Date(cur.lastModified());
          }
          scripts.add(map);
        }
      }
    }
  }

  public void store() throws MappableException, UserException {
    //nip
  }
  public void kill() {
    //nip
  }

//  public static void main(String[] args){
//    File f = new File("c:/projecten/sportlink-serv/navajo-tester/auxilary/scripts");
//    ScriptListMap l = new ScriptListMap();
//    l.getScripts(f);
//  }

}