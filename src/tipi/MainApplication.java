package tipi;
import javax.swing.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import com.dexels.navajo.swingclient.components.*;
import java.io.*;
import nanoxml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MainApplication {

//  static MainFrame frame;
  private static TipiContext myContext = null;

  public MainApplication() {
    myContext = TipiContext.getInstance();
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      loadXmlStream(getClass().getResource("test.xml").openStream());
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

 public void loadXML(String fileName){
    try {
     loadXmlStream(new FileInputStream(fileName));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void loadXmlStream(InputStream is) throws IOException, XMLParseException,TipiException {
      myContext.parseStream(is);
  }

  static public void main(String[] args){
    new MainApplication();
  }
}