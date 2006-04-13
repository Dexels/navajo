/*
 * Created on Apr 11, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.components.question.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiQuestion extends TipiBaseQuestion {
    private TipiSwingPanel myPanel;

  public void setValid(boolean b, String msg) {
    
            if (b == false) {
                Border bbb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red), msg);
                ((JComponent) getContainer()).setBorder(bbb);
            } else {
                Border bbb = BorderFactory.createEmptyBorder(2, 2, 2, 2);
                ((JComponent) getContainer()).setBorder(bbb);
            }
        }
  public Object createContainer() {

      myPanel = new TipiSwingPanel();
//      TipiHelper th = new TipiSwingHelper();
//      th.initHelper(this);
//      addHelper(th);
      myPanel.setLayout(new GridBagLayout());
//      ((Container)object).setPreferredSize(new Dimension(800,100));
       return myPanel;
  }

  public void addToContainer(Object c, Object constraints) {

          myPanel.add((Component)c, new GridBagConstraints(0,0,1,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
  }

  protected void setQuestionBorder(String val) {
      if (val==null) {
          ((JPanel) getContainer()).setBorder(BorderFactory.createTitledBorder("" + val));
      } else {
          ((JPanel) getContainer()).setBorder(BorderFactory.createLoweredBevelBorder());
      }        
  }
  
  public void runASyncInEventThread(Runnable r) {
      if (SwingUtilities.isEventDispatchThread()) {
        r.run();
      }
      else {
        SwingUtilities.invokeLater(r);
      }
    }
public void setQuestionVisible(boolean b) {
    ((JComponent)getContainer()).setVisible(isRelevant());
    
}  

}
