package tipi;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
//import com.dexels.navajo.document.nanoimpl.*;

public class MainApplication {

  static public void main(String[] args) throws Exception {
    if (args.length<1) {
      System.err.println("Usage: tipi <url to tipidef.xml>");
      return;
    }
    System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
//    System.err.println(">>>>>>>>>."+Tipi.class.);
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    DefaultTipiSplash dts = new DefaultTipiSplash();
    dts.show();
    TipiContext context = TipiContext.getInstance();
    context.setSplash(dts);
    context.setResourceURL(MainApplication.class.getResource(""));
//    System.err.println("RES: "+TipiContext.getInstance().getResourceURL());
    new com.dexels.navajo.server.Dispatcher(MainApplication.class.getClassLoader().getResource("server.xml"), new com.dexels.navajo.server.ClassloaderInputStreamReader());
//    System.err.println("Initialized direct connection");
//    System.err.println("Class access: "+Class.forName("com.dexels.navajo.adapter.NavajoAccess"));
    System.err.println("Opening: "+MainApplication.class.getResource(args[0]));
    TipiContext.getInstance().parseURL(MainApplication.class.getResource(args[0]));
  }
}
