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
//  private final GenericPropertyComponent namePropertyComponent = new
//      GenericPropertyComponent();
//  private final GenericPropertyComponent valuePropertyComponent = new
//      GenericPropertyComponent();
//  private final GenericPropertyComponent descriptionPropertyComponent = new
//      GenericPropertyComponent();
//  private final GenericPropertyComponent idPropertyComponent = new
//      GenericPropertyComponent();
//  private GenericPropertyComponent titlePropertyComponent = new GenericPropertyComponent();
//
  private String questionDefinitionName = null;

  public TipiQuestion() {
  }

//  public Object createContainer() {
//    JPanel jj = new JPanel();
//    jj.setLayout(new BoxLayout(jj, BoxLayout.Y_AXIS));
//    jj.add(idPropertyComponent);
//    jj.add(namePropertyComponent);
//    jj.add(valuePropertyComponent);
//    jj.add(descriptionPropertyComponent);
//    return jj;
//  }

  public void setComponentValue(String name, Object object) {
    if (name.equals("messagePath")) {
      messagePath = (String) object;
    }
    if (name.equals("questionDefinitionName")) {
      questionDefinitionName = (String) object;
    }
    super.setComponentValue(name, object);
  }

  public void loadData(Navajo n, TipiContext context) throws TipiException {
    super.loadData(n,context);
    removeAllChildren();
    System.err.println("Loading with messagePath: "+messagePath);

    Message m = n.getMessage(messagePath);
    if (m==null) {
      System.err.println("No such message");
      return;
    }
    System.err.println("My message: ");
    m.write(System.err);
    System.err.println("My prefix: "+prefix);
    Property titleProperty = m.getProperty("Title");
//    Property valueProperty = m.getProperty("Value");
//    Property nameProperty = m.getProperty("Name");
    if (titleProperty != null) {
      String val = titleProperty.getValue();
      ( (JPanel) getContainer()).setBorder(BorderFactory.createTitledBorder("" +
          val));
    }
    else {
      ( (JPanel) getContainer()).setBorder(BorderFactory.createEtchedBorder());
    }
//    idPropertyComponent.setProperty(idProperty);
//    valuePropertyComponent.setProperty(valueProperty);
//    namePropertyComponent.setProperty(nameProperty);
//    descriptionPropertyComponent.setProperty(descriptionProperty);
    Message subQuestionMessage = m.getMessage("Question");
    if (questionDefinitionName==null) {
      System.err.println("No template name found for subquestions. Ignoring subquestions");
      return;
    }
    if (subQuestionMessage != null) {
      ArrayList subQuestions = subQuestionMessage.getAllMessages();
      System.err.println("Subquestions: " + subQuestions.size());
      for (int i = 0; i < subQuestions.size(); i++) {
        Message current = (Message) subQuestions.get(i);
        String id = current.getProperty("Id").getValue();
//        XMLElement xe = new CaseSensitiveXMLElement();
//        xe.setName("tipi-instance");
//        xe.setAttribute("id", id);
//        xe.setAttribute("class", "tipiquestion");
//        xe.setAttribute("messagePath",
//                        "'" + current.getFullMessageName() + "@" + i + "'");
//        System.err.println("xe: " + xe.toString());
        TipiDataComponent tc = (TipiDataComponent)TipiInstantiateTipi.instantiateByDefinition(this,false,id,questionDefinitionName);
        tc.setValue("messagePath","'" + current.getFullMessageName()+"'");
        tc.setPrefix(current.getFullMessageName());
        tc.setValue("questionDefinitionName","'"+questionDefinitionName+"'");
        tc.loadData(n, myContext);
      }
    }
    myContext.fireTipiStructureChanged(this);
  }

  public void load(XMLElement def, XMLElement instance, TipiContext context) throws
      TipiException {
    super.load(def, instance, context);
  }
}
