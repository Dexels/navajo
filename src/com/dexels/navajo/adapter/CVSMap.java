package com.dexels.navajo.adapter;

import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.Mappable;
import java.io.*;
import org.netbeans.lib.cvsclient.command.*;
import org.netbeans.lib.cvsclient.connection.*;
import org.netbeans.lib.cvsclient.*;
import org.netbeans.lib.cvsclient.admin.*;
import org.netbeans.lib.cvsclient.event.*;
import org.netbeans.lib.cvsclient.command.status.*;
import org.netbeans.lib.cvsclient.command.log.*;
import java.util.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class CVSMap implements Mappable,CVSListener {
  GlobalOptions globalOptions;
  private Client client;
  private String cvsDir;
  private File scriptDir;
  public ScriptEntryMap[] scriptlist;
  private final Map fileNameMap = new HashMap();
  private final ArrayList scripts = new ArrayList();

  public CVSMap() {
  }

  public void load(Parameters parms, Navajo inMessage, Access access,NavajoConfig config) throws MappableException, UserException {
    cvsDir = config.getScriptPath();
    Message auth = inMessage.getMessage("QueryScriptList");
    if(auth == null){
      return;
    }

    String user = auth.getProperty("Username").getValue();
    String pass = auth.getProperty("Password").getValue();
    pass = StandardScrambler.getInstance().scramble(pass);
    if(user == null || "".equals(user)){
      System.err.println("---- NO USER ----");
      return;
    }

    String root = determineRoot(cvsDir);

    PServerConnection connection = createCVSConnection(root, user, pass);
    if (connection != null) {
      client = new Client(connection, new StandardAdminHandler());
      client.setLocalPath(cvsDir);
      client.getEventManager().addCVSListener(this);
    }
    loadMap();
    scriptDir = new File(cvsDir);
    if(scriptDir.isDirectory()){
      getScripts(scriptDir);
    }
  }

  public ScriptEntryMap[] getScriptlist(){
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
            map.name = cur.getName().substring(0, cur.getName().lastIndexOf("."));
            map.isinit = cur.getName().startsWith("Init");
            map.date = new Date(cur.lastModified());
            StatusInformation info = (StatusInformation) fileNameMap.get(map.name+".xml");
            if(info != null){
              map.status = info.getStatusString();
              map.revision = info.getWorkingRevision();
            }
          }else{
            map.name = dir.getName() + "/" + cur.getName().substring(0, cur.getName().lastIndexOf("."));
            map.isinit = cur.getName().startsWith("Init");
            map.date = new Date(cur.lastModified());
            StatusInformation info = (StatusInformation) fileNameMap.get(map.name+".xml");
            if(info != null){
              map.status = info.getStatusString();
              map.revision = info.getWorkingRevision();
            }
          }
          scripts.add(map);
        }
      }
    }
  }

  private void loadMap(){
    try{
      fileNameMap.clear();
      StatusCommand command = new StatusCommand();
      StatusBuilder sb = new StatusBuilder(client.getEventManager(), command);
      command.setBuilder(sb);
      command.setRecursive(true);
      client.executeCommand(command, globalOptions);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private String determineRoot(String path) {
    String root = null;
    BufferedReader r = null;
    try {
      File f = new File(path);
      File rootFile = new File(f, "CVS/Root");
      if (rootFile.exists()) {
        r = new BufferedReader(new FileReader(rootFile));
        root = r.readLine();
      }
    }
    catch (IOException e) {
      // ignore
    }
    finally {
      try {
        if (r != null) {
          r.close();
        }
      }
      catch (IOException e) {
        System.err.println("Warning: could not close CVS/Root file!");
      }
    }
    if (root == null) {
      root = System.getProperty("cvs.root");
    }
    System.err.println("----> CVSRoot: " + root);
    return root;
  }

  private PServerConnection createCVSConnection(String root, String user,
                                                String password) {
    try {
      globalOptions = new GlobalOptions();
      globalOptions.setCVSRoot(root);
      CVSRoot cvsroot = new CVSRoot(root);
      PServerConnection c = new PServerConnection();
      c.setUserName(user);
      c.setEncodedPassword(password);
      c.setHostName(cvsroot.host);
      c.setRepository(cvsroot.repository);
      c.open();
      return c;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public void messageSent(MessageEvent e) {
    String line = e.getMessage();
    StringBuffer taggedLine = new StringBuffer();

    if (e.isTagged()) {
      String message = e.parseTaggedMessage(taggedLine, line);
      // if we get back a non-null line, we have something
      // to output. Otherwise, there is more to come and we
      // should do nothing yet.
      if (message != null) {
//        System.err.println(message);
      }
    }
    else {
//      System.err.println(line);
    }
  }

  public void fileInfoGenerated(FileInfoEvent e) {
    try{
//      System.err.println("Starting to parser FileInfoEvent... " + e.toString());
      StatusInformation info = (StatusInformation) e.getInfoContainer();
      File f = e.getInfoContainer().getFile();
      File cvsDirFile = new File(cvsDir);
      String cvsDirPath = cvsDirFile.getAbsolutePath();
      String filePath = f.getPath().toString();
      String replacedPath = replaceString(filePath, "\\", "/");
      fileNameMap.put(replacedPath, info);
    }catch(Exception ex){
      ex.printStackTrace();
    }

  }
  public void fileUpdated(FileUpdatedEvent e) {
    System.err.println("Updated: " + e.getFilePath());
  }
  public void fileRemoved(FileRemovedEvent e) {
    System.err.println("Removed: " + e.getSource().getClass());
  }
  public void fileAdded(FileAddedEvent e) {
    System.err.println("Added: " + e.getFilePath());
  }

   public void moduleExpanded(ModuleExpansionEvent e) {
    //aap
   }
   public void commandTerminated(TerminationEvent e) {
    //aap
   }

   public String replaceString(String input, String oldValue, String newValue){
     int index = input.indexOf(oldValue);
     if(index > -1){
       String head = input.substring(0, index);
       String tail = input.substring(index + oldValue.length());
       return replaceString(head + newValue + tail, oldValue, newValue);
     }else{
       return input;
     }
   }



  public static void main(String[] args){
    CVSMap m = new CVSMap();
    File f = new File("c:\\dexels\\aap.xml");
    String filePath = f.getPath().toString();
    System.err.println("Replaced: " + filePath + " with: " + m.replaceString(filePath, "\\", "/"));
  }




























  public void store() throws MappableException, UserException {
    //
  }

  public void kill() {
    //
  }

































  /**
   * A struct containing the various bits of information in a CVS root
   * string, allowing easy retrieval of individual items of information
   */
  private static class CVSRoot {
    public String connectionType;
    public String user;
    public String host;
    public String repository;

    public CVSRoot(String root) throws IllegalArgumentException {
      if (!root.startsWith(":")) {
        throw new IllegalArgumentException();
      }

      int oldColonPosition = 0;
      int colonPosition = root.indexOf(':', 1);
      if (colonPosition == -1) {
        throw new IllegalArgumentException();
      }
      connectionType = root.substring(oldColonPosition + 1, colonPosition);
      oldColonPosition = colonPosition;
      colonPosition = root.indexOf('@', colonPosition + 1);
      if (colonPosition == -1) {
        throw new IllegalArgumentException();
      }
      user = root.substring(oldColonPosition + 1, colonPosition);
      oldColonPosition = colonPosition;
      colonPosition = root.indexOf(':', colonPosition + 1);
      if (colonPosition == -1) {
        throw new IllegalArgumentException();
      }
      host = root.substring(oldColonPosition + 1, colonPosition);
      repository = root.substring(colonPosition + 1);
      if (connectionType == null || user == null || host == null ||
          repository == null) {
        throw new IllegalArgumentException();
      }
    }
  }





}
