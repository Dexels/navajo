package com.dexels.navajo.tipi.components.swingimpl.embed;

import javax.swing.JPanel;
import java.io.IOException;
import com.dexels.navajo.tipi.TipiException;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.swingclient.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiStandaloneContainer extends JPanel {

  private EmbeddedContext embeddedContext = null;
  private final ArrayList libraries = new ArrayList();
  private UserInterface ui = null;

  public TipiStandaloneContainer() {
  }

  public void setUserInterface(UserInterface u) {
    ui = u;
    if (embeddedContext!=null) {
      embeddedContext.setUserInterface(u);
    }
  }

  public void loadDefinition(String tipiPath, String definitionName) throws IOException, TipiException {
    embeddedContext = new EmbeddedContext(new String[]{tipiPath},false,new String[]{definitionName},libraries);
    if (ui!=null) {
      embeddedContext.setUserInterface(ui);
    }
  }
  public void loadDefinition(String tipiPath[], String[] definitionName) throws IOException, TipiException {
    embeddedContext = new EmbeddedContext(tipiPath,false,definitionName,libraries);
    if (ui!=null) {
      embeddedContext.setUserInterface(ui);
    }
  }

  public void loadClassPathLib(String location) {
    libraries.add(location);
  }

  public void loadNavajo(Navajo nav,String method)  throws TipiException, TipiBreakException {
    embeddedContext.loadNavajo(nav,method);
  }

  public SwingTipiContext getContext() {
    return embeddedContext;
  }

  public void shutDownTipi() {
    embeddedContext.shutdown();
  }

  public static void main(String[] args) throws Exception{
    JFrame jf = new JFrame("Test frame");
    jf.setSize(new Dimension(500,300));
    jf.setVisible(true);
    TipiStandaloneContainer stc = new TipiStandaloneContainer();
    jf.getContentPane().setLayout(new BorderLayout());
    System.setProperty("com.dexels.navajo.propertyMap", "tipi.propertymap");

    stc.loadClassPathLib("com/dexels/navajo/tipi/components/swingimpl/swingclassdef.xml");
    stc.loadClassPathLib("com/dexels/navajo/tipi/classdef.xml");
    stc.loadDefinition("tipi/FinancialForm.xml","FinancialForm");
    jf.getContentPane().add((Container)stc.getContext().getTopLevel(),BorderLayout.CENTER);
  }

}
