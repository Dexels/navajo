package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.actions.*;
import java.util.*;
import com.dexels.navajo.tipi.internal.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiQuestionList
    extends TipiPanel {
  private String messagePath = null;
  private String questionDefinitionName = null;
  private String questionGroupDefinitionName = null;
  public TipiQuestionList() {
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
    return super.getComponentValue(name);
  }

  public void loadData(Navajo n, TipiContext context) throws TipiException {
    removeInstantiatedChildren();
    myNavajo = n;
    Message m = n.getMessage(messagePath);
    if (m == null) {
      return;
    }
    if (!m.getType().equals(Message.MSG_TYPE_ARRAY)) {
      return;
    }
    for (int i = 0; i < m.getArraySize(); i++) {
      Message current = m.getMessage(i);
      String id = current.getProperty("Id").getValue();
      TipiDataComponent tc = (TipiDataComponent) TipiInstantiateTipi.instantiateByDefinition(this, false, id,questionGroupDefinitionName);
      tc.setValue("messagePath", "'" + current.getFullMessageName() + "'");
      tc.setPrefix(current.getFullMessageName());
      tc.setValue("questionDefinitionName", "'" + questionDefinitionName + "'");
      tc.setValue("questionGroupDefinitionName", "'" + questionGroupDefinitionName + "'");
      tc.loadData(n, myContext);
      if (tc instanceof TipiQuestionGroup) {
        TipiQuestionGroup tqg = (TipiQuestionGroup) tc;
        tqg.setQuestionList(this);
      }
    }
    Message answer = n.getMessage("AnswerData");
    if (answer!=null) {
      insertAnswerData(answer);
    }

//    performTipiEvent("onLoad",null,false);
  }

  public boolean isValid() {
    for (int i = 0; i < getChildCount(); i++) {
      TipiDataComponent tc = (TipiDataComponent) getTipiComponent(i);
      Boolean b = (Boolean) tc.getValue("valid");
      System.err.println("checked group: " + tc.getId() + " returned: " + b.toString());
      if (!b.booleanValue()) {
        return false;
      }
    }
    System.err.println("No invalid groups found. List is valid.");
    return true;
  }

  public void setValid(boolean b) {
//    Map m = new HashMap();
//    m.put("valid",new Boolean(b));
//    try {
//      performTipiEvent("onValidationChanged", m, false);
//    }
//    catch (TipiException ex) {
//      ex.printStackTrace();
//    }
  }

  protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
    String serviceName = (String) compMeth.getEvaluatedParameter("serviceName", event).value;
    String serviceUrl = (String) compMeth.getEvaluatedParameter("serviceUrl", event).value;
    try {
      flatten(serviceName, serviceUrl);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
  }

  private void insertAnswerData(Message m) {
    System.err.println("Found: "+m.getArraySize()+" records.");
  }

  public void flatten(String serviceName, String server) throws NavajoException {
    Message questionList = getNavajo().getMessage("QuestionList@0");
    Navajo n = NavajoFactory.getInstance().createNavajo();
    Message m = getNavajo().getMessage("FormData").copy(n);
    n.addMessage(m);
    Message clubMsg = getNavajo().getMessage("Club").copy(n);
    n.addMessage(clubMsg);

    Property date = NavajoFactory.getInstance().createProperty(n, "TimeStamp", Property.DATE_PROPERTY, "", 0, "",
        Property.DIR_IN);
    date.setValue(new Date());
    m.addProperty(date);
    Message answers = NavajoFactory.getInstance().createMessage(n, "Answers", Message.MSG_TYPE_ARRAY);
    n.addMessage(answers);
    ArrayList questionGroups = questionList.getMessage("Group").getAllMessages();
    for (int i = 0; i < questionGroups.size(); i++) {
      Message group = (Message) questionGroups.get(i);
      flattenGroup(group, answers);
    }
    System.err.println("Navajo:::::::::::::::::");
    try {
      FileWriter fw = new FileWriter("c:/aap.xml");
      n.write(fw);
      fw.close();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    myContext.doSimpleSend(n, serviceName, this,-1,server);
  }

  private void flattenGroup(Message groupMessage, Message answerMessage) throws NavajoException {
    Property id = groupMessage.getProperty("Id");
    Message questions = groupMessage.getMessage("Question");
    if (questions == null) {
      return;
    }
    for (int i = 0; i < questions.getArraySize(); i++) {
      Message current = questions.getMessage(i);
      flattenQuestion(current, answerMessage, "/" + id.getValue());
    }
  }

  private void flattenQuestion(Message questionMessage, Message answerMessage, String prefix) throws NavajoException {
    Property value = questionMessage.getProperty("Value");
    Property id = questionMessage.getProperty("Id");
    Message answer = createAnswerMessage(answerMessage.getRootDoc(), prefix + "/" + id.getValue(), value);
    answerMessage.addMessage(answer);
    Message subQuestions = questionMessage.getMessage("Question");
    if (subQuestions == null) {
      return;
    }
    for (int i = 0; i < subQuestions.getArraySize(); i++) {
      Message current = subQuestions.getMessage(i);
      flattenQuestion(current, answerMessage, prefix + "/" + id.getValue());
    }
  }

  private Message createAnswerMessage(Navajo answerDoc, String id, Property value) throws NavajoException {
    Property newValue = NavajoFactory.getInstance().createProperty(answerDoc, "Value", value.getType(), value.getValue(),
        value.getLength(), value.getDescription(), value.getDirection());
    Message answerMessage = NavajoFactory.getInstance().createMessage(answerDoc, "Answer");
    Property idProp = NavajoFactory.getInstance().createProperty(answerDoc, "Id", Property.STRING_PROPERTY, id, 0, "",
        Property.DIR_IN);
    ArrayList al = value.getAllSelections();
    for (int i = 0; i < al.size(); i++) {
      Selection current = (Selection) al.get(i);
      Selection s = NavajoFactory.getInstance().createSelection(answerDoc, current.getName(), current.getValue(),
          current.isSelected());
      newValue.addSelection(s);
    }
    answerMessage.addProperty(idProp);
    answerMessage.addProperty(newValue);
    return answerMessage;
  }
}
