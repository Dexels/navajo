package tipi;

import java.util.*;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

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
    TipiContext context = new TipiContext();
    boolean studiomode = args[0].equals("-studio");
    boolean classicmode = args[0].equals("-classic");

    context.setStudioMode(studiomode);
    if (studiomode) {
      TipiSwingSplash dts = new TipiSwingSplash("tipi/splash_studio.jpg");
      dts.show();
      context.setSplash(dts);
      System.err.println("Opening: " + args[args.length - 1]);
      System.setProperty("com.dexels.navajo.propertyMap","com.dexels.navajo.tipi.studio.propertymap");

//     myContext.parseURL(TipiContext.getInstance().getResourceURL(args[args.length - 1]));
//      context.parseURL(ClassLoader.getSystemClassLoader().getResource("com/dexels/navajo/tipi/studio/studiolibs.xml"));
      context.parseFile(args[args.length - 1]);
      dts.setVisible(false);
    }
    else {
      if (classicmode) {
        System.err.println("Opening: " + context.getResourceURL(args[args.length - 1]));
        context.parseURL(context.getResourceURL(args[args.length - 1]));
      } else {
        context.parseFile(args[args.length - 1]);

      }

    }
  }

  private static void checkForProperties(String[] args) {
    for (int i = 0; i < args.length; i++) {
      String current = args[i];
      if (current.startsWith("-D")) {
        String prop = current.substring(2);
//        System.err.println("System property found: "+prop);
        try {
          StringTokenizer st = new StringTokenizer(prop, "=");
          String name = st.nextToken();
          String value = st.nextToken();
          System.setProperty(name,value);
          String verify = System.getProperty(name);
          if (value.equals(verify)) {
//            System.err.println("Verify ok");
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