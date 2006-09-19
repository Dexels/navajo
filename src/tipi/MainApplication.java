package tipi;

import java.util.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import javax.swing.UIManager.*;
import java.io.*;
import com.dexels.navajo.swingclient.*;

public class MainApplication {

  static public void main(String[] args) throws Exception {
    long startupTime = System.currentTimeMillis();
    if (args.length < 1) {
      System.err.println(
          "Usage: tipi [-studio | -classic] <url to tipidef.xml>");
      return;
    }
    try {
        Locale.setDefault(new Locale("nl","NL"));
        if (System.getProperty("com.dexels.navajo.DocumentImplementation")==null) {
            System.setProperty("com.dexels.navajo.DocumentImplementation",
            "com.dexels.navajo.document.base.BaseNavajoFactoryImpl");
            
        }
        System.setProperty("com.dexels.navajo.propertyMap", "tipi.propertymap");
        if (System.getProperty("tipilaf")==null) {
//            System.err.println("No supplied laf. Using default: "+UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());    
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } else {
            System.err.println("Found supplied laf: "+System.getProperty("tipilaf"));
            UIManager.setLookAndFeel(System.getProperty("tipilaf"));    
            
        }
    }
    catch(SecurityException se) {
        System.err.println("Security exception: "+se.getMessage());
        se.printStackTrace();
    }    
    checkForProperties(args);
    
    UIManager.put("Button.showMnemonics", Boolean.TRUE);
    boolean studiomode = false;
//    boolean classicmode = args[0].equals("-classic");
    boolean classicstudiomode = false;
    boolean debugMode = false;
    try {
        String debugStr = System.getProperty("com.dexels.navajo.tipi.debugMode");
          debugMode = debugStr!=null && debugStr.equals("true");
    }
    catch(SecurityException se) {
        System.err.println("Security exception: "+se.getMessage());
        se.printStackTrace();
    }    
    TipiContext context = null;

    
      context = new SwingTipiContext();
      SwingTipiUserInterface stui = new SwingTipiUserInterface((SwingTipiContext)context);
      SwingClient.setUserInterface(stui);

      context.setResourceBaseDirectory(new File("resource"));
      
      context.setDefaultTopLevel(new TipiScreen());
      context.getDefaultTopLevel().setContext(context);
      ((SwingTipiContext)context).setDebugMode(debugMode);
//      System.err.println("Opening: " +
//                         context.getResourceURL(args[args.length - 1]));
      context.parseRequiredIncludes();
      context.parseURL(context.getResourceURL(args[args.length - 1]),false);
//    }
//    long diff = System.currentTimeMillis()-startupTime;
//    System.err.println("\n\n*************8 bootup: "+diff+"\n");
  }

  private static void checkForProperties(String[] args) {
    for (int i = 0; i < args.length; i++) {
      String current = args[i];
      if (current.startsWith("-D")) {
        String prop = current.substring(2);
        try {
          StringTokenizer st = new StringTokenizer(prop, "=");
          String name = st.nextToken();
          String value = st.nextToken();
          System.setProperty(name, value);
          String verify = System.getProperty(name);
          if (value.equals(verify)) {
          }
          else {
            throw new RuntimeException(
                "Error: System property set, but it did not really stick. Value: " +
                verify + " expected: " + value);
          }
        }
        catch (NoSuchElementException ex) {
          System.err.println("Error parsing system property");
        } catch(SecurityException se) {
            System.err.println("Security exception: "+se.getMessage());
            se.printStackTrace();
        }
      }
    }
  }
}
