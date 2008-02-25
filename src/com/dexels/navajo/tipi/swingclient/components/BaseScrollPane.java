package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.*;
import com.dexels.navajo.client.ConditionErrorHandler;
import com.dexels.navajo.document.Message;
import java.awt.Component;

public class BaseScrollPane extends JScrollPane implements ConditionErrorHandler{

  public BaseScrollPane() {
  }

  public boolean hasConditionErrors(){
    Component view = getViewport().getView();
    if(BasePanel.class.isInstance(view)){
      BasePanel b = (BasePanel)view;
      return b.hasConditionErrors();
    }
    return false;
  }

  public void checkValidation(Message msg){
    Component view = getViewport().getView();
    if(BasePanel.class.isInstance(view)){
      BasePanel b = (BasePanel)view;
      b.checkValidation(msg);
    }
  }

  public void clearConditionErrors(){
    Component view = getViewport().getView();
    if(BasePanel.class.isInstance(view)){
      BasePanel b = (BasePanel)view;
      b.clearConditionErrors();
    }

  }
}
