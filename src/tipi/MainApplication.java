package tipi;

import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import java.io.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.ResourceBundle;
import java.util.*;

public class MainApplication {
  static public void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println("Usage: tipi [-studio | -classic] <url to tipidef.xml>");
      return;
    }
    System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
    System.setProperty("com.dexels.navajo.propertyMap", "tipi.propertymap");
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

    checkForProperties(args);

    UIManager.put("Button.showMnemonics", Boolean.TRUE);
    TipiContext context = TipiContext.getInstance();
    boolean studiomode = args[0].equals("-studio");
//    boolean classicmode = args[0].equals("-classic");

    context.setStudioMode(studiomode);
    if (studiomode) {
      DefaultTipiSplash dts = new DefaultTipiSplash("splash_studio.jpg");
      dts.show();
      context.setSplash(dts);
      System.err.println("Opening: " + args[args.length - 1]);
      TipiContext.getInstance().parseURL(TipiContext.getInstance().getResourceURL(args[args.length - 1]));
//      TipiContext.getInstance().parseFile(args[args.length - 1]);
    }
    else {
        System.err.println("Opening: " + TipiContext.getInstance().getResourceURL(args[args.length - 1]));
        TipiContext.getInstance().parseURL(TipiContext.getInstance().getResourceURL(args[args.length - 1]));
    }
  }

  private static void checkForProperties(String[] args) {
    for (int i = 0; i < args.length; i++) {
      String current = args[i];
      if (current.startsWith("-D")) {
        String prop = current.substring(2);
        System.err.println("System property found: "+prop);
        try {
          StringTokenizer st = new StringTokenizer(prop, "=");
          String name = st.nextToken();
          String value = st.nextToken();
          System.err.println("Setting property: "+name+" to value: "+value);
          System.setProperty(name,value);
          String verify = System.getProperty(name);
          if (value.equals(verify)) {
            System.err.println("Verify ok");
          } else {
            throw new RuntimeException("Error: System property set, but it did not really stick. Value: "+verify+" expected: "+value);
          }
        }
        catch (NoSuchElementException ex) {
          System.err.println("Error parsing systen property");
        }
      }

    }
  }
}