package tipi;

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
    frame = new MainFrame();
    frame.setBounds(100,100,800,600);
    frame.show();
  }

  static public void main(String[] args){
    new MainApplication();
  }
}