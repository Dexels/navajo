package com.dexels.navajo.tipi.studio.components;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class StudioSystemMessagesPanel extends JPanel  implements Runnable{
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane scrollPane = new JScrollPane();
  JTextArea output = new JTextArea();
  private Thread t;
  private BufferedReader err;

  public StudioSystemMessagesPanel() {
    try {
      jbInit();
//      PipedOutputStream pouterr = new PipedOutputStream();
//      PipedInputStream pinerr = new PipedInputStream(pouterr);
//      err = new BufferedReader(new InputStreamReader(pinerr));
//      System.setErr(new PrintStream(pouterr, true));
//      t = new Thread(this);
//      t.start();
    }
    catch(Exception e) {
      e.printStackTrace(System.out);
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(scrollPane, BorderLayout.CENTER);
    scrollPane.getViewport().add(output, null);
  }

  public void run(){
    log();
  }

  public void log(){
    try {
      String errLine = "";
      while(true){
        errLine = err.readLine();
        output.append(errLine+"\n");
        System.out.println("errLine: " + errLine);
        //output.append(outLine);
      }
    }catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}