package com.dexels.navajo.adapter;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 *
 * $Id$
 *
 * $Log$
 * Revision 1.2  2002/09/09 16:07:55  arjen
 * <No Comment Entered>
 *
 *
 */


import com.dexels.navajo.mapping.*;
import javax.naming.Context;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import java.util.ArrayList;

public class OptionMap implements Mappable {

  public OptionMap() {
  }
  public void load(Context parm1, Parameters parm2, Navajo parm3, Access parm4, ArrayList parm5) throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {

  }
  public void store() throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {

  }
  public void kill() {

  }
  public String getOptionName() {
    return optionName;
  }
  public void setOptionName(String optionName) {
    this.optionName = optionName;
  }
  public void setOptionSelected(boolean optionSelected) {
    this.optionSelected = optionSelected;
  }
  public boolean getOptionSelected() {
    return optionSelected;
  }
  public String getOptionValue() {
    return optionValue;
  }
  public void setOptionValue(String optionValue) {
    this.optionValue = optionValue;
  }
  public String optionName;
  public String optionValue;
  public boolean optionSelected;
}