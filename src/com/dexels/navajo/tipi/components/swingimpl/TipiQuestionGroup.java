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

public class TipiQuestionGroup extends TipiPanel {
  private String messagePath = null;
  private String questionDefinitionName = null;
  private String questionGroupDefinitionName = null;

  private final ArrayList myQuestions = new ArrayList();

 public TipiQuestionGroup() {
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

  public void loadData(Navajo n, TipiContext context) throws TipiException {
    removeInstantiatedChildren();
    myQuestions.clear();
   Message m = n.getMessage(messagePath);
    if (m==null) {
      return;
    }
    Message group = m.getMessage("Group");
    if (group!=null) {
      for (int i = 0; i < group.getArraySize(); i++) {
        Message current = group.getMessage(i);
        String id = current.getProperty("Id").getValue();
        TipiDataComponent tc = (TipiDataComponent)TipiInstantiateTipi.instantiateByDefinition(this,false,id,questionGroupDefinitionName);
        tc.setValue("messagePath","'" + current.getFullMessageName()+"'");
        tc.setPrefix(current.getFullMessageName());
        tc.setValue("questionDefinitionName","'"+questionDefinitionName+"'");
        tc.setValue("questionGroupDefinitionName","'"+questionGroupDefinitionName+"'");
        tc.loadData(n, myContext);
            }
    }
    Message question = m.getMessage("Question");
    if (question!=null) {
      for (int i = 0; i < question.getArraySize(); i++) {
        Message current = question.getMessage(i);
        String id = current.getProperty("Id").getValue();
        System.err.println("About to create a question with id: "+id);
        TipiQuestion tc = (TipiQuestion)TipiInstantiateTipi.instantiateByDefinition(this,false,id,questionDefinitionName);
        tc.setValue("messagePath","'" + current.getFullMessageName()+"'");
        tc.setPrefix(current.getFullMessageName());
        tc.setValue("questionDefinitionName","'"+questionDefinitionName+"'");
        System.err.println("Creating question. Setting path: "+getPath());
//        tc.setValue("questionGroupPath",this);
       tc.setQuestionGroup(this);
        tc.loadData(n, myContext);
         myQuestions.add(tc);
      }
    }
    updateQuestions();

  }

  public void updateQuestions() {
    for (int i = 0; i < myQuestions.size(); i++) {
      TipiQuestion tq = (TipiQuestion)myQuestions.get(i);
      tq.updateSubQuestions();
    }
  }

}
