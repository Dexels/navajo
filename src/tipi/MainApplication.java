package tipi;
import javax.swing.*;
import com.dexels.navajo.tipi.components.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MainApplication {

  MainFrame frame;

  public MainApplication() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    try{
      System.err.println("Making frame...");
      frame = new MainFrame();
      frame.setBounds(100,100,800,600);
      System.err.println("Showing frame..");
      frame.show();
    }catch(Exception e){
      System.err.println("Whoops, had an exception!");
      System.exit(-1);
    }
  }

  static public void main(String[] args){
    new MainApplication();
  }
}