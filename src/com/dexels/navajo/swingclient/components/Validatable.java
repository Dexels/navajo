package com.dexels.navajo.swingclient.components;
import com.dexels.navajo.document.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public interface Validatable {

   public final int EMPTY = 0;
   public final int INVALID = 1;
   public final int ADJUSTED = 2;
   public final int VALID = 3;

   public void addValidationRule(int state);
   public void setValidationMessage(Message msg);
   public void checkValidation();
   public void checkValidation(Message msg);
   public void clearValidationRules();
   public void setValidationState(int state);
   public int getValidationState();

}