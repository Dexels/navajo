package tipi;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.nanoimpl.*;

public class MainApplication {

  static public void main(String[] args) throws Exception {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    TipiContext.getInstance().setResourceURL(MainApplication.class.getResource(""));
    System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
    System.err.println("RES: "+TipiContext.getInstance().getResourceURL());
    new com.dexels.navajo.server.Dispatcher("server.xml");
    TipiContext.getInstance().parseURL(MainApplication.class.getResource("vla.xml"));
  }
}
