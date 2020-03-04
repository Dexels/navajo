package com.dexels.navajo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;


public class SelectionMap implements Mappable {

    private List<OptionMap> optionsList = null;
    public String option;
    public OptionMap[]  options;

    public SelectionMap() {}

    @Override
    public void load(Access parm4) throws UserException, MappableException {
        optionsList = new ArrayList<>();
    }

    @Override
    public void store() throws UserException, MappableException {}

    @Override
	public void kill() {}

    /**
     * $option = 'Man;M;1';
     * @param option
     */
    public void setOption(String option) {

        String name = option.substring(0, option.indexOf(';'));
        String value = option.substring(option.indexOf(';') + 1, option.lastIndexOf(';'));
        String selected = option.substring(option.lastIndexOf(';') + 1, option.length());

        OptionMap om = new OptionMap();

        om.optionName = name;
        om.optionValue = value;
        om.optionSelected = selected.equals("1");
        optionsList.add(om);
    }

    public OptionMap[] getOptions() {

        if (options != null)
          return options;

        OptionMap[] result = new OptionMap[optionsList.size()];
        result = optionsList.toArray(result);
        return result;
    }



  public void setOptions(OptionMap[] options) {
    this.options = options;
  }
}
