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
 * Revision 1.3  2002/09/18 08:30:19  matthijs
 * <No Comment Entered>
 *
 * Revision 1.2  2002/09/09 16:08:07  arjen
 * <No Comment Entered>
 *
 *
 */

import com.dexels.navajo.mapping.Mappable;
import javax.naming.Context;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import java.util.*;

public class SelectionMap implements Mappable {

  private ArrayList optionsList = null;
  public String option;
  public OptionMap []  options;

  public SelectionMap() {
  }
  public void load(Context parm1, Parameters parm2, Navajo parm3, Access parm4, ArrayList parm5) throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
    optionsList = new ArrayList();
  }
  public void store() throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {

  }
  public void kill() {

  }

  /**
   * $option = 'Man;M;1';
   * @param option
   */
  public void setOption(String option) {

    String name = option.substring(0, option.indexOf(";"));
    String value = option.substring(option.indexOf(";")+1,option.lastIndexOf(";"));
    String selected = option.substring(option.lastIndexOf(";")+1, option.length());

    OptionMap om = new OptionMap();
    om.optionName = name;
    om.optionValue = value;
    om.optionSelected = selected.equals("1");
    optionsList.add(om);
  }

  public OptionMap [] getOptions() {
     OptionMap [] result = new OptionMap[optionsList.size()];
     result = (OptionMap []) optionsList.toArray(result);
     return result;
  }

  public static void main(String args[]) {
    String option = ";;1";
    String name = option.substring(0, option.indexOf(";"));
    String value = option.substring(option.indexOf(";")+1,option.lastIndexOf(";"));
    String selected = option.substring(option.lastIndexOf(";")+1, option.length());
    System.out.println("name = " + name);
    System.out.println("value = " + value);
    System.out.println("selected = " + selected);
  }
}