package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import java.util.*;
import com.dexels.navajo.tipi.tipixml.*;
import javax.swing.*;
import java.awt.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.parser.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiQuestion
    extends TipiPanel {
  private String messagePath = null;
  private String questionDefinitionName = null;
//  private TipiQuestionGroup questionGroupPath = null;
  private TipiBaseQuestionGroup questionGroup = null;

  private final ArrayList mySubQuestions = new ArrayList();
  private String enabledCondition = null;
  private String visibleCondition = null;
  private String validationCondition = null;
  private Message myMessage = null;

  private String myId;

  public TipiQuestion() {
  }

  public void setComponentValue(String name, Object object) {
    if (name.equals("messagePath")) {
        System.err.println("Messagepath: >"+object+"<");
      messagePath = (String) object;
    }
    if (name.equals("questionDefinitionName")) {
        System.err.println("questionDefinitionName: >"+object+"<");
      questionDefinitionName = (String) object;
    }
    super.setComponentValue(name, object);
  }

  public void setQuestionGroup(TipiBaseQuestionGroup tqg) {
    questionGroup = tqg;
  }

  public void loadData(final Navajo n, final TipiContext context) throws TipiException {
//   runSyncInEventThread(new Runnable() {
//      public void run() {
      enabledCondition = null;
      visibleCondition = null;
      validationCondition = null;
//
      removeInstantiatedChildren();
//
//    removeAllChildren();
//
      mySubQuestions.clear();
      Message m = n.getMessage(messagePath);
      myMessage = m;
      if (m == null) {
        return;
      }
      Property titleProperty = m.getProperty("Title");
      if (titleProperty != null) {
        String val = titleProperty.getValue();
        ( (JPanel) getContainer()).setBorder(BorderFactory.createTitledBorder("" +
            val));
      }
      else {
        ( (JPanel) getContainer()).setBorder(BorderFactory.
                                             createLoweredBevelBorder());
      }
      Property visibleConditionProperty = m.getProperty("VisibleCondition");
      if (visibleConditionProperty != null) {
        visibleCondition = visibleConditionProperty.getValue();
      }
      Property enabledConditionProperty = m.getProperty("EnabledCondition");
      if (enabledConditionProperty != null) {
        enabledCondition = enabledConditionProperty.getValue();
      }
      Property validationConditionProperty = m.getProperty("ValidationCondition");
      if (validationConditionProperty != null) {
        validationCondition = validationConditionProperty.getValue();
      }
      for (int i = 0; i < properties.size(); i++) {
//          System.err.println("Property found!");
        PropertyComponent o = (PropertyComponent) properties.get(i);
        o.addTipiEventListener(new TipiEventListener() {
          public boolean performTipiEvent(String eventtype, Map source,
                                          boolean sync) throws TipiException {
            runASyncInEventThread(new Runnable() {
              public void run() {
                updateQuestionGroup();
              }
            });
            return true;
          }

          public void eventStarted(TipiExecutable te, Object event) {}

          public void eventFinished(TipiExecutable te, Object event) {}
        });
//          System.err.println("Property: "+o);
      }
      TipiDataComponent tdc = (TipiDataComponent) getTipiComponent("SubQuestions");
      Message question = m.getMessage("Question");
      if (question != null && tdc != null) {
        for (int i = 0; i < question.getArraySize(); i++) {
//          System.err.println(">>>>>>>>>>>>>>>>> LOOPING:: "+i+" of "+question.getArraySize());
          Message current = question.getMessage(i);
          Property idProp = current.getProperty("Id");
//
//          current.write(System.err);
//          Thread.dumpStack();
          if (idProp==null) {
            System.err.println("No id property found. Message: ");
            current.write(System.err);
            continue;
          }
          myId = idProp.getValue();
          try {
//            System.err.println("\n\n**************************************\nAdding component:..."+id+". \n*******************************************");
            TipiQuestion tc = (TipiQuestion) TipiInstantiateTipi.
                instantiateByDefinition(tdc, false, myId, questionDefinitionName,null);
//            System.err.println("Setting fullmsgname: "+current.getFullMessageName());
            tc.setValue("messagePath", current.getFullMessageName());
            tc.setPrefix(current.getFullMessageName());
            tc.setValue("questionDefinitionName",
                       questionDefinitionName );
            tc.loadData(n, myContext);
            tc.setQuestionGroup(questionGroup);
            mySubQuestions.add(tc);
          }
          catch (TipiException ex) {
            ex.printStackTrace();
          }
        }
      }
//    updateSubQuestions();
//    getSwingContainer().doLayout();
      try {
        oldLoadData(n, context);
      }
      catch (TipiException ex1) {
        ex1.printStackTrace();
      }

//      }
//    });
  }


  public void oldLoadData(Navajo n, TipiContext tc) throws TipiException {
    if (n == null) {
      throw new TipiException("Loading with null Navajo! ");
    }
    for (int i = 0; i < properties.size(); i++) {
      PropertyComponent current = (PropertyComponent) properties.get(i);
      Property p;
      if (prefix != null) {
        p = n.getProperty(prefix + "/" + current.getPropertyName());
        current.setProperty(p);
      }
      else {
        p = n.getProperty(current.getPropertyName());
        if (p != null) {
          current.setProperty(p);
        }
      }
    }
    if (n == null) {
      System.err.println("NULL NAVAJO!");
      return;
    }
    myNavajo = n;
  }


  public void updateQuestionGroup() {
//    if (questionGroupPath != null) {
//      Operand o = myContext.evaluate(questionGroupPath, this, null, myMessage);
//      TipiQuestionGroup tqg = (TipiQuestionGroup) o.value;
      if (questionGroup != null) {
        System.err.println("updatin: "+questionGroup.getPath());
        questionGroup.updateQuestions();
      } else {
        System.err.println("Not found");
      }
//    }
  }

  public boolean isRelevant() {
//    if (true) {
//      return true;
//    }
    if (visibleCondition != null) {
//      System.err.println("Evaluating: visibleCondition: " + visibleCondition);
//      System.err.println("ID: "+myId);
      Operand o = myContext.evaluate(visibleCondition, this, null, myMessage);
      if (o != null) {
        return ( (Boolean) o.value).booleanValue();
      }
      return false;
    }
    return true;
  }

  public void updateSubQuestions() {
    if (visibleCondition != null) {
      getSwingContainer().setVisible(isRelevant());
//      ((JComponent)getSwingContainer()).scrollRectToVisible(getSwingContainer().getBounds());
    }
    boolean invalidFound = false;
    for (int i = 0; i < mySubQuestions.size(); i++) {
      TipiQuestion tq = (TipiQuestion) mySubQuestions.get(i);
      tq.updateSubQuestions();
      if (tq.isValid() == false) {
        invalidFound = true;
        System.err.println("FOUND AN INVALID CHILD: " + tq.getPath());
      }
    }
    if (invalidFound) {
      setValid(false,"Subvraag incorrect");
    }
    else {
      if (!isValid()) {
        setValid(false);
      }
      else {
        setValid(true);
      }
    }
  }

  public boolean isRecursiveValid() {
    if (!isValid() ) {
      return false;
    }
    if (!isRelevant()) {
      return true;
    }
    for (int i = 0; i < mySubQuestions.size(); i++) {
      TipiQuestion tq = (TipiQuestion) mySubQuestions.get(i);
      if (tq.isRecursiveValid() == false) {
        return false;
      }
    }
    return true;
  }

  public void setValid(boolean b) {
    Property errorMessageProperty = myMessage.getProperty("ErrorMessage");
   String errorMessage = null;
   String msg = "Error";
   if (errorMessageProperty!=null) {
     errorMessage = errorMessageProperty.getValue();
   }
   if (errorMessage!=null) {
     Operand o = myContext.evaluate(errorMessage, this, null, myMessage);
     msg = ""+o.value;
   }
   setValid(b,msg);
 }

  public void setValid(boolean b, String msg) {

    if (b==false) {
       Border bbb = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.red),msg);
       ((JComponent)getSwingContainer()).setBorder(bbb);
    } else {
      Border bbb = BorderFactory.createEmptyBorder(2,2,2,2);
      ((JComponent)getSwingContainer()).setBorder(bbb);
    }
  }

  public boolean isValid() {
    if (!isRelevant()) {
      return true;
    }
//
//    return true;
//
    if (validationCondition != null) {
//      System.err.println("validationCondition: " + validationCondition);
      Operand o = myContext.evaluate(validationCondition, this, null, myMessage);
      if (o != null) {
        boolean result = ( (Boolean) o.value).booleanValue();
        return result;
      }
      else {
        return true;
      }
    }
    else {
      return true;
    }
  }

  public void load(XMLElement def, XMLElement instance, TipiContext context) throws
      TipiException {
    super.load(def, instance, context);
  }
}
