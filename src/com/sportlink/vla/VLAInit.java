package com.sportlink.vla;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiInitInterface;
import java.io.*;
import java.net.URL;
import java.sql.*;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Navajo;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class VLAInit implements TipiInitInterface {
  private String resource_dir = "c:/vladb/";

  public VLAInit() {
  }

  public void init(TipiContext context) {
    try{
      File dbProperties = new File(resource_dir + "vla.properties");
      File dbScript = new File(resource_dir + "vla.script");
      // Check if db files are there.
      if (!dbProperties.exists()) {
        context.setSplashInfo("Copying database files");
        dbProperties.getParentFile().mkdirs();
        URL input = getClass().getClassLoader().getResource("ddl/hsql/vla.properties");
        copyResourceFile(dbProperties, input.openStream());
      }
      if (!dbScript.exists()) {
        URL input = getClass().getClassLoader().getResource("ddl/hsql/vla.script");
        copyResourceFile(dbScript, input.openStream());
      }
    }catch(Exception e){
      System.err.println("Error copying files");
      e.printStackTrace();
    }

    try{
      context.setSplashInfo("Creating tables");
//      Class.forName("org.hsqldb.jdbcDriver");

      //Navajo n = context.doSimpleSend(NavajoFactory.getInstance().createNavajo(), "vla/InitVLAQueryMembers");
      //context.enqueueAsyncSend(n, "vla/ProcessVLAQueryMembers");

//      Connection con = DriverManager.getConnection("jdbc:hsqldb:"+ resource_dir + "vla", "sa", "");
//      con.setAutoCommit(true);
//      Statement ps = con.createStatement();
//      String statement = "SELECT * FROM CODETABLE";
//      ResultSet rs = ps.executeQuery(statement);
//      rs.getMetaData();
//      con.close();

    }catch(Exception ex){
      System.err.println("Error creating database");
      ex.printStackTrace();
    }
  }

  public void copyResource(OutputStream out, InputStream in){
    try{
      BufferedInputStream bin = new BufferedInputStream(in);
      BufferedOutputStream bout = new BufferedOutputStream(out);
      byte[] buffer = new byte[1024];
      while (bin.read(buffer) > -1) {
        bout.write(buffer);
      }
      bin.close();
      bout.flush();
      bout.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void copyResourceFile(File out, InputStream in){
    try{
      BufferedReader bin = new BufferedReader(new InputStreamReader(in));
      FileWriter fw = new FileWriter(out);
      String line;
      while((line = bin.readLine()) != null){
        fw.write(line +"\n");
      }
      bin.close();
      fw.flush();
      fw.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }



}