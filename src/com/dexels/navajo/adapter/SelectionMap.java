package com.dexels.navajo.adapter;

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
      StringTokenizer tokens = new StringTokenizer(option, ";");
      OptionMap om = new OptionMap();
      om.optionName = tokens.nextToken();
      om.optionValue = tokens.nextToken();
      om.optionSelected = (tokens.nextToken().equals("1"));
      optionsList.add(om);
  }

  public OptionMap [] getOptions() {
     OptionMap [] result = new OptionMap[optionsList.size()];
     result = (OptionMap []) optionsList.toArray(result);
     return result;
  }

}