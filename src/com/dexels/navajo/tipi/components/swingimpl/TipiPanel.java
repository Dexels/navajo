package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiPanel
    extends TipiSwingDataComponentImpl {

  public Object createContainer() {
	  TipiSwingPanel myPanel = new TipiSwingPanel();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
//    myPanel.setOpaque(false);
	
    return myPanel;
  }

///**
// * This method is only overridden to be performed in the event thread.
// */
//  public void loadData(final Navajo n, final String method) throws TipiException {
//      myNavajo = n;
//      myMethod = method;
//     runSyncInEventThread(new Runnable(){
//        public void run() {
//            try {
//                TipiPanel.super.loadData(n,method);
//            } catch (TipiException e) {
//                e.printStackTrace();
//            } catch (TipiBreakException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
//            
//        }});
//  }

  public static void main(String[] args) {
	  JFrame jf = new JFrame("Aap");
	  jf.setSize(300,200);
	  jf.setVisible(true);
//	  jf.getContentPane().setLayout(new FlowLayout());
	  JPanel jp = new JPanel();
	  jp.setLayout(new GridBagLayout());
	  jp.setBorder(BorderFactory.createTitledBorder("Monkeeeyyy"));
	  jf.getContentPane().add(jp);
	  jp.add(Box.createHorizontalStrut(100), new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(2,2,2,2),0,0));
	  jp.add(new JLabel("Da monkeyy"), new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(2,2,2,2),0,0));
	  JTextField jr = new JTextField("");
	  jf.getContentPane().add(jp);

	  jp.add(jr, new GridBagConstraints(1,0,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(2,2,2,2),0,0));
	  jp.add(Box.createHorizontalStrut(100), new GridBagConstraints(1,0,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL,new Insets(2,2,2,2),0,0));
  }
  
  
}
