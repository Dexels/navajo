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

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;

import java.util.*;


public class SelectionMap implements Mappable {

    private ArrayList optionsList = null;
    public String option;
    public OptionMap[]  options;

    public SelectionMap() {}

    public void load(Parameters parm2, Navajo parm3, Access parm4, NavajoConfig parm5) throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
        optionsList = new ArrayList();
    }

    public void store() throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {}

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
        OptionMap[] result = new OptionMap[optionsList.size()];

        result = (OptionMap[]) optionsList.toArray(result);
        return result;
    }

    public static void main(String args[]) {
        String option = ";;1";
        String name = option.substring(0, option.indexOf(";"));
        String value = option.substring(option.indexOf(";") + 1, option.lastIndexOf(";"));
        String selected = option.substring(option.lastIndexOf(";") + 1, option.length());

        System.out.println("name = " + name);
        System.out.println("value = " + value);
        System.out.println("selected = " + selected);
    }
}
