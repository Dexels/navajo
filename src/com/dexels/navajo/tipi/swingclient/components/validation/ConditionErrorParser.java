package com.dexels.navajo.tipi.swingclient.components.validation;
import java.util.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */
public class ConditionErrorParser {
  private ArrayList failedProperties = new ArrayList();
  private Map failureMap = new HashMap();
  private static ResourceBundle res = null;
 // private Map codeMap = null;
  public ConditionErrorParser() {
    if (res == null) {

      try {
        res = ResourceBundle.getBundle("com.dexels.sportlink.client.swing.components.validation");
      }
      catch (MissingResourceException ex) {
//        System.err.println("No validation found.");
        return;
      }
    }
  }

  // Always insert message 'conditionerrors'
  public ArrayList getFailures(Message msg) {
    failureMap.clear();
    failedProperties.clear();
    if(msg == null){
      System.err.println("getFailures() received a NULL message");
      return null;
    }
    if (msg.getName().equals("ConditionErrors")) {
      ArrayList failures = msg.getAllMessages();
      for (int i = 0; i < failures.size(); i++) {
        Message current = (Message) failures.get(i);
        String expression = current.getProperty("FailedExpression").getValue();
        String id = current.getProperty("Id").getValue();
        parseExpression(expression, id);
      }
      return failedProperties;
    }
    else {
      System.err.println("Non-conditionerrors message fed to ConditionErrorParser");
    }
    return null;
  }

  private final void parseExpression(String expr, String id) {
    StringTokenizer tok = new StringTokenizer(expr, "]");
    //ArrayList tokens = new ArrayList();
    while (tok.hasMoreTokens()) {
      String token = tok.nextToken();
      if (token.indexOf("[") > -1) {
        //String property = token.substring(token.lastIndexOf("/")+1, token.length());
        String property = token.substring(token.indexOf("[") + 1, token.length());
        if (failureMap.get(property) == null) {
          failedProperties.add(property);
          failureMap.put(property, id);
        }
      }
    }
  }

  public Map getFailedPropertyIdMap() {
    return failureMap;
  }

  public String getDescription(String propertyPath) {
    if (res==null) {
        return "Unknown error.";
    }
    String id = (String) failureMap.get(propertyPath);
    String description = res.getString(id);
    return description;
  }
}
