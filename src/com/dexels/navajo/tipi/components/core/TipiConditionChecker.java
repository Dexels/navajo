package com.dexels.navajo.tipi.components.core;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.document.*;
import java.util.*;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.TipiBreakException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiConditionChecker extends TipiDataComponentImpl {
  private String messagePath = null;
  private final ArrayList failedList = new ArrayList();
  public TipiConditionChecker() {
  }
  public Object createContainer() {
    return null;
  }

  public void setComponentValue(String name, Object object) {
    if ("messagePath".equals(name)) {
      messagePath = (String)object;
    }
  }

  private String getFailedHTMLString() {
    StringBuffer sb = new StringBuffer();
      sb.append("<html>");
      sb.append("<b>Aan de volgende voorwaarde(n) wordt niet voldaan:</b>");
      for (int i = 0; i < failedList.size(); i++) {
        String s = (String)failedList.get(i);
        sb.append(s);
        sb.append("<p>");
      }
      sb.append("</html>");
    return sb.toString();
  }

  public Object getComponentValue(String name) {
    if ("failedList".equals(name)) {
      return getFailedHTMLString();
    }
    return super.getComponentValue(name);
  }

  private void updateCondition(Message m) {
    failedList.clear();
    for (int i = 0; i < m.getArraySize(); i++) {
      Message current = m.getMessage(i);
      Property condition = current.getProperty("Condition");
      Property errorMessage = current.getProperty("ErrorMessage");
      try {
        condition.refreshExpression();
        Object o = condition.getTypedValue();
        if (o instanceof Boolean && ((Boolean)o).booleanValue()==false) {
          failedList.add(errorMessage.getTypedValue());
        }
      }
      catch (NavajoException ex) {
        System.err.println("Condition: "+condition.getValue()+" failed to evaluate. Assuming true");
      }

    }
  }

  public void loadData(Navajo n, TipiContext context, String method,String server) throws TipiException {
    if (messagePath==null) {
      return;
    }
    Message m = n.getMessage(messagePath);
    if (m==null) {
      return;
    }
    updateCondition(m);
  }

  protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
    if (name.equals("refresh")) {
      if (messagePath==null) {
        return;
      }
      if (getNearestNavajo()==null) {
        return;
      }
      Message m = getNearestNavajo().getMessage(messagePath);
      if (m==null) {
        return;
      }
      updateCondition(m);

    }
  }


}
