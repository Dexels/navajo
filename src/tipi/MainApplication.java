package tipi;

import java.util.*;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import javax.swing.UIManager.*;
import java.io.*;

public class MainApplication {

  static public void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.err.println(
          "Usage: tipi [-studio | -classic] <url to tipidef.xml>");
      return;
    }

    Locale.setDefault(new Locale("nl","NL"));
    System.setProperty("com.dexels.navajo.DocumentImplementation",
                       "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
    System.setProperty("com.dexels.navajo.propertyMap", "tipi.propertymap");
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    checkForProperties(args);
    UIManager.put("Button.showMnemonics", Boolean.TRUE);
    boolean studiomode = args[0].equals("-studio");
//    boolean classicmode = args[0].equals("-classic");
    boolean classicstudiomode = args[0].equals("-web");


    boolean debugMode = false;
    System.err.println("Deb: "+debugMode);

        debugMode = Boolean.getBoolean("com.dexels.navajo.tipi.debugMode");

    TipiContext context = null;

    if (studiomode || classicstudiomode) {
      Class c = Class.forName("com.dexels.navajo.tipi.studio.StudioTipiContext");
      context = (TipiContext)c.newInstance();
       context.setStudioMode(true);
      TipiSwingSplash dts = new TipiSwingSplash(
          "com/dexels/navajo/tipi/studio/images/studio-splash.png");
      dts.show();
      System.setProperty("com.dexels.navajo.propertyMap",
                         "com.dexels.navajo.tipi.studio.propertymap");

      ((SwingTipiContext)context).setDebugMode(debugMode);
      context.parseStudio();

//        context.parseURL(context.getResourceURL(args[args.length - 1]));
      dts.setVisible(false);
//      context.parseFile(args[args.length - 1]);
    }
    else {
      System.err.println("Starting non-studio mode");
      context = new SwingTipiContext();
      context.setDefaultTopLevel(new TipiScreen());
      context.getDefaultTopLevel().setContext(context);
      ((SwingTipiContext)context).setDebugMode(debugMode);

      System.err.println("Opening: " +
                         context.getResourceURL(args[args.length - 1]));
      context.parseURL(context.getResourceURL(args[args.length - 1]),false);
    }

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
        }
      }
    }
  }
}
