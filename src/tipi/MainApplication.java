package tipi;
import javax.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.nanoimpl.*;

public class MainApplication {

  static public void main(String[] args) throws Exception {
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    TipiContext.getInstance().setResourceURL(MainApplication.class.getResource(""));
    System.err.println("RES: "+TipiContext.getInstance().getResourceURL());
    TipiContext.getInstance().parseURL(MainApplication.class.getResource("vla.xml"));

  }
}