package com.dexels.navajo.nanoclient;

import com.dexels.navajo.document.*;

//import com.dexels.sportlink.client.swing.components.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.swingclient.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.document.nanoimpl.*;

public class AdvancedNavajoClient {

  private static NavajoClient client = NavajoClient.getInstance();
  private static boolean useLazyMessaging = false;

  public AdvancedNavajoClient() {
  }

  public static Navajo doSimpleSend(String service) {
    return doSimpleSend(NavajoFactory.getInstance().createNavajo(),service);
  }

  public static Message doSimpleSend(Navajo msg, String service, String messageName) {
    Navajo result = doSimpleSend(msg,service);
    if (result!=null) {
      return result.getMessage(messageName);
    }
    return null;
  }

  public static Navajo doSimpleSend(Navajo msg, String service) {
    Navajo result;
    try {
//      frame.setWaiting(true);
      result = client.doSimpleSend(msg,service);
      if (result!=null) {
        new ErrorHandler(result);
        return result;
      }
      return result;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      new ErrorHandler(ex);
      return null;
    } finally {
//      frame.setWaiting(false);
    }
  }

  public static void doAsyncSend(Navajo msg, String service) {
    client.doAsyncSend(msg,service,null,null);
  }

  public static void doAsyncSend(Navajo msg, String service, ResponseListener rl) {
    client.doAsyncSend(msg,service, rl,null);
  }

  public static void doAsyncSend(Navajo msg, String service, ResponseListener rl, String responseId) {
    client.doAsyncSend(msg,service,rl,responseId);
  }

  public static Message doSimpleSend(String service,String messageName){
    return doSimpleSend(service).getMessage(messageName);
  }

  public static Navajo doSimpleSend(String service, ConditionErrorHandler v){
    return doSimpleSend(NavajoFactory.getInstance().createNavajo() , service, v);
  }

  public static Message doSimpleSend(Navajo msg, String service, String messageName, ConditionErrorHandler v){
    return doSimpleSend(msg,service, v).getMessage(messageName);
  }

  public static Message doSimpleSend(String service, String messageName, ConditionErrorHandler v){
    return doSimpleSend(service, v).getMessage(messageName);
  }

  public static Navajo doSimpleSend(Navajo msg, String service, ConditionErrorHandler v){
    Navajo result = doSimpleSend(msg, service);
    Message conditionErrors = result.getMessage("conditionerrors");
    if(conditionErrors != null){
      v.checkValidation(conditionErrors);
    }
    return result;
  }

  public static LazyMessage doLazySend(Navajo n, String service, String lazyMessageName, int startIndex, int endIndex) {
/** @todo Fix again */
//    n.addLazyMessage(lazyMessageName,startIndex,endIndex);
    Navajo reply = doSimpleSend(n, service);
    Message m = reply.getMessage(lazyMessageName);
    if (m == null) {
//      System.err.println(n.toXml().toString());
      return null;
    }

    if (!LazyMessage.class.isInstance(m)) {
      System.err.println("No lazy result returned after lazy send!");
    } else {
      LazyMessage l = (LazyMessage)m;
      l.setResponseMessageName(lazyMessageName);
/** @todo FIX AGAIN */
//      l.setRequest(service,n);
      return l;
    }
    return (LazyMessage)m;
  }

  public static boolean useLazyMessaging() {
    return useLazyMessaging;
  }

  public static void setUseLazyMessaging(boolean b) {
    useLazyMessaging = b;
  }

  public static String getDistrict(){
    return client.getDistrict();
  }

   public static String getUsername() {
    return client.getUsername();
  }

  public static String getPassword() {
    return client.getPassword();
  }
  public static String getServerUrl() {
    return client.getServerUrl();
  }

  public static void setUsername(String s) {
   client.setUsername(s);
  }

  public static void setPassword(String s) {
    client.setPassword(s);
  }

  public static void setServerUrl(String s) {
    client.setServerUrl(s);
  }

//  public static void doQueuedSend(Navajo request, String service, ResponseListener rl, String responseId, String queueName) {
//  }


}