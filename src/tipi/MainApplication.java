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
      System.err.println("Usage: tipi <url to tipidef.xml>");
      return;
    }
    System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
    System.setProperty("com.dexels.navajo.propertyMap", "tipi.propertymap");
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    UIManager.put("Button.showMnemonics", Boolean.TRUE);
    DefaultTipiSplash dts = new DefaultTipiSplash();
    dts.show();
    TipiContext context = TipiContext.getInstance();
    context.setSplash(dts);
//    context.setResourceURL(MainApplication.class.getResource(""));

    System.err.println("Opening: "+ TipiContext.getInstance().getResourceURL(args[0]));
    TipiContext.getInstance().parseURL(TipiContext.getInstance().getResourceURL(args[0]));
  }

  static public void loadXML(String fileName){
    System.err.println("loadXML: " + fileName);
    TipiContext context = TipiContext.getInstance();
    context.closeAll();
//    context.setResourceURL(MainApplication.class.getResource(""));
    try {
      context.parseURL(MainApplication.class.getResource(fileName));
    }
    catch (IOException ex) {
      System.err.println("Whoops!!");
    }
    catch (XMLParseException ex) {
      System.err.println("Whuups!");
    }
    catch (TipiException ex) {
      System.err.println("Whaaps!!");
    }
  }
}