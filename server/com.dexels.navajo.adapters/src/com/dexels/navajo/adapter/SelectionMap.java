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
 * Revision 1.4  2012/03/20 10:43:16  frank
 * code improvements / cleaning
 *
 * Revision 1.3  2008/09/29 10:33:28  arjen
 * Several changes for Navajo Refactor (The NavaUnitTest Version)
 *
 * Revision 1.2  2004/05/05 12:17:51  arjen
 * Added support for killing async threads remotely.
 * Fixed bug in InitNavajoStatus causing unwanted duplication
 * of AsyncMappable objects.
 *
 * Revision 1.1.1.1  2003/04/16 14:09:33  arjen
 *
 *
 * Revision 1.6  2003/03/10 16:12:35  arjen
 * Post interfaced document version
 *
 * Revision 1.5  2002/11/06 09:33:46  arjen
 * Used Jacobe code beautifier over all source files.
 * Added log4j support.
 *
 * Revision 1.4  2002/09/25 12:05:59  arjen
 * *** empty log message ***
 *
 * Revision 1.3  2002/09/18 08:30:19  matthijs
 * <No Comment Entered>
 *
 * Revision 1.2  2002/09/09 16:08:07  arjen
 * <No Comment Entered>
 *
 *
 */

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;


public class SelectionMap implements Mappable {

    private List<OptionMap> optionsList = null;
    public String option;
    public OptionMap[]  options;
    
	private static final Logger logger = LoggerFactory
			.getLogger(SelectionMap.class);
	
    public SelectionMap() {}

@Override
    public void load(Access parm4) throws UserException, MappableException {
        optionsList = new ArrayList<OptionMap>();
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

        String name = option.substring(0, option.indexOf(";"));
        String value = option.substring(option.indexOf(";") + 1, option.lastIndexOf(";"));
        String selected = option.substring(option.lastIndexOf(";") + 1, option.length());

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

    public static void main(String args[]) {
        String option = ";;1";
        String name = option.substring(0, option.indexOf(";"));
        String value = option.substring(option.indexOf(";") + 1, option.lastIndexOf(";"));
        String selected = option.substring(option.lastIndexOf(";") + 1, option.length());

        logger.info("name = " + name);
        logger.info("value = " + value);
        logger.info("selected = " + selected);
    }

  public void setOptions(OptionMap[] options) {
    this.options = options;
  }
}
