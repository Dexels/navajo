package com.dexels.navajo.tipi.components;

import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.nanoclient.*;
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
  private static Map codeMap = null;

  public ConditionErrorParser() {
    if(codeMap == null){
      codeMap = new HashMap();
      ResourceBundle res = null;
      res = ResourceBundle.getBundle("tipi.validation");
      for(int i=0;i<10000;i++){
        String description = "Unknown error code: ";
        String iText = String.valueOf(i);
        try{
          description = res.getString(iText);
        }catch(Exception e){
          description = description + iText;
        }
        //System.err.println("ConditionError : " + iText + ":" + description);
        codeMap.put(iText, description);
      }
    }
  }

  // Always insert message 'conditionerrors'
  public ArrayList getFailures(Message msg){
    failureMap.clear();
    failedProperties.clear();
    if(msg.getName().equals("ConditionErrors")){
      ArrayList failures = msg.getAllMessages();
      for(int i=0;i<failures.size();i++){
        Message current = (Message)failures.get(i);
        String expression  = (String)current.getProperty("FailedExpression").getValue();
        String id = (String)current.getProperty("Id").getValue();
        parseExpression(expression, id);
      }
      return failedProperties;
    }else{
      System.err.println("Non-conditionerrors message fed to ConditionErrorParser");
    }
    return null;
  }

  private void parseExpression(String expr, String id){
    StringTokenizer tok = new StringTokenizer(expr, "]");
    ArrayList tokens = new ArrayList();
    while(tok.hasMoreTokens()){
      String token = tok.nextToken();
      if(token.indexOf("[") > -1){
        //String property = token.substring(token.lastIndexOf("/")+1, token.length());
        String property = token.substring(token.indexOf("[")+1, token.length());
        if(failureMap.get(property) == null){
          failedProperties.add(property);
          failureMap.put(property, id);
        }
      }
    }
  }

  public Map getFailedPropertyIdMap(){
    return failureMap;
  }

  public String getDescription(String propertyPath){
    String id = (String)failureMap.get(propertyPath);
    String description = (String)codeMap.get(id);
    return description;
  }

//  static public void main(String args[]) throws Exception{
//    NavajoClient nc = new NavajoClient();
//    Navajo m = nc.doSimpleSend(new Navajo(), "InitInsertMember");
//    Navajo result = nc.doSimpleSend(m, "ProcessInsertMember");
//    Message errors = result.getMessage("conditionerrors");
//    ConditionErrorParser p = new ConditionErrorParser();
//    ArrayList f = p.getFailures(errors);
//    for(int i=0;i<f.size();i++){
//      String s = (String)f.get(i);
//      System.out.println("Property: " + s);
//    }
//  }

}
