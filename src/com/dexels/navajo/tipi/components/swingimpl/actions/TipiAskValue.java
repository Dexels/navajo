package com.dexels.navajo.tipi.components.swingimpl.actions;

import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.tipi.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiAskValue extends TipiAction {
  public TipiAskValue() {
  }
  protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
    Operand text = getEvaluatedParameter("text",event);
    Operand globalvalue = getEvaluatedParameter("value",event);
    Operand initialValue = getEvaluatedParameter("initialValue",event);
    String initVal = "";
    if (initialValue!=null) {
      if (initialValue.value!=null) {
        initVal = ""+initialValue.value;
      }
    }
      String response = JOptionPane.showInputDialog( (Component) myContext.getTopLevel(), text.value, initVal);
//      , JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
      if (response != null) {
        myContext.setGlobalValue(""+globalvalue.value,response);
        TipiReference tr = (TipiReference)globalvalue.value;
        Operand o = new Operand(response,"string",null);

        tr.setValue(response,o,myComponent);

        System.err.println("Response accepted");
      } else {
          throw new TipiBreakException();

      }

  }

}
