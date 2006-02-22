package com.dexels.navajo.tipi.components.swingimpl;


import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;

import com.dexels.navajo.tipi.actions.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiBaseQuestionGroup extends TipiPanel {
  protected String messagePath = null;
  protected String questionDefinitionName = null;
  protected String questionGroupDefinitionName = null;

  protected final ArrayList myQuestions = new ArrayList();
  protected TipiBaseQuestionList myQuestionList = null;

 public TipiBaseQuestionGroup() {
  }

  public void setQuestionList(TipiBaseQuestionList tql) {
    myQuestionList = tql;
  }

  public void setComponentValue(String name, Object object) {
    if (name.equals("messagePath")) {
      messagePath = (String) object;
    }
    if (name.equals("questionDefinitionName")) {
      questionDefinitionName = (String) object;
    }
    if (name.equals("questionGroupDefinitionName")) {
      questionGroupDefinitionName = (String) object;
    }
    super.setComponentValue(name, object);
  }
  public Object getComponentValue(String name) {
     if (name.equals("valid")) {
       return new Boolean(isValid());
     }
     if (name.equals("description")) {
         return getDescription();
       }
     return super.getComponentValue(name);
   }


  public boolean isValid() {
    for (int i = 0; i < getChildCount(); i++) {
      if (getTipiComponent(i) instanceof TipiQuestion) {
          TipiQuestion tq = (TipiQuestion)getTipiComponent(i);
          if (!tq.isRecursiveValid()) {
            return false;
          }
      }
      
    }
    System.err.println("No invalid questions found. Group is valid.");
    return true;
  }

  public void loadData(Navajo n, TipiContext context,String method) throws TipiException {
    removeInstantiatedChildren();
    myQuestions.clear();
   Message m = n.getMessage(messagePath);
    if (m==null) {
      return;
    }
    Message question = m.getMessage("Question");
    if (question!=null) {
      for (int i = 0; i < question.getArraySize(); i++) {
        Message current = question.getMessage(i);
        
        String id = current.getProperty("Id").getValue();
        System.err.println("About to create a question with id: "+id);
        TipiQuestion tc = (TipiQuestion)TipiInstantiateTipi.instantiateByDefinition(this,false,id,questionDefinitionName,null);
        tc.setValue("messagePath",current.getFullMessageName());
        tc.setPrefix(current.getFullMessageName());
        tc.setValue("questionDefinitionName",questionDefinitionName);
        System.err.println("Creating question. Setting path: "+getPath());
//        tc.setValue("questionGroupPath",this);
       tc.setQuestionGroup(this);
        tc.loadData(n, myContext);
         myQuestions.add(tc);
      }
    }
    updateQuestions();

  }

  public String getDescription() {
      Navajo n = getNavajo();
      if (n==null) {
        return "-";
    }
    Message m = n.getMessage(messagePath);
    if (m==null) {
        return "--";
    }
    Property p = m.getProperty("Description");
    if (p==null) {
        return "---";
    }
    return ""+p.getValue();
  }
  
  public void updateQuestions() {
    for (int i = 0; i < myQuestions.size(); i++) {
      TipiQuestion tq = (TipiQuestion)myQuestions.get(i);
      tq.updateSubQuestions();
    }
    boolean valid = isValid();
    if (myQuestionList!=null) {
      myQuestionList.setValid(valid);
    }
  }

}
