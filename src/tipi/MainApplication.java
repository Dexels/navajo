package tipi;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import java.io.*;
import com.dexels.navajo.tipi.tipixml.*;
import java.util.ResourceBundle;

public class MainApplication {

  static public void main(String[] args) throws Exception {
    if (args.length<1) {
      System.err.println("Usage: tipi [-studio] <url to tipidef.xml>");
      return;
    }
    System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
    System.setProperty("com.dexels.navajo.propertyMap", "tipi.propertymap");
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    UIManager.put("Button.showMnemonics", Boolean.TRUE);
    TipiContext context = TipiContext.getInstance();
    boolean studiomode = args[0].equals("-studio");
    context.setStudioMode(studiomode);
    if (studiomode) {
      DefaultTipiSplash dts = new DefaultTipiSplash("splash_studio.jpg");
      dts.show();
      context.setSplash(dts);
    } else {
      DefaultTipiSplash dts = new DefaultTipiSplash("splash.jpg");
      dts.show();
      context.setSplash(dts);
    }


//    context.setResourceURL(MainApplication.class.getResource(""));

//    System.err.println("Opening: "+ TipiContext.getInstance().getResourceURL(args[0]));
//    TipiContext.getInstance().parseURL(TipiContext.getInstance().getResourceURL(args[0]));
    System.err.println("Opening: "+ args[args.length-1]);
    TipiContext.getInstance().parseFile(args[args.length-1]);

  }

}