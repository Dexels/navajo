package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RemarkPanel extends JPanel {
  private final TipiMegaTable myTable;
//  private final MessageTablePanel myMessageTable;
  private final Message myMessage;
//  private final TipiTableLayer myLayer;
  private final List<ConditionalRemark> conditionalRemarks;

  public RemarkPanel(TipiMegaTable tmt, Message m, List<ConditionalRemark> remarks) {
    myTable = tmt;
//    myMessageTable = mtp;
    myMessage = m;
//    myLayer = tmtl;
    conditionalRemarks = remarks;
  }

  public void updateConditionalRemarks() {
    if (conditionalRemarks.size() == 0) {
      return;
    }
    removeAll();
    int complied = 0;
    for (int i = 0; i < conditionalRemarks.size(); i++) {
      ConditionalRemark current = conditionalRemarks.get(i);
      Operand oo = null;
      try {
        oo = myTable.getContext().evaluate(current.getCondition(),
                                           myTable, null, myMessage);
      }
      catch (Throwable ex) {
        System.err.println("Error while updating remarks. Continuing.");
      }
      boolean complies = false;
      if (oo != null && oo.value != null) {
        Boolean b = (Boolean) oo.value;
        complies = b.booleanValue();
      }
      if (complies) {
        Operand o = myTable.getContext().evaluate(current.getRemark(), myTable, null,
                                                  myMessage);
        Operand q = myTable.getContext().evaluate(current.getColor(), myTable, null,
                                                  myMessage);
        Operand r = myTable.getContext().evaluate(current.getFont(), myTable, null,
                                                  myMessage);
        Color c = q == null ? null : (Color) q.value;
        Font f = r == null ? null : (Font) r.value;
//        Operand o = evaluate(current.getRemark(),this,null);
        add(createRemark("" + o.value, c, f),
                        new GridBagConstraints(0, complied, 1, 1, 1.0, 0.0,
                                               GridBagConstraints.WEST,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(1, 1, 1, 1), 0, 0));
        complied++;
      }
    }
    setVisible(complied > 0);
    revalidate();
//    mm.revalidate();
  }

  private Component createRemark(String remark, Color c, Font f) {
    JLabel ll = new JLabel(remark);
//    ll.setFont(ll.getFont().deriveFont(20.0f));
    if (f != null) {
      ll.setFont(f);
    }
    if (c != null) {
      ll.setForeground(c);
    }
    return ll;
  }

}
